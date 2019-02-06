package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Function;


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
    private ValidationSupport[]support;
    private ValidationSupport validation=new ValidationSupport();




    public AddScientificWorkController() {
        support = new ValidationSupport[6];
        for (int i = 0; i < 6; i++)
            support[i] = new ValidationSupport();
    }

    @FXML
    private void initialize() {
        support[0].registerValidator(title, false, Validator.createPredicateValidator(o1 -> validationTitle(title.getText()), "This field must not be empty"));
        support[1].registerValidator(author, false, Validator.createPredicateValidator(o1 -> validationAuthor(author.getText()), "This field can contain only letters and comma and must not be empty"));
        support[2].registerValidator(journal, false, Validator.createPredicateValidator(o1 -> validationJournal(journal.getText()), "This field can contain only letters or be empty"));
        support[3].registerValidator(aff, false, Validator.createPredicateValidator(o1 -> validationJournal(aff.getText()), "This field can contain only letters or be empty"));
        support[4].registerValidator(fieldStudy.getEditor(), false, Validator.createPredicateValidator(o1 -> validationJournal1(fieldStudy.getEditor().getText()), "This field can contain only letters and must not be empty"));
        support[5].registerValidator(publType.getEditor(), false, Validator.createPredicateValidator(o1 -> validationJournal1(publType.getEditor().getText()), "This field can contain only letters and  must not be empty"));
        check(title, this::validationTitle, support[0]);
        check(author, this::validationAuthor, support[1]);
        check(journal,this::validationJournal,support[2]);
        check(aff, this::validationJournal, support[3]);
        check(fieldStudy.getEditor(),this::validationJournal1, support[4]);
        check(publType.getEditor(),this::validationJournal1,support[5]);


        checkAfterFocus(title, support[0]);
        checkAfterFocus(author, support[1]);
        checkAfterFocus(journal,support[2]);
        checkAfterFocus(aff, support[3]);
        checkAfterFocus(fieldStudy.getEditor(),support[4]);
        checkAfterFocus(publType.getEditor(),support[5]);
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
    public void setId(int id) {
        this.id = id;
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



    private void checkAfterFocus(TextField tekstualnoPolje, ValidationSupport support) {
        tekstualnoPolje.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue)
                support.setErrorDecorationEnabled(false);
            else
                support.setErrorDecorationEnabled(true);
        });
    }


    boolean validationTitle(String s){
        return (s.length()>5);
    }
    boolean validationAuthor(String s){
        return (s.matches("^[a-zA-Z_ \\,]+$") && s.length()>5);
    }
    boolean validationJournal(String s){
        return (s.matches("^[a-zA-Z_ \\&]+$") || s.length()==0);
    }

    boolean validationJournal1(String s){
        return (s.matches("^[a-zA-Z_ \\&]+$") && s.length()>0);
    }
    public void add(ActionEvent actionEvent){
        if(validationTitle(title.getText()) && validationAuthor(author.getText()) && validationJournal(journal.getText()) && validationJournal(aff.getText()) && validationJournal1(fieldStudy.getEditor().getText()) && validationJournal1(publType.getEditor().getText())) {

            if (id != -1) {
                ScientificWorksDAO dao = ScientificWorksDAO.getInstance();
                int field = dao.getIdField(fieldStudy.getValue());
                if (field == -1) {
                    dao.addFieldS(fieldStudy.getValue());
                    field = dao.getIdField(fieldStudy.getValue());
                }
                int p = dao.getIdType(publType.getValue());
                if (p == -1) {
                    dao.addTypeP(publType.getValue());
                    p = dao.getIdType(publType.getValue());
                }
                int value = (Integer) year.getValue();
                int value1 = (Integer) citations.getValue();
                dao.updateScien(title.getText(), author.getText(), field, journal.getText(), p, value, value1, aff.getText(), id);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText(null);
                alert.setContentText("Scientific work successfully updated");
                Optional<ButtonType> action = alert.showAndWait();
                try {
                    if (action.get().equals(ButtonType.OK)) {
                        alert.close();
                    }
                } catch (NoSuchElementException z) {
                    return;
                }
                return;
            } else {
                ScientificWorksDAO dao = ScientificWorksDAO.getInstance();
                int field = dao.getIdField(fieldStudy.getValue());
                if (field == -1) {
                    dao.addFieldS(fieldStudy.getValue());
                    field = dao.getIdField(fieldStudy.getValue());
                }
                int p = dao.getIdType(publType.getValue());
                if (p == -1) {
                    dao.addTypeP(publType.getValue());
                    p = dao.getIdType(publType.getValue());
                }
                int value = (Integer) year.getValue();
                int value1 = (Integer) citations.getValue();
                dao.addScien(title.getText(), author.getText(), field, journal.getText(), p, value, value1, aff.getText());
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText(null);
                alert.setContentText("Scientific work successfully added");
                Optional<ButtonType> action = alert.showAndWait();
                try {
                    if (action.get().equals(ButtonType.OK)) {
                        alert.close();
                    }
                } catch (NoSuchElementException z) {
                    return;
                }
                return;
            }
        }
        else {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Incorrect data in fields!");
            Optional<ButtonType> action= alert.showAndWait();
            try{
                if(action.get().equals(ButtonType.OK)) {
                    if (!validationJournal1(fieldStudy.getEditor().getText())){
                        if (fieldStudy.getValue().equals("")) {
                            fieldStudy.setValue(".");
                            fieldStudy.getEditor().clear();
                        }
                }
                    if (!validationJournal1(publType.getEditor().getText())){
                    if(publType.getValue()==null){
                        publType.setValue(".");
                        publType.getEditor().clear();}}

                    if (!validationTitle(title.getText())){
                        title.setText(".");
                        title.clear();

                    }
                    if (!validationAuthor(author.getText())){
                        author.setText(".");
                        author.clear();
                    }

                    alert.close();
                }}
            catch(NoSuchElementException z){
                return;
            }
            return;
        }


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
