package collocations;

/**
 * Class for information storage of single bigram for similarity measurement
 *
 * @author dj
 */
public class BigramForSimilarity {
    public static final int HEAD_AND_TAIL = 3;
    public static final int TAIL = 2;
    public static final int HEAD = 1;
    public static final int NONE = 0;
    
    private String bigram;
    private float headInfo;
    private float tailInfo;
    private int firstMatch; // 0 - none; 1 - head; 2 - tail; 3 - both
    private int secondMatch;

    public BigramForSimilarity(String bigram) {
        this.bigram = bigram;
    }

    public String getBigram() {
        return this.bigram;
    }
    public float getHeadInfo() {
        return this.headInfo;
    }
    public float getTailInfo() {
        return this.tailInfo;
    }
    public int getFirstMatch() {
        return this.firstMatch;
    }
    public int getSecondMatch() {
        return this.secondMatch;
    }
    public String getHead() {
        return this.bigram.split("/")[0];
    }
    public String getHeadPartOfSpeech() {
        return this.bigram.split("_")[0].split("/")[1];
    }
    public String getTail() {
        return this.bigram.split("_")[1].split("/")[0];
    }
    public String getPartOfSpeechOfTail() {
        return this.bigram.split("_")[1].split("/")[1];
    }
    public void setHeadInfo(float newHeadInfo) {
        this.headInfo = newHeadInfo;
    }
    public void setTailInfo(float newTailInfo) {
        this.tailInfo = newTailInfo;
    }
    public void setFirstMatch(int newFirstMatch) {
        this.firstMatch = newFirstMatch;
    }
    public void setSecondMatch(int newSecondMatch) {
        this.secondMatch = newSecondMatch;
    }

    public void setMatch(int type, int nr) {
        if (1 == nr) {
            this.setFirstMatch(type);
        } else {
            this.setSecondMatch(type);
        }
    }

    public void setMatchType(String word, int nr) {
        if ((isHeadOfBigram(word)) && (isTailOfBigram(word))) {
            this.setMatch(HEAD_AND_TAIL, nr);
        } else if (isTailOfBigram(word)) {
            this.setMatch(TAIL, nr);
        } else if (isHeadOfBigram(word)) {
            this.setMatch(HEAD, nr);
        } else {
            this.setMatch(NONE, nr);
        }
    }

    public boolean isHeadOfBigram(String word) {
        if (this.getHead().equals(word)) {
            return true;
        }
        return false;
    }

    public boolean isTailOfBigram(String word) {
        if(this.getTail().equals(word)) {
            return true;
        }
        return false;
    }

}
