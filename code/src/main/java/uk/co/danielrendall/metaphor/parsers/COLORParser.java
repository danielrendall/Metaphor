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
        int colorDefIndex = readUnsignedInt(in);
        return new COLOR(colorDefIndex);
    }

    @Override
    protected int getInitialByte() {
        return ParserRegistry.COLOR;
    }
}
