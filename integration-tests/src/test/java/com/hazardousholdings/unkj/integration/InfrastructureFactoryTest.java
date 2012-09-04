package com.hazardousholdings.unkj.integration;

import com.hazardousholdings.unkj.Infrastructure;
import com.hazardousholdings.unkj.TestInfrastructureFactory;
import com.hazardousholdings.unkj.config.Configuration;
import org.junit.Test;

public class InfrastructureFactoryTest {

	@Test
	public void testFactoryInit() {
		Infrastructure infrastructure = TestInfrastructureFactory.get();
		Configuration config = infrastructure.getConfiguration();
	}

}
