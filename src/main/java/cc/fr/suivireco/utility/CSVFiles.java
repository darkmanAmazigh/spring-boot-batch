package cc.fr.suivireco.utility;

import java.io.File;
import java.io.FilenameFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * CSV File directory configuration
 *
 * @author Rafik BOUGHANI
 * @date 25/01/2016
 */
public class CSVFiles {

  private Logger logger = LoggerFactory.getLogger(CSVFiles.class);

  private String DEFAULT_IMPORT_DIRECTORY = "D:\\Ariane\\delta";

  /**
   * This method will return the requested file to read by the Batch
   *
   * @param startWith - that parameter is readed from application.properties file : ex organismeDelta
   * @param directoryPath - geted from application.properties : files path
   * @return the requested file
   */
  public File getFileToRead(final String startWith, String directoryPath) {

    File root;
    File retour = null;

    if (directoryPath.isEmpty()) {
      root = new File(this.DEFAULT_IMPORT_DIRECTORY);
    } else {
      root = new File(directoryPath);
    }

    if (!root.exists()) {
      try {
        this.logger.error("Le répertoire : " + root + "n exist pas.");
        throw new Exception("Le répertoire : " + root + "n exist pas.");
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    FilenameFilter beginswithm = new FilenameFilter() {
      public boolean accept(File directory, String filename) {

        return filename.matches("^(" + startWith + ")\\d{4}-\\d{2}-\\d{2}.csv$");
      }
    };
    File[] files = root.listFiles(beginswithm);

    switch (files.length) {
    case 1:
      retour = files[0];
      return files[0];
    case 0:
      try {
        this.logger
            .error("Aucun fichier qui commence par " + startWith + " n'a été trouvé dans le dossier " + directoryPath);
        throw new Exception(
            "Aucun fichier qui commence par " + startWith + " n'a été trouvé dans le dossier " + directoryPath);
      } catch (Exception e1) {
        e1.printStackTrace();
      }
      break;
    default:
      try {
        this.logger.error(
            "Plusieurs fichiers qui commencent par : " + startWith + " existent dans le dossier : " + directoryPath);
        throw new Exception(
            "Plusieurs fichiers qui commencent par : " + startWith + " existent dans le dossier : " + directoryPath);
      } catch (Exception e) {
        e.printStackTrace();
      }
      break;
    }
    return retour;
  }
}
