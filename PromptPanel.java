package classes;

import javax.swing.*;
import java.awt.*;

class PromptPanel extends JPanel{
    private Image img;

    public PromptPanel(String img) {
        this.loadImage(img);
    }
    void loadImage(String filename){
		try{
			img = Toolkit.getDefaultToolkit().getImage(filename);
		} catch(Exception e){
			
		}	
	}
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;

        g2d.drawImage(img, 0, 0, null);
    }

}