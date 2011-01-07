package collocations;

import java.util.HashMap;
import java.util.Set;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import java.io.*;
import java.util.Map;

/**
 * This class is used to compute information about bigrams.
 * @author dj
 */
public class Bigrams {
    public static final String X_PATH_SENTENCE = "s";
    public static final String X_PATH_WORD = "w";
    public static final String NOUN = "NN";
    public static final String VERB = "VB";
    public static final String ADJECTIVE = "JJ";
    public static final String ATTRIBUTE_NAME = "type";
    public static final String WORD = "w";
    public static final String PUNCTUATION = "c";

    private HashMap bigramsWithCount;
    private HashMap headsOfBigramsWithCount;
    private HashMap tailsOfBigramsWithCount;
    private int totalNumberOfBigrams;

    /**
     * Constructor which initializes hashmaps used in computations of bigrams.
     *
     * @param size
     */
    public Bigrams(int size) {
        bigramsWithCount = new HashMap(size);
        headsOfBigramsWithCount = new HashMap(size);
        tailsOfBigramsWithCount = new HashMap(size);
    }
    
    public HashMap getBigramsWithCount() {
        return this.bigramsWithCount;
    }
    public HashMap getHeadsOfBigramsWithCount() {
        return this.headsOfBigramsWithCount;
    }
    public HashMap getTailsOfBigramsWithCount() {
        return this.tailsOfBigramsWithCount;
    }
    public int getHeadCount(String bigram) {
        String head = bigram.split("_")[0];
        return getCount(headsOfBigramsWithCount, head);
    }
    public int getTailCount(String bigram) {
        String tail = bigram.split("_")[1];
        return getCount(headsOfBigramsWithCount, tail);
    }
    /**
     * From the specified xml file reads all the bigrams.
     *
     * @param fileURI
     */
    public void loadBigramsFromFile(String fileURI) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new File(fileURI));
            doc.getDocumentElement().normalize();
            NodeList listOfSentences = doc.getElementsByTagName(X_PATH_SENTENCE);
            parseListOfSentences(listOfSentences);
            totalNumberOfBigrams = this.bigramsWithCount.size();
        }
        catch (Exception e) {
            System.out.println("ERROR");
        }
    }
    
    /**
     * Method goes through list of sentences and calls method parseSentence for
     * each sentence
     * 
     * @param listOfSentences
     */
    public void parseListOfSentences(NodeList listOfSentences ) {
        for (int i = 0; i < listOfSentences.getLength(); i++) {
            Element sentence = (Element)listOfSentences.item(i);
            parseSentence(sentence);
        }
    }

    /**
     * Goes through nodes of sentence of checks if the node is of type of
     * interest
     *
     * @param sentence
     */
    public void parseSentence(Element sentence) {
        NodeList listOfPartsOfSentence = sentence.getChildNodes();

        ArrayList subSentence = new ArrayList();
        for (int j = 0; j < listOfPartsOfSentence.getLength(); j++) {

            if (!WORD.equals(listOfPartsOfSentence.item(j).getNodeName())) {
                if (PUNCTUATION.equals(listOfPartsOfSentence.item(j).
                        getNodeName())) {
                    parseSubSentence(subSentence);
                    subSentence = new ArrayList();
                }
                continue;
            }
            Element word = (Element)listOfPartsOfSentence.item(j);
            if (isTypeInteresting(word)) {
                subSentence.add(word);
            }
        }
        parseSubSentence(subSentence);
    }

    /**
     * Checks if the word is of type of interest.
     *
     * @param word Element of xml whose tad is named w
     * @return true if word is of type of interest, false otherwise
     */
    public boolean isTypeInteresting(Element word) {
        if(NOUN.equals(word.getAttribute(ATTRIBUTE_NAME)) ||
                            VERB.equals(word.getAttribute(ATTRIBUTE_NAME)) ||
                            ADJECTIVE.equals(word.getAttribute(ATTRIBUTE_NAME)))
        { return true; }
        return false;
    }

    /**
     * Put parts of subsentence to the structures of collocations class
     *
     * @param subSentence
     */
    public void parseSubSentence(ArrayList subSentence) {
        StringBuilder bigram = new StringBuilder();
        StringBuilder head = new StringBuilder();
        StringBuilder tail = new StringBuilder();
        for (int i = 0; i < subSentence.size() - 1; i++) {
            for (int j = i + 1; j < subSentence.size(); j++) {
                if (NOUN.equals(((Element)subSentence.get(j)).
                        getAttribute(ATTRIBUTE_NAME))) {
                    head.append(((Element)subSentence.get(i)).getTextContent()
                            .toLowerCase())
                            .append("/")
                            .append(((Element)subSentence.get(i))
                            .getAttribute(ATTRIBUTE_NAME));
                    tail.append(((Element)subSentence.get(j)).getTextContent()
                            .toLowerCase())
                            .append("/")
                            .append(((Element)subSentence.get(j))
                            .getAttribute(ATTRIBUTE_NAME));
                    bigram.append(head).append("_").append(tail);
                    handleHashMap(bigramsWithCount, bigram.toString());
                    handleHashMap(headsOfBigramsWithCount, head.toString());
                    handleHashMap(tailsOfBigramsWithCount, tail.toString());
                }
                bigram = new StringBuilder();
                head = new StringBuilder();
                tail = new StringBuilder();
            }
        }
    }

    /**
     * For the specified hash which value is count of the occurences of key
     * method returns value of key, if there were no such key then returns 0.
     *
     * @param hashMap
     * @param key
     * @return count of key
     */
    public int getCount(HashMap hashMap, String key) {
        if( hashMap.containsKey(key)) {
            return (Integer)hashMap.get(key);
        }
        else {
             return 0;
        }
    }

    /**
     * Gets chi square value of the bigram
     *
     * @param bigram
     * @return
     */
    public float countChiSquare(String bigram) {
        return (getHeadCount(bigram) * getTailCount(bigram)) /
                new Float(this.totalNumberOfBigrams);
    }

    /**
     * To handle means to check if there are such key then increase value by 1
     * and if there were no such key then put this key to hashMap with value 1.
     *
     * @param hashMap
     * @param key
     */
    public void handleHashMap(HashMap hashMap, String key) {
        if( hashMap.containsKey(key)) {
             hashMap.put(key, (Integer) hashMap.get(key) + 1);
        }
        else {
             hashMap.put(key, 1);
        }
    }

    /**
     * Order bigrams by frequency and print specified amount of most frequent
     * bigrams to console
     *
     * @param howMany
     * @param sortBy
     */
    public void printBigramsSortedBy(int howMany, String sortBy) {
        BigramWithMeasures[] bigrams = sortBigramsBy(sortBy);
        for(int i = 0; i < howMany; i++) {
            System.out.println(bigrams[i].getBigram() + " " +
                    bigrams[i].getValue(sortBy));
        }
    }

    /**
     * This method sorts bigrams by the measure of the bigram specified by
     * parameter sortBy and then writes sorted bigrams to file
     *
     * @param fileURI
     * @param howMany
     * @param sortBy
     */
    public void printBigramsToFileSortedBy(String fileURI, int howMany,
            String sortBy) {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(fileURI));
            BigramWithMeasures[] bigrams = sortBigramsBy(sortBy);
            for(int i = 0; i < howMany; i++) {
                out.write(bigrams[i].getBigram() + " " +
                        bigrams[i].getValue(sortBy) + "\n");
            }
            out.close();

        } catch (IOException e) {
        }
    }

    /**
     * Converts hashmap of bigrams to array where member of array is object of
     * class BigramWithMeasures, then sorts created array and returns sorted
     * array
     *
     * @return sorted array of bigrams
     */
    public BigramWithMeasures[] sortBigramsBy(String sortBy) {
        BigramWithMeasures[] sortedBigrams = new BigramWithMeasures
                [this.getBigramsWithCount().size()];
        Set<Map.Entry<String, Integer>> set = this.bigramsWithCount.entrySet();
        int i = 0;
        if (BigramWithMeasures.FREQUENCY.equals(sortBy)) {
            for (Map.Entry<String, Integer> me : set) {
                sortedBigrams[i] = new BigramWithMeasures(me.getKey(),
                        me.getValue());
                i++;
            }
        } else if (BigramWithMeasures.CHI_SQUARE.equals(sortBy)) {
            for (Map.Entry<String, Integer> me : set) {
                sortedBigrams[i] = new BigramWithMeasures(me.getKey(),
                        countChiSquare(me.getKey()));
                i++;
            }
        }
        Ordering.quickSort(sortedBigrams, 0, sortedBigrams.length-1, sortBy);
        return sortedBigrams;
    }

    public static void puts(Object arg) {
        System.out.println(arg);
    }

    /**
     * Method to print hashmap to specified file
     *
     * @param hashMap
     * @param fileURI
     */
    public void printHashMap(HashMap hashMap, String fileURI) {
        Set<Map.Entry<String, Integer>> set = hashMap.entrySet();
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(fileURI));
            for (Map.Entry<String, Integer> me : set) {
                out.write(me.getKey() + " " + me.getValue().toString() + "\n");
            }
            out.close();
        } catch (IOException e) {}
    }
}