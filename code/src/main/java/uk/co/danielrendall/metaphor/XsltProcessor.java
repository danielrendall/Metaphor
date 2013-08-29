package uk.co.danielrendall.metaphor;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URISyntaxException;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;

/*
 * XSLT Processor to apply XSL transformation to given XML
 * @author Murugan Natarajan
 */
public class XsltProcessor {
	private Transformer transformer = null;
	
	public XsltProcessor(InputStream xsltStream) throws TransformerConfigurationException{
		TransformerFactory tFactory = new net.sf.saxon.TransformerFactoryImpl();
		this.transformer = tFactory.newTransformer(new javax.xml.transform.stream.StreamSource(xsltStream));
	}
	
	public byte[] convert(byte[] inputContent) throws URISyntaxException, TransformerException{
		ByteArrayOutputStream result = new ByteArrayOutputStream();
        this.transformer.transform(new javax.xml.transform.stream.StreamSource(new ByteArrayInputStream(inputContent)), new javax.xml.transform.stream.StreamResult(result));
        return result.toByteArray();
    }
}
