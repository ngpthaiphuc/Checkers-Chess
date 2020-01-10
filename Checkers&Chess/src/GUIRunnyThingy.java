import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUIRunnyThingy extends JFrame{
	
	/*
	 * The 8x8 2D array of Square, buttonArr, was used to make a board of buttons. Each square in the double array was
	 * initialize with its color alternating in checkers pattern. A piece moves by pressing on the piece then the desire location. 
	 */
	private Square[][] buttonArr;
	public Square[][] getButtonArr(){
		return buttonArr;
	}
	
	private boolean isCheckers;
	private static int HEIGHT, WIDTH;
	private static Color CAROLINA_BLUE, BLACK;
	//A temporary variable Square that is used to store the first square clicked (the piece that is going to be moved)
	private Square from;
	private int clickCount, cocoCount, frodoCount;
	private boolean isFrodoTurn;
	
	//The images used for the pieces and for turn buttons (drawn by Joi Zooo)
	private String imageDirectory;
	//Checkers (promoted pieces' icons are the same for chess king pieces)
	private ImageIcon frodo, coco, kingFrodo, queenCoco;//, frodoTurn, cocoTurn;
	//Chess (k = knight, b = bishop, r = rook, q = queen)
	private ImageIcon kCoco, bCoco, rCoco, qCoco, kFrodo, bFrodo, rFrodo, qFrodo;  
	
	//GUI Components Declaration
	private JPanel mainLayout, boardLayout, leftLayout, rightLayout;
	private JLabel chooseMode, turnUpdate, instructions, liveUpdate, cocoScore, frodoScore;
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
		
		//Images for checkers
		frodo = new ImageIcon(imageDirectory + "Frodo.png");
		coco = new ImageIcon(imageDirectory + "Coco.png");
		kingFrodo = new ImageIcon(imageDirectory + "KingFrodo.png");
		queenCoco = new ImageIcon(imageDirectory + "QueenCoco.png");
//		frodoTurn = new ImageIcon(imageDirectory + "DownArrow.png");		
//		cocoTurn = new ImageIcon(imageDirectory + "UpArrow.png");
		
		//Scaling the images to each square size of the the board
		frodo = new ImageIcon(scaleImage(frodo.getImage()));
		coco = new ImageIcon(scaleImage(coco.getImage()));
		kingFrodo = new ImageIcon(scaleImage(kingFrodo.getImage()));
		queenCoco = new ImageIcon(scaleImage(queenCoco.getImage()));
//		frodoTurn = new ImageIcon(scaleImage(frodoTurn.getImage()));
//		cocoTurn = new ImageIcon(scaleImage(cocoTurn.getImage()));
		
		//Count of how many checkers pieces are left on the board
		cocoCount = 12;
		frodoCount = 12;
		
		//Images for chess
		kCoco = new ImageIcon(imageDirectory + "CocoKnight.png");
		bCoco = new ImageIcon(imageDirectory + "CocoBishop.png");
		rCoco = new ImageIcon(imageDirectory + "CocoRook.png");
		qCoco = new ImageIcon(imageDirectory + "CocoQueen.png");
		
		kFrodo = new ImageIcon(imageDirectory + "FrodoKnight.png");
		bFrodo = new ImageIcon(imageDirectory + "FrodoBishop.png");
		rFrodo = new ImageIcon(imageDirectory + "FrodoRook.png");
		qFrodo = new ImageIcon(imageDirectory + "FrodoQueen.png");
		
		//Scaling the images to each square size of the the board
		kCoco = new ImageIcon(scaleImage(kCoco.getImage()));
		bCoco = new ImageIcon(scaleImage(bCoco.getImage()));
		rCoco = new ImageIcon(scaleImage(rCoco.getImage()));
		qCoco = new ImageIcon(scaleImage(qCoco.getImage()));
		
		kFrodo = new ImageIcon(scaleImage(kFrodo.getImage()));
		bFrodo = new ImageIcon(scaleImage(bFrodo.getImage()));
		rFrodo = new ImageIcon(scaleImage(rFrodo.getImage()));
		qFrodo = new ImageIcon(scaleImage(qFrodo.getImage()));
		
		//GUI Component Initialization
		mainLayout  = new JPanel(new BorderLayout());
		boardLayout = new JPanel(new GridLayout(8, 8, 1, 1));	//8x8 grid with 1 pixel of spacing
		leftLayout  = new JPanel(new GridLayout(0, 1));
		rightLayout = new JPanel(new GridLayout(0, 1));
		
		chooseMode   = new JLabel("Checkers or Chess?");
		turnUpdate   = new JLabel("Frodo Turn", frodo, JLabel.LEFT);
		instructions = new JLabel("<html><body> <U>How to move</U>: Select the doggo you want to move <br> "
				+ "then click where you want to move it! </body></html>", JLabel.RIGHT);
		liveUpdate   = new JLabel("Checkers woof! Frodo moves first.", JLabel.CENTER);
		cocoScore    = new JLabel("Coco: " + cocoCount, queenCoco, JLabel.CENTER);
		frodoScore   = new JLabel("Frodo: " + frodoCount, kingFrodo, JLabel.CENTER);
		
		checkersButton = new JRadioButton("Checkers");
		chessButton    = new JRadioButton("Chess");
		radialGroup    = new ButtonGroup();
	}
	
	/*
	 * Add all the GUI components to the main JPanel which is then set as the content pane of the frame (main window)
	 */
	public void addComponentsToPane(Container pane) {
		//Set the pieces of checkers (default) onto the board
		setPieces(true);
		
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
//		buttonArr[7][0].getButton().setIcon(frodoTurn);
//		buttonArr[7][0].getButton().addActionListener(new NextTurnListener());
		
		//Left side of the window
		chooseMode.setFont(new Font("Serif", Font.BOLD, 18));
		turnUpdate.setFont(new Font("Serif", Font.BOLD, 18));
		
		leftLayout.add(chooseMode);
		leftLayout.add(checkersButton);
		leftLayout.add(chessButton);
		leftLayout.add(turnUpdate);
		
		//Right side of the window
		instructions.setFont(new Font("Serif", Font.BOLD, 16));
		liveUpdate.setFont(new Font("Serif", Font.BOLD, 20));
		
		rightLayout.add(cocoScore);
		rightLayout.add(instructions);
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
	 * On initialization, each square/button is initialized to a piece, position, color, and buttonIcon.
	 * Additionally, it sets the size of all buttons, its background color, and adds actionListener on all carolina blue buttons
	 * for checkers (default)
	 * 
	 * For checkers, pieces are set up on carolina blue squares -> Coco is on the top, Frodo is on the bottom (Frodo moves first)
	 * 
	 * For chess, pieces are set up on the top and bottom -> Coco is on the bottom, Frodo is on the top (Coco moves first)
	 */
	public void setPieces(boolean initilization) {
		//Default -> checkers
		if(initilization) {
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
		} else {
			//Set up for checkers
			if(isCheckers) {
				//Loop through all rows and columns
				for(int r = 0; r < 8; r++) {
					for(int c = 0; c < 8; c++) {
						//Removing existing action listener for checkers
						if(buttonArr[r][c].getButton().getActionListeners().length == 1)
							buttonArr[r][c].getButton().removeActionListener(buttonArr[r][c].getButton().getActionListeners()[0]);
						//Placing the alternating carolina blue squares and determine where to place the doggos (checkers pieces)
						if(((r+1) % 2 == 0 && (c+1) % 2 == 0) || ((r+1) % 2 == 1 && (c+1) % 2 == 1)) {
							//Coco -> top 3 rows, Frodo -> bottom 3 rows, empty -> middle 2 rows
							if(r < 3) {
								buttonArr[r][c].setDoggo(new Coco(this, buttonArr[r][c].getPosition()));
								buttonArr[r][c].getButton().setIcon(coco);
							} else if(r > 4){
								buttonArr[r][c].setDoggo(new Frodo(this, buttonArr[r][c].getPosition()));
								buttonArr[r][c].getButton().setIcon(frodo);
							}
						}
						//Adding action listener to all carolina blue squares
						if(buttonArr[r][c].getColor().equals(CAROLINA_BLUE)){
							buttonArr[r][c].getButton().addActionListener(new CheckersMoveListener());
						}
					}
				}
			//Set up for chess
			} else {
				//Loop through all rows and columns
				for(int r = 0; r < 8; r++) {
					for(int c = 0; c < 8; c++) {
						//Removing existing action listener for checkers
						if(buttonArr[r][c].getButton().getActionListeners().length == 1)
							buttonArr[r][c].getButton().removeActionListener(buttonArr[r][c].getButton().getActionListeners()[0]);
						
						//Frodo -> top 2 rows, Coco -> bottom 2 rows, empty -> middle 4 rows
						if(r == 1) {
							//Frodo's Pawns
							buttonArr[r][c].setDoggo(new Pawn("frodo", this, buttonArr[r][c].getPosition()));
							buttonArr[r][c].getButton().setIcon(frodo);
						} else if(r == 0) {
							if(c == 0 || c == 7) {
								//Frodo's Rooks
								buttonArr[r][c].setDoggo(new Rook("frodo", this, buttonArr[r][c].getPosition()));
								buttonArr[r][c].getButton().setIcon(rFrodo);
							} else if(c == 1 || c == 6) {
								//Frodo's Knights
								buttonArr[r][c].setDoggo(new Knight("frodo", this, buttonArr[r][c].getPosition()));
								buttonArr[r][c].getButton().setIcon(kFrodo);
							} else if(c == 2 || c == 5) {
								//Frodo's Bishops
								buttonArr[r][c].setDoggo(new Bishop("frodo", this, buttonArr[r][c].getPosition()));
								buttonArr[r][c].getButton().setIcon(bFrodo);
							} else if(c == 3) {
								//Frodo's Queen
								buttonArr[r][c].setDoggo(new Queen("frodo", this, buttonArr[r][c].getPosition()));
								buttonArr[r][c].getButton().setIcon(qFrodo);
							} else if(c == 4) {
								//Frodo's King
								buttonArr[r][c].setDoggo(new King("king", this, buttonArr[r][c].getPosition()));
								buttonArr[r][c].getButton().setIcon(kingFrodo);
							}
						} else if(r == 6) {
							//Coco's Pawns
							buttonArr[r][c].setDoggo(new Pawn("coco", this, buttonArr[r][c].getPosition()));
							buttonArr[r][c].getButton().setIcon(coco);
						} else if(r == 7) {
							if(c == 0 || c == 7) {
								//Coco's Rooks
								buttonArr[r][c].setDoggo(new Rook("coco", this, buttonArr[r][c].getPosition()));
								buttonArr[r][c].getButton().setIcon(rCoco);
							} else if(c == 1 || c == 6) {
								//Coco's Knights
								buttonArr[r][c].setDoggo(new Knight("coco", this, buttonArr[r][c].getPosition()));
								buttonArr[r][c].getButton().setIcon(kCoco);
							} else if(c == 2 || c == 5) {
								//Coco's Bishops
								buttonArr[r][c].setDoggo(new Bishop("coco", this, buttonArr[r][c].getPosition()));
								buttonArr[r][c].getButton().setIcon(bCoco);
							} else if(c == 3) {
								//Coco's Queen
								buttonArr[r][c].setDoggo(new Queen("coco", this, buttonArr[r][c].getPosition()));
								buttonArr[r][c].getButton().setIcon(qCoco);
							} else if(c == 4) {
								//Coco's King
								buttonArr[r][c].setDoggo(new King("queen", this, buttonArr[r][c].getPosition()));
								buttonArr[r][c].getButton().setIcon(queenCoco);
							}
						}
						//Adding action listener to all squares
						buttonArr[r][c].getButton().addActionListener(new ChessMoveListener());
					}
				}
			}
		}
	}
	
	//Accessor of the images of the pieces (both checkers and chess)
	public ImageIcon getImage(String piece) {
		return piece.equals("frodo") ? frodo : piece.equals("coco") ? coco : piece.equals("king") ? kingFrodo : piece.equals("queen")
				? queenCoco : piece.equals("cocoKnight") ? kCoco : piece.equals("cocoBishop") ? bCoco : piece.equals("cocoRook") ?
						rCoco : piece.equals("cocoQueen") ? qCoco : piece.equals("frodoKnight") ? kFrodo : piece.equals("frodoBishop")
								? bFrodo : piece.equals("frodoRook") ? rFrodo : piece.equals("frodoQueen") ? qFrodo : null;
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
			if(buttonArr[row][col].getDoggo() != null && (buttonArr[row][col].getDoggo().getType().equals("Coco") || 
					buttonArr[row][col].getDoggo().getType().equals("Queen"))) {
				cocoScore.setText("Coco: " + --cocoCount);
			} else {
				frodoScore.setText("Frodo: " + --frodoCount);
			}
		} else {
			
		}
		s.setDoggo(null);								//Remove the piece
	}
	
	//Removes all pieces on the board (to switch between checkers and chess)
	public void removeAllPieces() {
		for(int r = 0; r < 8; r++) {
			for(int c = 0; c < 8; c++) {
				removePiece(buttonArr[r][c]);
			}
		}
	}
	
	//
	private boolean alternatingTurn() {
		isFrodoTurn = !isFrodoTurn;
		if(isFrodoTurn) {
//			buttonArr[7][0].getButton().setIcon(frodoTurn);
			turnUpdate.setText("Frodo Turn");
			turnUpdate.setIcon(frodo);
		} else {
//			buttonArr[7][0].getButton().setIcon(cocoTurn);
			turnUpdate.setText("Coco Turn");
			turnUpdate.setIcon(coco);
		}
		return isFrodoTurn;
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
							if(from.getDoggo() != null)
								liveUpdate.setText(from.getDoggo().getType() + " is selected");
						}
						//Second click
						if(clickCount == 2) {
							//Checking if the square clicked on is a doggo (piece)
							if(from.getDoggo() != null) {
								//Checking whose turn it is
								if(isFrodoTurn) {
									if(from.getDoggo().getType().equals("Frodo") || (from.getDoggo().getType().equals("King"))) {
										boolean moved = from.getDoggo().move(buttonArr[r][c]);
										boolean jumped = false;
										//If can't move -> try jump
										if(!moved) {
											jumped = from.getDoggo().capture(buttonArr[r][c]);
											//If jump successfully -> check for successive jumps
											if(jumped) {
												liveUpdate.setText("Yip! Frodo jumped!");
												if(buttonArr[r][c].getDoggo().canCapture()) {
													isFrodoTurn = !isFrodoTurn;
													liveUpdate.setText("Yip! Frodo jumped! Jump again!");
												}
											}
											else{
												liveUpdate.setText("Illegal Move!");
												//After alternatingTurn() is executed -> the turn remains the same
												isFrodoTurn = !isFrodoTurn;
											}
										} else {
											liveUpdate.setText("Frodo moved");
										}
										//Check for promotion to KingFrodo
										if((moved || jumped) && buttonArr[r][c].getPosition().getRow() == 0 &&
												buttonArr[r][c].getDoggo().getType().equals("Frodo")) {
											buttonArr[r][c].setDoggo(new KingFrodo(buttonArr[r][c].getDoggo().getBoard(),
													buttonArr[r][c].getPosition()));
											buttonArr[r][c].getButton().setIcon(buttonArr[r][c].getDoggo().getBoard().getImage("king"));
											liveUpdate.setText("Yip! Frodo promoted to King Frodo!");
										}
									} else {
										liveUpdate.setText("Moving out of turn!");
										//After alternatingTurn() is executed -> the turn remains the same
										isFrodoTurn = !isFrodoTurn;
									}
								} else if(from.getDoggo().getType().equals("Coco") || (from.getDoggo().getType().equals("Queen"))){
									boolean moved = from.getDoggo().move(buttonArr[r][c]);
									boolean jumped = false;
									//If can't move -> try jump
									if(!moved) {
										jumped = from.getDoggo().capture(buttonArr[r][c]);
										//If jump successfully -> check for successive jumps
										if(jumped) {
											liveUpdate.setText("Bark! Coco jumped!");
											if(buttonArr[r][c].getDoggo().canCapture()) {
												isFrodoTurn = !isFrodoTurn;
												liveUpdate.setText("Bark! Coco jumped! Jump again!");
											}
										}
										else{
											liveUpdate.setText("Illegal Move!");
											//After alternatingTurn() is executed -> the turn remains the same
											isFrodoTurn = !isFrodoTurn;
										}
									} else {
										liveUpdate.setText("Coco moved");
									}
									//Checking for promotion to QueenCoco
									if((moved || jumped) && buttonArr[r][c].getPosition().getRow() == 7 &&
											buttonArr[r][c].getDoggo().getType().equals("Coco")) {
										buttonArr[r][c].setDoggo(new QueenCoco(buttonArr[r][c].getDoggo().getBoard(),
												buttonArr[r][c].getPosition()));
										buttonArr[r][c].getButton().setIcon(buttonArr[r][c].getDoggo().getBoard().getImage("queen"));
										liveUpdate.setText("Bark! Coco promoted to Queen Coco!");
									}
								} else {
									liveUpdate.setText("Moving out of turn!");
									//After alternatingTurn() is executed -> the turn remains the same
									isFrodoTurn = !isFrodoTurn;
								}
							} else {
								liveUpdate.setText("No doggo was selected to move");
								//After alternatingTurn() is executed -> the turn remains the same
								isFrodoTurn = !isFrodoTurn;
							}
							clickCount = 0;
//							System.out.println(alternatingTurn());
							alternatingTurn();
						}
					}
				}
			}
			if(cocoCount == 0) {
				liveUpdate.setText("Frodo wins! Woof woof!");
				liveUpdate.setIcon(kingFrodo);
				cocoScore.setIcon(kingFrodo);
				turnUpdate.setIcon(kingFrodo);
			} else if(frodoCount == 0) {
				liveUpdate.setText("Coco wins! Woof woof!");
				liveUpdate.setIcon(queenCoco);
				frodoScore.setIcon(queenCoco);
				turnUpdate.setIcon(queenCoco);
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
				removeAllPieces();
				setPieces(false);
				cocoCount = 12;	frodoCount = 12;
				cocoScore.setText("Coco: " + cocoCount);
				frodoScore.setText("Coco: " + frodoCount);
				turnUpdate.setText("Frodo turn");
				turnUpdate.setIcon(frodo);
				liveUpdate.setText("Checkers selected! Frodo moves first");
			} else if(e.getActionCommand().equals("chess")){
				isCheckers = false;
				removeAllPieces();
				setPieces(false);
				cocoCount = 39;	frodoCount = 39;
				cocoScore.setText("Coco: " + cocoCount);
				frodoScore.setText("Coco: " + frodoCount);
				turnUpdate.setText("Coco turn");
				turnUpdate.setIcon(coco);
				liveUpdate.setText("Chess selected! Coco moves first");
			}
		}
	}
	
	/*
	 * 
	 */
	private class ChessMoveListener implements ActionListener {
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
							if(from.getDoggo() != null)
								liveUpdate.setText(from.getDoggo().getType() + " is selected");
						}
						//Second click
						if(clickCount == 2) {
							//Checking if the square clicked on is a doggo (piece)
							if(from.getDoggo() != null) {
								//Checking whose turn it is (not implemented yet)
								boolean moved = from.getDoggo().move(buttonArr[r][c]);
								boolean captured = false;
								//If can't move -> try jump
								if(!moved) {
									captured = from.getDoggo().capture(buttonArr[r][c]);
									//If jump successfully -> check for successive jumps
									if(captured) {
										liveUpdate.setText("Yip! Frodo captured a piece!");
									}
									else{
										liveUpdate.setText("Illegal Move!");
										//After alternatingTurn() is executed -> the turn remains the same
										isFrodoTurn = !isFrodoTurn;
									}
								} else {
									liveUpdate.setText("Frodo moved");
								}
							} else {
								liveUpdate.setText("No doggo was selected to move");
								//After alternatingTurn() is executed -> the turn remains the same
								isFrodoTurn = !isFrodoTurn;
							}
							clickCount = 0;
//							System.out.println(alternatingTurn());
						}
					}
				}
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
	 * to move/jump if it is not their turn. (for testing)
	 */
//	private class NextTurnListener implements ActionListener {
//		public void actionPerformed(ActionEvent e) {
//			isFrodoTurn = !isFrodoTurn;
//			if(isFrodoTurn) {
//				buttonArr[7][0].getButton().setIcon(frodoTurn);
//				turnUpdate.setText("Frodo Turn");
//				turnUpdate.setIcon(frodo);
//			} else {
//				buttonArr[7][0].getButton().setIcon(cocoTurn);
//				turnUpdate.setText("Coco Turn");
//				turnUpdate.setIcon(coco);
//			}
//		}
//	}
}