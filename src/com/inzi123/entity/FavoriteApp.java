package com.inzi123.entity;

import java.io.Serializable;

public class FavoriteApp implements Serializable {

	public int id;
	public String title;
	public String iconpackage;
	public String iconresource;
	public String uri;
	public String displaymode;

	public FavoriteApp(int id, String title, String iconpackage,
			String iconresource, String uri, String displaymode) {
		super();
		this.id = id;
		this.title = title;
		this.iconpackage = iconpackage;
		this.iconresource = iconresource;
		this.uri = uri;
		this.displaymode = displaymode;
	}

	public FavoriteApp() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIconpackage() {
		return iconpackage;
	}

	public void setIconpackage(String iconpackage) {
		this.iconpackage = iconpackage;
	}

	public String getIconresource() {
		return iconresource;
	}

	public void setIconresource(String iconresource) {
		this.iconresource = iconresource;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getDisplaymode() {
		return displaymode;
	}

	public void setDisplaymode(String displaymode) {
		this.displaymode = displaymode;
	}

}
