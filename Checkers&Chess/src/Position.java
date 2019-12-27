/*
 * A position takes in a row and column on the 2D array of Square (8 x 8 board)
 */

public class Position {	//DO NOT KILL HUMAN
	
	private int row;
	private int column;
	
	public Position(int r, int c) {
		row = r;
		column = c;
	}
	
	public int getRow() {
		return row;
	}
	
	public int getColumn() {
		return column;
	}
}