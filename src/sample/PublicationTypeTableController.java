package sample;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.NoSuchElementException;
import java.util.Optional;

public class PublicationTypeTableController {
    public ScientificWorksDAO dao=ScientificWorksDAO.getInstance();
    public static PublicationTypeModel model=new PublicationTypeModel();
    public TableView<PublicationType>table;
    public TableColumn<PublicationType, SimpleIntegerProperty> id;
    public TableColumn<PublicationType, SimpleStringProperty>type;
    public TextField textField;
    public TextField textField1;
    public TextField textField2;


    @FXML
    private void initialize() {

        id.setCellValueFactory(new PropertyValueFactory<PublicationType, SimpleIntegerProperty>("id"));
        type.setCellValueFactory(new PropertyValueFactory<PublicationType, SimpleStringProperty>("Type"));

        FilteredList<PublicationType> flPerson = new FilteredList(model.getScWork(), p -> true);
        textField.setPromptText("Search here...");
        textField.setOnKeyReleased(keyEvent ->
        {
            flPerson.setPredicate(p -> {
                if(p.getType().toLowerCase().contains(textField.getText().toLowerCase().trim()))return true;
                if(String.valueOf(p.getId()).toLowerCase().contains(textField.getText().toLowerCase().trim()))return true;
                return false;
            });
            });
        SortedList<PublicationType> sortedData = new SortedList<>(flPerson);
        sortedData.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedData);

        table.setRowFactory(tv -> {
            TableRow<PublicationType> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && (! row.isEmpty()) ) {
                    textField2.textProperty().bindBidirectional(new SimpleStringProperty(table.getSelectionModel().getSelectedItem().getType()));
                }
            });
            return row ;
        });
        textField1.setOnMouseClicked(event -> {
            textField2.clear();
            table.getSelectionModel().clearSelection();
        });
    }

    public void delete (ActionEvent actionEvent)throws NoSelectedExeption {

        try {
            int id = table.getSelectionModel().getSelectedItem().getId();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure?");
            Optional<ButtonType> action= alert.showAndWait();
            if(action.get()== ButtonType.OK){
                dao.setNull1(id);
                dao.deletePT(id);
                model.getScWork().clear();
                model.reload();
                initialize();
                textField2.clear();
            }

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("No selected item");
            Optional<ButtonType> action= alert.showAndWait();
            try{
                if(action.get().equals(ButtonType.OK)){
                    alert.close();
                }}
            catch(NoSuchElementException z){
                return;
            }
            return;
        }
    }
    public void add (ActionEvent actionEvent){
        dao.addTypeP(textField1.getText());
        model.getScWork().clear();
        model.reload();
        initialize();
        textField1.clear();
    }

    public void update (ActionEvent actionEvent){
        if(table.getSelectionModel().getSelectedItem()==null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("No selected item");
            Optional<ButtonType> action= alert.showAndWait();
            try{
                if(action.get().equals(ButtonType.OK)){
                    alert.close();
                }}
            catch(NoSuchElementException z){
                return;
            }
            return;
        }
        else{
        dao.updateType(textField2.getText(), table.getSelectionModel().getSelectedItem().getId());
        model.getScWork().clear();
        model.reload();
        initialize();
        textField1.clear();
        }
    }
}

