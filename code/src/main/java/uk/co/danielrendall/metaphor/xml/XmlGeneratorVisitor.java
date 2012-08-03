package uk.co.danielrendall.metaphor.xml;

import nu.xom.Attribute;
import nu.xom.Element;
import uk.co.danielrendall.metaphor.Record;
import uk.co.danielrendall.metaphor.RecordVisitor;
import uk.co.danielrendall.metaphor.records.*;

import java.util.Stack;

/**
 * @author Daniel Rendall
 */
public class XmlGeneratorVisitor implements RecordVisitor {

    private final Element root;
    public final Stack<Element> current = new Stack<Element>();

    public XmlGeneratorVisitor() {
        root = new Element("mtef");
        current.push(root);
    }

    public Element getRoot() {
        return root;
    }

    public void visit(CHAR aChar) {
        Element el = new Element("char");
        el.addAttribute(new Attribute("charCode", "" + aChar.getCharCode()));
        current.peek().appendChild(el);
    }

    public void visit(COLOR aColor) {
        Element el = new Element("color");
        current.peek().appendChild(el);
    }

    public void visit(COLOR_DEF aColorDef) {
        Element el = new Element("colorDef");
        current.peek().appendChild(el);
    }

    public void visit(EMBELL anEmbell) {
        Element el = new Element("embell");
        current.peek().appendChild(el);
    }

    public void visit(ENCODING_DEF anEncodingDef) {
        Element el = new Element("encodingDef");
        current.peek().appendChild(el);
    }

    public void visit(END anEnd) {
        Element el = new Element("end");
        current.peek().appendChild(el);
    }

    public void visit(EQN_PREFS anEqnPrefs) {
        Element el = new Element("equPrefs");
        Element sizesEl = new Element("sizes");
        for (EQN_PREFS.Dimension size : anEqnPrefs.getSizes()) {
            Element sizeEl = new Element("size");
            sizeEl.addAttribute(new Attribute("unit", size.getUnit()));
            sizeEl.addAttribute(new Attribute("value", size.getValue()));
            sizesEl.appendChild(sizeEl);
        }

        Element spacingsEl = new Element("sizes");
        for (EQN_PREFS.Dimension spacing : anEqnPrefs.getSpacings()) {
            Element spacingEl = new Element("spacing");
            spacingEl.addAttribute(new Attribute("unit", spacing.getUnit()));
            spacingEl.addAttribute(new Attribute("value", spacing.getValue()));
            spacingsEl.appendChild(spacingEl);
        }

        Element stylesEl = new Element("styles");
        for (EQN_PREFS.Style style : anEqnPrefs.getStyles()) {
            Element styleEl = new Element("style");
            styleEl.addAttribute(new Attribute("fontDefIndex", "" + style.getFontDefIndex()));
            styleEl.addAttribute(new Attribute("characterStyle", "" + style.getCharacterStyle()));
            stylesEl.appendChild(styleEl);
        }
        el.appendChild(sizesEl);
        el.appendChild(spacingsEl);
        el.appendChild(stylesEl);
        current.peek().appendChild(el);
    }

    public void visit(FONT_DEF aFontDef) {
        Element el = new Element("fontDef");
        current.peek().appendChild(el);
    }

    public void visit(FONT_STYLE_DEF aFontStyleDef) {
        Element el = new Element("fontStyleDef");
        current.peek().appendChild(el);
    }

    public void visit(FULL aFull) {
        Element el = new Element("full");
        current.peek().appendChild(el);
    }

    public void visit(LINE aLine) {
        Element el = new Element("line");
        current.push(el);
        for(Record record : aLine.getRecords()) {
            record.accept(this);
        }
        current.pop();
        current.peek().appendChild(el);
    }

    public void visit(MATRIX aMatrix) {
        Element el = new Element("matrix");
        current.peek().appendChild(el);
    }

    public void visit(MTEF aMtef) {
        Element el = new Element("mtef");
        el.addAttribute(new Attribute("generatingPlatform", aMtef.getGeneratingPlatform()));
        el.addAttribute(new Attribute("generatingProduct", aMtef.getGeneratingProduct()));
        el.addAttribute(new Attribute("productVersion", "" + aMtef.getProductVersion()));
        el.addAttribute(new Attribute("productSubversion", "" + aMtef.getProductSubversion()));
        el.addAttribute(new Attribute("applicationKey", aMtef.getApplicationKey()));
        el.addAttribute(new Attribute("equationOptions", "" + aMtef.getEquationOptions()));
        current.push(el);
        for(Record record : aMtef.getRecords()) {
            record.accept(this);
        }
        current.pop();
        current.peek().appendChild(el);
    }

    public void visit(PILE aPile) {
        Element el = new Element("pile");
        current.peek().appendChild(el);
    }

    public void visit(RULER aRuler) {
        Element el = new Element("ruler");
        current.peek().appendChild(el);
    }

    public void visit(SIZE aSize) {
        Element el = new Element("size");
        current.peek().appendChild(el);
    }

    public void visit(SUB aSub) {
        Element el = new Element("sub");
        current.peek().appendChild(el);
    }

    public void visit(SUB2 aSub2) {
        Element el = new Element("sub2");
        current.peek().appendChild(el);
    }

    public void visit(SUBSYM aSubSym) {
        Element el = new Element("subsym");
        current.peek().appendChild(el);
    }

    public void visit(SYM aSym) {
        Element el = new Element("sym");
        current.peek().appendChild(el);
    }

    public void visit(TMPL aTmpl) {
        Element el = new Element("tmpl");
        el.addAttribute(new Attribute("templateType", "" + aTmpl.getTemplate().getType()));
        // and other things
        current.push(el);
        for(Record record : aTmpl.getRecords()) {
            record.accept(this);
        }
        current.pop();
        current.peek().appendChild(el);
    }
}
