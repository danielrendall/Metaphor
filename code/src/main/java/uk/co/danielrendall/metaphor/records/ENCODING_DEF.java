package uk.co.danielrendall.metaphor.records;

import uk.co.danielrendall.metaphor.Record;

/**
 * @author Daniel Rendall
 */
public class ENCODING_DEF extends Record {
    private final String encodingName;

    public ENCODING_DEF(String encodingName) {
        this.encodingName = encodingName;
    }

    public String getEncodingName() {
        return encodingName;
    }
}
