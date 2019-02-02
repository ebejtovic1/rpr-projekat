package sample;

import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableSet;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.print.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Callback;

import java.io.IOException;
import java.util.Optional;

public class Controller {
    public static ScientificModel model=new ScientificModel();
    public ScientificWorksDAO dao=ScientificWorksDAO.getInstance();
    public TableView<ScientificWork> table;
    public TableColumn<ScientificWork, SimpleIntegerProperty> id;
    public TableColumn<ScientificWork, SimpleStringProperty>title;
    public TableColumn<ScientificWork, SimpleStringProperty>author;
    public TableColumn<ScientificWork, String>field;
    public TableColumn<ScientificWork, SimpleStringProperty>journal;
    public TableColumn<ScientificWork, String>type;
    public TableColumn<ScientificWork, SimpleIntegerProperty> year;
    public TableColumn<ScientificWork, SimpleIntegerProperty> citations;
    public TableColumn<ScientificWork, SimpleStringProperty> affiliation;
    public ChoiceBox<String> choiceBox;
    public  TextField textField;
    public  TextField textField1;
    public  TextField textField2;
    public BorderPane hbox;

    @FXML
    private void initialize() {
        id.setCellValueFactory(new PropertyValueFactory<ScientificWork, SimpleIntegerProperty>("id"));
        title.setCellValueFactory(new PropertyValueFactory<ScientificWork, SimpleStringProperty>("Title"));
        author.setCellValueFactory(new PropertyValueFactory<ScientificWork, SimpleStringProperty>("Author"));

        field.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ScientificWork, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ScientificWork, String> p) {
                return new SimpleStringProperty(p.getValue().getFieldOfStudy().getTitle());
            }
        });

        type.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ScientificWork, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ScientificWork, String> p) {
                return new SimpleStringProperty(p.getValue().getPublicationType().getType());
            }
        });

        journal.setCellValueFactory(new PropertyValueFactory<ScientificWork, SimpleStringProperty>("Journal"));
        year.setCellValueFactory(new PropertyValueFactory<ScientificWork, SimpleIntegerProperty>("YearOfIssue"));
        citations.setCellValueFactory(new PropertyValueFactory<ScientificWork, SimpleIntegerProperty>("Citations"));
        affiliation.setCellValueFactory(new PropertyValueFactory<ScientificWork, SimpleStringProperty>("Affiliation"));
        FilteredList<ScientificWork> flPerson = new FilteredList(model.getScWork(), p -> true);


        title.setSortable(true);
        author.setSortable(true);

        choiceBox.getItems().addAll("All","By title", "By author", "By field of study", "By publication type", "By year of issue", "By ID", "By affiliation", "By citations", "By journal");
        choiceBox.setValue("All");
        choiceBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) ->
        {
            if (newVal != null)
            {
                textField.setText("");
                flPerson.setPredicate(null);
            }
        });

        textField.setPromptText("Search here...");
        textField.setOnKeyReleased(keyEvent ->
        {
            switch (choiceBox.getValue())
            {
                case "By title":
                    flPerson.setPredicate(p -> p.getTitle().toLowerCase().contains(textField.getText().toLowerCase().trim()));
                    break;

                case "By author":
                    flPerson.setPredicate(p -> p.getAuthor().toLowerCase().contains(textField.getText().toLowerCase().trim()));
                    break;

                case "By field of study":
                    flPerson.setPredicate(p -> p.getFieldOfStudy().getTitle().toLowerCase().contains(textField.getText().toLowerCase().trim()));
                    break;

                case "By publication type":
                    flPerson.setPredicate(p -> p.getPublicationType().getType().toLowerCase().contains(textField.getText().toLowerCase().trim()));
                    break;

                case "By year of issue":
                    flPerson.setPredicate(p -> String.valueOf(p.getYearOfIssue()).toLowerCase().contains(textField.getText().toLowerCase().trim()));
                    break;

                case "By ID":
                    flPerson.setPredicate(p -> String.valueOf(p.getId()).toLowerCase().contains(textField.getText().toLowerCase().trim()));
                    break;

                case "By affiliation":
                    flPerson.setPredicate(p -> p.getAffiliation()!=null && p.getAffiliation().toLowerCase().contains(textField.getText().toLowerCase().trim()));
                    break;

                case "By citations":
                    flPerson.setPredicate(p -> String.valueOf(p.getCitations()).toLowerCase().contains(textField.getText().toLowerCase().trim()));
                    break;

                case "By journal":
                    flPerson.setPredicate(p -> p.getJournal()!=null && p.getJournal().toLowerCase().contains(textField.getText().toLowerCase().trim()));
                    break;

                case "All":
                    flPerson.setPredicate(p -> {
                        if(String.valueOf(p.getYearOfIssue()).toLowerCase().contains(textField.getText().toLowerCase().trim()))return true;
                        if(String.valueOf(p.getId()).toLowerCase().contains(textField.getText().toLowerCase().trim()))return true;
                        if(String.valueOf(p.getCitations()).toLowerCase().contains(textField.getText().toLowerCase().trim()))return true;
                        else if(p.getTitle().toLowerCase().contains(textField.getText().toLowerCase().trim()))return true;
                        else if( p.getAuthor().toLowerCase().contains(textField.getText().toLowerCase().trim()))return true;
                        else if(p.getFieldOfStudy().getTitle().toLowerCase().contains(textField.getText().toLowerCase().trim()))return true;
                        else if(p.getPublicationType().getType().toLowerCase().contains(textField.getText().toLowerCase().trim()))return true;
                        else if(p.getAffiliation()!=null && p.getAffiliation().toLowerCase().contains(textField.getText().toLowerCase().trim()))return true;
                        else if(p.getJournal()!=null && p.getJournal().toLowerCase().contains(textField.getText().toLowerCase().trim()))return true;

                    return false;
                    });
                    break;
            }
        });


        SortedList<ScientificWork> sortedData = new SortedList<>(flPerson);
        sortedData.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedData);
    }

    public void print(ActionEvent actionEvent) {

        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null && job.showPrintDialog(table.getScene().getWindow())) {
            boolean success = job.printPage(table);
            if (success) {
                job.endJob();
            }
        }
        model.Ispisi();
    }

    public void exit(ActionEvent actionEvent){
        System.exit(0);
    }
    public void addS(ActionEvent actionEvent){
        FXMLLoader loader1 = new FXMLLoader(getClass().getResource("AddScientificWork.fxml"));
        Stage stage = new Stage(StageStyle.DECORATED);
        try { stage.setScene(new Scene((Pane) loader1.load())
        );
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.show();
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                model.getScWork().clear();
                model.reload();
                initialize();
            }
        });
    }

    public void addF(ActionEvent actionEvent){
        ScientificWorksDAO dao=ScientificWorksDAO.getInstance();
        dao.addFieldS(textField2.getText());
        textField2.clear();
    }

    public void addP(ActionEvent actionEvent){
        ScientificWorksDAO dao=ScientificWorksDAO.getInstance();
        dao.addTypeP(textField1.getText());
        textField1.clear();
    }

    public void delete (ActionEvent actionEvent)throws NoSelectedExeption {
        try {
            int id = table.getSelectionModel().getSelectedItem().getId();
            dao.delete(id);
            model.getScWork().clear();
            model.reload();
            initialize();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No selected item");
            Optional<ButtonType> action= alert.showAndWait();
            if(action.get()== ButtonType.OK){
                alert.close();
            }
            return;
        }
    }

    public void update (ActionEvent actionEvent)throws NoSelectedExeption {
        FXMLLoader loader1 = new FXMLLoader(getClass().getResource("AddScientificWork.fxml"));
        Stage stage = new Stage(StageStyle.DECORATED);
        try { stage.setScene(new Scene((Pane) loader1.load())
        );
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(table.getSelectionModel().getSelectedItem()==null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No selected item");
            Optional<ButtonType> action= alert.showAndWait();
            if(action.get()== ButtonType.OK){
                alert.close();
            }
            return;
        }
        stage.show();
        AddScientificWorkController ctrl = loader1.getController();

            ctrl.setId(table.getSelectionModel().getSelectedItem().getId());

            ctrl.getTitle().textProperty().bindBidirectional(new SimpleStringProperty(table.getSelectionModel().getSelectedItem().getTitle()));
            ctrl.getAuthor().textProperty().bindBidirectional(new SimpleStringProperty(table.getSelectionModel().getSelectedItem().getAuthor()));
            ctrl.getAff().textProperty().bindBidirectional(new SimpleStringProperty(table.getSelectionModel().getSelectedItem().getAffiliation()));
            ctrl.getJournal().textProperty().bindBidirectional(new SimpleStringProperty(table.getSelectionModel().getSelectedItem().getJournal()));

            ObjectProperty<Integer> objectProp = new SimpleObjectProperty<>(table.getSelectionModel().getSelectedItem().getYearOfIssue());
            IntegerProperty integerProperty = IntegerProperty.integerProperty(objectProp);
            ctrl.getYear().getValueFactory().valueProperty().bindBidirectional(objectProp);


            ObjectProperty<Integer> objectProp1 = new SimpleObjectProperty<>(table.getSelectionModel().getSelectedItem().getCitations());
            IntegerProperty integerProperty1 = IntegerProperty.integerProperty(objectProp1);
            ctrl.getCitations().getValueFactory().valueProperty().bindBidirectional(objectProp1);

            ctrl.getFieldStudy().valueProperty().bindBidirectional(new SimpleStringProperty(table.getSelectionModel().getSelectedItem().getFieldOfStudy().getTitle()));
            ctrl.getPublType().valueProperty().bindBidirectional(new SimpleStringProperty(table.getSelectionModel().getSelectedItem().getPublicationType().getType()));

            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                public void handle(WindowEvent we) {
                    model.getScWork().clear();
                    model.reload();
                    initialize();
                }
            });
        }
}
