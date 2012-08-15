/*
 * Copyright 2012 Thilo Planz
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.util.Arrays;

import org.junit.Test;

import uk.co.danielrendall.metaphor.ParseException;
import uk.co.danielrendall.metaphor.records.MTEF;

import com.google.common.io.ByteStreams;

/**
*
* @author Thilo Planz
*/
public class MTEFParserTest {

	private MTEF parse(InputStream in) throws ParseException, IOException {
		// throw away 28 byte OLE header
		in.skip(28);
		PushbackInputStream pis =  in instanceof PushbackInputStream ? (PushbackInputStream)in : new PushbackInputStream(in);
		return new MTEFParser().parse(pis);
	}

	@Test
	public void testIncompleteData() throws IOException {
		byte[] data = ByteStreams.toByteArray(MTEFParserTest.class
				.getResourceAsStream("/ole/fraction.bin"));
		try {
			parse(new ByteArrayInputStream(data, 0, data.length - 2));
			fail("should have detected incomplete data");
		} catch (ParseException e) {
			assertEquals("unexpected end of data", e.getMessage());
		}
		try {
			parse(new ByteArrayInputStream(data, 0, data.length - 1));
			fail("should have detected incomplete data");
		} catch (ParseException e) {
			assertEquals("unexpected end of data", e.getMessage());
		}
	}
	
	@Test
	public void testExtraDataAfterEND() throws IOException, ParseException{
		byte[] data = ByteStreams.toByteArray(MTEFParserTest.class
				.getResourceAsStream("/ole/fraction.bin"));
		data = Arrays.copyOf(data, data.length + 10);
		data[data.length-10] = 42;
		InputStream in = new PushbackInputStream(new ByteArrayInputStream(data));
		parse(in);
		assertEquals("next byte after END is still available", 42, in.read());
	}
	

}
