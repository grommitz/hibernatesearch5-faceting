package com.grommitz.facet;

public enum Gender {

	MALE("Boy"), FEMALE("Girl"), OTHER("Undecided");

	private final String description;

	Gender(String desc) {
		this.description = desc;
	}

	public String getDescription() {
		return description;
	}
}
