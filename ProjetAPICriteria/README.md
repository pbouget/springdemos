# API JPA Criteria avec Spring Boot

## Utilit�

Dans cette application de d�monstration, nous allons utiliser l'API **Criteria** qui permet de r�aliser des requ�tes sans utiliser de SQL mais plut�t par **programmation**. Cela nous permet de cr�er des requ�tes sp�cifiques en fonction des besoins tout en utilisant par la m�me occasion les interfaces JPA et CRUD repositories habituels. _La syntaxe de l'API Criteria permet de rester dans le langage objet_.

Depuis la version 5.2 d'Hibernate, l'API Hibernate Criteria est devenue obsol�te et d�sormais, nous devons utiliser **JPA Criteria API**

Avant de commencer, pensez � ajouter les d�pendances Hibernate dans Maven ou Gradle.

## Exemple : Requ�te simple (quoique !)

```java
Session session = HibernateUtil.getHibernateSession();
CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

CriteriaQuery<Client> criteriaQuery = criteriaBuilder.createQuery(Client.class);
Root<Client> root = criteriaQuery.from(Client.class);
criteriaQuery.select(root);
 
Query<Client> query = session.createQuery(criteriaQuery);
List<Client> resultats = query.getResultList();
```
  
Il est �vident que le fait d'utiliser **JpaRepository** ou **CrudRepository** est plus pratique et bien plus simple pour des requ�tes basiques.

Nous verrons par la suite qu'il y a plus simple en terme de mise en place.

Voici le d�tail des �tapes ci-dessus :

- Cr�ation d'une instance de Session hibernate avec SessionFactory
- Cr�ation d'une instance de l'objet *CriteriaBuilder* en appelant la m�thode *getCriteriaBuilder()*
- Cr�ation d'une instance de *CriteriaQuery* en appelant la m�thode *createQuery()* de l'objet *CriteriaBuilder*
- Cr�ation d'une instance de *Query* en appelant la m�thode *createQuery()*
- Appel de la m�thode *getResultList()* de l'objet *Query* qui nous retourne le r�sultats.

## Requ�tes avec la m�thode **where()**

- Obtenir la liste des clients dont le CA est sup�rieur � 15000	:

```java
criteriaQuery.select(root).where(criteriaBuilder.gt(root.get("ca"), 15000));
```

- Obtenir la liste des clients dont le CA est inf�rieur � 10000 :

```java
criteriaQuery.select(root).where(criteriaBuilder.lt(root.get("ca"), 10000));
```

- Liste des clients dont le nom contient la cha�ne : "MA" :

```java
criteriaQuery.select(root).where(criteriaBuilder.like(root.get("nom"), "%MA%"));
```

- Liste des clients dont le CA est compris entre 15000 et 55000 :

```java
criteriaQuery.select(root).where(criteriaBuilder.between(root.get("ca"), 15000, 55000));
```

- Liste des clients dont l'adresse est NULL :

```java
criteriaQuery.select(root).where(criteriaBuilder.isNull(root.get("adresse")));
```

- Liste des clients dont l'adresse est non NULL :

```java
criteriaQuery.select(root).where(criteriaBuilder.isNotNull(root.get("adresse")));
```

Il y a aussi les m�thodes **isEmpty()** et **isNotEmpty()**.

## Utilisez un objet **Predicate** pour des requ�tes qui combinent plusieurs conditions

```java
Predicate[] predicats = new Predicate[3];
predicats[0] = criteriaBuilder.isNotNull(root.get("adresse"));
predicats[1] = criteriaBuilder.like(root.get("nom"), "M%");
predicats[2] = criteriaBuilder.like(root.get("prenom"), "D%");
criteriaQuery.select(root).where(predicats);
```

Exemple avec 2 op�rations logiques :

```java
Predicate caPlusGrandQue = criteriaBuilder.gt(root.get("CA"), 10000);
Predicate nomAvecDU = criteriaBuilder.like(root.get("nom"), "DU%");
```

- Avec l'op�rateur logique **OR** :

```java
criteriaQuery.select(root).where(criteriaBuilder.or(caPlusGrandQue, nomAvecDU));
```

- Avec l'op�rateur logique **AND** :

```java
criteriaQuery.select(root).where(criteriaBuilder.and(caPlusGrandQue, nomAvecDU));
```

