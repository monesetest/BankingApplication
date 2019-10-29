package com.monese.BankingApplication.errorHandlers;

import com.monese.BankingApplication.error.AccountNotFoundException;
import com.monese.BankingApplication.error.InsufficientBalanceException;
import com.monese.BankingApplication.error.TransactionFailedRollbackException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(AccountNotFoundException.class)
    public void AccountNotFoundException(HttpServletResponse response) throws IOException {
        response.sendError(HttpServletResponse.SC_NOT_FOUND);
    }

    @ExceptionHandler(TransactionFailedRollbackException.class)
    public void TransactionFailedRollbackException(HttpServletResponse response) throws IOException {
        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(InsufficientBalanceException.class)
    public void InsufficientBalanceException(HttpServletResponse response) throws IOException {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }
}
