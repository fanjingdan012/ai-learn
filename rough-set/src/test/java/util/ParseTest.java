package util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;

/**
 * Parse Tester.
 * 
 * @author FJD
 * @since Dec 4, 2017
 *
 */
public class ParseTest {

    /**
     * 
     * Method: parse(String filename, double [] input, double output[])
     * 
     */
    @Test
    public void testParse() throws Exception {
        // TODO: Test goes here...
        // svc.parse(String filename, double [] input, double output[]);
    }

    /**
     * 
     * Method: parseNumber(String filename, double [][] input, double [][] output)
     * 
     */
    @Test
    public void testParseNumber() throws Exception {
        double[][] input = new double[10][25];
        double[][] output = new double[10][10];
        Parse.parseNumber("NumberLearningTest", input, output);
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 25; j++)
                System.out.print(input[i][j] + " ");
            System.out.println("");
        }
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++)
                System.out.print(output[i][j] + " ");
            System.out.println("");
        }
    }

}
