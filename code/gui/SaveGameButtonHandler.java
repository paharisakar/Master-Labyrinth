package code.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import code.model.GameBoard;
import code.utils.SaveFileIO;

public class SaveGameButtonHandler implements ActionListener {
	
	private String _name;
	private GameBoard _board;
	
	public SaveGameButtonHandler(GameBoard board){
		_name = "SaveFile";
		_board = board;
		
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		//if first thing you've done, save, else, spit back yelly message
		if(_board.getHasBeenModified()){
			
			JFrame invalidSaveFrame = new JFrame();
			JOptionPane.showMessageDialog(invalidSaveFrame, "Sorry, you cannot save once you have modified the board.");
		}
		else{
			SaveFileIO.saveFile(_name, _board);
		}
		
	}

	
	
	
}
