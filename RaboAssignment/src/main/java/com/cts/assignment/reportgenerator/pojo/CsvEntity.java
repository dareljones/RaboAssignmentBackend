package com.cts.assignment.reportgenerator.pojo;

import com.opencsv.bean.CsvBindByName;

public class CsvEntity {
	@CsvBindByName
	private Integer reference;
	@CsvBindByName
	private String accountNumber;
	@CsvBindByName
	private String description;
	@CsvBindByName(column = "Start Balance")
	private Float startBalance;
	@CsvBindByName
	private Float mutation;
	@CsvBindByName(column = "End Balance")
	private Float endBalance;

	public Integer getReference() {
		return reference;
	}

	public void setReference(Integer reference) {
		this.reference = reference;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Float getStartBalance() {
		return startBalance;
	}

	public void setStartBalance(Float startBalance) {
		this.startBalance = startBalance;
	}

	public Float getMutation() {
		return mutation;
	}

	public void setMutation(Float mutation) {
		this.mutation = mutation;
	}

	public Float getEndBalance() {
		return endBalance;
	}

	public void setEndBalance(Float endBalance) {
		this.endBalance = endBalance;
	}

}
