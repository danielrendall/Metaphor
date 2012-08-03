package uk.co.danielrendall.metaphor.records;

import uk.co.danielrendall.metaphor.Record;
import uk.co.danielrendall.metaphor.RecordVisitor;

/**
 * @author Daniel Rendall
 */
public class EMBELL extends Record {
    @Override
    public void accept(RecordVisitor visitor) {
        visitor.visit(this);
    }
}
