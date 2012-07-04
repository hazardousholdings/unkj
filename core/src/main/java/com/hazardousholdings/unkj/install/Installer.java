package com.hazardousholdings.unkj.install;

import com.hazardousholdings.unkj.BootstrapConfiguration;
import com.hazardousholdings.unkj.Infrastructure;

import java.io.*;

public class Installer {

	public static void main(String[] args) throws IOException {
		File unkjConfigFile = new File(args[0]);
		File appConfigFile = new File(args[1]);

		try (Reader unkjConfigReader = new FileReader(unkjConfigFile);
			 Reader appConfigReader = new FileReader(appConfigFile)) {

			Installer installer = new Installer(new BootstrapConfiguration(unkjConfigReader), new ApplicationConfiguration(appConfigReader));
			installer.init();
		}
	}

	private BootstrapConfiguration bootstrapConfig;
	private ApplicationConfiguration appConfig;

	public Installer(BootstrapConfiguration bootstrapConfig, ApplicationConfiguration appConfig) {
		this.bootstrapConfig = bootstrapConfig;
		this.appConfig = appConfig;
	}

	public void init() {
		initDatabase(this.bootstrapConfig);
		Infrastructure infrastructure = new Infrastructure(this.bootstrapConfig);

		initConfig(infrastructure, this.appConfig);
	}

	protected void initDatabase(BootstrapConfiguration bootstrapConfig) {
		DatabaseCreator databaseCreator = new DatabaseCreator(bootstrapConfig);
		databaseCreator.create();
	}

	protected void initConfig(Infrastructure infrastructure, ApplicationConfiguration appConfig) {
		ConfigLoader configLoader = new ConfigLoader(infrastructure);
		configLoader.loadDefaults();
		configLoader.loadFromFile(appConfig);
	}

}
