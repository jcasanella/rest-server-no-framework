package com.revolut.rest.server.api;

import com.revolut.rest.model.Account;
import com.revolut.rest.server.constants.NameResources;
import com.revolut.rest.server.errors.ExceptionHandler;
import com.revolut.rest.service.AccountsImpl;
import com.revolut.rest.service.DataOper;
import com.sun.net.httpserver.HttpExchange;

import java.util.List;

public class HandlerAccountsImpl extends Handler {

    private static DataOper ui;
    private static final String uriResources = "/" + NameResources.VERSION + "/" + NameResources.ACCOUNTS;

    public HandlerAccountsImpl(ExceptionHandler exceptionHandler) {
        super(exceptionHandler);
        ui = new AccountsImpl();
    }

    @Override
    protected void execute(HttpExchange exchange) throws Exception {
        log.info("Call to " + uriResources);

        if ("GET".equals(exchange.getRequestMethod())) {
            log.info("Processing GET " + exchange.getRequestURI().toString());
            List<String> args = getParam(exchange.getRequestURI().toString(), NameResources.ACCOUNTS);
            if (args.isEmpty()) {
                List<Account> accounts = ui.getAll();
                set(exchange, accounts);
            } else {
                Account account = (Account)ui.get(args.get(0));
                set(exchange, account);
            }
        } else if ("POST".equals(exchange.getRequestMethod())) {
            log.info("Processing POST " + exchange.getRequestURI().toString());
            Account account = get(exchange, Account.class);
            ui.add(account);
            set(exchange, account);
        } else if ("DELETE".equals(exchange.getRequestMethod())) {
            log.info("Processing DELETE " + exchange.getRequestURI().toString());
            List<String> args = getParam(exchange.getRequestURI().toString(), NameResources.ACCOUNTS);
            boolean res = false;
            if (!args.isEmpty())
                res = ui.delete(args.get(0));

            set(exchange, res);
        } else if ("PUT".equals(exchange.getRequestMethod())) {
            log.info("Processing PUT " + exchange.getRequestURI().toString());
            Account account = get(exchange, Account.class);
            Account updated = (Account) ui.update(account);
            set(exchange, updated);
        } else {
            throw new Exception("Bad Request");
        }

        exchange.close();
    }

}
