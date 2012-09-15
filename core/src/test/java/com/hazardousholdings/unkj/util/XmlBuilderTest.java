package com.hazardousholdings.unkj.util;

import junit.framework.Assert;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.junit.Test;

import java.util.List;

public class XmlBuilderTest {

	@Test
	public void rootNode() {
		final String rootElementName = "root";
		
		XmlBuilder builder = new XmlBuilder(rootElementName);
		Document builtDocument = builder.getDocument();
		
		Assert.assertEquals(rootElementName, builtDocument.getRootElement().getName());
		Assert.assertEquals(0, builtDocument.getRootElement().elements().size());
	}
	
	@Test
	public void allSiblings() {
		final String levelPrefix = "sibling";
		final int width = 4;
		
		XmlBuilder builder = new XmlBuilder("root");
		
		for(int i = 0; i < width; i++) {
			builder.startElement(levelPrefix + i);
			builder.endElement();
		}
		
		Document builtDocument = builder.getDocument();
		Element root = builtDocument.getRootElement();
		
		List<?> children = root.elements();
		Assert.assertEquals(width, children.size());
		
		for(int i = 0; i < width; i++) {
			Assert.assertNotNull(children.get(i));
			Assert.assertEquals(levelPrefix + i, ((Node) children.get(i)).getName());
		}
	}
	
	@Test
	public void allNested() {
		final String levelPrefix = "nested";
		final int depth = 4;
		
		XmlBuilder builder = new XmlBuilder("root");
		
		for(int i = 0; i < depth; i++) {
			builder.startElement(levelPrefix + i);
		}
		
		for(int i = depth - 1; i >= 0; i--) {
			builder.endElement();
		}
		
		Document builtDocument = builder.getDocument();
		Element root = builtDocument.getRootElement();
		
		for(int i = 0; i < depth; i++) {
			List<?> children = root.elements();
			Assert.assertEquals(1, children.size());
			Assert.assertNotNull(children.get(0));
			Assert.assertEquals(levelPrefix + i, ((Node) children.get(0)).getName());
			
			root = (Element) children.get(0);
		}
	}
	
	@Test
	public void tooManyCloses() {
		XmlBuilder builder = new XmlBuilder("root");
		Document builtDocument = builder.getDocument();
		
		builder.startElement("ele");
		builder.endElement();
		builder.endElement();
		builder.startElement("ment");
		builder.endElement();
		
		Assert.assertEquals(2, builtDocument.getRootElement().elements().size());
	}
	
	@Test
	public void testAddAttribute() {
		XmlBuilder builder = new XmlBuilder("root");
		Document builtDocument = builder.getDocument();
		
		final String name = "name";
		final String value = "value";
		builder.addAttribute(name, value);
		
		Assert.assertEquals(1, builtDocument.getRootElement().attributeCount());
		Assert.assertEquals(value, builtDocument.getRootElement().attributeValue(name));		
	}
	
}
