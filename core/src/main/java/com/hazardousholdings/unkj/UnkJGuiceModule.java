package com.hazardousholdings.unkj;

import com.google.inject.AbstractModule;
import com.hazardousholdings.unkj.config.Configuration;

public class UnkJGuiceModule extends AbstractModule {

	private Infrastructure infrastructure;

	public UnkJGuiceModule() {
		this.infrastructure = new Infrastructure();
	}

	@Override
	protected void configure() {
		bind(Infrastructure.class).toInstance(this.infrastructure);
		bind(Configuration.class).toInstance(this.infrastructure.getConfiguration());
	}
}
