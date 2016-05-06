package gr.opengov.agora.util;

import org.aspectj.lang.JoinPoint;

public interface IAgoraLogger {
	public void logAfterThrowing( JoinPoint jp, Throwable e );
}