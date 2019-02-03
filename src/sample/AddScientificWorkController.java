package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.NoSuchElementException;
import java.util.Optional;


public class AddScientificWorkController {
    public Button addBtn;
    public TextField title;
    public TextField author;
    public ComboBox<String> fieldStudy;
    public TextField journal;
    public ComboBox<String> publType;
    public Spinner<Integer> year;
    public Spinner<Integer> citations;
    public TextField aff;
    private int id=-1;

    public void setId(int id) {
        this.id = id;
    }

    @FXML
    private void initialize() {
        fieldStudy.getItems().addAll(
                "Medicine",
                "Biology",
                "Chemistry",
                "Engineering",
                "Physics",
                "History",
                "Art",
                "Computer science",
                "Mathematics",
                "Materials science",
                "Economics",
                "Political science",
                "Business",
                "Geography",
                "Geology",
                "Sociology",
                "Enviromental science",
                "Philosophy",
                "Psychology"
        );
        publType.getItems().addAll(
                "Journal publications",
                "Conference publication",
                "Articles",
                "Books",
                "Patents",
                "Book chapters",
                "Other"
        );

    }
    public void add(ActionEvent actionEvent){
        if(id!=-1){
            ScientificWorksDAO dao=ScientificWorksDAO.getInstance();
            int field=dao.getIdField(fieldStudy.getValue());
            if(field==-1){
                dao.addFieldS(fieldStudy.getValue());
                field=dao.getIdField(fieldStudy.getValue());
            }
            int p=dao.getIdType(publType.getValue());
            if(p==-1){
                dao.addTypeP(publType.getValue());
                p=dao.getIdType(publType.getValue());
            }
            int value = (Integer) year.getValue();
            int value1 = (Integer) citations.getValue();
            dao.updateScien(title.getText(),author.getText(),field,journal.getText(),p,value,value1,aff.getText(),id);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText("Scientific work successfully updated");
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
        ScientificWorksDAO dao=ScientificWorksDAO.getInstance();
        int field=dao.getIdField(fieldStudy.getValue());
            if(field==-1){
                dao.addFieldS(fieldStudy.getValue());
                field=dao.getIdField(fieldStudy.getValue());
            }
        int p=dao.getIdType(publType.getValue());
            if(p==-1){
                dao.addTypeP(publType.getValue());
                p=dao.getIdType(publType.getValue());
            }
        int value = (Integer) year.getValue();
        int value1 = (Integer) citations.getValue();
        dao.addScien(title.getText(),author.getText(),field,journal.getText(),p,value,value1,aff.getText());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText("Scientific work successfully added");
            Optional<ButtonType> action= alert.showAndWait();
            try{
                if(action.get().equals(ButtonType.OK)){
                    alert.close();
                }}
            catch(NoSuchElementException z){
                return;
            }
            return;}

    }

    public Button getAddBtn() {
        return addBtn;
    }

    public TextField getTitle() {
        return title;
    }

    public TextField getAuthor() {
        return author;
    }

    public ComboBox<String> getFieldStudy() {
        return fieldStudy;
    }

    public TextField getJournal() {
        return journal;
    }

    public ComboBox<String> getPublType() {
        return publType;
    }

    public Spinner<Integer> getYear() {
        return year;
    }

    public Spinner<Integer> getCitations() {
        return citations;
    }

    public TextField getAff() {
        return aff;
    }
}
