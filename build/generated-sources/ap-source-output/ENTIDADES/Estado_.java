package ENTIDADES;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Estado.class)
public abstract class Estado_ {

	public static volatile SingularAttribute<Estado, String> sigla;
	public static volatile CollectionAttribute<Estado, Cidade> cidadeCollection;
	public static volatile SingularAttribute<Estado, Integer> id;
	public static volatile SingularAttribute<Estado, String> nomeestado;

}

