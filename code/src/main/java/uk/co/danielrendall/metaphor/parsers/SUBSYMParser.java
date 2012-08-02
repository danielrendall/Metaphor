package uk.co.danielrendall.metaphor.parsers;

import uk.co.danielrendall.metaphor.ParseException;
import uk.co.danielrendall.metaphor.Parser;
import uk.co.danielrendall.metaphor.ParserRegistry;
import uk.co.danielrendall.metaphor.records.SUBSYM;

import java.io.PushbackInputStream;

/**
 * @author Daniel Rendall
 */
public class SUBSYMParser extends Parser<SUBSYM> {

    @Override
    protected SUBSYM doParse(PushbackInputStream in) throws ParseException {
        return SUBSYM.Instance;
    }

    @Override
    protected int getInitialByte() {
        return ParserRegistry.SUBSYM;
    }
}
