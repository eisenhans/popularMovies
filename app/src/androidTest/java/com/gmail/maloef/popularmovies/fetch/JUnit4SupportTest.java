package com.gmail.maloef.popularmovies.fetch;


import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Markus on 25.11.2015.
 */
@RunWith(AndroidJUnit4.class)
public class JUnit4SupportTest {

    @Test
    public void testTrue() {
        assertTrue(true);
    }

    @Test(expected = AssertionError.class)
    public void testFalse() {
        assertFalse(true);
    }
}
