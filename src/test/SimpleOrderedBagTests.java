package test;

import contracts.SimpleOrderedBag;
import dataStructures.SimpleSortedList;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class SimpleOrderedBagTests {

    private SimpleOrderedBag<String> names;
    private static final String[] TEST_STRING_NAMES =  new String[] {"Pesho", "Georgi", "Balkan"};
    private static final String TEST_STRING_PESHO = "Pesho";

    @Test
    public void testEmptyCtor() {
        this.names = new SimpleSortedList<String>(String.class);
        Assert.assertEquals(16, this.names.capacity());
        Assert.assertEquals(0, this.names.size());

    }

    @Test
    public void testCtorWithInitialCapacity(){
        this.names = new SimpleSortedList<String>(String.class, 20);
        Assert.assertEquals(20, this.names.capacity());
        Assert.assertEquals(0, this.names.size());
    }

    @Test
    public void testCtorWithInitialComparer(){
        this.names = new SimpleSortedList<String>(String.class, String.CASE_INSENSITIVE_ORDER);
        Assert.assertEquals(16, this.names.capacity());
        Assert.assertEquals(0, this.names.size());
    }


    @Test
    public void testCtorWithAllParams(){
        this.names = new SimpleSortedList<String>(String.class, String.CASE_INSENSITIVE_ORDER, 30);
        Assert.assertEquals(30, this.names.capacity());
        Assert.assertEquals(0, this.names.size());
    }

    @Before
    public void setUp() {
        this.names = new SimpleSortedList<String>(String.class);
    }

    @Test
    public void testAddIncreasesSize() {
        this.names.add(TEST_STRING_PESHO);
        Assert.assertEquals(1, this.names.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddNullThrowsException() {
        this.names.add(null);
    }

    @Test
    public void testAddUnsortedDataIsHeldSorted() {
        for (String testStringName : TEST_STRING_NAMES) {
            this.names.add(testStringName);
        }
        String[] sortedNamesToTest = new String[3];
        int index = 0;
        for (String name : names) {
            sortedNamesToTest[index++] = name;
        }

        Assert.assertEquals(TEST_STRING_NAMES[2], sortedNamesToTest[0]);
        Assert.assertEquals(TEST_STRING_NAMES[1], sortedNamesToTest[1]);
        Assert.assertEquals(TEST_STRING_NAMES[0], sortedNamesToTest[2]);

    }

    @Test
    public void testAddingMoreThanInitialCapacity() {
        for (int i = 0; i < 17; i++) {
            this.names.add(TEST_STRING_PESHO);
        }

        Assert.assertEquals(17, this.names.size());
        Assert.assertTrue(this.names.capacity() != 16);
    }

    @Test
    public void testAddingAllFromCollectionIncreasesSize() {
        this.names.addAll(Arrays.asList(TEST_STRING_NAMES));

        Assert.assertEquals(TEST_STRING_NAMES.length, this.names.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddingAllFromNullThrowsException() {
        this.names.addAll(null);
    }

    @Test
    public void testAddAllKeepsSorted() {
        this.names.addAll(Arrays.asList(TEST_STRING_NAMES));
        String[] sortedNamesToTest = new String[3];
        int index = 0;
        for (String name : names) {
            sortedNamesToTest[index++] = name;
        }

        Assert.assertEquals(TEST_STRING_NAMES[2], sortedNamesToTest[0]);
        Assert.assertEquals(TEST_STRING_NAMES[1], sortedNamesToTest[1]);
        Assert.assertEquals(TEST_STRING_NAMES[0], sortedNamesToTest[2]);
    }

    @Test
    public void testRemoveValidElementDecreasesSize() {
        this.names.addAll(Arrays.asList(TEST_STRING_NAMES));
        this.names.remove(TEST_STRING_PESHO);
        Assert.assertEquals(TEST_STRING_NAMES.length - 1, this.names.size());
    }

    @Test
    public void testRemoveValidElementRemovesSelectedOne() {
        this.names.addAll(Arrays.asList(TEST_STRING_NAMES));
        this.names.remove(TEST_STRING_PESHO);
        Assert.assertEquals(false, this.names.remove(TEST_STRING_PESHO));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemovingNullThrowsException() {
        this.names.remove(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testJoinWithNul() {
        this.names.joinWith(null);
    }

    @Test
    public void testJoinWorksFine() {
        this.names.addAll(Arrays.asList(TEST_STRING_NAMES));
        ArrayList<String> arrList = new ArrayList<>(Arrays.asList(TEST_STRING_NAMES));
        arrList.sort(String::compareTo);
        String answer = arrList.toString().replace("[", "").replace("]", "");
        Assert.assertEquals(answer, this.names.joinWith(", "));
    }
}
