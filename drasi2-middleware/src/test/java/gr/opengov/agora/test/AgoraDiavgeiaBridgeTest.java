package gr.opengov.agora.test;

import static org.junit.Assert.*;
import gr.opengov.agora.bridges.diavgeia.AgoraDiavgeiaBridge;
import gr.opengov.agora.bridges.diavgeia.IAgoraDiavgeiaBridge;
import gr.opengov.agora.domain.AuthenticationProfile;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration( "classpath:spring/root-context.xml" )
public class AgoraDiavgeiaBridgeTest {
	private static final Logger logger = LoggerFactory.getLogger(AgoraDiavgeiaBridgeTest.class);
//	@Resource( name="dataSourceDiavgeia")
//	private DataSource dataSourceDiavgeia;
	@Resource( name="diavgeiaBridge")
	private IAgoraDiavgeiaBridge bridge;
	private static int USER_ID_366_ADMIN = 17;
	
	@Test
	public void testIsValidOrganization() {
		logger.debug( "Testing valid organization" );
		assertTrue( bridge.isValidOrganization( 12 ) );
	}
	
	@Test
	public void testIsInvalidOrganization() {
		logger.debug( "Testing invalid organization" );
		assertFalse( bridge.isValidOrganization( -1 ) );
	}
	
	@Test
	public void testIsValidUnitForOrganizationNullUnit() {		
		logger.debug( "Testing isValidUnitForOrganization" );
		logger.debug( "...null unit" );
		assertTrue( bridge.isValidUnitForOrganization(null, 366) );
		logger.debug( "...valid unit for valid organization" );
		assertTrue( bridge.isValidUnitForOrganization( 172, 366) );
		logger.debug( "...invalid unit for valid organization" );
		assertFalse( bridge.isValidUnitForOrganization( 10, 366) );
		logger.debug( "..invalid organization" );
		assertFalse( bridge.isValidUnitForOrganization( 10,  919191 ) );
	}
	
	private List<Integer> getValidSignersForOrg12() {
		List<Integer> signers = new ArrayList<Integer>();
		signers.add( 1 );
		signers.add( 2 );
		signers.add( 3 );
		return signers;
	}

	private List<Integer> getValidAndInvalidSignersForOrg12() {
		List<Integer> signers = new ArrayList<Integer>();
		signers.add( 1 );
		signers.add( 2 );
		signers.add( 6000 );
		return signers;
	}
	
	@Test
	public void testIsValidAda() {
		assertTrue( bridge.isValidAda( "4Α1ΣΕΣ-Ι") );
	}
	
	@Test
	public void testIsInvalidAdaNonActive() {
		assertTrue( !bridge.isValidAda( "4Α1ΣΕΣ-3") );
	}
	
	@Test
	public void testAreValidSignersForOrganizationAllValid() {
		List<Integer> signers = getValidSignersForOrg12();
		boolean valid = bridge.areValidSignersForOrganization( signers, 12 );
		assertTrue( valid );		
	}

	@Test
	public void testAreValidSignersForOrganizationSomeInvalid() {
		List<Integer> signers = getValidAndInvalidSignersForOrg12();
		boolean valid = bridge.areValidSignersForOrganization( signers, 12 );
		assertFalse( valid );		
	}
	
	@Test
	public void testAreValidSignersForOrganizationGetInvalid() {
		Set<Integer> invalidIndex = new HashSet<Integer>();
		List<Integer> signers = getValidAndInvalidSignersForOrg12();
		boolean valid = bridge.areValidSignersForOrganization( signers, 12, invalidIndex );
		assertFalse( valid );
		assertTrue( invalidIndex.size() == 1 && invalidIndex.contains( 2 ) );
	}
	
	@Test
	public void testAuthenticationProfileInvalidUser() {
		assertEquals( bridge.getAuthenticationProfile( -1 ), null );
	}
	
	@Test
	public void testAuthenticationProfileValidUser() {
		AuthenticationProfile profile = bridge.getAuthenticationProfile( USER_ID_366_ADMIN );
		logger.debug( "Profile for 366_admin: " + profile.toString() );
		assertEquals( profile.getOdeMember().get(0).getIdRef(), 366 );
	}
	
}
