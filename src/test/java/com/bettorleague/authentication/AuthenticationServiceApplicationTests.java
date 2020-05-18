package com.bettorleague.authentication;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@DataMongoTest
@RunWith(SpringRunner.class)
public class AuthenticationServiceApplicationTests {

	@Test
	public void contextLoads() {
	}

}
