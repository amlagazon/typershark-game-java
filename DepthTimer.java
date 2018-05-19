
package classes;
import javax.swing.*;
import java.awt.*;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;

public class DepthTimer extends JPanel implements Runnable {
	private int depth;
	private Player player;
	private Stage stage;

	private static final int DEPTH_WIDTH = 400;
	private static final int DEPTH_CONSTANT = 1;


	public DepthTimer(Player player, Stage stage){
		this.player = player;
		this.stage = stage;
		this.depth = player.getDepth();

		// makes the JPanel transparent to allow underlying objects to be displayed 
		this.setOpaque(false);
		this.setBounds(0,0,500,500);
	}

	public int getDepth(){
		return this.depth;
	}

	// increases the depth of both the timer and the player by 5
	public void dive(){
		this.depth += DEPTH_CONSTANT;
		player.dive();
		
	}
	
	public void run(){
		while(true){
			try{
				Thread.sleep(1000);
			} catch(Exception e){}
			this.dive();
			this.repaint();
			stage.setDepthLabel();
		}
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;

		// the depth bar on the left changes color as the player goes deep
		if(this.getDepth()<=150) g2d.setColor(Color.CYAN);
		else if(this.getDepth()<=500) g2d.setColor(Color.BLUE);
		else if(this.getDepth()<=1500) g2d.setColor(Color.BLACK);
		else if(this.getDepth()<=3000) g2d.setColor(Color.RED);

		g2d.fillRect(0, 0, DepthTimer.DEPTH_WIDTH, this.getDepth());
	}

}