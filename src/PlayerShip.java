import javax.swing.ImageIcon;

public class PlayerShip extends Ship {
	public PlayerShip() {
		super();  
	}
	
	public PlayerShip(int x1) { 
		super(x1, 530, 85,95 , 3, new ImageIcon("playerShip.png"));
	}	
	
	
}
