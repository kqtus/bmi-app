package com.example.marek.bmi;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }
    /*
    @Test
    public void validation_isBmiCorrect() throws Exception {
        ValidatedBmi bmi = new ValidatedBmi(60, 100);
        double bmi_val = bmi.getValidatedBmi();
        assertEquals(bmi_val, 60 / 100 * 100, 0.001);
    }

    @Test(expected)
    public void validation_isValidationCorrect() throws Exception {
        ValidatedBmi bmi = new ValidatedBmi(-32, -322);
        double bmi_val = bmi.getValidatedBmi();
    }
    */
}