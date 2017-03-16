package oth;

import java.util.*;


public class Move {
	public static int o = 0;
	final double MAXVALUE = -10000;
	final double MINVALUE = 10000;
	private final double[][] static_h = {{100,-30,10,8,8,8,8,8,8,10,-30,100},
										  {-30,-50,-4.5,-5,-5,-5,-5,-5,-5,-4.5,-50,-30},
										  {10,-4.5,0.3,0.1,0.1,0.1,0.1,0.1,0.1,0.3,-4.5,10},
										  {8,-5,0.1,0.5,0.5,0.5,0.5,0.5,0.5,0.1,-5,8},
										  {8,-5,0.1,0.5,0.5,0.5,0.5,0.5,0.5,0.1,-5,8},
										  {8,-5,0.1,0.5,0.5,0.5,0.5,0.5,0.5,0.1,-5,8},
										  {8,-5,0.1,0.5,0.5,0.5,0.5,0.5,0.5,0.1,-5,8},
										  {8,-5,0.1,0.5,0.5,0.5,0.5,0.5,0.5,0.1,-5,8},
										  {8,-5,0.1,0.5,0.5,0.5,0.5,0.5,0.5,0.1,-5,8},
										  {10,-4.5,0.3,0.1,0.1,0.1,0.1,0.1,0.1,0.3,-4.5,10},
										  {-30,-50,-4.5,-5,-5,-5,-5,-5,-5,-4.5,-50,-30},
										  {100,-30,10,8,8,8,8,8,8,10,-30,100},
									  };
	
	
	
	public double minimax(Board tmpBoard, Position pos,final boolean maxTurn, boolean turn,int depth,final int maxDepth){
		double maxValue = MAXVALUE;
		//debug
		
		//debug
		if(depth > maxDepth){
			return 0; 
		}
		if(depth == maxDepth){
			Board b = new Board(tmpBoard);
			b.UpdateBoard(turn, pos);
			double result = calculateOverAllHeuristics(b,  maxTurn,pos);
			if(!goodMoveb(b, pos, turn)){
				return -1998;
			}
			return result;
		}
		else if(turn == maxTurn){
				tmpBoard.UpdateBoard(turn, pos);
				tmpBoard.DisplayOptins(!turn);
				ArrayList<Position> oppMoves = tmpBoard.ListOfPosition();
				tmpBoard.removeThrees();
				maxValue=MAXVALUE;
				if(oppMoves.size() == 0){
					Board b = new Board(tmpBoard);
					double result = calculateOverAllHeuristics(b,  maxTurn,pos);
					return result;
				}
				for(Position iterator: oppMoves){
					Board innerBoard = new Board(tmpBoard);
					double result = minimax(innerBoard, iterator, maxTurn, !turn, depth+1, maxDepth);
					if(result >= maxValue){
						maxValue=result;
					}
				}
				
				
		}
		else if(turn == !maxTurn){
				tmpBoard.UpdateBoard(turn, pos);
				tmpBoard.DisplayOptins(!turn);
				ArrayList<Position> oppMoves = tmpBoard.ListOfPosition();
				tmpBoard.removeThrees();
				maxValue=MINVALUE;
				if(oppMoves.size() == 0){
					Board b = new Board(tmpBoard);
					double result = calculateOverAllHeuristics(b,  maxTurn,pos);
					return result;
				}
				for(Position iterator: oppMoves){
					Board innerBoard = new Board(tmpBoard);
					double result = minimax(innerBoard, iterator, maxTurn, !turn, depth+1, maxDepth);
					if(result <= maxValue){
						maxValue=result;
					}
				}
		}
		return maxValue;
		
	}
	
	
	
