package uk.co.danielrendall.metaphor.parsers;

import com.google.common.collect.Lists;
import uk.co.danielrendall.metaphor.ParseException;
import uk.co.danielrendall.metaphor.Parser;
import uk.co.danielrendall.metaphor.ParserRegistry;
import uk.co.danielrendall.metaphor.Record;
import uk.co.danielrendall.metaphor.records.TMPL;

import java.io.PushbackInputStream;
import java.util.List;

/**
 * @author Daniel Rendall
 */
public class TMPLParser extends Parser<TMPL> {

    @Override
    protected TMPL doParse(PushbackInputStream in) throws ParseException {
        Record.Options options = readOptions(in);
        Record.Nudge nudge = options.nudge() ? readNudge(in) : Record.NO_NUDGE;
        int templateCode = readByte(in);
        int variation = readByte(in);
        if ((variation & 0x80) > 0) {
            // curious but true
            variation = (variation & 0x7F) | (readByte(in) << 8);
        }
        TMPL.Template template = TMPL.get(templateCode);
        int templateOptions = -1;
        if (template.hasOptions()) {
            templateOptions = readByte(in);
        }
        List<Record> records = Lists.newArrayList();
        if (!options.line_null()) {
            readRecordsToEnd(in, records);
        }
        return new TMPL(options, nudge, template, variation, templateOptions, records);
    }

    @Override
    protected int getInitialByte() {
        return ParserRegistry.TMPL;
    }
}
