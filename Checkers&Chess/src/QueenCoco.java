public class QueenCoco extends Coco {
	//A QueenCoco is a Coco that can move in both directions with a different imageIcon (crown)
	private GUIRunnyThingy board;
	public QueenCoco(GUIRunnyThingy b, Position p) {
		super(b, p);
		setImage(b.getImage("queen"));
		board = super.getBoard();
	}
	
	//Same as Coco but row to move to can be above or below the initial piece position
	public boolean move(Square from, Square to){
		if(from.getDoggo() != null && from.getDoggo().getType().equals("queenCoco") && to.getDoggo() == null && to.getColor().equals(GUIRunnyThingy.CAROLINA_BLUE) && 
				Math.abs(from.getPosition().getRow() - to.getPosition().getRow()) == 1 && 
				Math.abs(from.getPosition().getColumn() - to.getPosition().getColumn()) == 1) {
			this.setPosition(to.getPosition());
			to.setDoggo(from.getDoggo());
			from.setDoggo(null);
			int toRow = to.getPosition().getRow();
			int toCol = to.getPosition().getColumn();
			board.getButtonArr()[toRow][toCol].getButton().setIcon(board.getImage("queen"));
			int fromRow = from.getPosition().getRow();
			int fromCol = from.getPosition().getColumn();
			board.getButtonArr()[fromRow][fromCol].getButton().setIcon(null);
			return true;
		}
		return false;
	}
	
	//Same as Coco but can jump in either direction
	public boolean jump(Square from, Square to) {
		Position startPos = from.getPosition();
		Position jumpPos = to.getPosition();
		if(from.getDoggo() != null && from.getDoggo().getType().equals("queenCoco") && to.getDoggo() == null && to.getColor().equals(GUIRunnyThingy.CAROLINA_BLUE) 
				&& Math.abs(from.getPosition().getRow() - to.getPosition().getRow()) == 2 && 
				Math.abs(from.getPosition().getColumn() - to.getPosition().getColumn()) == 2 &&
				(board.findSquareWithPos(new Position((startPos.getRow() + jumpPos.getRow())/2, (startPos.getColumn() + jumpPos.getColumn())/2)).getDoggo().getType().equals("frodo") 
				|| (board.findSquareWithPos(new Position((startPos.getRow() + jumpPos.getRow())/2, (startPos.getColumn() + jumpPos.getColumn())/2)).getDoggo().getType().equals("kingFrodo")))){
			this.setPosition(to.getPosition());
			to.setDoggo(from.getDoggo());
			from.setDoggo(null);
			int toRow = to.getPosition().getRow();
			int toCol = to.getPosition().getColumn();
			board.getButtonArr()[toRow][toCol].getButton().setIcon(board.getImage("queen"));
			int fromRow = from.getPosition().getRow();
			int fromCol = from.getPosition().getColumn();
			board.getButtonArr()[fromRow][fromCol].getButton().setIcon(null);
			board.findSquareWithPos(new Position((startPos.getRow() + jumpPos.getRow())/2, (startPos.getColumn() + jumpPos.getColumn())/2)).getButton().setIcon(null);
			board.findSquareWithPos(new Position((startPos.getRow() + jumpPos.getRow())/2, (startPos.getColumn() + jumpPos.getColumn())/2)).setDoggo(null);
			return true;
		}
		return false;
	}

	public String getType() {
		return "queenCoco";
	}	
}