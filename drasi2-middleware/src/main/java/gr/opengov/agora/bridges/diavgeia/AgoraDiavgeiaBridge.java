package gr.opengov.agora.bridges.diavgeia;

import gr.opengov.agora.bridges.diavgeia.domain.DiavgeiaDeletedDecision;
import gr.opengov.agora.bridges.diavgeia.domain.DiavgeiaOdeMember;
import gr.opengov.agora.bridges.diavgeia.domain.DiavgeiaOrganization;
import gr.opengov.agora.bridges.diavgeia.domain.DiavgeiaSigner;
import gr.opengov.agora.bridges.diavgeia.domain.DiavgeiaUnit;
import gr.opengov.agora.bridges.diavgeia.domain.DiavgeiaUser;
import gr.opengov.agora.bridges.diavgeia.domain.IDiavgeiaDecision;
import gr.opengov.agora.bridges.diavgeia.domain.IDiavgeiaDeletedDecision;
import gr.opengov.agora.domain.AuthenticationProfile;
import gr.opengov.agora.domain.IOrganization;
import gr.opengov.agora.domain.IPublicOrganizationDecision;
import gr.opengov.agora.domain.Organization;
import gr.opengov.agora.exceptions.DecisionNotFoundException;
import gr.opengov.agora.exceptions.DiavgeiaConnectionException;
import gr.opengov.agora.exceptions.InvalidDiavgeiaDecisionTypeException;
import gr.opengov.agora.exceptions.OdeNotFoundException;
import gr.opengov.agora.security.IAccessControl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.sql.DataSource;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.context.CurrentSessionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;

public class AgoraDiavgeiaBridge implements IAgoraDiavgeiaBridge {
	private static final Logger logger = LoggerFactory.getLogger(AgoraDiavgeiaBridge.class);
	private IDiavgeiaConverter converter;
	private IAccessControl accessControl;
	protected SessionFactory sessionFactoryRw;
	protected SessionFactory sessionFactoryRo;

	public void setConverter( IDiavgeiaConverter converter ) {
		this.converter = converter;
	}
	
	public void setAccessControl(IAccessControl accessControl) {
		this.accessControl = accessControl;
	}
	
	public void setSessionFactoryRw( SessionFactory sessionFactoryRw ) {
		this.sessionFactoryRw = sessionFactoryRw;
	}
	
	public void setSessionFactoryRo( SessionFactory sessionFactoryRo ) {
		this.sessionFactoryRo = sessionFactoryRo;
	}
	
	private Session sessionReadWrite() {
		return sessionFactoryRw.getCurrentSession();
	}
	
	private Session sessionReadOnly() {
		return sessionFactoryRo.getCurrentSession();
	}	

	/**
	 * Check whether the specified ADA code exists in  ID is valid.
	 * @param ada
	 * @return
	 */
	@Override	
	public boolean isValidAda( String ada ) {
		logger.debug( "Checking for ada:" + ada );
		if ( ada == null ) return false;	
		Long count = (Long)sessionReadOnly().createQuery( "select count(*) from DiavgeiaDecision where ada=:ada and status='active'").setParameter( "ada", ada).uniqueResult();
		logger.debug( "Count: " + count );
		return count > 0;
	}	
	
	/**
	 * Check whether the specified ADA code exists in  ID is valid for the specified type of decision.
	 * @param ada
	 * @param decisionTypeId
	 * @return
	 */
	@Override	
	public boolean isValidAda( String ada, Integer decisionType ) {
		logger.debug( "Checking for ada:" + ada + " for decision type:" + decisionType);
		if ( ada == null ) return false;	
		Long count = (Long)sessionReadOnly().createQuery( "select count(*) from DiavgeiaDecision where ada=:ada and eidos_apofasis=:eidos_apofasis and status='active'").setParameter( "ada", ada).setParameter( "eidos_apofasis", decisionType).uniqueResult();
		logger.debug( "Count: " + count );
		return count > 0;
	}		
	
	/**
	 * Check whether the specified organization ID is valid.
	 * @param organizationId
	 * @return
	 */
	@Override
	public boolean isValidOrganization( Integer organizationId ) {
		if ( organizationId == null ) return false;
		Long count = (Long)sessionReadOnly().createQuery( "select count(*) from DiavgeiaOdeMember where organizationId=:orgId and status='1'").setParameter( "orgId", organizationId ).uniqueResult();
		return count > 0;
	}	
	
	/** 
	 * Check whether a unit ID is valid for the specified organization.
	 * @see gr.opengov.agora.bridges.diavgeia.IAgoraDiavgeiaBridge#isValidUnitForOrganization(int, int)
	 */
	@Override
	public boolean isValidUnitForOrganization( Integer unitId, Integer organizationId ) {
		if ( unitId == null ) return true;		
		HibernateTemplate template = new HibernateTemplate(sessionFactoryRo);
		DiavgeiaUnit unit = template.get( DiavgeiaUnit.class, unitId );
		if ( unit == null ) return false;
		return unit.getOrganization().getId().equals( organizationId );
	}
	
