package cc.fr.suivireco.processor;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import cc.fr.suivireco.csventity.ThemeCSV;
import cc.fr.suivireco.repository.ControlRepository;
import fr.cc.suivireco.productionmanagement.dataaccess.api.ControleEntity;

/**
 * Declaration of the process of converting readed data to persistence entity
 *
 * @author Rafik BOUGHANI
 * @date 09/01/2017
 */
public class ThemeItemProcessor implements ItemProcessor<ThemeCSV, ControleEntity> {

  private Logger logger = LoggerFactory.getLogger(OrganismeItemProcessor.class);

  @Autowired
  ApplicationContext context;

  Map<Integer, String> lineTreated;

  Integer i = 0;

  /**
   * {@inheritDoc}
   */
  @Override
  public ControleEntity process(ThemeCSV tehemeCSV) throws Exception {

    ControlRepository controlRepository = this.context.getBean(ControlRepository.class);

    this.lineTreated = new HashMap<Integer, String>();

    // RG 10
    ControleEntity controlExists = controlRepository.getCrcArianeCliDAriane(Long.parseLong(tehemeCSV.getCl_crc()),
        Long.parseLong(tehemeCSV.getCl_id()));

    ControleEntity control = new ControleEntity();

    if (controlExists != null) {
      // RG 10
      if (controlExists.getThemeEnquete() == null) {

        this.lineTreated.put(this.i,
            (Long.parseLong(tehemeCSV.getCl_crc()) + " " + Long.parseLong(tehemeCSV.getCl_id())));
        this.i++;
        controlExists.setThemeEnquete(tehemeCSV.getTheme_libl());

      } else {

        for (String value : this.lineTreated.values()) {
          if (value.equals(tehemeCSV.getCl_crc() + " " + tehemeCSV.getCl_crc())) {
            controlExists.setThemeEnquete(controlExists.getThemeEnquete() + tehemeCSV.getTheme_libl());
          } else {
            // TODO don't update
          }
        }
      }

      controlExists.setCrcAriane((!tehemeCSV.getCl_crc().isEmpty()) ? Long.parseLong(tehemeCSV.getCl_crc()) : null);
      controlExists.setClIdAriane((!tehemeCSV.getCl_id().isEmpty()) ? Long.parseLong(tehemeCSV.getCl_id()) : null);

      control = controlExists;
    } else {
      control = null;
    }
    this.logger.info("Construction de control à partir du fichier CSV Theme");

    return control;
  }

}
