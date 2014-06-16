package net.mokon.scrabble ;

import java.awt.AWTException ;
import java.awt.event.KeyEvent ;
import java.io.Serializable ;

/**
 * <p>
 * As part of the Mokon.Net Utility Package this class provides an extension of
 * the java.awt.Robot class to add some methods for typing. This class is not
 * synchronized and not {@link Serializable}.
 * </p>
 * 
 * @author David 'Mokon' Bond
 * @version 0.1 6/7/2007
 * @since 0.0 5/20/2007
 */
public class Robot extends java.awt.Robot {

  /**
   * <p>
   * A Basic Constructor.
   * </p>
   * 
   * @throws AWTException
   *             If the super flows an exception.
   */
  public Robot ( ) throws AWTException {
    super ( ) ;
  }

  /**
   * <p>
   * Types the given string and then hits enter.
   * </p>
   * 
   * @param s
   *            The String to type.
   */
  public void eType ( String s ) {
    this.type ( s ) ;
    this.keyPress ( KeyEvent.VK_ENTER ) ;
  }

  /**
   * <p>
   * Types the given string.
   * </p>
   * 
   * @param s
   *            The String to type.
   */
  public void type ( String s ) {
    s = s.toLowerCase ( ) ;
    for ( final char c : s.toCharArray ( ) ) {
      switch ( c ) {
        case 'a':
          this.keyPress ( KeyEvent.VK_A ) ;
          break ;
        case 'b':
          this.keyPress ( KeyEvent.VK_B ) ;
          break ;
        case 'c':
          this.keyPress ( KeyEvent.VK_C ) ;
          break ;
        case 'd':
          this.keyPress ( KeyEvent.VK_D ) ;
          break ;
        case 'e':
          this.keyPress ( KeyEvent.VK_E ) ;
          break ;
        case 'f':
          this.keyPress ( KeyEvent.VK_F ) ;
          break ;
        case 'g':
          this.keyPress ( KeyEvent.VK_G ) ;
          break ;
        case 'h':
          this.keyPress ( KeyEvent.VK_H ) ;
          break ;
        case 'i':
          this.keyPress ( KeyEvent.VK_I ) ;
          break ;
        case 'j':
          this.keyPress ( KeyEvent.VK_J ) ;
          break ;
        case 'k':
          this.keyPress ( KeyEvent.VK_K ) ;
          break ;
        case 'l':
          this.keyPress ( KeyEvent.VK_L ) ;
          break ;
        case 'm':
          this.keyPress ( KeyEvent.VK_M ) ;
          break ;
        case 'n':
          this.keyPress ( KeyEvent.VK_N ) ;
          break ;
        case 'o':
          this.keyPress ( KeyEvent.VK_O ) ;
          break ;
        case 'p':
          this.keyPress ( KeyEvent.VK_P ) ;
          break ;
        case 'q':
          this.keyPress ( KeyEvent.VK_Q ) ;
          break ;
        case 'r':
          this.keyPress ( KeyEvent.VK_R ) ;
          break ;
        case 's':
          this.keyPress ( KeyEvent.VK_S ) ;
          break ;
        case 't':
          this.keyPress ( KeyEvent.VK_T ) ;
          break ;
        case 'u':
          this.keyPress ( KeyEvent.VK_U ) ;
          break ;
        case 'v':
          this.keyPress ( KeyEvent.VK_V ) ;
          break ;
        case 'w':
          this.keyPress ( KeyEvent.VK_W ) ;
          break ;
        case 'x':
          this.keyPress ( KeyEvent.VK_X ) ;
          break ;
        case 'y':
          this.keyPress ( KeyEvent.VK_Y ) ;
          break ;
        case 'z':
          this.keyPress ( KeyEvent.VK_Z ) ;
          break ;
        case '1':
          this.keyPress ( KeyEvent.VK_1 ) ;
          break ;
        case '2':
          this.keyPress ( KeyEvent.VK_2 ) ;
          break ;
        case '3':
          this.keyPress ( KeyEvent.VK_3 ) ;
          break ;
        case '4':
          this.keyPress ( KeyEvent.VK_4 ) ;
          break ;
        case '5':
          this.keyPress ( KeyEvent.VK_5 ) ;
          break ;
        case '6':
          this.keyPress ( KeyEvent.VK_6 ) ;
          break ;
        case '7':
          this.keyPress ( KeyEvent.VK_7 ) ;
          break ;
        case '8':
          this.keyPress ( KeyEvent.VK_8 ) ;
          break ;
        case '9':
          this.keyPress ( KeyEvent.VK_9 ) ;
          break ;
        case '0':
          this.keyPress ( KeyEvent.VK_0 ) ;
          break ;
        case '.':
          this.keyPress ( KeyEvent.VK_PERIOD ) ;
          break ;
        case ' ':
          this.keyPress ( KeyEvent.VK_SPACE ) ;
          break ;
        default:
          break ;
      }
      this.delay ( 2 ) ;
    }
  }

}
