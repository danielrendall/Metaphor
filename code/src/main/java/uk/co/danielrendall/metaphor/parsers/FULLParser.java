package uk.co.danielrendall.metaphor.parsers;

import uk.co.danielrendall.metaphor.ParseException;
import uk.co.danielrendall.metaphor.Parser;
import uk.co.danielrendall.metaphor.ParserRegistry;
import uk.co.danielrendall.metaphor.records.FULL;

import java.io.PushbackInputStream;

/**
 * @author Daniel Rendall
 */
public class FULLParser extends Parser<FULL> {

    @Override
    protected FULL doParse(PushbackInputStream in) throws ParseException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    protected int getInitialByte() {
        return ParserRegistry.FULL;
    }
}
