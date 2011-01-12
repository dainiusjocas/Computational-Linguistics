/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package collocations;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
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
    public HashMap bigramsWithContext;

    public Context(ArrayList<String> bigrams) {
        this.bigramsWithContext = new HashMap();
        for (String bigram : bigrams) {
            this.bigramsWithContext.put(bigram, new HashMap());
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
    public void loadBigramsFromFile(String fileURI) {
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
    public void parseSentence(Element sentence, NodeList listOfSentences, int sentenceIndex) {
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
}
