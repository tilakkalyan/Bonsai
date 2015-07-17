package com.bonsai.common;

import java.io.Serializable;

import com.bonsai.dao.BonsaiDAO;

public class SearchParams implements Serializable{

	private String searchColumn = "Name";
	private String searchValue1;
	private String searchValue2;
	private String pageValue;
	
	public SearchParams(){
		this.searchColumn = "Name";
	}
	
	public SearchParams(String columnName){
		this.searchColumn = columnName;
	}
	
	public String getSearchColumn() {
		return searchColumn;
	}
	public void setSearchColumn(String searchColumn) {
		this.searchColumn = searchColumn;
	}
	public String getSearchValue1() {
		return searchValue1;
	}
	public void setSearchValue1(String searchValue1) {
		this.searchValue1 = searchValue1;
	}
	public String getSearchValue2() {
		return searchValue2;
	}
	public void setSearchValue2(String searchValue2) {
		this.searchValue2 = searchValue2;
	}
	
	public static SearchParams byRaasi(){
		return new SearchParams(BonsaiDAO.COLUMN_NAME_RAASI);
	}
	
	public static SearchParams byPlanet(){
		return new SearchParams(BonsaiDAO.COLUMN_NAME_PLANET);
	}
	
	public static SearchParams byStar(){
		return new SearchParams(BonsaiDAO.COLUMN_NAME_STAR);
	}

	public static SearchParams byRaaga(){
		return new SearchParams(BonsaiDAO.COLUMN_NAME_RAAGA);
	}
	
	public static SearchParams byStyle(){
		return new SearchParams(BonsaiDAO.COLUMN_NAME_STYLE);
	}
	
	public static SearchParams byDate(){
		return new SearchParams(BonsaiDAO.COLUMN_NAME_DATE);
	}

	public String getPageValue() {
		return pageValue;
	}

	public void setPageValue(String pageValue) {
		this.pageValue = pageValue;
	}

}
