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
import uk.co.danielrendall.metaphor.records.SIZE;

import java.io.PushbackInputStream;

/**
 * @author Daniel Rendall
 */
public class SIZEParser extends Parser<SIZE> {

    @Override
    protected SIZE doParse(PushbackInputStream in) throws ParseException {
        int firstByte = readByte(in);
        int lSize = 0;
        int dSize = 0;
        SIZE.SizeType sizeType;
        switch (firstByte) {
            case 101: // lsize < 0
                // this is supposed to be -(point size) which makes me think that 16 bit integers should be signed...?
                sizeType = SIZE.SizeType.ExplicitPointSize;
                lSize = readSimple16BitInteger(in);
                break;
            case 100:
                sizeType = SIZE.SizeType.LargeDelta;
                lSize = readByte(in);
                dSize = readSimple16BitInteger(in);
                break;
            default:
                sizeType = SIZE.SizeType.SmallDelta;
                lSize = firstByte;
                dSize = readByte(in) - 128;
                break;
        }
        return new SIZE(sizeType, lSize, dSize);
    }

    @Override
    protected int getInitialByte() {
        return ParserRegistry.SIZE;
    }
}
