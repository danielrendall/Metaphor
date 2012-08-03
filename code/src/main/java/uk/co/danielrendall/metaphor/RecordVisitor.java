package uk.co.danielrendall.metaphor;

import uk.co.danielrendall.metaphor.records.*;

/**
 * @author Daniel Rendall
 */
public interface RecordVisitor {
    public void visit(CHAR aChar);
    public void visit(COLOR aColor);
    public void visit(COLOR_DEF aColorDef);
    public void visit(EMBELL anEmbell);
    public void visit(ENCODING_DEF anEncodingDef);
    public void visit(END anEnd);
    public void visit(EQN_PREFS anEqnPrefs);
    public void visit(FONT_DEF aFontDef);
    public void visit(FONT_STYLE_DEF aFontStyleDef);
    public void visit(FULL aFull);
    public void visit(LINE aLine);
    public void visit(MATRIX aMatrix);
    public void visit(MTEF aMtef);
    public void visit(PILE aPile);
    public void visit(RULER aRuler);
    public void visit(SIZE aSize);
    public void visit(SUB aSub);
    public void visit(SUB2 aSub2);
    public void visit(SUBSYM aSubSym);
    public void visit(SYM aSym);
    public void visit(TMPL aTmpl);
}
