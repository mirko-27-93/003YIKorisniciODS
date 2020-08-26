package studiranje.ip.controller;

import studiranje.ip.util.UserChanging;

/**
 * Контрола када је су питању активности измјене корисника. 
 * @author mirko
 * @version 1.0
 */
public class UserChangingController {
	public UserChangingController() {}
	
	public UserChangingController(UserChangingController other) {
		try {
			this.changing = other.changing.clone();
		}catch(Exception ex) {
			throw new RuntimeException(ex); 
		}
	}
	
	private UserChanging changing = new UserChanging() {
		private static final long serialVersionUID = -1423103317309182940L;
		
		@Override
		public void change(String oldUsername, String neoUsername) {}

		@Override
		public UserChanging clone() throws CloneNotSupportedException {
			return (UserChanging) super.clone();
		}
		
	};

	public UserChanging getChanging() {
		return changing;
	}

	public void setChanging(UserChanging changing) {
		if(changing==null) changing =  new UserChanging() {
			private static final long serialVersionUID = 5092071155959705634L;

			@Override
			public void change(String oldUsername, String neoUsername) {}

			@Override
			public UserChanging clone() throws CloneNotSupportedException {
				return (UserChanging) super.clone();
			}
			
		};
		this.changing = changing;
	}
}
