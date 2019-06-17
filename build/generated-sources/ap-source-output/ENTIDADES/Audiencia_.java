package ENTIDADES;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Audiencia.class)
public abstract class Audiencia_ {

	public static volatile SingularAttribute<Audiencia, Forum> audienciaforum;
	public static volatile CollectionAttribute<Audiencia, Processoaudiencia> processoaudienciaCollection;
	public static volatile SingularAttribute<Audiencia, Integer> id;
	public static volatile SingularAttribute<Audiencia, String> tipoaudiencia;
	public static volatile SingularAttribute<Audiencia, Juiz> audienciajuiz;

}

