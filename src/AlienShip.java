import javax.swing.ImageIcon;

public class AlienShip extends Ship {
	public AlienShip() {
		super();  
	}
	
	public AlienShip(int x1, int y1, String pic) { 
		super(x1, y1, 50,40, 1, new ImageIcon(pic));
	}
	
}
