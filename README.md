# Spring BATCH 3.0

Spring Boot Batch를 Spring Data Flow에 등록 하는 방법을 알아보자.
간단하게 Docker 또는 쿠버네티스를 사용하지 않고 jar 파일을 등록 해서 사용 하는 방법을 사용할 것이다.
예제 코드는 spring 3.x 버전 기준으로 작성 하였다.
먼저 간단한 배치 프로그램을 만들기 위해  h2, task, spring-batch 라이브러리를 추가 한다.
task는 data flow를 위해 추가 하였다.

```groovy
dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-batch'
    implementation 'org.springframework.cloud:spring-cloud-starter-task'
    implementation 'com.h2database:h2:2.2.224'
    testImplementation 'org.springframework.boot:spring-boot-starter-test'
    testImplementation 'org.springframework.batch:spring-batch-test'
    testRuntimeOnly 'org.junit.platform:junit-platform-launcher'
}

bootJar.enabled = true
jar.enabled = true

bootJar {
    mainClass.set("com.example.springdataflow.SpringDataFlowApplication") // Main Application의 경로를 적어준다.
    archiveFileName.set("batch-0.0.1.jar")
}
```

BatchConfig.java를 작성 해야한다. applicatiobn이 정상적으로 실행 되면 자동으로 실행된다. 
기본적으로 모든 job가 실행 되지만 @ConditionalOnProperty 을 사용할 것이기에 별다른 조건은 추가 하지 않았다.

```java
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.boot.autoconfigure.batch.JobLauncherApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(BatchProperties.class)
public class BatchConfig {

    @Bean
    @ConditionalOnMissingBean
    public JobLauncherApplicationRunner jobLauncherApplicationRunner(
            JobRepository jobRepository,
            JobLauncher jobLauncher,
            JobExplorer jobExplorer) {

        return new JobLauncherApplicationRunner(jobLauncher, jobExplorer, jobRepository);
    }
}
```

간단하게 로그를 찍는 배치 프로그램이다. 프로퍼티 spring.batch.job.name의 값이 example.hello인 경우에만 bean을 생성한다.

```java
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.task.configuration.EnableTask;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@EnableTask
@Configuration
@ConditionalOnProperty(name = "spring.batch.job.name", havingValue = "example.hello")
public class HelloJobConfiguration {

    @Bean
    public Job job(JobRepository jobRepository, Step helloStep) {
        return new JobBuilder("example.hello.job", jobRepository)
                .start(helloStep)
                .build();
    }

    @Bean
    public Step helloStep (Tasklet helloTasklet, JobRepository jobRepository, PlatformTransactionManager transactionManager){
        return new StepBuilder("example.hello.step", jobRepository)
                .tasklet(helloTasklet, transactionManager)
                .build();
    }

    @Bean
    public Tasklet helloTasklet() {
        return (contribution, chunkContext) -> {

            System.out.println("++++++++++++++++++++++++++");
            System.out.println("helloTasklet !!!");
            System.out.println("++++++++++++++++++++++++++");

            return RepeatStatus.FINISHED;
        };
    }
}
```

빌드를 실행 하고 정상적으로 실행 되는지 확인 해보자.

```shell
java ./build/libs/batch-0.0.1.jar --spring.batch.job.name=example.hello
```

이제 data flow에 batch application을 등록해보자. spring-cloud-dataflow-server를 다운 받자

```java
wget https://repo.maven.apache.org/maven2/org/springframework/cloud/spring-cloud-dataflow-server/2.11.3/spring-cloud-dataflow-server-2.11.3.jar

java -jar spring-cloud-dataflow-server-2.11.3.jar
```

# http://localhost:9393/dashboard

# Add Application
1. Add Application(s) > Register one or more applications를 클릭한다.
2. name: springDataFlow (원하는 이름을 적는다.)
3. type: task
4. spring boot version : spring boot 3.x
5. uri: 파일 경로
6. import application(s)` 버튼을 클릭한다.

#  CREATE TASK
TASK 항목에서 springDataFlow 을 드래그해서 START END 중간으로 옴긴다. 그리고 서로 연결해준다. CREATE TASK 버튼을 클릭한다.
팝업 창이 뜨면 원하는 이름을 적고 CREATE THE TASK 버튼을 클릭한다.

# Launch  the TASK
Tasks 목록에서 만든 Task의 옵션에서 Launch를 클릭힌다. 그리고 Applications Properties 항목에서 정보를 추가한다.
배치 만들때 `ConditionalOnProperty`에 설정한 정보를 입력해 주면 된다. `spring.batch.job.name` / `example.hello`
`LAUNCH TASK` 버튼을 클릭한다. 그 후 Task executions, Job executions에 각각 등록 된것을 확인 할 수 있다.

job executions 목록에서 이름을 클릭해서 상세 화면에서 VIEW LOG 버튼을 클릭해서 로그를 확인할 수 있다.






