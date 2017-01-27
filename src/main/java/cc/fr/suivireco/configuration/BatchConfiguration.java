package cc.fr.suivireco.configuration;

import java.nio.charset.Charset;
import java.util.Properties;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import cc.fr.suivireco.csventity.ControlCSV;
import cc.fr.suivireco.csventity.OrganismeCSV;
import cc.fr.suivireco.csventity.ThemeCSV;
import cc.fr.suivireco.processor.ControlItemProcessor;
import cc.fr.suivireco.processor.OrganismeItemProcessor;
import cc.fr.suivireco.processor.ThemeItemProcessor;
import cc.fr.suivireco.utility.CSVFiles;
import fr.cc.suivireco.productionmanagement.dataaccess.api.ControleEntity;
import fr.cc.suivireco.productionmanagement.dataaccess.api.OrganismeEntity;

/**
 * The Batch configuration
 *
 * @author Rafik BOUGHANI
 * @date 07/01/2016
 */
@Configuration
@EnableBatchProcessing
@EntityScan("fr.cc.suivireco.productionmanagement.dataaccess.api")
@ComponentScan("fr.cc.suivireco")
// spring boot configuration
@EnableAutoConfiguration
// file that contains the properties
@EnableJpaRepositories(basePackages = { "cc.fr.suivireco.repository" })
@PropertySource("classpath:application.properties")
public class BatchConfiguration {

  private Logger logger = LoggerFactory.getLogger(OrganismeItemProcessor.class);

  private String ORGANISME_CSV_FILE;

  private String CONTROL_CSV_FILE;

  private String THEME_CSV_FILE;

  CSVFiles csvFile;

  @Autowired
  private Environment env;

  /*
   * Load the properties value
   */
  @Value("${database.driver}")
  private String databaseDriver;

  @Value("${database.url}")
  private String databaseUrl;

  @Value("${database.username}")
  private String databaseUsername;

  @Value("${database.password}")
  private String databasePassword;

  @Value("${import.csvPath}")
  private String csvPath;

  @Value("${organisme.name.startwith}")
  private String organismeFilePrefix;

  @Value("${controle.name.startwith}")
  private String controlFieldPrefix;

  @Value("${theme.name.startwith}")
  private String themeFieldPrefix;

  /**
   * The constructor.
   */
  public BatchConfiguration() {
    super();
    this.csvFile = new CSVFiles();
  }

  /**
   * We define a bean that read each line of the input file.
   *
   * @return
   */
  @Bean
  public ItemReader<OrganismeCSV> reader() {

    FlatFileItemReader<OrganismeCSV> itemReader = new FlatFileItemReader<OrganismeCSV>();

    this.ORGANISME_CSV_FILE = this.csvFile.getFileToRead(this.organismeFilePrefix, this.csvPath).getAbsolutePath();

    itemReader.setResource(new FileSystemResource(this.ORGANISME_CSV_FILE));
    System.err.println(Charset.defaultCharset());
    itemReader.setEncoding("ISO8859-1");
    // DelimitedLineTokenizer defaults to comma as its delimiter
    DefaultLineMapper<OrganismeCSV> lineMapper = new DefaultLineMapper<OrganismeCSV>();
    lineMapper.setLineTokenizer(new DelimitedLineTokenizer() {
      {
        setDelimiter("||");
        setNames(new String[] { "or_crc", "or_nom", "or_id", "ortyp", "or_nat", "or_dpt", "typ_code" });
      }
    });

    lineMapper.setFieldSetMapper(new PlayerFieldSetMapper());
    itemReader.setLineMapper(lineMapper);
    itemReader.setLinesToSkip(1);
    itemReader.open(new ExecutionContext());
    itemReader.close();

    return itemReader;
  }

  public static class PlayerFieldSetMapper implements FieldSetMapper<OrganismeCSV> {
    public OrganismeCSV mapFieldSet(FieldSet fieldSet) {

      OrganismeCSV organismeCSV = new OrganismeCSV();

      organismeCSV.setOr_crc((!fieldSet.readString(0).isEmpty()) ? Long.parseLong(fieldSet.readString(0)) : null);
      organismeCSV.setOr_nom(fieldSet.readString(1));
      organismeCSV.setOr_id((!fieldSet.readString(2).isEmpty()) ? Long.parseLong(fieldSet.readString(2)) : null);
      organismeCSV.setOr_dpt((!fieldSet.readString(3).isEmpty()) ? Long.parseLong(fieldSet.readString(3)) : null);
      organismeCSV.setOr_nat(fieldSet.readString(4));
      organismeCSV.setOrtyp(fieldSet.readString(5));
      organismeCSV.setTyp_code(fieldSet.readRawString(6));

      return organismeCSV;
    }
  }

