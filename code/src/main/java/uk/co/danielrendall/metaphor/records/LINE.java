package uk.co.danielrendall.metaphor.records;

import com.google.common.collect.ImmutableList;
import uk.co.danielrendall.metaphor.Record;

import java.util.List;

/**
 * @author Daniel Rendall
 */
public class LINE extends Record {
    private final Options options;
    private final Nudge nudge;
    private final int lineSpacing;
    private final RULER ruler;
    private final List<Record> records;

    public LINE(Options options, Nudge nudge, int lineSpacing, RULER ruler, List<Record> records) {

        this.options = options;
        this.nudge = nudge;
        this.lineSpacing = lineSpacing;
        this.ruler = ruler;
        this.records = ImmutableList.copyOf(records);
    }

    public Options getOptions() {
        return options;
    }

    public Nudge getNudge() {
        return nudge;
    }

    public int getLineSpacing() {
        return lineSpacing;
    }

    public RULER getRuler() {
        return ruler;
    }

    public List<Record> getRecords() {
        return records;
    }
}
