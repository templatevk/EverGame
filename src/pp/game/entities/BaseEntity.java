package pp.game.entities;

abstract class BaseEntity implements IBaseEntity {
	private Object data;
	
	public BaseEntity() {
		
	}
	
	@Override
	public Object getUserData() {
		return data;
	}
	
	@Override
	public void setUserData(Object data) {
		this.data = data;
	}
}
