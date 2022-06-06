import javax.swing.ImageIcon;

public class Missile {
	private int x, y;
	private int width, height;
	private int dx, dy;
	private ImageIcon missilePic; 
	private boolean collision;
	
	public Missile() {
		x = 0;
		y = 0; 
		width = 0;
		height = 0;
		dx = 0;
		dy = 0;
		missilePic = new ImageIcon();
	}
	
	public Missile(int x, int y, int w, int h, ImageIcon p, boolean c) {
		this.x = x;
		this.y = y; 
		this.width = w;
		this.height = h;
		this.dx = 0;
		this.dy = 0;
		this.missilePic = p;
		this.collision = c; 
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
	
	public ImageIcon getPic() {
		return missilePic;
	}
	
	public void setdx(int dx1) {
		x+=dx1;
	}
	
	public void setdy(int dy1) {
		y+=dy1;
	}
}
