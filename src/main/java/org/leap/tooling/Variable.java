package org.leap.tooling;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

import com.google.gson.annotations.Expose;

@Generated("org.jsonschema2pojo")
public class Variable {

	@Expose
	private Location location;
	@Expose
	private List<Object> modifiers = new ArrayList<Object>();
	@Expose
	private String name;
	@Expose
	private List<Object> references = new ArrayList<Object>();
	@Expose
	private String type;

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

	public Variable withLocation(Location location) {
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

	public Variable withModifiers(List<Object> modifiers) {
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

	public Variable withName(String name) {
		this.name = name;
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

	public Variable withReferences(List<Object> references) {
		this.references = references;
		return this;
	}

	/**
	 * 
	 * @return The type
	 */
	public String getType() {
		return type;
	}

	/**
	 * 
	 * @param type
	 *            The type
	 */
	public void setType(String type) {
		this.type = type;
	}

	public Variable withType(String type) {
		this.type = type;
		return this;
	}

}