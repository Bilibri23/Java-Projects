package com.bankmanagement.bankmanagement.controller;

import com.bankmanagement.bankmanagement.dao.persistentDao.EmployeeDao;
import com.bankmanagement.bankmanagement.helper.status.UserStatus;
import com.bankmanagement.bankmanagement.model.Employee;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.collections.FXCollections;

import java.net.URL;
import java.sql.*;

import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.scene.control.cell.PropertyValueFactory;

public class EmployeeViewController implements Initializable {
    @FXML
    private TableColumn<Employee, String> address1Colmn;

    @FXML
    private TableColumn<Employee, String> address2Colmn;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<Employee, String> cityColmn;

    @FXML
    private TableColumn<Employee, Date> created_dateColmn;

    @FXML
    private TableColumn<Employee, String> emailColmn;

    @FXML
    private TableColumn<Employee, String> first_nameColmn;

    @FXML
    private TableColumn<Employee, Long> idColmn;

    @FXML
    private TableColumn<Employee, String> last_nameColmn;

    @FXML
    private TableColumn<Employee, Date> last_updatedColmn;

    @FXML
    private TableColumn<Employee, String> passwordColmn;

    @FXML
    private TableColumn<Employee,String> phone_numberColmn;

    @FXML
    private TableColumn<Employee, String> roleColmn;

    @FXML
    private TableColumn<Employee, UserStatus> statusColmn;

    @FXML
    private TableView<Employee> table;

    @FXML
    private TextField txtaddress1;

    @FXML
    private TextField txtaddress2;

    @FXML
    private TextField txtcity;

    @FXML
    private DatePicker created_pick;

    @FXML
    private TextField txtemail;

    @FXML
    private TextField txtfirst_name;



    @FXML
    private TextField txtlast_name;

    @FXML
    private DatePicker last_pick;

    @FXML
    private TextField txtpassword;

    @FXML
    private TextField txtphone_number;

    @FXML
    private TextField txtrole;

    @FXML
    private ChoiceBox<UserStatus> statusbox;

    @FXML
    private TextField txtusername;

    @FXML
    private TableColumn<Employee, String> usernameColmn;

    EmployeeDao employeeDao = new EmployeeDao();

