
package classes;


import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Image;
import java.awt.BorderLayout;
import java.awt.Graphics;


import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.Rectangle;

public class Sprite extends JPanel {
	protected Image img;
	protected int xPos, yPos;
	protected int width, height;
	/**********************************************************************************************
	* Sprite constructor.
	* Initializes the attributes and loads the image for this sprite.
	***********************************************************************************************/
	public Sprite(int xPos, int yPos){
		this.setOpaque(false);
		this.setSize(500,500);
		this.xPos = xPos;
		this.yPos = yPos;
		
	}
	
	protected void loadImage(String filename){
		try{
			img = Toolkit.getDefaultToolkit().getImage(filename);
			// System.out.println("FILE FOUND!");
		} catch(Exception e){
			// System.out.println("FILE NOT FOUND...");
		}	
	}

	public Image getImage(){
		return this.img;
	}

	public int getXPos(){
		return this.xPos;
	}

	public int getYPos(){
		return this.yPos;
	}
	
	////gagamitin ba ito?
	/*
	public Rectangle getBounds() {
		return new Rectangle(this.xPos, this.yPos, this.width, this.height);
	}
	*/

	/**********************************************************************************************
	* Overrides the paintComponent method of JPanel.
	* Draws the image on its x and y coordinates.
	***********************************************************************************************/

	@Override
	public void paintComponent(Graphics g){}
}