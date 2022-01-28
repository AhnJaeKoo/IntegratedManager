package com.enuri.integratedmanager;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.enuri.integratedmanager.jpa.entity.LogList;
import com.enuri.integratedmanager.jpa.repository.LogListRepository;

@RunWith(SpringRunnerTest.class) // => Junit4 사용시 추가, Junit5 사용시 생략.
@DataJpaTest
@ActiveProfiles("local,h2")
//properties = {"spring.config.location=classpath:application-test.properties"} => 파일을 읽을수 있다.
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)	// 테스트 끝나면 롤백 => yml에 설정값으로 대처
public class JpaTest {

	@Autowired
	private LogListRepository logListRepository;

	@Value("${propertyTest.value}")
	private String propertyTestValue; //propertyTest

	@Value("${testValue}")
	private String value; //test

	@Before
	public void init() {

	}

	@Test
	public void insertTable() {
		int schduleId = 999;
		String backupPath = "backupPath";
		String dayCut = "N";

		LogList logList = LogList.builder().schduleId(schduleId).backupPath(backupPath).dayCut(dayCut).build();
		logListRepository.save(logList);
		//logListRepository.flush();
		List<LogList> list = logListRepository.findBySchduleId(schduleId);

		LogList selectLogList = list.get(0);
		assertThat(selectLogList.getSchduleId()).isEqualTo(schduleId);
		assertThat(selectLogList.getBackupPath()).isEqualTo(backupPath);
		assertThat(selectLogList.getDayCut()).isEqualTo(dayCut);

	}

	@After	//종료시 실행
    public void deleteProgramId() {
		logListRepository.removeBySchduleId("TestId");
    }

}
