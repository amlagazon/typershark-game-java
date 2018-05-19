
package classes;

import javax.swing.*;
import java.awt.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import java.awt.event.*;

import java.util.ArrayList;

public class MyFrame extends JFrame{

	private final Container container = this.getContentPane();

	private final JPanel splash = new JPanel();
	private final ImageIcon image = new ImageIcon("img/main2.png");
	private final JLabel label = new JLabel("", image, JLabel.CENTER);

	private final JPanel menuOps = new JPanel();
	// final ImageIcon bubble = new ImageIcon("img/newgame.png");

	private final JButton start = new JButton();
	private final JButton score = new JButton();
	private final JButton exit = new JButton();

	private final MyFrame Frame = this;
	static Typer typer;
	public MyFrame(){

		this.setPreferredSize(new Dimension(500,400));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);

		splash.setPreferredSize(new Dimension(500,400));
		menuOps.setLayout(new FlowLayout());

		this.mainMenu();
		this.menuFocus(true);
		this.options();

		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		  
	}

	void mainMenu(){
		
		start.setIcon(new ImageIcon("img/newgame.png"));
		start.setBorderPainted(false);
		start.setFocusPainted(false);
		start.setContentAreaFilled(false);
		// start.setLocation(250, 250);

		score.setIcon(new ImageIcon("img/highscores.png"));
		score.setBorderPainted(false);
		score.setFocusPainted(false);
		score.setContentAreaFilled(false);
		// start.setLocation(250, 250);

		exit.setIcon(new ImageIcon("img/exit.png"));
		exit.setBorderPainted(false);
		exit.setFocusPainted(false);
		exit.setContentAreaFilled(false);
		// start.setLocation(250, 250);

		container.add(label);
		
		menuOps.add(start);
		menuOps.add(score);
		menuOps.add(exit);
		menuOps.setBackground(new Color(17,199,239));


		
		// container.add(splash, BorderLayout.CENTER);
		container.add(menuOps, BorderLayout.SOUTH);


		//hover
		start.addMouseListener(new MouseListener()
		{
			public void mousePressed(MouseEvent e) {}

			public void mouseReleased(MouseEvent e) {}

			public void mouseEntered(MouseEvent e) {
				start.setIcon(new ImageIcon("img/newgame_hover.png"));
				start.setBorderPainted(false);
				start.setFocusPainted(false);
				start.setContentAreaFilled(false);
			}

			public void mouseExited(MouseEvent e) {
				start.setIcon(new ImageIcon("img/newgame.png"));
				start.setBorderPainted(false);
				start.setFocusPainted(false);
				start.setContentAreaFilled(false);
			}

			public void mouseClicked(MouseEvent e) {}	
		
		
		}
		);		

		//hover
		score.addMouseListener(new MouseListener()
		{
			public void mousePressed(MouseEvent e) {}

			public void mouseReleased(MouseEvent e) {}

			public void mouseEntered(MouseEvent e) {
				score.setIcon(new ImageIcon("img/highscores_hover.png"));
				score.setBorderPainted(false);
				score.setFocusPainted(false);
				score.setContentAreaFilled(false);
			}

			public void mouseExited(MouseEvent e) {
				score.setIcon(new ImageIcon("img/highscores.png"));
				score.setBorderPainted(false);
				score.setFocusPainted(false);
				score.setContentAreaFilled(false);
			}

			public void mouseClicked(MouseEvent e) {}	
		
		
		}
		);		


		//hover
		exit.addMouseListener(new MouseListener()
		{
			public void mousePressed(MouseEvent e) {}

			public void mouseReleased(MouseEvent e) {}

			public void mouseEntered(MouseEvent e) {
				exit.setIcon(new ImageIcon("img/exit_hover.png"));
				exit.setBorderPainted(false);
				exit.setFocusPainted(false);
				exit.setContentAreaFilled(false);
			}

			public void mouseExited(MouseEvent e) {
				exit.setIcon(new ImageIcon("img/exit.png"));
				exit.setBorderPainted(false);
				exit.setFocusPainted(false);
				exit.setContentAreaFilled(false);
			}

			public void mouseClicked(MouseEvent e) {}	
		
		
		}
		);		

		

	}

	private void highScoreDisplay(boolean isMenu){

		JPanel highscorePanel = new JPanel();
		
		highscorePanel.setLayout(new BoxLayout(highscorePanel, BoxLayout.Y_AXIS));
		highscorePanel.setBackground(new Color(17,199,239));
		JLabel caption = new JLabel("HIGH SCORES:");
		caption.setForeground(Color.WHITE);
		caption.setFont(new Font("Serif", Font.PLAIN, 30));
		highscorePanel.add(caption);

		String highscoreString = "";
		HighScore hs = new HighScore();	
		ArrayList<Score> score = hs.getScores();

		int TOP_5 = 5;
		int max_scores = score.size();

		if(max_scores > TOP_5)
			max_scores = TOP_5;
		
		for(int i = 0; i < max_scores; i++){
			String scorer = (i + 1) + ".\t" + score.get(i).getName() + "\t\t" + score.get(i).getScore() + "\n";
			scorer = scorer.replaceAll("\t","    ");
			JLabel scorerLabel = new JLabel(scorer);
			scorerLabel.setForeground(Color.WHITE);
			scorerLabel.setFont(new Font("Serif", Font.PLAIN, 20));
			highscorePanel.add(scorerLabel);
		}

		System.out.println(hs.getHighscoreString());

		JPanel west = new JPanel();
		west.add(highscorePanel);
		west.setBackground(new Color(17,199,239));

		container.add(west, BorderLayout.WEST);

		ImageIcon displayImage = new ImageIcon("img/highscore_view.png");
		JLabel label = new JLabel("", displayImage, JLabel.CENTER);
		container.add(label, BorderLayout.CENTER);
			
		if(isMenu){

			JButton exitHS = new JButton();

			exitHS.setIcon(new ImageIcon("img/back_button.png"));
			exitHS.setBorderPainted(false);
			exitHS.setFocusPainted(false);
			exitHS.setContentAreaFilled(false);

			exitHS.addMouseListener(new MouseListener()
			{
			public void mousePressed(MouseEvent e) {}

			public void mouseReleased(MouseEvent e) {}

			public void mouseEntered(MouseEvent e) {
				exitHS.setIcon(new ImageIcon("img/back_button_hover.png"));
				exitHS.setBorderPainted(false);
				exitHS.setFocusPainted(false);
				exitHS.setContentAreaFilled(false);
			}

			public void mouseExited(MouseEvent e) {
				exitHS.setIcon(new ImageIcon("img/back_button.png"));
				exitHS.setBorderPainted(false);
				exitHS.setFocusPainted(false);
				exitHS.setContentAreaFilled(false);
			}

			public void mouseClicked(MouseEvent e) {}	
		
		
			}
			);		

			JPanel exitPanel = new JPanel();
			exitPanel.setBackground(new Color(17,199,239));
			exitPanel.add(exitHS);
			container.add(exitPanel, BorderLayout.SOUTH);

			exitHS.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent e){
						Frame.resetFrame();
						Frame.mainMenu();
						Frame.options();
						Frame.menuFocus(true);
						Frame.refreshFrame();
					}
				}
			);
		}
	}

	private void options(){

		start.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					Frame.menuFocus(false);

					Frame.resetFrame();

					//instantiate player
					final Player player = new Player(0,170,"img/scuba.gif");
					final Stage stage = new Stage(Frame, container, player);
					Player.stage = stage;
					container.add(stage, BorderLayout.CENTER);
					typer.resetLabel();
					// focuses on the JPanel, which is the stage
					stage.setFocusable(true);
					stage.requestFocus();

					Frame.refreshFrame();	
				}
			}
		);

		// para sa highscore :3
		score.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){

					splash.setFocusable(false);
					menuOps.setFocusable(false);

					Frame.resetFrame();

					Frame.highScoreDisplay(true);

					Frame.refreshFrame();
				}
			}
		);

		exit.addActionListener(

			new ActionListener(){
				public void actionPerformed(ActionEvent e){
					System.exit(0);
				}

			}
		);
		
	}

	private void menuFocus(boolean state){
		splash.setFocusable(state);
		menuOps.setFocusable(state);

		if(state){
			splash.requestFocus();
			menuOps.requestFocus();
		}
	}

	private void refreshFrame(){	
		// updates the jframe
		container.revalidate();
		container.repaint();	
	}

	private void resetFrame(){
		// removes all of the components inside the container
		container.removeAll();
	}

	public MyFrame getFrameOps(){
		return Frame;
	}

	public void getRefreshFrame(){
		this.refreshFrame();
	}

	public void getResetFrame(){
		this.resetFrame();
	}

	public void getMenuFocus(boolean state){
		this.menuFocus(state);
	}

	public void getHighScoreDisplay(boolean isMenu){
		this.highScoreDisplay(isMenu);
	}

}
