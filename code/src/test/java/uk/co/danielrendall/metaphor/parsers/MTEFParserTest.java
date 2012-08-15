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
		PushbackInputStream pis = in instanceof PushbackInputStream ? (PushbackInputStream) in
				: new PushbackInputStream(in);
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
	public void testExtraDataAfterEND() throws IOException, ParseException {
		byte[] data = ByteStreams.toByteArray(MTEFParserTest.class
				.getResourceAsStream("/ole/fraction.bin"));
		data = Arrays.copyOf(data, data.length + 10);
		data[data.length - 10] = 42;
		InputStream in = new PushbackInputStream(new ByteArrayInputStream(data));
		parse(in);
		assertEquals("next byte after END is still available", 42, in.read());
	}

	@Test
	public void testSkipFUTURE() throws ParseException, IOException {
		byte[] data = { 28, 0, 0, 0, 2, 0, 0, 0, -53, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 0, 0, 6, 0, 68, 83, 77, 84,
				54, 0, 0, 100, 3, 0, 0, 0, 19, 77, 97, 99, 82, 111, 109, 97,
				110, 0, 17, 5, 84, 105, 109, 101, 115, 0, 17, 3, 83, 121, 109,
				98, 111, 108, 0, 17, 5, 67, 111, 117, 114, 105, 101, 114, 0,
				17, 4, 77, 84, 32, 69, 120, 116, 114, 97, 0, 18, 0, 8, 33, 47,
				69, -113, 68, 47, 65, 80, -12, 16, 15, 71, 95, 65, 80, -14, 31,
				33, 65, 80, -12, 21, 15, 65, 0, -12, 69, -12, 37, -12, -113,
				66, 95, 65, 0, -12, 16, 15, 67, 95, 65, 0, -12, -113, 69, -12,
				42, 95, 72, -12, -113, 65, 0, -12, 16, 15, 64, -12, -113, 65,
				127, 72, -12, 16, 15, 65, 42, 95, 68, 95, 69, -12, 95, 69, -12,
				95, 65, 15, 66, 15, 66, 15, 66, 15, 12, 1, 0, 1, 0, 1, 2, 2, 2,
				2, 0, 2, 0, 1, 1, 1, 0, 3, 0, 1, 0, 4, 0, 0, 10, 1, 0, 3, 0,
				11, 0, 0, 1, 0, 2, 0, -120, 51, 0, 0, 1, 0, 2, 0, -120, 56, 0,
				0, 0, 0, 0 };

		InputStream in = new PushbackInputStream(new ByteArrayInputStream(data));
		parse(in);
	}

}
