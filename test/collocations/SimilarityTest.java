/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package collocations;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author dj
 */
public class SimilarityTest {
    private Similarity similarity;

    public SimilarityTest() {
        this.similarity = new Similarity("testdata/onebigram");
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getInfoOfWord method, of class Similarity.
     */
    @Test
    public void testGetInfoOfWord() {
        System.out.println("getInfoOfWord");
        String word = "";
        String type = "";
        boolean headOrTail = false;
        Similarity instance = null;
        instance.getInfoOfWord(word, headOrTail, type);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getHeadInfo method, of class Similarity.
     */
    @Test
    public void testGetHeadInfo1() {
        String head = "moo";
        String type = "NN";
        Similarity instance = new Similarity("testdata/2heads1tail");
        double result = instance.getHeadInfo(head, type);
        assertEquals(0, result, 0.001);
    }

    /**
     * Test of getHeadInfo method, of class Similarity.
     */
    @Test
    public void testGetHeadInfo2() {
        String head = "moo";
        String type = "VB";
        Similarity instance = new Similarity("testdata/5bigrams");
        double result = instance.getHeadInfo(head, type);
        assertEquals((-1)*Math.log(3/4.0), result, 0.001);
    }

    /**
     * Test of getTailInfo method, of class Similarity.
     */
    @Test
    public void testGetTailInfo() {
        String tail = "foo";
        String type = "NN";
        Similarity instance = new Similarity("testdata/5bigrams");
        instance.getTailInfo(tail, type);
        double result = instance.getTailInfo(tail, type);
        assertEquals((-1) * Math.log(1/2.0), result, 0.001);
    }

    /**
     * Test of findNumberOfOccurencesAsHead method, of class Similarity.
     */
    @Test
    public void testFindNumberOfOccurencesAsHead1() {
        String word = "foo";
        Similarity instance = similarity;
        int expResult = 0;
        int result = instance.findNumberOfOccurencesAsHead(word);
        assertEquals(expResult, result);
    }

    /**
     * Test of findNumberOfOccurencesAsHead method, of class Similarity.
     */
    @Test
    public void testFindNumberOfOccurencesAsHead2() {
        String word = "moo";
        Similarity instance = similarity;
        int expResult = 1;
        int result = instance.findNumberOfOccurencesAsHead(word);
        assertEquals(expResult, result);
    }

    /**
     * Test of findNumberOfOccurencesAsHead method, of class Similarity.
     */
    @Test
    public void testFindNumberOfOccurencesAsHead3() {
        String word = "moo";
        Similarity instance = new Similarity("testdata/2heads1tail");
        int expResult = 2;
        int result = instance.findNumberOfOccurencesAsHead(word);
        assertEquals(expResult, result);
    }

    /**
     * Test of findNumberOfOccurencesAsTail method, of class Similarity.
     */
    @Test
    public void testFindNumberOfOccurencesAsTail1() {
        String word = "foo";
        Similarity instance = similarity;
        int expResult = 1;
        int result = instance.findNumberOfOccurencesAsTail(word);
        assertEquals(expResult, result);
    }

    /**
     * Test of findNumberOfOccurencesAsTail method, of class Similarity.
     */
    @Test
    public void testFindNumberOfOccurencesAsTail2() {
        String word = "woo";
        Similarity instance = new Similarity("testdata/onebigram");
        int expResult = 0;
        int result = instance.findNumberOfOccurencesAsTail(word);
        assertEquals(expResult, result);
    }

    /**
     * Test of findNumberOfOccurencesAsTail method, of class Similarity.
     */
    @Test
    public void testFindNumberOfOccurencesAsTail3() {
        String word = "moo";
        Similarity instance = new Similarity("testdata/2heads1tail");
        int expResult = 1;
        int result = instance.findNumberOfOccurencesAsTail(word);
        assertEquals(expResult, result);
    }
}