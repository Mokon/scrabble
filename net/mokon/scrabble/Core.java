package net.mokon.scrabble ;

import java.awt.Color ;
import java.awt.Font ;
import java.io.File ;
import java.io.PrintStream ;
import java.lang.reflect.Constructor ;
import java.lang.reflect.Method ;
import java.util.Scanner ;
import java.util.Vector ;
import javax.swing.JFrame ;
import javax.swing.JLabel ;
import javax.swing.JTextPane ;
import net.mokon.scrabble.cmd.Command ;

/**
 * This is the central component to the srabble solver. It launches the system.
 * 
 * @author David 'Mokon' Bond
 * @since 1.0, Dec 29, 2007
 */
public class Core {

  public static Boolean      first = true ;
  public static Player       main ;
  public static Vector<Move> moves ;
  static PrintStream         fileLogger ;
  static Scanner             inputScanner ;
  static ScrabbleBoard       scrabbleBoard ;

  /**
   * This method handles a given input cmd and does as should be done.
   * 
   * @param input the input
   * @param console the console to render on
   */
  public static void handle ( String input, JTextPane console ) {
    GUI.commandHistory.add ( input ) ;
    GUI.commandHistoryLocation = GUI.commandHistory.size ( ) ;

    GUI.write ( "Processing... [ "
        + console.getText ( ).replaceAll ( "\r", "" ).replaceAll ( "\n", "" )
        + " ]" ) ;
    console.setText ( "" ) ;

    input = input.replaceAll ( "\r", "" ).replaceAll ( "\n", "" ) ;
    try {
      if ( input.equalsIgnoreCase ( "quit" )
          || input.equalsIgnoreCase ( "exit" )
          || input.equalsIgnoreCase ( "close" ) ) {
        Core.fileLogger.flush ( ) ;
        System.exit ( 0 ) ;
      } else {
        final String inp[] = input.split ( "(\\s)+" ) ;
        if ( inp.length == 0 ) { return ; }
        final Class<?> cmdClass = Class.forName ( "net.mokon.scrabble.cmd.CMD_"
            + inp[ 0 ].toLowerCase ( ) ) ;
        final Constructor<?> cmdConstructor = cmdClass
            .getDeclaredConstructors ( )[ 0 ] ;
        final Command toRun = (Command) cmdConstructor.newInstance ( inp,
            console, Core.scrabbleBoard, Core.main, Core.moves,
            Core.scrabbleBoard.bag ) ;
        if ( inp[ 0 ].equalsIgnoreCase ( "word" ) ) {
          final Method method = cmdClass.getMethod ( "setFrame", JFrame.class ) ;
          method.invoke ( toRun, Core.showWorkingScreen ( ) ) ;
        }
        toRun.start ( ) ;
      }
    } catch ( final ClassNotFoundException e ) {
      GUI.write ( "Unknown Command - Valid are [ help, word, use, draw,"
          + " place, set, unset, grid, size, fill, lookup ]" ) ;
    } catch ( final Throwable e ) {
      GUI.write ( "An error has been enountered [ " + e.toString ( )
          + " ]. Attempting to continue..." ) ;
      e.printStackTrace ( ) ;
      e.printStackTrace ( Core.fileLogger ) ;
    }
  }

  /**
   * The main method to start the program.
   * 
   * @param args cmd line args. These are not used.
   */
  public static void main ( String[] args ) {

    try {
      Core.scrabbleBoard = new ScrabbleBoard ( ) ;
      Core.main = new Player ( Core.scrabbleBoard ) ;
      new GUI ( Core.scrabbleBoard, Core.main ) ;
      final File log = new File ( "error.log" ) ;
      log.createNewFile ( ) ;
      // log.deleteOnExit ( ) ;
      Core.fileLogger = new PrintStream ( log ) ;
    } catch ( final Throwable e ) {
      e.printStackTrace ( ) ;
    }
  }

  /**
   * A simple method to show a working screen for when a command is being
   * processed.
   * 
   * @return the working screen.
   */
  public static JFrame showWorkingScreen ( ) {
    final JFrame workingScreen = new JFrame ( ) ;
    final JLabel caption = new JLabel ( "Working. This may take a moment..." ) ;
    workingScreen.setBackground ( Color.BLACK ) ;
    caption.setBackground ( Color.BLACK ) ;
    caption.setForeground ( Color.GREEN ) ;
    caption.setFont ( new Font ( "Monospaced", Font.PLAIN, 30 ) ) ;
    workingScreen.setContentPane ( caption ) ;
    workingScreen.pack ( ) ;
    workingScreen.setVisible ( true ) ;
    return workingScreen ;
  }

}
