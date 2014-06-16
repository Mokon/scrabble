package net.mokon.scrabble.cmd ;

import java.util.Vector ;
import javax.swing.JTextPane ;
import net.mokon.scrabble.Core ;
import net.mokon.scrabble.GUI ;
import net.mokon.scrabble.Move ;
import net.mokon.scrabble.Player ;
import net.mokon.scrabble.ScrabbleBoard ;
import net.mokon.scrabble.WordPlacementException ;
import net.mokon.scrabble.core.Bag ;

/**
 * Places a word on the board.
 * 
 * @author David 'Mokon' Bond
 * @since 1.0, Dec 29, 2007
 */
public class CMD_place extends Command {

  public CMD_place ( String[] args, JTextPane console,
      ScrabbleBoard scrabbleBoard, Player main, Vector<Move> moves, Bag bag ) {
    super ( args, console, scrabbleBoard, main, moves, bag ) ;
  }

  /* (non-Javadoc)
   * @see net.mokon.scrabble.cmd.Command#run()
   */
  @Override public void run ( ) {
    if ( this.inp.length == 2 ) {
      try {
        final int i = Integer.parseInt ( this.inp[ 1 ] ) ;
        Core.handle ( this.moves.get ( i - 1 ).getCmd ( ), this.console ) ;
      } catch ( final NumberFormatException e ) {
        boolean found = false ;
        for ( int i = this.moves.size ( ) - 1 ; i >= 0 ; i-- ) {
          if ( this.moves.get ( i ).word.equals ( this.inp[ 1 ] ) ) {
            Core.handle ( this.moves.get ( i ).getCmd ( ), this.console ) ;
            found = true ;
            if ( Core.first ) {
              Core.first = false ;
            }
            break ;
          }
        }
        if ( !found ) { throw e ; }
      }
    } else {
      final String word = this.inp[ 1 ] ;
      final int y = Integer.parseInt ( this.inp[ 2 ] ) ;
      final int x = Integer.parseInt ( this.inp[ 3 ] ) ;
      final boolean isUpDown = !Boolean.parseBoolean ( this.inp[ 4 ] ) ;
      try {
        this.scrabbleBoard.placeWord ( x, y, isUpDown, word ) ;
        Vector<Move> v = new Vector<Move> ( ) ;
        v.setSize ( 1 ) ;
        GUI.gui.wordList.setListData ( v ) ;
        if ( Core.first ) {
          Core.first = false ;
        }
      } catch ( final WordPlacementException e ) {
        GUI.write ( "Word Can't Be Placed" ) ;
      }
    }
  }

}
