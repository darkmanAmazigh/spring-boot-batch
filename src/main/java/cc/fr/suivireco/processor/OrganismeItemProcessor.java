package cc.fr.suivireco.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import cc.fr.suivireco.csventity.OrganismeCSV;
import cc.fr.suivireco.repository.OrganismeRepository;
import cc.fr.suivireco.repository.TypeOrganismeRepository;
import fr.cc.suivireco.productionmanagement.dataaccess.api.OrganismeEntity;
import fr.cc.suivireco.productionmanagement.dataaccess.api.TypeOrganismeEntity;

public class OrganismeItemProcessor implements ItemProcessor<OrganismeCSV, OrganismeEntity> {

  private Logger logger = LoggerFactory.getLogger(OrganismeItemProcessor.class);

  @Autowired
  ApplicationContext context;

  @Override
  public OrganismeEntity process(OrganismeCSV personCSV) throws Exception {

    TypeOrganismeRepository typeOrganismeRepository = this.context.getBean(TypeOrganismeRepository.class);
    OrganismeRepository organismeRepository = this.context.getBean(OrganismeRepository.class);
    OrganismeEntity person = new OrganismeEntity();

    // R1 check if or_id && or_crc exist on db
    OrganismeEntity organismeExist =
        organismeRepository.getByCrcArianeAdOrIdAriane(personCSV.getOr_crc(), personCSV.getOr_id());

    if (organismeExist != null) {

      // RG4
      organismeExist.setModificationCounter(organismeExist.getModificationCounter() + 1);
      organismeExist.setCrcAriane(personCSV.getOr_crc());
      person = organismeExist;
    } else { // Entity not exist

      person.setCrcAriane(personCSV.getOr_crc());
      person.setLibelle(personCSV.getOr_nom());
      person.setOrIdAriane(personCSV.getOr_id());
      person.setDepartement(personCSV.getOr_dpt() + "");
      person.setNatureJuridique(personCSV.getOr_nat());

      TypeOrganismeEntity typeOrganismeEntity = typeOrganismeRepository.findByCode(personCSV.getTyp_code());
      // R3
      if (typeOrganismeEntity != null) {

        if (personCSV.getTyp_code().equalsIgnoreCase(typeOrganismeEntity.getCode())) {
          person.setTypeOrganismeId(typeOrganismeEntity.getId());
          person.setLoiNotre(typeOrganismeEntity.getLoiNotre());
        }

      } else {
        person.setTypeOrganismeId(null);
        this.logger.warn("Type d’organisme " + personCSV.getOrtyp() + " inexistant ");
      }
    }

    return person;
  }
}