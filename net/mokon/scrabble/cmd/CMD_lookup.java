package net.mokon.scrabble.cmd ;

import java.util.Vector ;
import javax.swing.JTextPane ;
import net.mokon.scrabble.GUI ;
import net.mokon.scrabble.Move ;
import net.mokon.scrabble.Player ;
import net.mokon.scrabble.ScrabbleBoard ;
import net.mokon.scrabble.StringWord ;
import net.mokon.scrabble.core.Bag ;

/**
 * Looks upa  word in the dictionary.
 * 
 * @author David 'Mokon' Bond
 * @since 1.0, Dec 29, 2007
 */
public class CMD_lookup extends Command {

  public CMD_lookup ( String[] args, JTextPane console,
      ScrabbleBoard scrabbleBoard, Player main, Vector<Move> moves, Bag bag ) {
    super ( args, console, scrabbleBoard, main, moves, bag ) ;
  }

  /* (non-Javadoc)
   * @see net.mokon.scrabble.cmd.Command#run()
   */
  @Override public void run ( ) {
    if ( this.inp[ 1 ].length ( ) == 1 ) {
      final char c = this.inp[ 1 ].charAt ( 0 ) ;
      GUI.write ( "The value of the character '" + c + "' is "
          + ScrabbleBoard.lookup ( c ) + "." ) ;
    } else {
      final boolean hasWord = this.main.dictionary.contains ( new StringWord (
          this.inp[ 1 ] ) ) ;
      GUI.write ( "The dictionary"
          + ( hasWord ? " contains " : " does not contain " ) + "the word '"
          + this.inp[ 1 ] + "'." ) ;
    }
  }

}
