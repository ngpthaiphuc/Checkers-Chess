/*
 * A Pawn is initialized with a GUI and a position like the checkers pieces. Its icon is the same as regular checkers pieces.
 * However, it also has a string identifier to determine if the Pawn is a Coco or Frodo.
 * For chess, Coco starts at the bottom and Frodo starts at the top.
 */
public class Pawn extends Piece {
	private GUIRunnyThingy board;
	private String doggo;	//"coco" vs "frodo"
	public Pawn(String doggo, GUIRunnyThingy b, Position p) {
		super(b.getImage(doggo), p);
		board = b;
		this.doggo = doggo;
	}
	
	//Accessor to determine the type of piece as well as the doggo (team) for capturing
	@Override
	public String getType() {
		return doggo + "Pawn";
	}

	@Override
	public GUIRunnyThingy getBoard() {
		return board;
	}

	//Checks to see if the path of the pawn is blocked (in front of it)
	@Override
	public boolean isBlocked(Square to) {
		Square from = board.findSquareWithPos(this.getPosition());
		//Checks if the pawn is moving straight forward (vertically)
		if(from.getPosition().getColumn() == to.getPosition().getColumn()) {
			//Coco starts on the bottom (row # increases from top to bottom)
			if(doggo.equals("coco")) {
				//Not first move -> checks for a piece right on top of the pawn
				if(from.getPosition().getRow() != 6 && to.getPosition().getRow() < from.getPosition().getRow() &&
						from.getPosition().getRow() - to.getPosition().getRow() <= 1) {
					if(board.findSquareWithPos(new Position(to.getPosition().getRow(), to.getPosition().getColumn())).getDoggo() != null) {
						return true;
					}
					return false;
				//First move! (can move up 2 spaces) -> checks accordingly
				} else if(from.getPosition().getRow() == 6 && to.getPosition().getRow() < from.getPosition().getRow() &&
						from.getPosition().getRow() - to.getPosition().getRow() <= 2) {
					if(board.findSquareWithPos(new Position(to.getPosition().getRow(), to.getPosition().getColumn())).getDoggo() != null ||
							(from.getPosition().getRow() - to.getPosition().getRow() == 2 &&
							board.findSquareWithPos(new Position(to.getPosition().getRow() + 1, to.getPosition().getColumn())).getDoggo() != null)) {
						return true;
					}
					return false;
				}
			//Frodo starts on the top
			} else if(doggo.equals("frodo")) {
				//Not first move -> checks for a piece right underneath the pawn
				if(from.getPosition().getRow() != 1 && from.getPosition().getRow() < to.getPosition().getRow() &&
						to.getPosition().getRow() - from.getPosition().getRow() <= 1) {
					if(board.findSquareWithPos(new Position(to.getPosition().getRow(), to.getPosition().getColumn())).getDoggo() != null) {
						return true;
					}
					return false;
				////First move! (can move down 2 spaces) -> checks accordingly
				} else if(from.getPosition().getRow() == 1 && from.getPosition().getRow() < to.getPosition().getRow() &&
						to.getPosition().getRow() - from.getPosition().getRow() <= 2) {
					if(board.findSquareWithPos(new Position(to.getPosition().getRow(), to.getPosition().getColumn())).getDoggo() != null ||
							(to.getPosition().getRow() - from.getPosition().getRow() == 2 && 
							board.findSquareWithPos(new Position(to.getPosition().getRow() - 1, to.getPosition().getColumn())).getDoggo() != null)) {
						return true;
					}
					return false;
				}
			}
		}
		return super.isBlocked(to);	//true
	}
	
	//Pawn can move forward (vertically) one space per turn, except for the very first move, it can move forward 2 spaces.
	@Override
	public boolean move(Square to) {
		Square from = board.findSquareWithPos(this.getPosition());
		if(!isBlocked(to)) {
			//Moves the pawn to the given square
			this.setPosition(to.getPosition());
			to.setDoggo(from.getDoggo());
			from.setDoggo(null);
			int toRow = to.getPosition().getRow();
			int toCol = to.getPosition().getColumn();
			board.getButtonArr()[toRow][toCol].getButton().setIcon(board.getImage(doggo));
			int fromRow = from.getPosition().getRow();
			int fromCol = from.getPosition().getColumn();
			board.getButtonArr()[fromRow][fromCol].getButton().setIcon(null);
			return true;
		}
		return false;
	}

	//Pawn can capture diagonally one space -> cannot capture in front of it
	@Override
	public boolean capture(Square to) {
		Square from = board.findSquareWithPos(this.getPosition());
		if(doggo.equals("coco")) {
			//For coco, checks for diagonal and moving up
			if(to.getPosition().getRow() == from.getPosition().getRow() -1 && Math.abs(to.getPosition().getColumn() - from.getPosition().getColumn()) == 1 &&
					to.getDoggo() != null && (to.getDoggo().getType().contains("frodo") || to.getDoggo().getType().equals("king"))) {
				//Removes the piece being captured
				board.removePiece(to);
				//Moves the pawn to the given square
				this.setPosition(to.getPosition());
				to.setDoggo(from.getDoggo());
				from.setDoggo(null);
				int toRow = to.getPosition().getRow();
				int toCol = to.getPosition().getColumn();
				board.getButtonArr()[toRow][toCol].getButton().setIcon(board.getImage(doggo));
				int fromRow = from.getPosition().getRow();
				int fromCol = from.getPosition().getColumn();
				board.getButtonArr()[fromRow][fromCol].getButton().setIcon(null);
				return true;
			}
		} else if(doggo.equals("frodo")) {
			//For frodo, checks for diagonal and moving down
			if(to.getPosition().getRow() == from.getPosition().getRow() +1 && Math.abs(to.getPosition().getColumn() - from.getPosition().getColumn()) == 1 &&
					to.getDoggo() != null && (to.getDoggo().getType().contains("coco") || to.getDoggo().getType().equals("queen"))) {
				//Removes the piece being captured
				board.removePiece(to);
				//Moves the pawn to the given square
				this.setPosition(to.getPosition());
				to.setDoggo(from.getDoggo());
				from.setDoggo(null);
				int toRow = to.getPosition().getRow();
				int toCol = to.getPosition().getColumn();
				board.getButtonArr()[toRow][toCol].getButton().setIcon(board.getImage(doggo));
				int fromRow = from.getPosition().getRow();
				int fromCol = from.getPosition().getColumn();
				board.getButtonArr()[fromRow][fromCol].getButton().setIcon(null);
				return true;
			}
		}
		return false;
	}

}
