package de.comparus.opensource.longmap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LongMapImplTest {

    private LongMapImpl<String> myMap = new LongMapImpl<>();

    @Before
    public void setUp() {
        myMap.put(0, "January");
        myMap.put(11, "February");
        myMap.put(30, "March");
        myMap.put(40, "April");
    }

    @After
    public void tearDown() {
        myMap.clear();
    }

    @Test
    public void put() {
        myMap.put(4, "April");
        assertNotNull(myMap);
        assertEquals(5, myMap.size());
        assertEquals("April", myMap.get(40));
    }

    @Test
    public void get() {
        assertEquals("February", myMap.get(11));
    }

    @Test
    public void remove() {
        String remove = myMap.remove(30);
        assertNull("March", myMap.get(30));
    }

    @Test
    public void isEmpty() {
        assertFalse(myMap.isEmpty());
    }

    @Test
    public void containsKey() {
        assertTrue(myMap.containsKey(0));
        assertTrue(myMap.containsKey(11));
    }

    @Test
    public void containsValue() {
        assertTrue("January", myMap.containsValue("January"));
    }

    @Test
    public void keys() {
        long[] keys = myMap.keys();
        assertEquals(4, keys.length);
    }

    @Test
    public void values() {
        String[] values = myMap.values();
        assertEquals(4, values.length);
    }

    @Test
    public void size() {
        assertEquals(4, myMap.size());
    }

    @Test
    public void clear() {
        myMap.clear();
        assertTrue(myMap.isEmpty());
    }
}
