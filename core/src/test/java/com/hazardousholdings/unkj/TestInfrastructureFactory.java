package com.hazardousholdings.unkj;

public class TestInfrastructureFactory {

	private static Infrastructure INFRASTRUCTURE;

	public synchronized static Infrastructure get() {
		if(INFRASTRUCTURE == null) {
			InfrastructureFactory factory = new InfrastructureFactory(new TestBootstrapConfiguration());
			Infrastructure infrastructure = factory.bootstrap();
			factory.initDefaults(infrastructure);
			factory.initFromConfig(infrastructure);
			INFRASTRUCTURE = infrastructure;
		}

		return INFRASTRUCTURE;
	}

	public static synchronized void reset() {
		INFRASTRUCTURE = null;
	}

}
