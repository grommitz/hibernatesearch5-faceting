package com.grommitz.facet;

import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.hibernate.search.query.engine.spi.FacetManager;
import org.hibernate.search.query.facet.Facet;
import org.hibernate.search.query.facet.FacetSortOrder;
import org.hibernate.search.query.facet.FacetingRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

import static com.grommitz.facet.Gender.FEMALE;
import static com.grommitz.facet.Gender.MALE;
import static com.grommitz.facet.Gender.OTHER;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class FacetingTest {

	EntityManager em;
	FullTextEntityManager ftem;

	@BeforeEach
	void setUp() {
		em = javax.persistence.Persistence
				.createEntityManagerFactory("TestDb").createEntityManager();
		ftem = Search.getFullTextEntityManager(em);

		createFixtureData();
	}

	@Test
	void searchChildrenWithoutFaceting() {

		QueryBuilder builder = ftem.getSearchFactory()
				.buildQueryBuilder().forEntity(Child.class).get();

		org.apache.lucene.search.Query luceneQuery =
				builder.keyword()
						.onField("name")
						.matching("jack")
						.createQuery();

		FullTextQuery fullTextQuery = ftem.createFullTextQuery(luceneQuery);
		List<Child> result = fullTextQuery.getResultList();

		result.forEach(System.out::println);

		assertThat(result.size(), is(1));
	}

	@Test
	void groupChildrenByParent() {

		QueryBuilder builder = ftem.getSearchFactory()
				.buildQueryBuilder().forEntity(Child.class).get();

		org.apache.lucene.search.Query luceneQuery =
				builder.all().createQuery();

		FullTextQuery fullTextQuery = ftem.createFullTextQuery(luceneQuery);

		FacetingRequest facetingRequest = builder.facet()
				.name("facetRequest")
				.onField("parent.parentId")
				.discrete()
				.orderedBy(FacetSortOrder.COUNT_DESC)
				.includeZeroCounts(false)
				.maxFacetCount(10)
				.createFacetingRequest();

		FacetManager facetManager = fullTextQuery.getFacetManager();
		facetManager.enableFaceting(facetingRequest);

		List<Child> result = fullTextQuery.getResultList();
		result.forEach(System.out::println);

		List<Facet> facets = facetManager.getFacets("facetRequest");
		facets.forEach(System.out::println);

		assertThat(result.size(), is(5));
		assertThat(facets.size(), is(2));
	}

	@Test
	void groupChildrenByGender() {
		QueryBuilder builder = ftem.getSearchFactory()
				.buildQueryBuilder().forEntity(Child.class).get();

		org.apache.lucene.search.Query luceneQuery =
				builder.all().createQuery();

		FullTextQuery fullTextQuery = ftem.createFullTextQuery(luceneQuery);

		FacetingRequest facetingRequest = builder.facet()
				.name("facetRequest")
				.onField("gender")
				.discrete()
				.orderedBy(FacetSortOrder.COUNT_DESC)
				.includeZeroCounts(true)
				.maxFacetCount(10)
				.createFacetingRequest();

		FacetManager facetManager = fullTextQuery.getFacetManager();
		facetManager.enableFaceting(facetingRequest);

		List<Child> result = fullTextQuery.getResultList();
		result.forEach(System.out::println);

		List<Facet> facets = facetManager.getFacets("facetRequest");
		facets.forEach(System.out::println);

		assertThat(result.size(), is(5));
		assertThat(facets.size(), is(3));
	}

	private void createFixtureData() {

		EntityTransaction tx = em.getTransaction();
		tx.begin();

		Parent p1 = new Parent("James", MALE);
		Child c1 = new Child("Jon", MALE);
		Child c2 = new Child("Jack", MALE);

		p1.addChild(c1);
		p1.addChild(c2);
		em.persist(p1);

		Parent p2 = new Parent("Martha", FEMALE);
		Child c3 = new Child("Marianne", FEMALE);
		Child c4 = new Child("Marley", OTHER);
		Child c5 = new Child("Mary", FEMALE);

		p2.addChild(c3);
		p2.addChild(c4);
		p2.addChild(c5);

		em.persist(p2);

		Parent p3 = new Parent("Gary", MALE);

		em.persist(p3);

		tx.commit();
	}


}
