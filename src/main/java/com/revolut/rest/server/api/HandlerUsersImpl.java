package com.revolut.rest.server.api;

import com.revolut.rest.server.constants.NameResources;
import com.revolut.rest.server.errors.ExceptionHandler;
import com.revolut.rest.service.DataOper;
import com.revolut.rest.service.UsersImpl;
import com.sun.net.httpserver.HttpExchange;
import com.revolut.rest.model.User;
import java.util.List;

public class HandlerUsersImpl extends Handler {

    private static DataOper ui = new UsersImpl();
    private static final String uriResources = "/" + NameResources.VERSION + "/" + NameResources.USERS;

    public HandlerUsersImpl(ExceptionHandler exceptionHandler) {
        super(exceptionHandler);
        ui = new UsersImpl();
    }

    public static DataOper getDataOper() { return ui; }

    @Override
    protected void execute(HttpExchange exchange) throws Exception {
        log.info("Call to " + uriResources);

        if ("GET".equals(exchange.getRequestMethod())) {
            log.info("Processing GET " + exchange.getRequestURI().toString());
            List<String> args = getParam(exchange.getRequestURI().toString(), NameResources.USERS);
            if (args.isEmpty()) {
                List<User> users = ui.getAll();
                set(exchange, users);
            } else {
                User user = (User)ui.get(args.get(0));
                set(exchange, user);
            }
        } else if ("POST".equals(exchange.getRequestMethod())) {
            log.info("Processing POST " + exchange.getRequestURI().toString());
            User user = get(exchange, User.class);
            User added = (User) ui.add(user);
            set(exchange, (added == null));
        } else if ("DELETE".equals(exchange.getRequestMethod())) {
            log.info("Processing DELETE " + exchange.getRequestURI().toString());
            List<String> args = getParam(exchange.getRequestURI().toString(), NameResources.USERS);
            boolean res = false;
            if (!args.isEmpty())
                res = ui.delete(args.get(0));

            set(exchange, res);
        }  else if ("PUT".equals(exchange.getRequestMethod())) {
            log.info("Processing PUT " + exchange.getRequestURI().toString());
            User user = get(exchange, User.class);
            User updated = (User) ui.update(user);
            set(exchange, (updated != null && user != updated));
        } else {
            throw new Exception("Bad Request");
        }

        exchange.close();
    }
}
