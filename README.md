# Découverte de SpringBoot : Solutions (session 3)

## Springdemo01 (Standalone)

Afficher un message dans la console avec Spring.

## Springdemo02 (Web)

Afficher un message dans un navigateur avec Spring.

## Springdemo03 (Web & RestController)

Afficher une liste d'objets au format JSON dans un navigateur avec Spring.

## Springdemo04 (JDBC - DAO - CRUD - JdbcTemplate)

Cette version démo utilise encore des requêtes SQL à l'aide de JdbcTemplate. ça permet de réviser le SQL pour le CRUD !

Accéder à la table Pilote dans la base de données bd-avion et exécuter les tâches suivantes (toujours dans la console) :

- Lister tous les pilotes
- Rechercher un pilote par nom comme "Elodie"
- Ajouter 2 nouveaux Pilotes
- Modifier le site d'un Pilote
- Supprimer le dernier Pilote

Il y a 2 versions de la méthode pour ajouter un pilote :

- Version non commentée en supposant que la clef primaire `PI_ID` de la table pilote est **auto-générée**
- Version commentée à utiliser si votre clef primaire `PI_ID`n'est pas auto-générée (Atttention, dans ce cas la méthode ne doit pas retourner un int !)  

## Spring-hibernate (Web - RestController - JPA - Hibernate)

Dans cet exemple, vous avez 2 classes **@Entity** : 

- Apprenant
- Region

Vous pouvez lister tous les apprenants ou toutes les régions au format JSON dans un navigateur.

Vous pouvez rechercher un apprenant par son **id**

Cette exemple permet de découvrir comment simplifier l'accès à la base de données grâce aux annotations.

## Spring pratique01 (Web - Spring - HTML - JSON - AJAX)

Autre démo basique

## Spring pratique02 (Web - JSON - JPA - Hibernate - SpringBoot)

Cet exemple permet de comprendre le lien entre un Front JS et un Back SpringBoot associé à JPA/Hibernate.

Il faut juste créer une base de données **pratique3** et modifier le fichier **applications.properties**.

## Mapping avec JPA/Hibernate et SpringBoot

### 1 - ENTITE VIN - PERSISTANCE (**[pinard01](exercices/pinard01-entity.md)**) identifiant **NON AUTO-GENERE**

![pinard01](images/pinard01.png)

### 2 - ENTITE VIN AVEC **CLE PRIMAIRE AUTO-GENEREE** (**[pinard02](exercices/pinard02-entity-clef-auto-generee.md)**)

![pinard01](images/pinard01.png)

### 3 - ENTITE VIN : CLE PRIMAIRE COMPOSITE avec **@IdClass** <!--([**pinard03]<(exercices/pinard03-entity-clef-composee-IdClass.md)**)-->

### 4 - ENTITE VIN : CLE PRIMAIRE COMPOSITE avec **EmbeddedId** <!--(**[pinard04](exercices/pinard04-entity-clef-composee-EmbeddedId.md.md)**)-->

### 5 - ENTITE CLIENT : MAPPING MULTI-TABLES avec **@SecondaryTable** (**[client01](exercices/client01-entity-table-secondaire.md)**)

![client01](images/client01.png)

### 6 - **@OneToOne** : ASSOCIATION UNIDIRECTIONNELLE DE 1 VERS 1 (**[client02](exercices/client02-@OneToOne-unidirectionnelle.md)**)

![tp6-uni-1-1.png](images/tp6-uni-1-1.png)

### 7 - **@OneToOne** : ASSOCIATION BIDIRECTIONNELLE DE 1 VERS 1 (**[client03](exercices/client03-@OneToOne-bidirectionnelle.md)**)

![tp7-bi-1-1.png](images/tp7-bi-1-1.png)

### 8 - **@OneToOne** & **@OneToMany** : ASSOCIATION UNIRECTIONNELLE DE 1 VERS n (**[client04](exercices/client04-@OneToOne-@OneToMany-uni-bidirectionnelle.md)**)

![tp8-uni-n-n.png](images/tp8-uni-1-n.png)

### 9 - **@ManyToOne** : ASSOCIATION UNIDIRECTIONNELLE DE N VERS 1 (**[croisiere01](exercices/croisiere01-@ManyToOne-unidirectionnelle.md)**)

![tp9-uni-n-1.png](images/tp9-uni-n-1.png)

### 10 - **@ManyToMany**, **@OneToMany** : ASSOCIATION BIDIRECTIONNELLE DE N VERS 1 (**[croisiere02](exercices/croisiere02-@ManyToMany-@ManyToOne-@OneToMany-uni-et-bidirectionnelle.md)**)

![heritage12.4.png](images/tp10-bi-n-1.png)

### 11 - **@ManyToMany**, **@OneToMany**, **@ManyToOne** : ASSOCIATION BIDIRECTIONNELLE DE N VERS N (**[croisiere03](exercices/croisiere03-@ManyToMany-@ManyToOne-@OneToMany-uni-et-bidirectionnelle.md)**)

![heritage12.4.png](images/tp11-bi-n-n.png)

### 12.1 - HERITAGE : **SINGLE_TABLE** - UNE TABLE UNIQUE PAR HIERARCHIE/CLASSE (**[heritage01](exercices/heritage01-Single-Table.md)**)

![heritage12.4.png](images/heritage12.1.png)

### 12.2 - HERITAGE : **TABLE_PER_CLASS** - UNE TABLE PAR CLASSE CONCRETE (**[heritage02](exercices/heritage02-Table-Per-Class.md)**)

![heritage12.4.png](images/heritage12.2.png)

### 12.3 - HERITAGE : **JOINED** - UNE TABLE PAR SOUS-CLASSE (**[heritage03](exercices/heritage03-Joined.md)**)

![heritage12.3.png](images/heritage12.3.png)

L'annoation **JOINED** permet de générer des jointures.

### 12.4 – HERITAGE : **@MappedSuperClass** - CLASSE DE BASE NON PERSISTANTE (**[heritage04](exercices/heritage04-@MappedSuperClass.md)**)

![heritage12.4.png](images/heritage12.4.png)

L'annoation **@MappedSuperClass** permet de générer des tables relatives au sous-classes en intégrant les propriété de la classe mère.