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
import com.google.common.collect.Lists;
import uk.co.danielrendall.metaphor.Record;
import uk.co.danielrendall.metaphor.RecordVisitor;

import java.util.List;

/**
 * @author Daniel Rendall
 */
public class RULER extends Record {

    public final static RULER NO_RULER = new RULER(new TabStop[]{});

    private final List<TabStop> tabStops;

    public RULER(TabStop[] tabStops) {
        this.tabStops = ImmutableList.copyOf(tabStops);
    }

    @Override
    public void accept(RecordVisitor visitor) {
        visitor.visit(this);
    }

    public List<TabStop> getTabStops() {
        return tabStops;
    }

    public static class TabStop {
        private final int type;
        private final int offset;

        public TabStop(int type, int offset) {
            this.type = type;
            this.offset = offset;
        }

        public int getType() {
            return type;
        }
    }
}
