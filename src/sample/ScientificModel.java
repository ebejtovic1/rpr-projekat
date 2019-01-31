package sample;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import java.time.LocalDate;

public class ScientificModel {

    
        private ObservableList<ScientificWork> scWork = FXCollections.observableArrayList();
        private ObjectProperty<ScientificWork> nowWork = new SimpleObjectProperty<>();

        public ScientificModel() {
            ScientificWorksDAO dao=ScientificWorksDAO.getInstance();
            scWork=dao.getAll();

        }

        public ObjectProperty<ScientificWork> nowWorkProperty() {
            return nowWork;
        }

        public ScientificWork getNewWork() {
            return nowWork.get();
        }

        public void setNewWork(ScientificWork k) {
            nowWork.set(k);
        }

    public ObservableList<ScientificWork> getScWork() {
        return scWork;
    }

    public void setScWork(ObservableList<ScientificWork> scWork) {
        this.scWork = scWork;
    }

   /* public void ispisiKnjige() {
            System.out.println("Knjige su:");
            for (ScientificWork k : knjige)
                System.out.println(k);
        }

        public String dajKnjige() {
            String ret="";
            for(ScientificWork k: scWork){
                ret+=k.ispis()+"\n";
            }
            return ret;
        }
*/

        public void deleteScientificWork() {
            scWork.remove(nowWork.get());
            nowWork.set(null);
        }

        public void addScientificWork(ScientificWork ScientificWork) {
            scWork.add(ScientificWork);
        }



}
