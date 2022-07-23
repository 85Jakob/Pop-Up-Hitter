package entity;

import main.GamePanel;

public class OBJ_Shield_Wood extends Entity{

	public OBJ_Shield_Wood(GamePanel gp) {
		super(gp);
		idol = setUp("/player/ball.png", gp.tileSize, gp.tileSize);
		setDefaultValues();
	} // End of constructor
	
	public void setDefaultValues() {
		height = width = gp.tileSize/3;
		speed = 1;
		
		// Hit box of obj
		solidArea.x = gp.tileSize/4;
		solidArea.y = 20;
		solidArea.width = 10;
		solidArea.height = 10;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		
		//  Determines fall speed of obj
		randomfloat = .2 + r.nextDouble() * (.5 - .3);
		
		direction = "down";
		collision = true;
	} // End of setDefaultValues
	
	public void setAction(){}
	
	// Obj moves switched direction if hit.
	public void objReaction() {
		direction = "up";
	} // End of objReaction
} // End of class
