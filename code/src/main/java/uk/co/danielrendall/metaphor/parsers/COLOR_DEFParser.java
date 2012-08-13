package uk.co.danielrendall.metaphor.parsers;

import com.google.common.collect.Lists;
import uk.co.danielrendall.metaphor.ParseException;
import uk.co.danielrendall.metaphor.Parser;
import uk.co.danielrendall.metaphor.ParserRegistry;
import uk.co.danielrendall.metaphor.Record;
import uk.co.danielrendall.metaphor.records.COLOR_DEF;

import java.io.PushbackInputStream;
import java.util.List;

/**
 * @author Daniel Rendall
 */
public class COLOR_DEFParser extends Parser<COLOR_DEF> {

    @Override
    protected COLOR_DEF doParse(PushbackInputStream in) throws ParseException {
        Record.Options options = readOptions(in);
        // default is RGB (3 colours) unless CMYK
        int listSize = options.color_cmyk() ? 4 : 3;
        List<Integer> colorValues = Lists.newArrayListWithCapacity(listSize);
        for (int i=0; i<listSize; i++) {
            colorValues.set(i, readSimple16BitInteger(in));
        }
        String name = options.color_name() ? readNullTerminatedString(in) : "";
        return new COLOR_DEF(options, colorValues, name);
    }

    @Override
    protected int getInitialByte() {
        return ParserRegistry.COLOR_DEF;
    }
}
