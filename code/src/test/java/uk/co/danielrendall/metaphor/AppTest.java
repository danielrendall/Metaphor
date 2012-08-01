package uk.co.danielrendall.metaphor;


import uk.co.danielrendall.metaphor.records.MTEF;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

import static junit.framework.Assert.assertEquals;

/**
 * Unit test for simple App.
 */
public class AppTest {

    @Test
    public void testParseQuadratic() throws ParseException, IOException {
        InputStream is = AppTest.class.getResourceAsStream("/ole/quadratic.bin");
        MTEF mtef = App.parse(is);
        assertEquals("Windows", mtef.getGeneratingPlatform());
    }
}
