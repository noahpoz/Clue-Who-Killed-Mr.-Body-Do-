package model;

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
	
	@Override
	public String toString() {
		return _name;
	}
}
