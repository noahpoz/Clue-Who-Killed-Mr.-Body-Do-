package model;

public class WallSpace implements BoardSpace {

	@Override
	public boolean isMovable() {
		return false;
	}

	@Override
	public boolean containsPlayer() {
		return false;
	}

	@Override
	public int getID() {
		return 1;
	}
	
	@Override
	public String toString() {
		return "w";
	}

	@Override
	public void setContainsPlayer(boolean b) {
		// TODO Auto-generated method stub
	}
}
