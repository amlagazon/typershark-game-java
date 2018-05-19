package classes;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.util.Random;
import java.awt.Color;
class Boss extends SeaCreatures implements Runnable{

	private static int missileCounter;
	private int life;
	private int toRelease=0;
	static boolean beKilled=false;
	private Random r = new Random();

	public Boss(int x, int y, String filename, Player player, Stage stage){
		super(x,y);
		loadImage(filename);
		this.player = player;
		this.stage = stage;
		this.life = 500;
		
	}

	protected void swim(){
		//boss is a faster swimmer
		this.xPos-=1; 
	}

	private boolean isDead(){
		if(this.life<=0) 
			return true;

		return false;
	}

	public void decLife(int minus){
		this.life-=minus;
	}

	private void releaseMissile(){
		int y1,y2,x1,x2;

		y1= r.nextInt(90)+140;
		y2= r.nextInt(90)+140;
		x1= r.nextInt(100);
		x2= r.nextInt(100);

		Missile missile1 = new Missile(this.xPos+x1,y1,"img/missileoctopus_left.gif",player,stage,this);
		Missile missile2 = new Missile(this.xPos+x2,y2,"img/missileoctopus_left.gif",player,stage,this);

		Thread thread1 = new Thread(missile1);
		Thread thread2 = new Thread(missile2);

		stage.add(missile1);
		stage.add(missile2);

		thread1.start();
		thread2.start();

	}

	public void run(){
		while(!this.isDead() &&!Boss.beKilled){
			this.swim();
			this.repaint();
			toRelease++;
			//relases missiles when toRelease is 12
			if(toRelease==12){ 
				toRelease=0; //then will reset to 0
				this.releaseMissile();
				stage.addBackground();
			}

			try{
				Thread.sleep(300);
			}catch(Exception e){}
		}

		this.setVisible(false);
		Missile.beKilled = true;
		player.incLevel();
		stage.setLevelLabel();

		player.setBossSpawnState(false);
		
		if(!(player.returnLevel()>20)) //game only has 20 levels
		{
			stage.createSharks();
			stage.addBackground();
		}
		
		
		Shark.setNumberDead(0);	

	}

	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;

		g2d.drawImage(this.getImage(),this.getXPos(),this.getYPos(),250,220,null); // yu may override this then change the size
		g.setColor(Color.RED);
		g2d.drawString("Life: " + String.valueOf(this.life),this.getXPos()+35,this.getYPos()+25);
		Toolkit.getDefaultToolkit().sync();
	}
}