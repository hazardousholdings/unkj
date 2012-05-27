package com.hazardousholdings.unkj;

import com.hazardousholdings.unkj.db.JDBCConnectInfo;
import org.dom4j.*;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.*;

class BootstrapConfiguration {

	private Version version;
	private JDBCConnectInfo dbInfo;
	private Map<String, String> config = new HashMap<>();
	private Map<String, JDBCConnectInfo> dbConfig = new HashMap<>();
	private List<String> groups = new ArrayList<>();

	public BootstrapConfiguration(File configFile, Version version) {
		this.version = version;
		try {
			SAXReader reader = new SAXReader();
			parseDocument(reader.read(configFile));
		} catch (DocumentException ex) {
			throw new RuntimeException("Failed to parse configuration file (" + configFile.getAbsolutePath() + ")", ex);
		}
	}

	BootstrapConfiguration(Document configXml, Version version) {
		this.version = version;
		parseDocument(configXml);
	}

	private void parseDocument(Document configXml) {
		Map<String, String> namespaceUris = new HashMap<>();
		namespaceUris.put("unkj", "http://unkj.hazardousholdings.com/configuration");

		XPath unkjDBXpath = DocumentHelper.createXPath("/unkj:application/unkj:unkj-database");
		unkjDBXpath.setNamespaceURIs(namespaceUris);
		Element unkjDBElement = (Element) unkjDBXpath.selectSingleNode(configXml);
		this.dbInfo = parseConnectInfo(unkjDBElement);

		XPath dbXPath = DocumentHelper.createXPath("/unkj:application/unkj:configuration/unkj:database");
		dbXPath.setNamespaceURIs(namespaceUris);
		List dbNodes = dbXPath.selectNodes(configXml);
		for(Object dbNode : dbNodes) {
			Element dbElement = (Element) dbNode;
			this.dbConfig.put(dbElement.attribute("key").getValue(), parseConnectInfo(dbElement));
		}

		XPath configXPath = DocumentHelper.createXPath("/unkj:application/unkj:configuration/unkj:config");
		configXPath.setNamespaceURIs(namespaceUris);
		List configNodes = configXPath.selectNodes(configXml);
		for(Object configNode : configNodes) {
			Element configElement = (Element) configNode;
			this.config.put(configElement.attribute("key").getValue(), configElement.getTextTrim());
		}

		XPath groupsXPath = DocumentHelper.createXPath("/unkj:application/unkj:configuration/unkj:groups/unkj:group");
		groupsXPath.setNamespaceURIs(namespaceUris);
		List groupNodes = groupsXPath.selectNodes(configXml);
		for(Object groupNode : groupNodes) {
			Element groupElement = (Element) groupNode;
			this.groups.add(groupElement.getTextTrim());
		}
	}

	private JDBCConnectInfo parseConnectInfo(Element element) {
		String className = element.element("class-name").getTextTrim();
		String connectString = element.element("connection-string").getTextTrim();
		String username = element.element("username").getTextTrim();
		String password = element.element("password").getTextTrim();

		return new JDBCConnectInfo(className, connectString, username, password);
	}

	public Version getVersion() {
		return this.version;
	}

	public JDBCConnectInfo getDBInfo() {
		return this.dbInfo;
	}

	public Map<String, String> getConfigEntries() {
		return Collections.unmodifiableMap(config);
	}

	public Map<String, JDBCConnectInfo> getDBConfigEntries() {
		return Collections.unmodifiableMap(dbConfig);
	}

	public List<String> getGroups() {
		return Collections.unmodifiableList(groups);
	}
}
