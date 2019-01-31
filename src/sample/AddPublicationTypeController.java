package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class AddPublicationTypeController {

        public Button addBtn;
        public TextField textField;

        public void add(ActionEvent actionEvent){
            ScientificWorksDAO dao=ScientificWorksDAO.getInstance();
            dao.addTypeP(textField.getText());
        }

}
