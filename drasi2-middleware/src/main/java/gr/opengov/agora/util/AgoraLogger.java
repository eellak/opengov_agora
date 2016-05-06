package gr.opengov.agora.util;

import gr.opengov.agora.security.IAccessControl;
import gr.opengov.agora.security.IClient;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.task.TaskExecutor;

public class AgoraLogger implements IAgoraLogger {
	private IAccessControl accessControl;
	private TaskExecutor taskExecutor;	
	
	static final private Logger logger = LoggerFactory.getLogger(AgoraLogger.class);

	public void setAccessControl(IAccessControl accessControl) {
		this.accessControl = accessControl;
	}
	
	public void setTaskExecutor( TaskExecutor taskExecutor ) {
		this.taskExecutor = taskExecutor;
	}
	
	private class AsynchronousLogger implements Runnable {
		private JoinPoint jp;
		private Throwable exception;
		private IClient client;
		private SimpleDateFormat df = new SimpleDateFormat( "EEE, d MMM yyyy HH:mm:ss Z" );
		public AsynchronousLogger( JoinPoint jp, Throwable exception, IClient client ) {
			this.jp = jp;
			this.exception = exception;
			this.client = client;
		}
		private void logAfterThrowingAsync( final JoinPoint jp, final Throwable throwable, final IClient client ) {
			Signature sig = jp.getSignature();		
			String email = client.getEmail();
			if ( email == null ) email = "";
			StringBuilder builder = new StringBuilder();
			Calendar c = GregorianCalendar.getInstance();
			builder.append( "\n" + df.format( c.getTime() ) + "\t" );
			builder.append( "User " + client.getUserId() + "\t" + "<" + email + ">" + "\t" );
			builder.append( "[" +  client.getAddress() + "] " + "\t" );
			builder.append( sig.getDeclaringType().getSimpleName() + "." + sig.getName() );
			builder.append( "\t(" );		
			boolean first = true;
			if ((jp.getArgs() != null) && (jp.getArgs().length>0) ){
				for ( Object arg: jp.getArgs() ) {
					if (arg != null){
						if ( !first ) builder.append( ", " );
						builder.append( arg.toString() );
						first = false;
					}
				}
			}
			builder.append( ")" );
			builder.append("\nCause:");
			builder.append(exception.getCause()+"\n");
			builder.append(exception.getClass());	
			
			String msg = builder.toString();
			logger.error( msg, throwable );
		}
		@Override
		public void run() {
			logAfterThrowingAsync( this.jp, this.exception, this.client );
		}
		
	}

	@Override
	public void logAfterThrowing( JoinPoint jp, Throwable e) {
		taskExecutor.execute( new AsynchronousLogger( jp, e, accessControl.getClient() ) );
	}
	
}
