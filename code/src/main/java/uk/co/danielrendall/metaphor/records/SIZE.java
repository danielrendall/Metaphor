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
public class SIZE extends Record {

    public enum SizeType {
        ExplicitPointSize,
        SmallDelta,
        LargeDelta
    }

    private final SizeType sizeType;
    private final int lSize;
    private final int dSize;

    public SIZE(SizeType sizeType, int lSize, int dSize) {

        this.sizeType = sizeType;
        this.lSize = lSize;
        this.dSize = dSize;
    }

    public SizeType getSizeType() {
        return sizeType;
    }

    public int getlSize() {
        return lSize;
    }

    public int getdSize() {
        return dSize;
    }

    @Override
    public void accept(RecordVisitor visitor) {
        visitor.visit(this);
    }
}
