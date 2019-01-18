package com.qant.customerprofilemanagement;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import io.restassured.RestAssured;
import io.restassured.config.MatcherConfig;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class IntegrationTestBase {
	
    @LocalServerPort
    protected int port;

    @Before
    public void setup() {
        RestAssured.port = port;
        RestAssured.config = RestAssured.config().matcherConfig(new MatcherConfig(MatcherConfig.ErrorDescriptionType.HAMCREST));
    }

    public static String loadJsonFile(final String path) {
        try (BufferedReader buffer = new BufferedReader(
                new InputStreamReader(IntegrationTestBase.class.getClassLoader().getResourceAsStream(path)))) {
            StringBuilder responseData = new StringBuilder();
            String line = null;
            while((line  = buffer.readLine()) != null) {
                responseData.append(line);
            }
            return responseData.toString();
        } catch (IOException ioe) {
            log.error(ioe.getMessage(), ioe);
        }
        return null;
    }

}