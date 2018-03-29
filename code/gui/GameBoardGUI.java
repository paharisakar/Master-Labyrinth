package code.gui;

import code.model.*;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;

/**
 * 
 * @author Ian,Weijin
 *
 */
public class GameBoardGUI implements Runnable, Observer{
	/**
	 * collection of 21 references to token JButtons 
	 */
	
	private ArrayList<JPanel> _playerPanelList;
	/**
	 * reference to the main window of the game that holds 
	 * _leftPanel and _rightPanel
	 */
	private JFrame _window;
	/**
	 * reference to the panel that the gameboard is on
	 */
	private JPanel _boardPanel;
	/**
	 * reference to the panel that is behind
	 *  the _boardPanel
	 */
	private JPanel _leftPanel;
	/**
	 * the labels (triangles) indicate the places 
	 * where players can insert the shiftable tile
	 */
	private JLabel _leftPanelBehind;
	/**
	 * reference to the main panel that's on the right 
	 * side of the game window
	 */
	private JPanel _rightPanel;
	/**
	 * the panel that holds the JTextPane _playerInfo
	 */
	private JPanel _playerInfoPanel;
	/**
	 * the panel that holds three JButtons 
	 * that are used for shifting the shiftable tile
	 */
	private JPanel _shiftableTilePanel;
	/**
	 * the panel that holds the JTextPane _gameFeedback
	 */
	private JPanel _gameFeedbackPanel;
	/**
	 * reference to the GameBoard class
	 */
	private GameBoard _gb;
	/**
	 * the JButton that used to hold the shiftable tile
	 */
	private JButton _shiftableTileButton;
	/**
	 * the JButton that is used to rotate the shiftable 
	 * tile counterclockwise by 90 degree
	 */
	private JButton _rotateCounterClockwise;
	/**
	 * the JButton that is used to rotate the shiftable 
	 * tile clockwise by 90 degree
	 */
	private JButton _rotateClockwise;
	/**
	 * the JTextPane that displays the players' information
	 */
	private JTextPane _playerInfo;
	/**
	 * the JTextPane gives players the game feedback 
	 * such as whose turn it is
	 */
	private JTextPane _gameFeedback;
	
	/** 
	 * the JButton that is used to end each player's turn
	 */
	private JButton _endTurnButton;
	
	private Player[] _playerArray;
	
	private JButton _saveButton;
	
	
	/**
	 * This method sets the observer for the gameboard
	 * gb refers to the GameBoard object
	 * @param gb refers to GameBoard
	 * @author Ian,Weijin
	 */
	public GameBoardGUI(GameBoard gb){
		_gb = gb;
		_gb.setObserver(this);
		_playerPanelList = new ArrayList<JPanel>();
	}

