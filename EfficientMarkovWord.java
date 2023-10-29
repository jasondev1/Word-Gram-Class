
/**
 * Write a description of EfficientMarkovWord here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import edu.duke.*;
import java.util.*;
import java.io.*;
import java.util.List;
import java.util.ArrayList;

public class EfficientMarkovWord implements IMarkovModel{
    private String[] myText;
    private Random myRandom;
    private int myOrder;
    private HashMap<WordGram,ArrayList<String>> myHashMap = new HashMap<WordGram,ArrayList<String>>();

    public EfficientMarkovWord(int order) {
        myRandom = new Random();
        myOrder = order;
    }

    public void setTraining(String text){
        myText = text.split("\\s+");
        System.out.println("myText is " + Arrays.toString(myText));
        buildMap();
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
        //System.out.println("current sb is " + sb);

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

        //Search the HashMap for the WordGram, then return the value 

        ArrayList<String> myList = new ArrayList<String>();

        for (WordGram w : myHashMap.keySet()) {
            WordGram currGram = w;

            if (w.equals(kGram)) {
                myList = myHashMap.get(w);

            }

        } 

        //if It doesnt exist, then we return -1

        return myList;

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

    public void buildMap () {
        //This method will build the HashMap to store a complete list of all follow after Characters of myOrder combinations

        //Loop over myText and get all possible combinations of the key length myOrder 
        //Once you have a key with myOrder length, you will call getFollows on it
        //Store the ArrayList<String> from getFollows in the HashMap as the value 
        //So you will end up with a key of hashCode(by calling hashCode) and value of ArrayList<String>

        //Loop over myText and build the WordGram
        for (int i = 0; i < myText.length-(myOrder-1); i++) {
            //System.out.println("myText length is " + myText.length);

            int tempi = i;
            //Loop over and create a new array of just the (myOrder) length
            ArrayList <String> currList= new ArrayList <String>(); 
            for (int j = 0; j < myOrder; j++) {
                if (j >= myText.length) {
                    System.out.println("we are breaking AA ");
                    break;
                }
                currList.add(myText[tempi]);
                tempi = tempi + 1;
                //System.out.println("currList is " + currList);
            }
            //System.out.println("currlist is " + currList);
            if (currList.size() == 0) {
                System.out.println("we are breaking A ");
                break;

            }
            //Convert the ArrayList to an Array
            String[] str = new String[currList.size()];
            for (int k = 0; k < currList.size(); k++) {
                str[k] = currList.get(k);
            }
            //System.out.println("str is " + Arrays.toString(str));
            WordGram currGram = new WordGram(str,0,myOrder);
            //First, see if this wordgram already exists in the HashMap
            //If the Wordgram already exists in the HashMap, ignore it 
            if (!myHashMap.containsKey(currGram)) {

                ArrayList<String> follows = new ArrayList<String>();

                for (int m = 0; m < myText.length-(myOrder); m++) {
                    int currIndex = indexOf (myText, currGram, m);

                    if (currIndex != -1 & currIndex < myText.length-myOrder) {
                        follows.add(myText[(currIndex+myOrder)]);

                        m = (currIndex+myOrder);
                    }

                    //Remove they key and readd the key and value back to the HashMap 

                }

                //System.out.println("follows list is " + follows);

                myHashMap.put(currGram,follows);

            }
            //If the Wordgram is not in the list, add the WordGram to the HashMap with an empty ArrayList

        }
        //System.out.println("myHashMap is " + myHashMap);
        //System.out.println("myHashMap sizer is " + myHashMap.size());

        //int mapSize = myHashMap.size();
        //int i = 0;
        /*
        //Loop over the HashMap, get the kGram, repeatabley call IndexOf, and add all the characters to the new ArrayList 
        for (WordGram w : myHashMap.keySet()) {
        ArrayList<String> follows = new ArrayList<String>();

        WordGram currGram = w;

        //System.out.println("current wordgram is " + w.toString());
        //Loop over myText and call Index of repeatably to get all the follow characters
        for (int m = 0; m < myText.length-(myOrder-1); m++) {
        int currIndex = indexOf (myText, currGram, m);

        if (currIndex != -1 & currIndex < myText.length-myOrder) {
        follows.add(myText[(currIndex+myOrder)]);

        m = (currIndex+myOrder)+1;
        }

        //Remove they key and readd the key and value back to the HashMap 

        }

        System.out.println("follows list is " + follows);
        //follows.clear();
        } 
         */
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
        buildMap();
        System.out.println("myHashMap is " + myHashMap);
        //System.out.println("Index Found is " + indexFound);
    }

    public void printHashMapInfo () {
        buildMap();

        //Print the HashMap (all the keys and their corresponding values)
        /*
        for (WordGram s : myHashMap.keySet()) {
            //Print the key and value 
            System.out.println("key is " + s);
            System.out.println("vlaue is " + myHashMap.get(s));
        } 

        */
        //Print the number of keys in the HashMap
        System.out.println("total number of keys is " + myHashMap.size());

        //Print the size of the largest value in the HashMap—that is, the size of the largest ArrayList of characters
        //loop over the keys, out the value into an ArrayList
        //Then run size() on array list and see if its bigger or equal to the largest 
        int highestSize = 0;
        WordGram highestGram = new WordGram(myText,0,myOrder);
        ArrayList<WordGram> gramList = new ArrayList<WordGram>();
        for (WordGram s : myHashMap.keySet()) {
            //Print the key and value 
            ArrayList<String> currList = myHashMap.get(s);
            int currSize = currList.size();

            if (currSize >= highestSize) {
                highestSize = currSize;
                highestGram = s;
            } 

        }
        
        //Now that you have the highest value, you can add all the WordGrams with that number

        for (WordGram g : myHashMap.keySet()) {
            //Print the key and value 
            ArrayList<String> currList = myHashMap.get(g);
            int currSize = currList.size();

            if (currSize == highestSize) {
                gramList.add(g);
            } 

        }
        
        //Loop over the list and print it 
        for (WordGram s : gramList) {
        // process each item in turn 
        System.out.println("WordGram with most values is " + s.toString() + " with " + highestSize + " values");
        } 
        

    
    }
    
    
    
    
}
