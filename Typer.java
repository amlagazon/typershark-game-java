
package classes;

import classes.Stage;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class Typer implements KeyListener{

	// stores the typed letters
	private String temp = "";
	private Stage stage;

	public Typer(Stage stage){
		this.stage = stage;
		Stage.typer = this;
		MyFrame.typer=this;
	}
	
	// keys being typed will be listened automatically when window is focused on the game
	public void keyPressed(KeyEvent ke){

		// enters the word to be searched throughout the list of sharks
		// ONLY USED WHEN IT IS A SHARK WAVE
		if(this.stage.getSharkWave()){
			if (ke.getKeyChar() == KeyEvent.VK_ENTER){

				//when entered, will update the 'answer' variable
				this.stage.setAnswer(temp);
				temp="";

				// displays the typed keys
				stage.getTypedLabel().setText(temp);

			}// deletes the whole typed word
			else if(ke.getKeyChar() == KeyEvent.VK_BACK_SPACE){
				if(temp.length()!=0) temp = temp.substring(0,temp.length()-1); //delete last letter
				this.stage.getTypedLabel().setText(temp);

			}else if(Character.isLetter(ke.getKeyChar())){
				temp += String.valueOf(ke.getKeyChar()).toLowerCase(); //only accept lower case letters
				this.stage.getTypedLabel().setText(temp);
			}



		}else if(this.stage.getPiranhaWave()){

			// resets the typed word display
			temp = "";
			this.stage.getTypedLabel().setText(temp);

			// pressing a key immediately enters that key to the answer
			// ONLY USED WHEN IT IS A PIRANHA WAVE
			if(ke.getKeyChar() == KeyEvent.VK_BACK_SPACE){
				temp = "";
				this.stage.getTypedLabel().setText(temp);

			}else if(Character.isLetter(ke.getKeyChar())){// String.valueOf(char ) converts a char character into a string character
				this.stage.setAnswer(String.valueOf(ke.getKeyChar()).toLowerCase());
			}

			// normally adds the key pressed into display
			// ONLY USED WHEN IT IS A SHARK WAVE

		}else{
			if (ke.getKeyChar() == KeyEvent.VK_ENTER){

				//when entered, will update the 'answer' variable
				this.stage.setAnswer(temp);
				temp="";

				// displays the typed keys
				stage.getTypedLabel().setText(temp);

			}// deletes the whole typed word
			else if(ke.getKeyChar() == KeyEvent.VK_BACK_SPACE){
				if(temp.length()!=0) temp = temp.substring(0,temp.length()-1);//delete last letter
				this.stage.getTypedLabel().setText(temp);

			}else if(Character.isLetter(ke.getKeyChar())){
				temp += String.valueOf(ke.getKeyChar()).toLowerCase(); //only accept lower case letters
				this.stage.getTypedLabel().setText(temp);
			}
		}


	}

	public void keyTyped(KeyEvent ke){}
	public void keyReleased(KeyEvent ke){}
	public void resetLabel(){
		temp = "";
		this.stage.getTypedLabel().setText(temp);
	}
}