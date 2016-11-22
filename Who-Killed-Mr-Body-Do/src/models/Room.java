package models;

import java.awt.Point;
import java.util.ArrayList;

public class Room {
	
	private ArrayList<Player> _playersInRoom;
	private String _name;
	private Room _secretPassage;
	private ArrayList<Point> _exits;
	
	public Room(String name) {
		_name = name;
		_secretPassage = null;
	}

	public String getName() {
		return _name;
	}
	
	public void setSecretPassage(Room room) {
		_secretPassage = room;
	}
}
