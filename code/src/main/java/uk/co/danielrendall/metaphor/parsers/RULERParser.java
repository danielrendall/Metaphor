package uk.co.danielrendall.metaphor.parsers;

import uk.co.danielrendall.metaphor.ParseException;
import uk.co.danielrendall.metaphor.Parser;
import uk.co.danielrendall.metaphor.ParserRegistry;
import uk.co.danielrendall.metaphor.records.RULER;

import java.io.PushbackInputStream;

/**
 * @author Daniel Rendall
 */
public class RULERParser extends Parser<RULER> {

    @Override
    protected RULER doParse(PushbackInputStream in) throws ParseException {
        // I assume this is a byte
        int numberOfTabStops = readByte(in);
        RULER.TabStop[] tabStops = new RULER.TabStop[numberOfTabStops];
        for (int i=0; i< numberOfTabStops; i++) {
            int type = readByte(in);
            int offset = readSimple16BitInteger(in);
            tabStops[i] = new RULER.TabStop(type, offset);
        }
        return new RULER(tabStops);
    }

    @Override
    protected int getInitialByte() {
        return ParserRegistry.RULER;
    }
}
