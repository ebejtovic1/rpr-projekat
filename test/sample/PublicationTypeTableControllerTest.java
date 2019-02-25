package sample;

import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;
import static org.junit.jupiter.api.Assertions.*;
import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
class PublicationTypeTableControllerTest {
    Stage theStage;
    PublicationTypeTableController ctrl;

    @Start
    public void start(Stage stage) throws Exception {
        ScientificWorksDAO dao = ScientificWorksDAO.getInstance();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("PublicationTypeTable.fxml"));
        Parent root = loader.load();
        ctrl=loader.getController();
        stage.setTitle("Publication type");
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        stage.setResizable(false);
        stage.show();
        stage.toFront();
        theStage = stage;
    }

    @Test
    public void testAdd(FxRobot robot) {

        // Čekamo da dijalog postane vidljiv
        robot.lookup("#textField1").tryQuery().isPresent();

        // Postoji li fieldNaziv
        robot.clickOn("#textField1");
        robot.write("Testtesttest");

        // Klik na dugme Ok
        //robot.clickOn("#add");


    }

}