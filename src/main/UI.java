package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
//import java.awt.RenderingHints;
import java.io.IOException;
import java.io.InputStream;



public class UI {
	
	GamePanel gp;
	Graphics2D g2;
	Font maruMonica;
	public boolean messageOn = false;
	public String message = "";
	public boolean gameFinished = false;
	public String currentDialogue;
	public int commandNum = 0;

	public UI(GamePanel gp) {
		this.gp = gp;

		try {
			InputStream is = getClass().getResourceAsStream("/fonts/x12y16pxMaruMonica.ttf");
			maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	
	}
	
	public void showMessage(String text) {
		message = text;
		messageOn = true;
	}
	
	public void draw(Graphics2D g2) {
		
		this.g2 = g2;
		
		g2.setFont(maruMonica);
		//g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2.setColor(Color.white);
		
		if(gp.gameState == gp.titleState) {
			drawTitleScreen();
		}
		else if(gp.gameState == gp.playState) {
			drawGameStats();
		}
		else if(gp.gameState == gp.gameOverState) {
			drawGameOver();
		}
		

	}
	
	public void drawGameStats(){
		g2.setColor(new Color(71, 183, 73));
		g2.fillRect(0,  0,  gp.screenWidth, gp.screenHeight);
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
		String text = "Hits:" + gp.hitCounter + " Misses:" + gp.missCounter;
		int x = getXforCenteredText(text);
		int y = gp.tileSize * 1;
		g2.setColor(Color.gray);
		g2.drawString(text, x - 5, y + 5);
		g2.setColor(Color.white);
		g2.drawString(text, x, y);
		
	}	
	
	
	public void drawTitleScreen() {
		
		g2.setColor(new Color(71, 183, 73));
		g2.fillRect(0,  0,  gp.screenWidth, gp.screenHeight);
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
		String text = "Pop Up Hitter";
		int x = getXforCenteredText(text);
		int y = gp.tileSize * 1;
		g2.setColor(Color.gray);
		g2.drawString(text, x - 5, y + 5);
		g2.setColor(Color.white);
		g2.drawString(text, x, y);
			
		// IMAGE
		x = gp.screenWidth/2 - (gp.tileSize*2)/2 + 48;
		y = gp.tileSize;
		g2.drawImage(gp.player.idol, x, y, gp.tileSize*2, gp.tileSize*2, null);
		
		// MENU
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
		
		text = "NEW GAME";
		x = getXforCenteredText(text);
		y += gp.tileSize* 2;
		g2.drawString(text, x, y);
		if(commandNum == 0) {
			g2.drawString(">", x - 25, y);
		}
		
		text = "QUIT";
		x = getXforCenteredText(text);
		y += gp.tileSize - 48;
		g2.drawString(text, x, y);
		if(commandNum == 1) {
			g2.drawString(">", x - 25, y);
		}
				
	}
	
	public void drawGameOver() {
		
		// WINDOW
		g2.setColor(new Color(71, 183, 73));
		g2.fillRect(0,  0,  gp.screenWidth, gp.screenHeight);
		int x = gp.tileSize;
		int y = gp.tileSize/2;
		int width = gp.screenWidth - (gp.tileSize * 2);
		int height = gp.tileSize*2+48;
		int lineHeight = 48;

		drawSubWindow(x, y, width, height);	
		
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 52F));
		String text = "GAME OVER";
		x = getXforCenteredText(text);
		y = gp.tileSize;
		g2.drawString(text, x, y);
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 32F));
		text = "Your Score: " + gp.hitCounter;
		y = y +  lineHeight;
		x = getXforCenteredText(text);
		g2.drawString(text, x, y);
		text = "High Score: " + gp.highscore;
		y = y +  lineHeight;
		x = getXforCenteredText(text);
		g2.drawString(text, x, y);
		text = "Press ENTER to Continue";
		y = y +  lineHeight * 2;
		x = getXforCenteredText(text);
		g2.drawString(text, x, y);
	
	}
	
	public void drawSubWindow(int x, int y, int width, int height) {
		
		Color windowColor = new Color(0, 0, 0, 220);
		Color borderColor = new Color(255, 255, 255);
		int arcW = 35;
		int arcH = 35;
		
		g2.setColor(windowColor);
		g2.fillRoundRect(x, y, width, height, arcW, arcH);
		
		g2.setColor(borderColor);
		g2.setStroke(new BasicStroke(5));
		g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, arcW - 10, arcH - 10);
		
	}
	
	public int getXforCenteredText(String text) {

		
		int textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = gp.screenWidth/2 - textLength/2;
		
		return x;
	}
	
	public int getXforAligntoRightText(String text, int tailX) {
		int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = tailX - length;
		return x;
	}
}
