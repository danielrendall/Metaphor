package uk.co.danielrendall.metaphor.records;

import com.google.common.collect.ImmutableList;
import uk.co.danielrendall.metaphor.Record;

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
