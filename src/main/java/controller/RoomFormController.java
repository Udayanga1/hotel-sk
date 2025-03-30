package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Customer;
import model.Room;
import service.BoFactory;
import service.custom.RoomBo;
import util.BoType;

import java.util.ArrayList;

public class RoomFormController {

    @FXML
    private TableColumn colRoomNo;

    @FXML
    private TableColumn colRoomPrice;

    @FXML
    private TableColumn colRoomStatus;

    @FXML
    private TableColumn colRoomType;

    @FXML
    private TableView tblRooms;

    @FXML
    private ComboBox cmbStatus;

    @FXML
    private ComboBox cmbType;

    @FXML
    private TextField txtRoomID;

    @FXML
    private TextField txtRoomPrice;

    RoomBo roomBo = BoFactory.getInstance().getBoType(BoType.ROOM);

    @FXML
    void btnAddOnAction(ActionEvent event) {
        if (txtRoomID.getText().length()>0){
            new Alert(Alert.AlertType.ERROR, "Please clear the fields before adding a new customer").show();
        } else {
            boolean isRoomAdd = roomBo.addRoom(
                    new Room(
                            null,
                            cmbType.getValue().toString(),
                            Double.parseDouble(txtRoomPrice.getText().toString()),
                            cmbStatus.getValue().toString()
                    )
            );
            if (isRoomAdd) {
                new Alert(Alert.AlertType.INFORMATION, "Room Added!!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Romm Not Added!!").show();

            }

            loadTable();
            clearInputFields();
        }
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearInputFields();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {
        Integer roomNo = Integer.parseInt(txtRoomID.getText());



        Room room = roomBo.searchRoom(roomNo);

        System.out.println("Received to controller: " + room);
        if (room!=null) {
            new Alert(Alert.AlertType.INFORMATION, "Room Found!!").show();
            cmbType.setValue(room.getType());
            txtRoomPrice.setText(String.valueOf(room.getPrice()));
            cmbStatus.setValue(room.getStatus());
        } else {
            new Alert(Alert.AlertType.ERROR, "Room Not Found!!").show();
        }
    }

    private void clearInputFields(){
        txtRoomID.setText("");
        cmbType.setValue(null);
        txtRoomPrice.setText("");
        cmbStatus.setValue(null);
    }

    private void loadTable(){
        colRoomNo.setCellValueFactory(new PropertyValueFactory<>("id"));
        colRoomType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colRoomPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colRoomStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        ArrayList<Room> roomArrayList = new ArrayList<>();
        ObservableList<Room> roomObservableList = FXCollections.observableArrayList();

        roomBo.getAll().forEach(room -> {
            roomArrayList.add(room);
        });


        roomArrayList.forEach(room -> {
            roomObservableList.add(room);
        });

        tblRooms.setItems(roomObservableList);
    }
}
