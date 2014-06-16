package net.mokon.scrabble.cmd ;

import java.util.Vector ;
import javax.swing.JTextPane ;
import net.mokon.scrabble.GUI ;
import net.mokon.scrabble.GridSquare ;
import net.mokon.scrabble.Move ;
import net.mokon.scrabble.Player ;
import net.mokon.scrabble.ScrabbleBoard ;
import net.mokon.scrabble.core.Bag ;

/**
 * Toggles the grid.
 * 
 * @author David 'Mokon' Bond
 * @since 1.0, Dec 29, 2007
 */
public class CMD_grid extends Command {

  /**
   * 
   * @param args
   * @param console
   * @param scrabbleBoard
   * @param main
   * @param moves
   * @param bag
   */
  public CMD_grid ( String[] args, JTextPane console,
      ScrabbleBoard scrabbleBoard, Player main, Vector<Move> moves, Bag bag ) {
    super ( args, console, scrabbleBoard, main, moves, bag ) ;
  }

  /* (non-Javadoc)
   * @see net.mokon.scrabble.cmd.Command#run()
   */
  @Override public void run ( ) {
    if ( GridSquare.debug ) {
      GUI.write ( "Turning Grid Off." ) ;
    } else {
      GUI.write ( "Turning Grid On." ) ;
    }
    GridSquare.debug = !GridSquare.debug ;
  }
}
