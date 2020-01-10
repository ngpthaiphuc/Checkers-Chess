/*
 * Same as Coco!
 * Frodo starts at the bottom and moves up the board.
 */ 
public class Frodo extends Piece{
	
	private GUIRunnyThingy board;
	public Frodo(GUIRunnyThingy b, Position p) {
		super(b.getImage("frodo"), p);
		board = b;
	}
	
	//Accessor to determine the type of piece, useful when determining whether jumping is allowed
	@Override
	public String getType() {
		return "Frodo";
	}
	
	//Accessor to initialize KingFrodo for GUI
	@Override
	public GUIRunnyThingy getBoard() {
		return board;
	}
	
	//Same as Coco, except moving up
	@Override
	public boolean move(Square to) {
		//Getting the square this piece is currently on
		Square from = board.findSquareWithPos(this.getPosition());
		//Checks if the positioning and piece types of each square passed allow for single spaced diagonal movement, 
		//1 row up and 1 column away in the correct direction, the square to be moved to has to be empty.
		if(from.getDoggo() != null && to.getDoggo() == null && to.getPosition().getRow() ==  from.getPosition().getRow() -1 &&
				Math.abs(to.getPosition().getColumn() - from.getPosition().getColumn()) == 1) {
			this.setPosition(to.getPosition());
			to.setDoggo(from.getDoggo());
			from.setDoggo(null);
			int toRow = to.getPosition().getRow();
			int toCol = to.getPosition().getColumn();
			board.getButtonArr()[toRow][toCol].getButton().setIcon(board.getImage("frodo"));
			int fromRow = from.getPosition().getRow();
			int fromCol = from.getPosition().getColumn();
			board.getButtonArr()[fromRow][fromCol].getButton().setIcon(null);
			return true;
		}
		return false;
	}
	
	//Same as Coco, except jumping up
	@Override
	public boolean capture(Square to) {
		Position startPos = this.getPosition();
		Square from = board.findSquareWithPos(startPos);
		Position jumpPos = to.getPosition();
		
		//The square(s) being jumped over
		Square middleR = null;
		Square middleL = null;
		//Ensuring the squares are not out of bound of the board
		if(startPos.getRow() -1 >= 0 && startPos.getColumn() +1 < 8)
			middleR = board.findSquareWithPos(new Position(startPos.getRow() -1, startPos.getColumn() +1));
		if(startPos.getRow() -1 >= 0 && startPos.getColumn() -1 >= 0)
			middleL = board.findSquareWithPos(new Position(startPos.getRow() -1, startPos.getColumn() -1));
		
		//Jumping right
		if(from.getDoggo() != null && to.getDoggo() == null && startPos.getRow() -2 == jumpPos.getRow() && middleR != null &&
				startPos.getColumn() +2 == jumpPos.getColumn() && middleR.getDoggo() != null &&
				(middleR.getDoggo().getType().equals("Coco") || middleR.getDoggo().getType().equals("Queen"))) {
			//Removing the piece being jumped over
			board.removePiece(middleR);
			//Jumping to the indicated square -> add new doggo to indicated square and remove existing doggo from the first square
			this.setPosition(to.getPosition());
			to.setDoggo(from.getDoggo());
			from.setDoggo(null);
			int toRow = to.getPosition().getRow();
			int toCol = to.getPosition().getColumn();
			board.getButtonArr()[toRow][toCol].getButton().setIcon(board.getImage("frodo"));
			int fromRow = from.getPosition().getRow();
			int fromCol = from.getPosition().getColumn();
			board.getButtonArr()[fromRow][fromCol].getButton().setIcon(null);
			return true;
		}
		//Jumping left
		else if(from.getDoggo() != null && to.getDoggo() == null && startPos.getRow() -2  == jumpPos.getRow() && middleL != null &&
				startPos.getColumn() -2 == jumpPos.getColumn() && middleL.getDoggo() != null &&
				(middleL.getDoggo().getType().equals("Coco") || middleL.getDoggo().getType().equals("Queen"))) {
			//Removing the piece being jumped over
			board.removePiece(middleL);
			//Jumping to the indicated square
			this.setPosition(to.getPosition());
			to.setDoggo(from.getDoggo());
			from.setDoggo(null);
			int toRow = to.getPosition().getRow();
			int toCol = to.getPosition().getColumn();
			board.getButtonArr()[toRow][toCol].getButton().setIcon(board.getImage("frodo"));
			int fromRow = from.getPosition().getRow();
			int fromCol = from.getPosition().getColumn();
			board.getButtonArr()[fromRow][fromCol].getButton().setIcon(null);
			return true;
		}
		return false;
	}

	//Checking if Frodo has any possible jump (for alternating turn)
	@Override
	public boolean canCapture() {
		//Frodo's position and square
		Position startPos = this.getPosition();
		Square from = board.findSquareWithPos(startPos);
		
		//2 possible positions and squares that Frodo can jump to
		Position jumpPosR = null;
		Position jumpPosL = null;
		Square toR = null;
		Square toL = null;
		//Ensuring the positions are not out of bound of the board
		if(startPos.getRow() -2 >= 0 && startPos.getColumn() +2 < 8) 
			jumpPosR = new Position(startPos.getRow() -2, startPos.getColumn() +2);
		if(startPos.getRow() -2 >= 0 && startPos.getColumn() -2 >= 0)
			jumpPosL = new Position(startPos.getRow() -2, startPos.getColumn() -2);
		if(jumpPosR != null)	toR = board.findSquareWithPos(jumpPosR);
		if(jumpPosL != null)	toL = board.findSquareWithPos(jumpPosL);
		
		//The square(s) being jumped over
		Square middleR = null;
		Square middleL = null;
		//Ensuring the squares are not out of bound of the board
		if(startPos.getRow() -1 >= 0 && startPos.getColumn() +1 < 8)
			middleR = board.findSquareWithPos(new Position(startPos.getRow() -1, startPos.getColumn() +1));
		if(startPos.getRow() -1 >= 0 && startPos.getColumn() -1 >= 0)
			middleL = board.findSquareWithPos(new Position(startPos.getRow() -1, startPos.getColumn() -1));
		
		//Jumping right
		if(from.getDoggo() != null && jumpPosR != null && toR != null && toR.getDoggo() == null &&
				startPos.getRow() -2 == jumpPosR.getRow() && middleR != null &&
				startPos.getColumn() +2 == jumpPosR.getColumn() && middleR.getDoggo() != null &&
				(middleR.getDoggo().getType().equals("Coco") || middleR.getDoggo().getType().equals("Queen"))) {
			return true;
		}
		//Jumping left
		else if(from.getDoggo() != null && jumpPosL != null && toL != null && toL.getDoggo() == null &&
				startPos.getRow() -2  == jumpPosL.getRow() && middleL != null &&
				startPos.getColumn() -2 == jumpPosL.getColumn() && middleL.getDoggo() != null &&
				(middleL.getDoggo().getType().equals("Coco") || middleL.getDoggo().getType().equals("Queen"))) {
			return true;
		}
		return super.canCapture();
	}
}