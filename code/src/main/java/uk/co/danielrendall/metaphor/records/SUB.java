package uk.co.danielrendall.metaphor.records;

import uk.co.danielrendall.metaphor.Record;
import uk.co.danielrendall.metaphor.RecordVisitor;

/**
 * @author Daniel Rendall
 */
public class SUB extends Record {
    public final static SUB Instance = new SUB();

    private SUB(){}
    @Override

    public void accept(RecordVisitor visitor) {
        visitor.visit(this);
    }
}
