package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.lang.reflect.Field;

public class FieldOfStudyModel {

    public ObservableList<FieldOfStudy> getScWork() {
        return scWork;
    }

    public void setScWork(ObservableList<FieldOfStudy> scWork) {
        this.scWork = scWork;
    }

    private ObservableList<FieldOfStudy> scWork = FXCollections.observableArrayList();

    public void reload(){
        ScientificWorksDAO dao=ScientificWorksDAO.getInstance();
        scWork=dao.getAllField();
    }

    public FieldOfStudyModel() {
        ScientificWorksDAO dao=ScientificWorksDAO.getInstance();
        scWork=dao.getAllField();
    }
}
