package gr.opengov.agora.test;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration( "classpath:spring/root-context.xml" )

public class ProjectBuildTest {
	private static final Logger logger = LoggerFactory.getLogger(ProjectBuildTest.class);

	@Value( "${mail.prefix}" ) String mailPrefix;
	@Value( "${build.number}" ) String buildNumber;
	@Value( "${build.timestamp}" ) String buildTimestamp;
	@Value( "${mail.from.name}" ) String mailFromName;	
	
	@Test
	public void testFilter() {
		logger.debug( "Mail prefix: " + mailPrefix );
		assertNotNull( mailPrefix );
		logger.debug( "Build number: " + buildNumber );
		assertNotNull( buildNumber );
		logger.debug( "Build timestamp: " + buildTimestamp );
		assertNotNull( buildTimestamp );
	}
	
	@Test
	public void testUtf8() {
		logger.debug( "Mail from: " + mailFromName );
	}
	
}
