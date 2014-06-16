package net.mokon.scrabble ;

import java.awt.BorderLayout ;
import java.awt.Color ;
import java.awt.Dimension ;
import java.awt.Font ;
import java.awt.Graphics ;
import java.awt.event.ActionEvent ;
import java.awt.event.ActionListener ;
import java.awt.event.ItemEvent ;
import java.awt.event.ItemListener ;
import java.awt.event.KeyEvent ;
import java.awt.event.KeyListener ;
import java.awt.event.MouseAdapter ;
import java.awt.event.MouseEvent ;
import java.io.File ;
import java.io.FileInputStream ;
import java.io.FileOutputStream ;
import java.io.IOException;
import java.io.ObjectInputStream ;
import java.io.ObjectOutputStream ;
import java.util.Collection ;
import java.util.Vector ;
import javax.swing.BorderFactory ;
import javax.swing.BoxLayout ;
import javax.swing.JFileChooser ;
import javax.swing.JFrame ;
import javax.swing.JList ;
import javax.swing.JMenu ;
import javax.swing.JMenuBar ;
import javax.swing.JMenuItem ;
import javax.swing.JPanel ;
import javax.swing.JScrollPane ;
import javax.swing.JTextArea ;
import javax.swing.JTextPane ;
import javax.swing.ScrollPaneConstants ;
import javax.swing.filechooser.FileNameExtensionFilter ;

