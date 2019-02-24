package sample;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.Start;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.io.File;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)

class ControllerTest {
    Stage theStage;
    Controller ctrl;


    @Start
    public void start(Stage stage) throws Exception {
        ScientificWorksDAO dao = ScientificWorksDAO.getInstance();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root = loader.load();
        ctrl=loader.getController();
        stage.setTitle("Grad");
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        stage.setResizable(false);
        stage.show();
        stage.toFront();
        theStage = stage;
    }

    @Test
    public void testAdd(FxRobot robot) {

        // Otvaranje forme za dodavanje
        robot.clickOn("#addS");

        // Čekamo da dijalog postane vidljiv
        robot.lookup("#title1").tryQuery().isPresent();

        // Postoji li fieldNaziv
        robot.clickOn("#title1");
        robot.write("Testtesttest");

        robot.clickOn("#author1");
        robot.write("Elma Bejtovic");

        robot.clickOn("#journal1");
        robot.write("Journal");

        robot.clickOn("#aff1");
        robot.write("Affilliation");

        // Klik na dugme Ok
        robot.clickOn("#addBtn");

        robot.press(KeyCode.ALT).press(KeyCode.F4).release(KeyCode.F4).release(KeyCode.ALT);
        robot.press(KeyCode.ALT).press(KeyCode.F4).release(KeyCode.F4).release(KeyCode.ALT);

       ScientificWorksDAO dao = ScientificWorksDAO.getInstance();
        ObservableList<ScientificWork> lista=dao.getAll();
        assertEquals("Journal",lista.get(lista.size()-1).getJournal());

        robot.clickOn("Testtesttest");
        robot.clickOn("#deleteS");

        // Čekamo da dijalog postane vidljiv
        robot.lookup(".dialog-pane").tryQuery().isPresent();

        // Klik na dugme Ok
        DialogPane dialogPane = robot.lookup(".dialog-pane").queryAs(DialogPane.class);
        Button okButton = (Button) dialogPane.lookupButton(ButtonType.OK);
        robot.clickOn(okButton);

    }

