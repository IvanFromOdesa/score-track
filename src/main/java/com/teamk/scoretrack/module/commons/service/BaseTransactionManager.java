package com.teamk.scoretrack.module.commons.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

// TODO: transactionTemplate configured manually as beans
@Service
public class BaseTransactionManager {
    private final TransactionTemplate transactionTemplate;

    @Autowired
    public BaseTransactionManager(TransactionTemplate transactionTemplate) {
        this.transactionTemplate = transactionTemplate;
    }

    public <T> T doInNewTransaction(TransactionCallback<T> callback) {
        return transactionTemplate.execute(callback);
    }

    public void doInNewTransaction(Runnable callback) {
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                callback.run();
            }
        });
    }
}
