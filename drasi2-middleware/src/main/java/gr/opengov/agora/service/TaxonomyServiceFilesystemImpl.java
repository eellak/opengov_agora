package gr.opengov.agora.service;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.io.FileUtils;
import org.hibernate.ObjectNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.oxm.UnmarshallingFailureException;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import gr.opengov.agora.bridges.diavgeia.IAgoraDiavgeiaBridge;
import gr.opengov.agora.bridges.diavgeia.domain.DiavgeiaOdeMember;
import gr.opengov.agora.bridges.diavgeia.domain.DiavgeiaOrganization;
import gr.opengov.agora.bridges.diavgeia.domain.DiavgeiaSigner;
import gr.opengov.agora.bridges.diavgeia.domain.DiavgeiaUnit;
import gr.opengov.agora.bridges.diavgeia.domain.IOdeMemberTaxonomyItem;
import gr.opengov.agora.bridges.diavgeia.domain.IUnitTaxonomyItem;
import gr.opengov.agora.bridges.diavgeia.domain.OdeMemberTaxonomyItem;
import gr.opengov.agora.bridges.diavgeia.domain.UnitTaxonomyItem;
import gr.opengov.agora.cms.ITaxonomy;
import gr.opengov.agora.cms.ITaxonomyItem;
import gr.opengov.agora.cms.Taxonomy;
import gr.opengov.agora.cms.TaxonomyItem;
import gr.opengov.agora.converters.ICommonsOXMConverter;
import gr.opengov.agora.exceptions.DuplicateOdeFoundException;
import gr.opengov.agora.exceptions.DuplicateTaxonomyFoundException;
import gr.opengov.agora.exceptions.InvalidTaxonomyException;
import gr.opengov.agora.exceptions.OdeNotActiveException;
import gr.opengov.agora.exceptions.TaxonomyNotFoundException;
import gr.opengov.agora.exceptions.TaxonomyPathNotFoundException;
import gr.opengov.agora.model.TaxonomyOXM;
import gr.opengov.agora.util.Constants;

public class TaxonomyServiceFilesystemImpl implements ITaxonomyService {	
	private static final Logger logger = LoggerFactory.getLogger(TaxonomyServiceFilesystemImpl.class);
	
	private IAgoraDiavgeiaBridge diavgeiaBridge;
	
	public void setDiavgeiaBridge(IAgoraDiavgeiaBridge diavgeiaBridge) {
		this.diavgeiaBridge = diavgeiaBridge;
	}
	
	File taxonomyPath;
	Jaxb2Marshaller marshaller;
//	@Autowired
	private ICommonsOXMConverter commonsConverter;
	Map<String,ITaxonomy> taxonomyMap = new HashMap<String, ITaxonomy>();
		
	public void setTaxonomyPath( File taxonomyPath ) {
		this.taxonomyPath = taxonomyPath;
	}
	
	public void setMarshaller(Jaxb2Marshaller marshaller) {
		this.marshaller = marshaller;
	}
	
	public void setCommonsConverter(ICommonsOXMConverter commonsConverter) {
		this.commonsConverter = commonsConverter;
	}
	
