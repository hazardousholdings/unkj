package com.hazardousholdings.unkj;

import com.hazardousholdings.unkj.db.JDBCConnectInfo;
import org.dom4j.*;
import org.dom4j.io.SAXReader;

import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

public class BootstrapConfiguration {

	private JDBCConnectInfo dbInfo;

	public BootstrapConfiguration(Reader reader) {
		try {
			SAXReader xmlReader = new SAXReader();
			parseDocument(xmlReader.read(reader));
		} catch (DocumentException ex) {
			throw new RuntimeException("Failed to parse unkj configuration file", ex);
		}
	}

	private void parseDocument(Document configXml) {
		Map<String, String> namespaceUris = new HashMap<>();
		namespaceUris.put("unkj", "http://unkj.hazardousholdings.com/configuration/unkj");

		XPath unkjDBXpath = DocumentHelper.createXPath("/unkj:unkj-config/unkj:database");
		unkjDBXpath.setNamespaceURIs(namespaceUris);
		Element unkjDBElement = (Element) unkjDBXpath.selectSingleNode(configXml);
		this.dbInfo = parseConnectInfo(unkjDBElement);
	}

	private JDBCConnectInfo parseConnectInfo(Element element) {
		String connectString = element.element("connection-string").getTextTrim();
		String username = element.element("username").getTextTrim();
		String password = element.element("password").getTextTrim();

		return new JDBCConnectInfo(org.postgresql.Driver.class.getName(), "jdbc:postgresql://" + connectString, username, password);
	}

	public JDBCConnectInfo getDBInfo() {
		return this.dbInfo;
	}
}
