package uk.co.danielrendall.metaphor;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.transform.TransformerException;

import com.google.common.collect.Lists;

/*
 * To recursively collect files of some pattern along with its output path
 * @author Murugan Natarajan
 */
public class FilesExplorer {
	private String searchPattern;
	private boolean generateOutPath;
	private String outputExtention;
	private Map<File, File> filePairs = new HashMap<File, File>();
	
	
	public String getSearchPattern() {
		return searchPattern;
	}

	public void setSearchPattern(String searchPattern) {
		this.searchPattern = searchPattern;
	}

	public String getOutputExtention() {
		return this.outputExtention;
	}

	public void setOutputExtention(String outputExtention) {
		this.outputExtention = outputExtention;
	}

	public Map<File, File> getFilePairs() {
		return this.filePairs;
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
    
    private void doWithFolder(File rootDir, File outPath)throws ParseException, IOException, TransformerException, URISyntaxException{
    	List<File> currentFiles = this.getFiles(rootDir);
    	for(File currentFile: currentFiles){
    		this.doWithFile(currentFile, outPath);
    	}
    	List<File> currentFolders = this.getFolders(rootDir);
    	for(File currentFolder: currentFolders){
    		File output = outPath;
    		if(this.generateOutPath){
    			output = new File(outPath, currentFolder.getName());
    		}
    		this.doWithFolder(currentFolder, output);
    	}
    }
    
    private void doWithFile(File inFile, File outPath)throws ParseException, IOException, TransformerException, URISyntaxException{
    	File outFile = outPath;
    	if(outPath.isDirectory() || !outPath.getName().contains(".")){
    		if(!outPath.exists()){
    			outPath.mkdirs();
    		}
    		outFile = new File(outPath, ((inFile.getName().contains("."))? inFile.getName().substring(0, inFile.getName().lastIndexOf(".")) : inFile.getName()) + this.getOutputExtention());
    	}
    	if(inFile.getName().matches(this.searchPattern)){
    		this.filePairs.put(inFile, outFile);
    	}
    }
    
    public void collectFilePairs(File inFile, File outPath) throws ParseException, IOException, TransformerException, URISyntaxException{
    	this.filePairs.clear();
    	if(inFile.getCanonicalFile().isFile()){
    		this.generateOutPath = false;
    		this.doWithFile(inFile, outPath);
    	}
    	if(inFile.getCanonicalFile().isDirectory()){
    		this.generateOutPath = true;
    		this.doWithFolder(inFile, outPath);
    	}
    }
}
