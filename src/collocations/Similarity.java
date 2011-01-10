package collocations;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is used for working with prepared bigrams
 * DON'T PAY ATTENTION TO TYPE OF THE WORD
 *
 * @author dj
 */
public class Similarity {
    public static final int NOUNS = 2;//168026;
    public static final int ADJECTIVES = 68728;
    public static final int VERBS = 4;//33985;
    public static final boolean HEAD = true;
    public static final boolean TAIL = false;

    private ArrayList <BigramForSimilarity> listOfBigrams;

    public Similarity(String fileUri) {
        this.listOfBigrams = new ArrayList();
        this.loadBigrams(fileUri);
    }

    private void loadBigrams(String fileURI) {
        try {
            BufferedReader in = new BufferedReader(new FileReader(fileURI));
            String line;
            while ((line = in.readLine()) != null) {
                this.listOfBigrams.add(new BigramForSimilarity(line.split(" ")[0]));
            }
            in.close();
        } catch (Exception e) { System.out.println("no such file"); }
    }

    public void getInfoOfWord(String word, boolean headOrTail, String type) {
        if (headOrTail) {
            getHeadInfo(word, type);
        } else {
            getTailInfo(word, type);
        }
    }
    public int getAmountOfType(String type) {
        if (type.equals("NN")) {
            return Similarity.NOUNS;
        } else if (type.equals("JJ")) {
            return Similarity.ADJECTIVES;
        }
        return Similarity.VERBS;
    }

    public double getHeadInfo(String head, String type) {
        double info = 0;
        int sum = findNumberOfOccurencesAsHead(head);
        info = (-1) * Math.log(sum/new Float(this.getAmountOfType(type)));
        return info;
    }
    public double getTailInfo(String tail, String type) {
        double info = 0;
        int sum = findNumberOfOccurencesAsTail(tail);
        info = (-1) * Math.log(sum/new Float(this.getAmountOfType(type)));
        return info;
    }

    public int findNumberOfOccurencesAsHead(String word) {
        int count = 0;
        for (BigramForSimilarity bigram : this.listOfBigrams) {
            if (bigram.isHeadOfBigram(word)) {
                count++;
            }
        }
        return count;
    }

    public int findNumberOfOccurencesAsTail(String word) {
        int count = 0;
        for (BigramForSimilarity bigram : this.listOfBigrams) {
            if (bigram.isTailOfBigram(word)) {
                count++;
            }
        }
        return count;
    }
}
