package com.arrowso.core.data;

import java.io.Serializable;
import java.util.Arrays;


public class WebsiteInfo implements Serializable {

	private static final long serialVersionUID = -2058645002573292840L;

	private String title;
	private String description;
	private String[] keyworlds;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String[] getKeyworlds() {
		return keyworlds;
	}
	public void setKeyworlds(String[] keyworlds) {
		this.keyworlds = keyworlds;
	}
	@Override
	public String toString() {
		return "WebsiteInfo [title=" + title + ", description=" + description + ", keyworlds="
				+ Arrays.toString(keyworlds) + "]";
	}
}
