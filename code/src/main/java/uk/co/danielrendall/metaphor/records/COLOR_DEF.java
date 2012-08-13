package uk.co.danielrendall.metaphor.records;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import uk.co.danielrendall.metaphor.Record;
import uk.co.danielrendall.metaphor.RecordVisitor;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

/**
 * @author Daniel Rendall
 */
public class COLOR_DEF extends Record {
    private final Options options;
    private final List<Integer> colorValues;
    private final String name;

    public COLOR_DEF(Options options, List<Integer> colorValues, String name) {
        //To change body of created methods use File | Settings | File Templates.
        this.options = options;
        this.colorValues = ImmutableList.copyOf(colorValues);
        this.name = name;
    }

    @Override
    public void accept(RecordVisitor visitor) {
        visitor.visit(this);
    }

    public Options getOptions() {
        return options;
    }

    public List<Integer> getColorValues() {
        return colorValues;
    }

    public String getName() {
        return name;
    }
}
