package uk.co.danielrendall.metaphor;

import java.io.IOException;
import java.util.Arrays;

/*
 * MTEF Bin parser to extract required and valid content from it
 * @author Murugan Natarajan
 */
public class BinFileHandler {
	public byte[] parseRequiredContent(byte[] bytes) throws ParseException, IOException {
    	// To identify and throw away the unwanted contents until MathType OLE Header position for higher version greater than MathType5
        Integer[] startKeys = new Integer[]{28, 0, 0, 0, 2, 0}; // Header Starting 6 byte values
        int startIndex = identifyKeys(startKeys, bytes);
        if(startIndex == -1){
        	throw new ParseException("Expected Header Start Value \"1c00 0000 0200\" Not Found with this file.");
        }
        bytes = Arrays.copyOfRange(bytes, startIndex, bytes.length);
        
       	// To throw away 28 bytes of MathType OLE Header
       	if(28 >= bytes.length){
       		throw new ParseException("Expected Header Start Value \"1c00 0000 0200\" Not Found with this file.");
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
        return bytes;
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
}
