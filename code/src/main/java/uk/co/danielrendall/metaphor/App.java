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
import uk.co.danielrendall.metaphor.xml.XmlGeneratorVisitor;

import java.io.ByteArrayInputStream;
import java.io.CharArrayReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.io.StringWriter;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.PosixParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nu.xom.Element;

import com.google.common.collect.Lists;
import com.google.common.io.Files;

/*
 * Entry Point to this Library
 * @author Daniel Rendall
 * @author Murugan Natarajan
 */
public class App{
	private static final Logger log = LoggerFactory.getLogger(App.class);
	private Properties config = new Properties();
	private Properties message = new Properties();
	
	public App() throws IOException{
		this.config.load(App.class.getResourceAsStream("/config.properties"));
		this.message.load(App.class.getResourceAsStream(this.config.getProperty("mathMl.conversion.message.path")));
	}
	
    public MTEF parse(InputStream is) throws ParseException, IOException {
    	byte[] bytes = new byte[is.available()];
    	is.read(bytes);
        
    	// To identify and throw away the unwanted contents until MathType OLE Header position for higher version greater than MathType5
        Integer[] startKeys = new Integer[]{28, 0, 0, 0, 2, 0}; // Header Starting 6 byte values
        int startIndex = identifyKeys(startKeys, bytes);
        if(startIndex == -1){
        	throw new ParseException(this.message.getProperty("mathType.ole.header.missing"));
        }
        bytes = Arrays.copyOfRange(bytes, startIndex, bytes.length);
        
       	// To throw away 28 bytes of MathType OLE Header
       	if(28 >= bytes.length){
       		throw new ParseException(this.message.getProperty("mathType.ole.header.missing"));
       	}
       	bytes = Arrays.copyOfRange(bytes, 28, bytes.length);
       	
       	// To identify and throw away the unwanted contents within the body of the equation for higher version greater than MathType5
       	Integer[] unwatedBodyKeys = new Integer[]{69, 0, 113, 0, 117, 0}; // Unwanted Body Content Starting 6 byte values
       	int unwantedBodyIndex = identifyKeys(unwatedBodyKeys, bytes);
       	if(unwantedBodyIndex >= 0 && unwantedBodyIndex + 512 <= bytes.length){
       		byte[] frontBytes = Arrays.copyOfRange(bytes, 0, unwantedBodyIndex);
       		byte[] backBytes = Arrays.copyOfRange(bytes, unwantedBodyIndex + 512, bytes.length);
       		bytes = new byte[frontBytes.length + backBytes.length];
       		System.arraycopy(frontBytes, 0, bytes, 0, frontBytes.length);
       		System.arraycopy(backBytes, 0, bytes, frontBytes.length, backBytes.length);
        }
        
       	PushbackInputStream pis = new PushbackInputStream(new ByteArrayInputStream(bytes));
        return new MTEFParser().parse(pis);
    }
    
    private int identifyKeys(Integer[] keys, byte[] bytes) throws IOException{
        int j = 0;
       	for(int i = 0; i<keys.length && j<bytes.length; j++){
       		if(keys[i] != (int)bytes[j]){
       			i = 0;
       		}else{
       			i++;
       		}
       	}
       	if(j < bytes.length){
       		return j - keys.length;
       	}
        return -1;
    }
    
    private List<File> getFiles(File path){
    	List<File> fileList = Lists.newArrayList();
    	for (File nextFile : path.listFiles()){
    		if(nextFile.isFile()){
    			fileList.add(nextFile);
    		}
    	}
    	return fileList;
    }
    
    private List<File> getFolders(File path){
    	List<File> folderList = Lists.newArrayList();
    	for (File nextFile : path.listFiles()){
    		if(nextFile.isDirectory()){
    			folderList.add(nextFile);
    		}
    	}
    	return folderList;
    }
    
    public void doWithFolder(File rootDir, File outPath, boolean generateOutPath)throws ParseException, IOException, TransformerException, URISyntaxException{
    	log.debug(String.format(this.message.getProperty("mathMl.conversion.current.folder"), rootDir.getName()));
    	List<File> currentFiles = getFiles(rootDir);
    	for(File currentFile: currentFiles){
    		doWithFile(currentFile, outPath, generateOutPath);
    	}
    	List<File> currentFolders = getFolders(rootDir);
    	for(File currentFolder: currentFolders){
    		File output = outPath;
    		if(generateOutPath){
    			output = new File(outPath, currentFolder.getName());
    		}
    		doWithFolder(currentFolder, output, generateOutPath);
    	}
    }
    
