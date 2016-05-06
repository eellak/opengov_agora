package gr.opengov.agora.security;

import org.aspectj.lang.JoinPoint;

public interface IAuditTrailRecorder {
	public void saveAuditTrail( JoinPoint jp, Object ret );
}