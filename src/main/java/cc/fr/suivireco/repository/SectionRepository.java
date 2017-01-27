package cc.fr.suivireco.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import fr.cc.suivireco.referentielmanagement.dataaccess.api.SectionEntity;

/**
 * TODO rboughani This type ...
 *
 * @author rboughani
 */
public interface SectionRepository extends JpaRepository<SectionEntity, Long> {

  @Query("select u from SectionEntity u where u.numero = ?1 and u.chambre.numAriane = ?2")
  SectionEntity getNumeroChambre(Long numero, Long numAriane);
}