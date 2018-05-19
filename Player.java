
package classes;


import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.io.Serializable;


class Player extends ScubaDiver implements Runnable, Serializable{

    private int life;

    ////NOT YET USED
    private int powerMeter;

    private int points;

    private int depth;
    
    public static boolean isGameOver;

    private static boolean bossSpawned = false;
	private static final int DEPTH_CONSTANT = 1;

    // REMOVE
    //contains the user's answer
    //private String input; 

    //if the user wants to use the 'power'
    Boolean isCharge; 
    static Stage stage;
    public Player(int x, int y,String filename){
         super(x,y);
         this.level = 1;
         this.life = 3;
         Player.isGameOver =false;
         this.depth = 0;
         loadImage(filename);
    }

    public int returnLevel(){
        return this.level;
    }

    public int returnPoints(){
        return this.points;
    }

    public int returnLife(){
        return this.life;
    }

    public int getDepth(){
    	return this.depth;
    }

    public void dive(){
		this.depth += DEPTH_CONSTANT;
	}
      public void incLife(){
        this.life++;
    }
    public void decPoints(){
        this.points-=2; //deducts score by 2
    }
    
    public void incLevel(){
    	this.level+=1;
    }

	//decreases player's life by 1
    public void decLife(){
    	this.life -= 1; 
    }

	//increments the points of the user
    public void addScore(int worth){
    	this.points += worth; 
    }

    public boolean isAlive(){
		// if the player is dead
		if(this.life<0) 
			return false; 

		return true;
    }
   
    public boolean isGameFinished(){
        if(this.level>=21) return true;
        return false;
    }
    
    
    @Override
    public void run(){
      //this.isAlive()
        while(this.isAlive()&&!this.isGameFinished()){ //player is alive, and the levels arent finished yet
            if(this.level ==15 && !Player.bossSpawned) {
                Player.stage.spawnBoss("img/boss.gif"); //first boss
                Player.stage.addBackground();
            }else if(this.level ==20 && !Player.bossSpawned){
                Player.stage.spawnBoss("img/bossOctopus.gif");
                Player.stage.addBackground();   //second boss
            }
            try{
            this.repaint();
                Thread.sleep(1000);

            }catch(Exception e){}
        }
        Player.isGameOver = true;
        Shark.autoKill = true;
        Boss.beKilled =true;
        if(this.isGameFinished()) stage.congratulations(); //if the user finished upto level 20
        else stage.gameOver(); //if the user dies in the middle of the game
        
        
    }

    @Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;

		// you may override this then change the size
		g2d.drawImage(this.getImage(),this.getXPos(),this.getYPos(),100,60,null); 
		Toolkit.getDefaultToolkit().sync();
	}

    public void setStage(Stage stage){
        this.stage = stage;
    }

    public static void setBossSpawnState(boolean state){
        Player.bossSpawned = state;
    }

}
