import collocations.Bigrams;
import collocations.BigramWithMeasures;
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
        bigrams.loadBigramsFromFile("data/a01.xml");
        bigrams.printBigramsToFileSortedBy("chi_square.txt", 100,
                BigramWithMeasures.FREQUENCY);

        puts(new Date().getTime() - start.getTime());
    }

    public static void puts(Object toPrint) {
        System.out.println(toPrint);
    }
}
