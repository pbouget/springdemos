package fr.bouget.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.bouget.model.Adresse;

/**
 * @author Philippe
 * Remarque : JpaRepository h�rite de PagingAndSortingRepository
 */
public interface AdresseRepository extends JpaRepository<Adresse, Integer> {

}

// Autre possibilit� d'�criture
//public interface AdresseRepository extends PagingAndSortingRepository<Adresse, Integer> {
//	 
//    List<Adresse> findAllByPays(String pays, Pageable pageable);
//}