package uk.co.danielrendall.metaphor;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PushbackInputStream;
import java.nio.charset.Charset;
import java.util.Map;

/**
 *
 * @author Daniel Rendall
 */
public abstract class Parser<T extends Record> {

    private final static Charset ASCII = Charset.forName("ASCII");

    public T parse (PushbackInputStream in) throws ParseException {
        try {
            int first = in.read();
            if (first != getInitialByte()) {
                throw new ParseException("First byte of record was " + first + " but expected " + getInitialByte());
            }
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

    protected int readUnsignedInt(PushbackInputStream in) throws ParseException {
        int first = readByte(in);
        if (first < 255) {
            return first;
        } else {
            int second = readByte(in);
            int third = readByte(in);
            return third << 8 + second;
        }
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
 }
