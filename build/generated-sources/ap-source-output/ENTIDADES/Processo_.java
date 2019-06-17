package ENTIDADES;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Processo.class)
public abstract class Processo_ {

	public static volatile CollectionAttribute<Processo, Processopeticao> processopeticaoCollection;
	public static volatile CollectionAttribute<Processo, Processopessoa> processopessoaCollection;
	public static volatile CollectionAttribute<Processo, Processoaudiencia> processoaudienciaCollection;
	public static volatile SingularAttribute<Processo, Jurisprudencia> processojurisprudencia;
	public static volatile SingularAttribute<Processo, Integer> id;
	public static volatile SingularAttribute<Processo, Integer> numeroprocesso;

}

