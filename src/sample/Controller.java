package sample;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

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

        //table.setItems(FXCollections.observableList(model.getScWork()));
        // ili table.setItems(FXCollections.observableList(dao.getAll()));

        FilteredList<ScientificWork> flPerson = new FilteredList(model.getScWork(), p -> true);//Pass the data to a filtered list
        //table.setItems(flPerson);//
        //table.getColumns().addAll(id, title, author, field,journal,type, year, citations,affiliation);

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

        textField.setPromptText("Search here!");
        textField.setOnKeyReleased(keyEvent ->
        {
            switch (choiceBox.getValue())//Switch on choiceBox value
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

    public void print(ActionEvent actionEvent){

    }
    public void exit(ActionEvent actionEvent){
        System.exit(0);
    }
    public void addS(ActionEvent actionEvent){

    }

    public void addF(ActionEvent actionEvent){

    }
    public void addP(ActionEvent actionEvent){

    }


}
