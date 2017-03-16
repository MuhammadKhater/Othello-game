package oth;

import java.awt.Desktop.Action;
import java.awt.Graphics;
import java.awt.Panel;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.RepaintManager;

/**
 * Class Board is the model (data structure) that holds and updates the
 * game status as Othello is played.  It provides methods to initialize
 * the game (done automatically when the game is first created), make moves,
 * and access the game status, which can be used by client code to display
 * the game board and the state of the game
 * 
 * @author Hal Perkins 
 * @version CSE142 Homework 7, August 5, 2001
 */
public class Board {
    // public constants

    /** Number of rows/columns in game board (must be even) */
    public static final int NROWSCOLS = 12; 

    /** Non-existant square */
    public static final int BADSQUARE = -1;

    /** Empty board square */
    public static final int EMPTY = 0;

    /** Board square containing black piece */ 
    public static final int BLACK = 1; 

    /** Board square containing white piece */
    public static final int WHITE = 2;  
    
    /** Board square containing white piece */
    public static final int GREEN = 3; 

    // private instance variables
    private int[][] board;      // game board
    private int numBlack;       // # of black pieces
    private int numWhite;       // # of white pieces
    int currentColor;   // color currently moving
    private Othello display;    // the gameboard display - use it to
                                // update display after changes


    /////////////////////////////////////////////////////////////////////
    // The following methods form the interface between the game board //
    // (this class) and the GUI display controller (class Othello)     //
    // These interfaces should not be modified.  The implementation of //
    // processClick WILL need to be changed to play Othello.           //
    /////////////////////////////////////////////////////////////////////

    /** Construct and initialize new game board
     *  @param display The Othello object for this game */
    public Board(Othello display) {
        this.board = new int[NROWSCOLS][NROWSCOLS];
        this.display = display;
        this.newGame();
    }
    
    // Copy constractor 
  
  public Board(Board other){
		this.board = new int[NROWSCOLS][NROWSCOLS];
		for(int i=0; i<12; i++){
			for(int j=0; j<12; j++){
				this.board[i][j] = other.getBoard()[i][j];
			}
		}
	}
  
  public Board() {
	// TODO Auto-generated constructor stub
}

	// Return board 
    public int[][] getBoard() {
		return board;
	}
  
    // Set the board with new val from myBoard.............
    public void setMyBoard(int[][] myBoard) {
		this.board = myBoard;
	}
  
    // Return number of stones to player
    public int numOfStones(boolean player){
		int count = 0;
		int mark = (player == false)? Board.BLACK : Board.WHITE;
		for(int i = 0 ; i< Board.NROWSCOLS ; i++){
			for(int j = 0; j< Board.NROWSCOLS; j++){
				if(this.board[i][j]==mark)
					count++;
			}
		}
		return count;
	}
    
    public ArrayList<Position> ListOfPosition(){
    	ArrayList<Position> l=new ArrayList<>();
    	for(int i=0; i<Board.NROWSCOLS; i++){
    		for(int j=0; j<Board.NROWSCOLS; j++){
    			if(this.board[i][j]==Board.GREEN){
    				Position p1= new Position(i, j);
    				l.add(p1);	
    			}
    		}
    	}
    	return l;
    }
    
    /** Initialize new game */
    public void newGame() {

        // set all board squares to empty
        for (int row = 0; row < NROWSCOLS; row++) {
            for (int col = 0; col < NROWSCOLS; col++) {
                this.board[row][col] = Board.EMPTY;
            }
        }

        // set initial game conditions
        this.board[NROWSCOLS/2-1][NROWSCOLS/2-1] = Board.WHITE;
        this.board[NROWSCOLS/2-1][NROWSCOLS/2  ] = Board.BLACK;
        this.board[NROWSCOLS/2  ][NROWSCOLS/2-1] = Board.BLACK;
        this.board[NROWSCOLS/2  ][NROWSCOLS/2  ] = Board.WHITE;
        //System.out.println("dis");
         this.DisplayOptins(true);
//        for (int row = 0; row < NROWSCOLS; row++) {
//            for (int col = 0; col < NROWSCOLS; col++) {
//                System.out.print("  "+this.board[row][col]);
//            }
//            System.out.println("  ");
//        }
        this.numWhite = 2;
        this.numBlack = 2;
        this.currentColor = Board.WHITE;
       
        
    }
    
