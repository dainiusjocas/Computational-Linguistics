/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package collocations;

import java.util.ArrayList;
import java.util.HashMap;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author dj
 */
public class ContextTest {

    public ContextTest() {
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
     * Test of getInterestingContext method, of class Context.
     */
    @Test
    public void testGetInterestingContext() {
        System.out.println("getInterestingContext");
        String fileURI = "";
        Context instance = null;
        instance.getInterestingContext(fileURI);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }


    /**
     * Test of parseListOfSentences method, of class Context.
     */
    @Test
    public void testParseListOfSentences() {
        System.out.println("parseListOfSentences");
        NodeList listOfSentences = null;
        Context instance = null;
        instance.parseListOfSentences(listOfSentences);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isTypeInteresting method, of class Context.
     */
    @Test
    public void testIsTypeInteresting() {
        System.out.println("isTypeInteresting");
        Element word = null;
        Context instance = null;
        boolean expResult = false;
        boolean result = instance.isTypeInteresting(word);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of parseSubSentence method, of class Context.
     */
    @Test
    public void testParseSubSentence() {
        System.out.println("parseSubSentence");
        ArrayList subSentence = null;
        NodeList listOfSentences = null;
        int indexOfSentence = 0;
        Context instance = null;
        instance.parseSubSentence(subSentence, listOfSentences, indexOfSentence);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getContext method, of class Context.
     */
    @Test
    public void testGetContext() {
        System.out.println("getContext");
        String bigram = "";
        NodeList listOfSentences = null;
        int indexOfSentence = 0;
        Context instance = null;
        instance.getContext(bigram, listOfSentences, indexOfSentence);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of handleHashMap method, of class Context.
     */
    @Test
    public void testHandleHashMap() {
        ArrayList<String> test = new ArrayList();
        test.add("moo");
        HashMap hashMap = new HashMap();
        String key = "moo";
        Context instance = new Context(test);
        instance.handleHashMap(hashMap, key);
        int expValue = 1;
        int result = (Integer) hashMap.get(key);
        assertEquals(expValue, result);
    }

    /**
     * Test of measureDistance method, of class Context.
     */
    @Test
    public void testMeasureDistance1() {
        HashMap <String, Integer> context1 = new HashMap();
        context1.put("moo", 1);
        context1.put("foo", 2);
        HashMap <String, Integer> context2 = new HashMap();
        context2.put("moo", 3);
        context2.put("too", 5);
        ArrayList al = new ArrayList();
        al.add("moo");
        Context instance = new Context(al);
        double expValue = 2.5;
        double result = instance.measureDistance(context1, context2);
        assertEquals(expValue, result, 0.0001);
    }

    /**
     * Test of measureDistance method, of class Context.
     */
    @Test
    public void testMeasureDistance2() {
        HashMap <String, Integer> context1 = new HashMap();
        context1.put("moo", 1);
        context1.put("foo", 2);
        HashMap <String, Integer> context2 = new HashMap();
        context2.put("moo", 3);
        context2.put("foo", 5);
        ArrayList al = new ArrayList();
        al.add("moo");
        Context instance = new Context(al);
        double expValue = 1.25;
        double result = instance.measureDistance(context1, context2);
        assertEquals(expValue, result, 0.0001);
    }

    /**
     * Test of measureDistance method, of class Context.
     */
    @Test
    public void testMeasureDistance3() {
        HashMap <String, Integer> context1 = new HashMap();
        context1.put("moo", 1);
        context1.put("foo", 2);
        HashMap <String, Integer> context2 = new HashMap();
        context2.put("qoo", 3);
        context2.put("woo", 5);
        ArrayList al = new ArrayList();
        al.add("moo");
        Context instance = new Context(al);
        double expValue = 4;
        double result = instance.measureDistance(context1, context2);
        assertEquals(expValue, result, 0.0001);
    }

    /**
     * Test of measureDistance method, of class Context.
     */
    @Test
    public void testGetDistancesBetweenBigrams() {
        ArrayList<String> bigramsList = new ArrayList();
        String bigram1 = "bigram1";
        String bigram2 = "bigram2";
        bigramsList.add(bigram1);
        bigramsList.add(bigram2);
        Context instance = new Context(bigramsList);
        instance.handleHashMap(instance.bigramsWithContext.get(bigram1), "moo");
        instance.handleHashMap(instance.bigramsWithContext.get(bigram1), "foo");
        instance.handleHashMap(instance.bigramsWithContext.get(bigram1), "foo");
        instance.handleHashMap(instance.bigramsWithContext.get(bigram2), "moo");
        instance.handleHashMap(instance.bigramsWithContext.get(bigram2), "moo");
        instance.handleHashMap(instance.bigramsWithContext.get(bigram2), "moo");
        instance.handleHashMap(instance.bigramsWithContext.get(bigram2), "foo");
        instance.handleHashMap(instance.bigramsWithContext.get(bigram2), "foo");
        instance.handleHashMap(instance.bigramsWithContext.get(bigram2), "foo");
        instance.handleHashMap(instance.bigramsWithContext.get(bigram2), "foo");
        instance.handleHashMap(instance.bigramsWithContext.get(bigram2), "foo");
        instance.getDistancesBetweenBigrams();
        int expectedSize = 1;
        int sizeOfHashOfDistances = instance.distancesBetweenBigrams.size();
        assertEquals(expectedSize, sizeOfHashOfDistances);
    }

    /**
     * Test of measureDistance method, of class Context.
     */
    @Test
    public void testGetDistancesBetweenBigrams1() {
        ArrayList<String> bigramsList = new ArrayList();
        String bigram1 = "bigram1";
        String bigram2 = "bigram2";
        String pair = bigram1 + Context.SEPARATOR + bigram2;
        bigramsList.add(bigram1);
        bigramsList.add(bigram2);
        Context instance = new Context(bigramsList);
        instance.handleHashMap(instance.bigramsWithContext.get(bigram1), "moo");
        instance.handleHashMap(instance.bigramsWithContext.get(bigram1), "foo");
        instance.handleHashMap(instance.bigramsWithContext.get(bigram1), "foo");
        instance.handleHashMap(instance.bigramsWithContext.get(bigram2), "moo");
        instance.handleHashMap(instance.bigramsWithContext.get(bigram2), "moo");
        instance.handleHashMap(instance.bigramsWithContext.get(bigram2), "moo");
        instance.handleHashMap(instance.bigramsWithContext.get(bigram2), "foo");
        instance.handleHashMap(instance.bigramsWithContext.get(bigram2), "foo");
        instance.handleHashMap(instance.bigramsWithContext.get(bigram2), "foo");
        instance.handleHashMap(instance.bigramsWithContext.get(bigram2), "foo");
        instance.handleHashMap(instance.bigramsWithContext.get(bigram2), "foo");
        instance.getDistancesBetweenBigrams();
        int expectedSize = 1;
        int sizeOfHashOfDistances = instance.distancesBetweenBigrams.size();
        assertNotNull(instance.distancesBetweenBigrams.get(pair));
    }

    /**
     * Test of measureDistance method, of class Context.
     */
    @Test
    public void testGetDistanceBetweenTwoBigrams1() {
        ArrayList<String> bigramsList = new ArrayList();
        String bigram1 = "bigram1";
        String bigram2 = "bigram2";
        String noSuchBigram = "bla";
        String pair = bigram1 + Context.SEPARATOR + bigram2;
        bigramsList.add(bigram1);
        bigramsList.add(bigram2);
        Context instance = new Context(bigramsList);
        instance.handleHashMap(instance.bigramsWithContext.get(bigram1), "moo");
        instance.handleHashMap(instance.bigramsWithContext.get(bigram1), "woo");
        instance.handleHashMap(instance.bigramsWithContext.get(bigram1), "woo");
        instance.handleHashMap(instance.bigramsWithContext.get(bigram2), "moo");
        instance.handleHashMap(instance.bigramsWithContext.get(bigram2), "moo");
        instance.handleHashMap(instance.bigramsWithContext.get(bigram2), "moo");
        instance.handleHashMap(instance.bigramsWithContext.get(bigram2), "woo");
        instance.handleHashMap(instance.bigramsWithContext.get(bigram2), "woo");
        instance.handleHashMap(instance.bigramsWithContext.get(bigram2), "woo");
        instance.handleHashMap(instance.bigramsWithContext.get(bigram2), "woo");
        instance.handleHashMap(instance.bigramsWithContext.get(bigram2), "woo");
        instance.getDistancesBetweenBigrams();
        double expectedDistance = 1.25;
        double distance = instance.getDistanceBetweenTwoBigrams(bigram2, bigram1);
        assertEquals(expectedDistance, distance, 0.001);
    }

    /**
     * Test of measureDistance method, of class Context.
     */
    @Test
    public void testGetDistanceBetweenTwoBigrams2() {
        ArrayList<String> bigramsList = new ArrayList();
        String bigram1 = "bigram1";
        String bigram2 = "bigram2";
        String pair = bigram1 + Context.SEPARATOR + bigram2;
        bigramsList.add(bigram1);
        bigramsList.add(bigram2);
        Context instance = new Context(bigramsList);
        instance.handleHashMap(instance.bigramsWithContext.get(bigram1), "moo");
        instance.handleHashMap(instance.bigramsWithContext.get(bigram1), "woo");
        instance.handleHashMap(instance.bigramsWithContext.get(bigram1), "woo");
        instance.handleHashMap(instance.bigramsWithContext.get(bigram2), "moo");
        instance.handleHashMap(instance.bigramsWithContext.get(bigram2), "moo");
        instance.handleHashMap(instance.bigramsWithContext.get(bigram2), "moo");
        instance.handleHashMap(instance.bigramsWithContext.get(bigram2), "woo");
        instance.handleHashMap(instance.bigramsWithContext.get(bigram2), "woo");
        instance.handleHashMap(instance.bigramsWithContext.get(bigram2), "woo");
        instance.handleHashMap(instance.bigramsWithContext.get(bigram2), "woo");
        instance.handleHashMap(instance.bigramsWithContext.get(bigram2), "woo");
        instance.getDistancesBetweenBigrams();
        double expectedDistance = 1.25;
        double distance = instance.getDistanceBetweenTwoBigrams(bigram1, bigram2);
        assertEquals(expectedDistance, distance, 0.001);
    }

    /**
     * Test of measureDistance method, of class Context.
     */
    @Test
    public void testGetDistanceBetweenTwoBigrams3() {
        ArrayList<String> bigramsList = new ArrayList();
        String bigram1 = "bigram1";
        String bigram2 = "bigram2";
        String noSuchBigram = "bla";
        String pair = bigram1 + Context.SEPARATOR + bigram2;
        bigramsList.add(bigram1);
        bigramsList.add(bigram2);
        Context instance = new Context(bigramsList);
        instance.handleHashMap(instance.bigramsWithContext.get(bigram1), "moo");
        instance.handleHashMap(instance.bigramsWithContext.get(bigram1), "woo");
        instance.handleHashMap(instance.bigramsWithContext.get(bigram1), "woo");
        instance.handleHashMap(instance.bigramsWithContext.get(bigram2), "moo");
        instance.handleHashMap(instance.bigramsWithContext.get(bigram2), "moo");
        instance.handleHashMap(instance.bigramsWithContext.get(bigram2), "moo");
        instance.handleHashMap(instance.bigramsWithContext.get(bigram2), "woo");
        instance.handleHashMap(instance.bigramsWithContext.get(bigram2), "woo");
        instance.handleHashMap(instance.bigramsWithContext.get(bigram2), "woo");
        instance.handleHashMap(instance.bigramsWithContext.get(bigram2), "woo");
        instance.handleHashMap(instance.bigramsWithContext.get(bigram2), "woo");
        instance.getDistancesBetweenBigrams();
        double expectedDistance = Double.NaN;
        double distance = instance.getDistanceBetweenTwoBigrams(bigram1, noSuchBigram);
        assertEquals(expectedDistance, distance, 0.001);
    }

    /**
     * Test of measureDistance method, of class Context.
     */
    @Test
    public void testGetDistanceBetweenTwoBigrams4() {
        ArrayList<String> bigramsList = new ArrayList();
        String bigram1 = "bigram1";
        String bigram2 = "bigram2";
        String noSuchBigram = "bla";
        String pair = bigram1 + Context.SEPARATOR + bigram2;
        bigramsList.add(bigram1);
        bigramsList.add(bigram2);
        Context instance = new Context(bigramsList);
        instance.handleHashMap(instance.bigramsWithContext.get(bigram1), "moo");
        instance.handleHashMap(instance.bigramsWithContext.get(bigram1), "woo");
        instance.handleHashMap(instance.bigramsWithContext.get(bigram1), "woo");
        instance.handleHashMap(instance.bigramsWithContext.get(bigram2), "moo");
        instance.handleHashMap(instance.bigramsWithContext.get(bigram2), "moo");
        instance.handleHashMap(instance.bigramsWithContext.get(bigram2), "moo");
        instance.handleHashMap(instance.bigramsWithContext.get(bigram2), "woo");
        instance.handleHashMap(instance.bigramsWithContext.get(bigram2), "woo");
        instance.handleHashMap(instance.bigramsWithContext.get(bigram2), "woo");
        instance.handleHashMap(instance.bigramsWithContext.get(bigram2), "woo");
        instance.handleHashMap(instance.bigramsWithContext.get(bigram2), "woo");
        instance.getDistancesBetweenBigrams();
        double expectedDistance = Double.NaN;
        double distance = instance.getDistanceBetweenTwoBigrams(noSuchBigram, bigram2);
        assertEquals(expectedDistance, distance, 0.001);
    }

    /**
     * Test of measureDistance method, of class Context.
     */
    @Test
    public void testGetDistanceBetweenTwoBigrams5() {
        ArrayList<String> bigramsList = new ArrayList();
        String bigram1 = "bigram1";
        String bigram2 = "bigram2";
        String noSuchBigram = "bla";
        String pair = bigram1 + Context.SEPARATOR + bigram2;
        bigramsList.add(bigram1);
        bigramsList.add(bigram2);
        Context instance = new Context(bigramsList);
        instance.handleHashMap(instance.bigramsWithContext.get(bigram1), "moo");
        instance.handleHashMap(instance.bigramsWithContext.get(bigram1), "woo");
        instance.handleHashMap(instance.bigramsWithContext.get(bigram1), "woo");
        instance.handleHashMap(instance.bigramsWithContext.get(bigram2), "moo");
        instance.handleHashMap(instance.bigramsWithContext.get(bigram2), "moo");
        instance.handleHashMap(instance.bigramsWithContext.get(bigram2), "moo");
        instance.handleHashMap(instance.bigramsWithContext.get(bigram2), "woo");
        instance.handleHashMap(instance.bigramsWithContext.get(bigram2), "woo");
        instance.handleHashMap(instance.bigramsWithContext.get(bigram2), "woo");
        instance.handleHashMap(instance.bigramsWithContext.get(bigram2), "woo");
        instance.handleHashMap(instance.bigramsWithContext.get(bigram2), "woo");
        instance.getDistancesBetweenBigrams();
        double expectedDistance = Double.NaN;
        double distance = instance.getDistanceBetweenTwoBigrams(noSuchBigram, noSuchBigram);
        assertEquals(expectedDistance, distance, 0.001);
    }

    /**
     * Test of measureDistance method, of class Context.
     */
    @Test
    public void getMinDistanceBetweenBigrams1() {
        ArrayList<String> bigramsList = new ArrayList();
        String bigram1 = "bigram1";
        String bigram2 = "bigram2";
        String bigram3 = "bigram3";
        String noSuchBigram = "bla";
        String pair = bigram1 + Context.SEPARATOR + bigram2;
        bigramsList.add(bigram1);
        bigramsList.add(bigram2);
        bigramsList.add(bigram3);
        Context instance = new Context(bigramsList);
        instance.handleHashMap(instance.bigramsWithContext.get(bigram1), "moo");
        instance.handleHashMap(instance.bigramsWithContext.get(bigram1), "woo");
        instance.handleHashMap(instance.bigramsWithContext.get(bigram1), "woo");
        instance.handleHashMap(instance.bigramsWithContext.get(bigram3), "moo");
        instance.handleHashMap(instance.bigramsWithContext.get(bigram3), "woo");
        instance.handleHashMap(instance.bigramsWithContext.get(bigram3), "woo");
        instance.handleHashMap(instance.bigramsWithContext.get(bigram2), "moo");
        instance.handleHashMap(instance.bigramsWithContext.get(bigram2), "moo");
        instance.handleHashMap(instance.bigramsWithContext.get(bigram2), "moo");
        instance.handleHashMap(instance.bigramsWithContext.get(bigram2), "woo");
        instance.handleHashMap(instance.bigramsWithContext.get(bigram2), "woo");
        instance.handleHashMap(instance.bigramsWithContext.get(bigram2), "woo");
        instance.handleHashMap(instance.bigramsWithContext.get(bigram2), "woo");
        instance.handleHashMap(instance.bigramsWithContext.get(bigram2), "woo");
        instance.getDistancesBetweenBigrams();
        double expectedDistance = 0;
        double minDistance = instance.getMinDistanceBetweenBigrams();
        assertEquals(expectedDistance, minDistance, 0.001);
    }

}