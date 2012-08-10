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
import uk.co.danielrendall.metaphor.records.END;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PushbackInputStream;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Daniel Rendall
 */
public abstract class Parser<T extends Record> {
    private static final Logger log = LoggerFactory.getLogger(Parser.class);

    private final static Charset ASCII = Charset.forName("ASCII");

    public T parse (PushbackInputStream in) throws ParseException {
        try {
            int first = in.read();
            if (first != getInitialByte()) {
                throw new ParseException("First byte of record was " + first + " but expected " + getInitialByte());
            }
            log.debug(this.getClass().getName() + " parsing record type " + first);
            return doParse(in);
        } catch (IOException e) {
            throw new ParseException("Unable to read stream", e);
        }
    }

    protected abstract T doParse(PushbackInputStream in) throws ParseException;

    protected abstract int getInitialByte();

    protected String readNullTerminatedString(PushbackInputStream in) throws ParseException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int i;
        while ((i = readByte(in)) > 0) {
            baos.write(i);
        }
        return new String(baos.toByteArray(), ASCII);
    }

    protected int readByte(PushbackInputStream in) throws ParseException {
        try {
            int i = in.read();
            if (i == -1) {
                throw new ParseException("Ran out of stream");
            }
            return i;
        } catch (IOException e) {
            throw new ParseException("Couldn't read from stream", e);
        }
    }

    protected Record.Options readOptions(PushbackInputStream in) throws ParseException {
        return new Record.Options(readByte(in));
    }

    protected Record.Nudge readNudge(PushbackInputStream in) throws ParseException {
        int dx = readByte(in);
        int dy = readByte(in);
        // TODO - check
        if (dx == 255 && dy == 255) {
            dx = readTwoByteSignedInt(in);
            dy = readTwoByteSignedInt(in);
            return new Record.Nudge(dx, dy);
        } else {
            return new Record.Nudge(dx - 128, dy - 128);
        }
    }

    protected int readTwoByteSignedInt(PushbackInputStream in) throws ParseException {
        return readTwoByteUnsignedInt(in) - 32768;
    }

    protected int readTwoByteUnsignedInt(PushbackInputStream in) throws ParseException {
        int low = readByte(in);
        int high = readByte(in);
        return (high << 8) + low;
    }

    protected int readUnsignedInt(PushbackInputStream in) throws ParseException {
        int first = readByte(in);
        if (first < 255) {
            return first;
        } else {
            return readTwoByteUnsignedInt(in);
        }
    }

    protected int readSimple16BitInteger(PushbackInputStream in) throws ParseException {
        return readTwoByteUnsignedInt(in);
    }

    protected String getFromMap(PushbackInputStream in, Map<Integer, String> map) throws ParseException {
        int key = readByte(in);
        if (map.containsKey(key)) {
            return map.get(key);
        }
        throw new ParseException("Key " + key + " wasn't in map");
    }

    protected void endRecord(PushbackInputStream in) throws ParseException {
        int i = readByte(in);
        if (i != 0) {
            throw new ParseException("Expected end of record but got " + i);
        }
    }

    protected int nextType(PushbackInputStream in) throws ParseException {
        try {
            int next = in.read();
            if (next != -1) {
                in.unread(next);
            }
            return next;
        } catch (IOException e) {
            throw new ParseException("Couldn't check next type", e);
        }
    }

    protected void readRecordsToEnd(PushbackInputStream in, List<Record> records) throws ParseException {
        while (true) {
            int type = nextType(in);
            Parser next = ParserRegistry.get(type);
            Record record = next.parse(in);
            if (record instanceof END) {
                break;
            }
            records.add(record);
        }
    }
}
