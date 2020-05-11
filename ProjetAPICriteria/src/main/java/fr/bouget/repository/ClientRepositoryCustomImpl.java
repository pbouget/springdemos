package fr.bouget.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import fr.bouget.model.Client;

public class ClientRepositoryCustomImpl implements ClientRepositoryCustom {


	@PersistenceContext
	private EntityManager manager;

	public Client findOneClientByCriteria(String prenom, String nom)
	{

		// on appelle l'objet Builder
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		// on pr�cise sur quelle Entit� (table) on souhaite travailler
		CriteriaQuery<Client> requete = builder.createQuery(Client.class);
		// ici,la table client, le type de l'objet qu'on souhaite retourner
		Root<Client> clientRoot = requete.from(Client.class);
		// on pr�cise les �l�ments de la requ�te (ici le pr�nom) :
		//	requete.where(builder.equal(clientRoot.get("prenom"), client.getPrenom()));

		// on pourrait pr�ciser les valeurs avec la syntaxe suivante :
		requete.where(builder.and(
				builder.equal(clientRoot.get("prenom"), prenom),
				builder.equal(clientRoot.get("nom"), nom)
				));

		return manager.createQuery(requete).getSingleResult();
	}

	public List<Client> findAllClientByCriteria()
	{
		// on appelle l'objet Builder
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		// on pr�cise sur quelle Entit� (table) on souhaite travailler
		CriteriaQuery<Client> requete = builder.createQuery(Client.class);
		// ici,la table client
		Root<Client> clientRoot = requete.from(Client.class);
		// c'est juste un select de client
		requete.select(clientRoot);
		System.out.println(requete);
		List<Client> liste = manager.createQuery(requete).getResultList();
		return liste;
	}

	public List<Client> findAllClientSansAdresseByCriteria()
	{
		// on appelle l'objet CriteriaBuilder
		CriteriaBuilder cb = manager.getCriteriaBuilder();
		// on pr�cise sur quelle Entit� (table) on souhaite travailler :
		CriteriaQuery<Client> cq = cb.createQuery(Client.class);
		// ici,la table client
		Root<Client> client = cq.from(Client.class);
		// qu'affiche t-on ?
		cq.select(client);

		// que veut-on ?
		// 1 - les clients qui n'ont pas d'adresse ou adresse nulle
		cq.where(client.get("adresse").isNull());		
		return  manager.createQuery(cq).getResultList();
	}

	public List<Client> findAllClientParNomByCriteria(String chaine)
	{
		// on appelle l'objet CriteriaBuilder
		CriteriaBuilder cb = manager.getCriteriaBuilder();
		// on pr�cise sur quelles Entit�s (tables) on souhaite travailler :
		CriteriaQuery<Client> cq = cb.createQuery(Client.class);
		// ici,la table client et adresse
		Root<Client> client = cq.from(Client.class);
		//	Root<Adresse> adresse = cq.from(Adresse.class);
		// qu'affiche t-on ? un objet Client
		cq.select(client);

		// que veut-on ?
		// les clients dont le nom contient la cha�ne pass�e en param�tre
		cq.where(cb.like(client.get("nom"),"%"+chaine+"%"));
		return manager.createQuery(cq).getResultList();
	}


	public List<Client> findClientsByNomAndCa(String nom, Integer ca)
	{
		CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
		CriteriaQuery<Client> criteriaQuery = criteriaBuilder.createQuery(Client.class);
		Root<Client> client = criteriaQuery.from(Client.class);

		Predicate nomPredicate = criteriaBuilder.like(client.get("nom"), nom ); // cr�ation du premier pr�dicat
		Predicate caPredicate = criteriaBuilder.ge(client.get("ca"), ca);	 	// cr�ation du second pr�dicat
		criteriaQuery.where(nomPredicate, caPredicate);							 // on combine les 2 pr�dicats
		TypedQuery<Client> query = manager.createQuery(criteriaQuery);	 		 // on ex�cute la requ�te
		return query.getResultList();											 // on retourne la liste
	}
}