    public void doWithFile(File inFile, File outPath, boolean generateOutPath)throws ParseException, IOException, TransformerException, URISyntaxException{
    	log.debug(String.format(this.message.getProperty("mathMl.conversion.current.file"), inFile.getPath()));
    	File mmlOutPath = new File(outPath.getParentFile(), ((outPath.getName().contains("."))? outPath.getName().substring(0, outPath.getName().lastIndexOf(".")) : outPath.getName()) + ".mml");
    	if(generateOutPath){
    		if(!outPath.exists()){
    			outPath.mkdirs();
    		}
    		mmlOutPath = new File(outPath, ((inFile.getName().contains("."))? inFile.getName().substring(0, inFile.getName().lastIndexOf(".")) : inFile.getName()) + ".mml");
    	}
    	String mtefXMLContent = convertMTEF_BIN2MTEF_XML(Files.toByteArray(inFile));
    	String mmlContent = convertMTEF_XML2MML(mtefXMLContent);
    	Files.write(mmlContent, mmlOutPath, Charset.forName("UTF-8"));
    }
    
    public String convertMTEF_BIN2MTEF_XML(byte[] inputContent) throws ParseException, IOException{
    	MTEF mtef = this.parse(new ByteArrayInputStream(inputContent));
        XmlGeneratorVisitor visitor = new XmlGeneratorVisitor();
        mtef.accept(visitor);
        Element root = visitor.getRoot();
        return root.toXML();
    }
    
    public String convertMTEF_XML2MML(String inputContent) throws URISyntaxException, TransformerException{
    	TransformerFactory tFactory = new net.sf.saxon.TransformerFactoryImpl();
        Transformer transformer = tFactory.newTransformer(new javax.xml.transform.stream.StreamSource(App.class.getResourceAsStream(this.config.getProperty("mathMl.conversion.xslt.path"))));
        StringWriter result = new StringWriter();
        transformer.transform(new javax.xml.transform.stream.StreamSource(new CharArrayReader(inputContent.toCharArray())), new javax.xml.transform.stream.StreamResult(result));
        return result.toString();
    }
    
    private void doWithArgs(String[] args) throws ParseException, IOException, TransformerException, URISyntaxException{
    	Options options = new Options();
    	HelpFormatter formatter = new HelpFormatter();
    	options.addOption(this.message.getProperty("mathMl.commandLine.input.option"), true, this.message.getProperty("mathMl.commandLine.input.desc"));
    	options.addOption(this.message.getProperty("mathMl.commandLine.output.option"), true, this.message.getProperty("mathMl.commandLine.output.desc"));
    	CommandLineParser parser = new PosixParser();
    	CommandLine cmd = null;
    	try {
    		cmd = parser.parse(options, args);
    	} catch (org.apache.commons.cli.ParseException e) {
    		System.err.println(this.message.getProperty("mathMl.commandLine.invalid"));
    		e.printStackTrace();
     	}
    	if (cmd == null || cmd != null && !cmd.hasOption(this.message.getProperty("mathMl.commandLine.input.option"))){
    		formatter.printHelp(this.message.getProperty("mathMl.commandLine.help.desc"), options);
    		System.exit(1);
     	}
    	String inputPath = cmd.getOptionValue(this.message.getProperty("mathMl.commandLine.input.option"));
    	String outputPath = this.config.getProperty("mathMl.commandLine.output.default");
    	if (cmd.hasOption(this.message.getProperty("mathMl.commandLine.output.option"))){
    		outputPath = cmd.getOptionValue(this.message.getProperty("mathMl.commandLine.output.option"));
  	   	}
    	File inputFile = new File(inputPath);
    	File outputFile = new File(outputPath);
    	if(!inputFile.exists()){
    		System.err.println(this.message.getProperty("mathMl.commandLine.input.invalid"));
    		System.exit(1);
    	}
    	if(inputFile.getCanonicalFile().isFile()){
    		doWithFile(inputFile, outputFile, false);
    	}else{
    		doWithFolder(inputFile, outputFile, true);
    	}
    	System.out.println(this.message.getProperty("mathMl.conversion.success"));
    }
    
    public static void main(String[] args)throws org.apache.commons.cli.ParseException, ParseException, IOException, TransformerException, URISyntaxException{
    	App obj = new App();
    	obj.doWithArgs(args);
    }
}