    /** Return white score */
    public int getWhiteScore() {
        return this.numWhite;
    }

    /** Return black score */
    public int getBlackScore() {
        return this.numBlack;
    }


    /** Return contents of game board square at given row/column
     *  (Squares numbered from 0 to NROWSCOLS-1).  */
    public int getSquare(int row, int col) {
    	
        if (inBounds(row,col)) {
            return this.board[row][col];
        } else {
            return Board.BADSQUARE;
        }
    }
    

    
    /** Return color of the pieces currently moving */
    public int getCurrentColor() {
        return this.currentColor;
    }


    /** Process a mouse click in the square at the given row and column.
     *  If it is a legal move, process it, otherwise ignore.
     *  (Called from user interface when a click is detected; assumes
     *  row/col numbers are in bounds.) */
    public void processClick(int row, int col) {
        // starter program code - needs to be modified to play Othello.
        // Place the current color on the selected square, then switch
        // to other color.  Call display object to update picture
    	if(this.board[row][col] == Board.GREEN){
	    	Position p1=new Position();
	    	p1.setX(row);
	    	p1.setY(col);
	    	boolean turn1=true;
	    	this.UpdateBoard(turn1,p1);
	    	this.DisplayOptins(!(turn1));
	    	this.currentColor=Board.BLACK;
	    	updateScore();
	    	this.display.updateDisplay();
	    	
	    	System.out.println();
		    System.out.println("fOR BLACK");
	    	PrintBoard();
	    	
	    	while(true){
	    		if(this.ListOfPosition().size() == 0){
	    			this.removeThrees();
	    			this.DisplayOptins(true);
	    			this.currentColor=Board.WHITE;
	    			System.out.println();
				    System.out.println("fOR WHITE");
			    	PrintBoard();
			    	break;
	    		}
	    		boolean turn2=false;
		    	Position p2=new Position();
		    	//this.DisplayOptins(turn2);
		    	//this.removeThrees();
		    	Move m=new Move();
		    	p2=m.getBestMove(this.board,turn2,this.display.selectedDepth-1,this.display.searchMethod);
		    	if(p2!=null){
		    		this.UpdateBoard(turn2,p2);
		    	}
		    	this.DisplayOptins(!(turn2));
		    	if(this.ListOfPosition().size() != 0){
		    		this.currentColor=Board.WHITE;
		    		System.out.println();
				    System.out.println("fOR WHITE");
			    	PrintBoard();
			    	break;
		    	}
		    	this.removeThrees();
		    	updateScore();
		    	this.display.updateDisplay();
		    	System.out.println();
			    System.out.println("fOR BLACK");
		    	PrintBoard();
			    
	    	}
	    	this.display.updateDisplay();
    	
    	}
    	/*if(this.board[row][col] == 0){
            this.board[row][col] = this.currentColor;
            this.toggleMove();
           this.display.updateDisplay();
    	}*/
    	
    }


    ////////////////////////////////////////////////////
    // Methods beyond this point are private to Board //
    ////////////////////////////////////////////////////