	public double minimaxB(Board tmpBoard, Position pos,final boolean maxTurn, boolean turn,int depth,final int maxDepth){
		double maxValue = MAXVALUE;
		//debug
		
		//debug
		if(depth > maxDepth){
			return 0; 
		}
		if(depth == maxDepth){
			Board b = new Board(tmpBoard);
			b.UpdateBoard(turn, pos);
			double result = calculateOverAllHeuristicsB(b,  maxTurn,pos);
			/*if(!goodMoveb(b, pos, turn)){
				return -1998;
			}*/
			return result;
		}
		else if(turn == maxTurn){
				tmpBoard.UpdateBoard(turn, pos);
				tmpBoard.DisplayOptins(!turn);
				ArrayList<Position> oppMoves = tmpBoard.ListOfPosition();
				tmpBoard.removeThrees();
				maxValue=MAXVALUE;
				if(oppMoves.size() == 0){
					Board b = new Board(tmpBoard);
					double result = calculateOverAllHeuristicsB(b,  maxTurn,pos);
					return result;
				}
				for(Position iterator: oppMoves){
					Board innerBoard = new Board(tmpBoard);
					double result = minimaxB(innerBoard, iterator, maxTurn, !turn, depth+1, maxDepth);
					if(result >= maxValue){
						maxValue=result;
					}
				}
				
				
		}
		else if(turn == !maxTurn){
				tmpBoard.UpdateBoard(turn, pos);
				tmpBoard.DisplayOptins(!turn);
				ArrayList<Position> oppMoves = tmpBoard.ListOfPosition();
				tmpBoard.removeThrees();
				maxValue=MINVALUE;
				if(oppMoves.size() == 0){
					Board b = new Board(tmpBoard);
					double result = calculateOverAllHeuristicsB(b,  maxTurn,pos);
					return result;
				}
				for(Position iterator: oppMoves){
					Board innerBoard = new Board(tmpBoard);
					double result = minimaxB(innerBoard, iterator, maxTurn, !turn, depth+1, maxDepth);
					if(result <= maxValue){
						maxValue=result;
					}
				}
		}
		return maxValue;
		
	}
	
	public double alphaVbeta(Board tmpBoard, Position pos,final boolean maxTurn,
									boolean turn,int depth,final int maxDepth,double alpha,double beta){
		double value;
		
		if(depth == maxDepth){
			Board b = new Board(tmpBoard);
			b.UpdateBoard(turn, pos);
			double result = calculateOverAllHeuristics(b,  maxTurn,pos);
			if(!goodMoveb(b, pos, turn)){
				return -1998;
			}
			return result;
		}
		else if(turn == maxTurn){
			tmpBoard.UpdateBoard(turn, pos);
			tmpBoard.DisplayOptins(!turn);
			ArrayList<Position> oppMoves = tmpBoard.ListOfPosition();
			tmpBoard.removeThrees();
			value = Double.MAX_VALUE*(-1);
			if(oppMoves.size() == 0){
				Board b = new Board(tmpBoard);
				double result = calculateOverAllHeuristics(b,  maxTurn,pos);
				return result;
			}
			for(Position iterator: oppMoves){
				Board innerBoard = new Board(tmpBoard);
				double result = alphaVbeta(innerBoard, iterator, maxTurn, !turn, depth+1, maxDepth,alpha,beta);
				value = result > value? result: value;
				alpha = value > alpha? value: alpha;
				if(beta <= alpha){
					break;
				}
			}
		}else{
			tmpBoard.UpdateBoard(turn, pos);
			tmpBoard.DisplayOptins(!turn);
			ArrayList<Position> oppMoves = tmpBoard.ListOfPosition();
			tmpBoard.removeThrees();
			value=Double.MAX_VALUE;
			if(oppMoves.size() == 0){
				Board b = new Board(tmpBoard);
				double result = calculateOverAllHeuristics(b,  maxTurn,pos);
				return result;
			}
			for(Position iterator: oppMoves){
				Board innerBoard = new Board(tmpBoard);
				double result = alphaVbeta(innerBoard, iterator, maxTurn, !turn, depth+1, maxDepth,alpha,beta);
				if(result < value){
					value=result;
				}
				value = result < value? result: value;
				beta = value < beta? value: beta;
				if(beta <= alpha){
					break;
				}
			}
		}
		
		return value;
	}
	
