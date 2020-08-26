package studiranje.ip.controller;

import javax.servlet.http.HttpSession;

import studiranje.ip.util.UserDeletionSessionable;

/**
 * Контрола када су у питању брисања корисника. 
 * @author mirko
 * @version 1.0
 */
public class UserDeletionSessionableController {
	public UserDeletionSessionableController() {}
	
	public UserDeletionSessionableController(UserDeletionSessionableController other) {
		try {
			this.deletion = other.deletion.clone();
		}catch(Exception ex) {
			throw new RuntimeException(ex);
		}
	}
	
	private UserDeletionSessionable deletion = new UserDeletionSessionable() {
		private static final long serialVersionUID = 6642765992206388677L;

		@Override
		public void delete(String username, HttpSession session) {}

		@Override
		public UserDeletionSessionable clone() throws CloneNotSupportedException {
			return (UserDeletionSessionable) super.clone();
		}
		
	};

	public UserDeletionSessionable getDeletion() {
		return deletion;
	}

	public void setDeletion(UserDeletionSessionable deletion) {
		if(deletion==null) deletion =new UserDeletionSessionable() {
			private static final long serialVersionUID = 7088102749292155754L;

			@Override
			public void delete(String username, HttpSession session) {}

			@Override
			public UserDeletionSessionable clone() throws CloneNotSupportedException {
				return (UserDeletionSessionable) super.clone();
			}
		};
		this.deletion = deletion;
	}
}