	/*
	 * The Update method getting the position of new pill 
	 * and the turn and update the matrix.
	 */
   /* public void generateComputerMove(){
    	boolean turn2=this.currentColor==2;
    	Position p2=new Position();
    	
    	Move m=new Move();
    	p2=m.getBestMove(this.board,turn2,4);
    	this.UpdateBoard(turn2,p2);
    	this.DisplayOptins(!(turn2));
    	this.toggleMove();
    	updateScore();
    	this.display.updateDisplay();
    }*/
    public void updateScore(){
    	this.numBlack = 0;
    	this.numWhite = 0;
    	for(int i = 0 ; i < 12 ; i++){
    		for(int j = 0 ; j < 12 ; j++){
    			if(board[i][j] == BLACK){
    				this.numBlack++;
    			}
    			if(board[i][j] == WHITE){
    				this.numWhite++;
    			}
    		}
    	}
    }
	public void UpdateBoard(boolean turn,Position p){
		int x,y,p1,p2;
		int row=p.getX();
		int col=p.getY();
		if(row > 11 || row<0 ||col>11 || col<0){
			return;
		}
		// Update in the row left side
		this.removeThrees();
		while(true){
			if(!turn){
				p1=Board.BLACK;
				p2=Board.WHITE;
			}
			else{
				p1=Board.WHITE;
				p2=Board.BLACK;
			}
			x=row;
			y=col;
			int count =0;
			
			if (y>9){
				break;
			}
			while((y<11)&&(this.board[x][y+1]==p2)){
				y++;count++;
			}
			if ((y<11)&&(this.board[x][y+1]!=p1)){
				break;
			}
			if((y<11)&&(this.board[x][y+1]==p1)){
				while( count!=0){
				y=col;
				this.board[x][y]=p1;
				this.board[x][y+count]=p1;
				count--;
				}}
			
			break;
		}//end while
		
		// Update in the row right side 
				while(true){
					if(!turn){
						p1=Board.BLACK;
						p2=Board.WHITE;
					}
					else{
						p1=Board.WHITE;
						p2=Board.BLACK;
					}
					x=row;
					y=col;
					int count =0;
					if (y<2){break;}
					while((y>0)&&(this.board[x][y-1]==p2)){y--;count++;}
					if ((y>0)&&(this.board[x][y-1]!=p1)){break;}
					if((y>0)&&(this.board[x][y-1]==p1)){
					while( (count!=0)){
						y=col;
						this.board[x][y]=p1;
						this.board[x][y-count]=p1;
						count--;
						}
					
					}
					break;
				}//end while
				
				// Update in the column up side 
				while(true){
					if(!turn){
						p1=Board.BLACK;
						p2=Board.WHITE;
					}
					else{
						p1=Board.WHITE;
						p2=Board.BLACK;
					}
					x=row;
					y=col;
					int count =0;
					if (x<2){break;}
					while((x>0)&&(this.board[x-1][y]==p2)){x--;count++;}
					if ((x>0)&&(this.board[x-1][y]!=p1)){break;}
					if((x>0)&&(this.board[x-1][y]==p1)){
					while((count!=0)){
						x=row;
						this.board[x][y]=p1;
						this.board[x-count][y]=p1;
						count--;
						}
					}
					break;
				}//end while
				
				// Update in the column down side 
				while(true){
					
					if(!turn){
						p1=Board.BLACK;
						p2=Board.WHITE;
					}
					else{
						p1=Board.WHITE;
						p2=Board.BLACK;
					}
					x=row;
					y=col;
					int count =0;
					if (x>9){break;}
					while((x<11)&&(this.board[x+1][y]==p2)){x++;count++;}
					if ((x<11)&&(this.board[x+1][y]!=p1)){break;}
					if((x<11)&&(this.board[x+1][y]==p1)){
					while((count!=0)){
						x=row;
						this.board[x][y]=p1;
						this.board[x+count][y]=p1;
						count--;
						}
					}
					break;
				}//end while
				
				// Update in the dig left up 
				while(true){
					if(!turn){
						p1=Board.BLACK;
						p2=Board.WHITE;
					}
					else{
						p1=Board.WHITE;
						p2=Board.BLACK;
					}
					x=row;
					y=col;
					int count =0;
					if (x<2 || y<2){break;}
					while((y>0 && x>0)&&(this.board[x-1][y-1]==p2)){x--;y--;count++;}
					if ((y>0 && x>0)&&(this.board[x-1][y-1]!=p1)){break;}
					if((y>0 && x>0)&&(this.board[x-1][y-1]==p1)){
					while((count!=0)){
						x=row;
						y=col;
						this.board[x][y]=p1;
						this.board[x-count][y-count]=p1;
						count--;
						}
					}
					break;
				}//end while
				
				// Update in the dig right up side 
				while(true){
					if(!turn){
						p1=Board.BLACK;
						p2=Board.WHITE;
					}
					else{
						p1=Board.WHITE;
						p2=Board.BLACK;
					}
					x=row;
					y=col;
					int count =0;
					if (x<2 || y>9){break;}
					while((y<11 && x>0)&&(this.board[x-1][y+1]==p2)){
						x--;
						y++;
						count++;
					}
					if ((y<11 && x>0)&&(this.board[x-1][y+1]!=p1)){
						break;
					}
					if((y<11 && x>0)&&(this.board[x-1][y+1]==p1)){
					while((count!=0)){
						x=row;
						y=col;
						this.board[x][y]=p1;
						this.board[x-count][y+count]=p1;
						count--;
						}
					}
					break;
				}//end while
				
				// Update in the dig left down side 
				while(true){
					if(!turn){
						p1=Board.BLACK;
						p2=Board.WHITE;
					}
					else{
						p1=Board.WHITE;
						p2=Board.BLACK;
					}
					x=row;
					y=col;
					int count =0;
					if (x>9 || y<2){break;}
					while((y>0 && x<11)&&(this.board[x+1][y-1]==p2)){x++;y--;count++;}
					if ((y>0 && x<11)&&(this.board[x+1][y-1]!=p1)){break;}
					if((y>0 && x<11)&&(this.board[x+1][y-1]==p1)){
					while((count!=0)){
						x=row;
						y=col;
						this.board[x][y]=p1;
						this.board[x+count][y-count]=p1;
						count--;
						}
					}
					break;
				}//end while
				

				// Update in the dig right down side 
				while(true){
					if(!turn){
						p1=Board.BLACK;
						p2=Board.WHITE;
					}
					else{
						p1=Board.WHITE;
						p2=Board.BLACK;
					}
					x=row;
					y=col;
					int count =0;
					if (x>9 || y>9){break;}
					while((y<11 && x<11)&&(this.board[x+1][y+1]==p2)){
						x++;y++;count++;
						}
					if ((x<11 && y<11) && this.board[x+1][y+1]!=p1){break;}
					if((x<11 && y<11) && (this.board[x+1][y+1]==p1)){
					while(( count!=0)){
						x=row;
						y=col;
						this.board[x][y]=p1;
						this.board[x+count][y+count]=p1;
						count--;
						}
					}
					break;
				}//end while
	}// end UpdateBoard
	
