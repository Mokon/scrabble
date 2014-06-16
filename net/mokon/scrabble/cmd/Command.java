package net.mokon.scrabble.cmd ;

import java.util.Vector ;
import javax.swing.JTextPane ;
import net.mokon.scrabble.Move ;
import net.mokon.scrabble.Player ;
import net.mokon.scrabble.ScrabbleBoard ;
import net.mokon.scrabble.core.Bag ;

/**
 * This is a class that represents a command entered on the command line.
 * Any command that is to be used must be added as an extension of this. The
 * command processor will load this command via reflection.
 * 
 * This class extends runable and will launch a seperate thread for command
 * execution.
 * 
 * @author David 'Mokon' Bond
 * @since 1.0, Dec 29, 2007
 */
public abstract class Command extends Thread {

  /**
   * The bag this command will operate on.
   */
  protected Bag           bag ;

  /**
   * The text pane this command is called from
   */
  protected JTextPane     console ;

  /**
   * TODO DOCUMENT
   */
  protected String[]      inp ;

  /**
   * The player this command will operate on.
   */
  protected Player        main ;

  /**
   * The moves this command will operate on.
   */
  protected Vector<Move>  moves ;

  /**
   * The scrabbleboard this command will operate on.
   */
  protected ScrabbleBoard scrabbleBoard ;

  /**
   * Default constructor.
   */
  public Command ( ) {} ;

  /**
   * This is a constructor to be loaded via reflection. 
   * 
   * @param args
   * @param console
   * @param scrabbleBoard
   * @param main
   * @param moves
   * @param bag
   */
  public Command ( String[] args, JTextPane console,
      ScrabbleBoard scrabbleBoard, Player main, Vector<Move> moves, Bag bag ) {
    this.inp = args ;
    this.console = console ;
    this.scrabbleBoard = scrabbleBoard ;
    this.main = main ;
    this.moves = moves ;
    this.bag = bag ;
  }

  /**
   * This method must be implemented by each command. It is what will be
   * executed by the command processor.
   */
  @Override public abstract void run ( ) ;

}
