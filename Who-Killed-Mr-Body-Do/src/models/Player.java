package models;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;

public class Player {
	
	private String _name;
	private ArrayList<Card> _cards;
	private int x;
	private int y;
	private boolean _inGame;
	private HashMap<String, Boolean> _checklist;
	private Game _game;
	
	public Player(String name, Game game) {
		_cards = new ArrayList<Card>();
		_name = name;
		_inGame = true;
		_checklist = new HashMap<String, Boolean>();
		_game = game;
	}
	
	public void addCardToHand(Card card) {
		_cards.add(card);
	}
	
	public String getName() { return _name; }
	private ArrayList<Card> getCards() { return _cards; }
	public boolean isInGame() { return _inGame; }
	public HashMap<String, Boolean> getChecklist() { return _checklist; }
	
	public ArrayList<Point> getAllMoves() {
		ArrayList<Point> moves = new ArrayList<Point>();
		return moves;
	}
	
	public ArrayList<Point> getAllMoves(int x, int y) {
		return null;
	}
	
	public boolean makeSuggestion(String player, String weapon, String room) {
		Suggestion newSuggestion = new Suggestion(player, weapon, room);
		
		if (newSuggestion.equals(_game.getEnvelope())) {
			return true;
		}
		
		else {
			_inGame = false;
			return false;
		}
	}
}