    @Test
    public void delete(FxRobot robot){
        robot.clickOn("#deleteS");
        DialogPane dialogPane = robot.lookup(".dialog-pane").queryAs(DialogPane.class);
        assertEquals("No selected item",dialogPane.getContentText());
        Button okButton = (Button) dialogPane.lookupButton(ButtonType.OK);
        robot.clickOn(okButton);
    }
    @Test
    public void update(FxRobot robot){
        robot.clickOn("#updateS");
        DialogPane dialogPane = robot.lookup(".dialog-pane").queryAs(DialogPane.class);
        assertEquals("No selected item",dialogPane.getContentText());
        Button okButton = (Button) dialogPane.lookupButton(ButtonType.OK);
        robot.clickOn(okButton);
    }
    @Test
    public void update1(FxRobot robot){
        // Otvaranje forme za dodavanje
        robot.clickOn("#addS");

        // Čekamo da dijalog postane vidljiv
        robot.lookup("#title1").tryQuery().isPresent();

        // Postoji li fieldNaziv
        robot.clickOn("#title1");
        robot.write("Testtesttest");

        robot.clickOn("#author1");
        robot.write("Elma Bejtovic");

        robot.clickOn("#journal1");
        robot.write("Journal");

        robot.clickOn("#aff1");
        robot.write("Affilliation");

        // Klik na dugme Ok
        robot.clickOn("#addBtn");

        robot.press(KeyCode.ALT).press(KeyCode.F4).release(KeyCode.F4).release(KeyCode.ALT);
        robot.press(KeyCode.ALT).press(KeyCode.F4).release(KeyCode.F4).release(KeyCode.ALT);
        robot.clickOn("Testtesttest");
        robot.clickOn("#updateS");
        robot.clickOn("#journal1");
        robot.write("Journal");
        robot.clickOn("#addBtn");
        robot.press(KeyCode.ALT).press(KeyCode.F4).release(KeyCode.F4).release(KeyCode.ALT);
        robot.press(KeyCode.ALT).press(KeyCode.F4).release(KeyCode.F4).release(KeyCode.ALT);
        ScientificWorksDAO dao = ScientificWorksDAO.getInstance();
        ObservableList<ScientificWork> lista=dao.getAll();
        assertEquals("JournalJournal",lista.get(lista.size()-1).getJournal());
        robot.clickOn("Testtesttest");
        robot.clickOn("#deleteS");

        // Čekamo da dijalog postane vidljiv
        robot.lookup(".dialog-pane").tryQuery().isPresent();

        // Klik na dugme Ok
        DialogPane dialogPane = robot.lookup(".dialog-pane").queryAs(DialogPane.class);
        Button okButton = (Button) dialogPane.lookupButton(ButtonType.OK);
        robot.clickOn(okButton);

    }
    @Test
    public void saveOpen(FxRobot robot){

        robot.press(KeyCode.ALT).press(KeyCode.F).release(KeyCode.F).press(KeyCode.S).release(KeyCode.S).release(KeyCode.ALT);
        int temp=ctrl.table.getItems().size();
        // Otvaranje forme za dodavanje
        robot.clickOn("#addS");

        // Čekamo da dijalog postane vidljiv
        robot.lookup("#title1").tryQuery().isPresent();

        // Postoji li fieldNaziv
        robot.clickOn("#title1");
        robot.write("Testtesttest");

        robot.clickOn("#author1");
        robot.write("Elma Bejtovic");

        robot.clickOn("#journal1");
        robot.write("Journal");

        robot.clickOn("#aff1");
        robot.write("Affilliation");

        // Klik na dugme Ok
        robot.clickOn("#addBtn");

        robot.press(KeyCode.ALT).press(KeyCode.F4).release(KeyCode.F4).release(KeyCode.ALT);
        robot.press(KeyCode.ALT).press(KeyCode.F4).release(KeyCode.F4).release(KeyCode.ALT);
        assertEquals(temp+1,ctrl.table.getItems().size());
        robot.press(KeyCode.ALT).press(KeyCode.F).release(KeyCode.F).press(KeyCode.O).release(KeyCode.O).release(KeyCode.ALT);
        assertEquals(temp,ctrl.table.getItems().size());

    }

    @Test
    public void test(FxRobot robot) {

        // Otvaranje forme za dodavanje
        robot.clickOn("#addS");

        // Čekamo da dijalog postane vidljiv
        robot.lookup("#title1").tryQuery().isPresent();

        // Postoji li fieldNaziv
        robot.clickOn("#title1");
        robot.write("Testtesttest");

        robot.clickOn("#author1");
        robot.write("Elma Bejtovic");

        robot.clickOn("#journal1");
        robot.write("Journal");

        robot.clickOn("#aff1");
        robot.write("Affilliation");

        robot.clickOn("#fieldStudy1");
        robot.press(KeyCode.E).release(KeyCode.END);
        robot.write("F");

        robot.clickOn("#publType1");
        robot.press(KeyCode.END).release(KeyCode.END);
        robot.write("N");

        // Klik na dugme Ok
        robot.clickOn("#addBtn");

        robot.press(KeyCode.ALT).press(KeyCode.F4).release(KeyCode.F4).release(KeyCode.ALT);
        robot.press(KeyCode.ALT).press(KeyCode.F4).release(KeyCode.F4).release(KeyCode.ALT);


        ScientificWorksDAO dao=ScientificWorksDAO.getInstance();
        dao.deleteFS(dao.getIdField("MedicineeF"));
        dao.deletePT(dao.getIdType("Journal publicationsN"));


        robot.clickOn("Testtesttest");
        robot.clickOn("#deleteS");

        // Čekamo da dijalog postane vidljiv
        robot.lookup(".dialog-pane").tryQuery().isPresent();
        // Klik na dugme Ok
        DialogPane dialogPane = robot.lookup(".dialog-pane").queryAs(DialogPane.class);
        Button okButton1 = (Button) dialogPane.lookupButton(ButtonType.OK);
        robot.clickOn(okButton1);

    }
    @Test
    public void Testic(FxRobot robot){

    }




}