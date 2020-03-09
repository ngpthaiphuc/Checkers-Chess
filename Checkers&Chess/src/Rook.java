/*
 * A Rook is initialized with a GUI and a position like the checkers pieces. Its icon is a regular checkers piece with a rook icon.
 * However, it also has a string identifier to determine if the Pawn is a Coco or Frodo.
 * For chess, Coco starts at the bottom and Frodo starts at the top.
 */
public class Rook extends Piece {
	private GUIRunnyThingy board;
	private String doggo;	//"coco" vs "frodo"
	public Rook(String doggo, GUIRunnyThingy b, Position p) {
		super(b.getImage(doggo + "Rook"), p);
		board = b;
		this.doggo = doggo;
	}
	
	//Accessor to determine the type of piece as well as the doggo (team) for capturing
	@Override
	public String getType() {
		return doggo + "Rook";
	}

	@Override
	public GUIRunnyThingy getBoard() {
		return board;
	}
	
	//Checks to see if the path of the rook is blocked, exclusive of the square it is moving to (horizontal and vertical)
	@Override
	public boolean isBlocked(Square to) {
		Square from = board.findSquareWithPos(this.getPosition());
		//Horizontal movement
		if(to.getPosition().getRow() == from.getPosition().getRow()) {
			if(to.getPosition().getColumn() > from.getPosition().getColumn()) {
				for(int i = from.getPosition().getColumn() +1; i < to.getPosition().getColumn(); i++) {
					if(board.findSquareWithPos(new Position(from.getPosition().getRow(), i)).getDoggo() != null)
						return true;
				}
			} else if(to.getPosition().getColumn() < from.getPosition().getColumn()) {
				for(int i = to.getPosition().getColumn() +1; i < from.getPosition().getColumn(); i++) {
					if(board.findSquareWithPos(new Position(from.getPosition().getRow(), i)).getDoggo() != null)
						return true;
				}
			}
			return false;
		//Vertical movement
		} else if(to.getPosition().getColumn() == from.getPosition().getColumn()) {
			if(to.getPosition().getRow() > from.getPosition().getRow()) {
				for(int i = from.getPosition().getRow() +1; i < to.getPosition().getRow(); i++) {
					if(board.findSquareWithPos(new Position(i, from.getPosition().getColumn())).getDoggo() != null)
						return true;
				}
			} else if(to.getPosition().getRow() < from.getPosition().getRow()) {
				for(int i = to.getPosition().getRow() +1; i < from.getPosition().getRow(); i++) {
					if(board.findSquareWithPos(new Position(i, from.getPosition().getColumn())).getDoggo() != null)
						return true;
				}
			}
			return false;
		}
		return super.isBlocked(to);	//true
	}

	//Rook can move horizontally or vertically infinitely as long as there are nothing blocking its path.
	@Override
	public boolean move(Square to) {
		Square from = board.findSquareWithPos(this.getPosition());
		if(to.getDoggo() == null && !isBlocked(to)) {
			//Moves the rook to the given square
			this.setPosition(to.getPosition());
			to.setDoggo(from.getDoggo());
			from.setDoggo(null);
			int toRow = to.getPosition().getRow();
			int toCol = to.getPosition().getColumn();
			board.getButtonArr()[toRow][toCol].getButton().setIcon(board.getImage(doggo + "Rook"));
			int fromRow = from.getPosition().getRow();
			int fromCol = from.getPosition().getColumn();
			board.getButtonArr()[fromRow][fromCol].getButton().setIcon(null);
			return true;
		}
		return false;
	}

	//Rook's capture is the same as its movement
	@Override
	public boolean capture(Square to) {
		Square from = board.findSquareWithPos(this.getPosition());
		if(doggo.equals("coco")) {
			if(to.getDoggo() != null && (to.getDoggo().getType().contains("frodo") || to.getDoggo().getType().equals("king")) && !isBlocked(to)) {
				//Removes the piece being captured
				board.removePiece(to);
				//Moves the rook to the given square
				this.setPosition(to.getPosition());
				to.setDoggo(from.getDoggo());
				from.setDoggo(null);
				int toRow = to.getPosition().getRow();
				int toCol = to.getPosition().getColumn();
				board.getButtonArr()[toRow][toCol].getButton().setIcon(board.getImage(doggo + "Rook"));
				int fromRow = from.getPosition().getRow();
				int fromCol = from.getPosition().getColumn();
				board.getButtonArr()[fromRow][fromCol].getButton().setIcon(null);
				return true;
			}
		} else if(doggo.equals("frodo")) {
			if(to.getDoggo() != null && (to.getDoggo().getType().contains("coco") || to.getDoggo().getType().equals("queen")) && !isBlocked(to)) {
				//Removes the piece being captured
				board.removePiece(to);
				//Moves the rook to the given square
				this.setPosition(to.getPosition());
				to.setDoggo(from.getDoggo());
				from.setDoggo(null);
				int toRow = to.getPosition().getRow();
				int toCol = to.getPosition().getColumn();
				board.getButtonArr()[toRow][toCol].getButton().setIcon(board.getImage(doggo + "Rook"));
				int fromRow = from.getPosition().getRow();
				int fromCol = from.getPosition().getColumn();
				board.getButtonArr()[fromRow][fromCol].getButton().setIcon(null);
				return true;
			}
		}
		return false;
	}

}
