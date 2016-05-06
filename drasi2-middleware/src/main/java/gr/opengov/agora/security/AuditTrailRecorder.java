package gr.opengov.agora.security;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.task.TaskExecutor;

public class AuditTrailRecorder implements IAuditTrailRecorder {
	private IAccessControl accessControl;
	private TaskExecutor taskExecutor;	
	
	static final private Logger logger = LoggerFactory.getLogger(AuditTrailRecorder.class);

	public void setAccessControl(IAccessControl accessControl) {
		this.accessControl = accessControl;
	}
	
	public void setTaskExecutor( TaskExecutor taskExecutor ) {
		this.taskExecutor = taskExecutor;
	}
	
	private class AsynchronousAuditTrailRecorder implements Runnable {
		private JoinPoint jp;
		private Object ret;
		private IClient client;
		private SimpleDateFormat df = new SimpleDateFormat( "EEE, d MMM yyyy HH:mm:ss Z" );
		public AsynchronousAuditTrailRecorder( JoinPoint jp, Object ret, IClient client ) {
			this.jp = jp;
			this.ret = ret;
			this.client = client;
		}
		private void saveAuditTrailAsync( final JoinPoint jp, final Object ret, final IClient client ) {
			Signature sig = jp.getSignature();		
			String email = client.getEmail();
			if ( email == null ) email = "";
			StringBuilder builder = new StringBuilder();
			Calendar c = GregorianCalendar.getInstance();
			builder.append( df.format( c.getTime() ) + "\t" );
			builder.append( "User " + client.getUserId() + "\t" + "<" + email + ">" + "\t" );
			builder.append( "[" +  client.getAddress() + "] " + "\t" );
			builder.append( sig.getDeclaringType().getSimpleName() + "." + sig.getName() );
			builder.append( "\t(" );		
			boolean first = true;
			for ( Object arg: jp.getArgs() ) {
				if ( !first ) builder.append( ", " );
				builder.append( arg.toString() );
				first = false;
			}
			builder.append( ")" );
			String msg = builder.toString();
			logger.info( msg );
		}
		@Override
		public void run() {
			saveAuditTrailAsync( this.jp, this.ret, this.client );
		}
		
	}

	@Override
	public void saveAuditTrail( JoinPoint jp, Object ret) {
		taskExecutor.execute( new AsynchronousAuditTrailRecorder( jp, ret, accessControl.getClient() ) );
	}
	
}
