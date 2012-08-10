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

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import uk.co.danielrendall.metaphor.parsers.*;

import java.util.Map;

/**
 * @author Daniel Rendall
 */
public class ParserRegistry {

    private static final Map<Integer, Parser> parsers;
    public static final int MTEF = 5;

    public static final int END = 0;
    public static final int LINE = 1;
    public static final int CHAR = 2;
    public static final int TMPL = 3;
    public static final int PILE = 4;
    public static final int MATRIX = 5;
    public static final int EMBELL = 6;
    public static final int RULER = 7;
    public static final int FONT_STYLE_DEF = 8;
    public static final int SIZE = 9;
    public static final int FULL = 10;
    public static final int SUB = 11;
    public static final int SUB2 = 12;
    public static final int SYM = 13;
    public static final int SUBSYM = 14;
    public static final int COLOR = 15;
    public static final int COLOR_DEF = 16;
    public static final int FONT_DEF = 17;
    public static final int EQN_PREFS = 18;
    public static final int ENCODING_DEF = 19;

    static {
        Map<Integer, Parser> _parsers = Maps.newHashMap();
        _parsers.put(END, new ENDParser());
        _parsers.put(LINE, new LINEParser());
        _parsers.put(CHAR, new CHARParser());
        _parsers.put(TMPL, new TMPLParser());
        _parsers.put(PILE, new PILEParser());
        _parsers.put(MATRIX, new MATRIXParser());
        _parsers.put(EMBELL, new EMBELLParser());
        _parsers.put(RULER, new RULERParser());
        _parsers.put(FONT_STYLE_DEF, new FONT_STYLE_DEFParser());
        _parsers.put(SIZE, new SIZEParser());
        _parsers.put(FULL, new FULLParser());
        _parsers.put(SUB, new SUBParser());
        _parsers.put(SUB2, new SUB2Parser());
        _parsers.put(SYM, new SYMParser());
        _parsers.put(SUBSYM, new SUBSYMParser());
        _parsers.put(COLOR, new COLORParser());
        _parsers.put(COLOR_DEF, new COLOR_DEFParser());
        _parsers.put(FONT_DEF, new FONT_DEFParser());
        _parsers.put(EQN_PREFS, new EQN_PREFSParser());
        _parsers.put(ENCODING_DEF, new ENCODING_DEFParser());
        parsers = ImmutableMap.copyOf(_parsers);
    }

    public static Parser get(int i) throws ParseException {
        if (parsers.containsKey(i)) {
            return parsers.get(i);
        }
        throw new ParseException("No parser for type " + i);
    }

}
