package model;

public class BorderSpace implements BoardSpace {

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 2;
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
