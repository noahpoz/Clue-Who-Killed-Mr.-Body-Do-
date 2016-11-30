package models;

public class Suggestion {
	
	private String _person;
	private String _weapon;
	private String _room;
	
	public Suggestion(String person, String weapon, String room) {
		_person = person;
		_weapon = weapon;
		_room = room;
	}
	
	public boolean equals(Suggestion s) {
		return (s.getPerson().equals(_person) 
				&& s.getWeapon().equals(_weapon)
				&& s.getRoom().equals(_room));
	}
	
	public String getPerson() { return _person;	}
	public String getWeapon() { return _weapon; }
	public String getRoom() { return _room; }
}
