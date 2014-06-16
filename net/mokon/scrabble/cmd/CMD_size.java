package net.mokon.scrabble.cmd ;

import java.util.Vector ;
import javax.swing.JTextPane ;
import net.mokon.scrabble.GUI ;
import net.mokon.scrabble.Move ;
import net.mokon.scrabble.Player ;
import net.mokon.scrabble.ScrabbleBoard ;
import net.mokon.scrabble.core.Bag ;

/**
 * Prints the size of the dictionary in use.
 * 
 * @author David 'Mokon' Bond
 * @since 1.0, Dec 29, 2007
 */
public class CMD_size extends Command {

  public CMD_size ( String[] args, JTextPane console,
      ScrabbleBoard scrabbleBoard, Player main, Vector<Move> moves, Bag bag ) {
    super ( args, console, scrabbleBoard, main, moves, bag ) ;
  }

  /* (non-Javadoc)
   * @see net.mokon.scrabble.cmd.Command#run()
   */
  @Override public void run ( ) {
    GUI.write ( "The size of the dictionary in use is "
        + this.main.dictionary.size ( ) + " words." ) ;
  }

}
