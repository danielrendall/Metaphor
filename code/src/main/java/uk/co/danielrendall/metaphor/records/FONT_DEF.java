package uk.co.danielrendall.metaphor.records;

import uk.co.danielrendall.metaphor.Record;
import uk.co.danielrendall.metaphor.RecordVisitor;

/**
 * @author Daniel Rendall
 */
public class FONT_DEF extends Record {
    private final int encDefIndex;
    private final String fontName;

    public FONT_DEF(int encDefIndex, String fontName) {
        this.encDefIndex = encDefIndex;
        this.fontName = fontName;
    }

    @Override
    public void accept(RecordVisitor visitor) {
        visitor.visit(this);
    }

    public int getEncDefIndex() {
        return encDefIndex;
    }

    public String getFontName() {
        return fontName;
    }
}
