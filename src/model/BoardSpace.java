package model;

public interface BoardSpace extends UIReadable {
	
	public boolean isMovable();
	public boolean containsPlayer();
	public void setContainsPlayer(boolean b);
}
