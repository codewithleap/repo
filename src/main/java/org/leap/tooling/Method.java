package org.leap.tooling;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

import com.google.gson.annotations.Expose;

@Generated("org.jsonschema2pojo")
public class Method {

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
	private String returnType;
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

	public Method withLocation(Location location) {
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

	public Method withModifiers(List<Object> modifiers) {
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

	public Method withName(String name) {
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

	public Method withParameters(List<Object> parameters) {
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

	public Method withReferences(List<Object> references) {
		this.references = references;
		return this;
	}

	/**
	 * 
	 * @return The returnType
	 */
	public String getReturnType() {
		return returnType;
	}

	/**
	 * 
	 * @param returnType
	 *            The returnType
	 */
	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}

	public Method withReturnType(String returnType) {
		this.returnType = returnType;
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

	public Method withType(Object type) {
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

	public Method withVisibility(String visibility) {
		this.visibility = visibility;
		return this;
	}

}