package uk.co.danielrendall.metaphor.records;

import uk.co.danielrendall.metaphor.Record;
import uk.co.danielrendall.metaphor.RecordVisitor;

/**
 *
 * @author Daniel Rendall
 */
public class END extends Record {
    public final static END Instance = new END();

    private END(){}

    @Override
    public void accept(RecordVisitor visitor) {
        visitor.visit(this);
    }

}