## fonctions de tri

Ci-dessous :

Tri par ordre croissant sur le nom et par ordre d�croissant sur le chiffre d'affaires.

```java
criteriaQuery.orderBy(
  criteriaBuilder.asc(root.get("nom")), 
  criteriaBuilder.desc(root.get("ca")));
```

## projections, aggr�gation et regroupement

Tout comme avec le langage SQL, on utilise des m�thodes qui portent le m�me nom :

- **avg()**
- **count()**
- **sum()**
- **max()**
- **min()**
- **count()**

Ci-dessous : r�cup�ration du nombre des adresses.

```java
CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
Root<Adresse> root = criteriaQuery.from(Adresse.class);
criteriaQuery.select(criteriaBuilder.count(root));
Query<Long> query = session.createQuery(criteriaQuery);
List<Long> nombreAdresses = query.getResultList();
```

Ci-dessous : r�cup�ration du CA moyen.

```java
CriteriaQuery<Double> CriteriaQuery = cb.createQuery(Double.class);
Root<Client> root = CriteriaQuery.from(Client.class);
CriteriaQuery.select(criteriaBuilder.avg(root.get("CA")));
Query<Double> query = session.createQuery(CriteriaQuery);
List caMoyen = query.getResultList();
```

## Autres classes et m�thodes de l'API Criteria

- **CriteriaUpdate<>**
- **CriteriaDelete<>**

Vous pouvez aussi explorer le package **javax.persistence.criteria** proche du package hibernate.

