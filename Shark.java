
package classes;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.Color;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;

import java.util.ArrayList;
import java.util.Random;

class Shark extends SeaCreatures implements Runnable{
	static boolean autoKill; //for kill all powerup

	private String word;
	
	private static int numberDead;
	
	private static ArrayList<String> usedWord = new ArrayList<String>();
	private Random r = new Random();
	
	private int whatPowerUp=0; //1 =Kill all, 2 slow them, 3 = +1 life

	public Shark(int x, int y, String filename, Player player, Stage stage){
		super(x,y);
		Shark.autoKill = false;
		
		loadImage(filename);

		this.whatPowerUp =r.nextInt(50); //randomize what powerUps
		this.word = randomWord((new Random()).nextInt(501));

		while(usedWord.contains(this.word))
			this.word = randomWord((new Random()).nextInt(501));

		usedWord.add(this.word);

		this.player = player;
		this.stage = stage;
		this.worth = 5;
		Shark.speed = 35 - player.returnLevel();

		if(Shark.speed <=20)
			Shark.speed = 20; //maximum speed
	}

	// loads a file of the list of words
	private String randomWord(int randomNthWord){

		try{
			BufferedReader read_word = new BufferedReader(new FileReader("listwords.txt"));
			String line;

	 		// counter used to find the nth word in the list of words file
			for(int counter = 0; (line = read_word.readLine()) != null; counter++){
				
				// if the counter is equal to the specified nth word
				if(counter==randomNthWord)
					return line;
			}

		}catch(Exception e){}


		// if the file was not found, default-ly return an empty string
		return "";
	}

	////FIXED THE MODIFIERS
	protected boolean isCorrect(){
		if(this.word.equals(this.stage.getAnswer())) {
			
			player.addScore(this.worth);
			stage.setPointLabel();// refresh the points obtained by the player

			return true;
		}
	
		return false;
	}

	protected boolean isCreatureInEnd(){

		//if the shark reaches the end.
		if(xPos <= 10){
			if(player.returnLife()<=0) player.decLife(); //so that life label would not display negative values
			else{
				player.decLife();
				System.out.println("Life: " + player.returnLife());
				this.stage.setLifeLabel();
			}
			
			
			return true;
		}

		return false;
	}

	protected void swim(){
		 this.xPos-=1;
	}

	protected static void setNumberDead(int value){
		Shark.numberDead = value;
	}
	
	public void run(){

		//will end swimming if the word is typed correctly and if the shark reaches end
		while(!this.isCorrect() && !this.isCreatureInEnd()&& !this.autoKill){
			this.swim();
			this.repaint();
			
			try{
				//speed depends on the level. as the level gets higher, sharks get faster
				Thread.sleep(Shark.speed);

			}catch(Exception e){}

		}

		this.loadImage("img/shark_dead.gif");
		//if kill-all is used
		if(Shark.autoKill) {
			player.addScore(5);
			stage.setPointLabel();// refresh the points obtained by the player
		}

		if(this.whatPowerUp==1 && this.word.equals(this.stage.getAnswer()) ) { //checks if the shark has powerup
		//+1 Life
			player.incLife();
			stage.setLifeLabel();
			this.word = "+1 Life";
		}

		else if(this.whatPowerUp==2 && this.word.equals(this.stage.getAnswer())) {
			//slow powerup
			Shark.speed = 100;
			this.word="Slow";

			try{
				//slows sharks for 5 seconds
				Thread.sleep(5000);

			}catch(Exception e){}

			Shark.speed = 35 - player.returnLevel();

			if(Shark.speed <= 20)
				Shark.speed = 20; //maximum speed

		}else if(this.whatPowerUp==3 && this.word.equals(this.stage.getAnswer())){
			//kill all
			Shark.autoKill = true;
			this.word="GOD LIKE!";
			try{
				//slows sharks for 5 seconds
				Thread.sleep(2000);

			}catch(Exception e){}
		}

		else if (!this.isCreatureInEnd())this.word = "+5 points";
		else this.word="";

		for(int i = 0; i < 10; i++){
			this.repaint();
			try{
				//when shark dies, it spins first before it disappears
				Thread.sleep(100);

			}catch(Exception e){}
		}
		
		this.setVisible(false);
		Shark.numberDead+=1;

		if(Shark.numberDead == stage.getTotalSharks() &&!Player.isGameOver){

			System.out.println("All sharks are dead!");
			stage.setTotalSharks(0);
			player.incLevel();
			stage.setLevelLabel();

			if(player.returnLevel() == 10 || player.returnLevel() == 5 || player.returnLevel()==18){
				stage.createPiranhas();
				Piranha.setNumberDead(0);

			}else if(player.returnLevel()!=15&&player.returnLevel()!=20){ 
				stage.createSharks();
				Shark.numberDead = 0;
			}
		}
	}

	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;

		g2d.drawImage(this.getImage(),this.getXPos(),this.getYPos(),120,50,null); // yu may override this then change the size
		if(!this.word.equals(this.stage.getAnswer())){ //when the shark dies, the word it carries disappears

			if(this.whatPowerUp!=1 && this.whatPowerUp!=2 && this.whatPowerUp!=3){
				g.setColor(Color.WHITE);
				g2d.drawString(this.word,this.getXPos()+28,this.getYPos()+30);
			}else { //kapag power up, yellow/green yung word
				if(this.whatPowerUp==1){
					g.setColor(new Color(27, 235, 36));
					g2d.drawString(this.word,this.getXPos()+28,this.getYPos()+30);
				}else if (this.whatPowerUp==2){
					g.setColor(new Color(130, 209, 255));
					g2d.drawString(this.word,this.getXPos()+28,this.getYPos()+30);
				}else if (this.whatPowerUp==3){
					g.setColor(Color.RED);
					g2d.drawString(this.word,this.getXPos()+28,this.getYPos()+30);
				}
				
			}
		}
		Toolkit.getDefaultToolkit().sync();
	}

}
