/*
 * A Knight is initialized with a GUI and a position like the checkers pieces. Its icon is a regular checkers piece with a knight icon.
 * However, it also has a string identifier to determine if the Pawn is a Coco or Frodo.
 * For chess, Coco starts at the bottom and Frodo starts at the top.
 */
public class Knight extends Piece {
	private GUIRunnyThingy board;
	private String doggo;	//"coco" vs "frodo"
	public Knight(String doggo, GUIRunnyThingy b, Position p) {
		super(b.getImage(doggo + "Knight"), p);
		board = b;
		this.doggo = doggo;
	}
	
	//Accessor to determine the type of piece as well as the doggo (team) for capturing
	@Override
	public String getType() {
		return doggo + "Knight";
	}

	@Override
	public GUIRunnyThingy getBoard() {
		return board;
	}

	@Override
	public boolean move(Square to) {
		if(doggo.equals("coco")) {
			
		}
		return false;
	}

	@Override
	public boolean capture(Square to) {
		// TODO Auto-generated method stub
		return false;
	}

}
