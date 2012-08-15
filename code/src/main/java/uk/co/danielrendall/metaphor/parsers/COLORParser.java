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
import uk.co.danielrendall.metaphor.records.COLOR;

import java.io.PushbackInputStream;

/**
 * @author Daniel Rendall
 */
public class COLORParser extends Parser<COLOR> {

    @Override
    protected COLOR doParse(PushbackInputStream in) throws ParseException {
        int colorDefIndex = readUnsignedInt(in);
        return new COLOR(colorDefIndex);
    }

    @Override
    protected int getInitialByte() {
        return ParserRegistry.COLOR;
    }
}
