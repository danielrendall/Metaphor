package uk.co.danielrendall.metaphor.parsers;

import uk.co.danielrendall.metaphor.ParseException;
import uk.co.danielrendall.metaphor.Parser;
import uk.co.danielrendall.metaphor.ParserRegistry;
import uk.co.danielrendall.metaphor.records.PILE;

import java.io.PushbackInputStream;

/**
 * @author Daniel Rendall
 */
public class PILEParser extends Parser<PILE> {

    @Override
    protected PILE doParse(PushbackInputStream in) throws ParseException {
        throw new UnsupportedOperationException("Implement me!");  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    protected int getInitialByte() {
        return ParserRegistry.PILE;
    }
}
