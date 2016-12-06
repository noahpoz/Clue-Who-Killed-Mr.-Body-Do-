package ui;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

import javax.swing.*;

import model.BoardSpace;
import model.Game;

/**
 * 
 * Responsible for displaying data in the Model, as well as grabbing user input and sending it to the model.
 *
 */

public class UI {

	Game _game;

	public static int WINDOW_WIDTH = 1100;
	public static int WINDOW_HEIGHT = 700;
	private final int PADDING = 5;
	private final int SIDE_LENGTH = 27;
	private final int BUTTON_HEIGHT = 50;
	private final int LABEL_HEIGHT = 15;
	private final int INPUT_HEIGHT = 50;
	
	private int _boardWidth = WINDOW_HEIGHT - PADDING * 2;
	private int _boardHeight = WINDOW_HEIGHT - PADDING * 2;

	private JFrame _window;

	private JLayeredPane _mainPanel;
	private JPanel _gridPanel;
	private CardsPane _cardsPanel;
	private JLabel _instruction;
	private JLabel _message;

	private SuggestionPane _suggestionInput;
	
	private JButton _suggestion;
	private JButton _rollDice;
	private JButton _skip;

	private Timer _timer;

	private JButton[][] _gridSpaces = new JButton[SIDE_LENGTH][SIDE_LENGTH];

	// Colors are based on a UIReadble identifier
	private Color[] _spaceColors = {
			Color.decode("#e8cd7d"),  // HallSpace
			Color.decode("#734b26"),  // WallSpace
			Color.decode("#006600"),  // BorderSpace
			Color.decode("#ffffff"),  // BlankSpace
			Color.decode("#ffffff"),  // DoorSpace
			Color.decode("#fffadf"),  // Secret Passage
			Color.decode("#00cc00"),  // Study
			Color.decode("#ffff66"),  // Hall
			Color.decode("#cc66ff"),  // Lounge
			Color.decode("#996633"),  // Dining Room
			Color.decode("#99ccff"),  // Kitchen
			Color.decode("#d22d2d"),  // Ballroom
			Color.decode("#ff66cc"),  // Conservatory
			Color.decode("#617dd1"),  // Billiard Room
			Color.decode("#ff9900")}; // Library

	public UI(Game g) {

		// enable two-way interaction between the model and UI
		_game = g;
		_game.setUI(this);

		_window = new JFrame();
		_mainPanel = new JLayeredPane();

		_mainPanel.setLayout(null);
		_window.setContentPane(_mainPanel);
		_mainPanel.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
		_window.pack();

		_gridPanel = new JPanel();
		_gridPanel.setSize(WINDOW_HEIGHT - PADDING * 2, WINDOW_HEIGHT - PADDING * 2);
		_gridPanel.setLocation(PADDING, PADDING);
		_gridPanel.setLayout(new GridLayout(SIDE_LENGTH, SIDE_LENGTH - 1));
		_mainPanel.add(_gridPanel);

		_instruction = new JLabel("");
		_instruction.setSize(WINDOW_WIDTH - (WINDOW_HEIGHT - PADDING * 2) - PADDING * 2, LABEL_HEIGHT);
		_instruction.setLocation(WINDOW_HEIGHT - PADDING * 2 + PADDING * 2, PADDING * 3);
		_mainPanel.add(_instruction);

		_cardsPanel = new CardsPane(this);
		_cardsPanel.setSize(WINDOW_WIDTH - (WINDOW_HEIGHT - PADDING * 2), WINDOW_HEIGHT / 2);
		_cardsPanel.setLocation(WINDOW_HEIGHT - PADDING * 2, PADDING * 5 + LABEL_HEIGHT);
		_mainPanel.add(_cardsPanel);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(1, 3));
		buttonPanel.setSize(WINDOW_WIDTH - (WINDOW_HEIGHT - PADDING * 2) - PADDING * 2, BUTTON_HEIGHT);
		buttonPanel.setLocation(WINDOW_HEIGHT - PADDING * 2 + PADDING, WINDOW_HEIGHT - BUTTON_HEIGHT - PADDING);
		_mainPanel.add(buttonPanel);

		_suggestion = new JButton("Suggestion");
		_rollDice = new JButton("Roll Dice");
		_skip = new JButton("Skip");
		buttonPanel.add(_suggestion);
		buttonPanel.add(_rollDice);
		buttonPanel.add(_skip);