	/**
	 * Check whether signers are valid for the specified organization.
	 */
	@Override
	public boolean areValidSignersForOrganization( List<Integer> signersId, Integer organizationId, Set<Integer> invalidIndex ) {
		if ( invalidIndex != null ) invalidIndex.clear();
		HibernateTemplate template = new HibernateTemplate(sessionFactoryRo);		
		List<DiavgeiaOrganization> orgs = template.find( "from DiavgeiaOrganization where id=?", organizationId );		
		Set<Integer> validSignersSet = new HashSet<Integer>();
		if ( orgs.size() > 0 ) {
			for ( DiavgeiaSigner signer: orgs.get(0).getSigners() ) {
				validSignersSet.add( signer.getId() );
			}
		}
		
		for ( int i = 0; i < signersId.size(); i++ ) {
			Integer id = signersId.get( i );
			if ( !validSignersSet.contains( id ) ) {
				if ( invalidIndex != null ) invalidIndex.add( i );
				else return false;
			}
		}
		return invalidIndex != null ? invalidIndex.isEmpty() : true;		
	}
	
	@Override
	public boolean areValidSignersForOrganization( List<Integer> signersId, Integer organizationId ) {
		return areValidSignersForOrganization(signersId, organizationId, null);
	}

	@Override
	public String getAdaOfDocument(String documentId) {
		// TODO change this in order to get the ada of each document from diavgeia. Ask about what is needed to send to diavgeia in order to get the ada code
		return "4Α12ΕΣ-1";
	}

	@Override
	public AuthenticationProfile getAuthenticationProfile(Integer userId) {
		try{
			HibernateTemplate template = new HibernateTemplate(sessionFactoryRo);
			DiavgeiaUser user = template.get( DiavgeiaUser.class, userId );
			if ( user == null ) return null;
			AuthenticationProfile profile = new AuthenticationProfile();
			profile.setUserId( userId );
			profile.setUserName( user.getUsername() );		
			profile.setFirstName( user.getFirstname() );
			profile.setLastName( user.getLastname() );
			profile.setEmail(user.getEmail());
			profile.setRole( user.getRealm() );
			
			/*
			 *  Get organizations for user. 
			 *  Currently there is only one organization per user.
			 */
			Integer orgId = user.getOrganization().getId(); //TODO: check if there is returned an empty set for organizations of user: ode == null
			DiavgeiaOdeMember ode = (DiavgeiaOdeMember)sessionReadOnly().createQuery( "from DiavgeiaOdeMember where organizationId=:orgId and status=1" ).setParameter( "orgId", orgId ).uniqueResult();
			Object organization = sessionReadOnly().createQuery( "from DiavgeiaOrganization where id=:organizationId)" ).setParameter( "organizationId", Integer.valueOf(orgId) ).uniqueResult();
			
			if (ode != null){
				IOrganization org = new Organization();
				org.setIdRef( orgId );
				org.setName( user.getOrganization().getName() );
				
				org.setAddress( ode.getOrganizationAddressRoad() );
				org.setAddressNo( ode.getOrganizationAddressNumber() );
				org.setAddressPostal( ode.getOrganizationAddressPostalCode() );
				org.setAfm( ode.getOrganizationAfm() );
				org.setOrganizationType( ode.getOrganization().getType() );
				List<IOrganization> orgs = new ArrayList<IOrganization>();
				orgs.add( org );		
				profile.setOdeMember( orgs );
			}
			else{
				throw new OdeNotFoundException("Unable to get user's ode");
			}
			
			return profile;
		}catch (HibernateException e){
			throw new DiavgeiaConnectionException(e.getMessage());
		}
	}
	
	@Override
	public List<DiavgeiaOdeMember> getOdeMembers(String orgId, boolean includeSupervisedMembers, boolean getUnits, boolean getSigners, String unitId){
		List<DiavgeiaOdeMember> odeMembers;
		
		if ((orgId != null) && (orgId.trim().length()>0) ){
			if (includeSupervisedMembers){
				//getting supervised members
				odeMembers = sessionReadOnly().createQuery( "from DiavgeiaOdeMember where status=1 and (organizationId<>0 and organizationId<>:organizationId and parentOrganizationId=:organizationId) order by id" ).setParameter( "organizationId", Integer.valueOf(orgId) ).list();
				//getting supervisor member
				odeMembers.add((DiavgeiaOdeMember)sessionReadOnly().createQuery( "from DiavgeiaOdeMember where status=1 and organizationId=:organizationId order by id" ).setParameter( "organizationId", Integer.valueOf(orgId) ).uniqueResult());
			}
			else{
				odeMembers = sessionReadOnly().createQuery( "from DiavgeiaOdeMember where status=1 and organizationId=:organizationId order by id" ).setParameter( "organizationId", Integer.valueOf(orgId) ).list();
			}
		}
		else{
			odeMembers = sessionReadOnly().createQuery( "from DiavgeiaOdeMember where status=1 and organizationId<>0 order by id" ).list();
		}
		
		if (getSigners){
			for (DiavgeiaOdeMember member:odeMembers){
				for (DiavgeiaSigner signer:member.getOrganization().getSigners()){
				}
			}			
		}
		
		if (getUnits){
			for (DiavgeiaOdeMember member:odeMembers){
				for (DiavgeiaUnit unit:member.getOrganization().getUnits()){
					if ((unitId != null) && (unitId.trim().length() > 0) ){
						for (DiavgeiaSigner signer:unit.getSigners()){
						}
					}
				}
			}
		}
		
		return odeMembers;
	}
	