	public void removeThrees(){
    	
    	for(int i=0; i<Board.NROWSCOLS; i++){
    		for(int j=0; j<Board.NROWSCOLS; j++){
    			if(this.board[i][j]==Board.GREEN){
    				this.board[i][j]=Board.EMPTY;
    			}
    		}
    	}
    }
    
	// Display all the options to the player
	public void DisplayOptins(boolean turn){
			int p1,p2,x,y;
		///	System.out.println("good");
			if(!turn){p1=1;p2=2;}
			else{p1=2;p2=1;}
				for(int i=0; i<12; i++){
					for(int j=0; j<12; j++){
						if(this.board[i][j]==Board.BLACK||this.board[i][j]==Board.WHITE){continue;}
						x=i;y=j;
						
						while(true){// row-left
							x=i;y=j;
							int counter=0;
							if (y>9){break;}
							while((y<11)&&(this.board[x][y+1]==p2)){y++;counter=1;}
							if ((y<11)&&(this.board[x][y+1]!=p1)||(counter==0)){break;}
							else{if((y<11)&&(this.board[x][y+1]==p1)){
										y=j;
										this.board[x][y]=Board.GREEN;;}
								}
							break;
						}//end while
						
						
						while(true){//row-right 
							x=i;y=j;
							int counter=0;
							if (y<2){break;}
							while((y>0)&&(this.board[x][y-1]==p2)){y--;counter=1;}
							if ((y>0)&&(this.board[x][y-1]!=p1)|| counter==0){break;}
							else{if((y>0)&&(this.board[x][y-1]==p1)){
								y=j;
								this.board[x][y]=Board.GREEN;}}
							break;
						}//end while
						
						
						while(true){//  column-up 
							x=i;y=j;
							int counter=0;
							if (x<2){break;}
							while((x>0)&&(this.board[x-1][y]==p2)){x--;counter=1;}
							if (((x>0)&&(this.board[x-1][y]!=p1)) || counter==0){break;}
							else{if((x>0)&&(this.board[x-1][y]==p1)){
								x=i;
								this.board[x][y]=Board.GREEN;;}}
							break;
						}//end while
						
						// column-down
						while(true){
							x=i;y=j;
							int counter=0;
							if (x>9){break;}
							while((x<11)&&(this.board[x+1][y]==p2)){x++;counter=1;}
							if ((x<11)&&(this.board[x+1][y]!=p1)|| counter==0){break;}
							else{if((x<11)&&(this.board[x+1][y]==p1)){
								x=i;
								this.board[x][y]=Board.GREEN;;}}
							break;
						}//end while
						
						// dig-left-up 
						while(true){
							x=i;y=j;
							int counter=0;
							if (x<2 || y<2){break;}
							while((y>0 && x>0)&&(this.board[x-1][y-1]==p2)){y--;x--;counter=1;}
							if (((y>0 && x>0)&&(this.board[x-1][y-1]!=p1))||counter==0){break;}
							else{if((y>0 && x>0)&&(this.board[x-1][y-1]==p1)){
								x=i;y=j;
								
								this.board[x][y]=Board.GREEN;;}}
							break;
						}//end while
						
						// dig-right-up
						while(true){
							x=i;y=j;
							int counter =0;
							if (x<2 || y>9){break;}
							while((y<11 && x>0)&&(this.board[x-1][y+1]==p2)){x--;y++;counter=1;}
							if ((y<11 && x>0)&&(this.board[x-1][y+1]!=p1)|| counter==0 ){break;}
							else{if((y<11 && x>0)&&(this.board[x-1][y+1]==p1)){
								x=i;y=j;
								this.board[x][y]=Board.GREEN;;}}
							break;
						}//end while
						
						//dig-left-down 
						while(true){
							x=i;y=j;
							int counter =0;
							if (x>9 || y<2){break;}
							while((y>0 && x<11)&&(this.board[x+1][y-1]==p2)){x++;y--;counter=1;}
							if ((y>0 && x<11)&&(this.board[x+1][y-1]!=p1) || counter==0){break;}
							else{if((y>0 && x<11)&&(this.board[x+1][y-1]==p1)){
								x=i;y=j;
								this.board[x][y]=Board.GREEN;;}}
							break;
						}//end while
						
						//dig-right-down
						while(true){
							x=i;y=j;
							int counter =0;
							if (x>9 || y>9){break;}
							while((y<11 && x<11)&&(this.board[x+1][y+1]==p2)){x++;y++;counter=1;}
							if ((y<11 && x<11)&&((this.board[x+1][y+1]!=p1)) || counter==0){break;}
							else{if((y<11 && x<11)&&(this.board[x+1][y+1]==p1)){
								x=i;y=j;
								this.board[x][y]=Board.GREEN;;}}
							break;
						}//end while
					}
				}
		
			
				// this.display.updateDisplay();
		}
    // Return color of other pieces not moving now
    
