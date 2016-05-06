package gr.opengov.agora.service;

import gr.opengov.agora.domain.IPublicOrganizationDecision;
import gr.opengov.agora.exceptions.DecisionNotFoundException;

import java.util.Calendar;

public interface IDecisionUtilsService {

	public abstract Calendar getSubmissionTimestamp(String id) throws DecisionNotFoundException;

	public abstract IPublicOrganizationDecision get(String id) throws DecisionNotFoundException;

	public abstract String getNameFromAfm(String afm);

}