package uk.co.danielrendall.metaphor.parsers;

import uk.co.danielrendall.metaphor.ParseException;
import uk.co.danielrendall.metaphor.Parser;
import uk.co.danielrendall.metaphor.ParserRegistry;
import uk.co.danielrendall.metaphor.records.END;

import java.io.PushbackInputStream;

/**
 * @author Daniel Rendall
 */
public class ENDParser extends Parser<END> {

    @Override
    protected END doParse(PushbackInputStream in) throws ParseException {
        return new END();
    }

    @Override
    protected int getInitialByte() {
        return ParserRegistry.END;
    }
}
