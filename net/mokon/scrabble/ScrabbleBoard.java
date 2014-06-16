package net.mokon.scrabble ;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable ;
import java.util.Scanner;
import java.util.Vector ;
import net.mokon.scrabble.core.Bag ;

/**
 * This class represents the basic scrabble board. It has methods to place words
 * , export to and from CVS, and other such utilities.
 * 
 * @author David 'Mokon' Bond
 * @since 1.0, Jan 1, 2008
 */
public class ScrabbleBoard implements Serializable {

  /**
   * What represents a blank character on this board.
   */
  public final char         blank            = '-' ;

  /**
   * The board height in squares.
   */
  public final int          boardHeight      = 15 ;

  /**
   * The board width in squares.
   */
  public final int          boardWidth       = 15 ;

  /**
   * The character grid.
   */
  public char[][]           charGrid ;

  /**
   * A flag indicating whether or not the board has been constructed.
   */
  public boolean            isReady ;

  /**
   * The bag of characters that can be drawn from.
   */
  public Bag                bag ;

  /**
   * The type grid.
   */
  public byte[][]           typeGrid ;

  /**
   * The value grid.
   */
  public byte[][]           valueGrid ;
  
  /**
   * Version Interop. 
   */
  private static final long serialVersionUID = 2961082521937002182L ;

