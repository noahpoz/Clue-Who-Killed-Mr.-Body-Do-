package models;

public class HallSpace implements BoardSpace{

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

}
