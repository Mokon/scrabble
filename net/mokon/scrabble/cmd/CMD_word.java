package net.mokon.scrabble.cmd ;

import java.util.Collections ;
import java.util.Vector ;
import javax.swing.JFrame ;
import javax.swing.JTextPane ;
import net.mokon.scrabble.Core ;
import net.mokon.scrabble.GUI ;
import net.mokon.scrabble.Move ;
import net.mokon.scrabble.Player ;
import net.mokon.scrabble.ScrabbleBoard ;
import net.mokon.scrabble.core.Bag ;

/**
 * A command that finds the best word to draw.
 * 
 * @author David 'Mokon' Bond
 * @since 1.0, Dec 29, 2007
 */
public class CMD_word extends Command {

  /**
   * A frame to display a waiting text piece.
   */
  private JFrame frame ;

  /**
   * Constructor
   * 
   * @param args
   * @param console
   * @param scrabbleBoard
   * @param main
   * @param moves
   * @param bag
   */
  public CMD_word ( String[] args, JTextPane console,
      ScrabbleBoard scrabbleBoard, Player main, Vector<Move> moves, Bag bag ) {
    super ( args, console, scrabbleBoard, main, moves, bag ) ;
  }

  /* (non-Javadoc)
   * @see net.mokon.scrabble.cmd.Command#run()
   */
  @Override public void run ( ) {
    if ( Core.first ) {
      Core.moves = this.main.computeFirstMove ( ) ;

    } else {
      Core.moves = this.main.commuteNextMove ( ) ;
    }
    this.moves = Core.moves ;
    if ( this.moves.isEmpty ( ) ) {
      if ( this.main.hand.isEmpty ( ) ) {
        GUI.write ( "Use the command, 'draw <character sequence>', "
            + "to add words to your hand." ) ;
      }
      GUI.write ( "No Words Found" ) ;
    } else {
      Collections.sort ( this.moves ) ;
      GUI.gui.setWordList ( this.moves ) ;

      Vector<Move> mcpy = new Vector<Move> ( 10 ) ;

      int i = 0 ;
      for ( ; i < 10 && this.moves.size ( ) > i ; i++ ) {
        mcpy.add ( this.moves.get ( i ) ) ;
      }

      Collections.reverse ( mcpy ) ;
      for ( final Move m : mcpy ) {
        GUI.write ( String.format ( "%3d", i ) + ": " + m.toFullString ( ) ) ;
        i-- ;
      }
      GUI.write ( "The scores and positions have been given. "
          + "Type 'place #' to place the word on the board." ) ;
      GUI.write ( "The following are the words that have been found and, "
          + "which can be places on this board." ) ;
    }
    this.console.getToolkit ( ).beep ( ) ;
    this.frame.setVisible ( false ) ;
    this.frame.setEnabled ( false ) ;
  }

  /**
   * Sets the frame.
   * 
   * @param frame
   */
  public void setFrame ( JFrame frame ) {
    this.frame = frame ;
  }
}