	/**
	 * This method creates the main window for the game and add two main panels with same size
	 * to the window. On the top of these two panels, there are gameboard panel, game 
	 * feedback panel, players information panel, and shiftable tile panel. And set the size of 
	 * these panels. This method also creates a JButton named "end turn" and add the ActionListener to it.
	 * @author Ian,Weijin
	 * @author Ian,Ken 04-10-16
	 */
	@Override public void run() {	
		_window = new JFrame("Master Labyrinth");
		_window.setBackground(new Color(245,245,220));
		_window.getContentPane().setLayout(new GridLayout(1,2));
		_window.setMaximumSize(new Dimension(1250, 720));
		_window.setMinimumSize(new Dimension(1250, 720));
		_leftPanel = new JPanel();
		//JPanel _leftPanelBack = new JPanel();
		try{
			Image img = ImageIO.read(getClass().getResourceAsStream("images/GameBoardBorder.bmp"));
			_leftPanelBehind = new JLabel(new ImageIcon(img));
		} catch (IOException ex) {}
		
		
		_rightPanel = new JPanel();
		_rightPanel.setBackground(new Color(245,245,220));
		_leftPanel.setBackground(new Color(245,245,220));
		_leftPanel.setSize(560,560);
		_leftPanelBehind.setSize(720,720);
		//_leftPanelBack.setSize(720,720);
		_rightPanel.setSize(600,720);
		
		_endTurnButton = new JButton("End Turn");
		_endTurnButton.setFont(new Font("Garamond", Font.BOLD, 40));
		_endTurnButton.setForeground(new Color(255,201,14));
		_endTurnButton.setPreferredSize(new Dimension(300,180));
		_endTurnButton.setBackground(new Color(0,0,0));
		_endTurnButton.addActionListener(new ActionListener(){
			
			/**
			 * This method add ActionListen to the "end turn" button, and by clicking 
			 * this button, a player can end his/her turn and so on.
			 * @param e action event generated when end turn clicked
			 * @author Ian,Weijin
			 * @author Ian,Ken 04-10-16
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				if(GameBoard.CURRENTPLAYER.getHasInsertedThisTurn()){
					GameBoard.CURRENTPLAYER.endMyTurn();
					//update();
					_gb.toggleNextPlayer();
					_gameFeedback.setText("\t\t\t\tGAME INFO\n\nIt is now " + GameBoard.CURRENTPLAYER.getName() +
							"'s (" + GameBoard.CURRENTPLAYER.getColor() + " pawn) turn."+
									"\nCurrent Collectible Token Number: " + _gb.getCurrentTargetTokenValue());
					_gameFeedback.setFont(new Font("Garamond", Font.BOLD, 14));
					
					String t1 = "";
					int cp = -1;
					for(int i = 0; i < _gb.getPlayers().length; i++){
						Player p = _gb.getPlayers()[i];
						if(p == GameBoard.CURRENTPLAYER){cp = i;}
						if(p != GameBoard.CURRENTPLAYER){
							String t2 = "";
							t1 = t1 + "Player " + i + ": " + p.getName() + " (" + p.getColor() + " Pawn) -- Tokens Coll.: ";
							for(Token t: p.getTokens()){
								t2 = t2 + t.getValue() + " ";
							}
							t1 = t1 + t2 + "\n";
						}
					}
					Player p = GameBoard.CURRENTPLAYER;
					String tokens = "";
					for(Token t: p.getTokens()){
						tokens = tokens + t.getValue() + " ";
					}
					_playerInfo.setText("\t\t\t\tPLAYER INFO\n\nCurrent Player (" + cp +"): " + p.getName() + " (" + 
							p.getColor() + " Pawn) \n" /*Current Score: "+
							p.getScore() + "\n" */+ "My Tokens Collected: " + tokens
							+ "\n\n" + t1);
					_playerInfo.setFont(new Font("Garamond", Font.BOLD, 14));
				}
			}	
		});

		_saveButton = new JButton("Save");
		_saveButton.setFont(new Font("Garamond", Font.BOLD,40));
		_saveButton.setForeground(new Color(255,201,14));
		_saveButton.setPreferredSize(new Dimension(200,180));
		_saveButton.setBackground(new Color(0,0,0));
		_saveButton.addActionListener(new SaveGameButtonHandler(_gb));
		
		
		
		
		_leftPanel.setLayout(new GridLayout(1,1));
		_rightPanel.setLayout(new FlowLayout());
		_leftPanelBehind.add(_leftPanel);
		_leftPanelBehind.setLayout(new GridBagLayout());
		_leftPanelBehind.setBackground(new Color(245,245,220));

		_leftPanelBehind.setVerticalAlignment(SwingConstants.CENTER);;
		_leftPanel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		_leftPanel.setAlignmentY(JComponent.CENTER_ALIGNMENT);

		
		_window.add(_leftPanelBehind);
		_window.add(_rightPanel);
		
		
		//Creates an ingredient and wand panel (singular panel) for each player
		//consider moving to createAndPopulatePlayerInfoPanel
		_playerArray = _gb.getPlayers();
		for(int i=0; i< _playerArray.length; i++){
			Player indexPlayer = _playerArray[i];
			
			this.createIngredientAndWandPanel(indexPlayer);
		}	
		
		createAndPopulateBoardPanel();
		createAndPopulatePlayerInfoPanel();
		createAndPopulateShiftableTilePanel();
		createAndPopulateGameFeedbackPanel();
		
		


		
		
		
		_leftPanel.add(_boardPanel);
		_boardPanel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		_boardPanel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		
		
		_rightPanel.add(_playerInfoPanel);
		_rightPanel.add(_shiftableTilePanel);
		_rightPanel.add(_gameFeedbackPanel);
		_rightPanel.add(_endTurnButton);
		_rightPanel.add(_saveButton);
		
		_window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		update();
		_gb.setHasBeenModifiedFalse();
		_window.setVisible(true);
	}
	
	/**
	 * This method creates and populates the gameboard feedback panel. It sets the size
	 * and background of the panel and there is a JTextPane on top of it that's keeping 
	 * track of whose turn it is
	 * @author Ian,Ken 04-10-16
	 */
	private void createAndPopulateGameFeedbackPanel() {
		_gameFeedbackPanel = new JPanel();
		_gameFeedbackPanel.setBackground(new Color(245,245,220));
		_gameFeedbackPanel.setPreferredSize(new Dimension(600,110));
		_gameFeedback = new JTextPane();
		_gameFeedback.setPreferredSize(new Dimension(600,180));
		_gameFeedbackPanel.add(_gameFeedback);
		_gameFeedback.setBackground(new Color(245,245,220));
		_gameFeedback.setText("\t\t\t\tGAME INFO\n\nIt is now " + GameBoard.CURRENTPLAYER.getName() + "'s (" + GameBoard.CURRENTPLAYER.getColor()+" pawn) turn."+
				"\nCurrent Collectible Token Number: " + _gb.getCurrentTargetTokenValue());
		_gameFeedback.setFont(new Font("Garamond", Font.BOLD, 14));
	}

	/**
	 * This method creates a JPanel that visaulizes the Formula Cards and available wands for each player.
	 * The for loop goes through the number of players and assigns to each player a random formula card from
	 * the shuffled arraylist. playerFormulaCard is a TextPane that contains the text for the Formula Cards. The
	 * Wands themselves are buttons. The TextPane is not editable. Since each player gets three wands in the beginning
	 * of the game, there are three separate instantiations of the wand class. Finally, the wands and the 
	 * playerFormulaCard are added to the playerPanel, which is added to the instance variable _playerPanelList.
	 * playerFormula
	 * @param player refers to the players in the game that this method will be called on.
	 * @author Nick, Tyler
	 */
	private void createIngredientAndWandPanel(Player player){
		for(int i = 0; i< _gb.getPlayers().length; i++){           
			player.addFormulaCard(_gb.givePlayerAFormulaCard(player)); 
		}
		//we need an array list to hold this shit that is an instance variable
		JPanel playerPanel = new JPanel();
		playerPanel.setPreferredSize(new Dimension(250, 180));
		playerPanel.setLayout(new FlowLayout());
		playerPanel.setBackground(new Color(245,245,220));
		
		JTextPane playerFormulaCard = new JTextPane();
		playerFormulaCard.setBackground(new Color(245,245,220));
		playerFormulaCard.setEditable(false);
		playerFormulaCard.setPreferredSize(new Dimension (200, 150));
		playerFormulaCard.setText("First Token to be collected: " +
		"|" + player.getFormulaCard().getToken1() + "|" +
		" Second Token to be collected: " + "|" + player.getFormulaCard().getToken2() + "|" +
		" Third Token to be collected: " + "|" + player.getFormulaCard().getToken3() + "|");  
		
		Wand wand1 = new Wand(this, player, _gb);
		Wand wand2 = new Wand(this, player, _gb);
		Wand wand3 = new Wand(this, player, _gb);

		playerPanel.add(wand1.getWandButton());
		playerPanel.add(wand2.getWandButton());
		playerPanel.add(wand3.getWandButton());
		playerPanel.add(playerFormulaCard);
		
		
//		JButton button1 = new JButton();
//		button1.setText("1");
//		JButton button2 = new JButton();
//		button1.setText("2");
//		JButton button3 = new JButton();
//		button1.setText("3");
//		
//		playerPanel.add(button1);
//		playerPanel.add(button2);
//		playerPanel.add(button3);
		
		_playerPanelList.add(playerPanel);
		
	}
	
	/**
	 * This method creates and populates the players information panel. It sets the size
	 * and background of the panel and there is a JTextPane on top of it that displays each player's 
	 * information and the number of tokens that a player has been collected.
	 * @author Ian,Ken 04-10-16
	 */
	private void createAndPopulatePlayerInfoPanel() {
		_playerInfoPanel = new JPanel();
		_playerInfoPanel.setPreferredSize(new Dimension(690,250));
		_playerInfoPanel.setBackground(new Color(245,245,220));
		_playerInfoPanel.setLayout(new FlowLayout());
		_playerInfo = new JTextPane();
		//_playerInfo.setPreferredSize(new Dimension(575,180));
		_playerInfo.setPreferredSize(new Dimension(250,250));
		_playerInfoPanel.add(_playerInfo);
		
		//THIS SHOULD TAKE THE FIRST PLAYER'S INGREDIENTANDWANDPANEL AND ADD IT TO PLAYERINFOPANEL
		_playerInfoPanel.add(_playerPanelList.get(0));
		
		
		_playerInfo.setBackground(new Color(245,245,220));
	
		
		String t1 = "";
		int cp = -1;
		for(int i = 0; i < _gb.getPlayers().length; i++){
			Player p = _gb.getPlayers()[i];
			if(p == GameBoard.CURRENTPLAYER){cp = i;}
			if(p != GameBoard.CURRENTPLAYER){
				String t2 = "";
				t1 = t1 + "Player " + i + ": " + p.getName() + " (" + p.getColor() + " Pawn) -- Tokens Coll.: ";
				for(Token t: p.getTokens()){
					t2 = t2 + t.getValue() + " ";
				}
				t1 = t1 + t2 + "\n";
			}
		}
		Player p = GameBoard.CURRENTPLAYER;
		String tokens = "";
		for(Token t: p.getTokens()){
			tokens = tokens + t.getValue() + " ";
		}
		_playerInfo.setText("\t\t\t\tPLAYER INFO\n\nCurrent Player (" + cp +"): " + p.getName() + " (" + 
				p.getColor() + " Pawn) \n" /*Current Score: "+
				p.getScore() + "\n" */+ "My Tokens Collected: " + tokens
				+ "\n\n" + t1);
		_playerInfo.setFont(new Font("Garamond", Font.BOLD, 14));
	}
	
	
	/**
	 * This method creates and populates the board panel. It sets the size,
	 * background, and layout of the gameboard, adds 49 JButtons to it which 
	 * represents the 49 tiles on gameboard, and add ActionListener to these buttons. 
	 * @author Ian,Ken 04-10-16
	 */
	private void createAndPopulateBoardPanel() {
		_boardPanel = new JPanel();
		_boardPanel.setSize(560,560);
		_boardPanel.setLayout(new GridLayout(7,7));
		for(int i = 0; i < 7; i++){	
			for(int j = 0; j < 7; j++){	
				JButton button = new JButton();

				button.setPreferredSize(new Dimension(80,80));
				button.setLayout(new FlowLayout());
				button.addActionListener(new GameBoardButtonHandler(i,j,_gb, this));
				_boardPanel.add(button);

				
			}
		}

	}

	/**
	 * This method creates and populates the shiftable tile panel. It sets the size,
	 * background, and layout of the panel. This method also creates three JButtons, 
	 * and add ActionsListeners to _rotateCounterClockwise and _rotateClockwise buttons.
	 * @author Ian,Ken 04-10-16
	 */
	private void createAndPopulateShiftableTilePanel() {
		_shiftableTilePanel = new JPanel();
		_shiftableTilePanel.setBackground(new Color(245,245,220));
		_shiftableTilePanel.setSize(500,180);
		_shiftableTilePanel.setLayout(new GridLayout(1,3));
		
		_shiftableTileButton = new JButton();
		_rotateCounterClockwise = new JButton();
		_rotateClockwise = new JButton();
		_shiftableTileButton.setPreferredSize(new Dimension(175,175));
		_rotateCounterClockwise.setPreferredSize(new Dimension(175,175));
		_rotateClockwise.setPreferredSize(new Dimension(175,175));
		_shiftableTileButton.setBackground(new Color(245,245,220));
		_rotateCounterClockwise.setBackground(new Color(245,245,220));
		_rotateClockwise.setBackground(new Color(245,245,220));
		_rotateCounterClockwise.addActionListener(new ActionListener(){
			
			
			/**
			 * This method adds ActionListener to the _rotateCounterClockwise button, so
			 * when a player clicks that button, the shiftable tile will shift counterclockwise
			 * by 90 degree.
			 * @param e action event generated when counterclockwise button clicked
			 * @author Ian,Ken 04-10-16
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(GameBoard.CURRENTPLAYER.getHasInsertedThisTurn()){
					_gameFeedback.setText("\t\t\t\tGAME INFO\n\nIt is now " + GameBoard.CURRENTPLAYER.getName() +
							"'s (" + GameBoard.CURRENTPLAYER.getColor() + " pawn) turn."+
							"\nCurrent Collectible Token Number: " + _gb.getCurrentTargetTokenValue()+"\n\nYou cannot rotate the shiftable tile because you have"
							+" already inserted this turn.");
					_gameFeedback.setFont(new Font("Garamond", Font.BOLD, 14));
				}
				if(!GameBoard.CURRENTPLAYER.getHasInsertedThisTurn()){
					GameBoard.CURRENTPLAYER.rotateShiftableTile(90);
					update();
				}
			}}
		);
		_rotateClockwise.addActionListener(new ActionListener(){
			
			/**
			 * This method add ActionListener to the _rotateClockwise button, so
			 * when a player clicks that button, the shiftable tile will shift clockwise
			 * by 90 degree. And catch the exception if it occurs. 
			 * @param e action event generated when counterclockwise button clicked
			 * @author Ian,Ken 04-10-16
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(GameBoard.CURRENTPLAYER.getHasInsertedThisTurn()){
					_gameFeedback.setText("\t\t\t\tGAME INFO\n\nIt is now " + GameBoard.CURRENTPLAYER.getName() +
							"'s (" + GameBoard.CURRENTPLAYER.getColor() + " pawn) turn."+
									"\nCurrent Collectible Token Number: " + _gb.getCurrentTargetTokenValue()+ "\n\nYou cannot rotate the shiftable tile because you have"
							+" already inserted this turn.");
					_gameFeedback.setFont(new Font("Garamond", Font.BOLD, 14));
				}
				if(!GameBoard.CURRENTPLAYER.getHasInsertedThisTurn()){
					GameBoard.CURRENTPLAYER.rotateShiftableTile(-90);
					update();
				}
			}}
		);
		try{
			Image rotateLeft = ImageIO.read(getClass().getResourceAsStream("images/rotateLeft1.gif"));
			Image rotateRight = ImageIO.read(getClass().getResourceAsStream("images/rotateRight1.gif"));
			//_rotateCounterClockwise.setText("Rotate 90 Degrees");
			_rotateCounterClockwise.setIcon(new ImageIcon(rotateLeft));
			
			_rotateClockwise.setIcon(new ImageIcon(rotateRight));
			//_rotateClockwise.setText("Rotate -90 degrees");
		}
		catch (IOException ex){}
		MoveableTile shiftableTile = _gb.getMoveableTileArray().get(0);
		_shiftableTileButton.setIcon(new ImageIcon(generateImageForTile(shiftableTile)));
		_shiftableTilePanel.add(_rotateCounterClockwise);
		_shiftableTilePanel.add(_shiftableTileButton);
		_shiftableTilePanel.add(_rotateClockwise);
		
		
		_shiftableTilePanel.add(_rotateCounterClockwise);
		_shiftableTilePanel.add(_shiftableTileButton);
		_shiftableTilePanel.add(_rotateClockwise);
	}
	
	/**
	 * This method puts the images to the tiles on the gameboard.
	 * @param at the AbstractTile class
	 * @return img the images of the tiles
	 * @author Ian Weijin
	 */
	public Image generateImageForTile(AbstractTile at){
		Image img = null;
		int t, b, l, r;
		if(at!=null){
			t = at.getTop();
			b = at.getBottom();
			l = at.getLeft();
			r = at.getRight();
			for(int i = 0; i < 7; i++){
				for(int j = 0; j < 7; j++){
					if(_gb.getBoard()[i][j] == at && ((i==2 && j==2) || (i==2 && j==4) || 
							(i==4 && j==2) || (i==4 && j==4))){
						try{
							if(i==2 && j==2){
								img = ImageIO.read(getClass().getResource("images/TBrownStart.gif"));
								return img;
							}
								
							else if(i==2 && j==4){
								img = ImageIO.read(getClass().getResource("images/TBlueStart.gif"));
								return img;
							}
							else if(i==4 && j==2){
								img = ImageIO.read(getClass().getResource("images/TRedStart.gif"));
								return img;
							}
							else if(i==4 && j==4){
								img = ImageIO.read(getClass().getResource("images/TWhiteStart.gif"));
								return img;
							}
						} catch (IOException ex) {}
					}
				}
			}
		
			try{
				//T Tile
				if(t==1 && b==0 && l==1 && r==1){
	            	 img = ImageIO.read(getClass().getResource("images/T0.gif"));
	            }
				else if(t==1 && b==1 && l==1 && r==0){
	            	 img = ImageIO.read(getClass().getResource("images/T90.gif"));
	            }
				else if(t==0 && b==1 && l==1 && r==1){
	            	 img = ImageIO.read(getClass().getResource("images/T180.gif"));
	            }
				else if(t==1 && b==1 && l==0 && r==1){
	            	 img = ImageIO.read(getClass().getResource("images/T270.gif"));
	            }
				
				//L Tile
				else if(t==1 && b==0 && l==0 && r==1){
	            	 img = ImageIO.read(getClass().getResource("images/L0.gif"));
	            }
				else if(t==1 && b==0 && l==1 && r==0){
	            	 img = ImageIO.read(getClass().getResource("images/L90.gif"));
	            }
				else if(t==0 && b==1 && l==1 && r==0){
	            	 img = ImageIO.read(getClass().getResource("images/L180.gif"));
	            }
				else if(t==0 && b==1 && l==0 && r==1){
	            	 img = ImageIO.read(getClass().getResource("images/L270.gif"));
	            }
				
				//I Tile
				else if(t==0 && b==0 && l==1 && r==1){
	            	 img = ImageIO.read(getClass().getResource("images/I0.gif"));
	            }
				else if(t==1 && b==1 && l==0 && r==0){
	            	 img = ImageIO.read(getClass().getResource("images/I90.gif"));
	            }
				else if(t==0 && b==0 && l==1 && r==1){
	            	 img = ImageIO.read(getClass().getResource("images/I180.gif"));
	            }
				else if(t==1 && b==1 && l==0 && r==0) { //(t==1 && b==1 && l==0 && r==0)
	            	 img = ImageIO.read(getClass().getResource("images/I270.gif"));
	            }
			} catch (IOException ex) {}
		}
		return img;
	}
	
	



	/**
	 * This method refreshes the GUI
	 * @author Ian,Weijin
	 */
	@Override public void update() {
		
		_gb.setHasBeenModified();
		
		for(int i = 0; i < 7; i++){
			for(int j = 0; j < 7; j++){
				JButton button = (JButton) _boardPanel.getComponent((i*7)+j);
				button.removeAll();
				AbstractTile at = _gb.getBoard()[i][j];
				Image icon = generateImageForTile(at);
				button.setIcon(new ImageIcon(icon));
				
				if(at.hasToken()){
					JButton tokenButton = new JButton();
					button.add(tokenButton);
					tokenButton.setPreferredSize(new Dimension(25,25));
					tokenButton.addActionListener(new TokenButtonHandler(i,j,_gb,tokenButton));
					Token token = at.getToken();
					
					try{
						int val = token.getValue();
						String path = "images/tok" + val + ".bmp";
						Image img = ImageIO.read(getClass().getResourceAsStream(path));
						tokenButton.setIcon(new ImageIcon(img));
					} catch (IOException ex) {}
				}
				
				if(at.hasPlayer()){
					ArrayList<Player> list = at.getPlayers();
					for(Player p: list){
						String s = p.getColor();
						char c = s.charAt(0);
						JButton playerButton = new JButton();
						String path = null;
						if(c=='T'){
							path = "images/PawnBrown.bmp";
						}
						else if(c=='B'){
							path = "images/PawnBlue.bmp";
						}
						else if(c=='R'){
							path = "images/PawnRed.bmp";
						}
						else if(c=='W'){
							path = "images/PawnWhite.bmp";
						}
						try{
							Image img = ImageIO.read(getClass().getResourceAsStream(path));
							playerButton.setIcon(new ImageIcon(img));
							button.add(playerButton);
							playerButton.setPreferredSize(new Dimension(15,15));
						} catch (IOException ex) {}
					}
				}
			}
		}
		
		
		
		
		
		//INSERT IF STATEMENTS HERE TO CONTROL FOR WHICH PLAYERPANEL IS ADDED TO THE TOP RIGHT
		//IS NEEDED: A PANEL THAT IS STABLE/OF FIXED SIZE
		_playerInfoPanel.remove(1);
		_playerInfoPanel.add(_playerPanelList.get(GameBoard.currentPlayerIndex));
		//COME BACK HERE LATER
		
		
		
		
		
		String t1 = "";
		int cp = -1;
		for(int i = 0; i < _gb.getPlayers().length; i++){
			Player p = _gb.getPlayers()[i];
			if(p == GameBoard.CURRENTPLAYER){cp = i;}
			if(p != GameBoard.CURRENTPLAYER){
				String t2 = "";
				t1 = t1 + "Player " + i + ": " + p.getName() + " (" + p.getColor() + " Pawn) -- Tokens Coll.: ";
				for(Token t: p.getTokens()){
					t2 = t2 + t.getValue() + " ";
				}
				t1 = t1 + t2 + "\n";
			}
		}
		Player p = GameBoard.CURRENTPLAYER;
		String tokens = "";
		for(Token t: p.getTokens()){
			tokens = tokens + t.getValue() + " ";
		}
		_playerInfo.setText("\t\t\t\tPLAYER INFO\n\nCurrent Player (" + cp +"): " + p.getName() + " (" + 
				p.getColor() + " Pawn) \n" /*Current Score: "+
				p.getScore() + "\n" */+ "My Tokens Collected: " + tokens
				+ "\n\n" + t1);
		_playerInfo.setFont(new Font("Garamond", Font.BOLD, 14));
		
		MoveableTile shiftableTile = _gb.getMoveableTileArray().get(0);
		_shiftableTileButton.setIcon(new ImageIcon(generateImageForTile(shiftableTile)));
		_window.repaint();
	}
	
	
	/**
	 * This method refreshes the GUI when the game is over
	 * @author Ian,Weijin
	 */
	public void updateGameIsOver() {
		
		for(int i = 0; i < 7; i++){
			for(int j = 0; j < 7; j++){
				JButton button = (JButton) _boardPanel.getComponent((i*7)+j);
				button.removeAll();
				AbstractTile at = _gb.getBoard()[i][j];
				Image icon = generateImageForTile(at);
				button.setIcon(new ImageIcon(icon));
				button.setEnabled(false);
				//button.setLayout(new FlowLayout());
				if(at.hasToken()){
					JButton tokenButton = new JButton();
					button.add(tokenButton);
					tokenButton.setPreferredSize(new Dimension(25,25));
					tokenButton.addActionListener(new TokenButtonHandler(i,j,_gb,tokenButton));
					tokenButton.setEnabled(false);
					Token token = at.getToken();
					
					try{
						int val = token.getValue();
						String path = "images/tok" + val + ".bmp";
						Image img = ImageIO.read(getClass().getResourceAsStream(path));
						tokenButton.setIcon(new ImageIcon(img));
					} catch (IOException ex) {}
					
				}
				if(at.hasPlayer()){
					ArrayList<Player> list = at.getPlayers();
					for(Player p: list){
						String s = p.getColor();
						char c = s.charAt(0);
						JButton playerButton = new JButton();
						String path = null;
						if(c=='T'){
							path = "images/PawnBrown.bmp";
						}
						else if(c=='B'){
							path = "images/PawnBlue.bmp";
						}
						else if(c=='R'){
							path = "images/PawnRed.bmp";
						}
						else if(c=='W'){
							path = "images/PawnWhite.bmp";
						}
						try{
							Image img = ImageIO.read(getClass().getResourceAsStream(path));
							playerButton.setIcon(new ImageIcon(img));
							button.add(playerButton);
							playerButton.setPreferredSize(new Dimension(15,15));
						} catch (IOException ex) {}
						playerButton.setEnabled(false);
					}
				}
			}
		}
		
		String t1 = "";
		int cp = -1;
		for(int i = 0; i < _gb.getPlayers().length; i++){
			Player p = _gb.getPlayers()[i];
			if(p == GameBoard.CURRENTPLAYER){cp = i;}
			if(p != GameBoard.CURRENTPLAYER){
				String t2 = "";
				t1 = t1 + "Player " + i + ": " + p.getName() + " (" + p.getColor() + " Pawn) -- Tokens Coll.: ";
				for(Token t: p.getTokens()){
					t2 = t2 + t.getValue() + " ";
				}
				t1 = t1 + t2 + "\n";
			}
		}
		Player p = GameBoard.CURRENTPLAYER;
		String tokens = "";
		for(Token t: p.getTokens()){
			tokens = tokens + t.getValue() + " ";
		}
		_playerInfo.setText("\t\t\t\tPLAYER INFO\n\nCurrent Player (" + cp +"): " + p.getName() + " (" + 
				p.getColor() + " Pawn) \n" /*Current Score: "+
				p.getScore() + "\n" */+ "My Tokens Collected: " + tokens
				+ "\n\n" + t1);
		_playerInfo.setFont(new Font("Garamond", Font.BOLD, 14));
		
		MoveableTile shiftableTile = _gb.getMoveableTileArray().get(0);
		_shiftableTileButton.setIcon(new ImageIcon(generateImageForTile(shiftableTile)));
		
		_window.pack();
		_window.repaint();	
	}
	
	/**
	 * This method sets the text that appears on the game feedback panel
	 * @param s the string of text 
	 * @author Ian,Satya 04-14-16
	 */
	public void updateGameFeedBack(String s){
		_gameFeedback.setText(s);
		_gameFeedback.setFont(new Font("Garamond", Font.BOLD, 14));
	}
	
	public JPanel getPlayerWandAndIngredientPanel(){
		
		return _playerPanelList.get(GameBoard.currentPlayerIndex);
	}

	/**
	 * This method is triggered upon the player picking up token with value 25 and the player calling
	 * the toggleNextToken() method after picking up this 25 value token.  This method sorts the 
	 * players by score from highest to lowest and then prints "GAME OVER" along with the winner, his 
	 * name and score and that of the runners up.
	 * @author Satya, Ian 04-14-16
	 */
	public void gameOver() {
		_gb.GAMEOVER = true;
		
		//update each player's score according to their formula cards and wands here
		
		
		updateGameIsOver();
		_rotateClockwise.setEnabled(false);
		_rotateCounterClockwise.setEnabled(false);
		_endTurnButton.setEnabled(false);
		_saveButton.setEnabled(false);

		
		int length = _gb.getPlayers().length;
		Player[] temp = new Player[length];
		for(int i = 0; i < length; i++){
			temp[i] = _gb.getPlayers()[i];
			_gb.getPlayers()[i].calculateScore();		//This should add points for matching ingredients and remaining wands
		}
		for(int i = 0; i < length; i++){
			
			for(int j = i+1; j<length; j++){
				int runningMax = temp[i].getScore();
				if(temp[j].getScore() > runningMax){
					runningMax = temp[j].getScore();
					Player p_temp = temp[i];
					temp[i] = temp[j];
					temp[j] = p_temp;
				}
			}
		}
		String s1 = "The winner and Master Wizard is ";
		String s2 = "Second place: ";
		String s3 = "Third place: ";
		String s4 = "Fourth place: ";
		
		if(length == 2){
			s1 = s1 + temp[0].getName() + " with " + temp[0].getScore() + " points!";
			s2 = s2 + temp[1].getName() + " with " + temp[1].getScore() + " points.";
		}
		if(length == 3){
			s1 = s1 + temp[0].getName() + " with " + temp[0].getScore() + " points!";
			s2 = s2 + temp[1].getName() + " with " + temp[1].getScore() + " points.";
			s3 = s3 + temp[2].getName() + " with " + temp[2].getScore() + " points.";
		}
		if(length == 4){
			s1 = s1 + temp[0].getName() + " with " + temp[0].getScore() + " points!";
			s2 = s2 + temp[1].getName() + " with " + temp[1].getScore() + " points.";
			s3 = s3 + temp[2].getName() + " with " + temp[2].getScore() + " points.";
			s4 = s4 + temp[3].getName() + " with " + temp[3].getScore() + " points.";
		}
		
		if(length == 2){
			_gameFeedback.setText("\t\t\t\t***GAME OVER!***\n\n" + s1 + "\n" + s2);
			_gameFeedback.setFont(new Font("Garamond", Font.BOLD, 14));
		}
		if(length == 3){
			_gameFeedback.setText("\t\t\t\t***GAME OVER!***\n\n" + s1 + "\n" + s2 + "\n" + s3);
			_gameFeedback.setFont(new Font("Garamond", Font.BOLD, 14));
		}
		if(length == 4){
			_gameFeedback.setText("\t\t\t\t***GAME OVER!***\n\n" + s1 + "\n" + s2 + "\n" + s3 + "\n" + s4);	
			_gameFeedback.setFont(new Font("Garamond", Font.BOLD, 14));
		}
	}
	
	



	
} //end of GameBoardGUI class definition
