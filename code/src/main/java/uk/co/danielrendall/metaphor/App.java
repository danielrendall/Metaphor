package uk.co.danielrendall.metaphor;

import uk.co.danielrendall.metaphor.parsers.MTEFParser;
import uk.co.danielrendall.metaphor.records.MTEF;

import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;

/**
 * Hello world!
 *
 */
public class App 
{
    public static MTEF parse(InputStream is) throws ParseException, IOException {
        PushbackInputStream pis = new PushbackInputStream(is);
        // hack - throw away 28 byte OLE header
        pis.read(new byte[28]);
        return new MTEFParser().parse(pis);
    }
}
