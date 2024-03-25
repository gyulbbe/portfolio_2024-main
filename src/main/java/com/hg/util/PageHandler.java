package com.hg.util;

public class PageHandler {

	private int currentPage; // 현재 페이지
	private int pageSize =10; // 한 페이지당 게시물 갯수+
	public  final int naviSize = 10; // page navigation size
	private int total; // 게시물의 총 갯수
	private int totalPage; // 전체 페이지의 갯수
	private int beginPage; // 화면에 보여줄 첫 페이지
	private int endPage; // 화면에 보여줄 마지막 페이지
	private int offset; //첫 게시물 -1

	public void doPaging(int currentPage, int total) {
		this.currentPage = currentPage;
		this.total = total;
		this.totalPage = totalPage();
		this.offset = calculateOffset(currentPage);
		this.beginPage = beginPage(currentPage);
		this.endPage = endPage();
	}
	
	//총 페이지 수 = (총 게시물/페이지 크기)올림
	public int totalPage() {
        return (int) Math.ceil(total / (double) pageSize);
    }

	//offSet = 시작 시 맨 처음 게시물
	public int calculateOffset(int currentPage) {
        return (currentPage - 1) * pageSize;
    }
	
	//끝 페이지
	public int endPage() {
		return Math.min(beginPage + naviSize - 1, totalPage);
	}
	
	//시작 페이지 = 현재페이지(10의자리 내림) +1
	public int beginPage(int currentPage) {
		return ((currentPage-1)/naviSize)*naviSize+1;
    }
	
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getBeginPage() {
		return beginPage;
	}
	public void setBeginPage(int beginPage) {
		this.beginPage = beginPage;
	}
	public int getEndPage() {
		return endPage;
	}
	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}
	public int getOffset() {
		return offset;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

}