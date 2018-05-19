
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

class Missile extends Sprite implements Runnable{
	static boolean beKilled;
	private String word;
	private Player player;
	private Stage stage;

	private Boss boss;

	private int move = 0;
	private static ArrayList<String> usedWord = new ArrayList<String>();

	public Missile(int x, int y, String filename, Player player, Stage stage, Boss boss){
		super(x,y);
		loadImage(filename);
		this.player = player;
		this.stage = stage;
		Missile.beKilled = false;
		
		this.word = randomWord((new Random()).nextInt(501));
		this.boss = boss;
		while(usedWord.contains(this.word))
			this.word = randomWord((new Random()).nextInt(501));
	}
	
	private String randomWord(int randomNthWord){

		try{
			BufferedReader read_word = new BufferedReader(new FileReader("listwords.txt"));
			String line;

			// counter used to find the nth word in the list of words file
			for(int counter = 0; (line = read_word.readLine()) != null; counter++){
				// if the counter equal to the specified nth word
				if(counter==randomNthWord)
					return line;
			}

		}catch(Exception e){}

		// if the file was not found, default-ly return an empty string
		return "";
	}

	protected boolean isCorrect(){
		if(this.word.equals(this.stage.getAnswer())) {
			this.move = 1;
			loadImage("img/missileoctopus_right.gif");
			
			this.repaint();
			return true;
		}

		return false;
	}

	private void moveLeft(){
		this.xPos--;
	}

	private void moveRight(){
		this.xPos++;
	}

	private void whatMove(int direction){
		if(direction == 1)
			moveRight();

		else if(direction == 0)
			moveLeft();
	}

	private boolean isEnd(){
		if(this.xPos<=50&&this.move == 0){
			if(player.returnLife()>0){
				System.out.println("Life: "+player.returnLife());
				player.decLife();
				this.stage.setLifeLabel();
				
			}else player.decLife();

			
			return true;
		}

		else if(this.xPos>=boss.getXPos()&&this.move ==1){
			if(this.xPos>=300){
				boss.decLife(40);
			}

			else if(this.xPos>=200){
				boss.decLife(30);
			}

			else  
				boss.decLife(20);

			return true;
		}

		return false;
	}

	public void run(){
		while(!this.isEnd() && !Missile.beKilled){
			this.isCorrect();
			this.whatMove(this.move);
			this.repaint();
			
			try{
				//speed depends on the level. as the level gets higher, sharks get faster
				Thread.sleep(20);

			}catch(Exception e){}
		}

		this.loadImage("img/monster_dead.gif");

		if(this.xPos>51) {
			this.word = "+3 points";
			player.addScore(3);
			stage.setPointLabel();// refresh the points obtained by the player
		}
		else this.word = "";

		for(int i = 0; i < 10; i++){
			this.repaint();
			try{
				//when shark dies, it spins first before it disappears
				Thread.sleep(100);

			}catch(Exception e){}
		}
		this.setVisible(false);

	}

	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;

		g2d.drawImage(this.getImage(),this.getXPos(),this.getYPos(),90,40,null); // yu may override this then change the size
		g.setColor(Color.WHITE);
		g2d.drawString(this.word,this.getXPos()+23,this.getYPos()+20);
		Toolkit.getDefaultToolkit().sync();
	}

}