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

package uk.co.danielrendall.metaphor.records;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import uk.co.danielrendall.metaphor.Record;
import uk.co.danielrendall.metaphor.RecordVisitor;

import javax.annotation.Nullable;
import java.util.List;

/**
 * @author Daniel Rendall
 */
public class MATRIX extends Record {
    private final Options options;
    private final Nudge nudge;
    private final int vAlign;
    private final int hJust;
    private final int vJust;
    private final int rows;
    private final int cols;
    private final List<Integer> rowPartition;
    private final List<Integer> columnPartition;
    private final List<List<Record>> rowList;

    public MATRIX(Options options, Nudge nudge, int vAlign, int hJust, int vJust, int rows, int cols, List<Integer> rowPartition, List<Integer> columnPartition, List<List<Record>> rowList) {

        this.options = options;
        this.nudge = nudge;
        this.vAlign = vAlign;
        this.hJust = hJust;
        this.vJust = vJust;
        this.rows = rows;
        this.cols = cols;
        this.rowPartition = rowPartition;
        this.columnPartition = columnPartition;
        List<List<Record>> newRowList = Lists.newArrayList(Lists.transform(rowList, new Function<List<Record>, List<Record>>() {
            public List<Record> apply(@Nullable List<Record> input) {
                return ImmutableList.copyOf(input);
            }
        }));
        this.rowList = ImmutableList.copyOf(newRowList);
    }

    public Options getOptions() {
        return options;
    }

    public Nudge getNudge() {
        return nudge;
    }

    public int getvAlign() {
        return vAlign;
    }

    public int gethJust() {
        return hJust;
    }

    public int getvJust() {
        return vJust;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public List<Integer> getRowPartition() {
        return rowPartition;
    }

    public List<Integer> getColumnPartition() {
        return columnPartition;
    }

    public List<List<Record>> getRowList() {
        return rowList;
    }

    public Record get(int row, int col) {
        if (row < 0 || row >= rows) {
            throw new IndexOutOfBoundsException("Row " + row + " out of bounds, there are " + rows + " rows in the matrix");
        }
        if (col < 0 || col >= cols) {
            throw new IndexOutOfBoundsException("Col " + col + " out of bounds, there are " + cols + " cols in the matrix");
        }
        return rowList.get(row).get(col);
    }

    @Override
    public void accept(RecordVisitor visitor) {
        visitor.visit(this);
    }
}
