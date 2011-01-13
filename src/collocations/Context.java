/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package collocations;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author dj
 */
public class Context {
    public static final String X_PATH_SENTENCE = "s";
    public static final String X_PATH_WORD = "w";
    public static final String NOUN = "NN";
    public static final String VERB = "VB";
    public static final String ADJECTIVE = "JJ";
    public static final String ATTRIBUTE_NAME = "type";
    public static final String WORD = "w";
    public static final String PUNCTUATION = "c";
    public static final String SEPARATOR = " ";
    public HashMap<String, HashMap> bigramsWithContext;
    public HashMap<String, Double> distancesBetweenBigrams;
    public ArrayList<String> listOfBigrams;

    public Context(ArrayList<String> bigrams) {
        this.listOfBigrams = bigrams;
        this.distancesBetweenBigrams = new HashMap();
        this.bigramsWithContext = new HashMap();
        for (String bigram : bigrams) {
            this.bigramsWithContext.put(bigram, new HashMap<String, Integer>());
        }
    }

    public void getInterestingContext(String fileURI) {
        loadBigramsFromFile(fileURI);
    }

    /**
     * From the specified xml file reads all the bigrams.
     *
     * @param fileURI
     */
    private void loadBigramsFromFile(String fileURI) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new File(fileURI));
            doc.getDocumentElement().normalize();
            NodeList listOfSentences = doc.getElementsByTagName(X_PATH_SENTENCE);
            parseListOfSentences(listOfSentences);
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
            parseSentence(sentence, listOfSentences, i);
        }
    }

    /**
     * Goes through nodes of sentence of checks if the node is of type of
     * interest
     *
     * @param sentence
     */
    private void parseSentence(Element sentence, NodeList listOfSentences, int sentenceIndex) {
        NodeList listOfPartsOfSentence = sentence.getChildNodes();

        ArrayList subSentence = new ArrayList();
        for (int j = 0; j < listOfPartsOfSentence.getLength(); j++) {

            if (!WORD.equals(listOfPartsOfSentence.item(j).getNodeName())) {
                if (PUNCTUATION.equals(listOfPartsOfSentence.item(j).
                        getNodeName())) {
                    parseSubSentence(subSentence, listOfSentences, sentenceIndex);
                    subSentence = new ArrayList();
                }
                continue;
            }
            Element word = (Element)listOfPartsOfSentence.item(j);
            if (isTypeInteresting(word)) {
                subSentence.add(word);
            }
        }
        parseSubSentence(subSentence, listOfSentences, sentenceIndex);
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
    public void parseSubSentence(ArrayList subSentence, NodeList listOfSentences, int indexOfSentence) {
        StringBuilder bigram = new StringBuilder();
        for (int i = 0; i < subSentence.size() - 1; i++) {
            for (int j = i + 1; j < subSentence.size(); j++) {
                if ("NN".equals(((Element)subSentence.get(j)).
                        getAttribute(ATTRIBUTE_NAME))) {
                    bigram.append(((Element)subSentence.get(i)).getTextContent()
                            .toLowerCase())
                            .append("/")
                            .append(((Element)subSentence.get(i))
                            .getAttribute(ATTRIBUTE_NAME)).append("_")
                            .append(((Element)subSentence.get(j)).getTextContent()
                            .toLowerCase())
                            .append("/")
                            .append(((Element)subSentence.get(j))
                            .getAttribute(ATTRIBUTE_NAME));
                    if (this.bigramsWithContext.containsKey(bigram.toString())) {
                        getContext(bigram.toString(), listOfSentences, indexOfSentence);
                    }
                }
                bigram = new StringBuilder();
            }
        }
    }

    public void getContext(String bigram, NodeList listOfSentences, int indexOfSentence) {
        NodeList listOfWords;
        listOfWords = listOfSentences.item(indexOfSentence).getChildNodes();
            for (int j = 0; j < listOfWords.getLength(); j++) {
                if ("w".equals(listOfWords.item(j).getNodeName())) {
                    handleHashMap((HashMap) this.bigramsWithContext.get(bigram), listOfWords.item(j).getTextContent());
                }
            }
        for (int i = 0; i < 5; i ++) {
            if (indexOfSentence + i < listOfSentences.getLength()) {
                listOfWords = listOfSentences.item(indexOfSentence + i).getChildNodes();
                for (int j = 0; j < listOfWords.getLength(); j++) {
                    if ("w".equals(listOfWords.item(j).getNodeName())) {
                        handleHashMap((HashMap) this.bigramsWithContext.get(bigram), listOfWords.item(j).getTextContent());
                    }
                }
            }
            if (0 < indexOfSentence - i) {
                listOfWords = listOfSentences.item(indexOfSentence - i).getChildNodes();
                for (int j = 0; j < listOfWords.getLength(); j++) {
                    if ("w".equals(listOfWords.item(j).getNodeName())) {
                        handleHashMap((HashMap) this.bigramsWithContext.get(bigram), listOfWords.item(j).getTextContent());
                    }
                }
            }
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

    public void getDistancesBetweenBigrams() {
        for (int i = 0; i < this.listOfBigrams.size(); i++) {
            for (int j = i + 1; j < this.listOfBigrams.size(); j++) {
                String pairOfBigrams = concatWithSeparator(this.listOfBigrams.get(i), this.listOfBigrams.get(j));
                HashMap context1 = this.bigramsWithContext.get(this.listOfBigrams.get(i));
                HashMap context2 = this.bigramsWithContext.get(this.listOfBigrams.get(j));
                this.distancesBetweenBigrams.put(pairOfBigrams, measureDistance(context1, context2));
            }
        }
    }

    public double getDistanceBetweenTwoBigrams(String bigram1, String bigram2) {
        String pairOfBigrams = concatWithSeparator(bigram1, bigram2);
        String pairOfBigramsReversed = concatWithSeparator(bigram2, bigram1);
        if ((null != this.distancesBetweenBigrams.get(pairOfBigrams))) {
            return this.distancesBetweenBigrams.get(pairOfBigrams);
        } else if ((null != this.distancesBetweenBigrams.get(pairOfBigramsReversed))) {
            return this.distancesBetweenBigrams.get(pairOfBigramsReversed);
        } else if (bigram1.equals(bigram2) && (this.listOfBigrams.contains(bigram1))) {
            return 0;
        }
        return Double.NaN;
    }

    /**
     * Method to print hashmap to specified file
     *
     * @param hashMap
     * @param fileURI
     */
    public void printDistances(String fileURI) {
        Set<Map.Entry<String, Double>> set = this.distancesBetweenBigrams.entrySet();
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(fileURI));
            for (Map.Entry<String, Double> me : set) {
                out.write(me.getKey() + " " + me.getValue().toString() + "\n");
            }
            out.close();
        } catch (Exception e) { System.out.println("ERROR when writeing to file!!"); }
    }

    public double getMinDistanceBetweenBigrams() {
        double minDistance = Double.MAX_VALUE;
        String minPair = "";
        for (int i = 0; i < this.listOfBigrams.size(); i++) {
            for (int j = i + 1; j < this.listOfBigrams.size(); j++) {
                String pairOfBigrams = concatWithSeparator(this.listOfBigrams.get(i), this.listOfBigrams.get(j));
                double temp = this.distancesBetweenBigrams.get(pairOfBigrams);
                if (minDistance > temp) {
                    minDistance = temp;
                    minPair = pairOfBigrams;
                }
            }
        }
        return minDistance;
    }

    private String concatWithSeparator(String bigram1, String bigram2) {
        return bigram1 + Context.SEPARATOR + bigram2;
    }

    public double measureDistance(HashMap context1, HashMap context2) {
        double distance = 0;
        int[] maxmin = getMaxDifference(context1, context2);
        int normConst = Math.abs(maxmin[0] - maxmin[1]);
        Set <String> words1 = context1.keySet();
        ArrayList <String> words = new ArrayList();
        Set <String> words2 = context2.keySet();
        for (String word : words1) {
            words.add(word);
        }
        for (String word : words2) {
            if (!words.contains(word)) {
                words.add(word);
            }
        }
        for (String word : words) {
            Integer count1 = (Integer) context1.get(word);
            Integer count2 = (Integer) context2.get(word);
            if (count1 == null || null == count2) {
                distance++;
            } else {
                distance += Math.abs(count1 - count2) / new Double(normConst);
            }
        }
        return distance;
    }

    private int[] getMaxDifference(HashMap context1, HashMap context2) {
        Collection <Integer> values1 = context1.values();
        Collection <Integer> values2 = context2.values();
        int max = 0;
        int min = 0;
        for (Integer value : values1) {
            max = min = value;
        }
        for (Integer value : values1){
            if (value > max) {
                max = value;
            }
            if (value < min) {
                min = value;
            }
        }
        for (Integer value : values2){
            if (value > max) {
                max = value;
            }
            if (value < min) {
                min = value;
            }
        }
        int[] a = {max, min};
        return a;
    }
}
