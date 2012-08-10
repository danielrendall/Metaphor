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

/**
 *
 * @author Daniel Rendall
 */
public abstract class Record {

    public final static Nudge NO_NUDGE = new Nudge(0,0);

    public abstract void accept(RecordVisitor visitor);

    /**
     * @author Daniel Rendall
     */
    public static class Options {
        public final static int NUDGE = 0x08;
        public final static int CHAR_EMBELL = 0x01;
        public final static int CHAR_FUNC_START = 0x02;
        public final static int CHAR_ENC_CHAR_8 = 0x04;
        public final static int CHAR_ENC_CHAR_16 = 0x10;
        public final static int CHAR_ENC_NO_MTCODE = 0x20;

        public final static int LINE_NULL = 0x01;
        public final static int LINE_LSPACE = 0x04;
        public final static int LP_RULER = 0x02;

        public final static int COLOR_CMYK = 0x01;
        public final static int COLOR_SPOT = 0x02;
        public final static int COLOR_NAME = 0x04;

        public final static int BO_LOWER = 0x01;
        public final static int BO_UPPER = 0x02;
        public final static int BO_SUM = 0x40;

        private final int option;

        public Options(int option) {
            this.option = option;
        }

        private boolean check(int flag) {
            return (option & flag) > 0;
        }

        public boolean nudge() {
            return check(NUDGE);
        }

        public boolean char_embell() {
            return check(CHAR_EMBELL);
        }

        public boolean char_func_start() {
            return check(CHAR_FUNC_START);
        }

        public boolean char_enc_char_8() {
            return check(CHAR_ENC_CHAR_8);
        }

        public boolean char_enc_char_16() {
            return check(CHAR_ENC_CHAR_16);
        }

        public boolean char_enc_no_mtcode() {
            return check(CHAR_ENC_NO_MTCODE);
        }

        public boolean line_null() {
            return check(LINE_NULL);
        }

        public boolean line_lspace() {
            return check(LINE_LSPACE);
        }

        public boolean lp_ruler() {
            return check(LP_RULER);
        }

        public boolean color_cmyk() {
            return check(COLOR_CMYK);
        }

        public boolean color_spot() {
            return check(COLOR_SPOT);
        }

        public boolean color_name() {
            return check(COLOR_NAME);
        }
    }

    public static class Nudge {
        private final int dx;
        private final int dy;

        public Nudge(int dy, int dx) {
            this.dy = dy;
            this.dx = dx;
        }

        public int getDx() {
            return dx;
        }

        public int getDy() {
            return dy;
        }
    }
}
