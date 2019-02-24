package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.Serializable;


public class ScientificModel implements Serializable {
    private ObservableList<ScientificWork> scWork= FXCollections.observableArrayList();
    public void reload(){
        ScientificWorksDAO dao=ScientificWorksDAO.getInstance();
        scWork=dao.getAll();
    }
    public ScientificModel(){
        ScientificWorksDAO dao=ScientificWorksDAO.getInstance();
        scWork=dao.getAll();
    }
    public ObservableList<ScientificWork> getScWork() {
        return scWork;
    }
    public void setScWork(ObservableList<ScientificWork> scWork) {
        this.scWork = scWork;
    }
}