package model;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import ui.UI;

/**
 * Serves as the "Model" in this program's Model/UI pattern. Responsible for maintaining the data
 * necessary for game play and enforces the rules.
 */

public class Game {

	static int HALL_SPACE = 0;
	static int DOOR_SPACE = 4;
	static int SECRET_PASSAGE = 5;

	private UI _ui;
	private Board _board;

	private ArrayList<Card> _deck = new ArrayList<Card>();
	private ArrayList<Card> _envelope = new ArrayList<Card>();

	private ArrayList<Player> _players = new ArrayList<Player>();
	private ArrayList<Room> _rooms = new ArrayList<Room>();

	private int _lastDiceRoll = -1;
	private int _remainingMoves = 0;

	/**
	 * Index in _players of the current Player
	 */
	private int _currentTurn = 0;

	/**
	 * State 1: Awaiting Dice Roll <p>
	 * State 2: Player is in the process of moving <p>
	 * State 3: Player has completed move and may choose to make a suggestion <p>
	 */
	private int _gameState = 0;

	public Game(int numPlayers) {
		readGameInfo();
		distributeCards();
		_board = new Board(_rooms);
		informDoors();
		
		System.out.println("Player order: " + _players);
	}

	/**
	 * Reads information from default_players_weapons_cards.txt, creates the envelope,
	 * constructs the deck, creates the list of players.
	 */
	private void readGameInfo() {

		ArrayList<Card> weapons = new ArrayList<Card>();
		ArrayList<Card> rooms = new ArrayList<Card>();
		ArrayList<Card> players = new ArrayList<Card>();

		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader("default_players_weapons_cards.txt"));		
			String line = br.readLine();

			int i = 0;
			while (!line.equals("END")) {
				players.add(new Card(line));
				Player p = new Player(line);
				_players.add(p);
				p.setLocation(Board._startingSpots[i]);
				line = br.readLine();
				i++;
			}

			line = br.readLine();
			while (!line.equals("END")) {
				weapons.add(new Card(line));
				line = br.readLine();
			}

			line = br.readLine();
			while (!line.equals("END")) {
				rooms.add(new Card(line));
				_rooms.add(new Room(line));
				line = br.readLine();
			}

			Collections.shuffle(players);
			Collections.shuffle(weapons);
			Collections.shuffle(rooms);

			_envelope.add(weapons.remove(0));
			_envelope.add(rooms.remove(0));
			_envelope.add(players.remove(0));

			_deck.addAll(players);
			_deck.addAll(weapons);
			_deck.addAll(rooms);
			Collections.shuffle(_deck);

			/* individual lists for players, weapons, and rooms will be disposed of, because they are referred to
			   by local variables */

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Evenly distribute the remaining cards (after the creation of the envelope) to all the players.
	 */
	private void distributeCards() {
		Collections.shuffle(_players);
		while (_deck.size() > 0) {
			_players.get(0).addCardToHand(_deck.remove(0));
			_players.add(_players.remove(0)); // shift the ordering of the players by 1 for even distribution
		}

	}

	public void diceRoll() {
		Random rand = new Random();
		_lastDiceRoll = rand.nextInt(6) + 1;
		_remainingMoves = _lastDiceRoll;
		_gameState += 1;
		_ui.update();
	}

	public int getLastDiceRoll() {
		return _lastDiceRoll;
	}

	public int getRemainingMoves() {
		return _remainingMoves;
	}

	public BoardSpace[][] getBoard() {
		return _board.getBoard();
	}

	public ArrayList<String> getEnvelope() {
		return null;
	}

	public String whoseTurn() {
		return _players.get(_currentTurn).getName();
	}

	public int getTurnIndex() {
		return _currentTurn;
	}

	public ArrayList<Point> getPlayerLocations() {

		ArrayList<Point> points = new ArrayList<Point>();
		for (Player p : _players) {
			points.add(p.getLocation());
		}

		return points;
	}

	/**
	 * @return ArrayList<String> of the names of the cards in the current player's hand.
	 */
	public ArrayList<String> getCurrentPlayersCards() {
		ArrayList<String> cards = new ArrayList<String>();
		for (Card c : _players.get(_currentTurn).getCards()) {
			cards.add(c.getName());
		}

		return cards;
	}

	/**
	 * @return ArrayList<Point> of locations where the current player is allowed to move.
	 */
	public ArrayList<Point> getValidMoveLocations() {

		Player currentPlayer = _players.get(_currentTurn);
		Point playerLoc = currentPlayer.getLocation();
		ArrayList<Point> locs = new ArrayList<Point>();

		if (currentPlayer.getResidence() == null) {
			locs = getValidMovesFromPoint(playerLoc);
		} else {

			BoardSpace[][] grid = _board.getBoard();
			for (int i = 0; i < grid.length; i++) {
				for (int j = 0; j < grid[i].length; j++) {

					if (grid[i][j].getID() == Game.DOOR_SPACE) {
						if (((DoorSpace) grid[i][j]).room.equals(currentPlayer.getResidence())) {
							locs.addAll(getValidMovesFromPoint(new Point(i, j)));
						}
					}
				}
			}
		}

		return locs;
	}

	public ArrayList<Point> getValidMovesFromPoint(Point p) {

		ArrayList<Point> locs = new ArrayList<Point>();
		int x = p.x, y = p.y;
		BoardSpace[][] grid = _board.getBoard();

		try {
			if (grid[x - 1][y].isMovable()) { locs.add(new Point(x - 1, y)); }
			if (grid[x + 1][y].isMovable()) { locs.add(new Point(x + 1, y)); }
			if (grid[x][y - 1].isMovable()) { locs.add(new Point(x, y - 1)); }
			if (grid[x][y + 1].isMovable()) { locs.add(new Point(x, y + 1)); }
		} catch (Exception e) { }

		return locs;
	}
	
