package com.example.assignment2;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.google.android.material.datepicker.CalendarConstraints;

import junit.framework.TestCase;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

public class RegisterMovieTest {

    @Test
    public void testRatingIsValid() {
        RegisterMovie movieRating = new RegisterMovie();
        Assert.assertTrue(movieRating.ratingIsValid(5));
    }

    @Test
    public void testYearIsValid() {
        RegisterMovie movieYear = new RegisterMovie();
        Assert.assertTrue(movieYear.yearIsValid(2000));
    }


    @Rule
    public TestRule rule = new InstantTaskExecutorRule();
}