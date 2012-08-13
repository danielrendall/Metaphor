package uk.co.danielrendall.metaphor.parsers;

import uk.co.danielrendall.metaphor.ParseException;
import uk.co.danielrendall.metaphor.Parser;
import uk.co.danielrendall.metaphor.ParserRegistry;
import uk.co.danielrendall.metaphor.records.SIZE;

import java.io.PushbackInputStream;

/**
 * @author Daniel Rendall
 */
public class SIZEParser extends Parser<SIZE> {

    @Override
    protected SIZE doParse(PushbackInputStream in) throws ParseException {
        throw new UnsupportedOperationException("Implement me!");  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    protected int getInitialByte() {
        return ParserRegistry.SIZE;
    }
}
