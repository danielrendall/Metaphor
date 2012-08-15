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
