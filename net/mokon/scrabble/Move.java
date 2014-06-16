package net.mokon.scrabble ;

import java.util.Vector ;

/**
 * 
 */

public class Move implements Comparable<Move> {

  public ScrabbleBoard board ;
  public boolean       isUpDown ;
  public String        word ;
  public int           x ;
  public int           y ;

  public Move ( int x, int y, boolean isUpDown, String word, ScrabbleBoard board ) {
    this.x = x ;
    this.y = y ;
    this.isUpDown = isUpDown ;
    this.word = word ;
    this.board = board ;
  }

  @Override public int compareTo ( Move o ) {
    return o.getScore ( o.board ) - this.getScore ( this.board ) ;
  }

  @Override public boolean equals ( Object obj ) {
    if ( this == obj ) { return true ; }
    if ( obj == null ) { return false ; }
    if ( this.getClass ( ) != obj.getClass ( ) ) { return false ; }
    final Move other = (Move) obj ;
    if ( this.isUpDown != other.isUpDown ) { return false ; }
    if ( this.word == null ) {
      if ( other.word != null ) { return false ; }
    } else if ( !this.word.equals ( other.word ) ) { return false ; }
    if ( this.x != other.x ) { return false ; }
    if ( this.y != other.y ) { return false ; }
    return true ;
  }

  public String getCmd ( ) {
    return "place " + this.word + " " + this.y + " " + this.x + " "
        + !this.isUpDown ;
  }

  public int getFalseScore ( ScrabbleBoard board ) {
    int score = 0 ;
    int wordBonus = 1 ;
    int xOff = 0 ;
    int yOff = 0 ;
    int offLineX = this.x - ( this.isUpDown ? 0 : 1 ) ;
    int offLineY = this.y - ( this.isUpDown ? 1 : 0 ) ;
    while ( offLineY >= 0 && offLineX >= 0 && offLineY < board.boardHeight
        && offLineX < board.boardWidth
        && board.charGrid[ offLineX ][ offLineY ] != board.blank ) {
      score += ScrabbleBoard.lookup ( board.charGrid[ offLineX ][ offLineY ] ) ;
      offLineX -= this.isUpDown ? 0 : 1 ;
      offLineY -= this.isUpDown ? 1 : 0 ;
    }
    for ( int i = 0 ; i < this.word.length ( ) ; i++ ) {
      xOff = this.x + ( this.isUpDown ? 0 : i ) ;
      yOff = this.y + ( this.isUpDown ? i : 0 ) ;
      score += ScrabbleBoard.lookup ( this.word.charAt ( i ) )
          * ( board.charGrid[ xOff ][ yOff ] == board.blank ? board.valueGrid[ xOff ][ yOff ]
              : 1 ) ;
      wordBonus *= board.charGrid[ xOff ][ yOff ] == board.blank ? board
          .getWordBonusForSquare ( xOff, yOff ) : 1 ;
      if ( board.charGrid[ xOff ][ yOff ] == board.blank ) {
        offLineX = xOff + ( this.isUpDown ? 1 : 0 ) ;
        offLineY = yOff + ( this.isUpDown ? 0 : 1 ) ;
        while ( offLineY >= 0 && offLineX >= 0 && offLineY < board.boardHeight
            && offLineX < board.boardWidth
            && board.charGrid[ offLineX ][ offLineY ] != board.blank ) {
          score += ScrabbleBoard
              .lookup ( board.charGrid[ offLineX ][ offLineY ] ) ;
          offLineX += this.isUpDown ? 1 : 0 ;
          offLineY += this.isUpDown ? 0 : 1 ;
        }
        offLineX = xOff - ( this.isUpDown ? 1 : 0 ) ;
        offLineY = yOff - ( this.isUpDown ? 0 : 1 ) ;
        while ( offLineY >= 0 && offLineX >= 0 && offLineY < board.boardHeight
            && offLineX < board.boardWidth
            && board.charGrid[ offLineX ][ offLineY ] != board.blank ) {
          score += ScrabbleBoard
              .lookup ( board.charGrid[ offLineX ][ offLineY ] ) ;
          offLineX -= this.isUpDown ? 1 : 0 ;
          offLineY -= this.isUpDown ? 0 : 1 ;
        }
      }
    }
    offLineX = xOff + ( this.isUpDown ? 0 : 1 ) ;
    offLineY = yOff + ( this.isUpDown ? 1 : 0 ) ;
    while ( offLineY >= 0 && offLineX >= 0 && offLineY < board.boardHeight
        && offLineX < board.boardWidth
        && board.charGrid[ offLineX ][ offLineY ] != board.blank ) {
      score += ScrabbleBoard.lookup ( board.charGrid[ offLineX ][ offLineY ] ) ;
      offLineX += this.isUpDown ? 0 : 1 ;
      offLineY += this.isUpDown ? 1 : 0 ;
    }
    return score * wordBonus ;
  }

