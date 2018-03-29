package code.model;

import java.awt.Dimension;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import code.gui.GameBoardGUI;
import code.gui.WandButtonHandler;
import code.gui.images.*;

public class Wand {

	JButton _button;
	JPanel _wandPanel;
	Player _player;
	GameBoard _gameBoard;
	
	public Wand(GameBoardGUI gui, Player player, GameBoard gameBoard){
		_button = new JButton();
		_button.setPreferredSize(new Dimension(40,40));
		_button.setVisible(true);
	
		
		_player = player;
		_gameBoard = gameBoard;
		

		try{
			Image img = ImageIO.read(getClass().getResourceAsStream("wandz.png"));
			_button.setIcon(new ImageIcon(img));
		} 
		catch (IOException ex) {System.out.println("Sorry! Can't find this image!");}
		
		
		_button.addActionListener(new WandButtonHandler(gui, _button, _player, _gameBoard));
		
	}
	

	
	public JButton getWandButton(){
		return _button;
	}
	
	
	
	
}
