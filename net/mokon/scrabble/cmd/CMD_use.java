package net.mokon.scrabble.cmd ;

import java.util.Vector ;
import javax.swing.JTextPane ;
import net.mokon.scrabble.Move ;
import net.mokon.scrabble.Player ;
import net.mokon.scrabble.ScrabbleBoard ;
import net.mokon.scrabble.core.Bag ;

/**
 * Removes a char from the hand.
 * 
 * @author David 'Mokon' Bond
 * @since 1.0, Dec 29, 2007
 */
public class CMD_use extends Command {

  public CMD_use ( String[] args, JTextPane console,
      ScrabbleBoard scrabbleBoard, Player main, Vector<Move> moves, Bag bag ) {
    super ( args, console, scrabbleBoard, main, moves, bag ) ;
  }

  /* (non-Javadoc)
   * @see net.mokon.scrabble.cmd.Command#run()
   */
  @Override public void run ( ) {
    for ( int i = 1 ; i < this.inp.length ; i++ ) {
      for ( final char c : this.inp[ i ].toCharArray ( ) ) {
        this.main.hand.remove ( new Character ( c ) ) ;
      }
    }
  }

}