[https://docs.oracle.com/javaee/7/api/javax/persistence/criteria/package-summary.html](https://docs.oracle.com/javaee/7/api/javax/persistence/criteria/package-summary.html)

## Tutoriel Hibernate Criteria et Jpa Criteria, liens utiles

[https://www.tutorialspoint.com/hibernate/hibernate_criteria_queries.htm](https://www.tutorialspoint.com/hibernate/hibernate_criteria_queries.htm)

[https://www.objectdb.com/java/jpa/query/criteria](https://www.objectdb.com/java/jpa/query/criteria)

## 3 mani�res d'utiliser JPA Criteria avec SpringBoot

- Cr�er une classe avec l'annotation **@Repository**
- Etendre la classe Repository avec des m�thodes personnalis�es
- Utiliser les sp�cifications JPA de Spring Data Jpa.

### Cr�er son propre Repository (c'est fastidieux, mais faisable)

```java 
@Repository
class ClientRepository {
 
EntityManager entityManager;
 
List<Client> findClientByNomAndCA(String name, Integer ca)
	{
	CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<Client> criteriaQuery = criteriaBuilder.createQuery(Client.class);
	Root<Client> client = criteriaQuery.from(Client.class);
	Predicate nomPredicate = criteriaBuilder.equal(client.get("nom"), nom ); // cr�ation du premier pr�dicat
    Predicate caPredicate = criteriaBuilder.gt(client.get("ca"), 15000));	 // cr�ation du second pr�dicat
    criteriaQuery.where(nomPredicate, caPredicate);							 // on combine les 2 pr�dicats
	TypedQuery<Client> query = entityManager.createQuery(criteriaQuery);	 // on ex�cute la requ�te
    return query.getResultList();											 // on retourne la liste
    }
}
```

### Etendre la classe **Repository** avec une interface personnalis�e (recommand�e)

Etapes :

1 - Il est possible d'impl�menter des m�thodes sp�cifique avec l'API Criteria dans une autre interface _ClientRepositoryCustom_ comme ci-dessous :

```java
interface ClientRepositoryCustom {
    Set<Client> findClientsByNomAndCA(String name, Integer ca);
}
```

2 - Il faut penser � cr�er une classe  **ClientRepositoryCustomImpl** qui impl�mente la ou les m�thodes de l'inetrface **ClientRepositoryCustom**.

```java
interface ClientRepositoryCustomImpl implements ClientRepositoryCustom {}
```

3 - Cr�ation d'une Interface qui h�rite des 2 interfaces 
- **JpaRepository**
- **ClientRepositoryCustom** (l'interface personnalis�e avec Criteria)

```java
interface ClientRepository extends JpaRepository<Client, Integer>, ClientRepositoryCustom {}
```

>Remarque : Une interface peut h�riter de plusieurs interfaces.

![Implementation Criteria](ImplementationCriteria.png)

## Utiliser les sp�cifications JPA de Spring

Package :

```java
org.springframework.data.jpa.domain.Specification
```

Exemple :

```java
interface Specification<T> {
    Predicate toPredicate(Root<T> root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder);
}

...

public static Specification<Client> hasNom(String parametreNom) {
    return (client, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(client.get("nom"), parametreNom);
}

public static Specification<Client> prenomContains(String chaine) {
    return (client, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(client.get("prenom"), "%" + chaine + "%");
}
```

Pour utiliser cette �criture, nous devons avoir un Repository qui h�rite de **org.springframework.data.jpa.repository.JpaSpecificationExecutor<T>** puisque chaque m�thode retourne un objet de type **Specification<T>**.

Exemple :

```java	
interface ClientRepository extends JpaRepository<Client, Integer>, JpaSpecificationExecutor<Client>
{

}
```

>Cette interface permet de d�clarer des m�thodes personnalis�es li�es � notre package *Specification*.

Pa exemple, nous pouvons utiliser notre m�thode sp�cifique pour r�cup�rer tous les clients dont le pr�nom contient une cha�ne pass�e en param�tre :

```java
public static Specification<Client> prenomContains(String chaine)
```

>Limite : Une seule sp�cification par m�thode !

Solution : encha�ner plusieurs m�thodes reli�es par des conditions logiques.

Exemple :

```java	
clientRepository.findAll(where(hasNom(client.getNom())).and(prenomContains(client.getprenom())));
```

Ci-dessus, **where()** est une m�thode *static* de la classe **Specification**.

Avec cette classe **Specification**, on peut se passer d'utiliser l'API Criteria standart puisque c'est fourni par Spring.
Cependant, il est pr�f�rable d'utiliser l'API Criteria pour �laborer des requ�tes sp�cifiques que ne permet pas la classe fournit par Spring !

## Interface PagingAndSortingRepository

Cette interface est bien pratique pour mettre en place une pagination lorsque nous avons beaucoup d'enregistrements et aussi pour effectuer des tris sur de multiples crit�res.

Voici le lien vers la documentation de Spring Data : [https://docs.spring.io/spring-data/data-commons/docs/current/api/org/springframework/data/repository/PagingAndSortingRepository.html](https://docs.spring.io/spring-data/data-commons/docs/current/api/org/springframework/data/repository/PagingAndSortingRepository.html) 

Vous constatez que cette interface h�rite de **CrudRepository<>**.

2 m�thodes :

- **Iterable<T> findAll(Sort sort)**
- **Page<T> findAll(Pageable pageable)**

Exemples de code

- Sans passer par *JpaRepository* :

```java
public interface ClientRepository extends PagingAndSortingRepository<Client, Integer> {
 
    List<Client> findAllByCA(Integer ca, Pageable pageable);
}
```
-En passant comme d'habitude par JpaRepository :

```java
@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {
    public List<Client> findAllByCA(Integer ca, Pageable pageable);
}
```

Etapes :

1. Cr�er et r�cup�rer un Objet **PageRequest** qui est une impl�mentation de l'interface **Pageable**.

2. Ins�rer notre objet **PageRequest** comme argument de notre m�thode.

3. Cr�er l'objet en passant le nombre d'�l�ments et le num�ro de page.

```java
Pageable premierePageAvec10Elements = PageRequest.of(0, 10);
```

Pour effectuer un tri, il suffit de cr�er un objet **Pageable** et d'y int�grer la classe **Sort** et de faire appel � ces m�thodes comme **by()**.

```java
Pageable sortedByNom = 
  PageRequest.of(0, 10, Sort.by("nom"));
 
Pageable sortedByCADesc = 
  PageRequest.of(0,100, Sort.by("ca").descending());
 
Pageable sortedByCADescNomAsc = 
  PageRequest.of(0, 10, Sort.by("ca").descending().and(Sort.by("nom")));
```

## Exemple � tester avec Swagger2

![test-projet-exemple-criteria.png](test-projet-exemple-criteria.png)

Lien GitHub
[https://github.com/pbougetsimplon/springdemos/tree/master/ProjetAPICriteria](https://github.com/pbougetsimplon/springdemos/tree/master/ProjetAPICriteria)