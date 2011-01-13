import collocations.BigramForSimilarity;
import collocations.Bigrams;
import collocations.BigramWithMeasures;
import collocations.Context;
import collocations.Similarity;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import javax.swing.text.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


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
//        Bigrams bigrams = new Bigrams(1000000);
//        bigrams.loadBigramsFromFile(args[0]);
//        if ("-f".equals(args[2])) {
//            bigrams.printBigramsToFileSortedBy(args[1],
//                    Integer.parseInt(args[3]), BigramWithMeasures.FREQUENCY);
//        } else if ("-c".equalsIgnoreCase(args[2])) {
//            bigrams.printBigramsToFileSortedBy(args[1],
//                    bigrams.getBigramsWithCount().size(),
//                    BigramWithMeasures.CHI_SQUARE);
//        } else if ("-x".equalsIgnoreCase(args[2])) {
        ArrayList <String> bigrams = new ArrayList<String>();
        int n = 100;
        String fileURI = "results/all_frequency";
        getNMostFrequentBigrams(bigrams, fileURI, n);
        Context context = new Context(bigrams);
        context.getInterestingContext("data/Corpus.xml");
        context.getDistancesBetweenBigrams();
        context.printDistances("distances");
//b
        //}
        //System.out.println(getPartOfSpeechCount("data/Corpus.xml", "ss"));
        //System.out.println("Results of the computations you can find in file " + args[1]);
        //System.out.println(getPartOfSpeechCount("data/Corpus.xml", "VB"));
        //Similarity bigrams = new Similarity("results/1000_frequency.txt");
        //System.out.println(bigrams.findNumberOfOccurencesAsTail("af"));
        //bigrams.measureSimilarityOfWords("af", "cell");

//        bigrams.printBigramsToFileSortedBy("temp"/*args[1]*/, bigrams.getBigramsWithCount().size(), BigramWithMeasures.CHI_SQUARE);

        System.out.println("Total execution time: " + 
                (new Date().getTime() - start.getTime()) + "ms");
    }

    static int getPartOfSpeechCount(String fileURI, String partOfSpeech) {
        int count = 0;
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            org.w3c.dom.Document doc = db.parse(new File(fileURI));
            doc.getDocumentElement().normalize();
            NodeList listOfWords = doc.getElementsByTagName("w");
            count = listOfWords.getLength();
//            for (int i = 0; i < listOfWords.getLength(); i++) {
//                if (((Element) listOfWords.item(i)).getAttribute("type").equals(partOfSpeech))
//                count++;
//            }
        }
        catch (Exception e) {
            System.out.println("ERROR");
        }
        return count;
    }

    static void getNMostFrequentBigrams(ArrayList bigrams, String fileURI, int n) {
        try {
            BufferedReader in = new BufferedReader(new FileReader(fileURI));
            String line;
            int index = 0;
            while ((line = in.readLine()) != null) {
                bigrams.add(line.split(" ")[0]);
                index++;
                if (index > n) { break; }
            }
            in.close();
        } catch (Exception e) { System.out.println("no such file"); }
    }
}
