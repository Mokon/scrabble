package net.mokon.scrabble ;

import java.awt.Color ;
import java.awt.Font ;
import java.io.File ;
import java.io.FileNotFoundException ;
import java.io.InputStream ;
import java.util.HashSet ;
import java.util.Scanner ;
import java.util.Set ;
import javax.swing.JFrame ;
import javax.swing.JLabel ;

/**
 * This is a dictionary class for reading in words.
 * 
 * @author David 'Mokon' Bond
 * @since 1.0, Dec 29, 2007
 */
public class Dictionary {

  private final Set<Word>     words ;

  private static final String SOWDPODSDictionaryFileName = "sowpods.txt" ;

  //private static final String TWLDictionaryFileName      = "TWL06.txt" ;

  /**
   * Constructor which creates the default dictionary.
   */
  public Dictionary ( ) {
    final JFrame loadingScreen = new JFrame ( ) ;
    final JLabel caption = new JLabel ( "Loading Dictionary..." ) ;
    loadingScreen.setBackground ( Color.BLACK ) ;
    caption.setBackground ( Color.BLACK ) ;
    caption.setForeground ( Color.GREEN ) ;
    caption.setFont ( new Font ( "Monospaced", Font.PLAIN, 30 ) ) ;
    loadingScreen.setContentPane ( caption ) ;
    loadingScreen.pack ( ) ;
    loadingScreen.setVisible ( true ) ;

    this.words = new HashSet<Word> ( ) ;

    try {
      //this.add ( Dictionary.TWLDictionaryFileName ) ;
      this.add ( Dictionary.SOWDPODSDictionaryFileName ) ;
    } catch ( final FileNotFoundException e ) {
      e.printStackTrace ( ) ;
    }
    loadingScreen.setVisible ( false ) ;
    loadingScreen.setEnabled ( false ) ;
  }

  /**
   * A method to add a dictionary to the program.
   * 
   * @param dicName
   * @throws FileNotFoundException
   */
  public void add ( String dicName ) throws FileNotFoundException {
    Scanner dictionaryScanner ;
    try {
      InputStream is = this.getClass ( ).getResourceAsStream (
          "dictionaries/" + dicName ) ;
      dictionaryScanner = new Scanner ( is ) ;
    } catch ( final RuntimeException e ) {
      e.printStackTrace ( ) ;
      dictionaryScanner = new Scanner ( new File ( dicName ) ) ;
    }
    while ( dictionaryScanner.hasNextLine ( ) ) {
      String word = dictionaryScanner.nextLine ( ) ;
      if ( word.contains ( "/" ) ) {
        word = word.substring ( 0, word.indexOf ( "/" ) ) ;
      }
      this.words.add ( new StringWord ( word.toLowerCase ( ) ) ) ;
    }
  }

  public boolean contains ( Word word ) {
    return this.words.contains ( word ) ;
  }

  public Set<Word> dictionary ( ) {
    return this.words ;
  }

  public boolean isValid ( String toVerify ) {
    for ( final char c : toVerify.toLowerCase ( ).toCharArray ( ) ) {
      if ( c < 'a' || c > 'z' ) { return false ; }
    }
    return true ;
  }

  public int size ( ) {
    return this.dictionary ( ).size ( ) ;
  }
}
