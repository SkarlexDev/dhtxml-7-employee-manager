package com.bean.jsonhelper.json;

public class JsonBeanHelper {

	private String id;
	private Long value;
	private String color;
	private String month;
	

	public JsonBeanHelper(String id, Long value, String color) {
		this.id = id;
		this.value = value;
		this.color = color;
		this.month = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getValue() {
		return value;
	}

	public void setValue(Long value) {
		this.value = value;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}
	
	public void setIdAndMonth(String val) {
		this.id = val;
		this.month = val;
	}

	@Override
	public String toString() {
		return "VacationSortedData [id=" + id + ", value=" + value + ", color=" + color + ", month=" + month + "]";
	}

}
