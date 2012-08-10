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

import com.google.common.io.Files;

import nu.xom.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.co.danielrendall.metaphor.records.MTEF;
import org.junit.Test;
import uk.co.danielrendall.metaphor.xml.XmlGeneratorVisitor;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import static junit.framework.Assert.assertEquals;

/**
 * Unit test for simple App.
 */
public class AppTest {
    private static final Logger log = LoggerFactory.getLogger(AppTest.class);

    @Test
    public void testParseQuadratic() throws ParseException, IOException {
        InputStream is = AppTest.class.getResourceAsStream("/ole/quadratic.bin");
        MTEF mtef = App.parse(is);
        assertEquals("Windows", mtef.getGeneratingPlatform());
        XmlGeneratorVisitor visitor = new XmlGeneratorVisitor();
        mtef.accept(visitor);
        Element root = visitor.getRoot();
        log.debug(root.toXML());
        Files.write(root.toXML(), new File("output.xml"), Charset.forName("UTF-8"));

    }
    
    @Test
    public void testParseFraction() throws ParseException, IOException {
        InputStream is = AppTest.class.getResourceAsStream("/ole/fraction.bin");
        MTEF mtef = App.parse(is);
        assertEquals("Windows", mtef.getGeneratingPlatform());
        XmlGeneratorVisitor visitor = new XmlGeneratorVisitor();
        mtef.accept(visitor);
        Element root = visitor.getRoot();
        log.debug(root.toXML());
    }
}
