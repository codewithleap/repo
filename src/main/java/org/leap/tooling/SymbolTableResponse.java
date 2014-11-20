package org.leap.tooling;

import javax.annotation.Generated;

import com.google.gson.annotations.Expose;

@Generated("org.jsonschema2pojo")
public class SymbolTableResponse {

	@Expose
	private Attributes attributes;
	@Expose
	private String Id;
	@Expose
	private String NamespacePrefix;
	@Expose
	private String Name;
	@Expose
	private Double ApiVersion;
	@Expose
	private String Status;
	@Expose
	private Boolean IsValid;
	// @Expose
	// private Integer BodyCrc;
	@Expose
	private String Body;
	@Expose
	private Integer LengthWithoutComments;
	@Expose
	private String CreatedDate;
	@Expose
	private String CreatedById;
	@Expose
	private String LastModifiedDate;
	@Expose
	private String LastModifiedById;
	@Expose
	private String SystemModstamp;
	@Expose
	private org.leap.tooling.SymbolTable SymbolTable;
	@Expose
	private org.leap.tooling.Metadata Metadata;
	@Expose
	private String FullName;

	/**
	 * 
	 * @return The attributes
	 */
	public Attributes getAttributes() {
		return attributes;
	}

	/**
	 * 
	 * @param attributes
	 *            The attributes
	 */
	public void setAttributes(Attributes attributes) {
		this.attributes = attributes;
	}

	public SymbolTableResponse withAttributes(Attributes attributes) {
		this.attributes = attributes;
		return this;
	}

	/**
	 * 
	 * @return The Id
	 */
	public String getId() {
		return Id;
	}

	/**
	 * 
	 * @param Id
	 *            The Id
	 */
	public void setId(String Id) {
		this.Id = Id;
	}

	public SymbolTableResponse withId(String Id) {
		this.Id = Id;
		return this;
	}

	/**
	 * 
	 * @return The NamespacePrefix
	 */
	public String getNamespacePrefix() {
		return NamespacePrefix;
	}

	/**
	 * 
	 * @param NamespacePrefix
	 *            The NamespacePrefix
	 */
	public void setNamespacePrefix(String NamespacePrefix) {
		this.NamespacePrefix = NamespacePrefix;
	}

	public SymbolTableResponse withNamespacePrefix(String NamespacePrefix) {
		this.NamespacePrefix = NamespacePrefix;
		return this;
	}

	/**
	 * 
	 * @return The Name
	 */
	public String getName() {
		return Name;
	}

	/**
	 * 
	 * @param Name
	 *            The Name
	 */
	public void setName(String Name) {
		this.Name = Name;
	}

	public SymbolTableResponse withName(String Name) {
		this.Name = Name;
		return this;
	}

	/**
	 * 
	 * @return The ApiVersion
	 */
	public Double getApiVersion() {
		return ApiVersion;
	}

	/**
	 * 
	 * @param ApiVersion
	 *            The ApiVersion
	 */
	public void setApiVersion(Double ApiVersion) {
		this.ApiVersion = ApiVersion;
	}

	public SymbolTableResponse withApiVersion(Double ApiVersion) {
		this.ApiVersion = ApiVersion;
		return this;
	}

	/**
	 * 
	 * @return The Status
	 */
	public String getStatus() {
		return Status;
	}

	/**
	 * 
	 * @param Status
	 *            The Status
	 */
	public void setStatus(String Status) {
		this.Status = Status;
	}

	public SymbolTableResponse withStatus(String Status) {
		this.Status = Status;
		return this;
	}

	/**
	 * 
	 * @return The IsValid
	 */
	public Boolean getIsValid() {
		return IsValid;
	}

	/**
	 * 
	 * @param IsValid
	 *            The IsValid
	 */
	public void setIsValid(Boolean IsValid) {
		this.IsValid = IsValid;
	}

	public SymbolTableResponse withIsValid(Boolean IsValid) {
		this.IsValid = IsValid;
		return this;
	}

	/**
	 * 
	 * @return The BodyCrc
	 */
	/*
	 * public Integer getBodyCrc() { return BodyCrc; }
	 */
	/**
	 * 
	 * @param BodyCrc
	 *            The BodyCrc
	 */
	/*
	 * public void setBodyCrc(Integer BodyCrc) { this.BodyCrc = BodyCrc; }
	 * 
	 * public SymbolTableResponse withBodyCrc(Integer BodyCrc) { this.BodyCrc =
	 * BodyCrc; return this; }
	 */

	/**
	 * 
	 * @return The Body
	 */
	public String getBody() {
		return Body;
	}

