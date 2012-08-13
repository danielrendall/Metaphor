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
import java.util.Arrays;
import java.util.List;

/**
 * @author Daniel Rendall
 */
public class COLOR_DEF extends Record {
    private final Options options;
    private final List<Integer> colorValues;
    private final String name;

    public COLOR_DEF(Options options, List<Integer> colorValues, String name) {
        //To change body of created methods use File | Settings | File Templates.
        this.options = options;
        this.colorValues = ImmutableList.copyOf(colorValues);
        this.name = name;
    }

    @Override
    public void accept(RecordVisitor visitor) {
        visitor.visit(this);
    }

    public Options getOptions() {
        return options;
    }

    public List<Integer> getColorValues() {
        return colorValues;
    }

    public String getName() {
        return name;
    }
}
