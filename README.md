# Faceting with Hibernate Search 5

Two entities: Parent & Child with a simple one-to-many relationship.

The Child is the @Indexed entity, the Parent is marked as @IndexedEmbedded.

I want to query the children and group by their parents.

Parent.id is marked with @Facet, but when the query runs it gives an error:

    org.hibernate.search.exception.SearchException: HSEARCH000268: Facet request 'facetRequest' tries to facet on  field 'parent.id' which either does not exist or is not configured for faceting (via @Facet). Check your configuration.
    
I would like to know how the @Facet should be set up to make this work, I have not seen any examples 
of faceting on an embedded entity.

See https://stackoverflow.com/questions/54871971/how-to-use-facet-with-embedded-entities

