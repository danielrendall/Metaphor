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

import uk.co.danielrendall.metaphor.parsers.MTEFParser;
import uk.co.danielrendall.metaphor.records.MTEF;

import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;

/**
 * Hello world!
 *
 */
public class App 
{
    public static MTEF parse(InputStream is) throws ParseException, IOException {
        PushbackInputStream pis = new PushbackInputStream(is);
        // hack - throw away 28 byte OLE header
        pis.read(new byte[28]);
        return new MTEFParser().parse(pis);
    }
}
