package net.mokon.scrabble ;

import java.awt.Color ;
import java.awt.Dimension ;
import java.awt.Font ;
import java.awt.Graphics ;
import javax.swing.JPanel ;

/**
 * 
 * @author David 'Mokon' Bond
 * @since 1.0, Dec 31, 2007
 */
public class GridSquare extends JPanel {

  public boolean            toogle ;
  private final Color       background ;
  private final int         i ;
  private final int         j ;
  private char              squChar ;

  private Color             toogleColor      = Color.GREEN ;
  public static boolean     debug            = false ;

  /**
   * 
   */
  private static final long serialVersionUID = 5259015536514953783L ;

  public GridSquare ( char c, Color background, int i, int j,
      ScrabbleBoard board ) {
    this.squChar = c ;
    this.setFont ( new Font ( "Monospaced", Font.PLAIN, 15 ) ) ;
    this.setPreferredSize ( new Dimension ( 30, 30 ) ) ;
    this.background = background ;
    this.i = i ;
    this.j = j ;
    this.toogle = false ;
    this.setToolTipText ( "( "
        + this.j
        + ", "
        + this.i
        + " ) "
        + GUI.getDescriptor ( board.valueGrid[ i ][ j ],
            board.typeGrid[ i ][ j ] ) ) ;
  }

  public void setChar ( char c ) {
    this.squChar = c ;
  }

  @Override protected void paintComponent ( Graphics arg0 ) {
    if ( this.toogle ) {
      arg0.setColor ( this.toogleColor ) ;
    } else {
      arg0.setColor ( this.background ) ;
    }
    arg0.fillRect ( 0, 0, arg0.getClipBounds ( ).width,
        arg0.getClipBounds ( ).height ) ;
    arg0.setFont ( this.getFont ( ) ) ;

    if ( GridSquare.debug ) {
      arg0.setColor ( Color.WHITE ) ;
      arg0.drawString ( this.j + " " + this.i, 0, this.getHeight ( ) / 4 ) ;
    }
    arg0.setColor ( Color.BLACK ) ;
    arg0.drawString ( this.squChar + "", this.getWidth ( ) / 2, this
        .getHeight ( ) / 2 ) ;
  }

}
