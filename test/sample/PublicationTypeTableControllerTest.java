package sample;

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
        robot.lookup("#textField1").tryQuery().isPresent();
        robot.clickOn("#textField1");
        robot.write("Testtesttest");
        robot.clickOn("#addTT");
        robot.press(KeyCode.ALT).press(KeyCode.F4).release(KeyCode.F4).release(KeyCode.ALT);
        robot.clickOn("Testtesttest");
        robot.clickOn("#delete");
        robot.lookup(".dialog-pane").tryQuery().isPresent();
        DialogPane dialogPane = robot.lookup(".dialog-pane").queryAs(DialogPane.class);
        Button okButton1 = (Button) dialogPane.lookupButton(ButtonType.OK);
        robot.clickOn(okButton1);
    }

    @Test
    public void testAdd1(FxRobot robot) {
        robot.lookup("#textField1").tryQuery().isPresent();
        robot.clickOn("#textField1");
        robot.write("...");
        robot.clickOn("#addTT");
        DialogPane dialogPane = robot.lookup(".dialog-pane").queryAs(DialogPane.class);
        Button okButton1 = (Button) dialogPane.lookupButton(ButtonType.OK);
        robot.clickOn(okButton1);
    }

    @Test
    public void testAdd2(FxRobot robot) {
        robot.lookup("#textField1").tryQuery().isPresent();
        robot.clickOn("#textField1");
        robot.clickOn("#addTT");
        DialogPane dialogPane = robot.lookup(".dialog-pane").queryAs(DialogPane.class);
        Button okButton1 = (Button) dialogPane.lookupButton(ButtonType.OK);
        robot.clickOn(okButton1);
    }

    @Test
    public void testUpdate2(FxRobot robot) {
        robot.lookup("#textField1").tryQuery().isPresent();
        robot.clickOn("#textField1");
        robot.write("Testtesttest");
        robot.clickOn("#addTT");
        robot.press(KeyCode.ALT).press(KeyCode.F4).release(KeyCode.F4).release(KeyCode.ALT);
        robot.clickOn("Testtesttest");
        robot.clickOn("#textField2");
        robot.write("heh");
        robot.clickOn("#update");
        robot.clickOn("#textField");
        robot.write("Testtest");
        robot.clickOn("Testtesttestheh");
        robot.clickOn("#delete");
        robot.lookup(".dialog-pane").tryQuery().isPresent();
        DialogPane dialogPane = robot.lookup(".dialog-pane").queryAs(DialogPane.class);
        Button okButton1 = (Button) dialogPane.lookupButton(ButtonType.OK);
        robot.clickOn(okButton1);
    }

    @Test
    public void testUpdate1(FxRobot robot) {
        robot.lookup("#textField1").tryQuery().isPresent();
        robot.clickOn("#textField1");
        robot.write("Testtesttest");
        robot.clickOn("#addTT");
        robot.press(KeyCode.ALT).press(KeyCode.F4).release(KeyCode.F4).release(KeyCode.ALT);
        robot.clickOn("Testtesttest");
        robot.clickOn("#textField2");
        robot.press(KeyCode.CONTROL).press(KeyCode.A).release(KeyCode.A).release(KeyCode.CONTROL);
        robot.press(KeyCode.DELETE).release(KeyCode.DELETE);
        robot.clickOn("#update");
        DialogPane dialogPane = robot.lookup(".dialog-pane").queryAs(DialogPane.class);
        Button okButton = (Button) dialogPane.lookupButton(ButtonType.OK);
        robot.clickOn(okButton);
        ScientificWorksDAO dao=ScientificWorksDAO.getInstance();
        dao.deletePT(dao.getIdType("Testtesttest"));
    }

    @Test
    public void testUpdate(FxRobot robot) {
        robot.clickOn("#update");
        DialogPane dialogPane = robot.lookup(".dialog-pane").queryAs(DialogPane.class);
        assertEquals("No selected item",dialogPane.getContentText());
        Button okButton = (Button) dialogPane.lookupButton(ButtonType.OK);
        robot.clickOn(okButton);
    }

    @Test
    public void delete(FxRobot robot) {
        robot.clickOn("#delete");
        DialogPane dialogPane = robot.lookup(".dialog-pane").queryAs(DialogPane.class);
        assertEquals("No selected item",dialogPane.getContentText());
        Button okButton = (Button) dialogPane.lookupButton(ButtonType.OK);
        robot.clickOn(okButton);
    }
}