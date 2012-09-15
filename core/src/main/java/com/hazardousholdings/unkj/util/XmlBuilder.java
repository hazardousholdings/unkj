package com.hazardousholdings.unkj.util;

import java.util.Stack;

import org.dom4j.Document;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;

public class XmlBuilder {

	private Document document;
	private Stack<Element> ancestors;
	
	public XmlBuilder(String rootElementName) {
		this.ancestors = new Stack<Element>();
		DocumentFactory factory = DocumentFactory.getInstance();
		this.document = factory.createDocument();
		
		Element root = this.document.addElement(rootElementName);
		this.ancestors.push(root);
	}
	
	public void startElement(String elementName) {
		Element element = this.ancestors.peek().addElement(elementName);
		this.ancestors.push(element);		
	}
	
	public void addAttribute(String name, Object value) {
		this.addAttribute(name, value.toString());
	}
	
	public void addAttribute(String name, String value) {
		this.ancestors.peek().addAttribute(name, value);
	}
	
	public void endElement() {
		if(this.ancestors.size() > 1) {
			this.ancestors.pop();
		}
	}
	
	public Document getDocument() {
		return this.document;
	}
	
}