  @Bean
  public ItemReader<ControlCSV> reader2() {

    FlatFileItemReader<ControlCSV> itemReader = new FlatFileItemReader<ControlCSV>();

    this.CONTROL_CSV_FILE = this.csvFile.getFileToRead(this.controlFieldPrefix, this.csvPath).getAbsolutePath();

    itemReader.setResource(new FileSystemResource(this.CONTROL_CSV_FILE));
    itemReader.setEncoding("ISO8859-1");

    DefaultLineMapper<ControlCSV> lineMapper = new DefaultLineMapper<ControlCSV>();
    lineMapper.setLineTokenizer(new DelimitedLineTokenizer() {
      {

        setDelimiter("||");
        setNames(new String[] { "CL_CRC", "CL_ID", "OR_ID", "CL_NUM", "CL_LIBC", "CD_CODE", "CD_LIBL" });

      }
    });

    lineMapper.setFieldSetMapper(new ControlFieldSetMapper());
    itemReader.setLineMapper(lineMapper);
    itemReader.setLinesToSkip(1);
    itemReader.open(new ExecutionContext());
    itemReader.close();

    return itemReader;
  }

  public static class ControlFieldSetMapper implements FieldSetMapper<ControlCSV> {
    public ControlCSV mapFieldSet(FieldSet fieldSet) {

      ControlCSV control = new ControlCSV();

      control.setCL_CRC(fieldSet.readString(0));
      control.setCL_ID(fieldSet.readString(1));
      control.setOR_ID(fieldSet.readString(2));
      control.setCL_NUM(fieldSet.readRawString(3));
      control.setCL_LIBC(fieldSet.readString(4));
      control.setCD_CODE(fieldSet.readString(5));
      control.setCD_LIBL(fieldSet.readRawString(6));

      return control;
    }
  }

  @Bean
  public ItemReader<ThemeCSV> reader3() {

    FlatFileItemReader<ThemeCSV> itemReader = new FlatFileItemReader<ThemeCSV>();

    this.THEME_CSV_FILE = this.csvFile.getFileToRead(this.themeFieldPrefix, this.csvPath).getAbsolutePath();

    itemReader.setResource(new FileSystemResource(this.THEME_CSV_FILE));
    itemReader.setEncoding("ISO8859-1");
    DefaultLineMapper<ThemeCSV> lineMapper = new DefaultLineMapper<ThemeCSV>();
    lineMapper.setLineTokenizer(new DelimitedLineTokenizer() {
      {

        setDelimiter("||");
        setNames(new String[] { "cl_crc", "cl_id", "theme_libl" });

      }
    });

    lineMapper.setFieldSetMapper(new ThemeFieldSetMapper());
    itemReader.setLineMapper(lineMapper);
    itemReader.setLinesToSkip(1);
    itemReader.open(new ExecutionContext());
    itemReader.close();

    return itemReader;
  }

  public static class ThemeFieldSetMapper implements FieldSetMapper<ThemeCSV> {
    public ThemeCSV mapFieldSet(FieldSet fieldSet) {

      ThemeCSV themecsv = new ThemeCSV();

      themecsv.setCl_crc(fieldSet.readString(0));
      themecsv.setCl_id(fieldSet.readString(1));
      themecsv.setTheme_libl(fieldSet.readString(2));

      return themecsv;
    }
  }

  /**
   * The ItemProcessor is called after a new line is read and it allows the developer to transform the data read In our
   * example it simply return the original object
   *
   * @return
   */
  @Bean
  public ItemProcessor<OrganismeCSV, OrganismeEntity> processor() {

    return new OrganismeItemProcessor();
  }

  @Bean
  public ItemProcessor<ControlCSV, ControleEntity> processor2() {

    return new ControlItemProcessor();
  }

  @Bean
  public ItemProcessor<ThemeCSV, ControleEntity> processor3() {

    return new ThemeItemProcessor();
  }

  /**
   * Nothing special here a simple JpaItemWriter
   *
   * @return
   */
  @Bean
  public ItemWriter<OrganismeEntity> writer() {

    // String sqlStatement =
    // "INSERT INTO organisme(CRC_ARIANE, OR_ID_ARIANE, LIBELLE, NATURE_JURIDIQUE, DEPARTEMENT, LOI_NOTRE,
    // ID_PRODUCTION"
    // + "VALUES (:id, :firstName, :lastName, :phoneNumber)";
    // JdbcBatchItemWriter<OrganismeEntity> jdbc = new JdbcBatchItemWriter<OrganismeEntity>();

    JpaItemWriter writer = new JpaItemWriter<OrganismeEntity>();
    writer.setEntityManagerFactory(entityManagerFactory().getObject());

    return writer;
  }

