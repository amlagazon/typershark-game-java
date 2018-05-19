
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

import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Random;

public class Piranha extends SeaCreatures implements Runnable{

	private String letter;

	private static int numberDead;

	private static ArrayList<String> usedLetter = new ArrayList<String>();
	
	public Piranha(int x, int y, String filename, Player player, Stage stage){
		super(x,y);
		loadImage(filename);

		// String.valueOf(char ) converts a char character into a string character
		this.letter = String.valueOf(randomLetter());

		while(Piranha.usedLetter.contains(this.letter)){
			this.letter = String.valueOf(randomLetter());		//to assure no letters would be repeated	
		}

		Piranha.usedLetter.add(this.letter);

		this.player = player;
		this.stage = stage;
		this.worth = 3;
	}

	private char randomLetter(){
		return (char)((new Random()).nextInt(26) + 97);
	}

	protected boolean isCorrect(){
		if(this.letter.equals(this.stage.getAnswer())) {
			player.addScore(this.worth);
			stage.setPointLabel();// refresh the points obtained by the player
			return true;
		}

		return false;
	}

	protected boolean isCreatureInEnd(){

		//if the creature reaches the end.
		if(this.xPos <= 10){
			player.decLife();
			System.out.println("Life: "+player.returnLife());
			this.stage.setLifeLabel();
			return true;
		}

		return false;
	}

	private static void resetList(){
		ListIterator<String> itr = usedLetter.listIterator();
		while(itr.hasPrevious()){
			itr.remove();
		}
	}

	protected void swim(){
		this.xPos -= 1;
	}

	protected static void setNumberDead(int value){
		Piranha.numberDead = value; 
	}

	public void run(){

		//will end swimming if the word is typed correctly and if the shark reaches end
		while(!this.isCorrect() && !this.isCreatureInEnd()){ 
			this.swim();
			this.repaint();
			this.speed = 20-player.returnLevel();

			if(this.speed <= 18) 
				this.speed = 15;

			try{
				//speed depends on the level. as the level gets higher, sharks get faster
				Thread.sleep(this.speed); 

			}catch(Exception e){}
			
		}
		this.loadImage("img/monster_dead.gif");
		if (!this.isCreatureInEnd())this.letter =  "+3 points";
		else this.letter="";
		for(int i = 0; i < 10; i++){
			this.repaint();
			try{
				//when shark dies, it spins first before it disappears
				Thread.sleep(80);

			}catch(Exception e){}
		}
		
		this.setVisible(false);
		Piranha.numberDead+=1;

		if(Piranha.numberDead == stage.getTotalPiranhas()&&!Player.isGameOver){

			Piranha.resetList(); //to empty the array list of used Letters

			System.out.println("All piranhas are dead!");

			stage.setTotalPiranhas(0);

			player.incLevel();
			stage.setLevelLabel();

			stage.createSharks();
			stage.addBackground();

			Shark.setNumberDead(0);	

		}
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		
		// yu may override this then change the size
		g2d.drawImage(this.getImage(),this.getXPos(),this.getYPos(),60,40,null); 
		g.setColor(Color.WHITE);
		g2d.drawString(this.letter, this.getXPos() + 35, this.getYPos() + 20);
		Toolkit.getDefaultToolkit().sync();
	}

}
