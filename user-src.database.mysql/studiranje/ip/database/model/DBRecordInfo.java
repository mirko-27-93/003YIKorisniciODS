package studiranje.ip.database.model;

import java.io.Serializable;

/**
 * Опис записа колоне у бази података. 
 * @author mirko
 * @version 1.0
 */
public final class DBRecordInfo implements Serializable, Cloneable, Comparable<DBRecordInfo>{
	private static final long serialVersionUID = -6974076321237930936L;
	private String field = ""; 
	private String type = ""; 
	private String key = ""; 
	private String zero = ""; 
	private String classic = "";
	private String extra = "";
	
	private String fieldName = "field"; 
	private String typeName = "type"; 
	private String keyName = "key";
	private String zeroName = "null"; 
	private String classicName = "default"; 
	private String extraName = "extra"; 
	

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getFieldName() {
		return fieldName;
	}
	public String getTypeName() {
		return typeName;
	}
	public String getKeyName() {
		return keyName;
	}
	public String getZeroName() {
		return zeroName;
	}
	public String getClassicName() {
		return classicName;
	}
	public String getExtraName() {
		return extraName;
	}
	public String getField() {
		return field;
	}
	
	public void setField(String field) {
		if(field==null) field = ""; 
		this.field = field;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		if(type=="") type=""; 
		this.type = type;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		if(key==null) key=""; 
		this.key = key;
	}
	public String getZero() {
		return zero;
	}
	public void setZero(String zero) {
		if(zero==null) zero = "";
		this.zero = zero;
	}
	public String getClassic() {
		return classic;
	}
	public void setClassic(String classic) {
		if(classic==null) classic=""; 
		this.classic = classic;
	}
	public String getExtra() {
		if(extra==null) extra = ""; 
		return extra;
	}
	public void setExtra(String extra) {
		this.extra = extra;
	}
	
	public final String originalString() {
		return super.toString();
	}
	
	public final int originalCode() {
		return super.hashCode(); 
	}
	
	@Override 
	public String toString() {
		return field.toString(); 
	}
	
	@Override
	public int hashCode() {
		return field.hashCode(); 
	}
	
	public boolean fieldNameEquals(DBRecordInfo info) {
		return field.contentEquals(info.field);
	}
	
	public boolean fullEquals(DBRecordInfo info) {
		if(!field.contentEquals(info.field)) return false; 
		if(!type.contentEquals(info.type)) return false;
		if(!key.contentEquals(info.key)) return false;
		if(!zero.contentEquals(info.zero)) return false; 
		if(!classic.contentEquals(info.classic)) return false;
		if(!extra.contentEquals(info.extra)) return false; 
		return true;
	}
	
	@Override
	public boolean equals(Object info) {
		if(info instanceof DBRecordInfo) {
			return fullEquals((DBRecordInfo)info);
		}
		return false; 
	}
	
	@Override
	public int compareTo(DBRecordInfo o) {
		return field.compareTo(o.field);
	} 
	
	@Override
	public DBRecordInfo clone() {
		DBRecordInfo record = new DBRecordInfo(); 
		record.field = this.field; 
		record.classic = this.classic;
		record.extra = this.extra; 
		record.field = this.field; 
		record.key = this.key; 
		record.type = this.type; 
		record.zero = this.zero; 
		return record;
	}
}
