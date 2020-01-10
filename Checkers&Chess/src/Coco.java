/*
 * Coco is initialized with a GUIRunnyThing as a parameter rather than assigning a new one so as to avoid errors
 * (each Coco needs to use the same GUI).
 * Coco is a piece and requires a position (separate from the square it is assigned to on the board because a piece moves while
 * a square is stationary).
 * Coco starts up top and moves down the board.
 */ 
public class Coco extends Piece {
	private GUIRunnyThingy board;
	public Coco(GUIRunnyThingy b, Position p) {
		super(b.getImage("coco"), p);
		board = b;
	}
	
	//Accessor to determine the type of piece, useful when determining whether jumping is allowed
	@Override
	public String getType() {
		return "Coco";
	}

	//Accessor to initialize QueenCoco for GUI
	@Override
	public GUIRunnyThingy getBoard() {
		return board;
	}
	
	/*
	 * By taking in board as a parameter, Coco is able to access buttonArr and alter the buttons that make up the checker board.
	 * To move a piece, the position of the piece itself is changed, the piece instance variable belonging to the 
	 * square to be moved to is assigned as the piece being moved there, and the icons are set accordingly to show correct
	 * movement of the piece.
	 * The method returns a boolean indicating whether or not a move action was successful.
	 */
	@Override
	public boolean move(Square to) {
		//Getting the square this piece is currently on
		Square from = board.findSquareWithPos(this.getPosition());
		//Checks if the positioning and piece types of each square passed allow for single spaced diagonal movement, 
		//1 row down and 1 column away in the correct direction, the square to be moved to has to be empty.
		if(from.getDoggo() != null && to.getDoggo() == null && to.getPosition().getRow() == from.getPosition().getRow() +1
				&& Math.abs(to.getPosition().getColumn() - from.getPosition().getColumn()) == 1) {
			this.setPosition(to.getPosition());
			to.setDoggo(from.getDoggo());
			from.setDoggo(null);
			int toRow = to.getPosition().getRow();
			int toCol = to.getPosition().getColumn();
			board.getButtonArr()[toRow][toCol].getButton().setIcon(board.getImage("coco"));
			int fromRow = from.getPosition().getRow();
			int fromCol = from.getPosition().getColumn();
			board.getButtonArr()[fromRow][fromCol].getButton().setIcon(null);
			return true;
		}
		return false;
	}
	
	/*
	 * Checking if starting square has a piece, ending square has no piece, and a piece of the opposite type is in the middle. 
	 * True is returned if jump operation is successful based in the conditions checked.
	 * 
	 * The doggos (pieces) in each square (from, to, and the one being jumped over) are set to their piece values after the action.
	 * Images assigned to buttons are then changed to display the results of the jump action.
	 * This method will be called in the main class if the first clicked square contains a piece of type Coco.
	 * Two if statements for 2 cases for jumping (to the left and to the right).
	 */
	@Override
	public boolean capture(Square to) {
		Position startPos = this.getPosition();
		Square from = board.findSquareWithPos(startPos);
		Position jumpPos = to.getPosition();

		//The square(s) being jumped over
		Square middleR = null;
		Square middleL = null;
		//Ensuring the squares are not out of bound of the board
		if(startPos.getRow() +1 < 8 && startPos.getColumn() +1 < 8)
			middleR = board.findSquareWithPos(new Position(startPos.getRow() +1, startPos.getColumn() +1));
		if(startPos.getRow() +1 < 8 && startPos.getColumn() -1 >= 0)
			middleL = board.findSquareWithPos(new Position(startPos.getRow() +1, startPos.getColumn() -1));
		
		//Jumping right
		if(from.getDoggo() != null && to.getDoggo() == null && startPos.getRow() +2 == jumpPos.getRow() && middleR != null &&
				startPos.getColumn() +2 == jumpPos.getColumn() && middleR.getDoggo() != null &&
				(middleR.getDoggo().getType().equals("Frodo") || middleR.getDoggo().getType().equals("King"))) {
			//Removing the piece being jumped over
			board.removePiece(middleR);
			//Jumping to the indicated square -> add new doggo to indicated square and remove existing doggo from the first square
			this.setPosition(to.getPosition());
			to.setDoggo(from.getDoggo());
			from.setDoggo(null);
			int toRow = to.getPosition().getRow();
			int toCol = to.getPosition().getColumn();
			board.getButtonArr()[toRow][toCol].getButton().setIcon(board.getImage("coco"));
			int fromRow = from.getPosition().getRow();
			int fromCol = from.getPosition().getColumn();
			board.getButtonArr()[fromRow][fromCol].getButton().setIcon(null);
			return true;
		}
		//Jumping left
		else if(from.getDoggo() != null && to.getDoggo() == null && startPos.getRow() +2  == jumpPos.getRow() && middleL != null &&
				startPos.getColumn() -2 == jumpPos.getColumn() && middleL.getDoggo() != null &&
				(middleL.getDoggo().getType().equals("Frodo") || middleL.getDoggo().getType().equals("King"))) {
			//Removing the piece being jumped over
			board.removePiece(middleL);
			//Jumping to the indicated square
			this.setPosition(to.getPosition());
			to.setDoggo(from.getDoggo());
			from.setDoggo(null);
			int toRow = to.getPosition().getRow();
			int toCol = to.getPosition().getColumn();
			board.getButtonArr()[toRow][toCol].getButton().setIcon(board.getImage("coco"));
			int fromRow = from.getPosition().getRow();
			int fromCol = from.getPosition().getColumn();
			board.getButtonArr()[fromRow][fromCol].getButton().setIcon(null);
			return true;
		}
		return false;
	}
	
