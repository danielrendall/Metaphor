package uk.co.danielrendall.metaphor.parsers;

import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.co.danielrendall.metaphor.ParseException;
import uk.co.danielrendall.metaphor.Parser;
import uk.co.danielrendall.metaphor.ParserRegistry;
import uk.co.danielrendall.metaphor.Record;
import uk.co.danielrendall.metaphor.records.CHAR;
import uk.co.danielrendall.metaphor.records.EMBELL;

import java.io.PushbackInputStream;
import java.util.List;

/**
 * @author Daniel Rendall
 * @author Thilo Planz
 */
public class CHARParser extends Parser<CHAR> {

    @Override
    protected CHAR doParse(PushbackInputStream in) throws ParseException {
        Record.Options options = readOptions(in);
        Record.Nudge nudge = options.nudge() ? readNudge(in) : Record.NO_NUDGE;
        int typeFace = readUnsignedInt(in);
        int charCode = -1;
        if (!options.char_enc_no_mtcode()) {
            charCode = readSimple16BitInteger(in);
        } else {
            if (options.char_enc_char_8()) {
                charCode = readByte(in);
            } else if (options.char_enc_char_16()) {
                charCode = readSimple16BitInteger(in);
            } else {
                throw new ParseException("Unable to read char code");
            }
        }
        List<Record> embellishments = Lists.newArrayList();
        if (options.char_embell()) {
            readRecordsToEnd(in, embellishments);
        }

        return new CHAR(options, nudge, typeFace, charCode, embellishments);
    }

    @Override
    protected int getInitialByte() {
        return ParserRegistry.CHAR;
    }
}
