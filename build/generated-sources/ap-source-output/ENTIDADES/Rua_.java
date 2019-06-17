package ENTIDADES;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Rua.class)
public abstract class Rua_ {

	public static volatile CollectionAttribute<Rua, Pessoa> pessoaCollection;
	public static volatile SingularAttribute<Rua, Bairro> ruabairro;
	public static volatile SingularAttribute<Rua, Integer> numerocasa;
	public static volatile SingularAttribute<Rua, String> nome;
	public static volatile SingularAttribute<Rua, Integer> id;
	public static volatile SingularAttribute<Rua, Integer> cep;

}

