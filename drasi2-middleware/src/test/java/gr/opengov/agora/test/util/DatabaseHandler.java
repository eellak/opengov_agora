package gr.opengov.agora.test.util;

import gr.opengov.agora.domain.IContract;
import gr.opengov.agora.domain.INotice;
import gr.opengov.agora.domain.IPayment;
import gr.opengov.agora.domain.IProcurementRequest;

import java.util.List;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DatabaseHandler implements IDatabaseHandler {
	private static final Logger logger = LoggerFactory.getLogger(DatabaseHandler.class);
	private SessionFactory sessionFactory;
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	/* (non-Javadoc)
	 * @see gr.opengov.agora.test.util.IDatabaseHandler#clearDb()
	 */
	@Override
	public void clearDb() {
		logger.debug( "Clearing Database" );
		List<IPayment> payments= sessionFactory.getCurrentSession().createQuery( "from Payment p" ).list();
		for ( IPayment p: payments ) {
			sessionFactory.getCurrentSession().delete( p );
		}		
		
		List<IContract> contracts= sessionFactory.getCurrentSession().createQuery( "from Contract c" ).list();
		for ( IContract c: contracts ) {
			sessionFactory.getCurrentSession().delete( c );
		}		
		
		List<IProcurementRequest> procurementRequests= sessionFactory.getCurrentSession().createQuery( "from ProcurementRequest pr" ).list();
		for ( IProcurementRequest pr: procurementRequests ) {
			sessionFactory.getCurrentSession().delete( pr );
		}
		
		List<INotice> notices = sessionFactory.getCurrentSession().createQuery( "from  Notice n" ).list();
		for ( INotice notice: notices ) {
			sessionFactory.getCurrentSession().delete( notice );
		}		
		
		logger.debug( "Done" );
	}
		
}
