/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

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
 *
 * @author dj
 */
public class Bigrams {
    public static final String X_PATH_SENTENCE = "s";
    public static final String X_PATH_WORD = "w";
    public static final String NOUN = "NN";
    public static final String VERB = "VB";
    public static final String ADJECTIVE = "JJ";
    public static final String ATTRIBUTE_NAME = "type";

    private HashMap bigramsWithCount;
    private HashMap headsOfBigramsWithCount;
    private HashMap tailsOfBigramsWithCount;
    private HashMap bigramsWithChiSquareValue;

    /**
     * Constructor which initializes hashmaps used in computations of bigrams.
     *
     * @param size
     */
    public Bigrams(int size) {
        bigramsWithCount = new HashMap(size);
        headsOfBigramsWithCount = new HashMap(size);
        tailsOfBigramsWithCount = new HashMap(size);
        bigramsWithChiSquareValue = new HashMap(size);
    }


    public void loadBigramsFromFile(String fileURI) {
        try {
            File input = new File(fileURI);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(input);
            doc.getDocumentElement().normalize();

            NodeList listOfSentences = doc.getElementsByTagName(X_PATH_SENTENCE);
            for (int i = 0; i < listOfSentences.getLength(); i++) {
                Element sentence = (Element)listOfSentences.item(i);

                NodeList listOfPartsOfSentence = sentence.getChildNodes();

                ArrayList subSentence = new ArrayList();
                for (int j = 0; j < listOfPartsOfSentence.getLength(); j++) {
                    
                    if (!"w".equals(listOfPartsOfSentence.item(j).getNodeName())) {
                        if ("c".equals(listOfPartsOfSentence.item(j).getNodeName())) {
                            parseSubSentence(subSentence);
                            subSentence = new ArrayList();
                        }
                        continue;
                    }
                    Element word = (Element)listOfPartsOfSentence.item(j);
                    if (NOUN.equals(word.getAttribute(ATTRIBUTE_NAME)) ||
                            VERB.equals(word.getAttribute(ATTRIBUTE_NAME)) ||
                            ADJECTIVE.equals(word.getAttribute(ATTRIBUTE_NAME))) {
                        subSentence.add(word);
                    }
                }
                parseSubSentence(subSentence);
            }
        }
        catch (Exception e) {
            System.out.println("ERROR");
        }
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
                if (NOUN.equals(((Element)subSentence.get(j)).getAttribute("type"))) {
                    head.append(((Element)subSentence.get(i)).getTextContent().toLowerCase())
                            .append("/")
                            .append(((Element)subSentence.get(i)).getAttribute("type"));
                    tail.append(((Element)subSentence.get(j)).getTextContent().toLowerCase())
                            .append("/")
                            .append(((Element)subSentence.get(j)).getAttribute("type"));
                    bigram.append(head).append("_").append(tail);
                    handleHashMap(bigramsWithCount, bigram.toString());
                    handleHashMap(headsOfBigramsWithCount, head.toString());
                    handleHashMap(tailsOfBigramsWithCount, head.toString());
                }
                bigram = new StringBuilder();
                head = new StringBuilder();
                tail = new StringBuilder();
            }
        }
    }

    public HashMap getBigramsWithCount() {
        return this.bigramsWithCount;
    }
    public HashMap getBigramsWithChiSquareValue() {
        return this.bigramsWithChiSquareValue;
    }
    public HashMap getHeadsOfBigramsWithCount() {
        return this.headsOfBigramsWithCount;
    }
    public HashMap getTailsOfBigramsWithCount() {
        return this.tailsOfBigramsWithCount;
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
     */
    public void printMostFrequentBigrams(int howMany) {
        EntryOfArray[] bigrams = sortBigramsByCount();
        for(int i = 0; i < howMany; i++) {
            System.out.println(bigrams[i].key + " " + bigrams[i].value);
        }
    }

    /**
     * Order bigrams by frequency and print specified amount of most frequent
     * bigrams to specified file
     * 
     * @param fileURI
     * @param howMany
     */
    public void printToFileMostFrequentBigrams(String fileURI, int howMany) {
        FileOutputStream fos;
        DataOutputStream dos;
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(fileURI));

            EntryOfArray[] bigrams = sortBigramsByCount();
            for(int i = 0; i < howMany; i++) {
                out.write(bigrams[i].key + " " + bigrams[i].value + "\n");
            }
            out.close();

        } catch (IOException e) {
        }
    }

    /**
     * Converts hashmap of bigrams to array where member of array is object of
     * class EntryOfArray, then sorts created array and returns sorted array
     *
     * @return sorted array of bigrams
     */
    public EntryOfArray[] sortBigramsByCount() {
        EntryOfArray[] bigramsSortedByCount = new EntryOfArray
                [this.getBigramsWithCount().size()];
        Set<Map.Entry<String, Integer>> set = this.bigramsWithCount.entrySet();
        int i = 0;
        for (Map.Entry<String, Integer> me : set) {
            bigramsSortedByCount[i] = new EntryOfArray(me.getKey(),
                    me.getValue());
            i++;
        }
        quickSort(bigramsSortedByCount, 0, bigramsSortedByCount.length-1);
        return bigramsSortedByCount;
    }

    /**
     * Method which does one iteration of quicksort
     *
     * @param arr
     * @param left
     * @param right
     * @return
     */
    int partition(EntryOfArray arr[], int left, int right){
        int i = left;
        int j = right;
        EntryOfArray tmp;
        int pivot = arr[(left + right) / 2].value;
        while (i <= j) {
            while (arr[i].value > pivot)
                i++;
            while (arr[j].value < pivot)
                j--;
            if (i <= j) {
                tmp = arr[i];
                arr[i] = arr[j];
                arr[j] = tmp;
                i++;
                j--;
            }
        }
        return i;
    }

    /**
     * Method to start do quicksorting recursively
     *
     * @param arr
     * @param left
     * @param right
     */
    void quickSort(EntryOfArray arr[], int left, int right) {
          int index = partition(arr, left, right);
          if (left < index - 1)
                quickSort(arr, left, index - 1);
          if (index < right)
                quickSort(arr, index, right);
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
        FileOutputStream fos;
        DataOutputStream dos;
        Set<Map.Entry<String, Integer>> set = hashMap.entrySet();
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(fileURI));
            for (Map.Entry<String, Integer> me : set) {
                out.write(me.getKey() + " ");
                out.write(me.getValue().toString() + "\n");
            }
            out.close();

        } catch (IOException e) {
        }
    }
}