	public double alphaVbetaB(Board tmpBoard, Position pos,final boolean maxTurn,
			boolean turn,int depth,final int maxDepth,double alpha,double beta){
		double value;
		
		if(depth == maxDepth){
			Board b = new Board(tmpBoard);
			b.UpdateBoard(turn, pos);
			double result = calculateOverAllHeuristicsB(b,  maxTurn,pos);
			
			return result;
		}
		else if(turn == maxTurn){
			tmpBoard.UpdateBoard(turn, pos);
			tmpBoard.DisplayOptins(!turn);
			ArrayList<Position> oppMoves = tmpBoard.ListOfPosition();
			tmpBoard.removeThrees();
			value = Double.MAX_VALUE*(-1);
			if(oppMoves.size() == 0){
				Board b = new Board(tmpBoard);
				double result = calculateOverAllHeuristicsB(b,  maxTurn,pos);
				return result;
			}
			for(Position iterator: oppMoves){
				Board innerBoard = new Board(tmpBoard);
				double result = alphaVbetaB(innerBoard, iterator, maxTurn, !turn, depth+1, maxDepth,alpha,beta);
				value = result > value? result: value;
				alpha = value > alpha? value: alpha;
				if(beta <= alpha){
					break;
				}
			}
		}else{
			tmpBoard.UpdateBoard(turn, pos);
			tmpBoard.DisplayOptins(!turn);
			ArrayList<Position> oppMoves = tmpBoard.ListOfPosition();
			tmpBoard.removeThrees();
			value=Double.MAX_VALUE;
			if(oppMoves.size() == 0){
				Board b = new Board(tmpBoard);
				double result = calculateOverAllHeuristicsB(b,  maxTurn,pos);
				return result;
			}
			for(Position iterator: oppMoves){
				Board innerBoard = new Board(tmpBoard);
				double result = alphaVbetaB(innerBoard, iterator, maxTurn, !turn, depth+1, maxDepth,alpha,beta);
				if(result < value){
					value=result;
				}
				value = result < value? result: value;
				beta = value < beta? value: beta;
				if(beta <= alpha){
					break;
				}
			}
		}
		
		return value;
	}
	
	
	public boolean goodMoveb(Board b, Position p, boolean turn){
		if(turn == true){
			return true;
		}
		Board tmpBoard = new Board(b);
		tmpBoard.DisplayOptins(true);
		if(tmpBoard.getBoard()[0][0] == 3 || tmpBoard.getBoard()[0][11] == 3 || tmpBoard.getBoard()[11][0] == 3 || tmpBoard.getBoard()[11][11] == 3){
			return false;
		}
		return true;
	}
	
	public boolean goodMove(Board b, Position p, boolean turn){
		Board tmpBoard = new Board(b);
		tmpBoard.UpdateBoard(false, p);
		tmpBoard.DisplayOptins(true);
		if(tmpBoard.getBoard()[0][0] == 3 || tmpBoard.getBoard()[0][11] == 3 || tmpBoard.getBoard()[11][0] == 3 || tmpBoard.getBoard()[11][11] == 3){
			return false;
		}
		return true;
	}
	
