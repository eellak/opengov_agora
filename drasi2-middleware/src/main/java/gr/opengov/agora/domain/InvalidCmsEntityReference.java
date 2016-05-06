package gr.opengov.agora.domain;

public class InvalidCmsEntityReference implements ICmsEntity {

	@Override
	public void finalizeEntity() {
		// TODO Auto-generated method stub

	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setId(String id) {
		// TODO Auto-generated method stub

	}

	@Override
	public ICmsMetadata getCmsMetadata() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setCmsMetadata(ICmsMetadata cmsMetadata) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isDeleted() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void markDeleted(String reason, String deletionType, Integer deletedFromUserId) {
		// TODO Auto-generated method stub

	}

	@Override
	public ICmsEntity getReplaces() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setReplaces(ICmsEntity replaces) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isValid() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCancelled() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void markCancelled(String reason, String cancellationType,
			Integer cancelledFromUserId) {
		// TODO Auto-generated method stub
		
	}

}
