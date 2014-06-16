package net.mokon.scrabble.cmd ;

import java.util.Vector ;
import javax.swing.JTextPane ;
import net.mokon.scrabble.Core ;
import net.mokon.scrabble.GUI ;
import net.mokon.scrabble.Move ;
import net.mokon.scrabble.Player ;
import net.mokon.scrabble.ScrabbleBoard ;
import net.mokon.scrabble.core.Bag ;
import net.mokon.scrabble.core.BagEmptyException ;

/**
 * Fills the players hand with random letters.
 * 
 * @author David 'Mokon' Bond
 * @since 1.0, Dec 29, 2007
 */
public class CMD_fill extends Command {

  /**
   * 
   * @param args
   * @param console
   * @param scrabbleBoard
   * @param main
   * @param moves
   * @param bag
   */
  public CMD_fill ( String[] args, JTextPane console,
      ScrabbleBoard scrabbleBoard, Player main, Vector<Move> moves, Bag bag ) {
    super ( args, console, scrabbleBoard, main, moves, bag ) ;
  }

  /* (non-Javadoc)
   * @see net.mokon.scrabble.cmd.Command#run()
   */
  @Override public void run ( ) {
    try {
      while ( Core.main.hand.size ( ) < 7 ) {
        Core.main.hand.add ( this.bag.draw ( ) ) ;
      }
    } catch ( final BagEmptyException e ) {
      GUI.write ( "The bag is empty." ) ;
    }
  }

}
