package collocations;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests for class BigramForSimilarity
 * @author dj
 */
public class BigramForSimilarityTest {

    public BigramForSimilarityTest() {
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
     * Test of getBigram method, of class BigramForSimilarity.
     */
    @Test
    public void testGetBigram() {
        BigramForSimilarity instance = new BigramForSimilarity("moo/VB_foo/NN");
        String expResult = "moo/VB_foo/NN";
        String result = instance.getBigram();
        assertEquals(expResult, result);
    }

    /**
     * Test of getHeadInfo method, of class BigramForSimilarity.
     */
    @Test
    public void testGetHeadInfo() {
        BigramForSimilarity instance = new BigramForSimilarity("moo/VB_foo/NN");
        float expResult = 12.34F;
        instance.setHeadInfo(expResult);
        float result = instance.getHeadInfo();
        assertEquals(expResult, result, 0.0);

    }

    /**
     * Test of getTailInfo method, of class BigramForSimilarity.
     */
    @Test
    public void testGetTailInfo() {
        BigramForSimilarity instance = new BigramForSimilarity("moo/VB_foo/NN");
        float expResult = 12.34F;
        instance.setTailInfo(expResult);
        float result = instance.getTailInfo();
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of getFirstMatch method, of class BigramForSimilarity.
     */
    @Test
    public void testGetFirstMatch() {
        BigramForSimilarity instance = new BigramForSimilarity("moo/VB_foo/NN");
        int expResult = 1;
        instance.setFirstMatch(expResult);
        int result = instance.getFirstMatch();
        assertEquals(expResult, result);
    }

    /**
     * Test of getSecondMatch method, of class BigramForSimilarity.
     */
    @Test
    public void testGetSecondMatch() {
        BigramForSimilarity instance = new BigramForSimilarity("moo/VB_foo/NN");
        int expResult = 2;
        instance.setSecondMatch(expResult);
        int result = instance.getSecondMatch();
        assertEquals(expResult, result);
    }

    /**
     * Test of getHead method, of class BigramForSimilarity.
     */
    @Test
    public void testGetHead() {
        BigramForSimilarity instance = new BigramForSimilarity("moo/VB_foo/NN");
        String expResult = "moo";
        String result = instance.getHead();
        assertEquals(expResult, result);
    }

    /**
     * Test of getHeadPartOfSpeech method, of class BigramForSimilarity.
     */
    @Test
    public void testGetHeadPartOfSpeech() {
        BigramForSimilarity instance = new BigramForSimilarity("moo/VB_foo/NN");
        String expResult = "VB";
        String result = instance.getHeadPartOfSpeech();
        assertEquals(expResult, result);
    }

    /**
     * Test of getTail method, of class BigramForSimilarity.
     */
    @Test
    public void testGetTail() {
        BigramForSimilarity instance = new BigramForSimilarity("moo/VB_foo/NN");
        String expResult = "foo";
        String result = instance.getTail();
        assertEquals(expResult, result);
    }

    /**
     * Test of getPartOfSpeechOfTail method, of class BigramForSimilarity.
     */
    @Test
    public void testGetPartOfSpeechOfTail() {
        BigramForSimilarity instance = new BigramForSimilarity("moo/VB_foo/NN");
        String expResult = "NN";
        String result = instance.getPartOfSpeechOfTail();
        assertEquals(expResult, result);
    }

    /**
     * Test of setHeadInfo method, of class BigramForSimilarity.
     */
    @Test
    public void testSetHeadInfo() {
        float newHeadInfo = 12.34F;
        BigramForSimilarity instance = new BigramForSimilarity("moo/VB_foo/NN");
        instance.setHeadInfo(newHeadInfo);
        float result = instance.getHeadInfo();
        assertEquals(newHeadInfo, result, 0.001);
    }

    /**
     * Test of setTailInfo method, of class BigramForSimilarity.
     */
    @Test
    public void testSetTailInfo() {
        float newTailInfo = 12.34F;
        BigramForSimilarity instance = new BigramForSimilarity("moo/VB_foo/NN");
        instance.setTailInfo(newTailInfo);
        float result = instance.getTailInfo();
        assertEquals(newTailInfo, result, 0.001);
    }

    /**
     * Test of setFirstMatch method, of class BigramForSimilarity.
     */
    @Test
    public void testSetFirstMatch() {
        int newFirstMatch = 3;
        BigramForSimilarity instance = new BigramForSimilarity("moo/VB_foo/NN");
        instance.setFirstMatch(newFirstMatch);
        int result = instance.getFirstMatch();
        assertEquals(newFirstMatch, result);
    }

    /**
     * Test of setSecondMatch method, of class BigramForSimilarity.
     */
    @Test
    public void testSetSecondMatch() {
        int newSecondMatch = 3;
        BigramForSimilarity instance = new BigramForSimilarity("moo/VB_foo/NN");
        instance.setSecondMatch(newSecondMatch);
        int result  = instance.getSecondMatch();
        assertEquals(newSecondMatch, result);
    }

    /**
     * Test of setMatch method, of class BigramForSimilarity.
     */
    @Test
    public void testSetMatch() {
        int type = 2;
        int nr = 1;
        BigramForSimilarity instance = new BigramForSimilarity("moo/VB_foo/NN");
        instance.setMatch(type, nr);
        int result = instance.getFirstMatch();
        assertEquals(type, result);
    }

    /**
     * Test of setMatchType method, of class BigramForSimilarity.
     */
    @Test
    public void testSetMatchType1() {
        String word = "moo";
        int nr = 1;
        BigramForSimilarity instance = new BigramForSimilarity("moo/VB_foo/NN");
        instance.setMatchType(word, nr);
        int result = instance.getFirstMatch();
        assertEquals(BigramForSimilarity.HEAD, result);
    }

    /**
     * Test of setMatchType method, of class BigramForSimilarity.
     */
    @Test
    public void testSetMatchType2() {
        String word = "moo";
        int nr = 2;
        BigramForSimilarity instance = new BigramForSimilarity("moo/VB_foo/NN");
        instance.setMatchType(word, nr);
        int result = instance.getSecondMatch();
        assertEquals(BigramForSimilarity.HEAD, result);
    }

    /**
     * Test of setMatchType method, of class BigramForSimilarity.
     */
    @Test
    public void testSetMatchType3() {
        String word = "woo";
        int nr = 2;
        BigramForSimilarity instance = new BigramForSimilarity("moo/VB_foo/NN");
        instance.setMatchType(word, nr);
        int result = instance.getSecondMatch();
        assertEquals(BigramForSimilarity.NONE, result);
    }

    /**
     * Test of setMatchType method, of class BigramForSimilarity.
     */
    @Test
    public void testSetMatchType4() {
        String word = "woo";
        int nr = 2;
        BigramForSimilarity instance = new BigramForSimilarity("woo/VB_woo/NN");
        instance.setMatchType(word, nr);
        int result = instance.getSecondMatch();
        assertEquals(BigramForSimilarity.HEAD_AND_TAIL, result);
    }

    /**
     * Test of setMatchType method, of class BigramForSimilarity.
     */
    @Test
    public void testSetMatchType5() {
        String word = "woo";
        int nr = 2;
        BigramForSimilarity instance = new BigramForSimilarity("moo/VB_woo/NN");
        instance.setMatchType(word, nr);
        int result = instance.getSecondMatch();
        assertEquals(BigramForSimilarity.TAIL, result);
    }

    /**
     * Test of isHeadOfBigram method, of class BigramForSimilarity.
     */
    @Test
    public void testIsHeadOfBigram1() {
        String word = "moo";
        BigramForSimilarity instance = new BigramForSimilarity("moo/VB_foo/NN");
        boolean expResult = true;
        boolean result = instance.isHeadOfBigram(word);
        assertEquals(expResult, result);
    }

    /**
     * Test of isHeadOfBigram method, of class BigramForSimilarity.
     */
    @Test
    public void testIsHeadOfBigram2() {
        String word = "foo";
        BigramForSimilarity instance = new BigramForSimilarity("moo/VB_foo/NN");
        boolean expResult = false;
        boolean result = instance.isHeadOfBigram(word);
        assertEquals(expResult, result);
    }

    /**
     * Test of isTailOfBigram method, of class BigramForSimilarity.
     */
    @Test
    public void testIsTailOfBigram1() {
        String word = "foo";
        BigramForSimilarity instance = new BigramForSimilarity("moo/VB_foo/NN");
        boolean expResult = true;
        boolean result = instance.isTailOfBigram(word);
        assertEquals(expResult, result);
    }

    /**
     * Test of isTailOfBigram method, of class BigramForSimilarity.
     */
    @Test
    public void testIsTailOfBigram2() {
        String word = "moo";
        BigramForSimilarity instance = new BigramForSimilarity("moo/VB_foo/NN");
        boolean expResult = false;
        boolean result = instance.isTailOfBigram(word);
        assertEquals(expResult, result);
    }

}