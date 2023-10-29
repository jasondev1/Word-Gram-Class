
/**
 * Write a description of class MarkovRunner here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import edu.duke.*;

public class MarkovRunner {
    public void runModel(IMarkovModel markov, String text, int size){ 
        markov.setTraining(text); 
        System.out.println("running with " + markov); 
        for(int k=0; k < 6; k++){ 
            String st = markov.getRandomText(size); 
            printOut(st); 
        } 
    } 

    public void runModel(IMarkovModel markov, String text, int size, int seed){ 
        markov.setTraining(text); 
        markov.setRandom(seed);
        System.out.println("running with " + markov); 
        for(int k=0; k < 6; k++){ 
            String st = markov.getRandomText(size); 
            printOut(st); 
        } 
    } 

    public void runMarkov() { 
        FileResource fr = new FileResource(); 
        String st = fr.asString(); 
        st = st.replace('\n', ' '); 
        MarkovWord mw = new MarkovWord(2); 
        runModel(mw, st, 25, 643); 
    } 
    
    public void runMarkovEfficient() { 
        FileResource fr = new FileResource(); 
        String st = fr.asString(); 
        //String st = "this is a test yes this is really a test yes a test this is wow";
        st = st.replace('\n', ' '); 
        EfficientMarkovWord mw = new EfficientMarkovWord(5); 
        runModel(mw, st, 50, 531); 
        //mw.printHashMapInfo();
    } 
    
    
    public void compareMethods () {
    
        FileResource fr = new FileResource(); 
        String st = fr.asString(); 
        //String st = "this is a test yes this is really a test yes a test this is wow";
        st = st.replace('\n', ' '); 
        EfficientMarkovWord mw = new EfficientMarkovWord(2); 
        runModel(mw, st, 200, 42); 
        System.out.println(System.nanoTime());
        
        MarkovWord mm = new MarkovWord(2); 
        runModel(mm, st, 200, 42); 
        System.out.println(System.nanoTime());
    
    }

    private void printOut(String s){
        String[] words = s.split("\\s+");
        int psize = 0;
        System.out.println("----------------------------------");
        for(int k=0; k < words.length; k++){
            System.out.print(words[k]+ " ");
            psize += words[k].length() + 1;
            if (psize > 60) {
                System.out.println(); 
                psize = 0;
            } 
        } 
        System.out.println("\n----------------------------------");
    } 

}
