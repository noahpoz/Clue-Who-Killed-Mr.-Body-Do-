package models;

public class WallSpace implements BoardSpace {

	@Override
	public boolean isMovable() {
		return false;
	}

	@Override
	public boolean containsPlayer() {
		return false;
	}
}
