package fr.bouget.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.bouget.jpa.model.Bouteille;

@Repository
public interface BouteilleRepository extends JpaRepository<Bouteille, Integer> {

}
