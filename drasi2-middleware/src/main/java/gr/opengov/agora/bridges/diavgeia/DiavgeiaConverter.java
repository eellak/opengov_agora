package gr.opengov.agora.bridges.diavgeia;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import gr.opengov.agora.bridges.diavgeia.domain.DiavgeiaDecision;
import gr.opengov.agora.bridges.diavgeia.domain.IDiavgeiaDecision;
import gr.opengov.agora.domain.Ada;
import gr.opengov.agora.domain.IPublicOrganizationDecision;
import gr.opengov.agora.exceptions.InvalidDiavgeiaDecisionTypeException;
import gr.opengov.agora.security.IClient;

public class DiavgeiaConverter implements IDiavgeiaConverter {
	
	private static <T> String toDiavgeiaList( List<T> list ) {
		StringBuffer buffer = new StringBuffer();
		boolean first = true;
		for ( T item: list ) {
			if ( !first ) buffer.append( "," );
			buffer.append( "#" + item.toString() + "#" );			
		}
		return buffer.toString();
	}
	
	private static <T> List<T> fromDiavgeiaList( String str, Class<T> type ) {
		List<T> items = new ArrayList<T>();
		StringTokenizer tok = new StringTokenizer( str, ",");
		while (tok.hasMoreTokens()) {
			String token = tok.nextToken();
			token = token.replaceAll("#", "");
			try {
				if ( type.isAssignableFrom( Integer.class ) ) {
					Integer id = Integer.parseInt(token);
					items.add( (T)id );
				}
				else {
					items.add( (T)token.toString() );
				}
			} catch (Exception e) { }
		}
		return items;		
	}
	
	/* Need a policy to convert a list of signer ids to the single id supported by
	 * Diavgeia. For now, use the first one.
	 */
	private Integer getDiavgeiaSignerId( List<Integer> signerIds ) {		
		if ( signerIds.isEmpty() ) return 0;
		return signerIds.get(0);
	}
	
	/* Need a policy to convert a list of related ADAs to those supported by
	 * Diavgeia.
	 */
	private String getDiavgeiaRelatedAdas( List<Ada> adas ) {
		if ( adas == null ) return "";
		List<String> list = new ArrayList<String>();
		for ( Ada ada: adas ) {
			if ( ada.isDiavgeiaAda() ) list.add( ada.getAdaCode() );
		}
		return toDiavgeiaList( list );
	}
	
	/* (non-Javadoc)
	 * @see gr.opengov.agora.bridges.IDiavgeiaConverter#getDiavgeiaDesission(gr.opengov.agora.domain.IPublicOrganizationDecision, gr.opengov.agora.security.IClient)
	 */
	@Override
	public IDiavgeiaDecision getDiavgeiaDecision( IPublicOrganizationDecision decision, IClient client ) throws InvalidDiavgeiaDecisionTypeException {
		IDiavgeiaDecision target = new DiavgeiaDecision();
		return convertDiavgeiaDecision(decision, client, target);
	}

	/*
	 * Copies to target
	 * @see gr.opengov.agora.bridges.diavgeia.IDiavgeiaConverter#convertDiavgeiaDecision(gr.opengov.agora.domain.IPublicOrganizationDecision, gr.opengov.agora.security.IClient, gr.opengov.agora.bridges.diavgeia.domain.IDiavgeiaDecision)
	 */
	@Override
	public IDiavgeiaDecision convertDiavgeiaDecision(
			IPublicOrganizationDecision decision, IClient client,
			IDiavgeiaDecision target)
			throws InvalidDiavgeiaDecisionTypeException 
	{
		if ( decision.getDiavgeiaType() == null ) throw new InvalidDiavgeiaDecisionTypeException( decision.getId() );
		
		target.setAda( decision.getId() );
		if ( decision.getReplaces() != null ) {
			target.setIsCorrectionOf( decision.getReplaces().getId() );
		}
		// TODO: Should use an 'origin' column, for now just use status
		target.setStatus( "agora" );
						
		target.getDecisionDate().setTime( decision.getDateSigned().getTime() );
		target.setIssuerEmail( decision.getIssuerEmail() );
		target.setOrganizationId( decision.getOrganizationDiavgeiaId() );
		target.setProtocolNumber( decision.getProtocolNumberCode() );
		target.setRelatedAdas( getDiavgeiaRelatedAdas( decision.getRelatedAdas() ) );
		target.setSignerId( getDiavgeiaSignerId( decision.getSignersDiavgeiaIds() ) );
		target.setSubject( decision.getTitle() );
		target.getSubmissionTimestamp().setTime( decision.getCmsMetadata().getSubmissionTime().getTime() );
		// TODO: Get appropriate thematics
		target.setThematicIds( "" );
		target.setTypeId( decision.getDiavgeiaType() );
		target.setUnitId( decision.getUnitDiavgeiaId() );
		target.setUsername( client.getName() );
		return target;
	}

}
