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
