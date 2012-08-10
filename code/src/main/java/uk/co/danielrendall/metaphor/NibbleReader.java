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

package uk.co.danielrendall.metaphor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Daniel Rendall
 */
public class NibbleReader {
    private static final Logger log = LoggerFactory.getLogger(NibbleReader.class);
    private final InputStream in;
    int currentByte = -1;

    public NibbleReader(InputStream in) {
        this.in = in;
    }

    public int nextNibble() throws ParseException {
        try {
            int res = -1;
            if (currentByte == -1) {
                currentByte = in.read();
                if (currentByte == -1) {
                    throw new ParseException("Ran out of nibbles");
                }
                res = (currentByte & 0xf0) >> 4;
            } else {
                res = currentByte & 0xf;
                currentByte = -1;
            }
            return res;
        } catch (IOException e) {
            throw new ParseException("Couldn't read from stream", e);
        }
    }

    public String readString() throws ParseException {
        StringBuilder sb = new StringBuilder();
        int nextNibble;
        while ((nextNibble = nextNibble()) != 0xf) {
            switch (nextNibble) {
                case 0xa:
                    sb.append(".");
                    break;
                case 0xb:
                    sb.append("-");
                    break;
                case 0xc:
                case 0xd:
                case 0xe:
                    throw new ParseException("Illegal nibble: " + nextNibble);
                default:
                    sb.append(nextNibble);
                    break;
            }
        }
        return sb.toString();
    }


}
