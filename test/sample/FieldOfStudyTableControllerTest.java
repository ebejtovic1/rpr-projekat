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

@ExtendWith(ApplicationExtension.class)
class FieldOfStudyTableControllerTest {
    Stage theStage;
    FieldOfStudyTableController ctrl;

    @Start
    public void start(Stage stage) throws Exception {
        ScientificWorksDAO dao = ScientificWorksDAO.getInstance();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FieldOfStudyTable.fxml"));
        Parent root = loader.load();
        ctrl=loader.getController();
        stage.setTitle("FieldOfStudy");
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
        robot.write("Geography");
        robot.clickOn("#addTT");
        robot.press(KeyCode.ALT).press(KeyCode.F4).release(KeyCode.F4).release(KeyCode.ALT);
        robot.clickOn("Medicine");
        robot.press(KeyCode.DOWN).release(KeyCode.DOWN);
        robot.press(KeyCode.DOWN).release(KeyCode.DOWN);
        robot.press(KeyCode.DOWN).release(KeyCode.DOWN);
        robot.press(KeyCode.DOWN).release(KeyCode.DOWN);
        robot.press(KeyCode.DOWN).release(KeyCode.DOWN);
        robot.press(KeyCode.DOWN).release(KeyCode.DOWN);
        robot.press(KeyCode.DOWN).release(KeyCode.DOWN);
        robot.press(KeyCode.DOWN).release(KeyCode.DOWN);
        robot.press(KeyCode.DOWN).release(KeyCode.DOWN);
        robot.press(KeyCode.DOWN).release(KeyCode.DOWN);
        robot.press(KeyCode.DOWN).release(KeyCode.DOWN);
        robot.press(KeyCode.DOWN).release(KeyCode.DOWN);
        robot.press(KeyCode.DOWN).release(KeyCode.DOWN);
        robot.press(KeyCode.DOWN).release(KeyCode.DOWN);
        robot.press(KeyCode.DOWN).release(KeyCode.DOWN);
        robot.press(KeyCode.DOWN).release(KeyCode.DOWN);
        robot.press(KeyCode.DOWN).release(KeyCode.DOWN);
        robot.press(KeyCode.DOWN).release(KeyCode.DOWN);
        robot.press(KeyCode.DOWN).release(KeyCode.DOWN);
        robot.press(KeyCode.DOWN).release(KeyCode.DOWN);
        robot.press(KeyCode.DOWN).release(KeyCode.DOWN);
        robot.press(KeyCode.DOWN).release(KeyCode.DOWN);
        robot.clickOn("Geography");
        robot.clickOn("#deleteFF");
        robot.lookup(".dialog-pane").tryQuery().isPresent();
        DialogPane dialogPane = robot.lookup(".dialog-pane").queryAs(DialogPane.class);
        Button okButton1 = (Button) dialogPane.lookupButton(ButtonType.OK);
        robot.clickOn(okButton1);
        ScientificWorksDAO dao = ScientificWorksDAO.getInstance();
        dao.deleteFS(dao.getIdField("Geography"));
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
    public void update(FxRobot robot){
        robot.clickOn("Medicine");
        robot.clickOn("#textField2");
        robot.press(KeyCode.CONTROL).press(KeyCode.A).release(KeyCode.A).release(KeyCode.CONTROL);
        robot.press(KeyCode.DELETE).release(KeyCode.DELETE);
        robot.clickOn("#update");
        robot.press(KeyCode.ALT).press(KeyCode.F4).release(KeyCode.F4).release(KeyCode.ALT);
    }

    @Test
    public void update1(FxRobot robot){
        robot.lookup("#textField1").tryQuery().isPresent();
        robot.clickOn("#textField1");
        robot.clickOn("#update");
        DialogPane dialogPane = robot.lookup(".dialog-pane").queryAs(DialogPane.class);
        Button okButton1 = (Button) dialogPane.lookupButton(ButtonType.OK);
        robot.clickOn(okButton1);
    }

    @Test
    public void update2(FxRobot robot){
        robot.clickOn("Medicine");
        robot.clickOn("#textField2");
        robot.press(KeyCode.CONTROL).press(KeyCode.A).release(KeyCode.A).release(KeyCode.CONTROL);
        robot.press(KeyCode.DELETE).release(KeyCode.DELETE);
        robot.write("Medicine");
        robot.clickOn("#update");
        robot.press(KeyCode.ALT).press(KeyCode.F4).release(KeyCode.F4).release(KeyCode.ALT);
    }

    @Test
    public void delete(FxRobot robot){
        robot.lookup("#textField1").tryQuery().isPresent();
        robot.clickOn("#textField1");
        robot.clickOn("#deleteFF");
        DialogPane dialogPane = robot.lookup(".dialog-pane").queryAs(DialogPane.class);
        Button okButton1 = (Button) dialogPane.lookupButton(ButtonType.OK);
        robot.clickOn(okButton1);
    }
}