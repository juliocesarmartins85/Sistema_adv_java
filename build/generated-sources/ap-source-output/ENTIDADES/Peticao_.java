package ENTIDADES;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Peticao.class)
public abstract class Peticao_ {

	public static volatile SingularAttribute<Peticao, String> paragrafo;
	public static volatile SingularAttribute<Peticao, String> lei;
	public static volatile CollectionAttribute<Peticao, Peticaopessoa> peticaopessoaCollection;
	public static volatile SingularAttribute<Peticao, String> reu;
	public static volatile CollectionAttribute<Peticao, Processopeticao> processopeticaoCollection;
	public static volatile SingularAttribute<Peticao, Integer> id;
	public static volatile SingularAttribute<Peticao, String> artigo;
	public static volatile CollectionAttribute<Peticao, Peticaotabeladejuros> peticaotabeladejurosCollection;

}

