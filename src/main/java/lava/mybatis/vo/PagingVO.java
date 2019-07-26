package lava.mybatis.vo;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * PagingVO
 */
@XmlRootElement(name = "paging")
public class PagingVO<M> implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	private int pageSize = 10; // 每页的记录数
	private int pageNum = 1; // 当前页
	private long total = 0; // 总记录数量
	private long totalPages; // 总页数
	private List<M> list = new ArrayList<M>();

	public PagingVO() {

	}

	public PagingVO(Integer pageNum, Integer pageSize) {
		if (pageNum != null) {
			this.pageNum = pageNum;
		}
		if (pageSize != null) {
			this.pageSize = pageSize;
		}
	}
	
	public PagingVO(int pageSize, int pageNum, long total, List<M> list) {
		this.pageSize = pageSize;
		this.pageNum = pageNum;
		this.total = total;
		this.list = list;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public long getTotalPages() {
		if (total > 0) {
			totalPages = total % pageSize == 0 ? (total / pageSize) : (total / pageSize + 1);
		}
		return totalPages;
	}

	public void setTotalPages(long totalPages) {
		this.totalPages = totalPages;
	}

	public List<M> getList() {
		return list;
	}

	public void setList(List<M> list) {
		this.list = list;
	}
}
