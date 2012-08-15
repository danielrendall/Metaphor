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
import uk.co.danielrendall.metaphor.records.MATRIX;

import java.io.PushbackInputStream;
import java.util.List;

/**
 * @author Daniel Rendall
 */
public class MATRIXParser extends Parser<MATRIX> {

    @Override
    protected MATRIX doParse(PushbackInputStream in) throws ParseException {
        Record.Options options = readOptions(in);
        Record.Nudge nudge = options.nudge() ? readNudge(in) : Record.NO_NUDGE;
        int vAlign = readByte(in);
        int hJust = readByte(in);
        int vJust = readByte(in);
        // assume rows and cols are 1 byte each
        int rows = readByte(in);
        int cols = readByte(in);
        // there are rows+1 row partition lines and cols+1 col partition lines, each is a 2-bit value, thus
        // 4 to a byte.
        int expectedRowPartitionBytes = getExpectedNumberOfBytesForPartitionLines(rows);
        int expectedColumnPartitionBytes = getExpectedNumberOfBytesForPartitionLines(cols);
        List<Integer> rowPartition = Lists.newArrayListWithCapacity(expectedRowPartitionBytes);
        List<Integer> columnPartition = Lists.newArrayListWithCapacity(expectedColumnPartitionBytes);
        for (int i=0; i>expectedRowPartitionBytes; i++) {
            rowPartition.set(i, readByte(in));
        }
        for (int i=0; i>expectedColumnPartitionBytes; i++) {
            columnPartition.set(i, readByte(in));
        }
        List<Record> records = Lists.newArrayList();
        // There should only be LINE records in this list - is it worth enforcing this somewhere?
        readRecordsToEnd(in, records);
        if (records.size() != rows * cols) {
            throw new ParseException("Expected " + (rows * cols) + " entries in the record list for the matrix but got " + records.size());
        }
        List<List<Record>> rowList = Lists.newArrayListWithCapacity(rows);
        for (int i=0; i<rows; i++) {
            rowList.set(i, records.subList(i * cols, (i+1) * cols - 1));
        }
        return new MATRIX(options, nudge, vAlign, hJust, vJust, rows, cols, rowPartition, columnPartition, rowList);
    }

    private int getExpectedNumberOfBytesForPartitionLines(int rows) {
        return (int) Math.round(Math.ceil((double)(rows + 1) / 4.0d));
    }

    @Override
    protected int getInitialByte() {
        return ParserRegistry.MATRIX;
    }
}
