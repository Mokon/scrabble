package net.mokon.scrabble ;

import java.util.Collections ;
import java.util.Comparator ;
import java.util.Vector ;

/**
 * This is the main class for placing words and such on the board.
 * 
 * @author David 'Mokon' Bond
 * @since 1.0, Jan 1, 2008
 */
public class Player {

  public Dictionary           dictionary = new Dictionary ( ) ;

  public Vector<Character>    hand ;
  public static ScrabbleBoard board ;

  /**
   * Constructor
   * 
   * @param board
   */
  public Player ( ScrabbleBoard board ) {
    this.hand = new Vector<Character> ( ) ;
    Player.board = board ;
  }

  public boolean checkIntercept ( int x, int y, boolean isUpDown, int xOrg,
      int yOrg ) {
    return isUpDown && x == xOrg || !isUpDown && y == yOrg ;
  }

  public boolean checkSafety ( Word word, int position, int x, int y,
      boolean isUpDownCheck ) {
    if ( position >= word.length ( ) ) { return true ; }
    final Vector<Character> chars = new Vector<Character> ( ) ;
    chars.add ( word.get ( position ) ) ;

    boolean good = true ;
    int i = 1 ;
    do {
      final int xDiff = isUpDownCheck ? i : 0 ;
      final int yDiff = isUpDownCheck ? 0 : i ;

      good &= x - xDiff >= 0 ;
      good &= y - yDiff >= 0 ;
      if ( good ) {
        good &= Player.board.charGrid[ x - xDiff ][ y - yDiff ] != Player.board.blank ;
        if ( good ) {
          chars.add ( Player.board.charGrid[ x - xDiff ][ y - yDiff ] ) ;
        }
      }
      i++ ;
    } while ( good ) ;
    Collections.reverse ( chars ) ;

    good = true ;
    i = 1 ;
    do {
      final int xDiff = isUpDownCheck ? i : 0 ;
      final int yDiff = isUpDownCheck ? 0 : i ;

      good &= x + xDiff < Player.board.boardWidth ;
      good &= y + yDiff < Player.board.boardHeight ;

      if ( good ) {
        good &= Player.board.charGrid[ x + xDiff ][ y + yDiff ] != Player.board.blank ;
        if ( good ) {
          chars.add ( Player.board.charGrid[ x + xDiff ][ y + yDiff ] ) ;
        }
      }
      i++ ;
    } while ( good ) ;
    StringBuilder sb = new StringBuilder ( ) ;
    for ( final char c : chars ) {
      sb.append ( c ) ;
    }
    if ( sb.length ( ) == 1
        || this.dictionary.contains ( new StringWord ( sb.toString ( ) ) ) ) {
      return this.checkSafety ( word, position + 1, x
          + ( isUpDownCheck ? 0 : 1 ), y + ( isUpDownCheck ? 1 : 0 ),
          isUpDownCheck ) ;
    } else {
      return false ;
    }
  }

  /**
   * 
   * @param word
   * @param position
   * @param x
   * @param y
   * @param isUpDownCheck
   * @param hand
   * @param orgHandSize
   * @return
   */
  public boolean checkWord ( Word word, int position, int x, int y,
      boolean isUpDownCheck, Vector<Character> hand, int orgHandSize ) {
    if ( position >= word.length ( ) ) {

      final int xOrg = x - ( isUpDownCheck ? 0 : position + 1 ) ;
      final int yOrg = y - ( isUpDownCheck ? position + 1 : 0 ) ;
      boolean toRet = orgHandSize > hand.size ( ) ;
      if ( x >= 0 && y >= 0 && x < Player.board.boardWidth
          && y < Player.board.boardHeight ) {
        toRet &= Player.board.charGrid[ x ][ y ] == Player.board.blank ;
      }
      if ( xOrg >= 0 && yOrg >= 0 && xOrg < Player.board.boardWidth
          && yOrg < Player.board.boardHeight ) {
        toRet &= Player.board.charGrid[ xOrg ][ yOrg ] == Player.board.blank ;
      }
      return toRet ;
    }

    if ( x >= Player.board.boardWidth || y >= Player.board.boardHeight ) { return false ; }
    if ( Player.board.charGrid[ x ][ y ] != Player.board.blank ) {
      if ( word.get ( position ) != Player.board.charGrid[ x ][ y ]
          && Player.board.charGrid[ x ][ y ] != '*' ) {
        return false ;
      } else {
        return this.checkWord ( word, position + 1, x
            + ( isUpDownCheck ? 0 : 1 ), y + ( isUpDownCheck ? 1 : 0 ),
            isUpDownCheck, hand, orgHandSize ) ;
      }
    } else {
      if ( hand.isEmpty ( ) || !hand.contains ( word.get ( position ) ) ) {
        if ( hand.contains ( '*' ) ) {
          hand.remove ( new Character ( '*' ) ) ;
        } else {
          return false ;
        }
      } else {
        hand.remove ( new Character ( word.get ( position ) ) ) ;
      }
      return this.checkWord ( word, position + 1,
          x + ( isUpDownCheck ? 0 : 1 ), y + ( isUpDownCheck ? 1 : 0 ),
          isUpDownCheck, hand, orgHandSize ) ;
    }
  }