  @Bean
  public ItemWriter<ControleEntity> writer2() {

    JpaItemWriter writer = new JpaItemWriter<ControleEntity>();
    writer.setEntityManagerFactory(entityManagerFactory().getObject());

    return writer;
  }

  @Bean
  public ItemWriter<ControleEntity> writer3() {

    JpaItemWriter writer = new JpaItemWriter<ControleEntity>();
    writer.setEntityManagerFactory(entityManagerFactory().getObject());

    return writer;
  }

  /**
   * This method declare the steps that the batch has to follow
   *
   * @param jobs
   * @param s1
   * @return
   */
  @Bean
  public Job importPerson(JobBuilderFactory jobs, @Qualifier("step1") Step s1, @Qualifier("step2") Step s2,
      @Qualifier("step3") Step s3, @Qualifier("step4") Step s4) {

    return jobs.get("import").incrementer(new RunIdIncrementer()).flow(s1).next(s2).next(s3).next(s4).end().build();
  }

  /**
   * Step We declare that every 1000 lines processed the data has to be committed
   *
   * @param stepBuilderFactory
   * @param reader
   * @param writer
   * @param processor
   * @return
   */

  @Bean
  public Step step1(StepBuilderFactory stepBuilderFactory, @Qualifier("reader") ItemReader<OrganismeCSV> reader,
      @Qualifier("writer") ItemWriter<OrganismeEntity> writer,
      @Qualifier("processor") ItemProcessor<OrganismeCSV, OrganismeEntity> processor) {

    return stepBuilderFactory.get("step1").<OrganismeCSV, OrganismeEntity> chunk(2000).reader(reader)
        .processor(processor).writer(writer).build();
  }

  /**
   * Ici le chunk est à 1, pour verifier qu'on insert qu'une seul ligne des doublons, par contre ça bouf en terme de
   * performance
   *
   * @param stepBuilderFactory
   * @param reader2
   * @param writer2
   * @param processor2
   * @return the step 2
   */
  @Bean
  public Step step2(StepBuilderFactory stepBuilderFactory, @Qualifier("reader2") ItemReader<ControlCSV> reader2,
      @Qualifier("writer2") ItemWriter<ControleEntity> writer2,
      @Qualifier("processor2") ItemProcessor<ControlCSV, ControleEntity> processor2) {

    return stepBuilderFactory.get("step2").<ControlCSV, ControleEntity> chunk(1).reader(reader2).processor(processor2)
        .writer(writer2).build();
  }

  @Bean
  public Step step3(StepBuilderFactory stepBuilderFactory, @Qualifier("reader3") ItemReader<ThemeCSV> reader3,
      @Qualifier("writer3") ItemWriter<ControleEntity> writer3,
      @Qualifier("processor3") ItemProcessor<ThemeCSV, ControleEntity> processor3) {

    return stepBuilderFactory.get("step3").<ThemeCSV, ControleEntity> chunk(1000).reader(reader3).processor(processor3)
        .writer(writer3).build();
  }

  @Bean
  public Step step4(StepBuilderFactory stepBuilderFactory) {

    return stepBuilderFactory.get("step4")
        .tasklet(new FileDeletingTasklet(new FileSystemResource(this.env.getProperty("import.csvPath")),
            new FileSystemResource(this.env.getProperty("imported.csvPath.newtarget"))))
        .build();
  }

  /**
   * As data source we use an external database
   *
   * @return
   */

  @Bean
  public DataSource dataSource() {

    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName(this.databaseDriver);
    dataSource.setUrl(this.databaseUrl);
    dataSource.setUsername(this.databaseUsername);
    dataSource.setPassword(this.databasePassword);
    return dataSource;
  }

  @Bean
  public LocalContainerEntityManagerFactoryBean entityManagerFactory() {

    LocalContainerEntityManagerFactoryBean lef = new LocalContainerEntityManagerFactoryBean();
    lef.setPackagesToScan("cc.fr.suivireco");
    lef.setDataSource(dataSource());
    lef.setJpaVendorAdapter(jpaVendorAdapter());
    lef.setJpaProperties(new Properties());
    return lef;
  }

  @Bean
  public JpaVendorAdapter jpaVendorAdapter() {

    HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
    jpaVendorAdapter.setDatabase(Database.ORACLE);
    jpaVendorAdapter.setGenerateDdl(true);
    jpaVendorAdapter.setShowSql(false);

    // jpaVendorAdapter.setDatabasePlatform("org.hibernate.dialect.MySQLDialect");
    jpaVendorAdapter.setDatabasePlatform("org.hibernate.dialect.Oracle10gDialect");
    return jpaVendorAdapter;
  }

}