	/**
	 * 
	 * @param Body
	 *            The Body
	 */
	public void setBody(String Body) {
		this.Body = Body;
	}

	public SymbolTableResponse withBody(String Body) {
		this.Body = Body;
		return this;
	}

	/**
	 * 
	 * @return The LengthWithoutComments
	 */
	public Integer getLengthWithoutComments() {
		return LengthWithoutComments;
	}

	/**
	 * 
	 * @param LengthWithoutComments
	 *            The LengthWithoutComments
	 */
	public void setLengthWithoutComments(Integer LengthWithoutComments) {
		this.LengthWithoutComments = LengthWithoutComments;
	}

	public SymbolTableResponse withLengthWithoutComments(
			Integer LengthWithoutComments) {
		this.LengthWithoutComments = LengthWithoutComments;
		return this;
	}

	/**
	 * 
	 * @return The CreatedDate
	 */
	public String getCreatedDate() {
		return CreatedDate;
	}

	/**
	 * 
	 * @param CreatedDate
	 *            The CreatedDate
	 */
	public void setCreatedDate(String CreatedDate) {
		this.CreatedDate = CreatedDate;
	}

	public SymbolTableResponse withCreatedDate(String CreatedDate) {
		this.CreatedDate = CreatedDate;
		return this;
	}

	/**
	 * 
	 * @return The CreatedById
	 */
	public String getCreatedById() {
		return CreatedById;
	}

	/**
	 * 
	 * @param CreatedById
	 *            The CreatedById
	 */
	public void setCreatedById(String CreatedById) {
		this.CreatedById = CreatedById;
	}

	public SymbolTableResponse withCreatedById(String CreatedById) {
		this.CreatedById = CreatedById;
		return this;
	}

	/**
	 * 
	 * @return The LastModifiedDate
	 */
	public String getLastModifiedDate() {
		return LastModifiedDate;
	}

	/**
	 * 
	 * @param LastModifiedDate
	 *            The LastModifiedDate
	 */
	public void setLastModifiedDate(String LastModifiedDate) {
		this.LastModifiedDate = LastModifiedDate;
	}

	public SymbolTableResponse withLastModifiedDate(String LastModifiedDate) {
		this.LastModifiedDate = LastModifiedDate;
		return this;
	}

	/**
	 * 
	 * @return The LastModifiedById
	 */
	public String getLastModifiedById() {
		return LastModifiedById;
	}

	/**
	 * 
	 * @param LastModifiedById
	 *            The LastModifiedById
	 */
	public void setLastModifiedById(String LastModifiedById) {
		this.LastModifiedById = LastModifiedById;
	}

	public SymbolTableResponse withLastModifiedById(String LastModifiedById) {
		this.LastModifiedById = LastModifiedById;
		return this;
	}

	/**
	 * 
	 * @return The SystemModstamp
	 */
	public String getSystemModstamp() {
		return SystemModstamp;
	}

	/**
	 * 
	 * @param SystemModstamp
	 *            The SystemModstamp
	 */
	public void setSystemModstamp(String SystemModstamp) {
		this.SystemModstamp = SystemModstamp;
	}

	public SymbolTableResponse withSystemModstamp(String SystemModstamp) {
		this.SystemModstamp = SystemModstamp;
		return this;
	}

	/**
	 * 
	 * @return The SymbolTable
	 */
	public org.leap.tooling.SymbolTable getSymbolTable() {
		return SymbolTable;
	}

	/**
	 * 
	 * @param SymbolTable
	 *            The SymbolTable
	 */
	public void setSymbolTable(org.leap.tooling.SymbolTable SymbolTable) {
		this.SymbolTable = SymbolTable;
	}

	public SymbolTableResponse withSymbolTable(
			org.leap.tooling.SymbolTable SymbolTable) {
		this.SymbolTable = SymbolTable;
		return this;
	}

	/**
	 * 
	 * @return The Metadata
	 */
	public org.leap.tooling.Metadata getMetadata() {
		return Metadata;
	}

	/**
	 * 
	 * @param Metadata
	 *            The Metadata
	 */
	public void setMetadata(org.leap.tooling.Metadata Metadata) {
		this.Metadata = Metadata;
	}

	public SymbolTableResponse withMetadata(org.leap.tooling.Metadata Metadata) {
		this.Metadata = Metadata;
		return this;
	}

	/**
	 * 
	 * @return The FullName
	 */
	public String getFullName() {
		return FullName;
	}

	/**
	 * 
	 * @param FullName
	 *            The FullName
	 */
	public void setFullName(String FullName) {
		this.FullName = FullName;
	}

	public SymbolTableResponse withFullName(String FullName) {
		this.FullName = FullName;
		return this;
	}

}