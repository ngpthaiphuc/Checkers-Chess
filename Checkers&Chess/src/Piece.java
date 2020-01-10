import javax.swing.*;

public abstract class Piece {
	
	private ImageIcon doggo;	//What a piece looks like for the GUI
	private Position position;	//Current position of a piece on the board
	
	public Piece(ImageIcon img, Position p) {
		doggo = img;
		position = p;
	}
	/*
	 * All pieces have different conditions allowing them to move and jump, as well as their own types that 
	 * cannot be written until a piece is actually constructed with a type
	 * 
	 * For checkers:
	 * 		move(Square to)		-> if the piece is able to move to a square (empty, 1 space away diagonally)
	 * 		capture(Square to)	-> if a piece can jump to square and move + delete piece being jumped over
	 *  	canCapture()		-> checks if piece is able to jump to any possible squares for alternating turn
	 * 
	 * For chess:
	 * 		move(Square to)		-> specific movement based on the piece's type
	 * 		capture(Square to)	-> specific capture methods based on the piece's type -> move to square and delete piece
	 * 		canCapture()		-> chess's alternating turn has no exception (consecutive jumps in checkers) so this is not used
	 */
	
	public abstract boolean move(Square to);
	public abstract boolean capture(Square to);
	//Only for checkers's alternating turn -> not needed for chess pieces -> not an abstract method
	public boolean canCapture() {
		return false;
	}
	//Only for chess -> checks to see if the piece is blocked by another piece in the middle of its path -> not an abstract method
	public boolean isBlocked(Square to) {
		return true;
	}

	//Return the type of the piece to determine the above movement and capture methods
	public abstract String getType();
	//getBoard must be abstract because Piece is not initialized with GUIRunnyThing, but all cocos and frodos are
	public abstract GUIRunnyThingy getBoard();
	
	//Various getters and setters that are the same for all pieces
	public ImageIcon getImage() {
		return doggo;
	}
	public void setImage(ImageIcon img) {
		doggo = img;
	}
	
	public Position getPosition() {
		return position;
	}
	public void setPosition(Position p) {
		position = p;
	}
}