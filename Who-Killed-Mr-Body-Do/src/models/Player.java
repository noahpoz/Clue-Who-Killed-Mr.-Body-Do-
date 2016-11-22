package models;

import java.util.ArrayList;

public class Player {
	
	private String _name;
	private ArrayList<Card> _cards;
	private int x;
	private int y;
	private boolean _inGame;
	
	public Player(String name, ArrayList<Card> cards) {
		_cards = cards;
		_name = name;
		_inGame = true;
	}
	
	public String getName() {
		return _name;
	}
	
	private ArrayList<Card> getCards() {
		return _cards;
	}
	
	public boolean isInGame() {
		return _inGame;
	}
}