	public void PrintBoard(){
		char leater=1;
		System.out.print("   ");
		for(int j=0; j<12; j++){
			System.out.print("  "+j);
		}
		System.out.println();
		for(int i=0; i<12; i++){
			System.out.print(i+"-");
			if(i<10){
				System.out.print(" ");
			}
			for(int j=0; j<12; j++){
				
				if(this.board[i][j]==2){System.out.print("  "+leater);}
				else{System.out.print("  "+this.board[i][j]);}	
			}
			System.out.println("");
		}
		
	}
	
	private int getOther() {
        if (this.currentColor == Board.WHITE) {
            return Board.BLACK;
        } else {
            return Board.WHITE;
        }
    }

    // Change current move to other color
    void toggleMove() {
        this.currentColor = getOther();
    }

    // Return true if row and column number are inside the board,
    // otherwise return false
    private boolean inBounds(int row, int col) {
        return (row >= 0 && row < NROWSCOLS && col >= 0 && col < NROWSCOLS);
    }

    
    // Return number of empty places
    public int NumEmpty(){
		int counter=0;
		for(int i=0; i<Board.NROWSCOLS; i++){
			for(int j=0; j<Board.NROWSCOLS; j++){
				if(this.board[i][j]==Board.EMPTY){counter++;}
			}
		}
		return counter;	
	}

    // Return true if it current player (Board.WHITE or Board.Black)
    //  can make a move at the given row and column
   /* private boolean canMove(int row, int col) {
    	this.DisplayOptins(true);
    	if(this.getSquare(row,col)==3){return true;}
        // replace the body of this method with your code
        return false;   // replace
    }*/

 

    // Make a move for the current player at the given row and column.
    // Precondition: canMove(row,col) is true when this is called.
    /*private void makeMove(int row, int col) {
        // replace this comment with your code
    }*/

}