package oth;
import java.awt.*;
import java.awt.event.*;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JSpinner;
import javax.swing.ListModel;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicBorders.RadioButtonBorder;

/**
 * This class provides the user interface and control for the game Othello.
 * 
 * @Hal Perkins, Robert Carr 
 * @version August 5, 2001
 */
public class Othello extends Panel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// game instance variables
    private Board board;        // game board
    public static boolean searchMethod=false;
    public static int selectedDepth = 1;
    // GUI components
    private Label blackScore;           // black and white scores
    private Label whiteScore;
    private Label turn;                 // Turn indicator
    private JButton startButton;
    private JButton searchAl;
    private JSpinner depthSpinner;
    private Label depth;
    private Button resetButton;         // reset control
    private Square[][] squares;         // board squares for display
    
    public static Panel tmp;

    /** Construct new Othello game */
    public Othello()    {
        // create and initialize game board and display representation
        this.board = new Board(this);
        this.squares = new Square[Board.NROWSCOLS][Board.NROWSCOLS];
        ////////////////this.board.DisplayOptins(true);
        // set layout for game display
        this.setLayout(new BorderLayout());
        
        // Set up panel containing scores
        Panel infoPanel = new Panel();
        infoPanel.setLayout(new GridLayout(1,3));

        this.blackScore = new Label("Black: " );
        this.blackScore.setFont(new Font("Serif", Font.BOLD, 16));
        this.whiteScore = new Label("White: " );
        this.whiteScore.setFont(new Font("Serif", Font.BOLD, 16));
        this.turn = new Label("Turn: ");
        this.turn.setFont(new Font("Serif", Font.BOLD, 18));
        
        infoPanel.add(this.blackScore);
        infoPanel.add(this.turn);
        infoPanel.add(this.whiteScore);
        
       /* Panel infoPanel2 = new Panel();
        infoPanel.setLayout(new GridLayout(1, 4));*/
        this.searchAl = new JButton("Minimax");
        this.searchAl.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				searchMethod = !searchMethod;
				String s = searchAl.getText().equals("Minimax") ? "Alpha-Beta":"Minimax";
				searchAl.setText(s);
			}
		});
        infoPanel.add(this.searchAl);
        this.depth = new Label("Depth Search: ");
        infoPanel.add(this.depth);
        SpinnerModel s = new SpinnerNumberModel(1, 1, 8, 1);
        this.depthSpinner = new JSpinner(s);
        depthSpinner.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				selectedDepth = (int) depthSpinner.getValue();
				
			}
		});
        infoPanel.add(this.depthSpinner);
        
        this.add(infoPanel, BorderLayout.NORTH);
        this.startButton = new JButton("Pc vs Pc");
        
        //this.add(infoPanel2,BorderLayout.NORTH);
        // Create board squares and add to display
        Panel boardPanel = new Panel();
        boardPanel.setLayout(new GridLayout(Board.NROWSCOLS,Board.NROWSCOLS));
        for (int row = 0; row < Board.NROWSCOLS; row++) {
            for (int col = 0; col < Board.NROWSCOLS; col++) {
                squares[row][col] = new Square(Board.EMPTY, row, col, this.board);
                boardPanel.add(squares[row][col]);
           }
        }
        this.add(boardPanel, BorderLayout.CENTER);
        tmp=boardPanel;

        // Set up reset button so it starts new game when clicked
        resetButton = new Button("new game");
        resetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                board.newGame();
                
                updateDisplay();
                repaint();
            }
        });
        this.add(resetButton, BorderLayout.SOUTH);
        
        	this.startButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				manageGame();
			}

			
		});
        infoPanel.add(startButton);

        // show initial display
       this.updateDisplay();
    }

    protected void manageGame() {
    	while(true){
    		Position p = new Position();
    		boolean currentTurn = this.board.currentColor == Board.WHITE;
    		this.board.DisplayOptins(currentTurn);
    		this.updateDisplay();
    		Move m = new Move();
    		if(currentTurn == false){
    			p = m.getBestMoveB(this.board.getBoard(),currentTurn,this.selectedDepth,this.searchMethod);
    		}
    		else{
        		p = m.getBestMove(this.board.getBoard(),currentTurn,this.selectedDepth,this.searchMethod);

    		}
    		if(p!= null){
    			this.board.UpdateBoard(currentTurn, p);
    		}
    		
    		this.board.toggleMove();
    		this.updateDisplay();
    		this.board.updateScore();
    		this.repaint();
    		if(this.board.NumEmpty() == 0){
    			break;
    		}
    	}
    	this.updateDisplay();
		
	}

	// Update display to match game state.
    public void updateDisplay() {
        // update scores
        this.whiteScore.setText("       White: " + this.board.getWhiteScore());
        this.blackScore.setText("Black: " + this.board.getBlackScore());
        
        String currentTurn = "Turn: ";
        if (this.board.getCurrentColor() == Board.WHITE) {
            currentTurn = currentTurn + "WHITE";
            this.turn.setBackground(Color.WHITE);
            
        } else {
            currentTurn = currentTurn + "BLACK";
            this.turn.setBackground(Color.BLACK);
        }
        this.turn.setText(currentTurn);

        // update board display
        for (int row = 0; row < Board.NROWSCOLS; row++) {
            for (int col = 0; col < Board.NROWSCOLS; col++) {
                this.squares[row][col].setState(this.board.getSquare(row,col));
            }
        }
        this.repaint();
        /*this.board.generateComputerMove();
        this.repaint();*/
    }
        
    public Square[][] getSquares(){
    	return this.squares;
    }
    /** Create new game and a window to display it */
    
   /* public void manageGame(){
    	
    		if(this.board.getCurrentColor() == Board.WHITE){
        		Board tmp = new Board(this.board);
        		tmp.DisplayOptins(true);
        		if(tmp.ListOfPosition().size() == 0){
        			boolean turn2=this.board.currentColor==2;
    		    	Position p2=new Position();
    		    	
    		    	Move m=new Move();
    		    	p2=m.getBestMove(this.board.getBoard(),turn2,this.selectedDepth-1,this.searchMethod);
    		    	this.board.UpdateBoard(turn2,p2);
    		    	this.board.DisplayOptins(!(turn2));
    		    	this.board.toggleMove();
    		    	this.board.updateScore();
        		}
        	}
    	
    	
    }*/
    @SuppressWarnings("deprecation")
	public static void main(String[] args) {
        Frame f = new Frame("Othello");     // top-level window
        Othello o = new Othello();
       
        
        f.add(o);
        
        f.addWindowListener( new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        f.setSize(800,800);
        f.show();
    }

	public Board getBoard() {
		// TODO Auto-generated method stub
		return this.board;
	}

}