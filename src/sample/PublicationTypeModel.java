package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PublicationTypeModel {

    public ObservableList<PublicationType> getScWork() {
        return scWork;
    }

    public void setScWork(ObservableList<PublicationType> scWork) {
        this.scWork = scWork;
    }

    private ObservableList<PublicationType> scWork = FXCollections.observableArrayList();

    public void reload(){
        ScientificWorksDAO dao=ScientificWorksDAO.getInstance();
        scWork=dao.getAllType();
    }

    public PublicationTypeModel() {
        ScientificWorksDAO dao=ScientificWorksDAO.getInstance();
        scWork=dao.getAllType();
    }
}
