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

package uk.co.danielrendall.metaphor.records;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import uk.co.danielrendall.metaphor.Nudgeable;
import uk.co.danielrendall.metaphor.ParseException;
import uk.co.danielrendall.metaphor.Record;
import uk.co.danielrendall.metaphor.RecordVisitor;

import java.util.List;
import java.util.Map;

/**
 * @author Daniel Rendall
 */
public class TMPL extends Record implements Nudgeable {

    private static final Map<Integer, Template> templates;

    public static final int ANGLE = 0;
    public static final int PAREN = 1;
    public static final int BRACE = 2;
    public static final int BRACK = 3;
    public static final int BAR = 4;
    public static final int DBAR = 5;
    public static final int FLOOR = 6;
    public static final int CEILING = 7;
    public static final int OBRACK = 8;
    public static final int INTERVAL = 9;
    public static final int ROOT = 10;
    public static final int FRACT = 11;
    public static final int UBAR = 12;
    public static final int OBAR = 13;
    public static final int ARROW = 14;
    public static final int INTEG = 15;
    public static final int SUM = 16;
    public static final int PROD = 17;
    public static final int COPROD = 18;
    public static final int UNION = 19;
    public static final int INTER = 20;
    public static final int INTOP = 21;
    public static final int SUMOP = 22;
    public static final int LIM = 23;
    public static final int HBRACE = 24;
    public static final int HBRACK = 25;
    public static final int LDIV = 26;
    public static final int SUB = 27;
    public static final int SUP = 28;
    public static final int SUBSUP = 29;
    public static final int DIRAC = 30;
    public static final int VEC = 31;
    public static final int TILDE = 32;
    public static final int HAT = 33;
    public static final int ARC = 24;
    public static final int JSTATUS = 35;
    public static final int STRIKE = 36;
    public static final int BOX = 37;

    static {
        Map<Integer, Template> _templates = Maps.newHashMap();
        _templates.put(ANGLE, new Fence(ANGLE));
        _templates.put(PAREN, new Fence(PAREN));
        _templates.put(BRACE, new Fence(BRACE));
        _templates.put(BRACK, new Fence(BRACK));
        _templates.put(BAR, new Fence(BAR));
        _templates.put(DBAR, new Fence(DBAR));
        _templates.put(FLOOR, new Fence(FLOOR));
        _templates.put(CEILING, new Fence(CEILING));
        _templates.put(OBRACK, new Fence(OBRACK));
        _templates.put(INTERVAL, new Interval(INTERVAL));
        _templates.put(ROOT, new Radical(ROOT));
        _templates.put(FRACT, new Fraction(FRACT));
        _templates.put(UBAR, new OverAndUnderBar(UBAR));
        _templates.put(OBAR, new OverAndUnderBar(OBAR));
        _templates.put(ARROW, new Arrow(ARROW));
        _templates.put(INTEG, new Integral(INTEG));
        _templates.put(SUM, new SumProductUnionIntersection(SUM));
        _templates.put(PROD, new SumProductUnionIntersection(PROD));
        _templates.put(COPROD, new SumProductUnionIntersection(COPROD));
        _templates.put(UNION, new SumProductUnionIntersection(UNION));
        _templates.put(INTER, new SumProductUnionIntersection(INTER));
        _templates.put(INTOP, new SumProductUnionIntersection(INTOP));
        _templates.put(SUMOP, new SumProductUnionIntersection(SUMOP));
        _templates.put(LIM, new Limit(LIM));
        _templates.put(HBRACE, new HorizontalBraceBracket(HBRACE));
        _templates.put(HBRACK, new HorizontalBraceBracket(HBRACK));
        _templates.put(LDIV, new LongDivision(LDIV));
        _templates.put(SUB, new SubscriptSuperscript(SUB));
        _templates.put(SUP, new SubscriptSuperscript(SUP));
        _templates.put(SUBSUP, new SubscriptSuperscript(SUBSUP));
        _templates.put(DIRAC, new DiracBraket(DIRAC));
        _templates.put(VEC, new Vector(VEC));
        _templates.put(TILDE, new HatArcTildeJoint(TILDE));
        _templates.put(HAT, new HatArcTildeJoint(HAT));
        _templates.put(ARC, new HatArcTildeJoint(ARC));
        _templates.put(JSTATUS, new HatArcTildeJoint(JSTATUS));
        _templates.put(STRIKE, new Overstrike(STRIKE));
        _templates.put(BOX, new Box(BOX));
        templates = ImmutableMap.copyOf(_templates);
    }
    
