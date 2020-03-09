/*
 * A Bishop is initialized with a GUI and a position like the checkers pieces. Its icon is a regular checkers piece with a bishop icon.
 * However, it also has a string identifier to determine if the Pawn is a Coco or Frodo.
 * For chess, Coco starts at the bottom and Frodo starts at the top.
 */
public class Bishop extends Piece {
	private GUIRunnyThingy board;
	private String doggo;	//"coco" vs "frodo"
	public Bishop(String doggo, GUIRunnyThingy b, Position p) {
		super(b.getImage(doggo + "Bishop"), p);
		board = b;
		this.doggo = doggo;
	}
	
	//Accessor to determine the type of piece as well as the doggo (team) for capturing
	@Override
	public String getType() {
		return doggo + "Bishop";
	}

	@Override
	public GUIRunnyThingy getBoard() {
		return board;
	}
	
	//Oof this is just wrong
	@Override
	public boolean isBlocked(Square to) {
		Square from = board.findSquareWithPos(this.getPosition());

		if(Math.abs(to.getPosition().getRow() - from.getPosition().getRow()) == Math.abs(to.getPosition().getColumn() - from.getPosition().getColumn())) {
			if(to.getPosition().getRow() > from.getPosition().getRow()) {
				//Bottom Left
				if(to.getPosition().getColumn() < from.getPosition().getColumn()) {
					for(int i = 1; i < to.getPosition().getRow() - from.getPosition().getRow(); i++) {
						if(board.findSquareWithPos(new Position(to.getPosition().getRow() -i, to.getPosition().getColumn() +i)).getDoggo() != null)
							return true;
					}
					return false;
				}
				//Bottom Right
				if(to.getPosition().getColumn() > from.getPosition().getColumn()) {
					for(int i = 1; i < to.getPosition().getRow() - from.getPosition().getRow(); i++) {
						if(board.findSquareWithPos(new Position(to.getPosition().getRow() -i, to.getPosition().getColumn() -i)).getDoggo() != null)
							return true;
					}
					return false;
				}
				
			} else if(to.getPosition().getRow() < from.getPosition().getRow()) {
				//Top Left
				if(to.getPosition().getColumn() < from.getPosition().getColumn()) {
					for(int i = 1; i < from.getPosition().getRow() - to.getPosition().getRow(); i++) {
						if(board.findSquareWithPos(new Position(to.getPosition().getRow() +i, to.getPosition().getColumn() +i)).getDoggo() != null)
							return true;
					}
					return false;
				}
				//Top Right
				if(to.getPosition().getColumn() > from.getPosition().getColumn()) {
					for(int i = 1; i < from.getPosition().getRow() - to.getPosition().getRow(); i++) {
						if(board.findSquareWithPos(new Position(to.getPosition().getRow() +i, to.getPosition().getColumn() -i)).getDoggo() != null) {
							return true;
						}
					}
					return false;
				}
			}
		}
		
		return super.isBlocked(to);	//true
	}

	@Override
	public boolean move(Square to) {
		Square from = board.findSquareWithPos(this.getPosition());
		if(to.getDoggo() == null && !isBlocked(to)) {
			//Moves the pawn to the given square
			this.setPosition(to.getPosition());
			to.setDoggo(from.getDoggo());
			from.setDoggo(null);
			int toRow = to.getPosition().getRow();
			int toCol = to.getPosition().getColumn();
			board.getButtonArr()[toRow][toCol].getButton().setIcon(board.getImage(doggo + "Bishop"));
			int fromRow = from.getPosition().getRow();
			int fromCol = from.getPosition().getColumn();
			board.getButtonArr()[fromRow][fromCol].getButton().setIcon(null);
			return true;
		}
		return false;
	}

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
				board.getButtonArr()[toRow][toCol].getButton().setIcon(board.getImage(doggo + "Bishop"));
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
				board.getButtonArr()[toRow][toCol].getButton().setIcon(board.getImage(doggo + "Bishop"));
				int fromRow = from.getPosition().getRow();
				int fromCol = from.getPosition().getColumn();
				board.getButtonArr()[fromRow][fromCol].getButton().setIcon(null);
				return true;
			}
		}
		return false;
	}

}
