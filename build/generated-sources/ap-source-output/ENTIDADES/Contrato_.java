package ENTIDADES;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Contrato.class)
public abstract class Contrato_ {

	public static volatile CollectionAttribute<Contrato, Contratopessoa> contratopessoaCollection;
	public static volatile SingularAttribute<Contrato, Integer> numerocontrato;
	public static volatile SingularAttribute<Contrato, Integer> id;
	public static volatile SingularAttribute<Contrato, Pessoa> contratopessoa;

}