    public static Template get(int i) throws ParseException {
        if (templates.containsKey(i)) {
            return templates.get(i);
        }
        throw new ParseException("No template for type " + i);
    }

    public enum TemplateClass {
        ParBox,
        RootBox,
        FracBox,
        BarBox,
        ArroBox,
        BigOpBox,
        LimBox,
        HFenceBox,
        LDivBox,
        ScrBox,
        DiracBox,
        HatBox,
        StrikeBox,
        TBoxBox
    }

    private final Options options;
    private final Nudge nudge;
    private final Template template;
    private final int variation;
    private final int templateOptions;
    private final List<Record> records;

    public TMPL(Options options, Nudge nudge, Template template, int variation, int templateOptions, List<Record> records) {
        this.options = options;
        this.nudge = nudge;
        this.template = template;
        this.variation = variation;
        this.templateOptions = templateOptions;
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

    public Template getTemplate() {
        return template;
    }

    public int getVariation() {
        return variation;
    }

    public int getTemplateOptions() {
        return templateOptions;
    }

    public List<Record> getRecords() {
        return records;
    }

    public abstract static class Template {
        public abstract TemplateClass getTemplateClass();

        private final int type;

        protected Template(int type) {
            this.type = type;
        }

        public int getType() {
            return type;
        }

        public boolean hasOptions() {
            return false;
        }
    }

    public static class Fence extends Template {
        public static final int FENCE_L = 0x1;
        public static final int FENCE_R = 0x2;

        protected Fence(int type) {
            super(type);
        }

        public TemplateClass getTemplateClass() {
            return TemplateClass.ParBox;
        }

        @Override
        public boolean hasOptions() {
            return true;
        }
    }

    public static class Interval extends Template {
        public static final int INTV_LEFT_LP = 0x00;
        public static final int INTV_LEFT_RP = 0x01;
        public static final int INTV_LEFT_LB = 0x02;
        public static final int INTV_LEFT_RB = 0x03;
        public static final int INTV_RIGHT_LP = 0x00;
        public static final int INTV_RIGHT_RP = 0x10;
        public static final int INTV_RIGHT_LB = 0x20;
        public static final int INTV_RIGHT_RB = 0x30;

        public Interval(int type) {
            super(type);
        }

        @Override
        public TemplateClass getTemplateClass() {
            return TemplateClass.ParBox;
        }
    }

    public static class Radical extends Template {
        public static final int ROOT_SQ = 0;
        public static final int ROOT_NTH = 1;

        public Radical(int type) {
            super(type);
        }

        @Override
        public TemplateClass getTemplateClass() {
            return TemplateClass.RootBox;
        }
    }

    public static class Fraction extends Template {
        public static final int FR_SMALL = 0x01;
        public static final int FR_SLASH = 0x02;
        public static final int FR_BASE = 0x04;

        public Fraction(int type) {
            super(type);
        }

        @Override
        public TemplateClass getTemplateClass() {
            return TemplateClass.FracBox;
        }
    }

    public static class OverAndUnderBar extends Template {
        public static final int BAR_DOUBLE = 0x01;

        public OverAndUnderBar(int type) {
            super(type);
        }

        @Override
        public TemplateClass getTemplateClass() {
            return TemplateClass.BarBox;
        }
    }

    public static class Arrow extends Template {
        public static final int AR_SINGLE = 0x00;
        public static final int AR_DOUBLE = 0x01;
        public static final int AR_HARPOON = 0x02;
        public static final int AR_TOP = 0x04;
        public static final int AR_BOTTOM = 0x08;
        public static final int AR_LEFT = 0x10;
        public static final int AR_RIGHT = 0x20;
        public static final int AR_LOS = 0x10;
        public static final int AR_SOL = 0x20;

        public Arrow(int type) {
            super(type);
        }

        @Override
        public TemplateClass getTemplateClass() {
            return TemplateClass.ArroBox;
        }
    }

    public static class Integral extends Template {
        public static final int INT_1 = 0x01;
        public static final int INT_2 = 0x02;
        public static final int INT_3 = 0x03;
        public static final int INT_LOOP = 0x04;
        public static final int INT_CW_LOOP = 0x08;
        public static final int INT_CCW_LOOP = 0x0C;
        public static final int INT_EXPAND = 0x0100;

        public Integral(int type) {
            super(type);
        }

        @Override
        public TemplateClass getTemplateClass() {
            return TemplateClass.BigOpBox;
        }

        @Override
        public boolean hasOptions() {
            return true;
        }
    }

    public static class SumProductUnionIntersection extends Template {
        public SumProductUnionIntersection(int type) {
            super(type);
        }

        @Override
        public TemplateClass getTemplateClass() {
            return TemplateClass.BigOpBox;
        }
    }

    public static class Limit extends Template {
        public static final int SUBAR = 0;
        public static final int DUBAR = 1;

        public Limit(int type) {
            super(type);
        }

        @Override
        public TemplateClass getTemplateClass() {
            return TemplateClass.LimBox;
        }
    }

    public static class HorizontalBraceBracket extends Template {
        public static final int HB_TOP = 0x01;

        public HorizontalBraceBracket(int type) {
            super(type);
        }

        @Override
        public TemplateClass getTemplateClass() {
            return TemplateClass.HFenceBox;
        }
    }

    public static class LongDivision extends Template {
        public static final int LD_UPPER = 0x01;

        public LongDivision(int type) {
            super(type);
        }

        @Override
        public TemplateClass getTemplateClass() {
            return TemplateClass.LDivBox;
        }
    }

    public static class SubscriptSuperscript extends Template {
        public static final int SU_PRECEDES = 0x01;

        public SubscriptSuperscript(int type) {
            super(type);
        }

        @Override
        public TemplateClass getTemplateClass() {
            return TemplateClass.ScrBox;
        }
    }

    public static class DiracBraket extends Template {
        public static final int DI_LEFT = 0x01;
        public static final int DI_RIGHT = 0x02;

        public DiracBraket(int type) {
            super(type);
        }

        @Override
        public TemplateClass getTemplateClass() {
            return TemplateClass.DiracBox;
        }
    }

    public static class Vector extends Template {
        public static final int VE_LEFT = 0x01;
        public static final int VE_RIGHT = 0x02;
        public static final int VE_UNDER = 0x04;
        public static final int VE_HARPOON = 0x08;

        public Vector(int type) {
            super(type);
        }

        @Override
        public TemplateClass getTemplateClass() {
            return TemplateClass.HatBox;
        }
    }

    public static class HatArcTildeJoint extends Template {

        public HatArcTildeJoint(int type) {
            super(type);
        }

        @Override
        public TemplateClass getTemplateClass() {
            return TemplateClass.HatBox;
        }
    }

    public static class Overstrike extends Template {

        public static final int ST_HORIZ = 0x01;
        public static final int ST_UP = 0x02;
        public static final int ST_DOWN = 0x04;

        public Overstrike(int type) {
            super(type);
        }

        @Override
        public TemplateClass getTemplateClass() {
            return TemplateClass.StrikeBox;
        }
    }

    public static class Box extends Template {
        public static final int BX_ROUND = 0x01;
        public static final int BX_LEFT = 0x02;
        public static final int BX_RIGHT = 0x04;
        public static final int BX_TOP = 0x08;
        public static final int BX_BOTTOM = 0x10;

        public Box(int type) {
            super(type);
        }

        @Override
        public TemplateClass getTemplateClass() {
            return TemplateClass.TBoxBox;
        }
    }
}
