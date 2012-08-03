package uk.co.danielrendall.metaphor.records;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import uk.co.danielrendall.metaphor.Record;
import uk.co.danielrendall.metaphor.RecordVisitor;

import java.util.List;

/**
 * @author Daniel Rendall
 */
public class RULER extends Record {

    public final static RULER NO_RULER = new RULER(new TabStop[]{});

    private final List<TabStop> tabStops;

    public RULER(TabStop[] tabStops) {
        this.tabStops = ImmutableList.copyOf(tabStops);
    }

    @Override
    public void accept(RecordVisitor visitor) {
        visitor.visit(this);
    }

    public static class TabStop {
        private final int type;
        private final int offset;

        public TabStop(int type, int offset) {
            this.type = type;
            this.offset = offset;
        }

        public int getType() {
            return type;
        }
    }
}
