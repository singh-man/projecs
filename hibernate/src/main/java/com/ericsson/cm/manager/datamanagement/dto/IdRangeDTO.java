package com.ericsson.cm.manager.datamanagement.dto;

/**
 * Data Transfer object for IDRange
 */
public class IdRangeDTO {
	private String name;
	private Long startValue;
	private Long rangeLength;

	public IdRangeDTO() {
		super();
	}

	public IdRangeDTO(String name, Long startValue, Long rangeLength) {
		super();
		this.name = name;
		this.startValue = startValue;
		this.rangeLength = rangeLength;
	}

	public IdRangeDTO(IdRangeDTO idRange) {
		super();
		this.name = idRange.name;
		this.rangeLength = idRange.rangeLength;
		this.startValue = idRange.startValue;
	}

	/**
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Method to set name
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return start value.
	 */
	public Long getStartValue() {
		return startValue;
	}

	/**
	 * Method to set start value
	 * 
	 * @param startValue
	 */
	public void setStartValue(Long startValue) {
		this.startValue = startValue;
	}

	/**
	 * @return range length
	 */
	public Long getRangeLength() {
		return rangeLength;
	}

	/**
	 * Method to set rangeLength
	 * 
	 * @param rangeLength
	 */
	public void setRangeLength(Long rangeLength) {
		this.rangeLength = rangeLength;
	}
}
