package com.omnisource.shopstyle.request;

public class SearchProductRequest {

	private String fts;
	private String[] filters;

	public String[] getFilters() {
		return filters;
	}

	public void setFilters(String[] filters) {
		this.filters = filters;
	}

	public String getFts() {
		return fts;
	}

	public void setFts(String fts) {
		this.fts = fts;
	}

}
