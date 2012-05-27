package com.hazardousholdings.unkj;

import com.google.inject.AbstractModule;
import com.hazardousholdings.unkj.config.Configuration;

import java.io.File;

public class UnkJGuiceModule extends AbstractModule {

	private Infrastructure infrastructure;

	public UnkJGuiceModule(File bootstrapConfigFile, Version appVersion) {
		this.infrastructure = InfrastructureFactory.init(bootstrapConfigFile, appVersion);
	}

	@Override
	protected void configure() {
		bind(Infrastructure.class).toInstance(this.infrastructure);
		bind(Configuration.class).toInstance(this.infrastructure.getConfiguration());
	}
}
