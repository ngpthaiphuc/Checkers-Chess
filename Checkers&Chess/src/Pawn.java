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
		
		if(from.getPosition().getColumn() == to.getPosition().getColumn()) {
			if(doggo.equals("coco")) {
				if(from.getPosition().getRow() != 6 && from.getPosition().getRow() - to.getPosition().getRow() <= 1) {
					if(board.findSquareWithPos(new Position(to.getPosition().getRow(), to.getPosition().getColumn())) != null) {
						return true;
					}
				} else if(from.getPosition().getRow() == 6 && from.getPosition().getRow() - to.getPosition().getRow() <= 2) {
					if(board.findSquareWithPos(new Position(to.getPosition().getRow(), to.getPosition().getColumn())) != null) {
						return true;
					}
				}
				return false;
			} else if(doggo.equals("frodo")) {
				if(from.getPosition().getRow() != 1 && to.getPosition().getRow() - from.getPosition().getRow() <= 1) {
					if(board.findSquareWithPos(new Position(to.getPosition().getRow(), to.getPosition().getColumn())) != null) {
						return true;
					}
				} else if(from.getPosition().getRow() == 1 && to.getPosition().getRow() - from.getPosition().getRow() <= 2) {
					if(board.findSquareWithPos(new Position(to.getPosition().getRow(), to.getPosition().getColumn())) != null) {
						return true;
					}
				}
				return false;
			}
		}
		return super.isBlocked(to);
	}
	
	@Override
	public boolean move(Square to) {
		Square from = board.findSquareWithPos(this.getPosition());
		
		if(from.getDoggo() != null && to.getDoggo() == null && to.getPosition().getColumn() == from.getPosition().getColumn() && !isBlocked(to)) {
			if((from.getPosition().getRow() != 6 && from.getPosition().getRow() - to.getPosition().getRow() <= 1) || 
					(from.getPosition().getRow() == 6 && from.getPosition().getRow() - to.getPosition().getRow() <= 2)) {
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

	@Override
	public boolean capture(Square to) {
		// TODO Auto-generated method stub
		return false;
	}

}