    @FXML
    public void Add(ActionEvent event) {
        String address1, address2,  city,  email,
                first_name, last_name, password, phone_number,
                role, username;
        Date created_date, last_updated;
        UserStatus status;


        address1 = txtaddress1.getText();
        address2 = txtaddress2.getText();
        city = txtcity.getText();
        created_date = Date.valueOf(created_pick.getValue());
        email = txtemail.getText();
        first_name = txtfirst_name.getText();
        last_name = txtlast_name.getText();
        last_updated = Date.valueOf(last_pick.getValue());
        password = txtpassword.getText();
        phone_number = txtphone_number.getText();
        role = txtrole.getText();
        status = statusbox.getValue();
        username  = txtusername.getText();

        try{
            pst = con.prepareStatement("insert into employee(address1, address2,  city, created_date, email,\n" +
                    "                        first_name, last_name, last_updated, password, phone_number,\n" +
                    "                       role, status, username)values(?,?,?,?,?,?,?,?,?,?,?,?,?)");


            pst.setString(1, address1);
            pst.setString(2, address2);
            pst.setString(3, city);
            pst.setDate(4, created_date);
            pst.setString(5, email);
            pst.setString(6, first_name);
            pst.setString(7, last_name);
            pst.setString(8, String.valueOf(last_updated));
            pst.setString(9, password);
            pst.setString(10, phone_number);
            pst.setString(11, role);
            pst.setInt(12, UserStatus.ACTIVE.ordinal());
            pst.setString(13, username);


            pst.executeUpdate();


            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Test connection");

            alert.setHeaderText("employee registtration");
            alert.setContentText("addedddd!");
            alert.showAndWait();

            //putting the record in the table(gui)

            table(); // calling the method that transfers information in the table






        }catch (SQLException e){
            Logger.getLogger(EmployeeViewController.class.getName()).log(Level.SEVERE, null , e);

        }

    }
    public void table() {
        connect();

        try {

            pst = con.prepareStatement("select  id, address1, address2,  city, created_date, email,\n" +
                    "                        first_name, last_name, last_updated, password, phone_number,\n" +
                    "                       role, status, username from employee ");
            ResultSet rs = pst.executeQuery();

            {
                while (rs.next()) {
                    Employee em = new Employee();
                    em.setId(rs.getLong("id"));
                    em.setAddress1(rs.getString("address1"));
                    em.setAddress2(rs.getString("address2"));
                    em.setCity(rs.getString("city"));
                    em.setCreatedDate(rs.getDate("created_date"));
                    em.setEmail(rs.getString("email"));
                    em.setFirstName(rs.getString("first_name"));
                    em.setLastName(rs.getString("last_name"));
                    em.setLastUpdated(rs.getDate("last_updated"));
                    em.setPassword(rs.getString("password"));
                    em.setPhoneNumber(rs.getString("phone_number"));
                    em.setRole(rs.getString("role"));
                    em.setStatus(UserStatus.valueOf(String.valueOf(rs.getInt("status"))));
                    em.setUsername(rs.getString("username"));

                    employees.add(em);
                }
            }
            table.setItems(employees);
        } catch (SQLException e) {
            Logger.getLogger(EmployeeViewController.class.getName()).log(Level.SEVERE, null, e);
        }
        table.setRowFactory(tv -> {
            TableRow<Employee> myRow = new TableRow<>();
            myRow.setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getClickCount() == 1 && (!myRow.isEmpty())) {
                    myIndex = table.getSelectionModel().getSelectedIndex();
                    id = Integer.parseInt(String.valueOf(table.getItems().get(myIndex).getId()));
                    txtaddress1.setText(table.getItems().get(myIndex).getAddress1());
                    txtaddress2.setText(table.getItems().get(myIndex).getAddress2());
                    txtcity.setText(table.getItems().get(myIndex).getCity());
                    //txtcreated_date.setText(String.valueOf(table.getItems().get(myIndex).getCreatedDate()));

                    created_pick.setValue(Date.valueOf(String.valueOf(table.getItems().get(myIndex).getCreatedDate())).toLocalDate());
                    
                    txtemail.setText(table.getItems().get(myIndex).getEmail());
                    txtfirst_name.setText(table.getItems().get(myIndex).getFirstName());
                    txtlast_name.setText(table.getItems().get(myIndex).getLastName());
                    //txtlast_updated.setText(String.valueOf(table.getItems().get(myIndex).getLastUpdated()));
                    last_pick.setValue(Date.valueOf(String.valueOf(table.getItems().get(myIndex).getCreatedDate())).toLocalDate());
                    txtpassword.setText(table.getItems().get(myIndex).getPassword());
                    txtphone_number.setText(table.getItems().get(myIndex).getPhoneNumber());
                    txtrole.setText(table.getItems().get(myIndex).getRole());
                    //txtstatus.setText(String.valueOf(table.getItems().get(myIndex).getStatus()));
                    statusbox.setValue(table.getItems().get(myIndex).getStatus());
                    txtusername.setText(table.getItems().get(myIndex).getUsername());

                }
            });
            return myRow;

        });
    }
    @FXML
    public void Delete(ActionEvent event) {
        myIndex = table.getSelectionModel().getSelectedIndex();
        id = Integer.parseInt(String.valueOf(table.getItems().get(myIndex).getId()));

        try {
            pst = con.prepareStatement("delete from employee where id = ? ");
            pst.setLong(1, id);
            pst.executeUpdate();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("employee registration");
            alert.setContentText("Deletedd!");
            alert.showAndWait();
            table();
        }
        catch (SQLException e){
            Logger.getLogger(EmployeeViewController.class.getName()).log(Level.SEVERE, null, e);

        }
    }
    @FXML
    public void Update(ActionEvent event) {
        String address1, address2,  city,  email,
                first_name, last_name,  password, phone_number,
                role, username;
        Date created_date;
        Date last_updated;
        UserStatus status;
        int myIndex = table.getSelectionModel().getSelectedIndex();
        id = Integer.parseInt(String.valueOf(table.getItems().get(myIndex).getId()));


        address1 = txtaddress1.getText();
        address2 = txtaddress2.getText();
        city = txtcity.getText();
        created_date = Date.valueOf(created_pick.getValue());
        email = txtemail.getText();
        first_name = txtfirst_name.getText();
        last_name = txtlast_name.getText();
        last_updated = Date.valueOf(last_pick.getValue());
        password = txtpassword.getText();
        phone_number = txtphone_number.getText();
        role = txtrole.getText();
        status = statusbox.getValue();
        username  = txtusername.getText();

        try{
            pst = con.prepareStatement("update employee set address1 = ?, address2 = ?, city =?, created_date = ?," +
                    "email = ?, first_name =?, last_name =?, last_updated = ?, password = ?, phone_number = ?, role = ?, " +
                    "status = ?,  username = ? where id = ? ");
            pst.setString(1, address1);
            pst.setString(2, address2);
            pst.setString(3, city);
            pst.setDate(4, created_date);
            pst.setString(5, email);
            pst.setString(6, first_name);
            pst.setString(7, last_name);
            pst.setString(8, String.valueOf(last_updated));
            pst.setString(9, password);
            pst.setString(10, phone_number);
            pst.setString(11, role);
            pst.setString(12, String.valueOf(status));
            pst.setString(13, username);
            pst.setLong(14, id);
            pst.executeUpdate();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("employee registration");
            alert.setContentText("updatedd!");
            alert.showAndWait();
            table();

        }
        catch (SQLException e){
            Logger.getLogger(EmployeeViewController.class.getName()).log(Level.SEVERE, null, e);
        }
    }



    Connection con;
    PreparedStatement pst;
    int myIndex;
    int id;


    public void connect () {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank", "root", "marc2023brian2004");

        } catch (ClassNotFoundException e) {

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    ObservableList<Employee> employees = FXCollections.observableArrayList();
    @Override
    public void initialize (URL url, ResourceBundle resourceBundle){
        connect();
        statusbox.getItems().addAll(UserStatus.values());

        List<Employee> employeeList = employeeDao.findAll();
        idColmn.setCellValueFactory(new PropertyValueFactory<Employee, Long>("id"));
        address1Colmn.setCellValueFactory(new PropertyValueFactory<Employee, String>("address1"));
        address2Colmn.setCellValueFactory(new PropertyValueFactory<Employee, String>("address2"));
        cityColmn.setCellValueFactory(new PropertyValueFactory<Employee, String>("city"));
        created_dateColmn.setCellValueFactory(new PropertyValueFactory<Employee, Date>("createdDate"));
        emailColmn.setCellValueFactory(new PropertyValueFactory<Employee, String>("email"));
        first_nameColmn.setCellValueFactory(new PropertyValueFactory<Employee, String>("firstName"));
        last_nameColmn.setCellValueFactory(new PropertyValueFactory<Employee, String>("lastName"));
        last_updatedColmn.setCellValueFactory(new PropertyValueFactory<Employee, Date>("lastUpdated"));
        passwordColmn.setCellValueFactory(new PropertyValueFactory<Employee, String>("password"));
        phone_numberColmn.setCellValueFactory(new PropertyValueFactory<Employee, String>("phoneNumber"));
        roleColmn.setCellValueFactory(new PropertyValueFactory<Employee, String>("role"));
        statusColmn.setCellValueFactory(new PropertyValueFactory<Employee, UserStatus>("status"));
        usernameColmn.setCellValueFactory(new PropertyValueFactory<Employee, String>("username"));
        employees.addAll(employeeList);

        table.setEditable(true);
        table.setItems(employees);
    }
}
