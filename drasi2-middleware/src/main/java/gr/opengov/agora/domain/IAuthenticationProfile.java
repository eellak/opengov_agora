package gr.opengov.agora.domain;

import java.util.List;
import java.util.Set;

import gr.opengov.agora.model.ArrayOfOrganizations;
import gr.opengov.agora.util.LazyEntity;

public interface IAuthenticationProfile extends LazyEntity {

	String getRole();

	void setRole(String role);

	String getUserName();

	void setUserName(String userName);

	String getFirstName();

	void setFirstName(String firstName);

	String getLastName();

	void setLastName(String lastName);

	List<IOrganization> getOdeMember();

	void setOdeMember(List<IOrganization> odeMember);

	int getUserId();

	void setUserId(int userId);

	String getEmail();

	void setEmail(String email);

}
