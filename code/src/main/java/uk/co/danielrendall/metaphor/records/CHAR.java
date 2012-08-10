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
 * @author Thilo Planz
 */
public class CHAR extends Record implements Nudgeable {
    private final Options options;
    private final Nudge nudge;
    private final int typeFace;
    private final Integer MTCode;
    private final Integer fontPosition;
    private final List<Record> embellishments;

    public CHAR(Options options, Nudge nudge, int typeFace, Integer MTCode, Integer fontPosition, List<Record> embellishments) {
        this.options = options;
        this.nudge = nudge;
        this.typeFace = typeFace;
        this.MTCode = MTCode;
        this.fontPosition = fontPosition;
        this.embellishments = ImmutableList.copyOf(embellishments);
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

    public int getTypeFace() {
        return typeFace;
    }
    
    public Integer getMTCode(){
    	return MTCode;
    }
    
    public Integer getFontPosition(){
    	return fontPosition;
    }


    public List<Record> getEmbellishments() {
        return embellishments;
    }
}
