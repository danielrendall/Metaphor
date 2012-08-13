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

import com.google.common.collect.ImmutableList;
import uk.co.danielrendall.metaphor.Nudgeable;
import uk.co.danielrendall.metaphor.Record;
import uk.co.danielrendall.metaphor.RecordVisitor;

import java.util.List;

/**
 * @author Daniel Rendall
 */
public class LINE extends Record implements Nudgeable {
    private final Options options;
    private final Nudge nudge;
    private final int lineSpacing;
    private final RULER ruler;
    private final List<Record> records;

    public LINE(Options options, Nudge nudge, int lineSpacing, RULER ruler, List<Record> records) {
        this.options = options;
        this.nudge = nudge;
        this.lineSpacing = lineSpacing;
        this.ruler = ruler;
        this.records = ImmutableList.copyOf(records);
    }

    @Override
    public void accept(RecordVisitor visitor) {
        visitor.visit(this);
    }

    public Options getOptions() {
        return options;
    }

    public Nudge getNudge() {
        return nudge;
    }

    public int getLineSpacing() {
        return lineSpacing;
    }

    public RULER getRuler() {
        return ruler;
    }

    public List<Record> getRecords() {
        return records;
    }
}
