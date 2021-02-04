package com.ohalo.user.repositories;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;

import com.ohalo.user.TestConfig;

@DataJpaTest
@Import(TestConfig.class)
@TestPropertySource(locations = "classpath:application-dev.properties")
public class RepoTest {

}
