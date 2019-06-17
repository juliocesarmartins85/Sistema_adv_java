package ENTIDADES;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Bairro.class)
public abstract class Bairro_ {

	public static volatile CollectionAttribute<Bairro, Rua> ruaCollection;
	public static volatile SingularAttribute<Bairro, Cidade> bairrocidade;
	public static volatile SingularAttribute<Bairro, Integer> id;
	public static volatile SingularAttribute<Bairro, String> nomebairro;

}

