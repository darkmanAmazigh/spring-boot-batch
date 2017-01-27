package cc.fr.suivireco.processor;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import cc.fr.suivireco.csventity.ControlCSV;
import cc.fr.suivireco.repository.ControlRepository;
import cc.fr.suivireco.repository.OrganismeRepository;
import cc.fr.suivireco.repository.SectionRepository;
import fr.cc.suivireco.productionmanagement.dataaccess.api.ControleEntity;
import fr.cc.suivireco.productionmanagement.dataaccess.api.OrganismeEntity;
import fr.cc.suivireco.referentielmanagement.dataaccess.api.SectionEntity;

/**
 * Declaration of the process of converting readed data to persistence entity
 *
 * @author Rafik BOUGHANI
 * @date 09/01/2017
 */
public class ControlItemProcessor implements ItemProcessor<ControlCSV, ControleEntity> {

  private Logger logger = LoggerFactory.getLogger(OrganismeItemProcessor.class);

  @Autowired
  ApplicationContext context;

  // Enregister la liste des lignes lu
  Set<ControlCSV> seenControles = new HashSet<ControlCSV>();

  /**
   * {@inheritDoc}
   */
  @Override
  public ControleEntity process(ControlCSV controlCSV) throws Exception {

    Logger logger = LoggerFactory.getLogger(OrganismeItemProcessor.class);
    ControlRepository controlRepository = this.context.getBean(ControlRepository.class);
    OrganismeRepository organismeRepository = this.context.getBean(OrganismeRepository.class);
    SectionRepository sectionRepository = this.context.getBean(SectionRepository.class);

    ControleEntity control = new ControleEntity();

    // RG 6
    ControleEntity controlExists = controlRepository.getCrcArianeCliDAriane(Long.parseLong(controlCSV.getCL_CRC()),
        Long.parseLong(controlCSV.getCL_ID()));

    // RG 8
    OrganismeEntity organismeExist = organismeRepository
        .getByCrcArianeAdOrIdAriane(Long.parseLong(controlCSV.getCL_CRC()), Long.parseLong(controlCSV.getOR_ID()));

    // RG 9
    SectionEntity sectionExist = sectionRepository.getNumeroChambre(
        (!controlCSV.getCD_CODE().isEmpty()) ? Long.parseLong(controlCSV.getCD_CODE()) : null,
        (!controlCSV.getCL_CRC().isEmpty()) ? Long.parseLong(controlCSV.getCL_CRC()) : null);

    if (!controlCSV.getCL_NUM().isEmpty()) {

      if (this.seenControles.contains(controlCSV)) {
        System.err.println(controlCSV.getCL_CRC() + " || " + controlCSV.getCL_ID());
      } else {
        this.seenControles.add(controlCSV);
      }

      if (controlExists != null) {

        controlExists.setModificationCounter(controlExists.getModificationCounter() + 1);

        controlExists.setCrcAriane((!controlCSV.getCL_CRC().isEmpty()) ? Long.parseLong(controlCSV.getCL_CRC()) : null);
        controlExists.setClIdAriane((!controlCSV.getCL_ID().isEmpty()) ? Long.parseLong(controlCSV.getCL_ID()) : null);
        controlExists.setNumero(controlCSV.getCL_NUM());
        controlExists.setLibelle(controlCSV.getCL_LIBC());
        // RG 9
        if (sectionExist != null) {
          controlExists.setSectionId(sectionExist.getId());
        } else {
          controlExists.setSectionId(null);
          logger
              .warn("section inexistante " + controlCSV.getCD_CODE() + " pour le contrôle " + controlCSV.getCL_LIBC());
        }
        // RG 8
        if (organismeExist != null) {
          controlExists.setOrganismeId(organismeExist.getId());
        } else {
          logger.error("organisme " + controlCSV.getCL_CRC() + "/" + controlCSV.getCL_ID()
              + " inexistant pour le contrôle " + controlCSV.getCL_LIBC());
          throw new Exception("Organisme inexistant");
        }

        control = controlExists;
      } else {
        // RG 7
        control.setCrcAriane((!controlCSV.getCL_CRC().isEmpty()) ? Long.parseLong(controlCSV.getCL_CRC()) : null);
        control.setClIdAriane((!controlCSV.getCL_ID().isEmpty()) ? Long.parseLong(controlCSV.getCL_ID()) : null);
        control.setNumero(controlCSV.getCL_NUM());
        control.setLibelle(controlCSV.getCL_LIBC());
        // RG 9
        if (sectionExist != null) {
          control.setSectionId(sectionExist.getId());
        } else {
          control.setSectionId(null);
          logger
              .warn("section inexistante " + controlCSV.getCD_CODE() + " pour le contrôle " + controlCSV.getCL_LIBC());
        }

        // RG 8
        if (organismeExist != null) {
          control.setOrganismeId(organismeExist.getId());
        } else {
          this.logger.error("organisme " + controlCSV.getCL_CRC() + "/" + controlCSV.getCL_ID()
              + " inexistant pour le contrôle " + controlCSV.getCL_LIBC());
          throw new Exception("Organisme inexistant");
        }
      }
    } else {
      control = null;
    }

    return control;
  }
}
