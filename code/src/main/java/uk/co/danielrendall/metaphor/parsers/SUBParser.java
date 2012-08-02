package uk.co.danielrendall.metaphor.parsers;

import uk.co.danielrendall.metaphor.ParseException;
import uk.co.danielrendall.metaphor.Parser;
import uk.co.danielrendall.metaphor.ParserRegistry;
import uk.co.danielrendall.metaphor.records.SUB;

import java.io.PushbackInputStream;

/**
 * @author Daniel Rendall
 */
public class SUBParser extends Parser<SUB> {

    @Override
    protected SUB doParse(PushbackInputStream in) throws ParseException {
        return SUB.Instance;
    }

    @Override
    protected int getInitialByte() {
        return ParserRegistry.SUB;
    }
}
