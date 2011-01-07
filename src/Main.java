/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



import collocations.Bigrams;
import java.io.IOException;
import java.util.Date;


/**
 *
 * @author dj
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        Date start = new Date();
        Bigrams bigrams = new Bigrams(1000000);
        bigrams.loadBigramsFromFile("brown_tei/Corpus.xml");
        //bigrams.printMostFrequentBigrams(100);
        //bigrams.printHashMap(bigrams.getBigramsWithCount(), "hashmap");
        bigrams.printToFileMostFrequentBigrams("frequency.txt", 1000);
        puts(new Date().getTime() - start.getTime());
    }

    public static void puts(Object toPrint) {
        System.out.println(toPrint);
    }
}
