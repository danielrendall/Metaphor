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

package uk.co.danielrendall.metaphor.parsers;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.co.danielrendall.metaphor.NibbleReader;
import uk.co.danielrendall.metaphor.ParseException;
import uk.co.danielrendall.metaphor.Parser;
import uk.co.danielrendall.metaphor.ParserRegistry;
import uk.co.danielrendall.metaphor.records.EQN_PREFS;

import java.io.PushbackInputStream;
import java.util.Map;

/**
 * @author Daniel Rendall
 */
public class EQN_PREFSParser extends Parser<EQN_PREFS> {
    private static final Logger log = LoggerFactory.getLogger(EQN_PREFSParser.class);

    private final static Map<Integer, String> dimensionsMap;

    static {
        Map<Integer, String> _dimensionsMap = Maps.newHashMap();
        _dimensionsMap.put(0, "inches");
        _dimensionsMap.put(1, "centimeters");
        _dimensionsMap.put(2, "points");
        _dimensionsMap.put(3, "picas");
        _dimensionsMap.put(4, "percentage");
        dimensionsMap = ImmutableMap.copyOf(_dimensionsMap);
    }


    @Override
    protected EQN_PREFS doParse(PushbackInputStream in) throws ParseException {
        int i = readByte(in);
        if (i != 0) {
            throw new ParseException("Options should have been 0");
        }
        int sizeCount = readByte(in);
        log.debug("Size count is " + sizeCount);
        NibbleReader sizeReader = new NibbleReader(in);
        EQN_PREFS.Dimension[] sizes = new EQN_PREFS.Dimension[sizeCount];
        for (int j=0; j < sizeCount; j++) {
            int type = sizeReader.nextNibble();
            String unit = dimensionsMap.get(type);
            String value = sizeReader.readString();
            sizes[j] = new EQN_PREFS.Dimension(unit, value);
            log.debug(value + " " + unit);
        }

        int spacingCount = readByte(in);
        log.debug("Spacing count is " + spacingCount);
        NibbleReader spacingReader = new NibbleReader(in);
        EQN_PREFS.Dimension[] spacings = new EQN_PREFS.Dimension[spacingCount];
        for (int j=0; j < spacingCount; j++) {
            int type = spacingReader.nextNibble();
            String unit = dimensionsMap.get(type);
            String value = spacingReader.readString();
            spacings[j] = new EQN_PREFS.Dimension(unit, value);
            log.debug(value + " " + unit);
        }

        int styleCount = readByte(in);
        log.debug("Style count is " + styleCount);
        EQN_PREFS.Style[] styles = new EQN_PREFS.Style[styleCount];
        for (int j=0; j<styleCount; j++) {
            int fontDefIndex = readUnsignedInt(in);
            EQN_PREFS.Style style = EQN_PREFS.UNUSED;
            if (fontDefIndex != 0) {
                int charStyle = readByte(in);
                style = new EQN_PREFS.Style(fontDefIndex, charStyle);
            }
            styles[j] = style;
        }

        return new EQN_PREFS(sizes, spacings, styles);
    }

    @Override
    protected int getInitialByte() {
        return ParserRegistry.EQN_PREFS;
    }
}
