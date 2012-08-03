package uk.co.danielrendall.metaphor.records;

import com.google.common.collect.ImmutableList;
import uk.co.danielrendall.metaphor.Record;
import uk.co.danielrendall.metaphor.RecordVisitor;

import java.util.List;

/**
 * @author Daniel Rendall
 */
public class EQN_PREFS extends Record {

    public final static Style UNUSED = new Style(0, 0);
    private final List<Dimension> sizes;
    private final List<Dimension> spacings;
    private final List<Style> styles;

    public EQN_PREFS(Dimension[] sizes, Dimension[] spacings, Style[] styles) {
        this.sizes = ImmutableList.copyOf(sizes);
        this.spacings = ImmutableList.copyOf(spacings);
        this.styles = ImmutableList.copyOf(styles);
    }

    @Override
    public void accept(RecordVisitor visitor) {
        visitor.visit(this);
    }

    public List<Dimension> getSizes() {
        return sizes;
    }

    public List<Dimension> getSpacings() {
        return spacings;
    }

    public List<Style> getStyles() {
        return styles;
    }

    public static class Dimension {
        private final String value;
        private final String unit;

        public Dimension(String unit, String value) {
            this.unit = unit;
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public String getUnit() {
            return unit;
        }
    }

    public static class Style {
        private final int fontDefIndex;
        private final int characterStyle;

        public Style(int fontDefIndex, int characterStyle) {
            this.fontDefIndex = fontDefIndex;
            this.characterStyle = characterStyle;
        }

        public int getFontDefIndex() {
            return fontDefIndex;
        }

        public int getCharacterStyle() {
            return characterStyle;
        }
    }
}
