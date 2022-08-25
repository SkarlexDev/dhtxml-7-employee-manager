package com.bean;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

public class Employee {

    private Long id;
    private String name;
    private String phone;
    private String email;
    private LocalDate birthDate;
    private String address;
    private String country;
    private String password;
    private Long vacations;
    private Set<Role> roles = new HashSet<>();

    public Employee() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }
    
    public String getBirthDateFormatted() {
    	final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MMM-yyyy");
        return formatter.format(this.getBirthDate());
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
    
    public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Role> getRoles() {
		return roles;
	}
	
	public void addRole(Role role) {
		roles.add(role);
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public Long getVacations() {
		return vacations;
	}

	public void setVacations(Long vacations) {
		this.vacations = vacations;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", phone=" + phone + ", email=" + email + ", birthDate="
				+ birthDate + ", address=" + address + ", country=" + country + ", password=" + password
				+ ", vacations=" + vacations + ", roles=" + roles + "]";
	}
	

}
