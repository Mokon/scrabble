package net.mokon.scrabble.core ;

import java.io.Serializable ;
import java.util.ArrayList ;
import java.util.List ;
import java.util.Random ;

/**
 * This is a class that represents the letter bag in scrabble. It provides a way
 * to draw pieces from a bag.
 * 
 * @author David 'Mokon' Bond
 */
public class Bag implements Serializable {

  /**
   * A random number generator to randomly choose a letter from the bag.
   */
  private final Random          generator ;

  /**
   * The letters that are currently in the bag.
   */
  private final List<Character> letters ;

  /**
   * Version Interop.
   */
  private static final long     serialVersionUID = 7902125044727612586L ;

  /**
   * Constructs an empty bad.
   */
  public Bag ( ) {
    this.letters = new ArrayList<Character> ( ) ;
    this.generator = new Random ( System.currentTimeMillis ( ) ) ;
  }

  /**
   * Adds the given char to the bag.
   * 
   * @param c
   *            The char to add.
   */
  public void add ( char c ) {
    this.letters.add ( new Character ( c ) ) ;
  }

  /**
   * Adds the given char the given number of times to bag.
   * 
   * @param c
   *            The char to add.
   * @param number
   *            The number of times to add it.
   */
  public void add ( char c, int number ) {
    while ( number-- > 0 ) {
      this.letters.add ( new Character ( c ) ) ;
    }
  }

  /**
   * Removes and returns a random char from the bag.
   * 
   * @return A random char from the bag.
   * @throws BagEmptyException
   *             If the bag is empty.
   */
  public char draw ( ) throws BagEmptyException {
    if ( this.letters.isEmpty ( ) ) { throw new BagEmptyException ( ) ; }
    return this.letters.get ( this.generator.nextInt ( this.letters.size ( ) ) ) ;
  }

  /**
   * Draws the given char from the bag.
   * 
   * @param toDraw The char to draw.
   * @return The char
   * @throws BagEmptyException If the bag does not have the given char.
   */
  public char draw ( char toDraw ) throws BagEmptyException {
    int index = this.letters.indexOf ( new Character ( toDraw ) ) ;
    if ( index == -1 ) {
      throw new BagEmptyException ( ) ;
    } else {
      return this.letters.get ( index ) ;
    }
  }

  /**
   * Fills the given bag with the default characters as specified by scrabble
   * rules.
   * 
   * @param bag
   *            The bag to fill.
   */
  public static void fillDefault ( Bag bag ) {
    bag.add ( 'a', 9 ) ;
    bag.add ( 'b', 2 ) ;
    bag.add ( 'c', 2 ) ;
    bag.add ( 'd', 4 ) ;
    bag.add ( 'e', 12 ) ;
    bag.add ( 'f', 2 ) ;
    bag.add ( 'g', 3 ) ;
    bag.add ( 'h', 2 ) ;
    bag.add ( 'i', 9 ) ;
    bag.add ( 'j', 1 ) ;
    bag.add ( 'k', 1 ) ;
    bag.add ( 'l', 4 ) ;
    bag.add ( 'm', 2 ) ;
    bag.add ( 'n', 6 ) ;
    bag.add ( 'o', 8 ) ;
    bag.add ( 'p', 2 ) ;
    bag.add ( 'q', 1 ) ;
    bag.add ( 'r', 6 ) ;
    bag.add ( 's', 4 ) ;
    bag.add ( 't', 6 ) ;
    bag.add ( 'u', 4 ) ;
    bag.add ( 'v', 2 ) ;
    bag.add ( 'w', 2 ) ;
    bag.add ( 'x', 1 ) ;
    bag.add ( 'y', 2 ) ;
    bag.add ( 'z', 1 ) ;
    bag.add ( '*', 2 ) ;
  }

}
