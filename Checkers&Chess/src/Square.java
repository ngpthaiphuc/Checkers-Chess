import javax.swing.*;
import java.awt.*;

public class Square{

	private Color color;
	private Piece doggo;
	private Position position;
	private JButton button;
	
	/* 
	 * A square is initialized with variables indicating the piece (doggo) present at the location of the square, the position of the square itself on the checkerboard,
	 * the button used to determine whether the square has been interacted with and actions to perform, the button the square is associated with, 
	 * and the color of a square.
	 */
	public Square(Piece d, Color c, Position p, JButton b){
		color = c;
		doggo = d;
		position = p;
		button = b;
	}
	
	//Getter and setter methods to be called in other classes
	public void setPosition(Position i) {
		position = i;
	}
	
	public Position getPosition() {
		return position;
	}
	
	public void setColor(Color c) {
		color = c;
	}
	
	public void setDoggo(Piece p) {
		doggo = p;
	}
	
	public Piece getDoggo() {
		return doggo;
	}
	
	public Color getColor(){
		return color;
	}
	
	public JButton getButton() {
		return button;
	}
}