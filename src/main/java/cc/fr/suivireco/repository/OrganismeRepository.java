package cc.fr.suivireco.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import fr.cc.suivireco.productionmanagement.dataaccess.api.OrganismeEntity;

/**
 * Declaration of the repository of persistence OrganismeEntity entity
 *
 * @author Rafik BOUGHANI
 * @date 09/01/2017
 */
public interface OrganismeRepository extends JpaRepository<OrganismeEntity, Long> {

  /**
   * @param crcAriane
   * @param OrIdAriane
   * @return OrganismeEntity
   */
  // OrganismeEntity findByCrcArianeAndOrIdAriane(Long crcAriane, Long OrIdAriane);

  @Query("select u from OrganismeEntity u where u.crcAriane = ?1 and orIdAriane = ?2")
  OrganismeEntity getByCrcArianeAdOrIdAriane(Long crcAriane, Long OrIdAriane);
}