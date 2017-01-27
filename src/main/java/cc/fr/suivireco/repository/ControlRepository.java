package cc.fr.suivireco.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import fr.cc.suivireco.productionmanagement.dataaccess.api.ControleEntity;

/**
 * Declaration of the repository of persistence ControleEntity entity
 *
 * @author Rafik BOUGHANI
 * @date 10/01/2017
 */
public interface ControlRepository extends JpaRepository<ControleEntity, Long> {

  @Query("select u from ControleEntity u where u.crcAriane = ?1 and u.cliDAriane = ?2")
  ControleEntity getCrcArianeCliDAriane(Long crcariane, Long clidAriane);
}