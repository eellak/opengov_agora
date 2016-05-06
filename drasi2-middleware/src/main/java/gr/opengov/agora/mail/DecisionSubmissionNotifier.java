package gr.opengov.agora.mail;

import gr.opengov.agora.domain.DecisionType;
import gr.opengov.agora.domain.IDecisionStorageReference;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.text.DateFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;

public class DecisionSubmissionNotifier implements IDecisionMailer {
	@Autowired
	private JavaMailSender mailSender;
	private TaskExecutor taskExecutor;
	private String prefix = "";
	private String from;
	private String fromName;
	private String encoding = "";		
	
	static final private Logger logger = LoggerFactory.getLogger(DecisionSubmissionNotifier.class);
	
	private Map< DecisionType, String[] > subjectPrefixes;
	private Map< DecisionType, String[] > bodyPrefixes;
	
	public void setMailSender( JavaMailSender mailSender ) {
		this.mailSender = mailSender;
	}
	
	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	
	public void setFrom(String from) {
		this.from = from;
	}
		
	public String getFrom() {
		return from;
	}
	
	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
	
	public TaskExecutor getTaskExecutor() {
		return taskExecutor;
	}

	public void setTaskExecutor(TaskExecutor taskExecutor) {
		this.taskExecutor = taskExecutor;
	}
	
	public String getFromName() {
		return fromName;
	}

	public void setFromName(String fromName) {
		this.fromName = fromName;
	}

	public DecisionSubmissionNotifier() {
		subjectPrefixes = new Hashtable<DecisionType, String[]>();
		subjectPrefixes.put( DecisionType.CONTRACT, new String[] { "Σύμβασης", "Συμβάσεων" } );
		subjectPrefixes.put( DecisionType.PROCUREMENT_REQUEST, new String[] { "Αιτήματος Προμήθειας", "Αιτημάτων Προμηθειών" } );
		subjectPrefixes.put( DecisionType.PAYMENT, new String[] { "Πληρωμής", "Πληρωμών" } );
		
		bodyPrefixes = new Hashtable<DecisionType, String[]>();
		bodyPrefixes.put( DecisionType.CONTRACT, new String[] { "η σύμβαση", "οι συμβάσεις" } );
		bodyPrefixes.put( DecisionType.PROCUREMENT_REQUEST, new String[] { "το αίτημα προμήθειας", "τα αιτήματα προμηθειών" } );
		bodyPrefixes.put( DecisionType.PAYMENT, new String[] { "η πληρωμή", "οι πληρωμές" } );
	}
	
	private String getDecisionSubjectPrefix( DecisionType type, boolean plural ) {
		String[] arr = subjectPrefixes.get( type );
		if ( plural ) return arr[ 1 ];
		else return arr[ 0 ];
	}
	
	private String getDecisionBodyPrefix( DecisionType type, boolean plural ) {
		String[] arr = bodyPrefixes.get( type );
		if ( plural ) return arr[ 1 ];
		else return arr[ 0 ];
	}	
	
	private String getURL( DecisionType type ) {
		String baseURL = "http://agora.gov.gr";
		switch ( type ) {
		case CONTRACT:
			return baseURL + "/contracts";
		case PROCUREMENT_REQUEST:
			return baseURL + "/procurementRequests";
		case PAYMENT:
			return baseURL + "/payments";
		default:
			return null;
		}
	}
	
	private <T> String getString( T[] list, String separator ) {
		StringBuilder builder = new StringBuilder();
		for ( T item: list ) {
			if ( builder.length() > 0 ) builder.append( separator );
			builder.append( item.toString() );			
		}
		return builder.toString();
	}

	private void sendMail( MimeMessage msg ) throws MailException {
		mailSender.send( msg );
	}	
	
	private MimeMessage getMimeMessage(List<String> emails) throws MessagingException {
		MimeMessage msg = mailSender.createMimeMessage();
		try {
			msg.setFrom(new InternetAddress(getFrom(), getFromName()));
		}
		catch ( UnsupportedEncodingException e ) {
			logger.warn( "From name not supported, ignoring: " + e.getMessage() );
			msg.setFrom(new InternetAddress(getFrom()));
		}
		
		msg.setHeader("Content-Type", "text/plain; charset=" + getEncoding() );
		for (String email : emails) {
			try {
				msg.addRecipient(Message.RecipientType.TO, new InternetAddress(
						email));
			} catch (AddressException e) {
				logger.warn("Error adding email to list of recipients, ignoring: "
						+ email);
				logger.warn("Reason: " + e.getMessage());
			}
		}
		return msg;
	}
	
