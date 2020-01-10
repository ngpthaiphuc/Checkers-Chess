/*
 * A King is initialized with a GUI and a position like the checkers pieces. Its icon is a regular checkers piece with a king icon.
 * However, it also has a string identifier to determine if the Pawn is a Coco or Frodo.
 * For chess, Coco starts at the bottom and Frodo starts at the top.
 */
public class King extends Piece {
	private GUIRunnyThingy board;
	private String doggo;	//Coco -> queen vs Frodo -> king
	public King(String doggo, GUIRunnyThingy b, Position p) {
		super(b.getImage(doggo), p);
		board = b;
		this.doggo = doggo;
	}
	
	//Accessor to determine the type of piece as well as the doggo (team) for capturing
	@Override
	public String getType() {
		return doggo;
	}

	@Override
	public GUIRunnyThingy getBoard() {
		return board;
	}

	@Override
	public boolean move(Square to) {
		if(doggo.equals("queen")) {
			
		}
		return false;
	}

	@Override
	public boolean capture(Square to) {
		// TODO Auto-generated method stub
		return false;
	}

}
