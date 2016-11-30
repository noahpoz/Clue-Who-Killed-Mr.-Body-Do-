package models;

import java.util.ArrayList;
import java.util.Random;

public class Game {
	
	private int _numPlayers;
	private ArrayList<String> _selectablePlayers;
	private ArrayList<String> _weapons;
	private ArrayList<Player> _players;
	private ArrayList<Room> _rooms;
	private ArrayList<Card> _deck;
	private Suggestion _envelope;
	
	public Game(int numPlayers) {
		_numPlayers = numPlayers;
		playerBootstrap();
		weaponBootstrap();
		roomBootstrap();
		generateDeck();
		putCardsInEnvelope();
	}
	
	public void switchPlayers() {
		_players.add(_players.remove(0));
		
		if (!_players.get(0).isInGame()) {
			switchPlayers();
		}
	}
	
	public Player getCurrentPlayer() { return _players.get(0); }
	public ArrayList<Player> getPlayers() { return _players; }
	public ArrayList<Room> getRooms() { return _rooms; }
	public Suggestion getEnvelope() { return _envelope; }
	
	public void addPlayer(String name) { _players.add(new Player(name, this)); }
	
	private void playerBootstrap() {
		_selectablePlayers = new ArrayList<String>();
		_selectablePlayers.add("Ms. Vivienne Scarlet");
		_selectablePlayers.add("Colonel Michael Mustard");
		_selectablePlayers.add("Reverend Jonathan Green");
		_selectablePlayers.add("Mrs. Elizabeth Peacock");
		_selectablePlayers.add("Mrs. Blanche White");
		_selectablePlayers.add("Professor Matthew Hertz");
	}
	
	private void weaponBootstrap() {
		_weapons = new ArrayList<String>();
		_weapons.add("Candlestick");
		_weapons.add("Rope");
		_weapons.add("Gloves");
		_weapons.add("Horseshoe");
		_weapons.add("Knife");
		_weapons.add("Lead Pipe");
		_weapons.add("Revolver");
		_weapons.add("CSE 116 Textbook");
	}
	
	private void roomBootstrap() {
		_rooms = new ArrayList<Room>();
		_rooms.add(new Room("Study"));
		_rooms.add(new Room("Lounge"));
		_rooms.add(new Room("Library"));
		_rooms.add(new Room("Billiard Room"));
		_rooms.add(new Room("Dining Room"));
		_rooms.add(new Room("Ballroom"));
		_rooms.add(new Room("Kitchen"));
		_rooms.add(new Room("TA Office"));
		_rooms.add(new Room("Baldy 21"));
	}
	
	public void generateDeck() {
		for (int i = 0; i < _selectablePlayers.size(); i++) {
			_deck.add(new Card(_selectablePlayers.get(i)));
		}
		
		for (int i = 0; i < _weapons.size(); i++) {
			_deck.add(new Card(_weapons.get(i)));
		}
		
		for (int i = 0; i < _rooms.size(); i++) {
			_deck.add(new Card(_rooms.get(i).getName()));
		}
	}
	
	public void putCardsInEnvelope() {
		Random rand = new Random();
		String player = _deck.remove(rand.nextInt(_selectablePlayers.size())).getName();
		String weapon = _deck.remove(rand.nextInt(_weapons.size()) + _selectablePlayers.size()).getName();
		String room = _deck.remove(rand.nextInt(_rooms.size()) + _selectablePlayers.size() + _weapons.size()).getName();
		
		_envelope = new Suggestion(player, weapon, room);
	}
	
	public void shuffleDeck() {
		ArrayList<Card> newDeck = new ArrayList<Card>();
		Random rand = new Random();
		
	}
	
	public void delegateCards() {
		for (int i = 0; i < _deck.size(); i++) {
			
		}
	}
	
	public int diceRoll() {
		Random rand = new Random();
		return rand.nextInt(6) + 1;
	}
}
