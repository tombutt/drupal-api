package com.redhat.drupal.entities;

import com.google.gson.Gson;

public class TaxonomyTerm {
	private Integer tid;
	private Integer vid;
	private String name;
	
	public static TaxonomyTerm fromJson(String json) {
		Gson gson = new Gson();
		return gson.fromJson(json, TaxonomyTerm.class);
	}
	
	public String toJson() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}
	
	public Integer getTid() {
		return tid;
	}
	public void setTid(Integer tid) {
		this.tid = tid;
	}
	public Integer getVid() {
		return vid;
	}
	public void setVid(Integer vid) {
		this.vid = vid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
