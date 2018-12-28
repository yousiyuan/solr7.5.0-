package com.solr.demo.entity;

import java.util.Date;

import org.apache.solr.client.solrj.beans.Field;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class Products {

	@Field(value = "id")
	private String pid;
	@Field(value = "title")
	private String name;
	@Field(value = "catalog_i")
	private Integer catalog;
	@Field(value = "catalog_name_txt_ik")
	private String catalogName;
	@Field(value = "price_f")
	private Float price;
	@Field(value = "number_i")
	private Integer number;
	@JSONField(serialize = false)
	@Field(value = "desc")
	private String description;
	@Field(value = "picture_txt_ik")
	private String picture;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	@Field(value = "release_time_dt")
	private Date releaseTime;

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCatalog() {
		return catalog;
	}

	public void setCatalog(Integer catalog) {
		this.catalog = catalog;
	}

	public String getCatalogName() {
		return catalogName;
	}

	public void setCatalogName(String catalogName) {
		this.catalogName = catalogName;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public Date getReleaseTime() {
		return releaseTime;
	}

	public void setReleaseTime(Date releaseTime) {
		this.releaseTime = releaseTime;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this, SerializerFeature.WRITE_MAP_NULL_FEATURES, SerializerFeature.QuoteFieldNames);
	}

}
