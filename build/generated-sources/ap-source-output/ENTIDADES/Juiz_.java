package ENTIDADES;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Juiz.class)
public abstract class Juiz_ {

	public static volatile SingularAttribute<Juiz, String> nomejuiz;
	public static volatile CollectionAttribute<Juiz, Audiencia> audienciaCollection;
	public static volatile SingularAttribute<Juiz, Integer> id;

}

