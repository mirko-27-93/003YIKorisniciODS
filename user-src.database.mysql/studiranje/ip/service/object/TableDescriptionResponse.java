package studiranje.ip.service.object;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import studiranje.ip.database.model.DBRecordInfo;

/**
 * Информације о табели добијене са сервиса. 
 * @author mirko
 * @version 1.0
 */
public class TableDescriptionResponse implements Serializable{
	private static final long serialVersionUID = -529770583447962497L;
	private List<DBRecordInfo> columns = new ArrayList<>();
	
	public List<DBRecordInfo> getColumns() {
		return columns;
	}
	public List<DBRecordInfo> liveColumns(){
		return columns;
	}
	public List<DBRecordInfo> applyColumns(){
		return new ArrayList<>(columns);
	}
	public synchronized List<DBRecordInfo> cloneColumns(){
		ArrayList<DBRecordInfo> list = new ArrayList<>();
		for(DBRecordInfo record: columns) {
			list.add(record.clone()); 
		}
		return list;
	}
	
	public void setColumns(List<DBRecordInfo> columns) {
		this.columns = columns;
	}
	public void aliasColumns(List<DBRecordInfo> columns) {
		this.columns = columns;
	}
	public void applyColums(List<DBRecordInfo> columns) {
		this.columns = new ArrayList<>();
	}
	public synchronized void flatColumns(List<DBRecordInfo> columns) {
		this.columns = new ArrayList<>(); 
		for(DBRecordInfo column: columns) {
			this.columns.add(column.clone()); 
		}
	}
}
