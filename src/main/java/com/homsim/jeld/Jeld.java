package com.homsim.jeld;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.homsim.jeld.control.GuiceControllerFactory;
import com.homsim.jeld.di.DatabaseModule;
import jakarta.persistence.EntityManagerFactory;
import org.h2.tools.Server;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class Jeld extends Application {
    private Injector injector;

    @Override
    public void init() throws SQLException {
        // temporary for the sake of development. Make this a CLI argument "--debug" or something later
        Server.createWebServer("-webPort", "8082").start();

        injector = Guice.createInjector(new DatabaseModule());
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(Jeld.class.getResource("main-view.fxml"));

        loader.setControllerFactory(new GuiceControllerFactory(injector));

        Scene scene = new Scene(loader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() {
        EntityManagerFactory emf = injector.getInstance(EntityManagerFactory.class);
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }

}
