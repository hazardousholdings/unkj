package com.hazardousholdings.unkj.install;

import com.hazardousholdings.unkj.BootstrapConfiguration;
import com.hazardousholdings.unkj.Infrastructure;

public class UnkJInstaller {

	private BootstrapConfiguration bootstrapConfig;
	private ApplicationConfiguration appConfig;

	public UnkJInstaller(BootstrapConfiguration bootstrapConfig, ApplicationConfiguration appConfig) {
		this.bootstrapConfig = bootstrapConfig;
		this.appConfig = appConfig;
	}

	public BootstrapConfiguration getBootstrapConfig() {
		return bootstrapConfig;
	}

	public ApplicationConfiguration getAppConfig() {
		return appConfig;
	}

	public void init() {
		initUnkJDB(this.bootstrapConfig);
		Infrastructure infrastructure = new Infrastructure(this.bootstrapConfig);

		initConfig(infrastructure, this.appConfig);
	}

	protected void initUnkJDB(BootstrapConfiguration bootstrapConfig) {
		DatabaseCreator databaseCreator = new DatabaseCreator(bootstrapConfig);
		databaseCreator.create();
	}

	protected void initConfig(Infrastructure infrastructure, ApplicationConfiguration appConfig) {
		ConfigLoader configLoader = new ConfigLoader(infrastructure);
		configLoader.loadDefaults();
		configLoader.loadFromFile(appConfig);
	}

}