  /**
   * Constructor which creates the default tournament scrabble board.
   */
  public ScrabbleBoard ( ) {
    this.isReady = false ;
    
    this.charGrid = new char[ this.boardHeight ][ this.boardWidth ] ;
    for ( int i = 0 ; i < this.boardHeight ; i++ ) {
      for ( int j = 0 ; j < this.boardWidth ; j++ ) {
        this.charGrid[ i ][ j ] = this.blank ;
      }
    }

    this.valueGrid = new byte[ this.boardHeight ][ this.boardWidth ] ;
    for ( int i = 0 ; i < this.boardHeight ; i++ ) {
      for ( int j = 0 ; j < this.boardWidth ; j++ ) {
        this.valueGrid[ i ][ j ] = 1 ;
      }
    }

    this.typeGrid = new byte[ this.boardHeight ][ this.boardWidth ] ;
    for ( int i = 0 ; i < this.boardHeight ; i++ ) {
      for ( int j = 0 ; j < this.boardWidth ; j++ ) {
        this.typeGrid[ i ][ j ] = 0 ;
      }
    }

    /* Set the Special Board Positions */
    this.typeGrid[ 0 ][ 0 ] = 3 ;
    this.typeGrid[ 0 ][ 14 ] = 3 ;
    this.typeGrid[ 14 ][ 14 ] = 3 ;
    this.typeGrid[ 14 ][ 0 ] = 3 ;
    this.typeGrid[ 0 ][ 7 ] = 3 ;
    this.typeGrid[ 7 ][ 0 ] = 3 ;
    this.typeGrid[ 7 ][ 14 ] = 3 ;
    this.typeGrid[ 14 ][ 7 ] = 3 ;

    this.valueGrid[ 1 ][ 5 ] = 3 ;
    this.valueGrid[ 5 ][ 1 ] = 3 ;
    this.valueGrid[ 5 ][ 5 ] = 3 ;
    this.valueGrid[ 5 ][ 9 ] = 3 ;
    this.valueGrid[ 1 ][ 9 ] = 3 ;
    this.valueGrid[ 13 ][ 5 ] = 3 ;
    this.valueGrid[ 13 ][ 9 ] = 3 ;
    this.valueGrid[ 9 ][ 5 ] = 3 ;
    this.valueGrid[ 9 ][ 9 ] = 3 ;
    this.valueGrid[ 5 ][ 13 ] = 3 ;
    this.valueGrid[ 9 ][ 13 ] = 3 ;
    this.valueGrid[ 9 ][ 1 ] = 3 ;

    this.valueGrid[ 0 ][ 3 ] = 2 ;
    this.valueGrid[ 3 ][ 0 ] = 2 ;
    this.valueGrid[ 14 ][ 3 ] = 2 ;
    this.valueGrid[ 3 ][ 14 ] = 2 ;
    this.valueGrid[ 0 ][ 11 ] = 2 ;
    this.valueGrid[ 11 ][ 0 ] = 2 ;
    this.valueGrid[ 11 ][ 14 ] = 2 ;
    this.valueGrid[ 14 ][ 11 ] = 2 ;
    this.valueGrid[ 2 ][ 6 ] = 2 ;
    this.valueGrid[ 2 ][ 8 ] = 2 ;
    this.valueGrid[ 3 ][ 7 ] = 2 ;
    this.valueGrid[ 6 ][ 2 ] = 2 ;
    this.valueGrid[ 7 ][ 3 ] = 2 ;
    this.valueGrid[ 8 ][ 2 ] = 2 ;
    this.valueGrid[ 6 ][ 12 ] = 2 ;
    this.valueGrid[ 7 ][ 11 ] = 2 ;
    this.valueGrid[ 8 ][ 12 ] = 2 ;
    this.valueGrid[ 12 ][ 6 ] = 2 ;
    this.valueGrid[ 12 ][ 8 ] = 2 ;
    this.valueGrid[ 11 ][ 7 ] = 2 ;
    this.valueGrid[ 6 ][ 6 ] = 2 ;
    this.valueGrid[ 6 ][ 8 ] = 2 ;
    this.valueGrid[ 8 ][ 6 ] = 2 ;
    this.valueGrid[ 8 ][ 8 ] = 2 ;
    this.valueGrid[ 6 ][ 6 ] = 2 ;
    this.valueGrid[ 6 ][ 6 ] = 2 ;

    this.typeGrid[ 1 ][ 1 ] = 2 ;
    this.typeGrid[ 2 ][ 2 ] = 2 ;
    this.typeGrid[ 3 ][ 3 ] = 2 ;
    this.typeGrid[ 4 ][ 4 ] = 2 ;
    this.typeGrid[ 10 ][ 10 ] = 2 ;
    this.typeGrid[ 11 ][ 11 ] = 2 ;
    this.typeGrid[ 12 ][ 12 ] = 2 ;
    this.typeGrid[ 13 ][ 13 ] = 2 ;
    this.typeGrid[ 10 ][ 4 ] = 2 ;
    this.typeGrid[ 11 ][ 3 ] = 2 ;
    this.typeGrid[ 12 ][ 2 ] = 2 ;
    this.typeGrid[ 13 ][ 1 ] = 2 ;
    this.typeGrid[ 4 ][ 10 ] = 2 ;
    this.typeGrid[ 3 ][ 11 ] = 2 ;
    this.typeGrid[ 2 ][ 12 ] = 2 ;
    this.typeGrid[ 1 ][ 13 ] = 2 ;

    this.typeGrid[ 7 ][ 7 ] = 4 ;

    this.isReady = true ;

    this.bag = new Bag ( ) ;
    Bag.fillDefault ( this.bag ) ;

  } ;

  /**
   * Returns the word multiplication bonus for the given square.
   * 
   * @param i
   * @param j
   * @return
   */
  public int getWordBonusForSquare ( int i, int j ) {
    final byte type = this.typeGrid[ i ][ j ] ;
    switch ( type ) {
      case 1:
      case 2:
      case 3:
        return type ;
      default:
        return 1 ;
    }
  }

