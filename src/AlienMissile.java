import java.util.ArrayList;
import javax.swing.ImageIcon;

public class AlienMissile extends Missile {
	private boolean collision = false; 
	public AlienMissile() {
		super();  
	}
	
	public AlienMissile(int x1, int y1, boolean c) { 
		super(x1, y1, 3, 17, new ImageIcon("alienMissile.png"), c);
	}
	
	public void collidePlayer(PlayerShip p) {
		if(getY() + getHeight() >= p.getY() && getY() < p.getY() + p.getHeight()) {
			if(getX() + getWidth() >= p.getX() && getX() <= p.getX() + p.getWidth() ) {
				setCollision(true);
				p.setHealth(p.getHealth() - 1);  
			}
		}	
		
	}
	public boolean getCollision() {
		return collision; 
	}
	
	public void setCollision(boolean b) {
		collision = b; 
	}
}
