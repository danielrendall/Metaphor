package uk.co.danielrendall.metaphor.records;

import com.google.common.collect.ImmutableList;
import uk.co.danielrendall.metaphor.Record;
import uk.co.danielrendall.metaphor.RecordVisitor;

import java.util.List;

/**
 * @author Daniel Rendall
 */
public class PILE extends Record {
    private final Options options;
    private final Nudge nudge;
    private final int hAlign;
    private final int vAlign;
    private final RULER ruler;
    private final List<Record> records;

    public PILE(Options options, Nudge nudge, int hAlign, int vAlign, RULER ruler, List<Record> records) {
        //To change body of created methods use File | Settings | File Templates.
        this.options = options;
        this.nudge = nudge;
        this.hAlign = hAlign;
        this.vAlign = vAlign;
        this.ruler = ruler;
        this.records = ImmutableList.copyOf(records);
    }

    @Override
    public void accept(RecordVisitor visitor) {
        visitor.visit(this);
    }

    public Options getOptions() {
        return options;
    }

    public Nudge getNudge() {
        return nudge;
    }

    public int gethAlign() {
        return hAlign;
    }

    public int getvAlign() {
        return vAlign;
    }

    public RULER getRuler() {
        return ruler;
    }

    public List<Record> getRecords() {
        return records;
    }
}
