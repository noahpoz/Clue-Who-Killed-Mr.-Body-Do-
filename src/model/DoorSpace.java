package model;

public class DoorSpace implements BoardSpace {
	public Room room;
	
	public DoorSpace() {
		super();
		
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 4;
	}

	@Override
	public boolean isMovable() {
		// TODO Auto-generated method stub
		return true;
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
