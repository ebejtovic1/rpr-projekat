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

public class FieldOfStudyTableController {

    public ScientificWorksDAO dao=ScientificWorksDAO.getInstance();
    public static FieldOfStudyModel model=new FieldOfStudyModel();
    public TableView<FieldOfStudy> table;
    public TableColumn<FieldOfStudy, SimpleIntegerProperty> id;
    public TableColumn<FieldOfStudy, SimpleStringProperty>type;
    public TextField textField;
    public TextField textField1;


    @FXML
    private void initialize() {
        id.setCellValueFactory(new PropertyValueFactory<FieldOfStudy, SimpleIntegerProperty>("id"));
        type.setCellValueFactory(new PropertyValueFactory<FieldOfStudy, SimpleStringProperty>("Title"));

        FilteredList<FieldOfStudy> flPerson = new FilteredList(model.getScWork(), p -> true);
        textField.setPromptText("Search here...");
        textField.setOnKeyReleased(keyEvent ->
        {
            flPerson.setPredicate(p -> {
                if(p.getTitle().toLowerCase().contains(textField.getText().toLowerCase().trim()))return true;
                if(String.valueOf(p.getId()).toLowerCase().contains(textField.getText().toLowerCase().trim()))return true;
                return false;
            });
        });
        SortedList<FieldOfStudy> sortedData = new SortedList<>(flPerson);
        sortedData.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedData);
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
                dao.setNull(id);
                dao.deleteFS(id);
                model.getScWork().clear();
                model.reload();
                initialize();
            }

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No selected item");
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
        dao.addFieldS(textField1.getText());
        model.getScWork().clear();
        model.reload();
        initialize();
        textField1.clear();
    }
}
