package gr.opengov.agora.test.util;

import java.util.ArrayList;
import java.util.List;

public class AgoraTestResources {
	private List<AgoraTestResource> tests;
	
	public AgoraTestResources(){
		tests = new ArrayList<AgoraTestResource>();
		tests.add(new AgoraTestResource(1,"Contract","sample-contract-1.xml", "fulfilled contract", true, false, false, false, false, false, false));
		tests.add(new AgoraTestResource(2,"Contract","sample-contract-2.xml", "Testing 'until' date before 'since'", false, false, false, false, false, false, false));
		tests.add(new AgoraTestResource(3,"Contract","sample-contract-3.xml", "Testing future signed date", false, false, false, false, false, false, false));
		tests.add(new AgoraTestResource(4,"Contract","sample-contract-4.xml", "Testing contract with negative cost contract items", false, false, false, false, false, false, false));
		tests.add(new AgoraTestResource(5,"Contract","sample-contract-5.xml", "Testing contract invalid vat", false, false, false, false, false, false, false));
		tests.add(new AgoraTestResource(6,"Contract","sample-contract-6.xml", "Testing contract with empty contractItems list", false, false, false, false, false, false, false));
		tests.add(new AgoraTestResource(7,"Contract","sample-contract-7.xml", "Testing contract with null contractItems list", false, false, false, false, false, false, false));
		tests.add(new AgoraTestResource(8,"Contract","sample-contract-8.xml", "Testing contract with invalid currency", false, false, false, false, false, false, false));
		tests.add(new AgoraTestResource(9,"Contract","sample-contract-9.xml", "Testing contract with invalid CPV code", false, false, false, false, false, false, false));
		tests.add(new AgoraTestResource(10,"Contract","sample-contract-10.xml", "Testing contract with empty CPV codes list", false, false, false, false, false, false, false));
		tests.add(new AgoraTestResource(11,"Contract","sample-contract-11.xml", "Testing contract with null CPV codes", false, false, false, false, false, false, false));
		tests.add(new AgoraTestResource(12,"Contract","sample-contract-12.xml", "Testing contract with invalid award procedure", false, false, false, false, false, false, false));
		tests.add(new AgoraTestResource(13,"Contract","sample-contract-13.xml", "Testing contract containing cmsMetadata", false, false, false, false, false, false, false));
		tests.add(new AgoraTestResource(14,"Contract","sample-contract-14.xml", "Testing contract extending not existing contract", false, false, false, false, false, false, false));
		tests.add(new AgoraTestResource(15,"Contract","sample-contract-15.xml", "Testing contract changing not existing contract", false, false, false, false, false, false, false));
		tests.add(new AgoraTestResource(16,"Contract","sample-contract-16.xml", "Testing contract replacing not existing contract", false, false, false, false, false, false, false));		
		tests.add(new AgoraTestResource(17,"Contract","sample-contract-17.xml", "Testing without until date", true, false, false, false, false, false, false));
		tests.add(new AgoraTestResource(18,"Contract","sample-contract-18.xml", "Testing contract with project code and contract type != project", false, false, false, false, false, false, false));		
		tests.add(new AgoraTestResource(19,"Contract","sample-contract-19.xml", "Testing contract with project code and contract type == project", true, false, false, false, false, false, false));		
		tests.add(new AgoraTestResource(20,"Contract","sample-contract-20.xml", "Testing contract with secondary party from other country than Greece, containing afm", true, false, false, false, false, false, false));
		tests.add(new AgoraTestResource(21,"Contract","sample-contract-21.xml", "Testing contract with secondary party from other Greece, containing invalid afm", false, false, false, false, false, false, false));
		tests.add(new AgoraTestResource(22,"Contract","sample-contract-22.xml", "Testing contract with coFunded == true and codeCoFunded == null", false, false, false, false, false, false, false));
		tests.add(new AgoraTestResource(23,"Contract","sample-contract-23.xml", "Testing contract with fundedFromPIP == true and codePIP == null", false, false, false, false, false, false, false));		
	}
	
	public List<AgoraTestResource> getTestResources(){
		return tests;
	}
}
