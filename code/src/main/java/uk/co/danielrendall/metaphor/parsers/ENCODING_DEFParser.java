package uk.co.danielrendall.metaphor.parsers;

import uk.co.danielrendall.metaphor.ParseException;
import uk.co.danielrendall.metaphor.Parser;
import uk.co.danielrendall.metaphor.ParserRegistry;
import uk.co.danielrendall.metaphor.records.ENCODING_DEF;

import java.io.PushbackInputStream;

/**
 * @author Daniel Rendall
 */
public class ENCODING_DEFParser extends Parser<ENCODING_DEF> {

    @Override
    protected ENCODING_DEF doParse(PushbackInputStream in) throws ParseException {
        String encodingName = readNullTerminatedString(in);
        return new ENCODING_DEF(encodingName);
    }

    @Override
    protected int getInitialByte() {
        return ParserRegistry.ENCODING_DEF;
    }
}
