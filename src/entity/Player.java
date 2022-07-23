package entity;

//import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity{
	
	KeyHandler keyH;
	
	public Player(GamePanel gp, KeyHandler keyH) {
		super(gp);
		this.keyH = keyH;
		setDefaultValues();
		getPlayerImage();
	} // End of constructor
	
	public void getPlayerImage() {
		idol = setUp("/player/swing1.png", width, height);
		swing2 = setUp("/player/swing2.png", width, height);
		swing3 = setUp("/player/swing3.png", width, height);
	} //End of getPlayerImage
	
	public void setDefaultValues() {
		xMultiplier = 1;
		yMultiplier = 3;
		height = width = gp.tileSize * 2;
		x = xMultiplier * gp.tileSize;
		y = yMultiplier * gp.tileSize;
		
		// Collision detection
        solidArea = new Rectangle(); // x-10, y30, width, height
        solidArea.x = gp.tileSize/2 + 50;
        solidArea.y = (gp.tileSize / 3) + gp.tileSize / 2;
        solidArea.width = gp.tileSize + 20;
        solidArea.height = gp.tileSize/3 - 20;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
	} // End of setDefaultValues
	
	public void update() {
		if(attacking == true) {
			attacking();
		}
		// Player can move when not swinging bat
		else if(attacking == false) {
			if(keyH.leftPressed == true) {
				// lower bounds
				if(xMultiplier > 0) {
					xMultiplier -= 1;
				}
				keyH.leftPressed = false;
			}
			else if(keyH.rightPressed == true) {
				// upper bounds
				if(xMultiplier < 4) {	
					xMultiplier += 1;
				}
				keyH.rightPressed = false;
			}
			x = xMultiplier * gp.tileSize;
			y = yMultiplier * gp.tileSize;
			if(KeyHandler.enterPressed == true) {
				gp.playTime = 0;
				attacking = true;
			}
    	} // end of else if
		collisionOn = false;
		int objIndex = gp.cChecker.checkEntity(this, gp.obj);
		interactObj(objIndex);
		
	} // End of update
	
	public void interactObj(int index) {
		if(index != 999) {}
	} // End of interactObj
	
	public void attacking() {
		// swing animation set to gameplay clock;
    	if(gp.playTime <= 5) {
    		spriteNum = 1;
    	}
    	if(gp.playTime > 30) {
    		spriteNum = 2;
    	}
    	if(gp.playTime > 115) {
    		attacking = false;
    	}
    	int objIndex = gp.cChecker.checkEntity(this, gp.obj);
		hitObject(objIndex);
	} // End of Attacking

	public void hitObject(int index) {
    	if(index != 999) { 
    		if(gp.obj[index].hit == false) {
    			gp.hitCounter++;
    			gp.playSE(0);
    			gp.obj[index].hit = true;
    			gp.obj[index].objReaction();
    		} // End of If
		} // End of if
    } // End of hitObject
	
	public void draw(Graphics2D g2) {
		BufferedImage image = null;
		if(attacking == true) {
    		if(spriteNum == 1) {image = swing2;}
    		else if(spriteNum == 2) {image = swing3;}
    		else {image = swing3;}
    	}
		else {
			image = idol;
		}
		g2.drawImage(image, x, y, width, height, null);
		// Uncomment to view hitbox
		//g2.setColor(Color.RED);
		//g2.drawRect( x + solidArea.x, y + solidArea.y, solidArea.width, solidArea.height);	 
	} // End of draw
}
