
package classes;


public abstract class SeaCreatures extends Sprite{
	
	//SeaCreatures have the following attributes
	protected static int speed;
	protected int worth;
	protected Player player;
	protected Stage stage;
	
	public SeaCreatures(int x, int y){
		super(x,y);
	}

	protected void swim(){}

	protected boolean isCorrect(){
		return false;
	}

	protected boolean isCreatureInEnd(){
		return false;
	}

	protected static void setNumberDead(){}
	
}