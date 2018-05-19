
package classes;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;

class UnderWater extends Sprite implements Runnable{
//this contains the background pic
//which changes as the depth increases

	private static DepthTimer depth;

	private static int BLUE = 255;
	private static int TRANSPARENCY = 0;
    private static final int BLUE_FADE_CONST = 1;
    private static final int TRANSPARENCY_FADE_CONST = 1;
    private static final int MAX_BLUE = 0;
    private static final int MAX_TRANSPARENCY = 150;
	private static final int HEIGHT = 500, WIDTH = 400;

	private Color bgColor;

    public UnderWater(int x, int y, String filename, DepthTimer depth){
        super(x,y);
        this.loadImage(filename);

        this.depth = depth;

        bgColor = new Color(0, 0, UnderWater.BLUE, UnderWater.TRANSPARENCY);
    }

    // as the depth increases (game prolongs), the backgounnd fades into blue
    private void dive(){

    	if(UnderWater.depth.getDepth() < MAX_TRANSPARENCY){
    		if(UnderWater.TRANSPARENCY + TRANSPARENCY_FADE_CONST < MAX_TRANSPARENCY)
    			UnderWater.TRANSPARENCY += TRANSPARENCY_FADE_CONST;
    		else
    			UnderWater.TRANSPARENCY = MAX_TRANSPARENCY;
    	
    	// the deeper the depth, the darker blue the background becomes
    	}else{
			if(UnderWater.BLUE - BLUE_FADE_CONST > MAX_BLUE)
				UnderWater.BLUE -= BLUE_FADE_CONST;
			else
				UnderWater.BLUE = MAX_BLUE;
		}

		// resets the color of the rectangle
    	bgColor = new Color(0, 0, UnderWater.BLUE, UnderWater.TRANSPARENCY);
    }
    
    public void run(){
        while(true){
            try{
                Thread.sleep(1000);
           		this.repaint();
           		this.dive();

            }catch(Exception e){}

        }
    }

    public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;

		g2d.drawImage(this.getImage(),this.getXPos(),this.getYPos(),500,400,null); // yu may override this then change the size
		Toolkit.getDefaultToolkit().sync();

		// set the transparent rectangle's color (w/ size of the stage) as transparent blue
		g2d.setColor(bgColor);
		g2d.fillRect(0, 0, HEIGHT, WIDTH);
	}
}
