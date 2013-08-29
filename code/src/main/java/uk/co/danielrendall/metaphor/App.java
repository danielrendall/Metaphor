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

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Properties;

import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.PosixParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.io.Files;

/*
 * Entry Point to this Library
 * @author Daniel Rendall
 * @author Murugan Natarajan
 */
public class App{
	private static final Logger log = LoggerFactory.getLogger(App.class);
	private Properties config = new Properties();
	private File inputFile = null;
	private File outputFile = null;
	
	private App() throws IOException, TransformerConfigurationException{
		this.config.load(App.class.getResourceAsStream("/cmdln/config.properties"));
	}
	
    private boolean doWithArgs(String[] args) throws ParseException, IOException, TransformerException, URISyntaxException{
    	Options options = new Options();
    	HelpFormatter formatter = new HelpFormatter();
    	options.addOption(this.config.getProperty("mathMl.commandLine.input.option"), true, this.config.getProperty("mathMl.commandLine.input.desc"));
    	options.addOption(this.config.getProperty("mathMl.commandLine.output.option"), true, this.config.getProperty("mathMl.commandLine.output.desc"));
    	CommandLineParser parser = new PosixParser();
    	CommandLine cmd = null;
    	try {
    		cmd = parser.parse(options, args);
    	} catch (org.apache.commons.cli.ParseException e) {
    		log.error(this.config.getProperty("mathMl.commandLine.invalid"));
    		e.printStackTrace();
     	}
    	if (cmd == null || cmd != null && !cmd.hasOption(this.config.getProperty("mathMl.commandLine.input.option"))){
    		formatter.printHelp(this.config.getProperty("mathMl.commandLine.help.desc"), options);
    		return false;
     	}
    	String inputPath = cmd.getOptionValue(this.config.getProperty("mathMl.commandLine.input.option"));
    	String outputPath = this.config.getProperty("mathMl.commandLine.output.default");
    	if (cmd.hasOption(this.config.getProperty("mathMl.commandLine.output.option"))){
    		outputPath = cmd.getOptionValue(this.config.getProperty("mathMl.commandLine.output.option"));
  	   	}
    	File inputFile = new File(inputPath);
    	File outputFile = new File(outputPath);
    	if(!inputFile.exists()){
    		log.error(this.config.getProperty("mathMl.commandLine.input.invalid"));
    		return false;
    	}
    	this.inputFile = inputFile;
    	this.outputFile = outputFile;
    	return true;
    }
    
    public static void main(String[] args)throws org.apache.commons.cli.ParseException, ParseException, IOException, TransformerException, URISyntaxException{
    	App obj = new App();
    	if(obj.doWithArgs(args)){
    		FilesExplorer explorer = new FilesExplorer();
    		BinFileHandler bin = new BinFileHandler();
    		MMLConverter converter = new MMLConverter();
    		explorer.setOutputExtention(".mml");
        	explorer.setSearchPattern("^([^.]*)\\.bin$");
    		explorer.collectFilePairs(obj.inputFile, obj.outputFile);
    		for (Map.Entry<File, File> pair : explorer.getFilePairs().entrySet()){
    			Files.write(converter.covertBin(bin.parseRequiredContent(Files.toByteArray(pair.getKey()))), pair.getValue());
    		}
    		log.info(obj.config.getProperty("mathMl.conversion.success"));
    	}
    }
}
