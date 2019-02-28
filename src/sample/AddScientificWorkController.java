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
    public TextField title1;
    public TextField author1;
    public ComboBox<String> fieldStudy1;
    public TextField journal1;
    public ComboBox<String> publType1;
    public Spinner<Integer> year1;
    public Spinner<Integer> citations1;
    public TextField aff1;
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
        support[0].registerValidator(title1, false, Validator.createPredicateValidator(o1 -> validationTitle(title1.getText()), "This field must not be empty"));
        support[1].registerValidator(author1, false, Validator.createPredicateValidator(o1 -> validationAuthor(author1.getText()), "This field can contain only letters and comma and must not be empty"));
        support[2].registerValidator(journal1, false, Validator.createPredicateValidator(o1 -> validationJournal(journal1.getText()), "This field can contain only letters or be empty"));
        support[3].registerValidator(aff1, false, Validator.createPredicateValidator(o1 -> validationJournal(aff1.getText()), "This field can contain only letters or be empty"));
        support[4].registerValidator(fieldStudy1.getEditor(), false, Validator.createPredicateValidator(o1 -> validationJournal1(fieldStudy1.getEditor().getText()), "This field can contain only letters and must not be empty"));
        support[5].registerValidator(publType1.getEditor(), false, Validator.createPredicateValidator(o1 -> validationJournal1(publType1.getEditor().getText()), "This field can contain only letters and  must not be empty"));
        check(title1, this::validationTitle, support[0]);
        check(author1, this::validationAuthor, support[1]);
        check(journal1,this::validationJournal,support[2]);
        check(aff1, this::validationJournal, support[3]);
        check(fieldStudy1.getEditor(),this::validationJournal1, support[4]);
        check(publType1.getEditor(),this::validationJournal1,support[5]);


        checkAfterFocus(title1, support[0]);
        checkAfterFocus(author1, support[1]);
        checkAfterFocus(journal1,support[2]);
        checkAfterFocus(aff1, support[3]);
        checkAfterFocus(fieldStudy1.getEditor(),support[4]);
        checkAfterFocus(publType1.getEditor(),support[5]);
        fieldStudy1.getItems().addAll(
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
        fieldStudy1.getSelectionModel().selectFirst();
        publType1.getItems().addAll(
                "Journal publications",
                "Conference publication",
                "Articles",
                "Books",
                "Patents",
                "Book chapters",
                "Other"
        );
        publType1.getSelectionModel().selectFirst();

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
        return (s.matches("^[a-zA-Z_ \\,\\.]+$") && s.length()>5);
    }
    boolean validationJournal(String s){
        if(s==null)return true;
        return (s.matches("^[a-zA-Z_ \\&]+$") || s.length()==0);
    }

    boolean validationJournal1(String s){
        return (s.matches("^[a-zA-Z_ \\&]+$") && s.length()>0);
    }
    public void add(ActionEvent actionEvent){
        if(validationTitle(title1.getText()) && validationAuthor(author1.getText()) && validationJournal(journal1.getText()) && validationJournal(aff1.getText()) && validationJournal1(fieldStudy1.getEditor().getText()) && validationJournal1(publType1.getEditor().getText())) {

            if (id != -1) {
                ScientificWorksDAO dao = ScientificWorksDAO.getInstance();
                int field = dao.getIdField(fieldStudy1.getValue());
                if (field == -1) {
                    dao.addFieldS(fieldStudy1.getValue());
                    field = dao.getIdField(fieldStudy1.getValue());
                }
                int p = dao.getIdType(publType1.getValue());
                if (p == -1) {
                    dao.addTypeP(publType1.getValue());
                    p = dao.getIdType(publType1.getValue());
                }
                int value = (Integer) year1.getValue();
                int value1 = (Integer) citations1.getValue();
                dao.updateScien(title1.getText(), author1.getText(), field, journal1.getText(), p, value, value1, aff1.getText(), id);
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
                int field = dao.getIdField(fieldStudy1.getValue());
                if (field == -1) {
                    dao.addFieldS(fieldStudy1.getValue());
                    field = dao.getIdField(fieldStudy1.getValue());
                }
                int p = dao.getIdType(publType1.getValue());
                if (p == -1) {
                    dao.addTypeP(publType1.getValue());
                    p = dao.getIdType(publType1.getValue());
                }
                int value = (Integer) year1.getValue();
                int value1 = (Integer) citations1.getValue();
                dao.addScien(title1.getText(), author1.getText(), field, journal1.getText(), p, value, value1, aff1.getText());
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
                    if (!validationJournal1(fieldStudy1.getEditor().getText())){
                        if (fieldStudy1.getValue().equals("")) {
                            fieldStudy1.setValue(".");
                            fieldStudy1.getEditor().clear();
                        }
                }
                    if (!validationJournal1(publType1.getEditor().getText())){
                    if(publType1.getValue().equals("")){
                        publType1.setValue(".");
                        publType1.getEditor().clear();}}

                    if (!validationTitle(title1.getText())){
                        title1.setText(".");
                        title1.clear();

                    }
                    if (!validationAuthor(author1.getText())){
                        author1.setText(".");
                        author1.clear();
                    }

                    alert.close();
                }}
            catch(NoSuchElementException z){
                return;
            }
            return;
        }
    }


    public TextField getTitle() {
        return title1;
    }
    public TextField getAuthor() {
        return author1;
    }
    public ComboBox<String> getFieldStudy() {
        return fieldStudy1;
    }
    public TextField getJournal() {
        return journal1;
    }
    public ComboBox<String> getPublType() {
        return publType1;
    }
    public Spinner<Integer> getYear() {
        return year1;
    }
    public Spinner<Integer> getCitations() {
        return citations1;
    }
    public TextField getAff() {
        return aff1;
    }
}
