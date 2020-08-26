
package studiranje.ip.bean;

import java.io.Serializable;

/**
 * Зрно које се користи за страничење и филтрирање. 
 * @author mirko
 * @version 1.0
 */
public class PageBean implements Serializable{
	private static final long serialVersionUID = -8885474450263808592L;
	private int pageNo = 0; 
	private int pageSize = 10;
	private int totalCount = 0; 
	private String startFilter = "";
	
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		if(pageNo<0) pageNo = 0; 
		this.pageNo = pageNo;
	}
	public int getPageSize() {
		if(pageSize<1) pageSize=1; 
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public String getStartFilter() {
		return startFilter;
	}
	public void setStartFilter(String startFilter) {
		if(startFilter==null) startFilter = "";
		this.startFilter = startFilter;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		if(totalCount<0) totalCount=0; 
		this.totalCount = totalCount;
	} 
	public int pageCount() {
		return totalCount/pageSize+(totalCount%pageSize==0?0:1); 
	}
	public int itemsInPage() {
		if(pageNo==0) return 0; 
		if(pageNo%pageSize==0) return pageSize;
		if(pageNo<totalCount/pageSize+1) return pageSize;
		else if(pageNo==totalCount/pageSize) return pageNo%pageSize; 
		else return 0;
	}
	public void nextPage() {
		pageNo++; 
	}
	public void previousPage() {
		pageNo--; 
		if(pageNo<0) pageNo=0;
	}
	public int pageFor(int index) {
		if(index<0) index=0; 
		return index/pageSize;
	}
	public int indexStartOfPage() {
		if(pageNo==0) return pageNo; 
		return Math.min(totalCount, pageSize*pageNo-pageSize);
	}
	
	public String getSQLEscapeStartFilter() {
		return startFilter.replaceAll("%", "\\\\%").replaceAll("_","\\\\_");
	}
}