	private double calculateOverAllHeuristics(Board tmpBoard, boolean turn,Position pos) {
		//this.o++;
		double corners_h=0,xSquares_h=0,cSquares=0,overAll=0,rSquares_h=0;
		int mark = (turn == false)? Board.BLACK : Board.WHITE;
		if(turn == false){
			tmpBoard.DisplayOptins(true);
			if(tmpBoard.getBoard()[0][0] == 3 || tmpBoard.getBoard()[0][11] == 3 || tmpBoard.getBoard()[11][0] == 3 || tmpBoard.getBoard()[11][11] == 3){
				return -50;
			}
			tmpBoard.removeThrees();
		}
		double myStones = tmpBoard.numOfStones(turn);
		double oppStones = tmpBoard.numOfStones(!turn);
		//stones count
		/*if(myStones + oppStones != 0){
			difference_h = (myStones > oppStones)?
					(myStones)/(myStones +oppStones):((-1)*oppStones)/(myStones +oppStones);
			difference_h*=144;
		}*/
		//corners
		myStones=0;oppStones=0;
		if(tmpBoard.getBoard()[0][0] == mark){
			myStones++;
		}else if(tmpBoard.getBoard()[0][0] == 3-mark){
			oppStones++;
		}
		
		if(tmpBoard.getBoard()[0][11] == mark){
			myStones++;
		}else if(tmpBoard.getBoard()[0][11] == 3-mark){
			oppStones++;
		}
		
		if(tmpBoard.getBoard()[11][0] == mark){
			myStones++;
		}else if(tmpBoard.getBoard()[11][0] == 3-mark){
			oppStones++;
		}
		
		if(tmpBoard.getBoard()[11][11] == mark){
			myStones++;
		}else if(tmpBoard.getBoard()[11][11] == 3-mark){
			oppStones++;
		}
		
		if(myStones + oppStones != 0){
			corners_h = 100*(myStones)/(myStones+oppStones);
		}
		//corners_h = 25*(myStones - oppStones);
		
		//risk near corners? x-squares
		myStones =0;oppStones=0;
		if(tmpBoard.getBoard()[1][1] == mark){
			myStones++;
		}else if(tmpBoard.getBoard()[1][1] == 3-mark){
			oppStones++;
		}
		if(tmpBoard.getBoard()[1][10] == mark){
			myStones++;
		}else if(tmpBoard.getBoard()[1][10] == 3-mark){
			oppStones++;
		}
		if(tmpBoard.getBoard()[10][1] == mark){
			myStones++;
		}else if(tmpBoard.getBoard()[10][1] == 3-mark){
			oppStones++;
		}
		if(tmpBoard.getBoard()[10][10] == mark){
			myStones++;
		}else if(tmpBoard.getBoard()[10][10] == 3-mark){
			oppStones++;
		}
		xSquares_h = (-25)*myStones + 25*oppStones;
		myStones =0;oppStones=0;
		//am I close to corners II?
		if(tmpBoard.getBoard()[0][1] == mark){//0 1
			myStones++;
		}else if(tmpBoard.getBoard()[0][1] == 3-mark){
			oppStones++;
		}
		if(tmpBoard.getBoard()[1][0] == mark){//1 0
			myStones++;
		}else if(tmpBoard.getBoard()[1][0] == 3-mark){
			oppStones++;
		}
		if(tmpBoard.getBoard()[0][10] == mark){//0 10
			myStones++;
		}else if(tmpBoard.getBoard()[0][10] == 3-mark){
			oppStones++;
		}
		if(tmpBoard.getBoard()[1][11] == mark){//1 11
			myStones++;
		}else if(tmpBoard.getBoard()[1][11] == 3-mark){
			oppStones++;
		}
		//bottom
		if(tmpBoard.getBoard()[10][0] == mark){//10 0
			myStones++;
		}else if(tmpBoard.getBoard()[10][0] == 3-mark){
			oppStones++;
		}
		if(tmpBoard.getBoard()[11][1] == mark){// 11 1
			myStones++;
		}else if(tmpBoard.getBoard()[11][1] == 3-mark){
			oppStones++;
		}
		if(tmpBoard.getBoard()[10][11] == mark){//10 11
			myStones++;
		}else if(tmpBoard.getBoard()[10][11] == 3-mark){
			oppStones++;
		}
		if(tmpBoard.getBoard()[11][10] == mark){//11 10
			myStones++;
		}else if(tmpBoard.getBoard()[11][10] == 3-mark){
			oppStones++;
		}
		
		cSquares = (-12.5)*myStones + 12.5*oppStones;
		/*****************************/
		//rSquares
		myStones=0;oppStones=0;
		
		if(tmpBoard.getBoard()[0][2] == mark){
			myStones++;
		}else if(tmpBoard.getBoard()[0][2] == 3 - mark){
			oppStones++;
			}
		
		if(tmpBoard.getBoard()[0][9] == mark){
			myStones++;
		}else if(tmpBoard.getBoard()[0][9] == 3 - mark){
			oppStones++;
			}
		
		if(tmpBoard.getBoard()[2][0] == mark){
			myStones++;
		}else if(tmpBoard.getBoard()[2][0] == 3 - mark){
			oppStones++;
			}
		
		if(tmpBoard.getBoard()[2][11] == mark){
			myStones++;
		}else if(tmpBoard.getBoard()[2][11] == 3 - mark){
			oppStones++;
			}
		
		if(tmpBoard.getBoard()[9][0] == mark){
			myStones++;
		}else if(tmpBoard.getBoard()[9][0] == 3 - mark){
			oppStones++;
			}
		
		if(tmpBoard.getBoard()[9][11] == mark){
			myStones++;
		}else if(tmpBoard.getBoard()[9][11] == 3 - mark){
			oppStones++;
			}
		
		if(tmpBoard.getBoard()[11][2] == mark){
			myStones++;
		}else if(tmpBoard.getBoard()[11][2] == 3 - mark){
			oppStones++;
			}
		
		if(tmpBoard.getBoard()[11][9] == mark){
			myStones++;
		}else if(tmpBoard.getBoard()[11][9] == 3 - mark){
			oppStones++;
			}
		
		rSquares_h = myStones >= oppStones? 10*(myStones - oppStones) : -10*(oppStones - myStones);
		/***********************/
		overAll =  ((65)*corners_h + (8.363)*xSquares_h + (3.89)*cSquares + (5.8)*rSquares_h)/4;
		double static_v = static_h[pos.getX()][pos.getY()];
		overAll+=static_v;
		//System.out.println("OverAll: " + overAll);
		/*System.out.println();
		System.out.println("#");
		System.out.println("Position: " + pos.getX() + " " + pos.getY());
		System.out.println();
		System.out.println( " corn: " +corners_h + " xSq: " +xSquares_h +" cSq: "+cSquares + " rSq: "+ rSquares_h + " stat " + static_v );
		System.out.println();*/
		return overAll;
		//return static_h[pos.getX()][pos.getY()];
	}
	
	
	private double calculateOverAllHeuristicsB(Board tmpBoard, boolean turn,Position pos) {
		return static_h[pos.getX()][pos.getY()];
	}
	
