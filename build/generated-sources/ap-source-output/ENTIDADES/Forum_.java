package ENTIDADES;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Forum.class)
public abstract class Forum_ {

	public static volatile CollectionAttribute<Forum, Audiencia> audienciaCollection;
	public static volatile SingularAttribute<Forum, Integer> id;
	public static volatile SingularAttribute<Forum, String> tipoforum;
	public static volatile SingularAttribute<Forum, String> numeroautenticacao;

}

