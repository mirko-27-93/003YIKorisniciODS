package studiranje.ip.controller;

import studiranje.ip.util.UserDeletion;

/**
 * Контрола када су у питању брисања корисника. 
 * @author mirko
 * @version 1.0
 */
public class UserDeletionController {
	public UserDeletionController() {}
	
	public UserDeletionController(UserDeletionController other) {
		try {
			this.deletion = other.deletion.clone();
		}catch(Exception ex) {
			throw new RuntimeException(ex);
		}
	}
	
	private UserDeletion deletion = new UserDeletion() {
		private static final long serialVersionUID = 6642765992206388677L;

		@Override
		public void delete(String username) {}

		@Override
		public UserDeletion clone() throws CloneNotSupportedException {
			return (UserDeletion) super.clone();
		}
		
	};

	public UserDeletion getDeletion() {
		return deletion;
	}

	public void setDeletion(UserDeletion deletion) {
		if(deletion==null) deletion =new UserDeletion() {
			private static final long serialVersionUID = 7088102749292155754L;

			@Override
			public void delete(String username) {}

			@Override
			public UserDeletion clone() throws CloneNotSupportedException {
				return (UserDeletion) super.clone();
			}
		};
		this.deletion = deletion;
	}
}
