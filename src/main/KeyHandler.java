package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{
	
	public boolean upPressed, downPressed, leftPressed, rightPressed;
	public static boolean enterPressed = false;
	/** Maximum elapsed time to consider key held down. */
	private static final long THRESHOLD = 90L;
	
	/** Timestamp of last invocation of method 'keyReleased'. */
	private long lastWhen;
	GamePanel gp;
	
	public KeyHandler(GamePanel gp) {
		this.gp = gp;
	}
		
	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		// TITLESTATE
		if(gp.gameState == gp.titleState) {
			titleState(code);
		}
		// PLAYSTATE
		else if(gp.gameState == gp.playState) {
			long when = e.getWhen();
			playState(code, when);
		}
		else if (gp.gameState == gp.gameOverState) {
			gameOverState(code);
		}		
	}
	
	private void playState(int code, long when) {
		
		long diff = when - lastWhen;
		if(code == KeyEvent.VK_ENTER) {
			if(diff > THRESHOLD) {
				enterPressed = true;
			}
			else {
				enterPressed = false;
			}
		}
		else if((code == KeyEvent.VK_W || code == KeyEvent.VK_UP)){
			upPressed = true;
			enterPressed = false;
		}
		else if(code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT){
			leftPressed = true;
			enterPressed = false;
		}
		else if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN){
			downPressed = true;
			enterPressed = false;
		}
		else if(code == KeyEvent.VK_D|| code == KeyEvent.VK_RIGHT){
			rightPressed = true;
			enterPressed = false;
		}
		else if(code == KeyEvent.VK_ESCAPE){
			gp.running = false;
		}
		lastWhen = when;
	} // End of playstate
	
	private void titleState(int code) {
		if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP){
			gp.ui.commandNum--;
			gp.playSE(2);
			if(gp.ui.commandNum < 0) {
				gp.ui.commandNum = 1;
			}
		}
		else if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN){
			gp.ui.commandNum++;
			gp.playSE(2);
			if(gp.ui.commandNum > 1) {
				gp.ui.commandNum = 0;
			}
		}
		else if(code == KeyEvent.VK_ENTER || code == KeyEvent.VK_X){
			if(gp.ui.commandNum == 0) {
				gp.missCounter = 0;
				gp.hitCounter = 0;
				gp.gameState = gp.playState;
				gp.setupGame(1);
				gp.player.setDefaultValues();
			}
			if(gp.ui.commandNum == 1) {
				gp.running = false;
			}
		}
		else if(code == KeyEvent.VK_ESCAPE){
			gp.running = false;
		}
	} // end of titlestate
	
	private void gameOverState(int code) {
		if(code == KeyEvent.VK_ENTER) {
			gp.gameState = gp.titleState;
		}
		else if(code == KeyEvent.VK_ESCAPE){
			gp.running = false;
		}
	} // end of gameOverState

	@Override
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		if(code == KeyEvent.VK_ENTER || code == KeyEvent.VK_X ) {
			enterPressed  = false;
		}
		if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP){
			upPressed = false;
		}
		if(code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT){
			leftPressed = false;
		}
		if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN){
			downPressed = false;
		}
		if(code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT){
			rightPressed = false;
		}
	}
} // end of class
