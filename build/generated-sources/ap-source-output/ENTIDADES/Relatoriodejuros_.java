package ENTIDADES;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Relatoriodejuros.class)
public abstract class Relatoriodejuros_ {

	public static volatile SingularAttribute<Relatoriodejuros, String> ano;
	public static volatile SingularAttribute<Relatoriodejuros, Double> juros;
	public static volatile SingularAttribute<Relatoriodejuros, Double> valor;
	public static volatile SingularAttribute<Relatoriodejuros, String> mes;
	public static volatile SingularAttribute<Relatoriodejuros, Integer> id;
	public static volatile CollectionAttribute<Relatoriodejuros, Peticaotabeladejuros> peticaotabeladejurosCollection;

}

