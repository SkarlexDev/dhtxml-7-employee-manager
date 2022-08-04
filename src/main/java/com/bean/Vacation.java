package com.bean;

import java.time.LocalDate;

public class Vacation {

	private Long id;
	private LocalDate vacationFrom;
	private LocalDate vacationTo;
	private String reason;
	private Long employeeId;
		
	public Vacation() {}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public LocalDate getVacationFrom() {
		return vacationFrom;
	}
	public void setVacationFrom(LocalDate vacationFrom) {
		this.vacationFrom = vacationFrom;
	}
	public LocalDate getVacationTo() {
		return vacationTo;
	}
	public void setVacationTo(LocalDate vacationTo) {
		this.vacationTo = vacationTo;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public Long getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	@Override
	public String toString() {
		return "Vacation [id=" + id + ", vacationFrom=" + vacationFrom + ", vacationTo=" + vacationTo + ", reason="
				+ reason + ", employeeId=" + employeeId + "]";
	}
}
