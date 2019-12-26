import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUIRunnyThingy extends JPanel{
	
	/*
	 * The 8x8 double array of Square, buttonArr, was used to make a board of buttons. Each square in the double array was
	 * initialize with its color alternating in checkers pattern. A piece moves by pressing on the piece then the desire location. 
	 */
	private Square[][] buttonArr;
	private static int HEIGHT;
	public static Color CAROLINA_BLUE, BLACK;
	public boolean isCheckers;
	
	public int count;
	//A temporary variable Square that is used to store the first square clicked (the piece that is going to be moved)
	public Square from;
	public boolean moved;
	public boolean isFrodoTurn;
	
	//The images used for the pieces and for turn buttons (drawn by Joi Zooo)
	String imageDirectory;
	public ImageIcon frodo, coco, kingFrodo, queenCoco, frodoTurn, cocoTurn;

	/*
	 * Setting the layout of the JPanel to an 8x8 grid layout with 1 pixels spacing
	 * 
	 * 3 Events are declared and initialized for when a button is pressed
	 * 
	 * The for loop is used to initialized each square in buttonArr to its corresponding piece, position, color, and button icon.
	 * Additionally, it sets the size of all buttons, its background color, and adds actionListener on all carolina blue buttons.
	 * Furthermore, 2 black buttons also have actionListener for special functions (see the corresponding event)
	 */
	
	/*
	 * 
	 */
	public void setPieces() {
		if(isCheckers) {
			frodo = new ImageIcon(imageDirectory + "Frodo.png");
			coco = new ImageIcon(imageDirectory + "Coco.png");
			kingFrodo = new ImageIcon(imageDirectory + "KingFrodo.png");
			queenCoco = new ImageIcon(imageDirectory + "QueenCoco.png");
		}
	}
	
	//Accessor of the images of the pieces
	public ImageIcon getImage(String piece) {
		return piece.equals("frodo") ? frodo : piece.equals("coco") ? coco : piece.equals("king") ? kingFrodo : piece.equals("queen") ? queenCoco : null;
	}
	
	//Constructor
	public GUIRunnyThingy() {
		//8x8 board of Square
		buttonArr = new Square[8][8];
		//The height of the computer's screen
		HEIGHT = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight() - 50;
		//The two colors that is used by buttonArr for the alternating color pattern
		CAROLINA_BLUE = new Color(75, 156, 211);
		BLACK = new Color(0, 0, 0);
		//Whether checkers or chess is selected
		isCheckers = true;
		
		//Keep track of the clicks in pairs, for each pair of click represents a move
		count = 0;
		//Determine whether or not a piece has successfully made a legal move
		moved = false;
		//Determine whether or not it is Frodo's or Coco's turn.
		isFrodoTurn = true;
		
		imageDirectory = "images\\";
		setPieces();
		
		frodoTurn = new ImageIcon(imageDirectory + "DownArrow.png");		
		Image down = frodoTurn.getImage();
		Image scaledF = down.getScaledInstance(1000/8, 1000/8, java.awt.Image.SCALE_SMOOTH);
		frodoTurn = new ImageIcon(scaledF);
		
		cocoTurn = new ImageIcon(imageDirectory + "UpArrow.png");
		Image up = cocoTurn.getImage();
		Image scaledC = up.getScaledInstance(1000/8, 1000/8, java.awt.Image.SCALE_SMOOTH);
		cocoTurn = new ImageIcon(scaledC);
		
		setLayout(new GridLayout(8, 8, 1, 1));
		
		MoveEvent moveDoggo = new MoveEvent();
		ResetEvent resetCount = new ResetEvent();
		NextTurnEvent nextTurn = new NextTurnEvent();
		
		for(int r = 0; r < 8; r++) {
			for(int c = 0; c < 8; c++) {
				//buttonArr[r][c] = new JButton();
				if(((r+1) % 2 == 0 && (c+1) % 2 == 0) || ((r+1) % 2 == 1 && (c+1) % 2 == 1)) {
					if(r < 3) {
						buttonArr[r][c] = new Square(new Coco(this, new Position(r, c)), CAROLINA_BLUE, new Position(r, c), new JButton(coco));
					} else if(r > 4){
						buttonArr[r][c] = new Square(new Frodo(this, new Position(r, c)), CAROLINA_BLUE, new Position(r, c), new JButton(frodo));
					} else {
						buttonArr[r][c] = new Square(null, CAROLINA_BLUE, new Position(r, c), new JButton());
					}
				} else {
					buttonArr[r][c] = new Square(null, BLACK, new Position(r, c), new JButton());
				}
				buttonArr[r][c].getButton().setSize(1000/8, 1000/8);	//Set button size to a square
				
				if(((r+1) % 2 == 0 && (c+1) % 2 == 0) || ((r+1) % 2 == 1 && (c+1) % 2 == 1))
					buttonArr[r][c].getButton().setBackground(buttonArr[r][c].getColor());	//Set button's color to Carolina Blue
				else
					buttonArr[r][c].getButton().setBackground(buttonArr[r][c].getColor());	//Set button's color to Black
				
				add(buttonArr[r][c].getButton());	//Add buttons to the panel
				
				if(buttonArr[r][c].getColor().equals(CAROLINA_BLUE)){
					buttonArr[r][c].getButton().addActionListener(moveDoggo);
				}
			}	
		}
		
		buttonArr[0][7].getButton().addActionListener(resetCount);
		buttonArr[7][0].getButton().setIcon(frodoTurn);
		buttonArr[7][0].getButton().addActionListener(nextTurn);
	}
	
	//Accessor method for buttonArr
	public Square[][] getButtonArr(){
		return buttonArr;
	}
	
	//Returns the square based on a given position
	public Square findSquareWithPos(Position p) {
		return buttonArr[p.getRow()][p.getColumn()];
	}
	
	//Removes a piece and its button icon when it is taken
	public void removePiece(Piece p) {
		int row = p.getPosition().getRow();
		int col = p.getPosition().getColumn();
		buttonArr[row][col].getButton().setIcon(null);
		p = null;
	}
	
	//CheckersRunnyThing is initialized here using JFrame
	public static void main(String[] args) {
		JFrame gui = new JFrame();
		GUIRunnyThingy g = new GUIRunnyThingy();
		gui.setContentPane(g);
		gui.setSize(HEIGHT, HEIGHT);
		gui.setLocationRelativeTo(null);					//sets the window position in the middle of the screen
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	//closes the window when the exit buttons is pressed
		gui.setVisible(true);								//ensures that the window is visible
		//gui.getContentPane().setBackground("C:/Users/Le Nguyen/Pictures/Saved Pictures/doggo background.JPG");
		gui.setTitle("Coco vs Frodo");						//sets the title of the window
	}
	
	/*
	 * When a button is pressed for the first time, it is stored into the Square variable "from". The second time the button is pressed,
	 * if from has a piece(doggo), then the code will check if the piece can move or jump over an opponent's piece(s). Moreover,
	 * if a top/bottom button on the board is pressed, the piece will be set to its corresponding King/Queen child class as long as
	 * the move is legal. If a piece cannot move, then the piece can check whether or not it can jump. If not, everything resets.
	 */
	public class MoveEvent implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			for(int r = 0; r < 8; r++) {
				for(int c = 0; c < 8; c++) {
					if((JButton)e.getSource() == buttonArr[r][c].getButton()) {
						count++;
						if(count == 1) {
							from = buttonArr[r][c];
						}
						if(count == 2) {
							if(from.getDoggo() != null) { 
								if(buttonArr[r][c].getPosition().getRow() == 7 && from.getDoggo().getType().equals("coco")) {
									//queenCoco
									from.setDoggo(new QueenCoco(from.getDoggo().getBoard(), new Position(r, c)));
									System.out.println("Yip");
								} else if(buttonArr[r][c].getPosition().getRow() == 0 && from.getDoggo().getType().equals("frodo")) {
									//kingFrodo
									from.setDoggo(new KingFrodo(from.getDoggo().getBoard(), new Position(r, c)));
									System.out.println("Bark");
								}
								if(isFrodoTurn) {
									System.out.println(isFrodoTurn);
									if(from.getDoggo().getType().equals("frodo") || (from.getDoggo().getType().equals("kingFrodo"))) {
										moved = from.getDoggo().move(from, buttonArr[r][c]);
										System.out.println("Frodo Move: " + moved);
										if(!moved) {
											from.setDoggo(new Frodo(from.getDoggo().getBoard(), new Position(r, c)));
											System.out.println("Sad Bark");
										}
									} else {
										System.out.println("Illegal Move");
									}
								} else if(from.getDoggo().getType().equals("coco") || (from.getDoggo().getType().equals("queenCoco"))){
									moved = from.getDoggo().move(from, buttonArr[r][c]);
									System.out.println("Coco Move: " + moved);
									if(!moved) {
										from.setDoggo(new Coco(from.getDoggo().getBoard(), new Position(r, c)));
										System.out.println("Sad Yip");
									}
								} else {
									System.out.println("Illegal Move");
								}
							} else {
								from = null;
							}
							if(!moved) {
								if (from != null) {
									System.out.println("Jump: " + from.getDoggo().jump(from, buttonArr[r][c]));
								}
							}
							count = 0;
						}
					}
				}
			}
/*			} catch (RuntimeException ex) {
				ex.printStackTrace();
			}*/
		}
	}
	
	/*
	 * Count is used to keep track of the pair of clicks. If a misclick occurs and messes up count,
	 * by pressing the top right button, count will be reset and the game can resume.
	 */
	public class ResetEvent implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			count = 0;
			System.out.println("Count was Reset");
		}
	}
	
	/*
	 * After a move is made, the player can press the bottom left button to indicate the end of their turn.
	 * The arrow will point up for Coco's turn and point down for Frodo's turn. The pieces will not be able
	 * to move/jump if it is not their turn.
	 */
	public class NextTurnEvent implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			isFrodoTurn = !isFrodoTurn;
			if(isFrodoTurn == true) {
				buttonArr[7][0].getButton().setIcon(frodoTurn);
				System.out.println("Frodo Turn");
			} else {
				buttonArr[7][0].getButton().setIcon(cocoTurn);
				System.out.println("Coco Turn");
			}
		}
	}
}