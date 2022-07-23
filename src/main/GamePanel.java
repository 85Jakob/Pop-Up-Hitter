package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

import javax.swing.JPanel;

import entity.Entity;
import entity.OBJ_Shield_Wood;
import entity.Player;


public class GamePanel extends JPanel implements Runnable{
	
	private static final long serialVersionUID = 1L;
	// SCREEN SETTINGS
	final int originalTileSize = 16;
	final int scale = 8;
	
	public final int tileSize = originalTileSize * scale; 
	
	final int maxScreenCol = 5;
	final int maxScreenRow = 5;
	final int screenWidth = (tileSize * maxScreenCol + 64);
	final int screenHeight = tileSize * maxScreenRow;
	
	// variables for off screen rending
	private Graphics2D g2;
	private Image dbImage = null;
	private static final int MAX_FRAME_SKIPS = 10;
	private static final int NO_DELAYS_PER_YIELD = 16;
	private static final int period = 33;
	public int hitCounter = 0;
	public int missCounter = 0;
	public int highscore = 0;

	// Running Program
	KeyHandler keyH = new KeyHandler(this);
	Thread animation;
	public volatile boolean running;
	public double playTime;
	public double fallTime;
	public Entity obj[] = new Entity[10];
	Sound soundEffects = new Sound();
	ArrayList<Entity> entityList = new ArrayList<>();
	public UI ui = new UI(this);
	
	// Player Default Variables
	Player player = new Player(this, keyH);
	public CollisionChecker cChecker = new CollisionChecker(this);
	
	// GAME STATES
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int gameOverState = 2;
	
	public GamePanel() {
		
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.addKeyListener(keyH);
		this.setFocusable(true);
		
	}
	public void startThread() {
		if( animation == null) {
    		animation = new Thread(this);
            animation.start();
    	}
	}

	@Override
	public void run() {
		
		long beforeTime, afterTime, timeDiff, sleepTime;
		long overSleepTime = 0L;
		int noDelays = 0;
		long excess = 0L;
		
		beforeTime = System.nanoTime();
		
		running = true;
		while(animation != null && running == true) {
			update();
			gameRender();
			paintScreen();
			
			afterTime = System.nanoTime();
			timeDiff = afterTime - beforeTime;
			sleepTime = (period - timeDiff) - overSleepTime;
			
			if (sleepTime > 0) {   // some time left in this cycle
				try {
					Thread.sleep(sleepTime/1000000L);  // nano -> ms
				}
				catch(InterruptedException ex){}
				overSleepTime =
					(System.nanoTime() - afterTime) - sleepTime;
			}
			else {    // sleepTime <= 0; frame took longer than the period
				excess -= sleepTime;  // store excess time value
				overSleepTime = 0L;

				if (++noDelays >= NO_DELAYS_PER_YIELD) {
					Thread.yield( );   // give another thread a chance to run
					noDelays = 0;
				}
			}

			beforeTime = System.nanoTime();
			/* If frame animation is taking too long, update the game state
	           without rendering it, to get the updates/sec nearer to
	           the required FPS. */
			int skips = 0;
			while((excess > period) && (skips < MAX_FRAME_SKIPS)) {
				excess -= period;
				update( );      // update state but don't render
				skips++;
			}
		}

		System.exit(0);
		
	}
	public void update() {
		
		if(gameState == playState) {
			Random random = new Random();
			int ranNum = random.nextInt(4)+1; // picks a random number from 1 - 100
			player.update();
			
			// GameTime
			playTime += (double)1/60;
			if(playTime > 120) {
				playTime = 0;
			}
			fallTime += (double)1/60;
			if(fallTime > 120) {
				fallTime = 0;
			}
			
			for(int i = 0; i < obj.length; i++) {
				if(obj[i] != null) {
					obj[i].update();
					if(obj[i].y < 0 || obj[i].y > 5 * tileSize) {
						if(obj[i].y > tileSize * 4) {
							missCounter++;
							playSE(1);
							
						}
						obj[i] = null;
						if(missCounter >= 3) {
							gameState = gameOverState;
							if (hitCounter > highscore) {
								highscore = hitCounter;
							}
						}
						else {
							setupGame(ranNum);
						}
					}
				
				}
				
			}
		
		}
		
		
	}
	private void gameRender() {
		if(dbImage == null) {
			dbImage = createImage(screenWidth, screenHeight);
			if (dbImage == null) {
				System.out.println("dbImage is null");
				return;
			}
			else {
				g2 = (Graphics2D) dbImage.getGraphics();
			}
		}
		g2.setColor(Color.black);
		g2.fillRect(0,  0,  screenWidth, screenHeight);
		 
		// Draw Characters
		// TITLE SCREEN
		if(gameState == titleState) {
			ui.draw(g2);
		} 
		else {
			// UI and background
		    ui.draw(g2);
		    
			for(int i = 0; i < obj.length; i++) {
		        	if(obj[i] != null) {
		        		entityList.add(obj[i]);
		        	}
		     }
			 Collections.sort(entityList, new Comparator<Entity>(){
	
					@Override
					public int compare(Entity e1, Entity e2) {
						
						int results = Integer.compare(e1.y, e2.y);
						return results;
					}	
		        	
		     });
			// DRAW ENTITIES
		    for(int i = 0; i < entityList.size(); i++) {
		    	entityList.get(i).draw(g2);
		    }
		    player.draw(g2);
		    // EMPTY ENTITIES LIST
		    entityList.clear();
		    
		}
	}
	
	public void paintScreen() {
		Graphics2D g;
		try {
			g = (Graphics2D) this.getGraphics();
			if((g != null) && (dbImage != null)) {
				g.drawImage(dbImage, 0, 0, null);
			}
			Toolkit.getDefaultToolkit().sync();
			g.dispose();
		} catch (Exception e) {
			System.out.println("Graphics Context Error:" + e);
		}
	} // End of painScreen
	
	public void setupGame(int xMultiplier) {
		
		obj[0] = new OBJ_Shield_Wood(this);
		obj[0].x = xMultiplier * tileSize + (tileSize/2);
		obj[0].y = tileSize * 0;
		
	
	}
    public void playSE(int i){
    	try {
			soundEffects.setFile(i);
	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("ERROR HERE");
		}
    	soundEffects.play(false);
    }
    public void stopSE() {
    	soundEffects.stop("SE");
    	
    }
}
