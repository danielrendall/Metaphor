package uk.co.danielrendall.metaphor.parsers;

import uk.co.danielrendall.metaphor.ParseException;
import uk.co.danielrendall.metaphor.Parser;
import uk.co.danielrendall.metaphor.ParserRegistry;
import uk.co.danielrendall.metaphor.records.SYM;

import java.io.PushbackInputStream;

/**
 * @author Daniel Rendall
 */
public class SYMParser extends Parser<SYM> {

    @Override
    protected SYM doParse(PushbackInputStream in) throws ParseException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    protected int getInitialByte() {
        return ParserRegistry.SYM;
    }
}
