package net.mokon.scrabble ;

/**
 * 
 */

public class GUIThread extends Thread {

  private final GUI toDisplay ;

  public GUIThread ( GUI toDisplay ) {
    this.toDisplay = toDisplay ;
  }

  @Override public void run ( ) {
    try {
      while ( Thread.currentThread ( ) == this ) {
        this.toDisplay.repaint ( ) ;
        try {
          Thread.sleep ( 10 ) ;
        } catch ( final InterruptedException e ) {
          e.printStackTrace ( ) ;
        }
      }
    } catch ( final RuntimeException e ) {
      e.printStackTrace ( ) ;
    }
  }

}
