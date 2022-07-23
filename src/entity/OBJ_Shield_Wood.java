package entity;



import main.GamePanel;

public class OBJ_Shield_Wood extends Entity{

	public OBJ_Shield_Wood(GamePanel gp) {
		
		super(gp);
		idol = setUp("/player/ball.png", gp.tileSize, gp.tileSize);
		setDefaultValues();
	}
	
	public void setDefaultValues() {
		height = width = gp.tileSize/3;
		speed = 1;
		solidArea.x = gp.tileSize/4;
		solidArea.y = 20;
		solidArea.width = 10;
		solidArea.height = 10;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		randomfloat = .2 + r.nextDouble() * (.5 - .3);
		direction = "down";

		collision = true;
	
	}
	
	public void setAction(){
	
		
	}
	
	public void objReaction() {
		
		direction = "up";

	}
	

}