	//Checking if Coco has any possible jump (for alternating turn)
	@Override
	public boolean canCapture() {
		//Coco's position and square
		Position startPos = this.getPosition();
		Square from = board.findSquareWithPos(startPos);
		
		//2 possible positions and squares that Coco can jump to
		Position jumpPosR = null;
		Position jumpPosL = null;
		Square toR = null;
		Square toL = null;
		//Ensuring the positions are not out of bound of the board
		if(startPos.getRow() +2 < 8 && startPos.getColumn() +2 < 8) 
			jumpPosR = new Position(startPos.getRow() +2, startPos.getColumn() +2);
		if(startPos.getRow() +2 < 8 && startPos.getColumn() -2 >= 0)
			jumpPosL = new Position(startPos.getRow() +2, startPos.getColumn() -2);
		if(jumpPosR != null)	toR = board.findSquareWithPos(jumpPosR);
		if(jumpPosL != null)	toL = board.findSquareWithPos(jumpPosL);
		
		//The square(s) being jumped over
		Square middleR = null;
		Square middleL = null;
		//Ensuring the squares are not out of bound of the board
		if(startPos.getRow() +1 < 8 && startPos.getColumn() +1 < 8)
			middleR = board.findSquareWithPos(new Position(startPos.getRow() +1, startPos.getColumn() +1));
		if(startPos.getRow() +1 < 8 && startPos.getColumn() -1 >= 0)
			middleL = board.findSquareWithPos(new Position(startPos.getRow() +1, startPos.getColumn() -1));
		
		//Jumping right #1
		if(from.getDoggo() != null && jumpPosR != null && toR != null && toR.getDoggo() == null &&
				startPos.getRow() +2 == jumpPosR.getRow() && middleR != null &&
				startPos.getColumn() +2 == jumpPosR.getColumn() && middleR.getDoggo() != null &&
				(middleR.getDoggo().getType().equals("Frodo") || middleR.getDoggo().getType().equals("King"))) {
			return true;
		}
		//Jumping left #2
		else if(from.getDoggo() != null && jumpPosL != null && toL != null && toL.getDoggo() == null &&
				startPos.getRow() +2  == jumpPosL.getRow() && middleL != null &&
				startPos.getColumn() -2 == jumpPosL.getColumn() && middleL.getDoggo() != null &&
				(middleL.getDoggo().getType().equals("Frodo") || middleL.getDoggo().getType().equals("King"))) {
			return true;
		}
		return super.canCapture();
	}
}