  /**
   * Computes all the possible moves from the given board position ordered by
   * score.
   */
  public Vector<Move> commuteNextMove ( ) {
    final Vector<Move> moves = new Vector<Move> ( ) ;
    /*
     * Go through each square on the board and if it is blank get the words from
     * it by going though each word in the dictionary and getting the up and
     * down words from it.
     */
    for ( int i = 0 ; i < Player.board.boardHeight ; i++ ) {
      for ( int j = 0 ; j < Player.board.boardWidth ; j++ ) {
        if ( Player.board.charGrid[ i ][ j ] != Player.board.blank ) {
          for ( final Word word : this.dictionary.dictionary ( ) ) {
            this.getMovesFromWord ( word, i, j, moves, true ) ; ;
            this.getMovesFromWord ( word, i, j, moves, false ) ;
          }
        }
      }
    }
    Collections.sort ( moves, new MoveComparator ( ) ) ;
    return moves ;
  }

  public Vector<Move> computeFirstMove ( ) {
    final Vector<Move> moves = new Vector<Move> ( ) ;
    for ( final Word word : this.dictionary.dictionary ( ) ) {
      if ( word.getWord ( ).equals ( "at" ) ) {
        int j = 99 ;
        j++ ;
      }
      this.getMovesFromWord ( word, 7, 7, moves, true ) ;
      this.getMovesFromWord ( word, 7, 7, moves, false ) ;
    }
    Collections.sort ( moves, new MoveComparator ( ) ) ;
    return moves ;
  }

  public void draw ( char c ) {
    this.hand.add ( c ) ;
  }

  public void getMovesFromWord ( Word word, int x, int y, Vector<Move> moves,
      boolean isUpDownCheck ) {
    for ( int i = 0 ; i < word.length ( ) ; i++ ) {
      final int xNew = x - ( isUpDownCheck ? 0 : i ) ;
      final int yNew = y - ( isUpDownCheck ? i : 0 ) ;
      if ( xNew >= 0 && yNew >= 0 && xNew < Player.board.boardWidth
          && yNew < Player.board.boardHeight ) {
        this.getMovesFromWordPositional ( word, xNew, yNew, moves,
            isUpDownCheck, x, y ) ;
      }
    }
  }

  public void getMovesFromWordPositional ( Word WORD, int x, int y,
      Vector<Move> moves, boolean isUpDown, int xOrg, int yOrg ) {

    boolean has = this.checkWord ( WORD, 0, x, y, isUpDown,
        new Vector<Character> ( this.hand ), this.hand.size ( ) ) ;

    if ( has ) {
      has &= this.checkIntercept ( x, y, isUpDown, xOrg, yOrg ) ;
      if ( has && this.checkSafety ( WORD, 0, x, y, isUpDown ) ) {
        String w = WORD.getWord ( ) ;
        final Move move = new Move ( x, y, isUpDown, w, Player.board ) ;
        for ( final Move m : moves ) {
          if ( m.equals ( move ) ) { return ; }
        }
        moves.add ( move ) ;
      }
    }
  }

  public static class MoveComparator implements Comparator<Move> {

    @Override public int compare ( Move arg0, Move arg1 ) {
      return arg0.getScore ( Player.board ) - arg1.getScore ( Player.board ) ;
    }

  }
}
