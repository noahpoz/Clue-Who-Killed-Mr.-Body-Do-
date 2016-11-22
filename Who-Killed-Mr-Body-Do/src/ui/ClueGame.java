package ui;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import models.Game;

public class ClueGame {
	
	private Game _game;
	JLabel[][] _labels;
	
	public ClueGame(Game game) {
		_game = game;
		_labels = new JLabel[25][24];
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		JPanel boardPanel = new JPanel();
		boardPanel.setLayout(new GridLayout(25, 24));
		
		for (int i = 0; i < _labels.length; i++) {
			for (int j = 0; j < _labels[i].length; j++) {
				_labels[i][j] = new JLabel();
				boardPanel.add(_labels[i][j]);
			}
		}
	}
}
