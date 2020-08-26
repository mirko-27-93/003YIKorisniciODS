package studiranje.ip.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import studiranje.ip.bean.InformationBean;
import studiranje.ip.lang.UserMessagesConstants;

/**
 * Контролер којим се поједностављује рад са порукама. 
 * @author mirko
 * @version 1.0
 */
public class UserMessageController {
	private InformationBean infoBean;

	public InformationBean getInfoBean() {
		return infoBean;
	}

	public UserMessageController setInfoBean(InformationBean infoBean) {
		this.infoBean = infoBean;
		return this;
	} 

	public UserMessageController setRegistrationSuccessForUI(HttpServletRequest req, HttpServletResponse resp) {
		infoBean.setMessage("msg", UserMessagesConstants.REGISTRATION_SUCCESS);
		return this;
	}
	
	public UserMessageController setRegistrationSuccessForWeb(HttpServletRequest req, HttpServletResponse resp) {
		infoBean.setMessageSource("/WEB-INF/msg.user/success.jsp");
		infoBean.setMessage("msg", UserMessagesConstants.REGISTRATION_SUCCESS);
		return this;
	}
	
	public UserMessageController setRegistrationGeneralFailureForUI(HttpServletRequest req, HttpServletResponse resp) {
		infoBean.setMessage("msg", UserMessagesConstants.REGISTRATION_FAILURE);
		return this;
	}
	
	public UserMessageController setRegistrationGeneralFailureForWeb(HttpServletRequest req, HttpServletResponse resp) {
		infoBean.setMessageSource("/WEB-INF/msg.user/error.jsp");
		infoBean.setMessage("msg", UserMessagesConstants.REGISTRATION_FAILURE);
		return this;
	}

	public UserMessageController setLoginSuccessForUI(HttpServletRequest req, HttpServletResponse resp) {
		infoBean.setMessage("msg", UserMessagesConstants.LOGIN_SUCCESS);
		return this;
	}
	
	public UserMessageController setLoginSuccessForWeb(HttpServletRequest req, HttpServletResponse resp) {
		infoBean.setMessageSource("/WEB-INF/msg.user/success.jsp");
		infoBean.setMessage("msg", UserMessagesConstants.LOGIN_SUCCESS);
		return this;
	}
	
	public UserMessageController setLoginGeneralFailureForUI(HttpServletRequest req, HttpServletResponse resp) {
		infoBean.setMessage("msg", UserMessagesConstants.LOGIN_FAILURE);
		return this;
	}
	
	public UserMessageController setLoginGeneralFailureForWeb(HttpServletRequest req, HttpServletResponse resp) {
		infoBean.setMessageSource("/WEB-INF/msg.user/error.jsp");
		infoBean.setMessage("msg", UserMessagesConstants.LOGIN_FAILURE);
		return this;
	}
	
	public UserMessageController setLogoutSuccessForUI(HttpServletRequest req, HttpServletResponse resp) {
		infoBean.setMessage("msg", UserMessagesConstants.LOGOUT_SUCCESS);
		return this;
	}
	
	public UserMessageController setLogoutSuccessForWeb(HttpServletRequest req, HttpServletResponse resp) {
		infoBean.setMessageSource("/WEB-INF/msg.user/success.jsp");
		infoBean.setMessage("msg", UserMessagesConstants.LOGOUT_SUCCESS);
		return this;
	}
	
	public UserMessageController setDeleteSuccessForUI(HttpServletRequest req, HttpServletResponse resp) {
		infoBean.setMessage("msg", UserMessagesConstants.DELETE_SUCCESS);
		return this;
	}
	
	public UserMessageController setDeleteSuccessForWeb(HttpServletRequest req, HttpServletResponse resp) {
		infoBean.setMessageSource("/WEB-INF/msg.user/success.jsp");
		infoBean.setMessage("msg", UserMessagesConstants.DELETE_SUCCESS);
		return this;
	}
	
	public UserMessageController setDeleteGeneralFailureForUI(HttpServletRequest req, HttpServletResponse resp) {
		infoBean.setMessage("msg", UserMessagesConstants.DELETE_FAILURE);
		return this;
	}
	
	public UserMessageController setDeleteGeneralFailureForWeb(HttpServletRequest req, HttpServletResponse resp) {
		infoBean.setMessageSource("/WEB-INF/msg.user/error.jsp");
		infoBean.setMessage("msg", UserMessagesConstants.DELETE_FAILURE);
		return this;
	}
	
	public UserMessageController setUpdateSuccessForUI(HttpServletRequest req, HttpServletResponse resp) {
		infoBean.setMessage("msg", UserMessagesConstants.UPDATE_SUCCESS);
		return this;
	}
	
	public UserMessageController setUpdateSuccessForWeb(HttpServletRequest req, HttpServletResponse resp) {
		infoBean.setMessageSource("/WEB-INF/msg.user/success.jsp");
		infoBean.setMessage("msg", UserMessagesConstants.UPDATE_SUCCESS);
		return this;
	}
	
	public UserMessageController setUpdateGeneralFailureForUI(HttpServletRequest req, HttpServletResponse resp) {
		infoBean.setMessage("msg", UserMessagesConstants.UPDATE_FAILURE);
		return this;
	}
	
	public UserMessageController setUpdateGeneralFailureForWeb(HttpServletRequest req, HttpServletResponse resp) {
		infoBean.setMessageSource("/WEB-INF/msg.user/error.jsp");
		infoBean.setMessage("msg", UserMessagesConstants.UPDATE_FAILURE);
		return this;
	}
	
	public UserMessageController setUploadProfilePictureGeneralFailureForUI(HttpServletRequest req, HttpServletResponse resp) {
		infoBean.setMessage("msg", UserMessagesConstants.UPLOAD_PROFILE_PICTURE_FAILURE);
		return this;
	}
	
	public UserMessageController setUploadProfilePictureGeneralFailureForWeb(HttpServletRequest req, HttpServletResponse resp) {
		infoBean.setMessageSource("/WEB-INF/msg.user/error.jsp");
		infoBean.setMessage("msg", UserMessagesConstants.UPLOAD_PROFILE_PICTURE_FAILURE);
		return this;
	}
	
	public UserMessageController setUploadUserPictureGeneralFailureForUI(HttpServletRequest req, HttpServletResponse resp) {
		infoBean.setMessage("msg", UserMessagesConstants.UPLOAD_USER_PICTURE_FAILURE);
		return this;
	}
	
	public UserMessageController setUploadUserPictureGeneralFailureForWeb(HttpServletRequest req, HttpServletResponse resp) {
		infoBean.setMessageSource("/WEB-INF/msg.user/error.jsp");
		infoBean.setMessage("msg", UserMessagesConstants.UPLOAD_USER_PICTURE_FAILURE);
		return this;
	}
	
	public UserMessageController setUploadCountryPictureGeneralFailureForUI(HttpServletRequest req, HttpServletResponse resp) {
		infoBean.setMessage("msg", UserMessagesConstants.UPLOAD_COUNTRY_FLAG_PICTURE_FAILURE);
		return this;
	}
	
	public UserMessageController setUploadCountryPicturePictureGeneralFailureForWeb(HttpServletRequest req, HttpServletResponse resp) {
		infoBean.setMessageSource("/WEB-INF/msg.user/error.jsp");
		infoBean.setMessage("msg", UserMessagesConstants.UPLOAD_COUNTRY_FLAG_PICTURE_FAILURE);
		return this;
	}
}