	private class AsynchronousMailSavedSender implements Runnable {
		private List<IDecisionStorageReference> storedDecisions;
		public AsynchronousMailSavedSender( List<IDecisionStorageReference> storedDecisions ) {
			this.storedDecisions = storedDecisions;
		}
		private void sendMailSavedAsync( final List<IDecisionStorageReference> storedDecisions ) {		
			if ( storedDecisions.isEmpty() ) return;
			DateFormat df = new SimpleDateFormat( "EEE, d MMM yyyy HH:mm:ss Z", new Locale( "el", "GR" ) );
			for ( IDecisionStorageReference ref: storedDecisions ) {
				try {
					logger.debug( "Email for new decision: " + ref.getId() );
					if ( ref.getEmails().isEmpty() ) {			
						logger.warn( "Empty email list for new decision: " + ref.getId() );
						continue;
					}
					DecisionType type = ref.getType();
					String subject = getPrefix() + "Καταχώριση " + getDecisionSubjectPrefix( type, false );
					subject += ": " + ref.getId();
					String text = "";
					text = "Καταχωρήθηκε στην Αγορά (http://agora.gov.gr) " + getDecisionBodyPrefix( type, false ) + " με στοιχεία: ";
					text += "\n\n\n";
					text += "ΑΔΑ: " + ref.getId() + "\n";		
					text += "Ημ/νια καταχώρισης: " + df.format( ref.getSubmissionTimestamp().getTime() ) + "\n";
					text += "URL: " + getURL( ref.getType() ) + "/" + ref.getId() + "\n";
					text += "SHA-256 υποβληθέντος αρχείου: " + ref.getHashOriginal() + "\n";
					text += "SHA-256 υπογεγραμμένου αρχείου: " + ref.getHashStamped() + "\n";
					text += "\n\n";

					MimeMessage msg = getMimeMessage( ref.getEmails() );			
					msg.setSubject( subject, getEncoding() );
					msg.setText( text, getEncoding() );
					sendMail( msg );
					/*
					logger.debug( "Delaying 5 sec..." );
					Thread.sleep( 5000 );
					logger.debug( "Ok" );
					*/
				}
				catch ( Exception e ) {
					logger.error( "Error sending email for new decision: " + ref.getId() );
					logger.error( "Reason: " + e.getMessage() );
				}
			}		
		}
		public void run() {
			sendMailSavedAsync( this.storedDecisions );;
		}
	}
	
	private class AsynchronousMailDeletedSender implements Runnable {
		private IDecisionStorageReference deletedDecision;
		public AsynchronousMailDeletedSender( IDecisionStorageReference deletedDecision ) {
			this.deletedDecision = deletedDecision;
		}
		private void sendMailDeletedAsync( final IDecisionStorageReference deletedDecision) {
			logger.debug( "Email for deleted decision: " + deletedDecision.getId() );
			if ( deletedDecision.getEmails().isEmpty() ) {			
				logger.warn( "Empty email list for deleted decision: " + deletedDecision.getId() );
				return;
			}
			try {
				DecisionType type = deletedDecision.getType();		
				String subject = getPrefix() + "Διαγραφή " + getDecisionSubjectPrefix( type, false );
				subject += ": " + deletedDecision.getId();
				String text = "";
				text = "Διαγράφηκε από την Αγορά (http://agora.gov.gr) " + getDecisionBodyPrefix( type, false ) + " με στοιχεία: ";
				text += "\n\n\n";
				text += "ΑΔΑ: " + deletedDecision.getId() + "\n";
				text += "\n";
				MimeMessage msg = getMimeMessage( deletedDecision.getEmails() );			
				msg.setSubject( subject, getEncoding() );			
				msg.setText( text, getEncoding() );
				sendMail( msg );
			}
			catch ( Exception e ) {
				logger.error( "Error sending email for deleted decision: " + deletedDecision.getId() );
				logger.error( "Reason: " + e.getMessage() );
			}
		}
		public void run() {
			sendMailDeletedAsync(this.deletedDecision);
		}
	}
	
	
	private class AsynchronousMailUpdatedSender implements Runnable {
		private IDecisionStorageReference updatedDecision;
		public AsynchronousMailUpdatedSender( IDecisionStorageReference updatedDecision ) {
			this.updatedDecision = updatedDecision;
		}
		private void sendMailUpdatedAsync( final IDecisionStorageReference updatedDecision) {
			logger.debug( "Email for updated decision: " + updatedDecision.getId() );
			if ( updatedDecision.getEmails().isEmpty() ) {
				logger.warn( "Empty email list for updated decision: " + updatedDecision.getId() );
				return;
			}
			try {
				DecisionType type = updatedDecision.getType();		
				String subject = getPrefix() + "Διαγραφή " + getDecisionSubjectPrefix( type, false );
				subject += ": " + updatedDecision.getId();
				String text = "";
				text = "Τροποποιήθηκε από την Αγορά (http://agora.gov.gr) " + getDecisionBodyPrefix( type, false ) + " με στοιχεία: ";
				text += "\n\n\n";
				text += "ΑΔΑ: " + updatedDecision.getId() + "\n";
				text += "\n";
				MimeMessage msg = getMimeMessage( updatedDecision.getEmails() );			
				msg.setSubject( subject, getEncoding() );			
				msg.setText( text, getEncoding() );
				sendMail( msg );
			}
			catch ( Exception e ) {
				logger.error( "Error sending email for updated decision: " + updatedDecision.getId() );
				logger.error( "Reason: " + e.getMessage() );
			}
		}
		public void run() {
			sendMailUpdatedAsync(this.updatedDecision);
		}
	}	
	
	/* AOP conflicts with asynchronous, so have to be verbose... */
	@Override
	public void sendMailSaved( List<IDecisionStorageReference> storedDecisions ) {
		getTaskExecutor().execute( new AsynchronousMailSavedSender(storedDecisions) );		
	}
	
	@Override
	public void sendMailDeleted( IDecisionStorageReference deletedDecision) {
		getTaskExecutor().execute( new AsynchronousMailDeletedSender(deletedDecision) );
	}
	
	@Override
	public void sendMailUpdated( IDecisionStorageReference updatedDecision) {
		getTaskExecutor().execute( new AsynchronousMailUpdatedSender(updatedDecision) );
	}	

}

