/*
 * Copyright 2012 Daniel Rendall
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.co.danielrendall.metaphor.parsers;

import com.google.common.collect.Lists;
import uk.co.danielrendall.metaphor.ParseException;
import uk.co.danielrendall.metaphor.Parser;
import uk.co.danielrendall.metaphor.ParserRegistry;
import uk.co.danielrendall.metaphor.Record;
import uk.co.danielrendall.metaphor.records.PILE;
import uk.co.danielrendall.metaphor.records.RULER;

import java.io.PushbackInputStream;
import java.util.List;

/**
 * @author Daniel Rendall
 */
public class PILEParser extends Parser<PILE> {

    @Override
    protected PILE doParse(PushbackInputStream in) throws ParseException {
        Record.Options options = readOptions(in);
        Record.Nudge nudge = options.nudge() ? readNudge(in) : Record.NO_NUDGE;
        int hAlign = readByte(in); // docs don't explicitly say this is a byte, but seems likely
        int vAlign = readByte(in);
        RULER ruler = options.lp_ruler() ? (RULER)ParserRegistry.get(ParserRegistry.RULER).parse(in) : RULER.NO_RULER;
        List<Record> records = Lists.newArrayList();
        // There should only be LINE records in this list - is it worth enforcing this somewhere?
        readRecordsToEnd(in, records);
        return new PILE(options, nudge, hAlign, vAlign, ruler, records);
    }

    @Override
    protected int getInitialByte() {
        return ParserRegistry.PILE;
    }
}
