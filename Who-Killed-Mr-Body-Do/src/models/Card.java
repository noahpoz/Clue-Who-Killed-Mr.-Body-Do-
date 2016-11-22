package models;

import java.util.ArrayList;

/**
 * There are three types of cards. We can implement them using a field or creating subclasses.
 * @author Jared
 *
 */
public class Card {
	
	String _name;
	//could add images if we wanted
	
	public Card(String name) {
		_name = name;
	}
	
	public static ArrayList<Card> generateDeck() {
		ArrayList<Card> deck = new ArrayList<Card>();
		
		return deck;
	}
}
