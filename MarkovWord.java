
/**
 * Write a description of MarkovWord here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import edu.duke.*;
import java.util.*;
import java.io.*;
import java.util.List;
import java.util.ArrayList;

public class MarkovWord implements IMarkovModel{
    private String[] myText;
    private Random myRandom;
    private int myOrder;

    public MarkovWord(int order) {
        myRandom = new Random();
        myOrder = order;
    }

    public void setTraining(String text){
        myText = text.split("\\s+");
        System.out.println("myText is " + Arrays.toString(myText));
    }

    public void setRandom(int seed) {
        myRandom = new Random(seed);
    }

    public String getRandomText(int numWords) { 

        StringBuilder sb = new StringBuilder();
        int index = myRandom.nextInt(myText.length-myOrder);  // random word to start with
        
        //Create the WordGram key
        WordGram currKey = new WordGram(myText,index,myOrder);

        sb.append(currKey);
        sb.append(" ");
        System.out.println("current sb is " + sb);
        for(int k=0; k < numWords-1; k++){
            ArrayList<String> follows = getFollows(currKey);
            //System.out.println("current getFollows is " + follows);
            if (follows.size() == 0) {
                break;
            }
            index = myRandom.nextInt(follows.size());
            String next = follows.get(index);
            sb.append(next);
            sb.append(" ");
           
            //System.out.println("current sb is " + sb);
            currKey = currKey.shiftAdd(next);

        }

        return sb.toString().trim();

    }

    private ArrayList<String> getFollows(WordGram kGram) {
        ArrayList<String> follows = new ArrayList<String>();
        String currString = kGram.toString();

        for (int i = 0; i < myText.length-myOrder; i++) {
            // process each item in turn 
            int currIndex = indexOf (myText, kGram, i);

            //System.out.println("i is " + i);
            //System.out.println("currIndex is " + currIndex);
            if (currIndex != -1 & currIndex < myText.length-myOrder) {
                follows.add(myText[(currIndex+myOrder)]);

                //System.out.println("follows list is " + follows);
                i = (currIndex+myOrder)+1;
               
            }

        } 

        //System.out.println("follows list is " + follows);
        return follows;

    }

    private int indexOf (String[] words, WordGram target, int start) {
        //This method will create a new WordGram for each index postion of (myOrder) size, then compare 
        //that WordGram to the target Wordgram passed in

        //This method starts looking at the start position and returns the first index location in words that matches target. 
        //If no word is found, then this method returns -1.
        //System.out.println("this is a test ");
        int indexFound = -1;
        int targetSize = target.length();
        //System.out.println("words is " + Arrays.toString(words));
        
        //Loop over words and find the target string 
        for (int i = start; i < words.length; i++) {
            //System.out.println("i is " + i);
            int tempi = start;
            //System.out.println("tempi is " + tempi);
            //System.out.println("targetsize is " + targetSize);
            //Loop over and create a new array of just the (myOrder) length
            ArrayList <String> currList= new ArrayList <String>(); 
            for (int j = 0; j < targetSize; j++) {
                currList.add(words[tempi]);
                //System.out.println("currList is " + currList);
                tempi = tempi + 1;
            }
            //System.out.println("currlist is " + currList);
            if (currList.size() == 0) {
            //System.out.println("we are breaking ");
            break;
            }
            //Convert the ArrayList to an Array
            String[] str = new String[currList.size()];
            for (int k = 0; k < currList.size(); k++) {
            str[k] = currList.get(k);
             }
            
            //System.out.println("str is " + Arrays.toString(str));
            //System.out.println("i is  " + i);
            //System.out.println("myOrder is  " + myOrder);
            //Create new WordGram using current postion 
            WordGram currGram = new WordGram(str,0,myOrder);
            //System.out.println("currGram is " + currGram.toString());
            //System.out.println("target is " + target.toString());
            //Cpmare the taget WordGram with the current postioned WordGram
            if (target.equals(currGram)) {
                indexFound = i;
                start = i;
                //System.out.println("true is true ");
                break;
            }
            
            indexFound = -1;
            //System.out.println("false is true ");
            //targetSize = targetSize+1;
            

            
        } 

        return indexFound;

    }

    public void testIndexOf () {
        String st = "this is just a test yes this is a simple test";
        String[] stNew = {};
        stNew = st.split("\\s+");
        myText = stNew;
        System.out.println("myText is " + Arrays.toString(myText));

        int size = 4;
        myOrder = 4;
        //int indexFound = indexOf(stNew,"test",5);

        //MarkovWord mkw = new MarkovWord(4);
        WordGram wgg = new WordGram(myText,1,size);
        System.out.println("WordGram is " + wgg.toString());
        ArrayList<String> testList = getFollows(wgg);
        System.out.println("hashCode is " + wgg.hashCode());
        
        //System.out.println("Index Found is " + indexFound);
    }

}
