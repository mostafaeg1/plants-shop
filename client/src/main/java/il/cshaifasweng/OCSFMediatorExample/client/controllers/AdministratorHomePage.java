package il.cshaifasweng.OCSFMediatorExample.client.controllers;

import il.cshaifasweng.OCSFMediatorExample.client.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;

public class AdministratorHomePage {

    @FXML
    private Button customers_button;

    @FXML
    private TextField page_head_text;

    @FXML
    private Button reports_button;

    @FXML
    void showCustomers(ActionEvent event) throws IOException {
        App.setRoot("ShowAllCustomers");
    }

    @FXML
    void showReports(ActionEvent event) throws IOException {
        App.setRoot("ShowReportsForAdmin");
    }

    @FXML
    void signOut(ActionEvent event) throws IOException {
        App.setRoot("LogIN");
    }

}