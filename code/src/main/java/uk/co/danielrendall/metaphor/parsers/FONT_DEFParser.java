package uk.co.danielrendall.metaphor.parsers;

import uk.co.danielrendall.metaphor.ParseException;
import uk.co.danielrendall.metaphor.Parser;
import uk.co.danielrendall.metaphor.ParserRegistry;
import uk.co.danielrendall.metaphor.records.FONT_DEF;

import java.io.PushbackInputStream;

/**
 * @author Daniel Rendall
 */
public class FONT_DEFParser extends Parser<FONT_DEF> {

    @Override
    protected FONT_DEF doParse(PushbackInputStream in) throws ParseException {
        int encDefIndex = readUnsignedInt(in);
        String fontName = readNullTerminatedString(in);
        return new FONT_DEF(encDefIndex, fontName);
    }

    @Override
    protected int getInitialByte() {
        return ParserRegistry.FONT_DEF;
    }
}
