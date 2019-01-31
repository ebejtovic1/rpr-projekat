package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;


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
        ScientificWorksDAO dao=ScientificWorksDAO.getInstance();
        int field=dao.getIdField(fieldStudy.getValue());
        int p=dao.getIdType(publType.getValue());
        int value = (Integer) year.getValue();
        int value1 = (Integer) citations.getValue();
        dao.addScien(title.getText(),author.getText(),field,journal.getText(),p,value,value1,aff.getText());
    }

}