	public boolean isValidMoveLocation(Point p) {
		return getValidMoveLocations().contains(p);
	}

	/**
	 * Gives each DoorSpace a reference to its adjacent Room. 
	 */
	private void informDoors() {

		BoardSpace[][] grid = _board.getBoard();
		Point[] points = { 
				new Point(7, 4), 
				new Point(10, 4),
				new Point(12, 7),
				new Point(13, 7),
				new Point(18, 6), 
				new Point(18, 10), 
				new Point(17, 13), 
				new Point(20, 19), 
				new Point(16, 20), 
				new Point(15, 18), 
				new Point(10, 18), 
				new Point(9, 20), 
				new Point(5, 20), 
				new Point(6, 16), 
				new Point(2, 13), 
				new Point(4, 11), 
				new Point(7, 9) 
		};

		Room[] rooms = new Room[17];

		int room = 0;

		// Study
		rooms[0] = _rooms.get(room); 

		// Hall
		room++;
		rooms[1] = _rooms.get(room); 
		rooms[2] = _rooms.get(room);
		rooms[3] = _rooms.get(room);

		// Lounge
		room++;
		rooms[4] = _rooms.get(room);

		//Dining Room
		room++;
		rooms[5] = _rooms.get(room);
		rooms[6] = _rooms.get(room);

		// Kitchen
		room++;
		rooms[7] = _rooms.get(room);

		// Ballroom
		room++;
		rooms[8] = _rooms.get(room);
		rooms[9] = _rooms.get(room);
		rooms[10] = _rooms.get(room);
		rooms[11] = _rooms.get(room);

		// Conservatory
		room++;
		rooms[12] = _rooms.get(room);

		// Billiard Room
		room++;
		rooms[13] = _rooms.get(room);
		rooms[14] = _rooms.get(room);

		// Library
		room++;
		rooms[15] = _rooms.get(room);
		rooms[16] = _rooms.get(room);

		int i = 0;
		for (Point p : points) {
			((DoorSpace) _board.getBoard()[p.x][p.y]).room = rooms[i];
			i++;
		}
	}

	public void skip() {
		nextPlayer();
		_ui.update();
	}

	/**
	 * Game logic for determining what to do when the UI receives a click on the game board.
	 * 
	 * @param p Position on Board of the mouse click
	 */
	
	public void tileClicked(Point p) {

		Player currentPlayer = _players.get(_currentTurn);

		if (isValidMoveLocation(p) && _gameState == 1 && !_board.getBoard()[p.x][p.y].containsPlayer()) {

			Point prevLoc = currentPlayer.getLocation();

			// tell the space where the player used to be that there is no longer a player there
			_board.getBoard()[prevLoc.x][prevLoc.y].setContainsPlayer(false);

			_remainingMoves--;

			// check if Player has just entered a room
			if (_board.getBoard()[p.x][p.y].getID() == Game.DOOR_SPACE) {

				Room entered = ((DoorSpace) _board.getBoard()[p.x][p.y]).room;

				BoardSpace[][] grid = _board.getBoard();
				boolean done = false;
				for (int i = 0; i < grid.length; i++) {
					for (int j = 0; j < grid[i].length; j++) {

						if (!playerOnSpace(new Point(i, j)) && grid[i][j].equals(entered)) {
							currentPlayer.setLocation(new Point(i, j));

							// inform the clicked space that there is now a player there
							entered.addPlayerToRoom(currentPlayer);
							currentPlayer.setResidence(entered);

							_gameState++;

							done = true;
							break;
						}
					}
					if (done) {
						break;
					}
				}

			} else {

				currentPlayer.setLocation(p);
				
				// if the player is leaving the room, we have to indicate that he is indeed leaving
				if (_board.getBoard()[p.x][p.y].getID() == Game.HALL_SPACE) {
					currentPlayer.setResidence(null);
				}

				// inform the clicked space that there is now a player there
				_board.getBoard()[p.x][p.y].setContainsPlayer(true);

				if (_remainingMoves == 0) {
					_gameState++;

					// if the player cannot make a suggestion, it's automatically the next player's turn
					if (_gameState == 2 && currentPlayer.getResidence() == null) {
						_gameState = 0;
						nextPlayer();
					}
				}
			}

			_ui.update();
		}
	}

	public boolean playerOnSpace(Point p) {

		for (Player player : _players) {
			if (player.getLocation().equals(p)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * 
	 * @param suggestion An ArrayList<String> containing a weapon, room, and player.
	 * @return null if the suggestion was not disproved, ArrayList<String> containing Player and Card if disproved 
	 */

	public ArrayList<String> makeSuggestion(ArrayList<String> suggestion) {
		
		for (Player p : _players) {

			ArrayList<Card> hand = p.getCards();
			for (Card c : hand) {
				if (suggestion.contains(c.toString())) {
					ArrayList<String> ret = new ArrayList<String>();
					ret.add(p.getName());
					ret.add(c.toString());

					nextPlayer();
					_ui.update();

					return ret;
				}
			}
		}

		// Here, I might recommend altering the game state to an "accusation" state.
		nextPlayer();
		_ui.update();
		
		return null;
	}

	/**
	 * Resets _gameState and selects the next player in the order.
	 */
	public void nextPlayer() {
		_currentTurn++;
		if (_currentTurn == _players.size()) {
			_currentTurn = 0;
		}

		_gameState = 0;
	}

	public int getGameState() {
		return _gameState;
	}

	public void setUI(UI ui) {
		_ui = ui;
	}
}
