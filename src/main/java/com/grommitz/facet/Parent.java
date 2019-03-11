package com.grommitz.facet;

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Facet;
import org.hibernate.search.annotations.FacetEncodingType;
import org.hibernate.search.annotations.Field;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Parent {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Field(name = "parentId", analyze = Analyze.NO)
	@Facet(forField = "parentId", encoding = FacetEncodingType.STRING)
	long id;

	@OneToMany(mappedBy="parent", cascade={CascadeType.ALL})
	List<Child> children = new ArrayList<>();

	@Field(name = "parentName")
	private String name;

	@Field(analyze = Analyze.NO)
	@Facet(encoding = FacetEncodingType.STRING)
	private Gender gender;

	public Parent() {}

	public Parent(String name, Gender gender) {
		this.name = name;
		this.gender = gender;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public List<Child> getChildren() {
		return children;
	}

	public void setChildren(List<Child> children) {
		this.children = children;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public void addChild(Child c) {
		this.children.add(c);
		c.setParent(this);
	}

	@Override
	public String toString() {
		return name;
	}

}
