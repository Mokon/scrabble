package net.mokon.scrabble.cmd ;

import java.util.Vector ;
import javax.swing.JTextPane ;
import net.mokon.scrabble.Move ;
import net.mokon.scrabble.Player ;
import net.mokon.scrabble.ScrabbleBoard ;
import net.mokon.scrabble.core.Bag ;

/**
 * Sets the given square.
 * 
 * @author David 'Mokon' Bond
 * @since 1.0, Dec 29, 2007
 */
public class CMD_set extends Command {

  public CMD_set ( String[] args, JTextPane console,
      ScrabbleBoard scrabbleBoard, Player main, Vector<Move> moves, Bag bag ) {
    super ( args, console, scrabbleBoard, main, moves, bag ) ;
  }

  /* (non-Javadoc)
   * @see net.mokon.scrabble.cmd.Command#run()
   */
  @Override public void run ( ) {
    final int y = Integer.parseInt ( this.inp[ 1 ] ) ;
    final int x = Integer.parseInt ( this.inp[ 2 ] ) ;
    final char c = this.inp[ 3 ].charAt ( 0 ) ;
    this.scrabbleBoard.charGrid[ x ][ y ] = c ;
  }

}
