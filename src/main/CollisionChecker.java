package main;

import entity.Entity;

public class CollisionChecker {
	
	GamePanel gp;
	
	public CollisionChecker(GamePanel gp) {
		this.gp = gp;
	}
	
	public int checkEntity(Entity entity, Entity[] target) {
		
		int index = 999;
		for(int i = 0; i < target.length; i++){
			 
			if(target[i] != null) {
				// Get entity's solid area position
				entity.solidArea.y = entity.y + entity.solidArea.y;
				entity.solidArea.x = entity.x + entity.solidArea.x;
				// Get the object's solid are position
				target[i].solidArea.y = target[i].y + target[i].solidArea.y;
				target[i].solidArea.x = target[i].x + target[i].solidArea.x;
				
				switch(entity.direction) {
				case "up": 
					entity.solidArea.y -= entity.speed;
					break;
				case "down":
					entity.solidArea.y += entity.speed;
					break;
				case "left":
					entity.solidArea.x -= entity.speed;
					break;
				case "right":
					entity.solidArea.x += entity.speed;
					break;
				}
				if(entity.solidArea.intersects(target[i].solidArea)) {
					if(target[i] != entity) {
						entity.collisionOn = true;
						index = i;
					}
				}
				entity.solidArea.x = entity.solidAreaDefaultX;
				entity.solidArea.y = entity.solidAreaDefaultY;
				target[i].solidArea.x = target[i].solidAreaDefaultX;
				target[i].solidArea.y = target[i].solidAreaDefaultY;
				
			}
		}
		return index;
	}
	
public void checkPlayer(Entity entity) {
		
		
		// Get entity's solid area position
		entity.solidArea.y = entity.y + entity.solidArea.y;
		entity.solidArea.x = entity.x + entity.solidArea.x;
		// Get the object's solid are position
		gp.player.solidArea.y = gp.player.y + gp.player.solidArea.y;
		gp.player.solidArea.x = gp.player.x + gp.player.solidArea.x;
		
		switch(entity.direction) {
		case "up":
			entity.solidArea.y -= entity.speed;
			break;
		case "down":
			entity.solidArea.y += entity.speed;
			break;
		case "left":
			entity.solidArea.x -= entity.speed;
			break;
		case "right":
			entity.solidArea.x += entity.speed;
			break;
		}
		if(entity.solidArea.intersects(gp.player.solidArea)) {
			entity.collisionOn = true;
			
		}
		entity.solidArea.x = entity.solidAreaDefaultX;
		entity.solidArea.y = entity.solidAreaDefaultY;
		gp.player.solidArea.x = gp.player.solidAreaDefaultX;
		gp.player.solidArea.y = gp.player.solidAreaDefaultY;
	
	}
	
	
	

}
