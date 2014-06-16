package net.mokon.scrabble.cmd ;

import java.util.Vector ;
import javax.swing.JTextPane ;
import net.mokon.scrabble.GUI ;
import net.mokon.scrabble.Move ;
import net.mokon.scrabble.Player ;
import net.mokon.scrabble.ScrabbleBoard ;
import net.mokon.scrabble.core.Bag ;

/**
 * A commmand that generates a help text info.
 * 
 * @author David 'Mokon' Bond
 * @since 1.0, Dec 29, 2007
 */
public class CMD_help extends Command {

  /**
   * 
   * @param args
   * @param console
   * @param scrabbleBoard
   * @param main
   * @param moves
   * @param bag
   */
  public CMD_help ( String[] args, JTextPane console,
      ScrabbleBoard scrabbleBoard, Player main, Vector<Move> moves, Bag bag ) {
    super ( args, console, scrabbleBoard, main, moves, bag ) ;
  }

  /* (non-Javadoc)
   * @see net.mokon.scrabble.cmd.Command#run()
   */
  @Override public void run ( ) {
    GUI.write ( "help: Diplays this text." ) ;
    GUI.write ( "Undocumented: size, fill" ) ;
    GUI.write ( "\tSyntax: lookup <character>|<word>" ) ;
    GUI
        .write ( "lookup: Returns the point value of a character or whether the dictionary contains the given word." ) ;
    GUI.write ( "grid: Turns on/off a grid on the scrabble board." ) ;
    GUI.write ( "\tSyntax: unset <x> <y>" ) ;
    GUI
        .write ( "unset: Removes whatever character was at the given (x,y) coordinate." ) ;
    GUI.write ( "\tSyntax: set <x> <y> <character>" ) ;
    GUI.write ( "set: Sets the given (x,y) coordinate to the given character." ) ;
    GUI.write ( "\tSyntax: use <character sequence>" ) ;
    GUI
        .write ( "use: Removes a character or set of characters from your hand." ) ;
    GUI.write ( "\tSyntax: draw <char sequence>" ) ;
    GUI
        .write ( "draw: Places the given character sequence into the players hand." ) ;
    GUI.write ( "\t\tplace <word>" ) ;
    GUI.write ( "\t\tplace <history reference>" ) ;
    GUI.write ( "\tSyntax: place <word> <x> <y> <isVertical>" ) ;
    GUI.write ( "place: Places a word on the board." ) ;
    GUI.write ( "word: Returns a valid word given the current board and hand." ) ;
    GUI.write ( "........................................................" ) ;
    GUI.write ( "Author: David 'Mokon' Bond, 8/16/07, Mokon.Net" ) ;
    GUI.write ( "........................................................" ) ;
    GUI.write ( "Scrabble Solver Help " ) ;
  }

}
