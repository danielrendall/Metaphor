package uk.co.danielrendall.metaphor.records;

import uk.co.danielrendall.metaphor.Record;

/**
 * @author Daniel Rendall
 */
public class TMPL extends Record {



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
    private final int template;
    private final int variation;
    private final int templateOptions;


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

    public static class OverAndUnderBars extends Template {
        public static final int BAR_DOUBLE = 0x01;

        public OverAndUnderBars(int type) {
            super(type);
        }

        @Override
        public TemplateClass getTemplateClass() {
            return TemplateClass.BarBox;
        }
    }

    public static class Arrows extends Template {
        public static final int AR_SINGLE = 0x00;
        public static final int AR_DOUBLE = 0x01;
        public static final int AR_HARPOON = 0x02;
        public static final int AR_TOP = 0x04;
        public static final int AR_BOTTOM = 0x08;
        public static final int AR_LEFT = 0x10;
        public static final int AR_RIGHT = 0x20;
        public static final int AR_LOS = 0x10;
        public static final int AR_SOL = 0x20;

        public Arrows(int type) {
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

    public static class HorizontalBracesBrackets extends Template {
        public static final int HB_TOP = 0x01;

        public HorizontalBracesBrackets(int type) {
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
