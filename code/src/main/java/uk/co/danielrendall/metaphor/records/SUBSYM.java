package uk.co.danielrendall.metaphor.records;

import uk.co.danielrendall.metaphor.Record;
import uk.co.danielrendall.metaphor.RecordVisitor;

/**
 * @author Daniel Rendall
 */
public class SUBSYM extends Record {
    public final static SUBSYM Instance = new SUBSYM();

    private SUBSYM(){}

    @Override
    public void accept(RecordVisitor visitor) {
        visitor.visit(this);
    }
}
