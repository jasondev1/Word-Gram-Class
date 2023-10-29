import edu.duke.*;
import java.util.*;
import java.io.*;
import java.util.List;
import java.util.ArrayList;

public class WordGram {

    private String[] myWords;
    private int myHash;

    public WordGram(String[] source, int start, int size) {
        myWords = new String[size];
        System.arraycopy(source, start, myWords, 0, size);
    }

    public String wordAt(int index) {
        if (index < 0 || index >= myWords.length) {
            throw new IndexOutOfBoundsException("bad index in wordAt "+index);
        }
        return myWords[index];
    }

    public int length(){
        return myWords.length;
    }

    public String toString(){
        String ret = "";
        for (int k=0; k < myWords.length; k++) {
            ret += myWords[k] + " ";
        }
        return ret.trim();
    }

    public boolean equals(Object o) {
        WordGram other = (WordGram) o;
        if(this.length() != other.length()){
            return false;
        }

        for (int k=0; k < myWords.length; k++) {
            if (! myWords[k].equals(other.wordAt(k))) {
                return false;
            }
        }

        return true;
    }

    public WordGram shiftAdd(String word) {	

        //Loop over and shift the positions down by 1, you have have a new array list of 1 less
        ArrayList<String> tempWords = new ArrayList<String>();

        for (int k=1; k < myWords.length; k++) {
            tempWords.add(myWords[k]);
        }

        //then add the new word to the end of the array 
        tempWords.add(word);
        //Convert the ArrayList to an Array   
        String[] tempWordsArray = tempWords.toArray(new String[tempWords.size()]);

        //Make a WordGram using the new array         
        WordGram out = new WordGram(tempWordsArray, 0, tempWordsArray.length);

        // shift all words one towards 0 and add word at the end. 
        // you lose the first word
        // TODO: Complete this method

        //System.out.println("tempWords ArrayList is" + tempWords);
        //System.out.println("tempWordsArray is " + Arrays. toString(tempWordsArray));
        //System.out.println("shift add ran");
        //System.out.println("out is " + out.toString());
        return out;
    }

    
    public int hashCode () {
    
    int answer = 0;
    
    String currString = this.toString();
    
    answer = currString.hashCode();
    
    return answer;
    
    
    }
    
    
    
    
    
    
    
    
    
}