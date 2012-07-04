package com.hazardousholdings.unkj.install;

import com.hazardousholdings.unkj.db.JDBCConnectInfo;
import org.dom4j.*;
import org.dom4j.io.SAXReader;

import java.io.Reader;
import java.util.*;

public class ApplicationConfiguration {

	private Map<String, String> config = new HashMap<>();
	private Map<String, JDBCConnectInfo> dbConfig = new HashMap<>();
	private List<String> groups = new ArrayList<>();

	public ApplicationConfiguration(Reader reader) {
		try {
			SAXReader xmlReader = new SAXReader();
			parseDocument(xmlReader.read(reader));
		} catch (DocumentException ex) {
			throw new RuntimeException("Failed to parse application configuration file", ex);
		}
	}

	private void parseDocument(Document configXml) {
		Map<String, String> namespaceUris = new HashMap<>();
		namespaceUris.put("unkj", "http://unkj.hazardousholdings.com/configuration/app");

		XPath dbXPath = DocumentHelper.createXPath("/unkj:application-config/unkj:configuration/unkj:database");
		dbXPath.setNamespaceURIs(namespaceUris);
		List dbNodes = dbXPath.selectNodes(configXml);
		for(Object dbNode : dbNodes) {
			Element dbElement = (Element) dbNode;
			this.dbConfig.put(dbElement.attribute("key").getValue(), parseConnectInfo(dbElement));
		}

		XPath configXPath = DocumentHelper.createXPath("/unkj:pplication-config/unkj:configuration/unkj:config");
		configXPath.setNamespaceURIs(namespaceUris);
		List configNodes = configXPath.selectNodes(configXml);
		for(Object configNode : configNodes) {
			Element configElement = (Element) configNode;
			this.config.put(configElement.attribute("key").getValue(), configElement.getTextTrim());
		}

		XPath groupsXPath = DocumentHelper.createXPath("/unkj:pplication-config/unkj:configuration/unkj:groups/unkj:group");
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
