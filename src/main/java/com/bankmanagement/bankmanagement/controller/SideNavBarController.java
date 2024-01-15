package com.bankmanagement.bankmanagement.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;

public class SideNavBarController {

    @FXML
    private Label activitiesBtn;

    @FXML
    private Label customersbtn;

    @FXML
    private Label userBtn;

    @FXML
    private Label employeesbtn;

    @FXML
    private Label ordersBtn;

    @FXML
    private Label signout;

    @FXML
    void showActivities(MouseEvent event) {

    }

    @FXML
    void showCustomers(MouseEvent event) {

    }

    @FXML
    void showDashboard(MouseEvent event) {

    }

    @FXML
    void showEmployees(MouseEvent event) {
        customersbtn.getScene().getWindow().hide();
        try{
            customersbtn.getScene().getWindow().hide();
            URL fxmllocation = getClass().getResource("/com/bankmanagement/bankmanagement/Employee-View.fxml");
            if(fxmllocation == null){
                System.err.println("fxml file not found");
                return;
            }
            FXMLLoader fxmlLoader = new FXMLLoader(fxmllocation);
            Parent page = fxmlLoader.load();
            Scene scene = new Scene(page, 779, 604);
            Stage stage = (Stage) customersbtn.getScene().getWindow();
            stage.setScene(scene);
            stage.setFullScreen(true);
            stage.setResizable(true);
            stage.show();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    void showOrders(MouseEvent event) {

    }

    @FXML
    void signout(MouseEvent event) {

    }

    public void showUsers(MouseEvent mouseEvent) {
    }
}
