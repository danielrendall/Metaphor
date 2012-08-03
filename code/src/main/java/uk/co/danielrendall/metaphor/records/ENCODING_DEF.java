package uk.co.danielrendall.metaphor.records;

import uk.co.danielrendall.metaphor.Record;
import uk.co.danielrendall.metaphor.RecordVisitor;

/**
 * @author Daniel Rendall
 */
public class ENCODING_DEF extends Record {
    private final String encodingName;

    public ENCODING_DEF(String encodingName) {
        this.encodingName = encodingName;
    }

    @Override
    public void accept(RecordVisitor visitor) {
        visitor.visit(this);
    }

    public String getEncodingName() {
        return encodingName;
    }
}
