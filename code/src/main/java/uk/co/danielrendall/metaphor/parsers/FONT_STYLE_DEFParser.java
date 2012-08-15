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
import uk.co.danielrendall.metaphor.records.FONT_STYLE_DEF;

import java.io.PushbackInputStream;

/**
 * @author Daniel Rendall
 */
public class FONT_STYLE_DEFParser extends Parser<FONT_STYLE_DEF> {

    @Override
    protected FONT_STYLE_DEF doParse(PushbackInputStream in) throws ParseException {
        int fontDefIndex = readUnsignedInt(in);
        int charStyle = readByte(in);
        return new FONT_STYLE_DEF(fontDefIndex, charStyle);
    }

    @Override
    protected int getInitialByte() {
        return ParserRegistry.FONT_STYLE_DEF;
    }
}
