package studiranje.ip.model;

import java.io.Serializable;

/**
 * Подаци који се чувају о сесијама, накнадни. 
 * @author mirko
 * @version 1.0
 */
public final class SessionInfo implements Serializable, Comparable<SessionInfo>{
	private static final long serialVersionUID = 1541949808483633945L;
	
	private String sessionId = "";
	private String systemId  = ""; 
	private String platformId = ""; 
	private String machineId = ""; 
	private String applicationId = ""; 
	private String partId = ""; 
	private String userId = ""; 
	private String basicId = ""; 
	
	private String description = ""; 
	private String otherData = ""; 
	
	public SessionInfo(String id) {
		if(id==null) throw new NullPointerException();
		sessionId = id; 
	}
	
	public SessionInfo(SessionInfo info) {
		sessionId = info.sessionId; 
		systemId = info.systemId; 
		platformId = info.platformId; 
		machineId = info.machineId; 
		applicationId = info.applicationId; 
		partId = info.partId; 
		description = info.description; 
		otherData = info.otherData; 
		userId = info.userId; 
		basicId = info.basicId; 
	}
	
	public SessionInfo(String id, SessionInfo info) {
		if(id==null) throw new NullPointerException();
		sessionId = id; 
		systemId = info.systemId; 
		platformId = info.platformId; 
		machineId = info.machineId; 
		applicationId = info.applicationId; 
		partId = info.partId; 
		description = info.description; 
		otherData = info.otherData; 
		userId = info.userId; 
		basicId = info.basicId;
	}
	
	public String getSessionId() {
		return sessionId;
	}
	

	public String getSystemId() {
		return systemId;
	}
	public void setSystemId(String systemId) {
		if(systemId==null) systemId = "";  
		this.systemId = systemId;
	}
	
	
	public String getPlatformId() {
		return platformId;
	}
	public void setPlatformId(String platformId) {
		if(platformId==null) platformId = ""; 
		this.platformId = platformId;
	}
	
	
	public String getMachineId() {
		return machineId;
	}
	public void setMachineId(String machineId) {
		if(machineId==null) machineId = ""; 
		this.machineId = machineId;
	}
	
	
	public String getApplicationId() {
		return applicationId;
	}
	public void setApplicationId(String applicationId) {
		if(applicationId==null) applicationId = ""; 
		this.applicationId = applicationId;
	}

	
	public String getPartId() {
		return partId;
	}
	public void setPartId(String partId) {
		if(partId==null) partId=""; 
		this.partId = partId;
	}
	
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		if(description==null) description =  ""; 
		this.description = description;
	}
	
	
	public String getOtherData() {
		return otherData;
	}
	public void setOtherData(String otherData) {
		if(otherData==null) otherData = ""; 
		this.otherData = otherData;
	}

	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		if(userId==null) userId=""; 
		this.userId = userId;
	}

	
	public String getBasicId() {
		return basicId;
	}
	public void setBasicId(String basicId) {
		if(basicId==null) basicId = "";
		this.basicId = basicId;
	}
	

	public final String originalString() {
		return super.toString(); 
	}
	public final int originalCode() {
		return super.hashCode(); 
	}
	
	
	@Override
	public String toString() {
		return sessionId; 
	}
	@Override
	public int hashCode() {
		return sessionId.hashCode(); 
	}
	
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof SessionInfo) {
			SessionInfo si = (SessionInfo) o; 
			return sessionId.contentEquals(si.sessionId); 
		}
		return false;
	}
	@Override
	public int compareTo(SessionInfo o) {
		return sessionId.compareTo(o.sessionId);
	}
	
	public boolean fullEquals(SessionInfo info) {
		if(!sessionId.contentEquals(info.sessionId)) return false; 
		if(!systemId.contentEquals(info.systemId)) return false; 
		if(!platformId.contentEquals(info.platformId)) return false; 
		if(!applicationId.contentEquals(info.applicationId)) return false; 
		if(!userId.contentEquals(info.userId)) return false; 
		if(!partId.contentEquals(info.userId)) return false; 
		if(!basicId.contentEquals(info.basicId)) return false; 
		if(!applicationId.contentEquals(info.machineId)) return false; 
		if(!description.contentEquals(info.description)) return false; 
		if(!otherData.contentEquals(info.otherData)) return false; 
		return true; 
	}
	
	
	public String getOtherDataFirstLine() {
		if(!otherData.contains("\n")) return otherData; 
		return otherData.split("\n",2)[0];
	}
	
	public String getOtherDataWithoutFirstLine() {
		if(!otherData.contains("\n")) return ""; 
		return otherData.split("\n",2)[1];
	}
	
	public String getOtherDataWithoutSecondLine() {
		if(!otherData.contains("\n")) return ""; 
		return otherData.split("\n")[1];
	}
	
	public String getAppTypeLine() {
		if(getOtherDataFirstLine().trim().contentEquals("text/plain"))
			return getOtherDataWithoutSecondLine(); 
		return ""; 
	}
}
