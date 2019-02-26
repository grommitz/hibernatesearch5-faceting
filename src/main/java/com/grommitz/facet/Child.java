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
	@Facet
	public long getParentId() {
		return parent.getId();
	}

	public Child() {}

	public Child(String name) {
		this.name = name;
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
		return name;
	}
}
