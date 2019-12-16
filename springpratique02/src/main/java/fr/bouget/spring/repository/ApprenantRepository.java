package fr.bouget.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import fr.bouget.spring.model.Apprenant;

/**
 * Interface pour l'entity Apprenant
 * 
 */
@RepositoryRestResource
public interface ApprenantRepository  extends JpaRepository<Apprenant, Integer> {

	// rien � ajouter car toutes les fonctionnalit�s CRUD sont existantes dans la 
	// classe JpaRepository... c'est cool !) 
}
