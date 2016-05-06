package gr.opengov.agora.test;

import static org.junit.Assert.assertTrue;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DevelopmentTest {
	private static final Logger logger = LoggerFactory.getLogger(DevelopmentTest.class);

	@Test
	public void testCompareDates() {
		Calendar now = GregorianCalendar.getInstance();
		now.add( Calendar.DAY_OF_YEAR, 1 );
		assertTrue( now.compareTo( GregorianCalendar.getInstance() ) > 0 );
	}
	
	@Test
	public void testDateLocale() {
		Calendar now = GregorianCalendar.getInstance();		
		Locale locale = new Locale( "el", "GR" );
		DateFormat df = new SimpleDateFormat( "EEE, d MMM yyyy HH:mm:ss Z", locale );		
		logger.info( "Local date: " + df.format( now.getTime() ) );
	}
}