  public int getScore ( ScrabbleBoard board ) {
    if ( this.word.equals ( "yogee" ) ) {
      System.out.print ( "" ) ;
    }
    final Vector<Integer> wordScores = new Vector<Integer> ( ) ;

    int score = 0 ;
    int wordBonus = 1 ;
    int xOff = 0 ;
    int yOff = 0 ;
    int numLetsUsed = 0 ;

    /* Check Up Word */
    int offLineX = this.x - ( this.isUpDown ? 0 : 1 ) ;
    int offLineY = this.y - ( this.isUpDown ? 1 : 0 ) ;
    while ( offLineY >= 0 && offLineX >= 0 && offLineY < board.boardHeight
        && offLineX < board.boardWidth
        && board.charGrid[ offLineX ][ offLineY ] != board.blank ) {
      score += ScrabbleBoard.lookup ( board.charGrid[ offLineX ][ offLineY ] ) ;
      offLineX -= this.isUpDown ? 0 : 1 ;
      offLineY -= this.isUpDown ? 1 : 0 ;
    }

    /* Add up Score for Word */
    for ( int i = 0 ; i < this.word.length ( ) ; i++ ) {
      xOff = this.x + ( this.isUpDown ? 0 : i ) ;
      yOff = this.y + ( this.isUpDown ? i : 0 ) ;
      score += ScrabbleBoard.lookup ( this.word.charAt ( i ) )
          * ( board.charGrid[ xOff ][ yOff ] == board.blank ? board.valueGrid[ xOff ][ yOff ]
              : 1 ) ;
      wordBonus *= board.charGrid[ xOff ][ yOff ] == board.blank ? board
          .getWordBonusForSquare ( xOff, yOff ) : 1 ;

      /* Now Check if Other New Words were formed. */
      if ( board.charGrid[ xOff ][ yOff ] == board.blank ) {
        numLetsUsed++ ;
        int nScore = ScrabbleBoard.lookup ( this.word.charAt ( i ) )
            * board.valueGrid[ xOff ][ yOff ] ;
        boolean hasWord = false ;

        /* Right of Char Pos. */
        offLineX = xOff + ( this.isUpDown ? 1 : 0 ) ;
        offLineY = yOff + ( this.isUpDown ? 0 : 1 ) ;
        while ( offLineY >= 0 && offLineX >= 0 && offLineY < board.boardHeight
            && offLineX < board.boardWidth
            && board.charGrid[ offLineX ][ offLineY ] != board.blank ) {
          hasWord = true ;
          nScore += ScrabbleBoard
              .lookup ( board.charGrid[ offLineX ][ offLineY ] ) ;
          offLineX += this.isUpDown ? 1 : 0 ;
          offLineY += this.isUpDown ? 0 : 1 ;
        }

        /* Left of Char Pos. */
        offLineX = xOff - ( this.isUpDown ? 1 : 0 ) ;
        offLineY = yOff - ( this.isUpDown ? 0 : 1 ) ;
        while ( offLineY >= 0 && offLineX >= 0 && offLineY < board.boardHeight
            && offLineX < board.boardWidth
            && board.charGrid[ offLineX ][ offLineY ] != board.blank ) {
          hasWord = true ;
          nScore += ScrabbleBoard
              .lookup ( board.charGrid[ offLineX ][ offLineY ] ) ;
          offLineX -= this.isUpDown ? 1 : 0 ;
          offLineY -= this.isUpDown ? 0 : 1 ;
        }

        /* Now add the word if a new one was formed. */
        if ( hasWord ) {
          wordScores.add ( nScore * board.getWordBonusForSquare ( xOff, yOff ) ) ;
        }
      }
    }

    /* Now Check Down Word */
    offLineX = xOff + ( this.isUpDown ? 0 : 1 ) ;
    offLineY = yOff + ( this.isUpDown ? 1 : 0 ) ;
    while ( offLineY >= 0 && offLineX >= 0 && offLineY < board.boardHeight
        && offLineX < board.boardWidth
        && board.charGrid[ offLineX ][ offLineY ] != board.blank ) {
      score += ScrabbleBoard.lookup ( board.charGrid[ offLineX ][ offLineY ] ) ;
      offLineX += this.isUpDown ? 0 : 1 ;
      offLineY += this.isUpDown ? 1 : 0 ;
    }

    /* Finalize the Score and Return it. */
    score *= wordBonus ;
    for ( final int i : wordScores ) {
      score += i ;
    }
    if ( numLetsUsed == 7 ) {
      score += 50 ;
    }
    return score ;
  }

  @Override public int hashCode ( ) {
    final int prime = 31 ;
    int result = 1 ;
    result = prime * result + ( this.isUpDown ? 1231 : 1237 ) ;
    result = prime * result + ( this.word == null ? 0 : this.word.hashCode ( ) ) ;
    result = prime * result + this.x ;
    result = prime * result + this.y ;
    return result ;
  }

  public String toFullString ( ) {
    return String.format ( "Word:%-15.15sWorth:%2d%10.10s%-20.20s", this.word,
        this.getScore ( this.board ), "", "(" + this.y + ", " + this.x + ") "
            + ( !this.isUpDown ? "Vertical" : "" ) ) ;
  }

  @Override public String toString ( ) {
    return String.format ( "  %5d  %-25.25s", this.getScore ( this.board ),
        this.word ) ;
  }

}
