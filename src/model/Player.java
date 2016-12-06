package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;

public class Player {
	
	private String _name;
	private ArrayList<Card> _cards;
	private int x;
	private int y;
	private HashMap<String, Boolean> _checklist;
	private Room _residence;
	
	public Player(String name) {
		_cards = new ArrayList<Card>();
		_name = name;
		_residence = null;
		_checklist = new HashMap<String, Boolean>();
	}
	
	public void addCardToHand(Card card) {
		_cards.add(card);
	}
	
	public String getName() { return _name; }
	
	/**
	 * @return An ArrayList<Card> of the cards in the player's hand.
	 */
	public ArrayList<Card> getCards() { return _cards; }
	
	public HashMap<String, Boolean> getChecklist() { return _checklist; }
	
	public ArrayList<Point> getAllMoves() {
		ArrayList<Point> moves = new ArrayList<Point>();
		return moves;
	}
	
	public ArrayList<Point> getAllMoves(int x, int y) {
		return null;
	}
	
	public void setLocation(Point p) {
		x = p.x;
		y = p.y;
	}
	
	public void setResidence(Room r) {
		_residence = r;
	}
	
	public Room getResidence() {
		return _residence;
	}
	
	public Point getLocation() {
		return new Point(x, y);
	}
	
	@Override
	public String toString() {
		return _name; 
	}
}
