package com.pozoriste;

import com.pozoriste.pages.LoginPage.LoginPage;
import com.pozoriste.pages.Page;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.UUID;
import java.util.function.Consumer;


public class Router {
    static class Route {
        Page page;

        Route (Page page) {
            this.page = page;
        }

        public Page getPage() {
            return page;
        }
    }

    private final Map<UUID, Consumer<Route>> consumers = new HashMap<>();
    private final Stack<Route> stack = new Stack<>();

    public Stack<Route> getStack(){
        return stack;
    }

    public Route getRoute(){
        return stack.peek();
    }

    public void push(Class<? extends Page> c) {
        push(c, new String[]{ });
    }

    public void push(Class<? extends Page> c, String[] args) {
        try {
            Class[] cArg = new Class[2];
            cArg[0] = Router.class;
            cArg[1] = String[].class;
            Page page = c.getDeclaredConstructor(cArg).newInstance(this, args);

            stack.push(new Route(page));

            notifySubscribers();
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException ex) {
            System.out.println(ex);
        }
    }

    public void pop() {
        if (stack.size() <= 1) {
            return;
        }

        Route route = stack.pop();

        if (stack.peek().getPage() instanceof LoginPage) {
            stack.push(route);
            return;
        }

        notifySubscribers();
    }

    public UUID subscribe(Consumer<Route> subscriber) {
        final UUID uuid = UUID.randomUUID();
        consumers.put(uuid, subscriber);
        return uuid;
    }

    private void notifySubscribers() {
        consumers.values().parallelStream()
                .forEach(subscriber -> subscriber.accept(getRoute()));
    }
}
