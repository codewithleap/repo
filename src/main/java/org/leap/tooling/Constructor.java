package org.leap.tooling;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

import com.google.gson.annotations.Expose;

@Generated("org.jsonschema2pojo")
public class Constructor {

	@Expose
	private Location location;
	@Expose
	private List<Object> modifiers = new ArrayList<Object>();
	@Expose
	private String name;
	@Expose
	private List<Object> parameters = new ArrayList<Object>();
	@Expose
	private List<Object> references = new ArrayList<Object>();
	@Expose
	private Object type;
	@Expose
	private String visibility;

	/**
	 * 
	 * @return The location
	 */
	public Location getLocation() {
		return location;
	}

	/**
	 * 
	 * @param location
	 *            The location
	 */
	public void setLocation(Location location) {
		this.location = location;
	}

	public Constructor withLocation(Location location) {
		this.location = location;
		return this;
	}

	/**
	 * 
	 * @return The modifiers
	 */
	public List<Object> getModifiers() {
		return modifiers;
	}

	/**
	 * 
	 * @param modifiers
	 *            The modifiers
	 */
	public void setModifiers(List<Object> modifiers) {
		this.modifiers = modifiers;
	}

	public Constructor withModifiers(List<Object> modifiers) {
		this.modifiers = modifiers;
		return this;
	}

	/**
	 * 
	 * @return The name
	 */
	public String getName() {
		return name;
	}

	/**
	 * 
	 * @param name
	 *            The name
	 */
	public void setName(String name) {
		this.name = name;
	}

	public Constructor withName(String name) {
		this.name = name;
		return this;
	}

	/**
	 * 
	 * @return The parameters
	 */
	public List<Object> getParameters() {
		return parameters;
	}

	/**
	 * 
	 * @param parameters
	 *            The parameters
	 */
	public void setParameters(List<Object> parameters) {
		this.parameters = parameters;
	}

	public Constructor withParameters(List<Object> parameters) {
		this.parameters = parameters;
		return this;
	}

	/**
	 * 
	 * @return The references
	 */
	public List<Object> getReferences() {
		return references;
	}

	/**
	 * 
	 * @param references
	 *            The references
	 */
	public void setReferences(List<Object> references) {
		this.references = references;
	}

	public Constructor withReferences(List<Object> references) {
		this.references = references;
		return this;
	}

	/**
	 * 
	 * @return The type
	 */
	public Object getType() {
		return type;
	}

	/**
	 * 
	 * @param type
	 *            The type
	 */
	public void setType(Object type) {
		this.type = type;
	}

	public Constructor withType(Object type) {
		this.type = type;
		return this;
	}

	/**
	 * 
	 * @return The visibility
	 */
	public String getVisibility() {
		return visibility;
	}

	/**
	 * 
	 * @param visibility
	 *            The visibility
	 */
	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}

	public Constructor withVisibility(String visibility) {
		this.visibility = visibility;
		return this;
	}

}