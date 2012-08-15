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

import uk.co.danielrendall.metaphor.Record;
import uk.co.danielrendall.metaphor.RecordVisitor;

/**
 * @author Daniel Rendall
 */
public class EMBELL extends Record {
    private final Options options;
    private final Nudge nudge;
    private final int embell;

    public EMBELL(Options options, Nudge nudge, int embell) {
        this.options = options;
        this.nudge = nudge;
        this.embell = embell;
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

    public int getEmbell() {
        return embell;
    }
}