public class GUI extends JPanel implements KeyListener, ActionListener,
    ItemListener {

  public ScrabbleBoard         board ;

  public final JList           wordList ;
  private final JTextPane      console ;

  private final GridSquare[][] grid ;
  private final JTextArea      handGUI ;

  private final JScrollPane    historyArea ;

  private final Player         player ;

  private final JPanel         textArea ;

  private final JMenuBar       menuBar ;

  public static Vector<String> commandHistory ;
  public static int            commandHistoryLocation ;
  public static GUI            gui ;
  private static JTextArea     history ;

  /**
   * 
   */
  private static final long    serialVersionUID = -2336242012084111709L ;

  private static final String  binaryExtension  = "bsc" ;
  private static final String  cvsExtension     = "csv" ;

  public GUI ( ScrabbleBoard board, Player player ) {
    super ( ) ;
    GUI.gui = this ;
    this.setLayout ( new BorderLayout ( ) ) ;
    this.player = player ;

    final JPanel gridGUI = new JPanel ( ) ;
    gridGUI.setLayout ( new BoxLayout ( gridGUI, BoxLayout.Y_AXIS ) ) ;
    gridGUI.setPreferredSize ( new Dimension ( 15 * 50, 15 * 50 ) ) ;
    this.add ( gridGUI, BorderLayout.WEST ) ;

    this.menuBar = new JMenuBar ( ) ;
    JMenu menu = new JMenu ( "File" ) ;
    JMenuItem saveAsBinary = new JMenuItem ( "Save as Binary" ) ;
    JMenuItem openBinary = new JMenuItem ( "Open Binary" ) ;
    JMenuItem openCVS = new JMenuItem ( "Open CSV" ) ;
    JMenuItem saveAsCVS = new JMenuItem ( "Save as CSV" ) ;
    menu.add ( saveAsBinary ) ;
    menu.add ( saveAsCVS ) ;
    menu.add ( openBinary ) ;
    menu.add ( openCVS ) ;
    this.menuBar.add ( menu ) ;
    this.add ( this.menuBar, BorderLayout.NORTH ) ;
    saveAsBinary.addActionListener ( this ) ;
    saveAsCVS.addActionListener ( this ) ;
    openBinary.addActionListener ( this ) ;
    openCVS.addActionListener ( this ) ;

    this.wordList = new JList ( ) ;
    this.wordList.setFixedCellWidth ( 150 ) ;

    this.add ( this.wordList, BorderLayout.EAST ) ;
    this.wordList.setBorder ( BorderFactory.createTitledBorder ( BorderFactory
        .createEtchedBorder ( ), "Words" ) ) ;
    this.wordList.setListData ( new String[] { "" } ) ;

    this.textArea = new JPanel ( ) ;
    this.textArea.setLayout ( new BorderLayout ( ) ) ;
    this.add ( this.textArea, BorderLayout.CENTER ) ;

    this.handGUI = new JTextArea ( " Hand [ ] ", 1, 30 ) ;
    this.handGUI.setFont ( new Font ( "Monospaced", Font.PLAIN, 30 ) ) ;
    this.handGUI.setEditable ( false ) ;
    this.handGUI.setBackground ( Color.BLACK ) ;
    this.handGUI.setForeground ( Color.WHITE ) ;
    //this.handGUI.setMaximumSize ( this.handGUI.getPreferredSize ( ) ) ;
    this.textArea.add ( this.handGUI, BorderLayout.NORTH ) ;

    this.console = new JTextPane ( ) ;
    this.console.setBackground ( Color.BLACK ) ;
    this.console.setForeground ( Color.GREEN ) ;
    this.console.setCaretColor ( Color.GREEN ) ;
    this.console.setEditable ( true ) ;
    this.console.addKeyListener ( this ) ;
    this.console.setCaretPosition ( 0 ) ; ;
    this.console.setFont ( new Font ( "Monospaced", Font.PLAIN, 30 ) ) ;
    //this.console.setPreferredSize ( this.handGUI.getPreferredSize ( ) ) ;
    //this.console.setMaximumSize ( getPreferredSize ( ) ) ;
    this.textArea.add ( this.console, BorderLayout.SOUTH ) ;

    GUI.history = new JTextArea ( ) ;
    GUI.history.setWrapStyleWord ( true ) ;
    GUI.history.setFont ( new Font ( "Monospaced", Font.PLAIN, 13 ) ) ;
    GUI.history.setBackground ( Color.BLACK ) ;
    GUI.history.setForeground ( Color.GREEN ) ;
    GUI.history.setEditable ( false ) ;

    this.historyArea = new JScrollPane ( GUI.history ) ;
    //this.historyArea.setPreferredSize ( new Dimension ( 400, 400 ) ) ;
    this.historyArea
        .setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS ) ;
    this.historyArea.setBackground ( Color.BLACK ) ;
    this.historyArea.setForeground ( Color.GREEN ) ;
    this.textArea.add ( this.historyArea, BorderLayout.CENTER ) ;

    this.board = board ;

    this.grid = new GridSquare[ board.boardHeight ][ board.boardWidth ] ;
    for ( int i = 0 ; i < this.board.boardHeight ; i++ ) {

      final JPanel row = new JPanel ( ) ;
      row.setLayout ( new BoxLayout ( row, BoxLayout.X_AXIS ) ) ;

      for ( int j = 0 ; j < this.board.boardWidth ; j++ ) {
        this.grid[ i ][ j ] = new GridSquare ( this.board.charGrid[ i ][ j ],
            GUI.getColor ( this.board.valueGrid[ i ][ j ],
                this.board.typeGrid[ i ][ j ] ), i, j, this.board ) ;
        row.add ( this.grid[ i ][ j ] ) ;
      }
      gridGUI.add ( row ) ;
    }

    this.wordList.addMouseListener ( new MouseAdapter ( ) {
      @Override public void mouseClicked ( MouseEvent evt ) {
        JList list = (JList) evt.getSource ( ) ;
        int index = list.locationToIndex ( evt.getPoint ( ) ) ;

        if ( evt.getClickCount ( ) == 1
            && !evt.isShiftDown ( )
            && GUI.this.wordList.getMaxSelectionIndex ( ) == GUI.this.wordList
                .getMinSelectionIndex ( ) ) {
          final Move selected = (Move) GUI.this.wordList.getSelectedValue ( ) ;
          final int x = selected.x ;
          final int y = selected.y ;
          final int l = selected.word.length ( ) ;
          final char[] cOld = new char[ l ] ;

          for ( int i = 0 ; i < l ; i++ ) {
            GUI.this.grid[ x + ( !selected.isUpDown ? i : 0 ) ][ y
                + ( selected.isUpDown ? i : 0 ) ].toogle = true ;
            cOld[ i ] = GUI.this.board.charGrid[ x
                + ( !selected.isUpDown ? i : 0 ) ][ y
                + ( selected.isUpDown ? i : 0 ) ] ;
            GUI.this.board.charGrid[ x + ( !selected.isUpDown ? i : 0 ) ][ y
                + ( selected.isUpDown ? i : 0 ) ] = selected.word.charAt ( i ) ;
          }
          new Thread ( new Runnable ( ) {
            @Override public void run ( ) {
              try {
                Thread.sleep ( 2000 ) ;
              } catch ( InterruptedException e ) {}
              for ( int i = 0 ; i < l ; i++ ) {
                GUI.this.grid[ x + ( !selected.isUpDown ? i : 0 ) ][ y
                    + ( selected.isUpDown ? i : 0 ) ].toogle = false ;
                GUI.this.board.charGrid[ x + ( !selected.isUpDown ? i : 0 ) ][ y
                    + ( selected.isUpDown ? i : 0 ) ] = cOld[ i ] ;
              }
            }

          } ).start ( ) ;

        } else if ( evt.getClickCount ( ) == 1
            && evt.isShiftDown ( )
            && GUI.this.wordList.getMaxSelectionIndex ( ) == GUI.this.wordList
                .getMinSelectionIndex ( ) ) {
          Core.handle ( "place " + index + 1, GUI.this.console ) ;
        }

      }
    } ) ;

    final JFrame window = new JFrame (
        "Mokon.Net Scrabble Solver - by David 'Mokon' Bond" ) ;
    window.setResizable ( false ) ;
    window.setContentPane ( this ) ;
    window.setDefaultCloseOperation ( JFrame.EXIT_ON_CLOSE ) ;
    window.pack ( ) ;
    window.setVisible ( true ) ;

    GUI.commandHistory = new Vector<String> ( ) ;
    new GUIThread ( this ).start ( ) ;
    GUI.write ( "Start typing in the console above to begin." ) ;
  }

  /* (non-Javadoc)
   * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
   */
  @Override public void actionPerformed ( ActionEvent e ) {
    JFileChooser chooser = new JFileChooser ( ) ;
    FileNameExtensionFilter filter = new FileNameExtensionFilter (
        "Binary Scrabble Format, CVS Format", GUI.binaryExtension, GUI.cvsExtension ) ;
    chooser.setFileFilter ( filter ) ;

    if ( e.getActionCommand ( ).equals ( "Save as Binary" ) ) {
      if ( chooser.showSaveDialog ( this ) == JFileChooser.APPROVE_OPTION ) {
        try {
          File toCreate = new File ( chooser.getSelectedFile ( )
              .getAbsolutePath ( )
              + "." + GUI.binaryExtension ) ;
          toCreate.createNewFile ( ) ;
          new ObjectOutputStream ( new FileOutputStream ( toCreate ) )
              .writeObject ( this.board ) ;
        } catch ( Exception e1 ) {
          GUI.write ( e1.toString ( ) ) ;
        }
      }
    } else if ( e.getActionCommand ( ).equals ( "Save as CSV" ) ) {
      if ( chooser.showSaveDialog ( this ) == JFileChooser.APPROVE_OPTION ) {
        try {
          File toCreate = new File ( chooser.getSelectedFile ( )
              .getAbsolutePath ( )
              + "." + GUI.cvsExtension ) ;
          toCreate.createNewFile ( ) ;
          new FileOutputStream ( toCreate ).write ( this.board.toCSV ( )
              .getBytes ( ) ) ;
        } catch ( Exception e1 ) {
         GUI.write ( e1.toString ( ) ) ;
        }
      }
    } else if ( e.getActionCommand ( ).equals ( "Open Binary" ) ) {
      if ( chooser.showOpenDialog ( this ) == JFileChooser.APPROVE_OPTION ) {
        try {
          File toOpen = new File ( chooser.getSelectedFile ( )
              .getAbsolutePath ( ) ) ;
          toOpen.createNewFile ( ) ;
          this.board = (ScrabbleBoard) new ObjectInputStream (
              new FileInputStream ( toOpen ) ).readObject ( ) ;
        } catch ( Exception e1 ) {
          GUI.write ( e1.toString ( ) ) ;
        }
      }
    } else if ( e.getActionCommand ( ).equals ( "Open CSV" ) ) {
      if ( chooser.showOpenDialog ( this ) == JFileChooser.APPROVE_OPTION ) {
        try {
          File toOpen = new File ( chooser.getSelectedFile ( )
              .getAbsolutePath ( ) ) ;
          toOpen.createNewFile ( ) ;
          this.board.fromCSV ( new FileInputStream ( toOpen ) ) ;
        } catch ( IOException e1 ) {
          GUI.write ( e1.toString ( ) ) ;
        }
      }
    }

  }

  /* (non-Javadoc)
   * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
   */
  @Override public void itemStateChanged ( ItemEvent e ) {}

  @Override public void keyPressed ( KeyEvent arg0 ) {
    if ( arg0.getKeyCode ( ) == KeyEvent.VK_DOWN ) {
      GUI.commandHistoryLocation-- ;
      if ( GUI.commandHistoryLocation >= 0
          && GUI.commandHistoryLocation < GUI.commandHistory.size ( ) ) {
        this.console.setText ( GUI.commandHistory
            .get ( GUI.commandHistoryLocation ) ) ;
      } else {
        GUI.commandHistoryLocation = 0 ;
      }
    } else if ( arg0.getKeyCode ( ) == KeyEvent.VK_UP ) {
      GUI.commandHistoryLocation++ ;
      if ( GUI.commandHistoryLocation >= 0
          && GUI.commandHistoryLocation < GUI.commandHistory.size ( ) ) {
        this.console.setText ( GUI.commandHistory
            .get ( GUI.commandHistoryLocation ) ) ;
      } else {
        GUI.commandHistoryLocation = GUI.commandHistory.size ( ) - 1 ;
      }
    }
  }

  @Override public void keyReleased ( KeyEvent arg0 ) {}

  @Override public void keyTyped ( KeyEvent arg0 ) {
    if ( arg0.getKeyCode ( ) == KeyEvent.VK_ENTER
        || arg0.getKeyChar ( ) == '\n' ) {
      Core.handle ( this.console.getText ( ), this.console ) ;
    }
  }

  public void setWordList ( Collection<Move> moves ) {
    this.wordList.setListData ( moves.toArray ( ) ) ;
  }

  @Override protected void paintComponent ( Graphics arg0 ) {
    if ( this.board.isReady ) {
      for ( int i = 0 ; i < this.board.boardHeight ; i++ ) {
        for ( int j = 0 ; j < this.board.boardWidth ; j++ ) {
          this.grid[ i ][ j ].setChar ( this.board.charGrid[ i ][ j ] ) ;
        }
      }
      String handText = " Hand [ " ;
      for ( int i = 0 ; i < this.player.hand.size ( ) ; i++ ) {
        if ( i == 30 ) {
          handText += "..." ;
          break ;
        }
        handText += this.player.hand.get ( i ) ;
      }
      this.handGUI.setText ( handText + " ] " ) ;
    }
  }

  public static Color getColor ( byte multiplier, byte type ) {
    switch ( type ) {
      case 0:
        switch ( multiplier ) {
          case 1:
            return new Color ( 170, 100, 0 ) ;
          case 2:
            return Color.CYAN ;
          case 3:
            return Color.BLUE ;
          default:
            return null ;
        }
      case 2:
        return Color.PINK ;
      case 3:
        return Color.MAGENTA ;
      case 4:
        return Color.PINK ;
      default:
        return null ;
    }
  }

  public static String getDescriptor ( byte multiplier, byte type ) {
    switch ( type ) {
      case 0:
        switch ( multiplier ) {
          case 1:
            return "" ;
          case 2:
            return "Double Letter Score" ;
          case 3:
            return "Triple Letter Score" ;
          default:
            return null ;
        }
      case 2:
        return "Double Word Score" ;
      case 3:
        return "Triple Word Score" ;
      case 4:
        return "Starting Position" ;
      default:
        return null ;
    }
  }

  public static void write ( String s ) {
    GUI.history.setText ( "> " + s + "\n" + GUI.history.getText ( ) ) ;
    GUI.history.setCaretPosition ( 0 ) ;
    GUI.history.repaint ( 100 ) ;
  }
}
