import javax.swing.ImageIcon;

public class Ship {
	private int x, y;
	private int width, height;
	private int dx, dy, health;
	private ImageIcon shipPic; 
	
	public Ship() {
		x = 0;
		y = 0; 
		width = 0;
		height = 0;
		dx = 0;
		dy = 0;
		shipPic = new ImageIcon();
		health = 0;
	}
	
	public Ship(int x, int y, int w, int h, int health, ImageIcon p) {
		this.x = x;
		this.y = y; 
		this.width = w;
		this.height = h;
		this.dx = 0;
		this.dy = 0;
		this.health = health;
		this.shipPic = p;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getHealth() {
		return health;
	}
	
	public ImageIcon getPic() {
		return shipPic;
	}
	
	public void setdx(double dx1) {
		x+=dx1;
	}
	
	public void setdy(double dy1) {
		y+=dy1;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public void setHealth(int i) {
		this.health = i;
	}
	
	public void setShipPic(ImageIcon s) {
		this.shipPic = s;
	}
	
}
