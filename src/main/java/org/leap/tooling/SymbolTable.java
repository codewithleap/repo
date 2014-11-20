package org.leap.tooling;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

import com.google.gson.annotations.Expose;

@Generated("org.jsonschema2pojo")
public class SymbolTable {

	@Expose
	private List<Constructor> constructors = new ArrayList<Constructor>();
	@Expose
	private List<Object> externalReferences = new ArrayList<Object>();
	@Expose
	private String id;
	@Expose
	private List<Object> innerClasses = new ArrayList<Object>();
	@Expose
	private List<Object> interfaces = new ArrayList<Object>();
	@Expose
	private String key;
	@Expose
	private List<Method> methods = new ArrayList<Method>();
	@Expose
	private String name;
	@Expose
	private String namespace;
	@Expose
	private List<Property> properties = new ArrayList<Property>();
	@Expose
	private TableDeclaration tableDeclaration;
	@Expose
	private List<Variable> variables = new ArrayList<Variable>();

	/**
	 * 
	 * @return The constructors
	 */
	public List<Constructor> getConstructors() {
		return constructors;
	}

	/**
	 * 
	 * @param constructors
	 *            The constructors
	 */
	public void setConstructors(List<Constructor> constructors) {
		this.constructors = constructors;
	}

	public SymbolTable withConstructors(List<Constructor> constructors) {
		this.constructors = constructors;
		return this;
	}

	/**
	 * 
	 * @return The externalReferences
	 */
	public List<Object> getExternalReferences() {
		return externalReferences;
	}

	/**
	 * 
	 * @param externalReferences
	 *            The externalReferences
	 */
	public void setExternalReferences(List<Object> externalReferences) {
		this.externalReferences = externalReferences;
	}

	public SymbolTable withExternalReferences(List<Object> externalReferences) {
		this.externalReferences = externalReferences;
		return this;
	}

	/**
	 * 
	 * @return The id
	 */
	public String getId() {
		return id;
	}

	/**
	 * 
	 * @param id
	 *            The id
	 */
	public void setId(String id) {
		this.id = id;
	}

	public SymbolTable withId(String id) {
		this.id = id;
		return this;
	}

	/**
	 * 
	 * @return The innerClasses
	 */
	public List<Object> getInnerClasses() {
		return innerClasses;
	}

	/**
	 * 
	 * @param innerClasses
	 *            The innerClasses
	 */
	public void setInnerClasses(List<Object> innerClasses) {
		this.innerClasses = innerClasses;
	}

	public SymbolTable withInnerClasses(List<Object> innerClasses) {
		this.innerClasses = innerClasses;
		return this;
	}

	/**
	 * 
	 * @return The interfaces
	 */
	public List<Object> getInterfaces() {
		return interfaces;
	}

	/**
	 * 
	 * @param interfaces
	 *            The interfaces
	 */
	public void setInterfaces(List<Object> interfaces) {
		this.interfaces = interfaces;
	}

	public SymbolTable withInterfaces(List<Object> interfaces) {
		this.interfaces = interfaces;
		return this;
	}

	/**
	 * 
	 * @return The key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * 
	 * @param key
	 *            The key
	 */
	public void setKey(String key) {
		this.key = key;
	}

	public SymbolTable withKey(String key) {
		this.key = key;
		return this;
	}

	/**
	 * 
	 * @return The methods
	 */
	public List<Method> getMethods() {
		return methods;
	}

	/**
	 * 
	 * @param methods
	 *            The methods
	 */
	public void setMethods(List<Method> methods) {
		this.methods = methods;
	}

	public SymbolTable withMethods(List<Method> methods) {
		this.methods = methods;
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

	public SymbolTable withName(String name) {
		this.name = name;
		return this;
	}

	/**
	 * 
	 * @return The namespace
	 */
	public String getNamespace() {
		return namespace;
	}

	/**
	 * 
	 * @param namespace
	 *            The namespace
	 */
	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public SymbolTable withNamespace(String namespace) {
		this.namespace = namespace;
		return this;
	}

	/**
	 * 
	 * @return The properties
	 */
	public List<Property> getProperties() {
		return properties;
	}

	/**
	 * 
	 * @param properties
	 *            The properties
	 */
	public void setProperties(List<Property> properties) {
		this.properties = properties;
	}

	public SymbolTable withProperties(List<Property> properties) {
		this.properties = properties;
		return this;
	}

	/**
	 * 
	 * @return The tableDeclaration
	 */
	public TableDeclaration getTableDeclaration() {
		return tableDeclaration;
	}

	/**
	 * 
	 * @param tableDeclaration
	 *            The tableDeclaration
	 */
	public void setTableDeclaration(TableDeclaration tableDeclaration) {
		this.tableDeclaration = tableDeclaration;
	}

	public SymbolTable withTableDeclaration(TableDeclaration tableDeclaration) {
		this.tableDeclaration = tableDeclaration;
		return this;
	}

	/**
	 * 
	 * @return The variables
	 */
	public List<Variable> getVariables() {
		return variables;
	}

	/**
	 * 
	 * @param variables
	 *            The variables
	 */
	public void setVariables(List<Variable> variables) {
		this.variables = variables;
	}

	public SymbolTable withVariables(List<Variable> variables) {
		this.variables = variables;
		return this;
	}

}