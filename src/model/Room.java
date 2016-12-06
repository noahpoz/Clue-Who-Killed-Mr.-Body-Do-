package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;

public class Room implements BoardSpace {
	
	private ArrayList<Player> _playersInRoom = new ArrayList<Player>();
	private String _name;
	private Room _secretPassage;
	private ArrayList<Point> _exits;
	
	// static so that roomNames is initialized only once, not once for every BoardSpace
	private static ArrayList<String> roomNames = 
			new ArrayList<String>(Arrays.asList("Study", "Hall", "Lounge", "Dining Room", "Kitchen", "Ballroom", "Conservatory", "Billiard Room", "Library"));
	
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
	
	public void addPlayerToRoom(Player p) {
		_playersInRoom.add(p);
	}

	@Override
	public int getID() {
		
		// offset by five for WallSpace, HallSpace, BorderSpace, BlankSpace, and DoorSpace
		return roomNames.indexOf(_name) + 6;
	}
	
	@Override
	public String toString() {
		return _name;
	}

	@Override
	public boolean isMovable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsPlayer() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setContainsPlayer(boolean b) {
		// TODO Auto-generated method stub
	}
}