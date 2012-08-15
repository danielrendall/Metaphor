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

import java.io.IOException;
import java.io.PushbackInputStream;

import uk.co.danielrendall.metaphor.ParseException;
import uk.co.danielrendall.metaphor.Parser;
import uk.co.danielrendall.metaphor.records.FUTURE;

import com.google.common.io.ByteStreams;

/**
* @author Thilo Planz
*/

public class FUTUREParser extends Parser<FUTURE> {

	private final int type;
	
	public FUTUREParser(int recordType){
		this.type = recordType;
	}
	
	@Override
	protected FUTURE doParse(PushbackInputStream in) throws ParseException {
		int length = readUnsignedInt(in);
		byte[] data = new byte[length];
		try {
			ByteStreams.readFully(in, data);
		} catch (IOException e) {
			throw new ParseException("failed to read " + length
					+ " bytes for FUTURE", e);
		}
		return new FUTURE(type, data);
	}

	@Override
	protected int getInitialByte() {
		return type;
	}

}