		// Create an anonymous inner class to handle the button's action
		_suggestion.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				if (_game.getGameState() == 2) {
				
					_suggestionInput.grabSuggestionInput();
					
				} else {
					displayMessage("You cannot make a suggestion yet!");
				}
			}
		});

		_rollDice.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (_game.getGameState() == 0) {
					_game.diceRoll();
				} else {
					displayMessage("You cannot roll the dice at this time.");
				}
			}
		});
		
		_skip.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				_game.skip();
			}
		});
		
		_message = new JLabel();
		_message.setForeground(Color.RED);
		_message.setSize(WINDOW_WIDTH - (WINDOW_HEIGHT - PADDING * 2) - PADDING * 2, LABEL_HEIGHT);
		_message.setLocation(WINDOW_HEIGHT - PADDING * 2 + PADDING, WINDOW_HEIGHT - BUTTON_HEIGHT - LABEL_HEIGHT - PADDING * 4 - INPUT_HEIGHT);
		_mainPanel.add(_message);

		_suggestionInput = new SuggestionPane(this);
		_suggestionInput.setSize(WINDOW_WIDTH - (WINDOW_HEIGHT - PADDING * 2) - PADDING * 2, INPUT_HEIGHT);
		_suggestionInput.setLocation(WINDOW_HEIGHT - PADDING * 2 + PADDING, WINDOW_HEIGHT - BUTTON_HEIGHT - PADDING * 2 - INPUT_HEIGHT);
		_mainPanel.add(_suggestionInput);
		
		update();
		_window.setVisible(true);
	}
	
	/**
	 * Queries the Model to determine the accuracy of a player's suggestion.
	 * 
	 * @param suggestion ArrayList of the contents of a user's suggestion.
	 */
	
	public void passSuggestion(ArrayList<String> suggestion) {
		
		ArrayList<String> result = _game.makeSuggestion(suggestion);
		
		if (result != null) {
			
			displayMessage(result.get(0) + " has the card " + result.get(1) + "!");
			
		} else {
			
			displayMessage("No one contradicted your suggestion!");
			
		}
	}

	/**
	 * Displays a warning message at the bottom of the window that goes away after five seconds.
	 * 
	 * @param String to be displayed.
	 */
	
	public void displayMessage(String s) {

		_timer = new Timer(5000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				_message.setText("");
				_timer.stop();	
			}
		});
		_timer.start();

		_message.setText(s);
	}
	
	/**
	 * Displays non-disappearing text at the top of the screen.
	 * 
	 * @param s Instruction to be displayed.
	 */
	
	public void displayInstruction(String s) {
		_instruction.setText(s);
	}

	/**
	 * Displays the most up-to-date information about the current state of the game.
	 */
	public void update() {

		_window.setTitle(_game.whoseTurn() + "'s Turn!");

		_window.setIgnoreRepaint(true);

		_gridPanel.removeAll();
		_gridSpaces = new JButton[SIDE_LENGTH][SIDE_LENGTH];
		BoardSpace[][] board = _game.getBoard();

		for (int i = 0; i < SIDE_LENGTH; i++) {
			for (int j = 0; j < SIDE_LENGTH - 1; j++) {

				JButton button = new LocationButton(j, i);
				button.setOpaque(true);
				button.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.BLACK));

				_gridSpaces[j][i] = button;

				try {
					int id = board[j][i].getID();
					button.setBackground(_spaceColors[id]);
					if (id < 1) {
						button.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.decode("#86582d")));
					}

				} catch (Exception e) {
					// do nothing
				}

				_gridPanel.add(button);
			}
		}

		// Display the locations of the players
		ArrayList<Point> playerLocs = _game.getPlayerLocations();
		int num = 1;
		for (Point p : playerLocs) {
			JButton b = _gridSpaces[p.x][p.y];
			b.setText("" + num);
			if (num - 1 == _game.getTurnIndex()) {
				b.setBackground(Color.CYAN);
			}
			num++;
		}

		// Display state specific information
		int state = _game.getGameState();
		switch (state) {

		case 0 :
			_instruction.setText("Please roll the dice.");
			break;

		case 1 :
			// Display appropriate message
			_instruction.setText("You rolled a " + _game.getLastDiceRoll() + 
					". You have " + _game.getRemainingMoves() + " moves left.");
			
			// Display valid spots for a player to move
			ArrayList<Point> valid = _game.getValidMoveLocations();
			for (Point p : valid) {
				_gridSpaces[p.x][p.y].setBackground(Color.RED);
			}
			
			break;
			
		case 2:
			_instruction.setText("You may make a suggestion now.");
			
			break;
		}

		SwingUtilities.invokeLater(new Runnable() {
			@Override 
			public void run() {
				JPanel namesake = new JPanel() {

					@Override
					public void paint(Graphics g) {
						super.paint(g);
						
						Graphics2D g2 = (Graphics2D) g;
						g2.setFont(new Font("Sans Serif", Font.BOLD, 14));
						g2.drawString("Who Killed", 7, 15);
						g2.drawString("Mr. Body Do?", 1, 33);
					}

				};
				
				// keep the positioning proportional, so that the window can be resized
				namesake.setLocation((int) (_boardWidth / 2.43), (int) (_boardHeight / 2.3));
				
				namesake.setSize(100, 50);
				namesake.setBackground(Color.WHITE);
				_mainPanel.add(namesake);
				_mainPanel.setComponentZOrder(namesake, 0); // bring this panel to the front
			}
		});

		_cardsPanel.removeAllCards();
		for (String s : _game.getCurrentPlayersCards()) {
			_cardsPanel.addCard(s);
		}
		_cardsPanel.drawCards();

		_window.setIgnoreRepaint(false);
		_window.repaint();
	}
	
	private class LocationButton extends JButton {
		
		private Point _gridLocation;
		
		public LocationButton(int x, int y) {
			super();
			_gridLocation = new Point(x, y);
			
			this.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					
					_game.tileClicked(_gridLocation);
					
				}
			});
		}
		
		public Point getLocation() {
			return _gridLocation;
		}
	}
}
