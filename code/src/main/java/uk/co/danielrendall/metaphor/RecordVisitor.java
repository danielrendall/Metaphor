/*
 * Copyright 2012 Daniel Rendall
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
