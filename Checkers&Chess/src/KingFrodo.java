public class KingFrodo extends Frodo {
	//A KingFrodo is a Frodo that can move in both directions with a different imageIcon (crown)
	private GUIRunnyThingy board;
	public KingFrodo(GUIRunnyThingy b, Position p) {
		super(b, p);
		setImage(b.getImage("king"));
		board = super.getBoard();		
	}
	
	//Used to determine the type of piece, useful when determining whether jumping is allowed
	@Override
	public String getType() {
		return "King";
	}
	
	//Same as Frodo but can move diagonally 1 square up or down
	@Override
	public boolean move(Square to){
		//Getting the square this piece is currently on
		Square from = board.findSquareWithPos(this.getPosition());
		//Checking to see if KingFrodo can move to the square indicated
		if(from.getDoggo() != null && to.getDoggo() == null &&
				Math.abs(from.getPosition().getRow() - to.getPosition().getRow()) == 1 && 
				Math.abs(from.getPosition().getColumn() - to.getPosition().getColumn()) == 1) {
			this.setPosition(to.getPosition());
			to.setDoggo(from.getDoggo());
			from.setDoggo(null);
			int toRow = to.getPosition().getRow();
			int toCol = to.getPosition().getColumn();
			board.getButtonArr()[toRow][toCol].getButton().setIcon(board.getImage("king"));
			int fromRow = from.getPosition().getRow();
			int fromCol = from.getPosition().getColumn();
			board.getButtonArr()[fromRow][fromCol].getButton().setIcon(null);
			return true;
		}
		return false;
	}
	
	//Same as Frodo but can jump in either direction
	@Override
	public boolean capture(Square to) {
		Position startPos = this.getPosition();
		Square from = board.findSquareWithPos(startPos);
		Position jumpPos = to.getPosition();
		
		//The square being jumped over
		Square middle = board.findSquareWithPos(new Position((startPos.getRow() + jumpPos.getRow()) /2,
				(startPos.getColumn() + jumpPos.getColumn()) /2));
		
		if(from.getDoggo() != null && to.getDoggo() == null && middle.getDoggo() != null &&
				Math.abs(from.getPosition().getRow() - to.getPosition().getRow()) == 2 && 
				Math.abs(from.getPosition().getColumn() - to.getPosition().getColumn()) == 2 &&
				(middle.getDoggo().getType().equals("Coco") || (middle.getDoggo().getType().equals("Queen")))){
			//Removing the piece being jumped over
			board.removePiece(middle);
			//Jumping to the indicated square -> add new doggo to indicated square and delete existing doggo from the first square
			this.setPosition(to.getPosition());
			to.setDoggo(from.getDoggo());
			from.setDoggo(null);
			int toRow = to.getPosition().getRow();
			int toCol = to.getPosition().getColumn();
			board.getButtonArr()[toRow][toCol].getButton().setIcon(board.getImage("king"));
			int fromRow = from.getPosition().getRow();
			int fromCol = from.getPosition().getColumn();
			board.getButtonArr()[fromRow][fromCol].getButton().setIcon(null);
			return true;
		}
		return false;
	}
	
	//Checking if KingFrodo has any possible jump (for alternating turn)
	@Override
	public boolean canCapture() {
		Position startPos = this.getPosition();
		Square from = board.findSquareWithPos(startPos);
		
		//4 possible positions and squares that KingFrodo can jump to (Upper Right/Left and Lower Right/Left)
		Position jumpPosUR = null;
		Position jumpPosUL = null;
		Position jumpPosLR = null;
		Position jumpPosLL = null;
		Square toUR = null;
		Square toUL = null;
		Square toLR = null;
		Square toLL = null;
		//4 possible squares being jumped over
		Square middleUR = null;
		Square middleUL = null;
		Square middleLR = null;
		Square middleLL = null;
		//Ensuring the positions are not out of bound of the board
		if(startPos.getRow() -2 >= 0 && startPos.getColumn() +2 < 8) 
			jumpPosUR = new Position(startPos.getRow() -2, startPos.getColumn() +2);
		if(startPos.getRow() -2 >= 0 && startPos.getColumn() -2 >= 0)
			jumpPosUL = new Position(startPos.getRow() -2, startPos.getColumn() -2);
		if(startPos.getRow() +2 < 8 && startPos.getColumn() +2 < 8) 
			jumpPosLR = new Position(startPos.getRow() +2, startPos.getColumn() +2);
		if(startPos.getRow() +2 < 8 && startPos.getColumn() -2 >= 0)
			jumpPosLL = new Position(startPos.getRow() +2, startPos.getColumn() -2);
		//Initializing the squares based on the possible positions
		if(jumpPosUR != null) {
			toUR = board.findSquareWithPos(jumpPosUR);
			middleUR = board.findSquareWithPos(new Position((startPos.getRow() + jumpPosUR.getRow()) /2,
					(startPos.getColumn() + jumpPosUR.getColumn()) /2));
		}
		if(jumpPosUL != null) {
			toUL = board.findSquareWithPos(jumpPosUL);
			middleUL = board.findSquareWithPos(new Position((startPos.getRow() + jumpPosUL.getRow()) /2,
					(startPos.getColumn() + jumpPosUL.getColumn()) /2));
		}
		if(jumpPosLR != null) {
			toLR = board.findSquareWithPos(jumpPosLR);
			middleLR = board.findSquareWithPos(new Position((startPos.getRow() + jumpPosLR.getRow()) /2,
					(startPos.getColumn() + jumpPosLR.getColumn()) /2));
		}
		if(jumpPosLL != null) {
			toLL = board.findSquareWithPos(jumpPosLL);
			middleLL = board.findSquareWithPos(new Position((startPos.getRow() + jumpPosLL.getRow()) /2,
					(startPos.getColumn() + jumpPosLL.getColumn()) /2));
		}
		
		//Jumping Upper Right
		if(from.getDoggo() != null && toUR != null && middleUR.getDoggo() != null && toUR.getDoggo() == null &&
				Math.abs(from.getPosition().getRow() - toUR.getPosition().getRow()) == 2 && 
				Math.abs(from.getPosition().getColumn() - toUR.getPosition().getColumn()) == 2 &&
				(middleUR.getDoggo().getType().equals("Coco") || (middleUR.getDoggo().getType().equals("Queen")))){
			return true;
		}
		//Jumping Upper Left
		if(from.getDoggo() != null && toUL != null && middleUL.getDoggo() != null && toUL.getDoggo() == null &&
				Math.abs(from.getPosition().getRow() - toUL.getPosition().getRow()) == 2 && 
				Math.abs(from.getPosition().getColumn() - toUL.getPosition().getColumn()) == 2 &&
				(middleUL.getDoggo().getType().equals("Coco") || (middleUL.getDoggo().getType().equals("Queen")))){
			return true;
		}
		//Jumping Lower Right
		if(from.getDoggo() != null && toLR != null && middleLR.getDoggo() != null && toLR.getDoggo() == null &&
				Math.abs(from.getPosition().getRow() - toLR.getPosition().getRow()) == 2 && 
				Math.abs(from.getPosition().getColumn() - toLR.getPosition().getColumn()) == 2 &&
				(middleLR.getDoggo().getType().equals("Coco") || (middleLR.getDoggo().getType().equals("Queen")))){
			return true;
		}//Jumping Lower Left
		if(from.getDoggo() != null && toLL != null && middleLL.getDoggo() != null && toLL.getDoggo() == null &&
				Math.abs(from.getPosition().getRow() - toLL.getPosition().getRow()) == 2 && 
				Math.abs(from.getPosition().getColumn() - toLL.getPosition().getColumn()) == 2 &&
				(middleLL.getDoggo().getType().equals("Coco") || (middleLL.getDoggo().getType().equals("Queen")))){
			return true;
		}
		return false;
	}
}