 package gr.opengov.agora.web;

import gr.opengov.agora.cms.ITaxonomy;
import gr.opengov.agora.converters.ICommonsOXMConverter;
import gr.opengov.agora.converters.IContractOXMConverter;
import gr.opengov.agora.domain.IContract;
import gr.opengov.agora.exceptions.TaxonomyNotFoundException;
import gr.opengov.agora.security.IAccessControl;
import gr.opengov.agora.service.IDecisionGenericService;
import gr.opengov.agora.service.ITaxonomyService;
import gr.opengov.agora.util.Constants;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
/**
 * Handles requests for the application home page.
 */
@Controller
public class TaxonomyController {
	
	@Resource(name = "taxonomyService")
	private ITaxonomyService taxonomyService;
	
	@Resource(name = "taxonomyServiceReadOnly")
	private ITaxonomyService taxonomyServiceReadOnly;	
	
	@Resource(name = "commonsOxmConverter")
	private ICommonsOXMConverter commonsConverter;
	
	@Autowired
	private IAccessControl accessControl;	
	
	@Resource(name = "commonsOxmConverterReadOnly")
	private ICommonsOXMConverter commonsConverterReadOnly;	

	private static final Logger logger = LoggerFactory.getLogger(TaxonomyController.class);

	private ITaxonomyService getTaxonomyService(){
		if (accessControl.getClient().isAnonymous()){
			return taxonomyServiceReadOnly;
		}
		else{
			return taxonomyService;
		}
	}
	
	private ICommonsOXMConverter getCommonsConverter(){
		if (accessControl.getClient().isAnonymous()){
			return commonsConverterReadOnly;
		}
		else{
			return commonsConverter;
		}
	}	
	
	
	/**
	 * Returns the full details of the specified taxonomy
	 * @param taxonomyName
	 * @param model
	 * @return
	 */
	@RequestMapping( value="/taxonomy/{taxonomyName}", method = RequestMethod.GET )
	public String getTaxonomy( @PathVariable String taxonomyName, @RequestParam(value = "item",  required = false) String itemId, @RequestParam(value = "unit",  required = false) String unitId, Model model ) {
		ITaxonomy taxonomy;
		
		if (unitId == null){
			taxonomy = getTaxonomyService().getTaxonomy( taxonomyName, itemId );
		}
		else
		{
			taxonomy = getTaxonomyService().getSignersOfUnitTaxonomy( taxonomyName, itemId, unitId );
		}
		
		if ( taxonomy == null ) throw new TaxonomyNotFoundException( taxonomyName );
		
		if (
					taxonomyName.equals(Constants.SIGNERSTAXONOMYNAME) ||
					taxonomyName.equals(Constants.ODATAXONOMYNAME) ||
					taxonomyName.equals(Constants.UNITSTAXONOMYNAME)
			){
			model.addAttribute( getCommonsConverter().getOdaTaxonomy( taxonomy ) );
		}
		else
		{
			model.addAttribute( getCommonsConverter().getTaxonomy( taxonomy ) );
		}
		
		return "taxonomy";
	}
}