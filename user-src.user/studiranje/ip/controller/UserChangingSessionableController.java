package studiranje.ip.controller;

import javax.servlet.http.HttpSession;

import studiranje.ip.util.UserChangingSessionable;

/**
 * Контрола када је су питању активности измјене корисника. 
 * @author mirko
 * @version 1.0
 */
public class UserChangingSessionableController {
	public UserChangingSessionableController() {}
	
	public UserChangingSessionableController(UserChangingSessionableController other) {
		try {
			this.changing = other.changing.clone();
		}catch(Exception ex) {
			throw new RuntimeException(ex); 
		}
	}
	
	private UserChangingSessionable changing = new UserChangingSessionable() {
		private static final long serialVersionUID = -1423103317309182940L;
		
		@Override
		public void change(String oldUsername, String neoUsername, HttpSession session) {}

		@Override
		public UserChangingSessionable clone() throws CloneNotSupportedException {
			return (UserChangingSessionable) super.clone();
		}
		
	};

	public UserChangingSessionable getChanging() {
		return changing;
	}

	public void setChanging(UserChangingSessionable changing) {
		if(changing==null) changing =  new UserChangingSessionable() {
			private static final long serialVersionUID = 5092071155959705634L;

			@Override
			public void change(String oldUsername, String neoUsername, HttpSession session) {}

			@Override
			public UserChangingSessionable clone() throws CloneNotSupportedException {
				return (UserChangingSessionable) super.clone();
			}
			
		};
		this.changing = changing;
	}
}
