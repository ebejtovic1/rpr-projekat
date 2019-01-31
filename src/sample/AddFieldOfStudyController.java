package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class AddFieldOfStudyController {
    public Button addBtn;
    public TextField textField;

    public void add(ActionEvent actionEvent){
        ScientificWorksDAO dao=ScientificWorksDAO.getInstance();
        dao.addFieldS(textField.getText());
    }

}
