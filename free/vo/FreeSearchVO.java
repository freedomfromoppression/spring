package com.future.my.free.vo;

import com.future.my.commons.vo.PagingVO;

public class FreeSearchVO extends PagingVO{

	private String searchWord; 			 /*검색어*/
	private String searchType;           /*검색컬럼*/      																	
	private String searchCategory;		 /*글 분류*/
	public String getSearchWord() {
		return searchWord;
	}
	public void setSearchWord(String searchWord) {
		this.searchWord = searchWord;
	}
	public String getSearchType() {
		return searchType;
	}
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}
	public String getSearchCategory() {
		return searchCategory;
	}
	public void setSearchCategory(String searchCategory) {
		this.searchCategory = searchCategory;
	}
	@Override
	public String toString() {
		return "FreeSearchVO [searchWord=" + searchWord + ", searchType=" + searchType + ", searchCategory="
				+ searchCategory + "]";
	}

}