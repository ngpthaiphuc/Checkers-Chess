import javax.swing.*;

public abstract class Piece {
	
	private ImageIcon doggo;
	private String side;
	private Position position;
	
	public Piece(ImageIcon img, String s, Position p) {
		doggo = img;
		side = s;
		position = p;
	}
	
	//All pieces have different conditions allowing them to move and jump, as well as their own types that 
	//cannot be written until a piece is actually constructed with a type
	public abstract boolean move(Square from, Square to); //if the piece is able to move to a square (empty, 1 space away diagonally)
	public abstract boolean jump(Square from, Square to); //checks if piece is able to jump and sets hasPiece variables of squares
	public abstract String getType();
	//getBoard must be abstract because Piece is not initialized with a CheckersRunnyThing, but all cocos and frodos are
	public abstract GUIRunnyThingy getBoard();
	//Various getters and setters that are the same for all piece types
	public ImageIcon getImage() {
		return doggo;
	}
	
	public String getSide() {
		return side;
	}
	
	public Position getPosition() {
		return position;
	}
	
	public void setPosition(Position p) {
		position = p;
	}

	public void setImage(ImageIcon img) {
		doggo = img;
	}
	
}