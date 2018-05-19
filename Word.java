package classes;

import java.util.ArrayList;
interface Word{
    public ArrayList<String> ListWords = new  ArrayList<String>();         //words from a file

    public static int difficulty=5; //where length of words depend
    public String randomWord(int randomNthWord);
}
