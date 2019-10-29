package com.monese.BankingApplication.api;

import com.monese.BankingApplication.model.Transaction;
import com.monese.BankingApplication.model.TransactionId;
import com.monese.BankingApplication.repository.TransactionRepository;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.runner.RunWith;

import javax.annotation.PostConstruct;

import java.math.BigDecimal;
import java.util.UUID;

import static io.restassured.RestAssured.with;
import static java.net.HttpURLConnection.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class ApiControllerTest {

    @Autowired
    private TransactionRepository transactionRepository;

    @LocalServerPort
    private int port;

    @Before
    public void setUp() {
        RestAssured.port = port;
    }

    private String uri;

    @PostConstruct
    public void init() { uri = "http://localhost:" + port;}

    @Test
    public void getStatementForAccount() {

        Transaction transaction = Transaction.builder()
                .transactionType(TransactionId.TransactionType.DEBIT)
                .transactionId(UUID.randomUUID())
                .targetAccountId(1L)
                .resultingBalance(new BigDecimal(90))
                .amount(new BigDecimal(10))
                .sourceId(2L)
                .build();

        transactionRepository.save(transaction);

        with().given().contentType("application/json\r\n")
                .when()
                .request("GET", "/accounts/1/statement")
                .then().log().body().statusCode(HTTP_OK);
    }

    @Test
    public void getStatementForAccountThatDoesNotExist() {

        with().given().contentType("application/json\r\n")
                .when()
                .request("GET", "/accounts/99/statement")
                .then().log().body().statusCode(HTTP_NOT_FOUND);
    }

    @Test
    public void makeNegativeTransactionForAccount() {

        String payload = "{\n" +
                "  \"accountId\": \"2\",\n" +
                "  \"amount\": \"-13.00\",\n" +
                "  \"targetAccountId\": \"1\",\n" +
                "  \"comment\": \"foo\"\n" +
                "}";

        with().given()
                .contentType("application/json\r\n")
                .body(payload)
                .post("/transactions")
                .then()
                .statusCode(HTTP_BAD_REQUEST)
                .log();

    }

    @Test
    public void makeTransactionForAccount() {

        String payload = "{\n" +
                "  \"sourceId\": \"2\",\n" +
                "  \"amount\": \"13.00\",\n" +
                "  \"targetAccountId\": \"1\",\n" +
                "  \"comment\": \"foo\"\n" +
                "}";

        with().given()
                .contentType("application/json\r\n")
                .body(payload)
                .post("/transactions")
                .then()
                .statusCode(HTTP_CREATED)
                .log();

    }

    @Test
    public void makeTransactionForAccountThatDoesNotExist() {

        String payload = "{\n" +
                "  \"sourceId\": \"99\",\n" +
                "  \"amount\": \"13.00\",\n" +
                "  \"targetAccountId\": \"1\",\n" +
                "  \"comment\": \"foo\"\n" +
                "}";

        with().given()
                .contentType("application/json\r\n")
                .body(payload)
                .post("/transactions")
                .then()
                .statusCode(HTTP_NOT_FOUND)
                .log();

    }

    @Test
    public void makeInvalidTransactionForAccount() {

        String payload = "{\n" +
                "  \"accountI\": \"2\",\n" +
                "  \"targetAccountId\": \"1\",\n" +
                "  \"comment\": \"foo\"\n" +
                "}";

        with().given()
                .contentType("application/json\r\n")
                .body(payload)
                .post("/transactions")
                .then()
                .statusCode(HTTP_BAD_REQUEST)
                .log();

    }
}
