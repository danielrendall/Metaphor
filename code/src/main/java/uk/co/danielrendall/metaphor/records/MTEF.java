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
import uk.co.danielrendall.metaphor.Record;
import uk.co.danielrendall.metaphor.RecordVisitor;

import java.util.List;

/**
 *
 *  @author Daniel Rendall
 */
public class MTEF extends Record {
    private final String generatingPlatform;
    private final String generatingProduct;
    private final int productVersion;
    private final int productSubversion;
    private final String applicationKey;
    private final int equationOptions;
    private final List<Record> records;

    public MTEF(String generatingPlatform, String generatingProduct, int productVersion, int productSubversion, String applicationKey, int equationOptions, List<Record> records) {
        this.generatingPlatform = generatingPlatform;
        this.generatingProduct = generatingProduct;
        this.productVersion = productVersion;
        this.productSubversion = productSubversion;
        this.applicationKey = applicationKey;
        this.equationOptions = equationOptions;
        this.records = ImmutableList.copyOf(records);
    }

    @Override
    public void accept(RecordVisitor visitor) {
        visitor.visit(this);
    }

    public String getGeneratingPlatform() {
        return generatingPlatform;
    }

    public String getGeneratingProduct() {
        return generatingProduct;
    }

    public int getProductVersion() {
        return productVersion;
    }

    public int getProductSubversion() {
        return productSubversion;
    }

    public String getApplicationKey() {
        return applicationKey;
    }

    public int getEquationOptions() {
        return equationOptions;
    }

    public List<Record> getRecords() {
        return records;
    }
}
