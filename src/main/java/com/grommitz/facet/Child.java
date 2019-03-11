package com.grommitz.facet;

import org.hibernate.search.annotations.*;

import javax.persistence.*;

@Indexed
@Entity
public class Child {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@DocumentId
	long id;

	@Field
	String name;

	@ManyToOne
	@IndexedEmbedded(includeEmbeddedObjectId = true)
	Parent parent;

	@Field(analyze = Analyze.NO)
	@Facet(encoding = FacetEncodingType.STRING)
	private Gender gender;

	public Child() {}

	public Child(String name, Gender gender) {
		this.name = name;
		this.gender = gender;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Parent getParent() {
		return parent;
	}

	public void setParent(Parent parent) {
		this.parent = parent;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return String.format("%s (%s)", name, gender.getDescription());
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}


}