	public Position getBestMove(int[][] b, boolean turn, int maxDepth, boolean searchOp){
		Board board = new Board();
		board.setMyBoard(b);
		double maxValue = -20000;
		Position bestMove=null;
		board.DisplayOptins(turn);
		ArrayList<Position> possibleMoves = board.ListOfPosition();
		board.removeThrees();
		if(possibleMoves.size() == 0){
			return null;
		}
		if(possibleMoves.size() == 1){
			bestMove = possibleMoves.get(0);
			//board.UpdateBoard(turn, possibleMoves.get(0));
			//System.out.println();
			//System.out.println( "Computer's turn - Choice: [" + possibleMoves.get(0).getX() + "]["+possibleMoves.get(0).getY() + "]");
			//currentState();
			//System.out.println();
		}
		else{
			if(!searchOp){
				for(Position p: possibleMoves){
					Move move = new Move();
					Board evaluationBoard = new Board(board);
					double evaluation = move.minimax(evaluationBoard, p, false, false, 0, maxDepth);
					//double evaluation = move.alphaVbeta(evaluationBoard, p, turn, turn, 0, 3,(-1)*Double.MAX_VALUE,Double.MAX_VALUE);
					if(!goodMove(evaluationBoard, p, false)){
						evaluation = -1999;
					}
					if(evaluation>=maxValue){
						maxValue=evaluation;
						bestMove=p;
					}
					System.out.println();
					System.out.println("maxValue is: " + maxValue + " evaluation is: " + evaluation + " at: "+"[" + p.getX() + "]["+p.getY() + "]");
					System.out.println();
				}
			}else{
				for(Position p: possibleMoves){
					Move move = new Move();
					Board evaluationBoard = new Board(board);
					//double evaluation = move.AlphaVBeta(evaluationBoard, p, turn, turn, 0, maxDepth);
					double evaluation = move.alphaVbeta(evaluationBoard, p, false, false, 0, maxDepth,(-1)*Double.MAX_VALUE,Double.MAX_VALUE);
					if(!goodMove(evaluationBoard, p, false)){
						evaluation = -1999;
					}
					if(evaluation>=maxValue){
						maxValue=evaluation;
						bestMove=p;
					}
					System.out.println();
					System.out.println("maxValue is: " + maxValue + " evaluation is: " + evaluation + " at: "+"[" + p.getX() + "]["+p.getY() + "]");
					System.out.println();
				}
			}
		}
		System.out.println();
		System.out.println("#######################[" + bestMove.getX() + "]["+bestMove.getY() + "]###########################");
		System.out.println();
		return bestMove;
	}
	
	
	public Position getBestMoveB(int[][] b, boolean turn, int maxDepth, boolean searchOp){
		Board board = new Board();
		board.setMyBoard(b);
		double maxValue = -20000;
		Position bestMove=null;
		board.DisplayOptins(turn);
		ArrayList<Position> possibleMoves = board.ListOfPosition();
		board.removeThrees();
		if(possibleMoves.size() == 0){
			return null;
		}
		if(possibleMoves.size() == 1){
			bestMove = possibleMoves.get(0);
			//board.UpdateBoard(turn, possibleMoves.get(0));
			//System.out.println();
			//System.out.println( "Computer's turn - Choice: [" + possibleMoves.get(0).getX() + "]["+possibleMoves.get(0).getY() + "]");
			//currentState();
			//System.out.println();
		}
		else{
			if(!searchOp){
				for(Position p: possibleMoves){
					Move move = new Move();
					Board evaluationBoard = new Board(board);
					double evaluation = move.minimaxB(evaluationBoard, p, turn, turn, 0, maxDepth);
					//double evaluation = move.alphaVbeta(evaluationBoard, p, turn, turn, 0, 3,(-1)*Double.MAX_VALUE,Double.MAX_VALUE);
					
					if(evaluation>=maxValue){
						maxValue=evaluation;
						bestMove=p;
					}
					/*System.out.println();
					System.out.println("maxValue is: " + maxValue + " evaluation is: " + evaluation + " at: "+"[" + p.getX() + "]["+p.getY() + "]");
					System.out.println();*/
				}
			}else{
				for(Position p: possibleMoves){
					Move move = new Move();
					Board evaluationBoard = new Board(board);
					//double evaluation = move.AlphaVBeta(evaluationBoard, p, turn, turn, 0, maxDepth);
					double evaluation = move.alphaVbetaB(evaluationBoard, p, turn, turn, 0, maxDepth,(-1)*Double.MAX_VALUE,Double.MAX_VALUE);
					if(evaluation>=maxValue){
						maxValue=evaluation;
						bestMove=p;
					}
					/*System.out.println();
					System.out.println("maxValue is: " + maxValue + " evaluation is: " + evaluation + " at: "+"[" + p.getX() + "]["+p.getY() + "]");
					System.out.println();*/
				}
			}
		}
		/*System.out.println();
		System.out.println("#######################[" + bestMove.getX() + "]["+bestMove.getY() + "]###########################");
		System.out.println();*/
		return bestMove;
	}
	

}
