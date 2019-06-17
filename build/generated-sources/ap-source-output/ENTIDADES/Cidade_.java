package ENTIDADES;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Cidade.class)
public abstract class Cidade_ {

	public static volatile CollectionAttribute<Cidade, Bairro> bairroCollection;
	public static volatile SingularAttribute<Cidade, Estado> cidadeestado;
	public static volatile SingularAttribute<Cidade, Integer> id;
	public static volatile SingularAttribute<Cidade, String> nomecidade;

}

