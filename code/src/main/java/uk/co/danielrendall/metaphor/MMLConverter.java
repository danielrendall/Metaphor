package uk.co.danielrendall.metaphor;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PushbackInputStream;
import java.net.URISyntaxException;
import java.util.Properties;

import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;

import nu.xom.Element;
import uk.co.danielrendall.metaphor.parsers.MTEFParser;
import uk.co.danielrendall.metaphor.records.MTEF;
import uk.co.danielrendall.metaphor.xml.XmlGeneratorVisitor;

/*
 * MML Conversion process simple entry point
 * @author Murugan Natarajan
 */
public class MMLConverter {
	private Properties config = new Properties();
	private XsltProcessor xslt = null;

	public MMLConverter() throws IOException, TransformerConfigurationException{
		this.config.load(MMLConverter.class.getResourceAsStream("/convert/mmlconverter.properties"));
		this.xslt = new XsltProcessor(MMLConverter.class.getResourceAsStream(this.config.getProperty("converter.mt.mml.xslt.file")));
	}
	
	private byte[] convertMTEF_BIN2MTEF_XML(byte[] inputContent) throws ParseException, IOException{
    	PushbackInputStream pis = new PushbackInputStream(new ByteArrayInputStream(inputContent));
    	MTEF mtef = new MTEFParser().parse(pis);
        XmlGeneratorVisitor visitor = new XmlGeneratorVisitor();
        mtef.accept(visitor);
        Element root = visitor.getRoot();
        return root.toXML().getBytes();
    }
    
    private byte[] convertMTEF_XML2MML(byte[] inputContent) throws URISyntaxException, TransformerException{
    	return this.xslt.convert(inputContent);
    }
    
    public byte[] covertBin(byte[] inputContent) throws URISyntaxException, TransformerException, ParseException, IOException{
    	return this.convertMTEF_XML2MML(this.convertMTEF_BIN2MTEF_XML(inputContent));
    }
}
