import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Game extends JPanel implements Runnable, KeyListener, MouseListener, MouseMotionListener {
	private BufferedImage back;
	private PlayerShip playerShip; 
	private ArrayList <AlienShip> aliensList;
	private ArrayList <PlayerMissile> pMissileList; 
	private ArrayList <AlienMissile> aMissileList;  
	private final int SCREEN_WIDTH = 1200, SCREEN_HEIGHT = 700; 
	private final int ALIEN_WIDTH = 60, ALIEN_HEIGHT = 50; 
	private final int PLAYER_WIDTH = 150, PLAYER_HEIGHT = 75; 
	private int key;
	private boolean isLeft, isRight, isShoot, isPressed, reset, bullet;
	private boolean collideWallSide, collideWallUp, swap; 
	private char screen = 'M';
	private ImageIcon startBackground, arrowBackground, heartLives, win, lose, about,aShip, pShip; 
	private int backgroundScreen = 1;
	private int counter = 0, score = 0; 
	private ArrayList <AlienShip> list1, list2, list3, list4;
	
	
	public Game() {
		back=null;
		key = -1;
		new Thread(this).start();
		this.addKeyListener(this);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		playerShip = new PlayerShip((SCREEN_WIDTH-150)/2);
		pMissileList = new ArrayList<>(); 
		aMissileList = new ArrayList<>();
		list1  = new ArrayList<>();
		list2  = new ArrayList<>();
		list3  = new ArrayList<>();
		list4  = new ArrayList<>();
		aliensList =  setAlien(); 
		isLeft = false; isRight = false; isShoot = false; 
		isPressed = true; 
		collideWallSide = false; collideWallUp = false;
		startBackground = new ImageIcon("start_background.gif");
		arrowBackground = new ImageIcon("arrow_background.gif");
		win = new ImageIcon("win.gif");
		lose = new ImageIcon("lose.gif");
		about = new ImageIcon("about.gif");
		heartLives = new ImageIcon("heart.png");
		aShip = new ImageIcon("alien1.png");
		pShip = new ImageIcon("playerShip.png");
		reset = false;
		swap = false; 
		bullet = false;  
		Player.playmusic("battlemusic.wav");
	}
	
	
	public void run() {
		try {
			while(true) {
				Thread.currentThread().sleep(15);
				repaint();
			}
		}
		catch(Exception e) {}
	}
	
	public void screen(Graphics g) {
		switch(screen){
		case 'M':
			g.drawImage(startBackground.getImage(), 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, this);
			drawStartScreen(g);
			
			break;
		case 'G': 
			
			reset();
			// for each loop - a replace aliens.get(i)
			for (AlienShip a: aliensList) {
				g.drawImage(a.getPic().getImage(), a.getX(), a.getY(), a.getWidth(), a.getHeight(), this); 
			}
			
			g.drawImage(playerShip.getPic().getImage(), playerShip.getX(), playerShip.getY(), playerShip.getWidth(), playerShip.getHeight(),this);
			playerShip();
			movePlayerMissile();
			deletePlayerMissile();
//			moveAlien();
			alienCollisionSide();
//			moveAlienDown();
			for (PlayerMissile p: pMissileList) 
				g.drawImage(p.getPic().getImage(), p.getX(), p.getY(), p.getWidth(), p.getHeight(), this); 
			counter++; 
			if(counter%75==0) {
				setAienMissile();
				Player.playmusic("stop");
				Player.playmusic("shoot.wav");
			}
			for (AlienMissile q: aMissileList) 
				g.drawImage(q.getPic().getImage(), q.getX(), q.getY(), q.getWidth(), q.getHeight(), this); 
			
			moveAlienMissile();
			deleteAlienMissle();
			System.out.println(list3.size());
			getHeart(g);
			scoreBoard(g);
			getWin();
			swap();
			break;
		case 'L': 
			g.drawImage(lose.getImage(),0,0,SCREEN_WIDTH,SCREEN_HEIGHT, this);
			break;
		case 'W': 
			g.drawImage(win.getImage(),0,0,SCREEN_WIDTH,SCREEN_HEIGHT, this);
			break;
		case 'A': 
			g.drawImage(about.getImage(),0,0,SCREEN_WIDTH,SCREEN_HEIGHT, this);
			break;
		}	
	}
	
	public void getWin() {
		if (aliensList.size() == 0 &&playerShip.getHealth() > 0) {
			screen = 'W';
			Player.playmusic("stop");
			Player.playmusic("whale.wav");
		}
	}
	public ArrayList<AlienShip> setAlien() {
		ArrayList <AlienShip> list = new ArrayList<>();
		for (int w = 0; w<(SCREEN_WIDTH/ALIEN_WIDTH)-3;w++) {
			list1.add(new AlienShip(50+ w*(ALIEN_WIDTH + 5), 50+0*ALIEN_HEIGHT*2, "alien1.png"));
			list2.add(new AlienShip(50+ w*(ALIEN_WIDTH + 5), 50+1*ALIEN_HEIGHT*2, "alien2.png"));
		}
		for (int j = 0; j<(SCREEN_WIDTH/ALIEN_WIDTH)-5;j++) {
			list3.add(new AlienShip(100+ j*(ALIEN_WIDTH + 5), 100+0*ALIEN_HEIGHT*2, "alien3.png"));
			list4.add(new AlienShip(100+ j*(ALIEN_WIDTH + 5), 100+1*ALIEN_HEIGHT*2, "alien4.png"));
		}
		/*for (int w = 0; w<(SCREEN_WIDTH/ALIEN_WIDTH)-3;w++) {
			for (int h = 0; h<(SCREEN_HEIGHT/5)/ALIEN_HEIGHT - 1; h++){
				list.add(new AlienShip(50+ w*(ALIEN_WIDTH + 5), 50+h*ALIEN_HEIGHT*2));
			}
		}
		for (int j = 0; j<(SCREEN_WIDTH/ALIEN_WIDTH)-5;j++) {
			for (int i = 0; i<(SCREEN_HEIGHT/5)/ALIEN_HEIGHT-1; i++){
				list.add(new AlienShip(100+ j*(ALIEN_WIDTH + 5), 100+i*ALIEN_HEIGHT*2));
			}
		}*/
		list.addAll(list1);
		list.addAll(list2);
		list.addAll(list3);
		list.addAll(list4);
		
		return list; 
	}	
	
	
	public void playerShip() {
		
		if(isLeft && playerShip.getX() > 10) 
			playerShip.setdx(-5);
		if(isRight &&  playerShip.getX() + playerShip.getWidth() + 20< SCREEN_WIDTH) 
			playerShip.setdx(5);
		if(isShoot && isPressed) {
		
			setPlayerMissile();
			isPressed=false;
		}
			
	}
	
	// starting screen 
	public void drawStartScreen(Graphics g) {
		g.setColor(Color.white);
		if(backgroundScreen ==1) {
			g.drawImage(arrowBackground.getImage(), 820, 235, 100, 50, this);
			g.drawLine(960, 280, 1130, 280);
		}
		else if (backgroundScreen == 2) {
			g.drawImage(arrowBackground.getImage(), 820, 290, 100, 50, this);
			g.drawLine(960, 335, 1130, 335);
		}
		else if (backgroundScreen == 3) {
			g.drawImage(arrowBackground.getImage(), 820, 350, 100, 50, this);
			g.drawLine(1005,390,1130, 390);
		}
	}
	
	public void paint (Graphics g){
		Graphics2D twoDgraph = (Graphics2D)g;
		if (back==null) 
			back =(BufferedImage) (createImage(getWidth(), getHeight()));
		Graphics g2d = back.createGraphics();
		g2d.clearRect(0, 0, getSize().width, getSize().height); 
		
		
		screen(g2d);
		
		twoDgraph.drawImage(back, 0, 0, null);
	}
	
	public void setPlayerMissile() {
		
		if(!bullet)
		pMissileList.add(new PlayerMissile(playerShip.getX() + playerShip.getWidth()/2 - 1, false));
		else {
			pMissileList.add(new PlayerMissile(playerShip.getX() + playerShip.getWidth()/2 - 1, false));
			pMissileList.add(new PlayerMissile(playerShip.getX() + playerShip.getWidth()/2 + 15, false));
			pMissileList.add(new PlayerMissile(playerShip.getX() + playerShip.getWidth()/2 - 17, false));
		}
	}
	
	public void scoreBoard(Graphics g) {
		getScore();
		g.setColor(Color.white);
		g.setFont(new Font ("Rockwell Extra Bold", Font.BOLD, 30));
		g.drawString("Score: " + score, 50,650);
	}
	
	public void getHeart(Graphics g) {
		switch(playerShip.getHealth()) {
		case 3: 
			g.drawImage(heartLives.getImage(), 1020, 630, 50, 25, this);
		case 2: 
			g.drawImage(heartLives.getImage(), 1070, 630, 50, 25, this);
		case 1: 
			g.drawImage(heartLives.getImage(), 1120, 630, 50, 25, this);
			break;
		default: 
			screen = 'L';
			Player.playmusic("stop");
			Player.playmusic("timmie.wav");
		}
	}
	
	public int getRandom() {
		int counter; 
		counter = (int)(Math.random()*((aliensList.size()-1)+ 1) + 0);
		return counter;
		
	}
	
	public void setAienMissile() {
			aMissileList.add(new AlienMissile(aliensList.get(getRandom()).getX() + aliensList.get(getRandom()).getWidth()/2 - 5, aliensList.get(getRandom()).getY() + aliensList.get(getRandom()).getHeight(), false));
		
	}
	
	public void deleteAlienMissle() {
		for (int i = 0; i < aMissileList.size(); i++) {
			if(aMissileList.get(i).getY() > SCREEN_HEIGHT) 
				aMissileList.remove(i);
			else if (aMissileList.get(i).getCollision()) 
				aMissileList.remove(i);
			
		}
	}
	
	public void movePlayerMissile() {
		for (PlayerMissile p: pMissileList) {
			p.setdy(-3);
			p.collideAlien(aliensList);
			p.collideAlien(list1);
			p.collideAlien(list2);
			p.collideAlien(list3);
			p.collideAlien(list4);
		}
	}
	
	public void moveAlienMissile() {
		for (AlienMissile a: aMissileList) {
			a.setdy(3);
			a.collidePlayer(playerShip);
			//a.collideAlien(aliensList);
		}
	}
	
	public void deletePlayerMissile() {
		for (int i = 0; i < pMissileList.size(); i++) {
			if(pMissileList.get(i).getY() + pMissileList.get(i).getHeight() < 0) 
				pMissileList.remove(i);
			else if (pMissileList.get(i).getCollision()) {
				pMissileList.remove(i);
				
			}
			
		}
	}

	public void getScore() {
			score  = ((17 - list1.size())*400)  + ((17 - list2.size())*200)  + ((15 - list3.size())*300) + ((15 - list4.size())*100);
		}
	
	public void moveAlienDown() {
		if(collideWallSide) {
			for (AlienShip a: aliensList) {
				a.setX(a.getX() + 10);
			}
		}
	}
	
	
	public void alienCollisionSide() {
		boolean collide = false; 
		boolean stop = false; 
		for (AlienShip a: aliensList) {
			if (a.getX()  < 5) {
				collideWallSide = !collideWallSide;
				collide = true;
				break;
			}
			else if(a.getX() + a.getWidth() + 20 > SCREEN_WIDTH) {
				collideWallSide = !collideWallSide;
				break;
			}
		}
		for (AlienShip a: aliensList) {
			if(collideWallSide && !stop) {
				a.setdx(1);
				collideWallUp = true;
			}
			if(!collideWallSide && !stop)
				a.setdx(-1);
				
		}
		if(collideWallUp && collide) {
			for (AlienShip a:aliensList) {
				stop = true;
			a.setY(a.getY() + 5);
			
			}
			collideWallUp= false;
			collide = false;
		}
		
	}
	public void swap() {
		if(swap) {
			for (AlienShip a:aliensList) 
				a.setShipPic(pShip);
			playerShip.setShipPic(aShip);
		}
		else {
			for(AlienShip l:list1)
				l.setShipPic(new ImageIcon("alien1.png"));
			for(AlienShip m:list2)
				m.setShipPic(new ImageIcon("alien2.png"));
			for(AlienShip n:list3)
				n.setShipPic(new ImageIcon("alien3.png"));
			for(AlienShip o:list4)
				o.setShipPic(new ImageIcon("alien4.png"));
			playerShip.setShipPic(pShip);
		}
	}
	public void reset() {
		if(reset) {
			bullet = false;
			aliensList.clear();
			pMissileList.clear();
			aMissileList.clear();
			list1.clear(); list2.clear(); list3.clear(); list4.clear();
			aliensList = setAlien();
			playerShip.setHealth(3);
			score = 0;
			counter = 0; 
			playerShip.setX((SCREEN_WIDTH-150)/2);
			swap = false; 
			reset = false;
		}
	}
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		key= arg0.getKeyCode();
	switch(screen) {
	case 'M':
		switch(key) {
		case KeyEvent.VK_UP: 
			if(backgroundScreen == 1) backgroundScreen = 3;
				else backgroundScreen--;
			break;
		case KeyEvent.VK_DOWN: 
			if(backgroundScreen == 3) backgroundScreen = 1;
				else backgroundScreen++;
			break;
		case KeyEvent.VK_SPACE: 
			if(backgroundScreen == 1) {
				screen = 'G';
				Player.playmusic("stop");
			}
			else if (backgroundScreen == 2)
				screen = 'A';
			else if (backgroundScreen == 3)
				System.exit(0);
			break;
		}    
		break;
		
	case 'G': 
		switch (key) {
		case KeyEvent.VK_LEFT: isLeft = true;break;
		case KeyEvent.VK_RIGHT: isRight = true; break;
		case KeyEvent.VK_SPACE: 
			isShoot = true;
			break;
		
		case KeyEvent.VK_A: swap = !swap; break;
		case KeyEvent.VK_S: bullet = !bullet; break;
		case KeyEvent.VK_D: playerShip.setHealth(3); break;
		case KeyEvent.VK_W: 
			if(list4.size()>0) {
				aliensList.removeAll(list4);
				list4.clear();
			}
			else if(list2.size()>0) {
				aliensList.removeAll(list2);
				list2.clear();
			}
			else if(list3.size()>0) {
				aliensList.removeAll(list3);
				list3.clear();
			}
			else if(list1.size()>0) {
				aliensList.removeAll(list1);
				list1.clear();
				
			}
		}
	//	playerShip.setX(playerShip.getX());
		break;
	case 'A':
		if(key == KeyEvent.VK_BACK_SPACE)
			screen = 'M';
		break;
	case 'W': 
		
		if(key == KeyEvent.VK_M) {
			screen = 'M';
			reset = true;
			Player.playmusic("stop");
			Player.playmusic("battlemusic.wav");
		}	
		break; 
	case 'L': 
		
		if(key == KeyEvent.VK_M) {
			screen = 'M';
			reset = true;  
			Player.playmusic("stop");
			Player.playmusic("battlemusic.wav"); 
		}
		break;
		}
	}
	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getKeyCode()) { 
			case KeyEvent.VK_LEFT: isLeft = false; break;
			case KeyEvent.VK_RIGHT: isRight = false; break;
			case KeyEvent.VK_SPACE: 
				isShoot = false; isPressed=true; 
				break;
		}	
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
//		getPlayerX = arg0.getX()- PLAYER_WIDTH/2;
//		if (arg0.getX() > 50 && arg0.getX() < SCREEN_WIDTH-PLAYER_WIDTH/2) {
//			playerShip.setX(getPlayerX);
//		}
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}

