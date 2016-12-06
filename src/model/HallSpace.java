package model;

public class HallSpace implements BoardSpace {

	boolean _containsPlayer;
	
	public HallSpace() {
		_containsPlayer = false;
	}

	@Override
	public boolean isMovable() {
		return true;
	}

	@Override
	public boolean containsPlayer() {
		return _containsPlayer;
	}

	@Override
	public int getID() {
		return 0;
	}

	@Override
	public boolean setContainsPlayer(boolean b) {
		// TODO Auto-generated method stub
		return false;
	}

}
