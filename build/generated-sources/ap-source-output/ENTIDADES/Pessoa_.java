package ENTIDADES;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Pessoa.class)
public abstract class Pessoa_ {

	public static volatile CollectionAttribute<Pessoa, Peticaopessoa> peticaopessoaCollection;
	public static volatile CollectionAttribute<Pessoa, Juridica> juridicaCollection;
	public static volatile CollectionAttribute<Pessoa, Procuracao> procuracaoCollection;
	public static volatile CollectionAttribute<Pessoa, Processopessoa> processopessoaCollection;
	public static volatile CollectionAttribute<Pessoa, Telefone> telefoneCollection;
	public static volatile CollectionAttribute<Pessoa, Advogado> advogadoCollection;
	public static volatile CollectionAttribute<Pessoa, Contratopessoa> contratopessoaCollection;
	public static volatile SingularAttribute<Pessoa, String> nome;
	public static volatile SingularAttribute<Pessoa, Integer> id;
	public static volatile CollectionAttribute<Pessoa, Fisica> fisicaCollection;
	public static volatile SingularAttribute<Pessoa, Rua> pessoarua;
	public static volatile CollectionAttribute<Pessoa, Contrato> contratoCollection;

}

