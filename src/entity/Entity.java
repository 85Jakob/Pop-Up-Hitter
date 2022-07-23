package entity;

//import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class Entity {
	
	GamePanel gp;
	
	public int x, y;
	public int xMultiplier;
	public int yMultiplier;
	public BufferedImage idol, swing2, swing3;
	public int width, height;
	public Rectangle solidArea = new Rectangle(0, 0, 64, 64);
	public boolean collisionOn = false;
    public int solidAreaDefaultX, solidAreaDefaultY;
	public boolean attacking = false;
	public boolean collision = false;
	public int spriteCounter = 0;
    public int spriteNum = 1;
    public String direction = "down";
    public float speed;
    public int actionLockCounter;
    public boolean hit;
    Random r = new Random();
    double randomfloat;
    
    public Entity(GamePanel gp) {
    	this.gp = gp;
    }
    public void update() {
    	
    	setAction();
    	collisionOn = false;
    	gp.cChecker.checkPlayer(this);
    	
        switch(direction) {
			case "up":
				if (gp.fallTime > randomfloat) {
					y -= speed;
					gp.fallTime = 0;
				}
		    break;
		    case "down":
		    	if (gp.fallTime > randomfloat) {
		    		y += speed;
		      		gp.fallTime = 0;
		      	}
		    	break;
	    }
        
    	
 
    }
	
	public BufferedImage setUp(String imagePath, int width, int height) {
	   	
	   	UtilityTool uTool = new UtilityTool();
	   	BufferedImage image = null;
	   	
	   	try {
	   		image = ImageIO.read(getClass().getResourceAsStream(imagePath));
	   		image = uTool.scaleImage(image, width, height);
	   		
	   	}catch(IOException e) {
	   		e.printStackTrace();
	   	}
	   	return image;
	   	
	}
	public void setAction() {}
	public void objReaction() {}
	
	
	public void draw(Graphics2D g2) {
		
		BufferedImage image = null;
		
		image = idol;
		
		g2.drawImage(image, x, y, width, height, null);
		//g2.setColor(Color.RED);
		//g2.drawRect( x + solidArea.x, y + solidArea.y, solidArea.width, solidArea.height);
		 
	}
	
}