  /**
   * @param x
   * @param y
   * @param isUpDown
   * @param word
   * @return
   * @throws WordPlacementException
   * @deprecated
   */
  public int placeWord ( int x, int y, boolean isUpDown, String word )
      throws WordPlacementException {
    final Vector<Move> moves = new Vector<Move> ( ) ;
    Word w = new StringWord ( word ) ;
    Core.main.getMovesFromWordPositional ( w, x, y, moves, isUpDown, x, y ) ;
    if ( moves.isEmpty ( ) && Core.main.dictionary.contains ( w ) ) {
      throw new WordPlacementException ( ) ; //  !contains?
    }
    int score = 0 ;
    try {
      int i = 0 ;
      for ( final char c : word.toCharArray ( ) ) {

        final int charValue = ScrabbleBoard.lookup ( c ) ;
        if ( isUpDown ) {
          if ( this.charGrid[ x ][ y + i ] != this.blank ) {
            if ( this.charGrid[ x ][ y + i ] != c
                && this.charGrid[ x ][ y + i ] != '*' ) { throw new WordPlacementException ( ) ; }
          }
          score += this.valueGrid[ x ][ y + i ] * charValue ;
        } else {
          if ( this.charGrid[ x + i ][ y ] != this.blank ) {
            if ( this.charGrid[ x + i ][ y ] != c
                && this.charGrid[ x ][ y + i ] != '*' ) { throw new WordPlacementException ( ) ; }
          }
          score += this.valueGrid[ x + i ][ y ] * charValue ;
        }
        i++ ;
      }
    } catch ( final ArrayIndexOutOfBoundsException e ) {
      throw new WordPlacementException ( ) ;
    }
    int i = 0 ;
    boolean isAst = false ;
    for ( final char c : word.toCharArray ( ) ) {
      if ( this.charGrid[ x + ( !isUpDown ? i : 0 ) ][ y + ( isUpDown ? i : 0 ) ] == this.blank ) {
        if ( !Core.main.hand.remove ( new Character ( c ) ) ) {
          if ( !Core.main.hand.remove ( new Character ( '*' ) ) ) {
            throw new WordPlacementException ( ) ;
          } else {
            isAst = true ;
          }
        }
        if ( isUpDown ) {
          this.charGrid[ x ][ y + i ] = isAst ? '*' : c ;
        } else {
          this.charGrid[ x + i ][ y ] = isAst ? '*' : c ;
        }
      }
      i++ ;
      isAst = false ;
    }
    return score ;
  }

  /**
   * Converts the scrabbleboard into a csv grid.
   * 
   * @return The cvs grid.
   */
  public String toCSV ( ) {
    StringBuilder sb = new StringBuilder ( ) ;
    for ( int x = 0 ; x < this.boardHeight ; x++ ) {
      for ( int y = 0 ; y < this.boardWidth ; y++ ) {
        sb.append ( this.charGrid[ x ][ y ] ) ;
        sb.append ( ',' ) ;
      }
      sb.append ( '\n' ) ;
    }
    return sb.toString ( ) ;
  } ;
  
  /**
   * Reads the srabbleboard in from a given csv file.
   * 
   * @param is The csv file to read from.
   * @throws IOException On a read error.
   */
  public void fromCSV( InputStream is ) throws IOException {
    Scanner s = new Scanner( is ) ;
    for( int j = 0 ; s.hasNextLine ( ) ; j++ ) {
      String[] stA = s.nextLine ( ).split ( "," ) ;
      if( stA.length != this.boardWidth ) {
        throw new IOException("Read from CVS Error" ) ;
      } else {
        for( int i = 0 ; i < this.boardWidth ; i++ ) {
          this.charGrid[ j ][ i ] =  stA[ i ].charAt ( 0 ) ; 
        }
      }
    }
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override public String toString ( ) {
    StringBuilder toRet = new StringBuilder( ) ;
    for ( final char[] ca : this.charGrid ) {
      for ( final char c : ca ) {
        toRet.append ( c ).append ( " " ) ;
      }
      toRet.append ( "\r\n" ) ;
    }
    return toRet.toString ( ) ;
  }

  /**
   * @return The point value for the piece.
   */
  public static int lookup ( char c ) {
    switch ( c ) {
      case 'a':
      case 'r':
      case 'n':
      case 'i':
      case 's':
      case 't':
      case 'u':
      case 'o':
      case 'e':
      case 'l':
        return 1 ;
      case 'g':
      case 'd':
        return 2 ;
      case 'p':
      case 'c':
      case 'm':
      case 'b':
      case 'k':
        return 3 ;
      case 'v':
      case 'f':
      case 'h':
      case 'w':
      case 'y':
        return 4 ;
      case 'j':
      case 'x':
        return 8 ;
      case 'q':
      case 'z':
        return 10 ;
      case '*':
        return 0 ;
      default:
        throw new RuntimeException ( ) ;
    }
  }

}
