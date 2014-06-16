package net.mokon.scrabble.cmd ;

import java.util.Vector ;
import javax.swing.JTextPane ;
import net.mokon.scrabble.GUI ;
import net.mokon.scrabble.Move ;
import net.mokon.scrabble.Player ;
import net.mokon.scrabble.ScrabbleBoard ;
import net.mokon.scrabble.core.Bag ;
import net.mokon.scrabble.core.BagEmptyException ;

/**
 * A command that draws the given characters from the bag.
 * 
 * @author David 'Mokon' Bond
 *
 */
public class CMD_draw extends Command {

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
  public CMD_draw ( String[] args, JTextPane console,
      ScrabbleBoard scrabbleBoard, Player main, Vector<Move> moves, Bag bag ) {
    super ( args, console, scrabbleBoard, main, moves, bag ) ;
  }

  /* (non-Javadoc)
   * @see net.mokon.scrabble.cmd.Command#run()
   */
  @Override public void run ( ) {
    final Vector<Character> chars = new Vector<Character> ( ) ;
    for ( int i = 1 ; i < this.inp.length ; i++ ) {
      for ( final char c : this.inp[ i ].toLowerCase ( ).toCharArray ( ) ) {
        chars.add ( c ) ;
      }
    }
    for ( final char c : chars ) {
      if ( c >= 'a' && c <= 'z' || c == '*' ) {
        try {
          this.main.hand.add ( this.bag.draw ( c ) ) ;
        } catch ( BagEmptyException e ) {
          GUI.write ( "The bag does not contain the character " + c + "." ) ;
        }
      }
    }
  }

}
