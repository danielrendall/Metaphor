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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

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
    public MTEF parse(InputStream is) throws ParseException, IOException {
    	byte[] bytes = new byte[is.available()];
    	is.read(bytes);
        
    	// To identify and throw away the unwanted contents until MathType OLE Header position for higher version greater than MathType5
        Integer[] startKeys = new Integer[]{28, 0, 0, 0, 2, 0}; // Header Starting 6 byte values
        int startIndex = identifyKeys(startKeys, bytes);
        if(startIndex == -1){
        	throw new ParseException("Expected Header Start Value \"1c00 0000 0200\" Not Found with this file.");
        }
        bytes = Arrays.copyOfRange(bytes, startIndex, bytes.length);
        
       	// To throw away 28 bytes of MathType OLE Header
       	if(28 >= bytes.length){
       		throw new ParseException("Expected Header Start Value \"1c00 0000 0200\" Found at the End of file.");
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
    	log.debug("Processing Folder \"" + rootDir.getName() + "\" ...");
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
    	log.debug("Processing File \"" + inFile.getPath() + "\" ...");
    	File xmlOutPath = new File(outPath.getParentFile(), ((outPath.getName().contains("."))? outPath.getName().substring(0, outPath.getName().lastIndexOf(".")) : outPath.getName()) + ".xml");
    	File mmlOutPath = new File(outPath.getParentFile(), ((outPath.getName().contains("."))? outPath.getName().substring(0, outPath.getName().lastIndexOf(".")) : outPath.getName()) + ".mml");
    	if(generateOutPath){
    		if(!outPath.exists()){
    			outPath.mkdirs();
    		}
    		xmlOutPath = new File(outPath, ((inFile.getName().contains("."))? inFile.getName().substring(0, inFile.getName().lastIndexOf(".")) : inFile.getName()) + ".xml");
    		mmlOutPath = new File(outPath, ((inFile.getName().contains("."))? inFile.getName().substring(0, inFile.getName().lastIndexOf(".")) : inFile.getName()) + ".mml");
    	}
        InputStream is = new FileInputStream(inFile);
        MTEF mtef = this.parse(is);
        XmlGeneratorVisitor visitor = new XmlGeneratorVisitor();
        mtef.accept(visitor);
        Element root = visitor.getRoot();
        log.debug(root.toXML());
        Files.write(root.toXML(), xmlOutPath, Charset.forName("UTF-8"));
        TransformerFactory tFactory = new net.sf.saxon.TransformerFactoryImpl();
        Transformer transformer = tFactory.newTransformer(new javax.xml.transform.stream.StreamSource(new File(App.class.getResource("/xslt/math.xsl").toURI())));
        transformer.transform(new javax.xml.transform.stream.StreamSource(xmlOutPath), new javax.xml.transform.stream.StreamResult( new FileOutputStream(mmlOutPath)));
    }
    
    private void doWithArgs(String[] args) throws ParseException, IOException, TransformerException, URISyntaxException{
    	Options options = new Options();
    	HelpFormatter formatter = new HelpFormatter();
    	options.addOption("i", true, "MathType MTEF OLE binary file location.");
    	options.addOption("o", true, "XML Output File Name, By Default it will be placed in current location and it will be in same name as input file name with xml file extention.");
    	CommandLineParser parser = new PosixParser();
    	CommandLine cmd = null;
    	try {
    		cmd = parser.parse(options, args);
    	} catch (org.apache.commons.cli.ParseException e) {
    		System.err.println("Invalid Command Line Arguments Given.");
    		e.printStackTrace();
     	}
    	if (cmd == null || cmd != null && !cmd.hasOption("i")){
    		formatter.printHelp("java -jar <JarFileName>.jar", options);
    		System.exit(1);
     	}
    	String inputPath = cmd.getOptionValue("i");
    	String outputPath = "./";
    	if (cmd.hasOption("o")){
    		outputPath = cmd.getOptionValue("o");
  	   	}
    	File inputFile = new File(inputPath);
    	File outputFile = new File(outputPath);
    	if(!inputFile.exists()){
    		System.err.println("Input directory or file location given not found.");
    		System.exit(1);
    	}
    	if(inputFile.getCanonicalFile().isFile()){
    		doWithFile(inputFile, outputFile, false);
    	}else{
    		doWithFolder(inputFile, outputFile, true);
    	}
    }
    
    public static void main(String[] args)throws org.apache.commons.cli.ParseException, ParseException, IOException, TransformerException, URISyntaxException{
    	App obj = new App();
    	obj.doWithArgs(args);
    	System.out.println("Process Completed Successfully ...");
    }
}
