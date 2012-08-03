package uk.co.danielrendall.metaphor.records;

import com.google.common.collect.ImmutableList;
import uk.co.danielrendall.metaphor.Record;

import java.util.List;

/**
 * @author Daniel Rendall
 */
public class CHAR extends Record {
    private final Options options;
    private final Nudge nudge;
    private final int typeFace;
    private final int charCode;
    private final List<Record> embellishments;

    public CHAR(Options options, Nudge nudge, int typeFace, int charCode, List<Record> embellishments) {
        //To change body of created methods use File | Settings | File Templates.
        this.options = options;
        this.nudge = nudge;
        this.typeFace = typeFace;
        this.charCode = charCode;
        this.embellishments = ImmutableList.copyOf(embellishments);
    }

    public Options getOptions() {
        return options;
    }

    public Nudge getNudge() {
        return nudge;
    }

    public int getTypeFace() {
        return typeFace;
    }

    public int getCharCode() {
        return charCode;
    }

    public List<Record> getEmbellishments() {
        return embellishments;
    }
}
