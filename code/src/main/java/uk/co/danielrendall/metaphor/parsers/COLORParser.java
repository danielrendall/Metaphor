package uk.co.danielrendall.metaphor.parsers;

import uk.co.danielrendall.metaphor.ParseException;
import uk.co.danielrendall.metaphor.Parser;
import uk.co.danielrendall.metaphor.ParserRegistry;
import uk.co.danielrendall.metaphor.records.COLOR;

import java.io.PushbackInputStream;

/**
 * @author Daniel Rendall
 */
public class COLORParser extends Parser<COLOR> {

    @Override
    protected COLOR doParse(PushbackInputStream in) throws ParseException {
        throw new UnsupportedOperationException("Implement me!");  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    protected int getInitialByte() {
        return ParserRegistry.COLOR;
    }
}
