package uk.co.danielrendall.metaphor.parsers;

import uk.co.danielrendall.metaphor.ParseException;
import uk.co.danielrendall.metaphor.Parser;
import uk.co.danielrendall.metaphor.ParserRegistry;
import uk.co.danielrendall.metaphor.records.FONT_STYLE_DEF;

import java.io.PushbackInputStream;

/**
 * @author Daniel Rendall
 */
public class FONT_STYLE_DEFParser extends Parser<FONT_STYLE_DEF> {

    @Override
    protected FONT_STYLE_DEF doParse(PushbackInputStream in) throws ParseException {
        throw new UnsupportedOperationException("Implement me!");  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    protected int getInitialByte() {
        return ParserRegistry.FONT_STYLE_DEF;
    }
}
