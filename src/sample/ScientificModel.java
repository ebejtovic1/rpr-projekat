package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ScientificModel {

    
        private ObservableList<ScientificWork> scWork = FXCollections.observableArrayList();


        public ScientificModel() {
            ScientificWorksDAO dao=ScientificWorksDAO.getInstance();
            scWork=dao.getAll();

        }


    public ObservableList<ScientificWork> getScWork() {
        return scWork;
    }

    public void setScWork(ObservableList<ScientificWork> scWork) {
        this.scWork = scWork;
    }

    public void reload(){
        ScientificWorksDAO dao=ScientificWorksDAO.getInstance();
        scWork=dao.getAll();
    }

        public void Ispisi(){
            int i=1;
            for(ScientificWork heh: scWork){
                System.out.println(i+". TITLE: "+ heh.getTitle() + ", AUTHOR: " + heh.getAuthor() + ", FIELD OF STUDY: "+ heh.getFieldOfStudy().getTitle() + ", JOURNAL: " +heh.getJournal()+ ", PUBLICATION TYPE: " + heh.getPublicationType().getType()+ ", YEAR: "+ String.valueOf(heh.getYearOfIssue())+ ", CITATIONS: " + String.valueOf(heh.getCitations())+ ", AFFILIATION: " + heh.getAffiliation());
i++;
            }
        }

        public void addScientificWork(ScientificWork ScientificWork) {
            scWork.add(ScientificWork);
        }

}
