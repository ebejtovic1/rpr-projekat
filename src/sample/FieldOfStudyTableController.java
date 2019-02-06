package sample;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Function;

public class FieldOfStudyTableController {

    public ScientificWorksDAO dao=ScientificWorksDAO.getInstance();
    public static FieldOfStudyModel model=new FieldOfStudyModel();
    public TableView<FieldOfStudy> table;
    public TableColumn<FieldOfStudy, SimpleIntegerProperty> id;
    public TableColumn<FieldOfStudy, SimpleStringProperty>type;
    public TextField textField;
    public TextField textField1;
    public TextField textField2;
    private ValidationSupport[]support;


    public FieldOfStudyTableController() {
        support = new ValidationSupport[2];
        for (int i = 0; i < 2; i++)
            support[i] = new ValidationSupport();}

    @FXML
    private void initialize() {
        support[0].registerValidator(textField1, false, Validator.createPredicateValidator(o1 -> validation(textField1.getText()), "This field can contain only letters and must not be empty"));
        support[1].registerValidator(textField2, false, Validator.createPredicateValidator(o1 -> validation(textField2.getText()), "This field can contain only letters and must not be empty"));
        check(textField1, this::validation, support[0]);
        check(textField2, this::validation, support[1]);

        checkAfterFocus(textField1, support[0]);
        checkAfterFocus(textField2, support[1]);

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

        table.setRowFactory(tv -> {
            TableRow<FieldOfStudy> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && (! row.isEmpty()) ) {
                    textField2.textProperty().bindBidirectional(new SimpleStringProperty(table.getSelectionModel().getSelectedItem().getTitle()));
                }
            });
            return row ;
        });

        textField1.setOnMouseClicked(event -> {
            textField2.clear();
            table.getSelectionModel().clearSelection();
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
                textField2.clear();
                textField2.getStyleClass().removeAll("poljeNijeIspravno", "poljeNeutralno");
                textField2.getStyleClass().add("poljeIspravno");
                support[1].setErrorDecorationEnabled(false);
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
    private void checkAfterFocus(TextField tekstualnoPolje, ValidationSupport support) {
        tekstualnoPolje.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue){
                support.setErrorDecorationEnabled(false);
                tekstualnoPolje.getStyleClass().removeAll("poljeNijeIspravno", "poljeNeutralno");
                tekstualnoPolje.getStyleClass().add("poljeIspravno");
                support.setErrorDecorationEnabled(false);}
            else{
                support.setErrorDecorationEnabled(true);
                tekstualnoPolje.getStyleClass().removeAll("poljeNijeIspravno", "poljeNeutralno");
                tekstualnoPolje.getStyleClass().add("poljeIspravno");
                support.setErrorDecorationEnabled(false);}
        });
    }

        boolean validation(String s){
            return (s.matches("^[a-zA-Z_ \\&]+$") && s.length()>0);
        }

        private void check(TextField tekstualnoPolje, Function<String, Boolean> validacija, ValidationSupport support) {
            tekstualnoPolje.textProperty().addListener((observableValue, oldValue, newValue) -> {
                if (validacija.apply(newValue)) {
                    tekstualnoPolje.getStyleClass().removeAll("poljeNijeIspravno", "poljeNeutralno");
                    tekstualnoPolje.getStyleClass().add("poljeIspravno");
                } else {
                    tekstualnoPolje.getStyleClass().removeAll("poljeIspravno", "poljeNeutralno");
                    tekstualnoPolje.getStyleClass().add("poljeNijeIspravno");
                    support.setErrorDecorationEnabled(true);
                }
            });
        }
    public void add (ActionEvent actionEvent){
        if(validation(textField1.getText())){
        dao.addFieldS(textField1.getText());
        model.getScWork().clear();
        model.reload();
        initialize();
        textField1.clear();
            textField1.getStyleClass().removeAll("poljeNijeIspravno", "poljeNeutralno");
            textField1.getStyleClass().add("poljeIspravno");
            support[0].setErrorDecorationEnabled(false);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText("Field of study successfully added");
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
            textField1.getStyleClass().removeAll("poljeIspravno", "poljeNeutralno");
            textField1.getStyleClass().add("poljeNijeIspravno");
            support[0].setErrorDecorationEnabled(true);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Incorrect data in field!");

            Optional<ButtonType> action= alert.showAndWait();

            try{
                if(action.get().equals(ButtonType.OK)) {

                    textField1.getStyleClass().removeAll("poljeIspravno", "poljeNeutralno");
                    textField1.getStyleClass().add("poljeNijeIspravno");
                    support[0].setErrorDecorationEnabled(true);
                    if(textField1.getText().equals(""))
                    {
                        textField1.setText(".");
                        textField1.clear();
                    }
                    alert.close();
                }}
            catch(NoSuchElementException z){
                return;
            }
            return;

        }
    }
    public void update(ActionEvent actionEvent){

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
            if(validation(textField2.getText())){


        dao.updateField(textField2.getText(), table.getSelectionModel().getSelectedItem().getId());
        model.getScWork().clear();
        model.reload();
        initialize();
        textField1.clear();

        textField2.clear();
                textField2.getStyleClass().removeAll("poljeNijeIspravno", "poljeNeutralno");
                textField2.getStyleClass().add("poljeIspravno");
                support[1].setErrorDecorationEnabled(false);

        }
        else{
                textField2.getStyleClass().removeAll("poljeIspravno", "poljeNeutralno");
                textField2.getStyleClass().add("poljeNijeIspravno");
                support[1].setErrorDecorationEnabled(true);
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Incorrect data in field!");

                Optional<ButtonType> action= alert.showAndWait();

                try{
                    if(action.get().equals(ButtonType.OK)) {

                        textField2.getStyleClass().removeAll("poljeIspravno", "poljeNeutralno");
                        textField2.getStyleClass().add("poljeNijeIspravno");
                        support[1].setErrorDecorationEnabled(true);
                        if(textField2.getText().equals(""))
                        {
                            textField2.setText(".");
                            textField2.clear();
                        }
                        alert.close();
                    }}
                catch(NoSuchElementException z){
                    return;
                }
                return;


            }
        }
    }
}