	@Override
	public DiavgeiaOdeMember getOde(String id){
		if ((id == null) || (id.trim().length() == 0) ){
			return null;
		}
		DiavgeiaOdeMember odeMember;
		
		odeMember = (DiavgeiaOdeMember)sessionReadOnly().createQuery( "from DiavgeiaOdeMember where status=1 and organizationId=:organizationId order by id" ).setParameter( "organizationId", Integer.valueOf(id) ).uniqueResult();

			for (DiavgeiaSigner signer:odeMember.getOrganization().getSigners()){
			}
			for (DiavgeiaUnit unit:odeMember.getOrganization().getUnits()){
//				for (DiavgeiaSigner signer:unit.getSigners()){
//					
//				}
			}
		
		return odeMember;
	}	
	
	@Override
	public DiavgeiaOrganization getOdeWithUnits(String id, Integer status){
		if ((id == null) || (id.trim().length() == 0) ){
			return null;
		}
		
		DiavgeiaOrganization organization;
		
		if (status == null){
			organization = (DiavgeiaOrganization)sessionReadOnly().createQuery( "from DiavgeiaOrganization where id=:organizationId) order by id" ).setParameter( "organizationId", Integer.valueOf(id) ).uniqueResult();
		}
		else{
			Object odeMember = sessionReadOnly().createQuery( "from DiavgeiaOdeMember where status=:status and organizationId=:organizationId order by id" ).setParameter("status", status.toString()).setParameter( "organizationId", Integer.valueOf(id) ).uniqueResult();
			if (odeMember == null){
				organization = null;
			}
			else{
				organization = (DiavgeiaOrganization)sessionReadOnly().createQuery( "from DiavgeiaOrganization where id=:organizationId) order by id" ).setParameter( "organizationId", Integer.valueOf(id) ).uniqueResult();
			}
		}
		
		for (DiavgeiaSigner signer:organization.getSigners()){
			
		}		
		for (DiavgeiaUser user:organization.getUsers()){
			
		}
		for (DiavgeiaUnit unit:organization.getUnits()){
			
		}
		return organization;
	}	
	
	@Override
	public DiavgeiaUser getDiavgeiaUser(Integer userId) {
		HibernateTemplate template = new HibernateTemplate(sessionFactoryRo);
		return template.get( DiavgeiaUser.class, userId );
	}	

	@Override
	// TODO: Needs to save file
	// TODO: Needs access control
	public void saveDecision(IPublicOrganizationDecision decision) throws InvalidDiavgeiaDecisionTypeException {
		IDiavgeiaDecision obj = converter.getDiavgeiaDecision( decision, accessControl.getClient() );
		sessionReadWrite().save( obj );
	}

	@Override
	// TODO: Needs to update file
	// TODO: Needs access control
	public void updateDecision(IPublicOrganizationDecision decision) throws InvalidDiavgeiaDecisionTypeException, DecisionNotFoundException {		
		IDiavgeiaDecision saved = (IDiavgeiaDecision)sessionReadWrite().createQuery( "from DiavgeiaDecision where status='agora' and ada=:ada" ).setParameter( "ada", decision.getId() ).uniqueResult();
		if ( saved == null ) throw new DecisionNotFoundException( decision.getId() );
		logger.debug( "Found existing decision, subject: " + saved.getSubject() );		
		converter.convertDiavgeiaDecision( decision, accessControl.getClient(), saved );
		logger.debug( "Updating with subject: " + saved.getSubject() );
		sessionReadWrite().update( saved );
		sessionReadWrite().flush();
	}

	@Override
	// TODO: Needs access control. Do NOT delete file
	public void deleteDecision(String ada) {
		logger.debug( "Deleting decision with ada: " + ada );
		IDiavgeiaDecision saved = (IDiavgeiaDecision)sessionReadWrite().createQuery( "from DiavgeiaDecision where status='agora' and ada=:ada" ).setParameter( "ada", ada ).uniqueResult();
		if ( saved == null ) throw new DecisionNotFoundException( ada );
		IDiavgeiaDeletedDecision deleted = new DiavgeiaDeletedDecision();
		logger.debug( "Cloning" );
		BeanUtils.copyProperties( saved, deleted );
		deleted.setId( null );
		deleted.setDeletionReason( "Deleted through Agora" );
		deleted.setDecisionDate( GregorianCalendar.getInstance() );
		deleted.setDeletionUser( accessControl.getClient().getName() );
		logger.debug( "Saving deleted decision" );
		sessionReadWrite().save( deleted );
		logger.debug( "Deleting original decision" );
		sessionReadWrite().delete( saved );
		sessionReadWrite().flush();
	}
}
