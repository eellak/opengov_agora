package gr.opengov.agora.security;

import gr.opengov.agora.util.Tools;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class OdeAuthenticationProvider implements AuthenticationProvider {
	private DataSource dataSource = null;	
	@Autowired
	private Properties configProperties;
	
	private static final Logger logger = LoggerFactory.getLogger(OdeAuthenticationProvider.class);

	public void setConfigProperties( Properties properties ) {
		this.configProperties = properties;
	}
	
	public OdeAuthenticationProvider( DataSource dataSource ) {
		this.dataSource = dataSource;
	}

	private String getHash( String msg ) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance( "MD5" );
		byte[] hash = md.digest( msg.getBytes() );
		String mask = Tools.hex(hash) + "stavsalt";
		return Tools.hex( md.digest( mask.getBytes() ) );
	}
	
	@Override
	public Authentication authenticate(Authentication auth)
			throws AuthenticationException {		
		logger.debug( "Datasource: " + dataSource );
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		Authentication ret = null;
		logger.debug( "JdbcTemplate: " + jdbcTemplate );
		logger.debug( "CALLED CUSTOM AUTH PROVIDER" );
		String q = "SELECT ID, email, start_pb_id, realm " +
				   "FROM auth WHERE username=? and password=? " +
				   "GROUP BY ID";
		final String username = (String)auth.getPrincipal();		
		String password = (String)auth.getCredentials();	
		final String passwordBefore = (String)auth.getCredentials();
		try {
//			logger.debug("pwd before:"+password);
//			logger.debug("1263=" + getHash("1263"));
			password = getHash( password );	
//			logger.debug("pwd after:"+password);
			List<OdeUser> userList = jdbcTemplate.query( q, new Object[] { username, password },					
					new RowMapper<OdeUser>() {
						@Override
						public OdeUser mapRow(ResultSet rs, int arg1)
								throws SQLException {
							Integer db_id = rs.getInt( "ID" );
							Integer db_pb_id = rs.getInt( "start_pb_id" );
							String db_realm = rs.getString( "realm" );
							String email = rs.getString( "email" );	//						
							OdeUser user = new OdeUser( );
							user.setUsername(username);
							user.setPassword(passwordBefore);
							user.setEmail(email);
							user.setUserId( db_id );
							user.setOrganizationId( db_pb_id );
							user.setRealm( db_realm );
							user.setProperties( configProperties );
							user.authenticate();
							logger.debug( "User: " + user.toString() );
							return user;
						}						
					}
			);
			if ( userList.size() == 0 ) return null;
			OdeUser user = userList.get( 0 );
			logger.debug( "Retrieved user from database: " + user );
			return user;
		}
		catch ( Exception exp ) { 
			logger.debug( "Exception: " + exp.getMessage() );
			exp.printStackTrace();
		}	
		return ret;
	}

	@Override
	public boolean supports(Class<? extends Object> arg) { 		
		if ( arg.isAssignableFrom( UsernamePasswordAuthenticationToken.class ) ) return true;
		return false;
	}	
}

