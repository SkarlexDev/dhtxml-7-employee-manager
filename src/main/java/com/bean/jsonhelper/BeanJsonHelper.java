package com.bean.jsonhelper;

public class BeanJsonHelper {

	private Long total;
	private Long value1;
	private Long value2;
	private Long value3;
	
	public Long getValue1() {
		return value1;
	}
	public void setValue1(Long value1) {
		this.value1 = value1;
	}
	public Long getValue2() {
		return value2;
	}
	public void setValue2(Long value2) {
		this.value2 = value2;
	}
	public Long getValue3() {
		return value3;
	}
	public void setValue3(Long value3) {
		this.value3 = value3;
	}
	public Long getTotal() {
		return total;
	}
		
	public void setTotal(Long total) {
		this.total = total;
	}
	public void setAccountsTotal() {
		this.total = this.value1 + this.value2;
	}
	
	public void setEmployeeTotalUsers() {
		this.value3 = this.total - this.value1 - this.value2;
	}
	
	public void setVacationsTotal() {
		this.total = this.value1 + this.value2 + this.value3;
	}
	
	
}
