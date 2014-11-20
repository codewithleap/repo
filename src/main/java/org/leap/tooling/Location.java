package org.leap.tooling;

import javax.annotation.Generated;

import com.google.gson.annotations.Expose;

@Generated("org.jsonschema2pojo")
public class Location {

	@Expose
	private Integer column;
	@Expose
	private Integer line;

	/**
	 * 
	 * @return The column
	 */
	public Integer getColumn() {
		return column;
	}

	/**
	 * 
	 * @param column
	 *            The column
	 */
	public void setColumn(Integer column) {
		this.column = column;
	}

	public Location withColumn(Integer column) {
		this.column = column;
		return this;
	}

	/**
	 * 
	 * @return The line
	 */
	public Integer getLine() {
		return line;
	}

	/**
	 * 
	 * @param line
	 *            The line
	 */
	public void setLine(Integer line) {
		this.line = line;
	}

	public Location withLine(Integer line) {
		this.line = line;
		return this;
	}

}