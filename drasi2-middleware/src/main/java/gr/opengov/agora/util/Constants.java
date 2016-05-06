package gr.opengov.agora.util;

import java.util.Arrays;
import java.util.List;

public final class Constants {
	public final static List<Integer> CONTRACTTYPES_PROJECT = Arrays.asList(1,2,3);
	public enum Action{CREATE, READ, UPDATE, DELETE;};
	public final static String COUNTRYCODE_GREECE = "GR";
	public final static double COSTOVERRUN = 0.04;
	public final static long TIMESLOT_FOR_CHANGE_OR_REMOVE_DECISION = 48*60*60*1000;
	public final static String APPROVED_REQUESTS_FILTER_KEY="approved";
	public final static String NOTAPPROVED_REQUESTS_FILTER_KEY="notapproved";
	public final static String APPROVAL_REQUESTS_FILTER_KEY="approval";
	public final static String DOMAIN_CONTRACT="Contract";
	public final static String DOMAIN_PROCUREMENTREQUEST="ProcurementRequest";
	public final static String DOMAIN_PAYMENT="Payment";
	public final static String DOMAIN_NOTICE="Notice";
	public final static String ODATAXONOMYNAME="oda";
	public final static String UNITSTAXONOMYNAME="units";
	public final static String SIGNERSTAXONOMYNAME="signers";
	public final static String SIGNERSOFUNITTAXONOMYNAME="signers";
}
