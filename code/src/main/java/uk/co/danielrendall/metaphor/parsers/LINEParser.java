package uk.co.danielrendall.metaphor.parsers;

import com.google.common.collect.Lists;
import uk.co.danielrendall.metaphor.*;
import uk.co.danielrendall.metaphor.records.END;
import uk.co.danielrendall.metaphor.records.LINE;
import uk.co.danielrendall.metaphor.records.RULER;

import java.io.PushbackInputStream;
import java.util.List;

/**
 * @author Daniel Rendall
 */
public class LINEParser extends Parser<LINE> {

    @Override
    protected LINE doParse(PushbackInputStream in) throws ParseException {
        Record.Options options = readOptions(in);
        Record.Nudge nudge = options.nudge() ? readNudge(in) : Record.NO_NUDGE;
        int lineSpacing = options.line_lspace() ? readSimple16BitInteger(in) : -1;
        RULER ruler = options.lp_ruler() ? (RULER)ParserRegistry.get(ParserRegistry.RULER).parse(in) : RULER.NO_RULER;
        List<Record> records = Lists.newArrayList();
        if (!options.line_null()) {
            readRecordsToEnd(in, records);
        }
        return new LINE(options, nudge, lineSpacing, ruler, records);
    }

    @Override
    protected int getInitialByte() {
        return ParserRegistry.LINE;
    }
}
