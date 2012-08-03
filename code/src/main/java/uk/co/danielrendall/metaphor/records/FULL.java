package uk.co.danielrendall.metaphor.records;

import uk.co.danielrendall.metaphor.Record;
import uk.co.danielrendall.metaphor.RecordVisitor;

/**
 * @author Daniel Rendall
 */
public class FULL extends Record {
    public final static FULL Instance = new FULL();

    private FULL(){}

    @Override
    public void accept(RecordVisitor visitor) {
        visitor.visit(this);
    }
}
