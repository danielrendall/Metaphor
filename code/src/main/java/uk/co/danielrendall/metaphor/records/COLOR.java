package uk.co.danielrendall.metaphor.records;

import uk.co.danielrendall.metaphor.Record;
import uk.co.danielrendall.metaphor.RecordVisitor;

/**
 * @author Daniel Rendall
 */
public class COLOR extends Record {
    private final int colorDefIndex;

    public COLOR(int colorDefIndex) {
        this.colorDefIndex = colorDefIndex;
    }

    @Override
    public void accept(RecordVisitor visitor) {
        visitor.visit(this);
    }

    public int getColorDefIndex() {
        return colorDefIndex;
    }
}
