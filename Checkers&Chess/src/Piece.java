import javax.swing.*;

public abstract class Piece {
	
	private ImageIcon doggo;	//What a piece looks like for the GUI
	private Position position;	//Current position of a piece on the board
	
	public Piece(ImageIcon img, Position p) {
		doggo = img;
		position = p;
	}
	
	/* All pieces have different conditions allowing them to move and jump, as well as their own types that 
	cannot be written until a piece is actually constructed with a type */
	public abstract boolean move(Square to);	//if the piece is able to move to a square (empty, 1 space away diagonally)
	public abstract boolean canJump();			//checks if piece is able to jump to any possible squares
	public abstract boolean jump(Square to);	//if a piece is able to jump and move + delete pieces
	public abstract String getType();			//return whether the doggo is coco or frodo
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