	/**
	 * This is supposed to run only once. Filesystem taxonomies CANNOT change while the service is running.
	 */
	private void loadTaxonomies() throws DuplicateTaxonomyFoundException, InvalidTaxonomyException {
		logger.info( "Loading taxonomies." );
		taxonomyMap.clear();
		if (taxonomyPath == null) throw new TaxonomyPathNotFoundException();
		File[] files = taxonomyPath.listFiles( new FilenameFilter() {			
			@Override
			public boolean accept(File dir, String name) {
				if ( name.endsWith( ".xml" ) ) return true;
				else return false;
			}
		});
		for ( File file: files ) {
			String basename = FileUtils.basename( file.getName() );
			basename = basename.substring( 0, basename.length() - 1 );
			Source source = new StreamSource( file );
			try {
				Object obj = marshaller.unmarshal( source );
				if ( ! ( obj instanceof TaxonomyOXM ) ) continue;
				ITaxonomy taxonomy = commonsConverter.getTaxonomy( (TaxonomyOXM)obj );
				
				logger.debug( "Adding taxonomy: " + basename );
				if ((taxonomy.getName() == null) || (taxonomy.getName().trim().length() == 0)) throw new InvalidTaxonomyException();
				if (taxonomyMap.containsKey(taxonomy.getName())) throw new DuplicateTaxonomyFoundException();
				taxonomyMap.put( taxonomy.getName(), taxonomy );
			}
			catch ( UnmarshallingFailureException e ) {
				logger.debug( "Ignoring invalid XML file: " + basename );
			}			
		}
	}
	/**
	 * 
	 * @param members
	 * @param alreadyRetrieved
	 * @param member
	 * @param includeSupervisedMembers
	 * @param includeUnits
	 * @param includeSigners
	 * @param unitId
	 * @return the member containing all its supervised members, units and signers, depending on the include* input params
	 */
	private IOdeMemberTaxonomyItem getMemberItem(List<DiavgeiaOdeMember> members, List<Integer> alreadyRetrieved, DiavgeiaOdeMember member, boolean includeSupervisedMembers, boolean includeUnits, boolean includeSigners, String unitId){
		IOdeMemberTaxonomyItem taxonomyItem = new OdeMemberTaxonomyItem(member.getOrganizationId().toString(), member.getOrganization().getName());
		taxonomyItem.setLatinName(member.getOrganizationLatinName());
		if (!alreadyRetrieved.contains(member.getId())){
				
			if (includeSupervisedMembers){
				ITaxonomy childTaxonomy = new Taxonomy();
				
				Iterator membersIterator = members.iterator();
				
				DiavgeiaOdeMember childMember = null;
				while (membersIterator.hasNext()){
					childMember = (DiavgeiaOdeMember)membersIterator.next();

					if ( childMember.getParentOrganizationId().equals(member.getOrganizationId()) && (!childMember.getParentOrganizationId().equals(childMember.getOrganizationId()))){
						IOdeMemberTaxonomyItem childTaxonomyItem = new OdeMemberTaxonomyItem(childMember.getOrganizationId().toString(), childMember.getOrganization().getName());
						childTaxonomyItem.setLatinName(childMember.getOrganizationLatinName());
						childTaxonomy.addItem(childTaxonomyItem);
						alreadyRetrieved.add(childMember.getId());
					}					
				}
				
				taxonomyItem.setTaxonomy(childTaxonomy);
			}
		
			
			if (includeUnits){				
				for (DiavgeiaUnit unit:member.getOrganization().getUnits()){
					if ( (unitId != null) && (unitId.trim().length() > 0) ){ //signers of unit asked
						if (unit.getId().toString().equals(unitId)){
							UnitTaxonomyItem unitItem = new UnitTaxonomyItem(unit.getId().toString(), unit.getName());
							unitItem.setSigners( unit.getSigners());
							taxonomyItem.addUnit( unitItem );
						}
					}
					else{
						taxonomyItem.addUnit( new UnitTaxonomyItem(unit.getId().toString(), unit.getName()) );
					}
				}
			}
			
			if ( (unitId != null) && (unitId.trim().length() > 0) ){
				for (DiavgeiaSigner signer:member.getOrganization().getSigners() ){
					if (signer.getUnitId().intValue() == 1000000){
						taxonomyItem.addSigner( signer );
					}
				}				
			}
			else
			{
				if (includeSigners){				
					for (DiavgeiaSigner signer:member.getOrganization().getSigners() ){
						taxonomyItem.addSigner( signer );
					}
				}
			}
			alreadyRetrieved.add(member.getId());
			return taxonomyItem;
		}
		else{
			return null;
		}
	
	}

	@Override
	public ITaxonomy getDiavgeiaOdeMembersTaxonomy(String orgId, boolean getUnits, boolean getSigners, String unitId){
		try{
			boolean includeSupervisedMembers = !(getUnits || getSigners);
			List<DiavgeiaOdeMember> members = diavgeiaBridge.getOdeMembers(orgId, includeSupervisedMembers, getUnits, getSigners, unitId);
			
			ITaxonomy membersTaxonomy = new Taxonomy();
			
			List<Integer> alreadyInserted = new ArrayList<Integer>();
			
			//get first level members with their supervised members
			for (DiavgeiaOdeMember member:members){
				if (member.getParentOrganizationId().equals(member.getOrganizationId())){
					IOdeMemberTaxonomyItem taxonomyItem = getMemberItem(members, alreadyInserted, member, includeSupervisedMembers, getUnits, getSigners, unitId);
					if (taxonomyItem != null){
						membersTaxonomy.addItem(taxonomyItem);
					}
				}
			}			
			
			//get second level (supervised) members
			for (DiavgeiaOdeMember member:members){
				if (!alreadyInserted.contains(member.getId())){
					IOdeMemberTaxonomyItem taxonomyItem = getMemberItem(members, alreadyInserted, member, includeSupervisedMembers, getUnits, getSigners, unitId);
					if (taxonomyItem != null){
						membersTaxonomy.addItem(taxonomyItem);
					}
				}
			}
		
			return membersTaxonomy;
		}catch (ObjectNotFoundException ex){
			throw new TaxonomyNotFoundException(ex.getMessage());
		}
		
	}

	@Override
	public List<String> getTaxonomyNames() {
		synchronized( taxonomyMap ) {
			if ( taxonomyMap.isEmpty() ) loadTaxonomies();
			return new ArrayList<String>( taxonomyMap.keySet() );
		}		
	}

	@Override	
	public ITaxonomy getSignersOfUnitTaxonomy(String name, String orgId, String unitId) {
		return getDiavgeiaOdeMembersTaxonomy(orgId, true, true, unitId);
	}	
	
	@Override
	public ITaxonomy getTaxonomy(String name, String itemId) {
		if (name.equals(Constants.ODATAXONOMYNAME)){
			return getDiavgeiaOdeMembersTaxonomy(itemId, false, false, null);
		}
		
		
		if (name.equals(Constants.UNITSTAXONOMYNAME)){
			return getDiavgeiaOdeMembersTaxonomy(itemId, true, false, null);
		}
		if (name.equals(Constants.SIGNERSTAXONOMYNAME)){
			return getDiavgeiaOdeMembersTaxonomy(itemId, false, true, null);
		}		
		
		synchronized( taxonomyMap ) {
			if ( taxonomyMap.isEmpty() ) loadTaxonomies();
			return taxonomyMap.get( name );
		}
	}

}
