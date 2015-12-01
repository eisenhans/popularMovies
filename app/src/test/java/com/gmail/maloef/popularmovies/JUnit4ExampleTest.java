package com.gmail.maloef.popularmovies;


import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class JUnit4ExampleTest {

    @Test
    public void testTrue() {
        assertTrue(true);
    }

    @Test(expected = AssertionError.class)
    public void testFalse() {
        assertFalse(true);
    }
}
