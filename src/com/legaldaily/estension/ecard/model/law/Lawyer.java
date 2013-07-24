package com.legaldaily.estension.ecard.model.law;


public class Lawyer extends LawCooperate {
	private LawOffice lawOffice;
	//专长
	private String specialty;
	
	public LawOffice getLawOffice() {
		return lawOffice;
	}
	public void setLawOffice(LawOffice lawOffice) {
		this.lawOffice = lawOffice;
	}
	public String getSpecialty() {
		return specialty;
	}
	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}
}
