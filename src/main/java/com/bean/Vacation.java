package com.bean;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(value = { "id" })
public class Vacation {

	private Long id;
	private LocalDate vacationFrom;
	private LocalDate vacationTo;
	private String reason;
	private String status;
	private Long employeeId;

	public Vacation() {
	}

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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}
	
	public String uploadProtect() {
		return this.getStatus().contains("Declined") ? status : this.hashCode() + "";
	}

	@Override
	public String toString() {
		return "Vacation [id=" + id + ", vacationFrom=" + vacationFrom + ", vacationTo=" + vacationTo + ", reason="
				+ reason + ", status=" + this.uploadProtect() + ", employeeId=" + employeeId + "]";
	}

	
}
