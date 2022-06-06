import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main extends JFrame{
	private static final int WIDTH =1200;
	private static final int HEIGHT=700;
	
	public Main () {
		super("Space Invader!");
		setSize(WIDTH, HEIGHT);
		Game play = new Game();
		((Component) play).setFocusable(true);
		setBackground(Color.BLACK);
		getContentPane().add(play);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
		setLocationRelativeTo(null);
	}
	

	public static void main(String[] args) {
		Main run = new Main();
	}
}
