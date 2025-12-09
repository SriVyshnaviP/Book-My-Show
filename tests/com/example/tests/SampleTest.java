package com.example.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

public class SampleTest {

    @Test
    public void sampleTest() {
        System.out.println("Running sample TestNG test...");
        Assert.assertEquals(1 + 1, 2);
    }
}
