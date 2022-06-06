import java.util.ArrayList;

import javax.swing.ImageIcon;

public class PlayerMissile extends Missile {
	
	private ArrayList<AlienShip> toRemove = new ArrayList<>();
	private boolean collision; 
	
	public PlayerMissile() {
		super(); // look at default constructor (no parameters) 
		collision = false;
	}
	
	public PlayerMissile(int x1, boolean c) { // doesn't have to exactly match super
		super(x1, 530, 3, 17, new ImageIcon("playerMissile.png"), c);
	}
	
	/* 
	 * public void setdx() {
	 * 		super.setdx(-5); // run in Game, if it isn't in subclass it will look to superclass 
	 * }
	 */
	
	public void collideAlien(ArrayList<AlienShip> removeList) {
		for(AlienShip r :removeList) {
			if(getY() < r.getY() + r.getHeight() && getY() > r.getY()) {
				if(getX() + getWidth() >= r.getX() && getX() <= r.getX() + r.getWidth()) {
					toRemove.add(r);
					setCollision(true); 
				}
			}	
		} 
		removeList.removeAll(toRemove);
	}
	
	public boolean getCollision() {
		return collision; 
	}
	
	public void setCollision(boolean b) {
		collision = b; 
	}
	
}
