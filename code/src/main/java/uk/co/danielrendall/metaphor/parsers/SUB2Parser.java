package uk.co.danielrendall.metaphor.parsers;

import uk.co.danielrendall.metaphor.ParseException;
import uk.co.danielrendall.metaphor.Parser;
import uk.co.danielrendall.metaphor.ParserRegistry;
import uk.co.danielrendall.metaphor.records.SUB2;

import java.io.PushbackInputStream;

/**
 * @author Daniel Rendall
 */
public class SUB2Parser extends Parser<SUB2> {

    @Override
    protected SUB2 doParse(PushbackInputStream in) throws ParseException {
        return SUB2.Instance;
    }

    @Override
    protected int getInitialByte() {
        return ParserRegistry.SUB2;
    }
}
