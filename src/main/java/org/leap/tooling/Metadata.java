package org.leap.tooling;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

import com.google.gson.annotations.Expose;

@Generated("org.jsonschema2pojo")
public class Metadata {

	@Expose
	private Double apiVersion;
	@Expose
	private List<Object> packageVersions = new ArrayList<Object>();
	@Expose
	private String status;
	@Expose
	private Object urls;

	/**
	 * 
	 * @return The apiVersion
	 */
	public Double getApiVersion() {
		return apiVersion;
	}

	/**
	 * 
	 * @param apiVersion
	 *            The apiVersion
	 */
	public void setApiVersion(Double apiVersion) {
		this.apiVersion = apiVersion;
	}

	public Metadata withApiVersion(Double apiVersion) {
		this.apiVersion = apiVersion;
		return this;
	}

	/**
	 * 
	 * @return The packageVersions
	 */
	public List<Object> getPackageVersions() {
		return packageVersions;
	}

	/**
	 * 
	 * @param packageVersions
	 *            The packageVersions
	 */
	public void setPackageVersions(List<Object> packageVersions) {
		this.packageVersions = packageVersions;
	}

	public Metadata withPackageVersions(List<Object> packageVersions) {
		this.packageVersions = packageVersions;
		return this;
	}

	/**
	 * 
	 * @return The status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * 
	 * @param status
	 *            The status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	public Metadata withStatus(String status) {
		this.status = status;
		return this;
	}

	/**
	 * 
	 * @return The urls
	 */
	public Object getUrls() {
		return urls;
	}

	/**
	 * 
	 * @param urls
	 *            The urls
	 */
	public void setUrls(Object urls) {
		this.urls = urls;
	}

	public Metadata withUrls(Object urls) {
		this.urls = urls;
		return this;
	}

}