import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUIRunnyThingy extends JFrame{
	
	/*
	 * The 8x8 double array of Square, buttonArr, was used to make a board of buttons. Each square in the double array was
	 * initialize with its color alternating in checkers pattern. A piece moves by pressing on the piece then the desire location. 
	 */
	private Square[][] buttonArr;
	public Square[][] getButtonArr(){
		return buttonArr;
	}
	
	private boolean isCheckers;
	private static int HEIGHT, WIDTH;
	private static Color CAROLINA_BLUE, BLACK;
	
	private int clickCount, cocoCount, frodoCount;
	//A temporary variable Square that is used to store the first square clicked (the piece that is going to be moved)
	private Square from;
	private boolean isFrodoTurn;
	
	//The images used for the pieces and for turn buttons (drawn by Joi Zooo)
	private String imageDirectory;
	private ImageIcon frodo, coco, kingFrodo, queenCoco, frodoTurn, cocoTurn;
	
	//GUI Components Declaration
	private JPanel mainLayout, boardLayout, leftLayout, rightLayout;
	private JLabel chooseMode, turnUpdate, liveUpdate, cocoScore, frodoScore;
	private JRadioButton checkersButton, chessButton;
	private ButtonGroup radialGroup;
	
	//Constructor -> Declarations
	public GUIRunnyThingy() {
		//8x8 board
		buttonArr = new Square[8][8];
		//Whether checkers or chess is selected (checkers is default)
		isCheckers = true;
		//The height and width of the computer's screen
		HEIGHT = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight() - 30;
		WIDTH = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		//The two colors that is used by the board for the alternating color pattern
		CAROLINA_BLUE = new Color(75, 156, 211);
		BLACK = new Color(0, 0, 0);
		
		//Keep track of the clicks in pairs, for each pair of click represents a move
		clickCount = 0;
		//Determine whether or not it is Frodo's or Coco's turn.
		isFrodoTurn = true;
		
		//Setting and scaling images for all the pieces from the "images" folder
		imageDirectory = "images\\";
		if(isCheckers) {
			//Images for checkers
			frodo = new ImageIcon(imageDirectory + "Frodo.png");
			coco = new ImageIcon(imageDirectory + "Coco.png");
			kingFrodo = new ImageIcon(imageDirectory + "KingFrodo.png");
			queenCoco = new ImageIcon(imageDirectory + "QueenCoco.png");
			frodoTurn = new ImageIcon(imageDirectory + "DownArrow.png");		
			cocoTurn = new ImageIcon(imageDirectory + "UpArrow.png");
			
			//Scaling the images to each square size of the the board
			frodo = new ImageIcon(scaleImage(frodo.getImage()));
			coco = new ImageIcon(scaleImage(coco.getImage()));
			kingFrodo = new ImageIcon(scaleImage(kingFrodo.getImage()));
			queenCoco = new ImageIcon(scaleImage(queenCoco.getImage()));
			frodoTurn = new ImageIcon(scaleImage(frodoTurn.getImage()));
			cocoTurn = new ImageIcon(scaleImage(cocoTurn.getImage()));
			
			//Count of how many pieces are left on the board
			cocoCount = 12;
			frodoCount = 12;
		} else {
			//Images for chess
			
		}
		
		//GUI Component Initialization
		mainLayout = new JPanel(new BorderLayout());
		boardLayout = new JPanel(new GridLayout(8, 8, 1, 1));	//8x8 grid with 1 pixel of spacing
		leftLayout = new JPanel(new GridLayout(0, 1));
		rightLayout = new JPanel(new GridLayout(0, 1));
		
		chooseMode = new JLabel("Checkers or Chess?");
		turnUpdate = new JLabel("Frodo Turn", frodo, JLabel.LEFT);
		liveUpdate = new JLabel("Checkers woof! Frodo moves first", JLabel.CENTER);
		cocoScore = new JLabel("Coco: " + cocoCount, queenCoco, JLabel.CENTER);
		frodoScore = new JLabel("Frodo: " + frodoCount, kingFrodo, JLabel.CENTER);
		
		checkersButton = new JRadioButton("Checkers");
		chessButton = new JRadioButton("Chess");
		radialGroup = new ButtonGroup();
	}
	
	/*
	 * Add all the GUI components to the main JPanel which is then set as the content pane of the frame (main window)
	 */
	public void addComponentsToPane(Container pane) {
		//Set the pieces of checkers (default) onto the board
		setPieces();
		
		//Link the 2 radio buttons together
		radialGroup.add(checkersButton);
		radialGroup.add(chessButton);
		
		checkersButton.setSelected(true); //Checkers is selected as default
		checkersButton.setActionCommand("checkers");
		checkersButton.addActionListener(new ChangeModeListener());
		chessButton.setActionCommand("chess");
		chessButton.addActionListener(new ChangeModeListener());
		
		//In case the count is offset while playing (for testing) -> secret top right button
		buttonArr[0][7].getButton().addActionListener(new ResetListener());
		//For testing until alternating turn is implemented -> toggle turn
		buttonArr[7][0].getButton().setIcon(frodoTurn);
		buttonArr[7][0].getButton().addActionListener(new NextTurnListener());
		
		//Left side of the window
		leftLayout.add(chooseMode);
		leftLayout.add(checkersButton);
		leftLayout.add(chessButton);
		leftLayout.add(turnUpdate);
		
		//Right side of the window
		rightLayout.add(cocoScore);
		rightLayout.add(liveUpdate);
		rightLayout.add(frodoScore);
		
		//Setting the sizes of the sub panels
		boardLayout.setPreferredSize(new Dimension(HEIGHT, HEIGHT));	//Square of the screen's height
		//Everything else on the left/right
		leftLayout.setPreferredSize(new Dimension((WIDTH - HEIGHT) / 2, HEIGHT));
		rightLayout.setPreferredSize(new Dimension((WIDTH - HEIGHT) / 2, HEIGHT));
		
		//Adding the sub panels to the main panel
		mainLayout.add(boardLayout, BorderLayout.CENTER);
		mainLayout.add(leftLayout, BorderLayout.WEST);
		mainLayout.add(rightLayout, BorderLayout.EAST);
		//Adding whitespace border on the side
		mainLayout.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
		//Setting the main panel as the frame's content panel 
		pane.add(mainLayout);
	}
	
	public static void createAndShowGUI() {
		GUIRunnyThingy frame = new GUIRunnyThingy();			//creates the window
		
		frame.addComponentsToPane(frame.getContentPane());		//adds all GUI components to the window
		//frame.setSize(WIDTH, HEIGHT);
		frame.pack();											//ensures the window is sized based on the preferred sizes of panels
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);			//maximizes the window
		frame.setLocationRelativeTo(null);						//sets the window position in the middle of the screen
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	//closes the window when the exit buttons is pressed
		frame.setVisible(true);									//ensures that the window is visible
		frame.setTitle("Coco vs Frodo");						//sets the title of the window
	}
	
	public static void main(String[] args) {	
		// Invoke and start the GUI
		javax.swing.SwingUtilities.invokeLater(() -> createAndShowGUI());
	}
	
	/*
	 * The for loop is used to initialized each square in buttonArr to its corresponding piece, position, color, and button icon.
	 * Additionally, it sets the size of all buttons, its background color, and adds actionListener on all carolina blue buttons
	 * for checkers!
	 * 
	 * For chess, coming soon!
	 */
	public void setPieces() {
		//Set up for checkers
		if(isCheckers) {
			//Loop through all rows and columns
			for(int r = 0; r < 8; r++) {
				for(int c = 0; c < 8; c++) {
					//Placing the alternating carolina blue squares and determine where to place the doggos (checkers pieces)
					if(((r+1) % 2 == 0 && (c+1) % 2 == 0) || ((r+1) % 2 == 1 && (c+1) % 2 == 1)) {
						//Coco -> top 3 rows, Frodo -> bottom 3 rows, empty -> middle 2 rows
						if(r < 3) {
							buttonArr[r][c] = new Square(new Coco(this, new Position(r, c)), CAROLINA_BLUE, new Position(r, c), new JButton(coco));
						} else if(r > 4){
							buttonArr[r][c] = new Square(new Frodo(this, new Position(r, c)), CAROLINA_BLUE, new Position(r, c), new JButton(frodo));
						} else {
							buttonArr[r][c] = new Square(null, CAROLINA_BLUE, new Position(r, c), new JButton());
						}
					//Placing the alternating black squares
					} else {
						buttonArr[r][c] = new Square(null, BLACK, new Position(r, c), new JButton());
					}
					buttonArr[r][c].getButton().setSize(HEIGHT/8, HEIGHT/8);					//Set button size to a square
					
					if(((r+1) % 2 == 0 && (c+1) % 2 == 0) || ((r+1) % 2 == 1 && (c+1) % 2 == 1))
						buttonArr[r][c].getButton().setBackground(buttonArr[r][c].getColor());	//Set button's color to Carolina Blue
					else
						buttonArr[r][c].getButton().setBackground(buttonArr[r][c].getColor());	//Set button's color to Black
					
					boardLayout.add(buttonArr[r][c].getButton());								//Add buttons to JPanel
					
					//Adding action listener to all carolina blue squares
					if(buttonArr[r][c].getColor().equals(CAROLINA_BLUE)){
						buttonArr[r][c].getButton().addActionListener(new CheckersMoveListener());
					}
				}
			}
		//Set up for chess
		} else {
			
		}
	}
	
	//Accessor of the images of the pieces
	public ImageIcon getImage(String piece) {
		return piece.equals("frodo") ? frodo : piece.equals("coco") ? coco : piece.equals("king") ? kingFrodo : piece.equals("queen") ? queenCoco : null;
	}
	
	//Scale the images to the button/square size of the board
	public Image scaleImage(Image image) {
		return image.getScaledInstance(HEIGHT/8, HEIGHT/8, java.awt.Image.SCALE_SMOOTH);
	}
	
	//Returns the square based on a given position
	public Square findSquareWithPos(Position p) {
		return buttonArr[p.getRow()][p.getColumn()];
	}
	
	//Removes a piece and its button icon when it is taken
	public void removePiece(Square s) {
		int row = s.getPosition().getRow();
		int col = s.getPosition().getColumn();
		buttonArr[row][col].getButton().setIcon(null);	//Turn off the image icon
		//Decrement the count of Coco and Frodo based on checkers or chess
		if(isCheckers) {
			if(buttonArr[row][col].getDoggo().getType().equals("Coco") || buttonArr[row][col].getDoggo().getType().equals("Queen")) {
				cocoScore.setText("Coco: " + --cocoCount);
			} else {
				frodoScore.setText("Frodo: " + --frodoCount);
			}
		} else {
			
		}
		s.setDoggo(null);								//Delete the piece
	}
	
	/*
	 * When a button is pressed for the first time, it is stored into the Square variable "from". The second time the button is pressed,
	 * if from has a piece (doggo), then the code will check if the piece can move or jump over an opponent's piece(s). Moreover,
	 * if a top/bottom button on the board is pressed, the piece will be set to its corresponding King/Queen child class as long as
	 * the move is legal. If a piece cannot move, then the piece can check whether or not it can jump. If not, everything resets.
	 */
	private class CheckersMoveListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			//Looping through every row and column
			for(int r = 0; r < 8; r++) {
				for(int c = 0; c < 8; c++) {
					//If a carolina blue square is click, increment clickCount
					if((JButton)e.getSource() == buttonArr[r][c].getButton()) {
						clickCount++;
						//First click -> save the click location on the board and update the player with text
						if(clickCount == 1) {
							from = buttonArr[r][c];
							liveUpdate.setText(from.getDoggo().getType() + " is selected");
						}
						//Second click
						if(clickCount == 2) {
							//If the square that was clicked on has a doggo (piece)
							if(from.getDoggo() != null) {
								//Promotion to queen or king
								if(buttonArr[r][c].getPosition().getRow() == 7 && from.getDoggo().getType().equals("Coco")) {
									//queenCoco
									from.setDoggo(new QueenCoco(from.getDoggo().getBoard(), new Position(r, c)));
									liveUpdate.setText("Bark! Coco promoted to Queen Coco!");
								} else if(buttonArr[r][c].getPosition().getRow() == 0 && from.getDoggo().getType().equals("Frodo")) {
									//kingFrodo
									from.setDoggo(new KingFrodo(from.getDoggo().getBoard(), new Position(r, c)));
									liveUpdate.setText("Yip! Frodo promoted to King Frodo!");
								}
								//On frodo turn -> if first square selected was a frodo -> move
								if(isFrodoTurn) {
									if(from.getDoggo().getType().equals("Frodo") || (from.getDoggo().getType().equals("King"))) {
										boolean moved = from.getDoggo().move(from, buttonArr[r][c]);
//										liveUpdate.setText("Frodo Move: " + moved);
										//If can't move -> jump
										if(!moved) {
											boolean jump = from.getDoggo().jump(from, buttonArr[r][c]);
											if(jump)	liveUpdate.setText("Yip! Frodo jumped!");
											else		liveUpdate.setText("Illegal Move!");
										}
									} else {
										liveUpdate.setText("Moving out of turn!");
									}
								} else if(from.getDoggo().getType().equals("Coco") || (from.getDoggo().getType().equals("Queen"))){
									boolean moved = from.getDoggo().move(from, buttonArr[r][c]);
//									liveUpdate.setText("Coco Move: " + moved);
									if(!moved) {
										boolean jump = from.getDoggo().jump(from, buttonArr[r][c]);
										if(jump)	liveUpdate.setText("Bark! Coco jumped!");
										else		liveUpdate.setText("Illegal Move!");
									}
								} else {
									liveUpdate.setText("Moving out of turn!");
								}
							} else {
								from = null;
								liveUpdate.setText("No doggo was selected to move");
							}
							clickCount = 0;
						}
					}
				}
			}
			if(cocoCount == 0) {
				liveUpdate.setText("Frodo wins! Woof woof!");
				liveUpdate.setIcon(kingFrodo);
				cocoScore.setIcon(kingFrodo);
			} else if(frodoCount == 0) {
				liveUpdate.setText("Coco wins! Woof woof!");
				liveUpdate.setIcon(queenCoco);
				frodoScore.setIcon(queenCoco);
			}
		}
	}
	
	/*
	 * Click count is used to keep track of the pair of clicks. If a misclick occurs and messes up count,
	 * by pressing the top right button, count will be reset and the game can resume.
	 */
	private class ResetListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			clickCount = 0;
			liveUpdate.setText("Click count was Reset");
		}
	}
	
	/*
	 * After a move is made, the player can press the bottom left button to indicate the end of their turn.
	 * The arrow will point up for Coco's turn and point down for Frodo's turn. The pieces will not be able
	 * to move/jump if it is not their turn.
	 */
	private class NextTurnListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			isFrodoTurn = !isFrodoTurn;
			if(isFrodoTurn) {
				buttonArr[7][0].getButton().setIcon(frodoTurn);
				turnUpdate.setText("Frodo Turn");
				turnUpdate.setIcon(frodo);
			} else {
				buttonArr[7][0].getButton().setIcon(cocoTurn);
				turnUpdate.setText("Coco Turn");
				turnUpdate.setIcon(coco);
			}
		}
	}
	
	/*
	 * Change the boolean that would rearrange the board depending on what mode the player selected
	 */
	private class ChangeModeListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand().equals("checkers")) {
				isCheckers = true;
				liveUpdate.setText("Checkers selected! Frodo moves first");
			} else if(e.getActionCommand().equals("chess")){
				//isCheckers = false;
				liveUpdate.setText("Chess selected! Coco moves first");
			}
		}
	}
}