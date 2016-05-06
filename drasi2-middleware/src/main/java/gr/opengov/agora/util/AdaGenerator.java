package gr.opengov.agora.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import gr.opengov.agora.domain.IContract;
import gr.opengov.agora.domain.INotice;
import gr.opengov.agora.domain.IPayment;
import gr.opengov.agora.domain.IProcurementRequest;
import gr.opengov.agora.domain.IPublicOrganizationDecision;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.web.bind.annotation.RequestMapping;


public class AdaGenerator  implements IAdaGenerator {
	private static final Logger logger = LoggerFactory.getLogger(AdaGenerator.class);
	private DataSource dataSource;	
	private static final DateFormat df = new SimpleDateFormat( "yyyyMMdd" );
	
	public AdaGenerator( DataSource dataSource ) {
		this.dataSource = dataSource;
		JdbcTemplate jdbcTemplate = new JdbcTemplate( dataSource );			
		String q = "CREATE TABLE IF NOT EXISTS uid_generator (" +
					"id BIGINT NOT NULL AUTO_INCREMENT, " +
					"orgId INTEGER NOT NULL, " +
					"dateKey INTEGER NOT NULL, " +
					"PRIMARY KEY( orgId, dateKey, id ) ) " +
					"engine=MyISAM";
		jdbcTemplate.execute( q );		
	}
	
	private Integer getNewAutoIncrementId( final Integer orgId, final Integer dateKey ) {
		KeyHolder keyHolder = new GeneratedKeyHolder();	
		JdbcTemplate jdbcTemplate = new JdbcTemplate( dataSource );
		jdbcTemplate.update(new PreparedStatementCreator() {			
			@Override
			public PreparedStatement createPreparedStatement(Connection connection)
					throws SQLException 
			{
				String q = "INSERT INTO uid_generator ( orgId, dateKey ) VALUES( ?, ? )";
				PreparedStatement ps = connection.prepareStatement( q, Statement.RETURN_GENERATED_KEYS);
				ps.setInt( 1, orgId );
				ps.setInt( 2, dateKey );
				return ps;
			}
		}, keyHolder );
		return keyHolder.getKey().intValue();	
	}
	
	private Integer getDateValue( Date date ) {
		String dateString = df.format( date );
		dateString = dateString.substring( dateString.length() - 6 );
		return Integer.parseInt( dateString );		
	}
	
	private String encode( Integer num ) {
		final char[] table = new char[] {
			'2', '3', '4', '5', '6', '7', '8',
			'9', 'Α', 'Β', 'Γ', 'Δ', 'Ε', 'Ζ', 
			'Η', 'Κ', 'Λ', 'Μ', 'Ν', 'Ξ', 'Π', 'Ρ',
			'Σ', 'Τ', 'Υ', 'Φ', 'Χ', 'Ψ', 'Ω' 
		};
		
		int length = table.length;
		
		StringBuffer buffer = new StringBuffer();		
		while ( num > 0 ) { 
			int loc = num % length;			
			buffer.append( table[ loc ] );
			num = num / length;			
		}
		return buffer.reverse().toString();		
	}

	private String getSuffix( IPublicOrganizationDecision org ) {
		if ( org instanceof IContract ) return "-Σ";
		if ( org instanceof IProcurementRequest ) return "-Α";
		if ( org instanceof IPayment ) return "-Π";
		if ( org instanceof INotice ) return "-Δ";
		return "";
	}
	
	@Override
	public String getNewId( IPublicOrganizationDecision entity ) {
		Integer orgId = entity.getOrganizationDiavgeiaId();
		Integer dateKey = getDateValue( GregorianCalendar.getInstance().getTime() );		
		Integer id = getNewAutoIncrementId( orgId, dateKey);
		
		return encode( dateKey ) + encode( orgId ) + "-" + encode( id ) + getSuffix( entity );
	}

}
