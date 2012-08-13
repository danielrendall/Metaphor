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

import uk.co.danielrendall.metaphor.ParseException;
import uk.co.danielrendall.metaphor.Parser;
import uk.co.danielrendall.metaphor.ParserRegistry;
import uk.co.danielrendall.metaphor.records.RULER;

import java.io.PushbackInputStream;

/**
 * @author Daniel Rendall
 */
public class RULERParser extends Parser<RULER> {

    @Override
    protected RULER doParse(PushbackInputStream in) throws ParseException {
        // I assume this is a byte
        int numberOfTabStops = readByte(in);
        RULER.TabStop[] tabStops = new RULER.TabStop[numberOfTabStops];
        for (int i=0; i< numberOfTabStops; i++) {
            int type = readByte(in);
            int offset = readSimple16BitInteger(in);
            tabStops[i] = new RULER.TabStop(type, offset);
        }
        return new RULER(tabStops);
    }

    @Override
    protected int getInitialByte() {
        return ParserRegistry.RULER;
    }
}
