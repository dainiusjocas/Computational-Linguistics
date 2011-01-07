package collocations;

/**
 * This class should serve as a container for of one element which is bigram with
 * it's frequency.
 *
 * @author dj
 */
public class BigramWithMeasures {
    public static final String FREQUENCY = "frequency";
    public static final String CHI_SQUARE = "chiSquare";
    private int frequency;
    private float chiSquare;
    private String bigram;

    /**
     * Constructor for all kind of measures
     *
     * @param bigram
     * @param frequency
     * @param chiSquare
     */
    public BigramWithMeasures(String bigram, int frequency, float chiSquare) {
        this.bigram = bigram;
        this.frequency = frequency;
        this.chiSquare = chiSquare;
    }

    /**
     * Constructor for situation when important is frequency
     *
     * @param bigram
     * @param frequency
     */
    public BigramWithMeasures(String bigram, int frequency) {
        this.bigram = bigram;
        this.frequency = frequency;
    }

    /**
     * Constructor for situation when important is chi-square value
     * @param bigram
     * @param chiSquare
     */
    public BigramWithMeasures(String bigram, float chiSquare) {
        this.bigram = bigram;
        this.chiSquare = chiSquare;
    }

    /**
     * Bigrams can be sorted either by frequency or by chi-square value, so
     * there is a need dinamically get the needed value by the name of the field
     * by which array should be sorted.
     *
     * @param nameOfField
     * @return
     */
    public float getValue(String nameOfField) {
        if (BigramWithMeasures.FREQUENCY.equals(nameOfField)) {
            return this.frequency;
        }
        if (BigramWithMeasures.CHI_SQUARE.equals(nameOfField)) {
            return this.chiSquare;
        }
        return 0;
    }

    /**
     * @return value o field named bigram
     */
    public String getBigram() {
        return this.bigram;
    }

}
