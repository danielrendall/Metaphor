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

import org.junit.Test;

import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;

/**
 * Unit test for simple App.
 * @author Daniel Rendall
 * @author Murugan Natarajan
 */
public class AppTest {
	private FilesExplorer explorer = new FilesExplorer();
	private BinFileHandler bin = new BinFileHandler();
	private MMLConverter converter = null;
    
    public AppTest() throws TransformerConfigurationException, IOException {
    	this.converter = new MMLConverter();
    	this.explorer.setOutputExtention(".mml");
    	this.explorer.setSearchPattern("^([^.]*)\\.bin$");
    }

    @Test
    public void testParsesWord2000() throws ParseException, IOException, URISyntaxException, TransformerException {
    	explorer.collectFilePairs(new File(AppTest.class.getResource("/ole/word2000/").toURI()), new File("output/word2000/"));
		for (Map.Entry<File, File> pair : explorer.getFilePairs().entrySet()){
			Files.write(converter.covertBin(bin.parseRequiredContent(Files.toByteArray(pair.getKey()))), pair.getValue());
		}
    }
    
    @Test
    public void testParsesWord2010() throws ParseException, IOException, URISyntaxException, TransformerException {
    	explorer.collectFilePairs(new File(AppTest.class.getResource("/ole/word2010/").toURI()), new File("output/word2010/"));
		for (Map.Entry<File, File> pair : explorer.getFilePairs().entrySet()){
			Files.write(converter.covertBin(bin.parseRequiredContent(Files.toByteArray(pair.getKey()))), pair.getValue());
		}
    }
}
