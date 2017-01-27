package cc.fr.suivireco.configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;

/**
 * Archive read files
 *
 * @author Rafik BOUGHANI
 * @date 13/01/2016
 */
public class FileDeletingTasklet implements Tasklet, InitializingBean {

  private Resource directory;

  private Resource targetFileDirectory;

  private String PATH_NAME;

  /**
   * The constructor.
   *
   * @param directory
   * @param targetFileDirectory
   */
  public FileDeletingTasklet(Resource directory, Resource targetFileDirectory) {
    super();
    this.directory = directory;
    this.targetFileDirectory = targetFileDirectory;
  }

  public void setDirectoryResource(Resource directory) {

    this.directory = directory;
  }

  @Override
  public void afterPropertiesSet() throws Exception {

    Assert.notNull(this.directory, "directory must be set");
    Assert.notNull(this.targetFileDirectory, "targetFileDirectory must be set");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

    String taregtDirectoryName = this.targetFileDirectory.getFilename();
    Date date = new Date();
    String batchDate = new SimpleDateFormat("dd-MM-yyyy").format(date);

    this.PATH_NAME = taregtDirectoryName + "/" + batchDate + "/";
    Path path = Paths.get(this.PATH_NAME);
    Files.createDirectories(path);

    File dir = this.directory.getFile();
    Assert.state(dir.isDirectory());

    InputStream inStream = null;
    OutputStream outStream = null;

    File[] files = dir.listFiles();

    // Create the target directory

    File organismeFile = new FileSystemResource(this.PATH_NAME + files[0].getName()).getFile();

    File controlFile = new FileSystemResource(this.PATH_NAME + files[1].getName()).getFile();

    File themeFile = new FileSystemResource(this.PATH_NAME + files[2].getName()).getFile();

    // targetFile contains 3 files
    File[] targetFile = { organismeFile, controlFile, themeFile };

    for (int i = 0; i < files.length; i++) {

      inStream = new FileInputStream(files[i]);
      outStream = new FileOutputStream(targetFile[i]);

      byte[] buffer = new byte[1024];

      int length;
      // copy the file content in bytes
      while ((length = inStream.read(buffer)) > 0) {

        outStream.write(buffer, 0, length);

      }

      buffer = null;
      outStream.flush();
      outStream.close();
      inStream.close();

      // delete the original file
      if (files[i].delete()) {
        System.out.println(files[i].getName() + " deleted!");
      } else {
        System.out.println(files[i].getName() + " delete failed.");
      }
      files[i] = null;
    }
    return RepeatStatus.FINISHED;
  }

}