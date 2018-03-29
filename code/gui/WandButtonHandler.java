package code.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import code.model.GameBoard;
import code.model.Player;
import code.model.Wand;



public class WandButtonHandler implements ActionListener {
	
	private JButton _button;
	private GameBoardGUI _gui;
	private Player _player;
	private GameBoard _gameBoard;

	
	public WandButtonHandler(GameBoardGUI gui, JButton button, Player player, GameBoard gameBoard){
		_gui = gui;
		_button = button;
		_player = player;
		_gameBoard = gameBoard;

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		boolean hasNotUsedWandThisRound = _gameBoard.getCanUseWand();
		
		if (hasNotUsedWandThisRound){
			
			if(_player.getHasInsertedThisTurn()==true){
				_player.setHasMovedThisTurn();
				_player.setHasInsertedThisTurn();
				_player.decrementWandCount();
				
				_button.removeActionListener(this);
				_gui.getPlayerWandAndIngredientPanel().remove(_button);
				_gameBoard.setCanUseWand(false);
				_gui.update();
			}
			
			else{
				JFrame invalidWandInvocationFrame = new JFrame();
				JOptionPane.showMessageDialog(invalidWandInvocationFrame, "You cannot use a wand until you have both shifted and moved your pawn.");	
			}
			
		}
		
		else{
			JFrame alreadyUsedOneWandThisTurnFrame = new JFrame();
			JOptionPane.showMessageDialog(alreadyUsedOneWandThisTurnFrame, "You have already used a wand this turn. Please end your turn.");
		}
		
		
		

		

	}

}
