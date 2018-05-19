
package classes;

import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Scanner;

import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Stage extends JPanel{

	//put here everything, characters*

	/* NOTE: variable 'options' is a MyFrame variable and is used for Frame manipulation operations */
	private MyFrame options;

	// used for the seacreature's position to the stage
	private int yPosition;
	private int xPosition;
	public HighScore highscore = new HighScore();

	//will be compared in every shark's word
	private static String answer = "";

	private Random r = new Random();

	//so we'll be able to call these when we use fxns
	private static Player player;

	// total number of sharks
	private static int totalSharks;

	// total number of piranhas
	private static int totalPiranhas;

	private boolean sharkWave, piranhaWave,bossWave;

	// adds the 'timer' to the stage
	private DepthTimer depthBar;

	// adds the background to the stage
    private final UnderWater bg = new UnderWater(0,0,"img/sea.png", depthBar);

    private static final int INIT_XPOS = 500;
    private static final int INIT_YPOS = 30;

	//will contain details of the player
	private JPanel details = new JPanel();	

	//will contain what the user typed
	private JPanel typedPanel = new JPanel(); 

	private JLabel depthLabel;
	private JLabel levelLabel;
	private JLabel pointLabel;
	private JLabel lifeLabel;
	private JLabel typed = new JLabel(answer);
	static Typer typer;
	private Container container;

    // the playing field
	public Stage(MyFrame mf,Container container, Player player){
		
		this.options = mf;

		Stage.player = player;
		
		this.container = container;
		Typer keyTyper = new Typer(this);
		this.addKeyListener(keyTyper);


		this.setNumberCreatures();


		// initialize the JPanel which is the stage
		this.setBackground(new Color(51,171,249));
		this.setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(500,400));

		// determines the wave of SeaCreatures to appear
		this.sharkWave = false;
		this.piranhaWave = false;
		this.bossWave= false;

		details.setBackground(new Color(0,0,0,170));
		
		
		typedPanel.setBackground(new Color(0,0,0,75));
		
		depthBar = new DepthTimer(player, this);
		depthLabel = new JLabel("Depth: "+player.getDepth());

		levelLabel = new JLabel("Level: "+player.returnLevel());
		pointLabel = new JLabel("Points "+player.returnPoints());
		lifeLabel = new JLabel("Life: "+player.returnLife());

		//sets the text to white color
		depthLabel.setForeground(Color.WHITE);
		levelLabel.setForeground(Color.WHITE);
		pointLabel.setForeground(Color.WHITE);
		lifeLabel.setForeground(Color.WHITE);
		typed.setForeground(Color.WHITE);

		details.add(depthLabel);
		details.add(levelLabel);
		details.add(pointLabel);
		details.add(lifeLabel);
		typedPanel.add(typed);

		// add components to the stage
        this.add(details,BorderLayout.NORTH);
        this.add(typedPanel,BorderLayout.SOUTH);
		this.add(depthBar, BorderLayout.WEST);
        this.add(player);

        // creates the thread for the dynamic game components in the stage
        Thread threadPlayer = new Thread(player);
        Thread threadBackground = new Thread(bg);
        Thread threadDepthBar = new Thread(depthBar);
        threadPlayer.start();
        threadBackground.start();
        threadDepthBar.start();

        // ////SPAWN THE SEA CREATURES!!! :D/
		// this.spawnBoss("img/bossOctopus.gif"); //first boss
        // spawns the sharks
        this.createSharks();
		this.add(bg);

	} //end of constructor

	// spawns the sharks
	public void setNumberCreatures(){
		Stage.setTotalSharks(0);
		Shark.setNumberDead(0); //set number of creatures to zero, since user wants a new game
		Piranha.setNumberDead(0);
		Stage.setTotalPiranhas(0);


	}
	public void createSharks(){

		// indicates a shark level
		this.sharkWave = true;
		this.piranhaWave = false;
		this.bossWave= false;

		ArrayList<Integer> usedYPos = new ArrayList<Integer>();
		ArrayList<Thread> listShark = new ArrayList<Thread>();

		//number of sharks in a certain level
		int noOfSharks = player.returnLevel()+4;

		if(noOfSharks >= 10)
			noOfSharks = 9;

		for(int counter = 0; counter < noOfSharks; counter++){

			yPosition = r.nextInt(9)+1;

			//randomized x Posittion, para di sila in straight line
			xPosition = r.nextInt(300);

			// checks if the vertical position is already used
			// this prevents the clumping up of the SeaCreatures
			while(usedYPos.contains(yPosition))
				yPosition = r.nextInt(9)+1;

			// adds the psoition to the list so that position wont be taken
			usedYPos.add(yPosition);

			Shark shark = new Shark(INIT_XPOS + xPosition, INIT_YPOS * yPosition,"img/shark.gif",player,this);
			Thread threadShark = new Thread(shark);

			listShark.add(threadShark);
			threadShark.start();

			this.add(shark);
			this.totalSharks++;

			shark.setVisible(true);

		}
		this.addBackground();

		System.out.println("Total sharks: "+Stage.getTotalSharks());
	}

	public void spawnBoss(String image){
		this.sharkWave = false;
		this.piranhaWave = false;
		this.bossWave= true;

		Boss boss = new Boss(350,100,image,player,this);
		Thread thread = new Thread(boss);

		player.setBossSpawnState(true);

		this.add(boss);
		thread.start();
	}

	// spawns the piranhas
	public void createPiranhas(){

		// indicates a piranha level
		this.sharkWave = false;
		this.piranhaWave = true;
		this.bossWave= false;

		ArrayList<Integer> usedYPos = new ArrayList<Integer>();
		ArrayList<Thread> listPiranha = new ArrayList<Thread>();

		//number of piranhas in a certain level
		int noOfPiranhas = 8;

		// if(noOfPiranhas >= 10)
		// 	noOfPiranhas = 9;

		for(int counter = 0; counter < noOfPiranhas; counter++){

			yPosition = r.nextInt(9)+1;

			//randomized x Position, para di sila in straight line
			xPosition = r.nextInt(100);

			// checks if the vertical position is already used
			// this prevents the clumping up of the SeaCreatures
			while(usedYPos.contains(yPosition))
				yPosition = r.nextInt(9)+1;

			usedYPos.add(yPosition);

			Piranha piranha = new Piranha(INIT_XPOS + xPosition, INIT_YPOS * yPosition,"img/monster.gif",player,this);
			Thread threadPiranha = new Thread(piranha);

			listPiranha.add(threadPiranha);
			threadPiranha.start();

			this.add(piranha);
			this.totalPiranhas++;

			piranha.setVisible(true);

		}

		this.addBackground();

		System.out.println("Total piranhas: "+Stage.getTotalPiranhas());
	}

	public void congratulations(){
		/*
			NOTE:
				variable 'options' is a MyFrame variable and is used for Frame manipulation operations
		*/
		// container.remove(this);
		// container.revalidate();
		// container.repaint();
		options.getResetFrame();
		options.getRefreshFrame();

		JPanel overPanel = new JPanel();
		overPanel.setBackground(new Color(0,110,213));
		ImageIcon imge = new ImageIcon("img/congratulations_prompt.png");
		JLabel label = new JLabel("", imge, JLabel.CENTER);

	
		JLabel scoreDisplay = new JLabel("	Final Score: "+player.returnPoints());		

		JTextField getName = new JTextField("");
		getName.setPreferredSize( new Dimension( 100, 24 ) );
		scoreDisplay.setForeground(Color.WHITE);

		JPanel buttonPanel = new JPanel();
		JButton backGame = new JButton();
		JButton newGame = new JButton();
		JButton enterName = new JButton();

		backGame.setIcon(new ImageIcon("img/back_button.png"));
		backGame.setBorderPainted(false);
		backGame.setFocusPainted(false);
		backGame.setContentAreaFilled(false);
		
		newGame.setIcon(new ImageIcon("img/newgame.png"));
		newGame.setBorderPainted(false);
		newGame.setFocusPainted(false);
		newGame.setContentAreaFilled(false);
		
		enterName.setIcon(new ImageIcon("img/entername.png"));
		enterName.setBorderPainted(false);
		enterName.setFocusPainted(false);
		enterName.setContentAreaFilled(false);
		
		overPanel.add(scoreDisplay);

		buttonPanel.add(backGame);
		buttonPanel.add(getName);
		buttonPanel.add(enterName);
		buttonPanel.add(newGame);

		buttonPanel.setBackground(new Color(0,110,213));

		container.add(overPanel,BorderLayout.NORTH);
		container.add(label,BorderLayout.CENTER);
		container.add(buttonPanel,BorderLayout.SOUTH);

//LISTENERS
		backGame.addMouseListener(new MouseListener()
		{
			public void mousePressed(MouseEvent e) {}
			public void mouseReleased(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {
				backGame.setIcon(new ImageIcon("img/back_button_hover.png"));
				backGame.setBorderPainted(false);
				backGame.setFocusPainted(false);
				backGame.setContentAreaFilled(false);
			}
			public void mouseExited(MouseEvent e) {
				backGame.setIcon(new ImageIcon("img/back_button.png"));
				backGame.setBorderPainted(false);
				backGame.setFocusPainted(false);
				backGame.setContentAreaFilled(false);
			}
			public void mouseClicked(MouseEvent e) {}	
		}
		);		
		enterName.addMouseListener(new MouseListener()
		{
			public void mousePressed(MouseEvent e) {}
			public void mouseReleased(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {
				enterName.setIcon(new ImageIcon("img/entername_hover.png"));
				enterName.setBorderPainted(false);
				enterName.setFocusPainted(false);
				enterName.setContentAreaFilled(false);
			}
			public void mouseExited(MouseEvent e) {
				enterName.setIcon(new ImageIcon("img/entername.png"));
				enterName.setBorderPainted(false);
				enterName.setFocusPainted(false);
				enterName.setContentAreaFilled(false);
			}
			public void mouseClicked(MouseEvent e) {}	
		}
		);		
		newGame.addMouseListener(new MouseListener()
		{
			public void mousePressed(MouseEvent e) {}
			public void mouseReleased(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {
				newGame.setIcon(new ImageIcon("img/newgame_hover.png"));
				newGame.setBorderPainted(false);
				newGame.setFocusPainted(false);
				newGame.setContentAreaFilled(false);
			}
			public void mouseExited(MouseEvent e) {
				newGame.setIcon(new ImageIcon("img/newgame.png"));
				newGame.setBorderPainted(false);
				newGame.setFocusPainted(false);
				newGame.setContentAreaFilled(false);
			}
			public void mouseClicked(MouseEvent e) {}	
		}
		);		

		enterName.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent e){
						String name = getName.getText();
						// adds the score to the save highscore data
						highscore.addScore(name,player.returnPoints());
						//then back to menu

						options.getResetFrame();

						options.mainMenu();
						options.getMenuFocus(true);

						options.getRefreshFrame();
				}
			}
		);

		newGame.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent e){
					// container.remove(overPanel);
					// container.remove(newGame);
					// container.remove(scoreDisplay);
					options.getResetFrame();
					// container.revalidate();
					// container.repaint();
					options.getRefreshFrame();
					
					//instantiate player
					
					Stage.setTotalSharks(0);
					Shark.setNumberDead(0); //set number of creatures to zero, since user wants a new game
					Piranha.setNumberDead(0);
					Stage.setTotalPiranhas(0);
					
					final Player player = new Player(0,170,"img/scuba.gif");
					final Stage stage = new Stage(options, container, player);
					Player.stage = stage;
					container.add(stage, BorderLayout.CENTER);
					// focuses on the JPanel, which is the stage
					stage.setFocusable(true);
					stage.requestFocus();
					typer.resetLabel();
					// container.revalidate();
					// container.repaint();
					options.getRefreshFrame();
				}
			}
		);

		backGame.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent e){
					// container.remove(overPanel);
					// container.remove(newGame);
					// container.remove(scoreDisplay);
					options.getResetFrame();

					options.mainMenu();
					options.getMenuFocus(true);

					// container.revalidate();
					// container.repaint();
					options.getRefreshFrame();
				}
			}
		);
		
		options.getRefreshFrame();

	}

	//when the player dies, this will be displayed
	public void gameOver(){ 
		/*
			NOTE:
				variable 'options' is a MyFrame variable and is used for Frame manipulation operations
		*/
		// container.remove(this);
		// container.revalidate();
		// container.repaint();
		options.getResetFrame();
		options.getRefreshFrame();
		

		JPanel overPanel = new JPanel();
		overPanel.setBackground(new Color(255,138,0));
		ImageIcon imge = new ImageIcon("img/game_over_prompt.png");
		JLabel label = new JLabel("", imge, JLabel.CENTER);
		
		JTextField getName = new JTextField("");
		getName.setPreferredSize( new Dimension( 100, 24 ) );

		
		//displays the score 
		JLabel scoreDisplay = new JLabel("Final Score: "+player.returnPoints());		
		scoreDisplay.setForeground(Color.WHITE);


		JPanel buttonPanel = new JPanel();
		JButton backGame = new JButton();
		JButton newGame = new JButton();
		JButton enterName = new JButton();

		backGame.setIcon(new ImageIcon("img/back_button.png"));
		backGame.setBorderPainted(false);
		backGame.setFocusPainted(false);
		backGame.setContentAreaFilled(false);
		
		newGame.setIcon(new ImageIcon("img/newgame.png"));
		newGame.setBorderPainted(false);
		newGame.setFocusPainted(false);
		newGame.setContentAreaFilled(false);
		
		enterName.setIcon(new ImageIcon("img/entername.png"));
		enterName.setBorderPainted(false);
		enterName.setFocusPainted(false);
		enterName.setContentAreaFilled(false);
		
		
		
		overPanel.add(scoreDisplay);
		

		buttonPanel.add(backGame);
		buttonPanel.add(getName);
		buttonPanel.add(enterName);
		buttonPanel.add(newGame);

		buttonPanel.setBackground(new Color(255,138,0));

		container.add(overPanel,BorderLayout.NORTH);
		container.add(label,BorderLayout.CENTER);
		container.add(buttonPanel,BorderLayout.SOUTH);

		//LISTENERS
		backGame.addMouseListener(new MouseListener()
		{
			public void mousePressed(MouseEvent e) {}
			public void mouseReleased(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {
				backGame.setIcon(new ImageIcon("img/back_button_hover.png"));
				backGame.setBorderPainted(false);
				backGame.setFocusPainted(false);
				backGame.setContentAreaFilled(false);
			}
			public void mouseExited(MouseEvent e) {
				backGame.setIcon(new ImageIcon("img/back_button.png"));
				backGame.setBorderPainted(false);
				backGame.setFocusPainted(false);
				backGame.setContentAreaFilled(false);
			}
			public void mouseClicked(MouseEvent e) {}	
		}
		);		
		enterName.addMouseListener(new MouseListener()
		{
			public void mousePressed(MouseEvent e) {}
			public void mouseReleased(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {
				enterName.setIcon(new ImageIcon("img/entername_hover.png"));
				enterName.setBorderPainted(false);
				enterName.setFocusPainted(false);
				enterName.setContentAreaFilled(false);
			}
			public void mouseExited(MouseEvent e) {
				enterName.setIcon(new ImageIcon("img/entername.png"));
				enterName.setBorderPainted(false);
				enterName.setFocusPainted(false);
				enterName.setContentAreaFilled(false);
			}
			public void mouseClicked(MouseEvent e) {}	
		}
		);		
		newGame.addMouseListener(new MouseListener()
		{
			public void mousePressed(MouseEvent e) {}
			public void mouseReleased(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {
				newGame.setIcon(new ImageIcon("img/newgame_hover.png"));
				newGame.setBorderPainted(false);
				newGame.setFocusPainted(false);
				newGame.setContentAreaFilled(false);
			}
			public void mouseExited(MouseEvent e) {
				newGame.setIcon(new ImageIcon("img/newgame.png"));
				newGame.setBorderPainted(false);
				newGame.setFocusPainted(false);
				newGame.setContentAreaFilled(false);
			}
			public void mouseClicked(MouseEvent e) {}	
		}
		);		

		enterName.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent e){
						String name = getName.getText();
						// adds the score to the save highscore data
						highscore.addScore(name,player.returnPoints());
						//then back to menu

						options.getResetFrame();

						options.mainMenu();
						options.getMenuFocus(true);

						options.getRefreshFrame();
				}
			}
		);

		newGame.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent e){
					// container.remove(overPanel);
					// container.remove(newGame);
					// container.remove(scoreDisplay);
					options.getResetFrame();
					// container.revalidate();
					// container.repaint();
					options.getRefreshFrame();
					
					//instantiate player
					
					Stage.setTotalSharks(0);
					Shark.setNumberDead(0); //set number of creatures to zero, since user wants a new game
					Piranha.setNumberDead(0);
					Stage.setTotalPiranhas(0);
					
					final Player player = new Player(0,170,"img/scuba.gif");
					final Stage stage = new Stage(options, container, player);
					Player.stage = stage;
					container.add(stage, BorderLayout.CENTER);
					// focuses on the JPanel, which is the stage
					stage.setFocusable(true);
					stage.requestFocus();
					typer.resetLabel();
					// container.revalidate();
					// container.repaint();
					options.getRefreshFrame();
				}
			}
		);

		backGame.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent e){
					// container.remove(overPanel);
					// container.remove(newGame);
					// container.remove(scoreDisplay);
					options.getResetFrame();

					options.mainMenu();
					options.getMenuFocus(true);

					// container.revalidate();
					// container.repaint();
					options.getRefreshFrame();
				}
			}
		);
		
		options.getRefreshFrame();


	}
	
	public void addBackground(){
		this.add(bg);
	}

	public boolean getSharkWave(){
		return sharkWave;
	}

	public boolean getPiranhaWave(){
		return piranhaWave;
	}
	public boolean bossWave(){
		return bossWave;
	}

	public JLabel getTypedLabel(){
		return this.typed;
	}

	public String getAnswer(){
		return this.answer;
	}

	public static int getTotalSharks(){
		return Stage.totalSharks;
	}

	public static int getTotalPiranhas(){
		return Stage.totalPiranhas;
	}

	public static void setAnswer(String answer){
		Stage.answer = answer;
	}

	public static void setTotalSharks(int value){
		Stage.totalSharks = value;
	}

	public static void setTotalPiranhas(int value){
		Stage.totalPiranhas = value;
	}

	public void setDepthLabel(){
		depthLabel.setText("Depth: "+player.getDepth());
	}

	public void setLifeLabel(){
		lifeLabel.setText("Life: "+player.returnLife());
	}

	public void setLevelLabel(){
		levelLabel.setText("Level: "+player.returnLevel());
	}

	public void setPointLabel(){
		pointLabel.setText("Points: "+player.returnPoints());
	}
	
}
