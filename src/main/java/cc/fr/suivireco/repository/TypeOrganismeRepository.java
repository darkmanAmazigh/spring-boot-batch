package cc.fr.suivireco.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.cc.suivireco.productionmanagement.dataaccess.api.TypeOrganismeEntity;

/**
 * Declaration of the repository of persistence TypeOrganismeEntity entity
 *
 * @author Rafik BOUGHANI
 * @date 09/01/2017
 */
public interface TypeOrganismeRepository extends JpaRepository<TypeOrganismeEntity, Long> {

  TypeOrganismeEntity findByCode(String code);

}
