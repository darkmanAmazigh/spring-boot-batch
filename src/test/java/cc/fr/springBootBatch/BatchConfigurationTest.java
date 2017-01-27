package cc.fr.springBootBatch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.StepScopeTestExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import cc.fr.suivireco.configuration.Application;

/**
 * The Batch configuration JUnit test
 *
 * @author Rafik BOUGHANI
 * @date 12/01/2016
 */
// @ContextConfiguration(locations = { "classpath:spring/spring-context.xml" })
@ContextConfiguration(classes = Application.class, loader = SpringApplicationContextLoader.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, StepScopeTestExecutionListener.class })
@RunWith(SpringJUnit4ClassRunner.class)
public class BatchConfigurationTest {

  // private JobLauncherTestUtils jobLauncherTestUtils;

  private ItemReader<String> reader;

  @Autowired
  ApplicationContext context;

  @Test
  public void testReader() throws UnexpectedInputException, ParseException, NonTransientResourceException, Exception {

    // The reader is initialized and bound to the input data
    assertNotNull(this.reader.read());
  }

  @Test
  public void launchJob() throws Exception {

    JobLauncherTestUtils jobLauncherTestUtils = this.context.getBean(JobLauncherTestUtils.class);
    JobExecution jobExecution = jobLauncherTestUtils.launchJob();

    assertEquals(BatchStatus.COMPLETED, jobExecution.getStatus());

  }

  @Bean
  public JobLauncherTestUtils getJobLauncherTestUtils() {

    return new JobLauncherTestUtils() {
      @Override
      @Autowired
      public void setJob(@Qualifier("job") Job job) {

        super.setJob(job);
      }
    };
  }
}
