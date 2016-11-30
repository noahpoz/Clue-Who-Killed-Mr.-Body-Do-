package models;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * There are three types of cards. We can implement them using a field or creating subclasses.
 * @author Jared
 *
 */
public class Card {
	
	private String _name;
	
	public Card(String name) {
		_name = name;
	}
	
	public String getName() {
		return _name;
	}
	
	public static ArrayList<Card> generateDeck() {
		ArrayList<Card> deck = new ArrayList<Card>();
		try {
			BufferedReader bf = new BufferedReader(new FileReader("default_players_weapons_cards.txt"));
			
			while (bf.readLine() != null) {
				deck.add(new Card(bf.readLine()));
			}
			
		} catch (IOException e) {
			System.out.println("Check filepath");
		}
		
		return deck;
	}
}
