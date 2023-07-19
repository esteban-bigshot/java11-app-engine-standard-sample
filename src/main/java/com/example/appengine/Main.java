package com.example.appengine;

import org.eclipse.jetty.annotations.AnnotationConfiguration;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.jetty.webapp.WebInfConfiguration;

public class Main {
    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.err.println("Usage: need a relative path to the war file to execute");
            System.exit(1);
        }

        System.setProperty("org.eclipse.jetty.util.log.class", "org.eclipse.jetty.util.log.StdErrLog");
        System.setProperty("org.eclipse.jetty.LEVEL", "DEBUG");

        // Create a basic Jetty server object that will listen on port defined by
        // the PORT environment variable when present, otherwise on 8080.
        int port = Integer.parseInt(System.getenv().getOrDefault("PORT", "8080"));
        Server server = new Server(port);

        // The WebAppContext is the interface to provide configuration for a web
        // application. In this example, the context path is being set to "/" so
        // it is suitable for serving root context requests.
        WebAppContext webapp = new WebAppContext();
        webapp.setContextPath("/");
        webapp.setWar(args[0]);

        // Setting for finding servlets when requested.
        // See: https://www.eclipse.org/lists/jetty-users/msg02977.html
        webapp.setClassLoader(Main.class.getClassLoader());

        webapp.addConfiguration(new AnnotationConfiguration(), new WebInfConfiguration());

        // Set the WebAppContext as the ContextHandler for the server.
        server.setHandler(webapp);

        //To see what the server state is,
        // including what your WebAppContext looks like,
        // and its Configuration entries.
        // This is a great way to see what the changes are
        // doing to the server and contexts.
        server.setDumpAfterStart(true);

        // Start the server! By using the server.join() the server thread will
        // join with the current thread. See
        // "http://docs.oracle.com/javase/1.5.0/docs/api/java/lang/Thread.html#join()"
        // for more details.
        server.start();
        server.join();
    }

}
