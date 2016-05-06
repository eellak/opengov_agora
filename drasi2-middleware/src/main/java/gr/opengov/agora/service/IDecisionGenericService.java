package gr.opengov.agora.service;

import gr.opengov.agora.domain.IContract;
import gr.opengov.agora.domain.ICpv;
import gr.opengov.agora.domain.IDecisionStorageReference;
import gr.opengov.agora.domain.IDocument;
import gr.opengov.agora.domain.IPublicOrganizationDecision;
import gr.opengov.agora.exceptions.DecisionNotFoundException;
import gr.opengov.agora.search.ISearchParameter;
import gr.opengov.agora.security.IAccessControl;
import gr.opengov.agora.util.IPaginationInfo;
import gr.opengov.agora.validation.IValidation;

import java.io.InputStream;
import java.util.List;

public interface IDecisionGenericService<T extends IPublicOrganizationDecision> {
	
	/**
	 * Save a list of entities, performing validation.
	 * This is an atomic operation; either all entities are saved, or none is.
	 * Returns a list of references between uniqueDocumentCodes and saved entity ids. The list is empty if
	 * validation has failed.
	 * @param contracts
	 * @return
	 */
	public abstract List<IDecisionStorageReference> save( List<T> decisions, IValidation validation );
	
	/**
	 * Returns a list of all entities in the specified limits range
	 * Contracts are sorted by descending submission time
	 * Updates pagination with the real values
	 */
	public abstract List<T> getAll( IPaginationInfo pagination, List<ISearchParameter> searchParams );
	public abstract List<T> getAll( IPaginationInfo pagination );
	public abstract List<T> getAll();
	
	/**
	 * Returns a single entity
	 * Throws DecisionNotFoundException if entity is not found
	 * @param contractId
	 * @return
	 */
	public abstract T get(String id);
	
	/**
	 * Marks an entity as 'deleted'. Does NOT remove entity from the database.
	 * Return information about the deleted entity
	 * @param deletionType 
	 * @param contractId
	 */

	public abstract IDecisionStorageReference delete( String id, String reason, String deletionType );

	/**
	 * Delete an entity from the database.
	 * Return information about the deleted entity
	 * @param contractId
	 */

	public abstract void purge( String id );
	
	/**
	 * Update an entity
	 * @param contract
	 * @return 
	 */
	public abstract IDecisionStorageReference update(T contract, IValidation validation );	
	
	/**
	 * Return an entity's document. getContract may not return the document property, due to lazy loading
	 */
	public abstract IDocument getDocument(String id) throws DecisionNotFoundException;
	
	/**
	 * Check if an entity exists
	 * 
	 */
	public abstract boolean exists( String id );
//	public abstract boolean existsWithUniqueDocumentCode( String documentCode );
			
	public abstract InputStream getDocumentData(IDocument document) throws DecisionNotFoundException, Exception;
	public abstract InputStream getDocumentOriginalData( IDocument document) throws DecisionNotFoundException, Exception;
	
	public abstract String getXmlPath();

	public abstract T getUnmanaged(String id);

	public ICpv getCpv(String cpvCode);

	public boolean exist(List<ISearchParameter> searchParams);

	IDecisionStorageReference cancel(String id, String reason, String cancellationType);
	
}
