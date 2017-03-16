package oth;
import java.awt.*;
import java.awt.event.*;

/**
 * This class provides the display representation of a single square
 * on the Othello game board and responds to mouse clicks on that square.
 * 
 * @Hal Perkins, Robert Carr 
 * @version August 5, 2001
 */
public class Square extends Panel implements MouseListener {
        
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int state;          // Contents of this square (Board.EMPTY, etc.)
    private int row;            // Row of this square on the game board    
    private int col;            // Column of this square on the game board
    private Board board;        // Reference to the actual game board
     
    /** Initialize this Square to the given state, and remember the row and
     *  column number locating this square on the game board.  Remember the
     *  actual game board object so we can report clicks to it. */
    public Square(int state, int row, int col, Board board) {
        this.state = state;
        this.row = row;
        this.col = col;
        this.board = board;
        this.addMouseListener(this);
    }
    
    /** Change state of this square.  Repaint only if state changes
     *  to minimize flicker.  */
    public void setState(int state) {
        int oldstate = this.state;
        this.state = state;
        if (oldstate != state) {
            this.repaint();
        }
    }
     
    /** Paint this square when requested */
    public void paint(Graphics g) {
        super.paint(g);
        
        g.setColor(Color.orange);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        g.setColor(Color.black);
        g.drawRect(0, 0, this.getWidth() - 1, this.getHeight()- 1);
        
        
        
        if(state == Board.WHITE) {
            g.setColor(Color.white);
            g.fillOval(5, 5, this.getWidth() - 10, this.getHeight() - 10);
        } else if (state == Board.BLACK) {
            g.setColor(Color.black);
            g.fillOval(5, 5, this.getWidth() - 10, this.getHeight() - 10);
        } else if (state == Board.GREEN) {
            g.setColor(Color.green);
            g.fillOval(5, 5, this.getWidth() - 10, this.getHeight() - 10);
        }
        
    }
    
    // Respond to mouse click on this square
    public void mouseClicked(MouseEvent e) {
        this.board.processClick(this.row, this.col);
        //System.out.println("good");
        //this.board.processClick(row, col);
        
        
        this.repaint();
        
    }
    
    // Other methods in MouseListener.  Do nothing if these happen.
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {this.repaint();}
}