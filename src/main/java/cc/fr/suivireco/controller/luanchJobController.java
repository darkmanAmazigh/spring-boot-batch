// package cc.fr.suivireco.controller;
//
// import java.nio.file.Files;
// import java.nio.file.Path;
// import java.nio.file.Paths;
// import java.text.SimpleDateFormat;
// import java.util.Date;
//
// import org.springframework.batch.core.BatchStatus;
// import org.springframework.batch.core.Job;
// import org.springframework.batch.core.JobExecution;
// import org.springframework.batch.core.JobParameters;
// import org.springframework.batch.core.JobParametersBuilder;
// import org.springframework.batch.core.launch.JobLauncher;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.context.annotation.PropertySource;
// import org.springframework.core.env.Environment;
// import org.springframework.stereotype.Controller;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestMethod;
//
/// **
// * The Batch Rest controller launcher TODO change the controller to an main application to use them as profile execute
// *
// * @author Rafik BOUGHANI
// * @date 13/01/2016
// */
// @Controller
// @RequestMapping(value = "/batch")
// @PropertySource("classpath:application.properties")
// public class luanchJobController {
//
// @Autowired
// JobLauncher jobLauncher;
//
// @Autowired
// Job job;
//
// @Autowired
// private Environment env;
//
// private String PATH_NAME;
//
// @RequestMapping(method = RequestMethod.GET)
// public String handle() throws Exception {
//
// // create the current date folder like target
// String taregtDirectoryName = this.env.getProperty("imported.csvPath.newtarget");
// Date date = new Date();
// String batchDate = new SimpleDateFormat("dd-MM-yyyy").format(date);
//
// this.PATH_NAME = taregtDirectoryName + "/" + batchDate + "/";
// Path path = Paths.get(this.PATH_NAME);
// Files.createDirectories(path);
//
// JobParameters jobParameters =
// new JobParametersBuilder().addLong("time", System.currentTimeMillis()).toJobParameters();
//
// JobExecution execution = this.jobLauncher.run(this.job, jobParameters);
// System.out.println("Exit Status : " + execution.getStatus());
//
// /**
// * If the returned status is COMPLETED, then we can remove or move our files
// */
// if (execution.getStatus().equals(BatchStatus.COMPLETED)) {
// execution.stop();
// }
//
// return "done";
// }
//
// /**
// * @return pATH_NAME
// */
// public String getPATH_NAME() {
//
// return this.PATH_NAME;
// }
//
// /**
// * @param pATH_NAME the pATH_NAME to set
// */
// public void setPATH_NAME(String pATH_NAME) {
//
// this.PATH_NAME = pATH_NAME;
// }
// }
