package sample;

import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.swing.JRViewer;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;
import javax.swing.*;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Function;

public class Controller {
    public static ScientificModel model=new ScientificModel();
    public ScientificWorksDAO dao=ScientificWorksDAO.getInstance();
    public TableView<ScientificWork> table;
    public TableColumn<ScientificWork, SimpleIntegerProperty> id;
    public TableColumn<ScientificWork, SimpleStringProperty>title;
    public TableColumn<ScientificWork, SimpleStringProperty>author;
    public TableColumn<ScientificWork, String>field;
    public TableColumn<ScientificWork, SimpleStringProperty>journal;
    public TableColumn<ScientificWork, String>type;
    public TableColumn<ScientificWork, SimpleIntegerProperty> year;
    public TableColumn<ScientificWork, SimpleIntegerProperty> citations;
    public TableColumn<ScientificWork, SimpleStringProperty> affiliation;
    public ChoiceBox<String> choiceBox;
    public  TextField textField;
    public  TextField textField1;
    public  TextField textField2;
    private ValidationSupport[]support;
    public Button addS;




    public Controller() {
        support = new ValidationSupport[2];
        for (int i = 0; i < 2; i++)
            support[i] = new ValidationSupport();
    }


    @FXML
    private void initialize() {
        support[0].registerValidator(textField1, false, Validator.createPredicateValidator(o1 -> validation(textField1.getText()), "This field can contain only letters and must not be empty"));
        support[1].registerValidator(textField2, false, Validator.createPredicateValidator(o1 -> validation(textField2.getText()), "This field can contain only letters and must not be empty"));
        check(textField1, this::validation, support[0]);
        check(textField2, this::validation, support[1]);

        checkAfterFocus(textField1, support[0]);
        checkAfterFocus(textField2, support[1]);



        id.setCellValueFactory(new PropertyValueFactory<ScientificWork, SimpleIntegerProperty>("id"));
        title.setCellValueFactory(new PropertyValueFactory<ScientificWork, SimpleStringProperty>("Title"));
        author.setCellValueFactory(new PropertyValueFactory<ScientificWork, SimpleStringProperty>("Author"));

        field.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ScientificWork, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ScientificWork, String> p) {
                if(p.getValue().getFieldOfStudy()!=null)
                return new SimpleStringProperty(p.getValue().getFieldOfStudy().getTitle());
                return new SimpleStringProperty(" ");
            }

        });

        type.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ScientificWork, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ScientificWork, String> p) {
                if(p.getValue().getPublicationType()!=null)
                return new SimpleStringProperty(p.getValue().getPublicationType().getType());
                return new SimpleStringProperty(" ");
            }
        });



        journal.setCellValueFactory(new PropertyValueFactory<ScientificWork, SimpleStringProperty>("Journal"));

        year.setCellValueFactory(new PropertyValueFactory<ScientificWork, SimpleIntegerProperty>("YearOfIssue"));
        citations.setCellValueFactory(new PropertyValueFactory<ScientificWork, SimpleIntegerProperty>("Citations"));
        affiliation.setCellValueFactory(new PropertyValueFactory<ScientificWork, SimpleStringProperty>("Affiliation"));
        FilteredList<ScientificWork> flPerson = new FilteredList(model.getScWork(), p -> true);


        title.setSortable(true);
        author.setSortable(true);

        choiceBox.getItems().addAll("All","By title", "By author", "By field of study", "By publication type", "By year of issue", "By ID", "By affiliation", "By citations", "By journal");
        choiceBox.setValue("All");
        choiceBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) ->
        {
            if (newVal != null)
            {
                textField.setText("");
                flPerson.setPredicate(null);
            }
        });

        textField.setPromptText("Search here...");
        textField.setOnKeyReleased(keyEvent ->
        {
            switch (choiceBox.getValue())
            {
                case "By title":
                    flPerson.setPredicate(p -> p.getTitle().toLowerCase().contains(textField.getText().toLowerCase().trim()));
                    break;

                case "By author":
                    flPerson.setPredicate(p -> p.getAuthor().toLowerCase().contains(textField.getText().toLowerCase().trim()));
                    break;

                case "By field of study":
                    flPerson.setPredicate(p -> p.getFieldOfStudy().getTitle().toLowerCase().contains(textField.getText().toLowerCase().trim()));
                    break;

                case "By publication type":
                    flPerson.setPredicate(p -> p.getPublicationType().getType().toLowerCase().contains(textField.getText().toLowerCase().trim()));
                    break;

                case "By year of issue":
                    flPerson.setPredicate(p -> String.valueOf(p.getYearOfIssue()).toLowerCase().contains(textField.getText().toLowerCase().trim()));
                    break;

                case "By ID":
                    flPerson.setPredicate(p -> String.valueOf(p.getId()).toLowerCase().contains(textField.getText().toLowerCase().trim()));
                    break;

                case "By affiliation":
                    flPerson.setPredicate(p -> p.getAffiliation()!=null && p.getAffiliation().toLowerCase().contains(textField.getText().toLowerCase().trim()));
                    break;

                case "By citations":
                    flPerson.setPredicate(p -> String.valueOf(p.getCitations()).toLowerCase().contains(textField.getText().toLowerCase().trim()));
                    break;

                case "By journal":
                    flPerson.setPredicate(p -> p.getJournal()!=null && p.getJournal().toLowerCase().contains(textField.getText().toLowerCase().trim()));
                    break;

                case "All":
                    flPerson.setPredicate(p -> {
                        if(String.valueOf(p.getYearOfIssue()).toLowerCase().contains(textField.getText().toLowerCase().trim()))return true;
                        if(String.valueOf(p.getId()).toLowerCase().contains(textField.getText().toLowerCase().trim()))return true;
                        if(String.valueOf(p.getCitations()).toLowerCase().contains(textField.getText().toLowerCase().trim()))return true;
                        else if(p.getTitle().toLowerCase().contains(textField.getText().toLowerCase().trim()))return true;
                        else if( p.getAuthor().toLowerCase().contains(textField.getText().toLowerCase().trim()))return true;
                        else if(p.getFieldOfStudy().getTitle().toLowerCase().contains(textField.getText().toLowerCase().trim()))return true;
                        else if(p.getPublicationType().getType().toLowerCase().contains(textField.getText().toLowerCase().trim()))return true;
                        else if(p.getAffiliation()!=null && p.getAffiliation().toLowerCase().contains(textField.getText().toLowerCase().trim()))return true;
                        else if(p.getJournal()!=null && p.getJournal().toLowerCase().contains(textField.getText().toLowerCase().trim()))return true;

                    return false;
                    });
                    break;
            }
        });



        SortedList<ScientificWork> sortedData = new SortedList<>(flPerson);
        sortedData.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedData);
    }

    boolean validation(String s){
        return (s.matches("^[a-zA-Z_ \\&]+$") && s.length()>0);
    }

    private void check(TextField tekstualnoPolje, Function<String, Boolean> validacija, ValidationSupport support) {
        tekstualnoPolje.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (validacija.apply(newValue)) {
                tekstualnoPolje.getStyleClass().removeAll("poljeNijeIspravno", "poljeNeutralno");
                tekstualnoPolje.getStyleClass().add("poljeIspravno");
            } else {
                tekstualnoPolje.getStyleClass().removeAll("poljeIspravno", "poljeNeutralno");
                tekstualnoPolje.getStyleClass().add("poljeNijeIspravno");
                support.setErrorDecorationEnabled(true);
            }
        });
    }



    private void checkAfterFocus(TextField tekstualnoPolje, ValidationSupport support) {
        tekstualnoPolje.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue){
                support.setErrorDecorationEnabled(false);
            tekstualnoPolje.getStyleClass().removeAll("poljeNijeIspravno", "poljeNeutralno");
            tekstualnoPolje.getStyleClass().add("poljeIspravno");
            support.setErrorDecorationEnabled(false);}
            else{
                support.setErrorDecorationEnabled(true);
            tekstualnoPolje.getStyleClass().removeAll("poljeNijeIspravno", "poljeNeutralno");
            tekstualnoPolje.getStyleClass().add("poljeIspravno");
            support.setErrorDecorationEnabled(false);}
        });
    }
    public void reload(){
        id.setCellValueFactory(new PropertyValueFactory<ScientificWork, SimpleIntegerProperty>("id"));
        title.setCellValueFactory(new PropertyValueFactory<ScientificWork, SimpleStringProperty>("Title"));
        author.setCellValueFactory(new PropertyValueFactory<ScientificWork, SimpleStringProperty>("Author"));

        field.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ScientificWork, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ScientificWork, String> p) {
                if(p.getValue().getFieldOfStudy()!=null)
                    return new SimpleStringProperty(p.getValue().getFieldOfStudy().getTitle());
                return new SimpleStringProperty(" ");
            }

        });

        type.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ScientificWork, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ScientificWork, String> p) {
                if(p.getValue().getPublicationType()!=null)
                    return new SimpleStringProperty(p.getValue().getPublicationType().getType());
                return new SimpleStringProperty(" ");
            }
        });

        journal.setCellValueFactory(new PropertyValueFactory<ScientificWork, SimpleStringProperty>("Journal"));
        year.setCellValueFactory(new PropertyValueFactory<ScientificWork, SimpleIntegerProperty>("YearOfIssue"));
        citations.setCellValueFactory(new PropertyValueFactory<ScientificWork, SimpleIntegerProperty>("Citations"));
        affiliation.setCellValueFactory(new PropertyValueFactory<ScientificWork, SimpleStringProperty>("Affiliation"));
        FilteredList<ScientificWork> flPerson = new FilteredList(model.getScWork(), p -> true);


        title.setSortable(true);
        author.setSortable(true);
        choiceBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) ->
        {
            if (newVal != null)
            {
                textField.setText("");
                flPerson.setPredicate(null);
            }
        });

        textField.setPromptText("Search here...");
        textField.setOnKeyReleased(keyEvent ->
        {
            switch (choiceBox.getValue())
            {
                case "By title":
                    flPerson.setPredicate(p -> p.getTitle().toLowerCase().contains(textField.getText().toLowerCase().trim()));
                    break;

                case "By author":
                    flPerson.setPredicate(p -> p.getAuthor().toLowerCase().contains(textField.getText().toLowerCase().trim()));
                    break;

                case "By field of study":
                    flPerson.setPredicate(p -> p.getFieldOfStudy().getTitle().toLowerCase().contains(textField.getText().toLowerCase().trim()));
                    break;

                case "By publication type":
                    flPerson.setPredicate(p -> p.getPublicationType().getType().toLowerCase().contains(textField.getText().toLowerCase().trim()));
                    break;

                case "By year of issue":
                    flPerson.setPredicate(p -> String.valueOf(p.getYearOfIssue()).toLowerCase().contains(textField.getText().toLowerCase().trim()));
                    break;

                case "By ID":
                    flPerson.setPredicate(p -> String.valueOf(p.getId()).toLowerCase().contains(textField.getText().toLowerCase().trim()));
                    break;

                case "By affiliation":
                    flPerson.setPredicate(p -> p.getAffiliation()!=null && p.getAffiliation().toLowerCase().contains(textField.getText().toLowerCase().trim()));
                    break;

                case "By citations":
                    flPerson.setPredicate(p -> String.valueOf(p.getCitations()).toLowerCase().contains(textField.getText().toLowerCase().trim()));
                    break;

                case "By journal":
                    flPerson.setPredicate(p -> p.getJournal()!=null && p.getJournal().toLowerCase().contains(textField.getText().toLowerCase().trim()));
                    break;

                case "All":
                    flPerson.setPredicate(p -> {
                        if(String.valueOf(p.getYearOfIssue()).toLowerCase().contains(textField.getText().toLowerCase().trim()))return true;
                        if(String.valueOf(p.getId()).toLowerCase().contains(textField.getText().toLowerCase().trim()))return true;
                        if(String.valueOf(p.getCitations()).toLowerCase().contains(textField.getText().toLowerCase().trim()))return true;
                        else if(p.getTitle().toLowerCase().contains(textField.getText().toLowerCase().trim()))return true;
                        else if( p.getAuthor().toLowerCase().contains(textField.getText().toLowerCase().trim()))return true;
                        else if(p.getFieldOfStudy().getTitle().toLowerCase().contains(textField.getText().toLowerCase().trim()))return true;
                        else if(p.getPublicationType().getType().toLowerCase().contains(textField.getText().toLowerCase().trim()))return true;
                        else if(p.getAffiliation()!=null && p.getAffiliation().toLowerCase().contains(textField.getText().toLowerCase().trim()))return true;
                        else if(p.getJournal()!=null && p.getJournal().toLowerCase().contains(textField.getText().toLowerCase().trim()))return true;

                        return false;
                    });
                    break;
            }
        });


        SortedList<ScientificWork> sortedData = new SortedList<>(flPerson);
        sortedData.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedData);

    }


    public void exit(ActionEvent actionEvent){
        System.exit(0);
    }
    public void addS(ActionEvent actionEvent){
        FXMLLoader loader1 = new FXMLLoader(getClass().getResource("AddScientificWork.fxml"));
        Stage stage = new Stage(StageStyle.DECORATED);
        try { stage.setScene(new Scene((Pane) loader1.load())
        );
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.show();
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                model.getScWork().clear();
                model.reload();
                reload();

            }
        });
    }

    public void addF(ActionEvent actionEvent){
        if(validation(textField2.getText())){
        ScientificWorksDAO dao=ScientificWorksDAO.getInstance();
        dao.addFieldS(textField2.getText());
        textField2.clear();
        textField2.getStyleClass().removeAll("poljeNijeIspravno", "poljeNeutralno");
        textField2.getStyleClass().add("poljeIspravno");
        support[1].setErrorDecorationEnabled(false);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText("Field of study successfully added");
        Optional<ButtonType> action= alert.showAndWait();
        try{
            if(action.get().equals(ButtonType.OK)){
                alert.close();
            }}
        catch(NoSuchElementException z){
            return;
        }
        return;
        }
        else{
            textField2.getStyleClass().removeAll("poljeIspravno", "poljeNeutralno");
            textField2.getStyleClass().add("poljeNijeIspravno");
            support[1].setErrorDecorationEnabled(true);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Incorrect data in field!");

            Optional<ButtonType> action= alert.showAndWait();

            try{
                if(action.get().equals(ButtonType.OK)) {

                    textField2.getStyleClass().removeAll("poljeIspravno", "poljeNeutralno");
                    textField2.getStyleClass().add("poljeNijeIspravno");
                    support[1].setErrorDecorationEnabled(true);
                    if(textField2.getText().equals(""))
                    {
                        textField2.setText(".");
                        textField2.clear();
                    }
                    alert.close();
                }}
            catch(NoSuchElementException z){
                return;
            }
            return;
        }

    }
    public void save(ActionEvent actionEvent) throws FileNotFoundException {

        ArrayList<ScientificWork>zapisi=new ArrayList<ScientificWork>();
        for(ScientificWork modell:model.getScWork()){
            zapisi.add(modell);
        }
        try {
            XMLEncoder izlaz = new XMLEncoder(new FileOutputStream("saved.xml"));
            izlaz.writeObject(zapisi);
            izlaz.close();
        } catch(Exception e) {
            System.out.println("Error: "+e);
        }
    }
    public void open(ActionEvent actionEvent) {
        ArrayList<ScientificWork>ucitaj=new ArrayList<ScientificWork>();
        try {
            XMLDecoder ulaz = new XMLDecoder(new FileInputStream("saved.xml"));
            ucitaj = (ArrayList<ScientificWork>) ulaz.readObject();
            ulaz.close();
        } catch(Exception e) {
            System.out.println("Greška: "+e);
        }
        dao.deleteAll();
        for(ScientificWork novi: ucitaj){
            dao.addScien(novi.getTitle(),novi.getAuthor(),novi.getFieldOfStudy().getId(),novi.getJournal(),novi.getPublicationType().getId(),novi.getYearOfIssue(),novi.getCitations(),novi.getAffiliation());
        }
        model.getScWork().clear();
        model.reload();
        reload();

    }


    public void addP(ActionEvent actionEvent){
        if(validation(textField1.getText())){
        ScientificWorksDAO dao=ScientificWorksDAO.getInstance();
        dao.addTypeP(textField1.getText());
        textField1.clear();
        textField1.getStyleClass().removeAll("poljeNijeIspravno", "poljeNeutralno");
        textField1.getStyleClass().add("poljeIspravno");
        support[0].setErrorDecorationEnabled(false);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText("Publication type successfully added");
        Optional<ButtonType> action= alert.showAndWait();
        try{
            if(action.get().equals(ButtonType.OK)){
                alert.close();
            }}
        catch(NoSuchElementException z){
            return;
        }
        return;}
        else{
            textField1.getStyleClass().removeAll("poljeIspravno", "poljeNeutralno");
            textField1.getStyleClass().add("poljeNijeIspravno");
            support[0].setErrorDecorationEnabled(true);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Incorrect data in field!");
            Optional<ButtonType> action= alert.showAndWait();
            try{
                if(action.get().equals(ButtonType.OK)) {
                    if(textField1.getText().equals(""))
                    {
                        textField1.setText(".");
                        textField1.clear();
                    }
                    alert.close();
                }}
            catch(NoSuchElementException z){
                return;
            }
            return;
        }
    }

    public void delete (ActionEvent actionEvent)throws NoSelectedException {

        try {
            int id = table.getSelectionModel().getSelectedItem().getId();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure?");
            Optional<ButtonType> action= alert.showAndWait();
            if(action.get()== ButtonType.OK){
                dao.delete(id);
                model.getScWork().clear();
                model.reload();
                reload();
            }

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("No selected item");
            Optional<ButtonType> action= alert.showAndWait();
            try{
            if(action.get().equals(ButtonType.OK)){
                alert.close();
            }}
            catch(NoSuchElementException z){
                return;
            }
            return;
        }
    }

    public void update (ActionEvent actionEvent)throws NoSelectedException {
        FXMLLoader loader1 = new FXMLLoader(getClass().getResource("AddScientificWork.fxml"));
        Stage stage = new Stage(StageStyle.DECORATED);
        try { stage.setScene(new Scene((Pane) loader1.load())
        );
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(table.getSelectionModel().getSelectedItem()==null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("No selected item");
            Optional<ButtonType> action= alert.showAndWait();
            try{
                if(action.get().equals(ButtonType.OK)){
                    alert.close();
                }}
            catch(NoSuchElementException z){
                return;
            }
            return;


        }
        stage.show();
        AddScientificWorkController ctrl = loader1.getController();

            ctrl.setId(table.getSelectionModel().getSelectedItem().getId());

            ctrl.getTitle().textProperty().bindBidirectional(new SimpleStringProperty(table.getSelectionModel().getSelectedItem().getTitle()));
            ctrl.getAuthor().textProperty().bindBidirectional(new SimpleStringProperty(table.getSelectionModel().getSelectedItem().getAuthor()));
            ctrl.getAff().textProperty().bindBidirectional(new SimpleStringProperty(table.getSelectionModel().getSelectedItem().getAffiliation()));
            ctrl.getJournal().textProperty().bindBidirectional(new SimpleStringProperty(table.getSelectionModel().getSelectedItem().getJournal()));

            ObjectProperty<Integer> objectProp = new SimpleObjectProperty<>(table.getSelectionModel().getSelectedItem().getYearOfIssue());
            IntegerProperty integerProperty = IntegerProperty.integerProperty(objectProp);
            ctrl.getYear().getValueFactory().valueProperty().bindBidirectional(objectProp);


            ObjectProperty<Integer> objectProp1 = new SimpleObjectProperty<>(table.getSelectionModel().getSelectedItem().getCitations());
            IntegerProperty integerProperty1 = IntegerProperty.integerProperty(objectProp1);
            ctrl.getCitations().getValueFactory().valueProperty().bindBidirectional(objectProp1);

            try{
            ctrl.getFieldStudy().valueProperty().bindBidirectional(new SimpleStringProperty(table.getSelectionModel().getSelectedItem().getFieldOfStudy().getTitle()));
            }catch(Exception e){
                ctrl.getFieldStudy().valueProperty().bindBidirectional(new SimpleStringProperty(""));
            }
            try {
                ctrl.getPublType().valueProperty().bindBidirectional(new SimpleStringProperty(table.getSelectionModel().getSelectedItem().getPublicationType().getType()));
            }catch(Exception e){
                ctrl.getPublType().valueProperty().bindBidirectional(new SimpleStringProperty(""));
            }
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                public void handle(WindowEvent we) {
                    model.getScWork().clear();
                    model.reload();
                    reload();
                }
            });
        }


    public void modifyT(ActionEvent actionEvent){
        FXMLLoader loader1 = new FXMLLoader(getClass().getResource("PublicationTypeTable.fxml"));
        Stage stage = new Stage(StageStyle.DECORATED);
        try { stage.setScene(new Scene((Pane) loader1.load())
        );
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setTitle("Publication type");
        stage.setResizable(false);
        stage.show();
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                model.getScWork().clear();
                model.reload();
                reload();
            }
        });
    }

    public void about(ActionEvent actionEvent){
        FXMLLoader loader1 = new FXMLLoader(getClass().getResource("About.fxml"));
        Stage stage = new Stage(StageStyle.DECORATED);
        try { stage.setScene(new Scene((Pane) loader1.load())
        );
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setTitle("Informations");
        stage.setResizable(false);
        stage.show();
    }

    public void modifyF(ActionEvent actionEvent){
        FXMLLoader loader1 = new FXMLLoader(getClass().getResource("FieldOfStudyTable.fxml"));
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setResizable(false);
        try { stage.setScene(new Scene((Pane) loader1.load())
        );
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setTitle("Field of study");
        stage.show();

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                model.getScWork().clear();
                model.reload();
                reload();
            }
        });
    }

    public class PrintReport extends JFrame {
        public void showReport(Connection conn) throws JRException {
            String reportSrcFile = getClass().getResource("/reports/Simple_Blue.jrxml").getFile();
            String reportsDir = getClass().getResource("/reports/").getFile();

            JasperReport jasperReport = JasperCompileManager.compileReport(reportSrcFile);
            HashMap<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("reportsDirPath", reportsDir);
            ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
            list.add(parameters);
            JasperPrint print = JasperFillManager.fillReport(jasperReport, parameters, conn);
            JRViewer viewer = new JRViewer(print);
            viewer.setOpaque(true);
            viewer.setVisible(true);
            this.add(viewer);
            this.setSize(1300, 1000);
            this.setVisible(true);
        }
    }

    public class PrintReport1 extends JFrame {
        public void showReport(Connection conn) throws JRException {
            String reportSrcFile = getClass().getResource("/reports/Coffee_3.jrxml").getFile();
            String reportsDir = getClass().getResource("/reports/").getFile();

            JasperReport jasperReport = JasperCompileManager.compileReport(reportSrcFile);
            HashMap<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("reportsDirPath", reportsDir);
            ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
            list.add(parameters);
            JasperPrint print = JasperFillManager.fillReport(jasperReport, parameters, conn);
            JRViewer viewer = new JRViewer(print);
            viewer.setOpaque(true);
            viewer.setVisible(true);
            this.add(viewer);
            this.setSize(1300, 1000);
            this.setVisible(true);
        }
    }
    public class PrintReport2 extends JFrame {
        public void showReport(Connection conn) throws JRException {
            String reportSrcFile = getClass().getResource("/reports/Coffee_4.jrxml").getFile();
            String reportsDir = getClass().getResource("/reports/").getFile();

            JasperReport jasperReport = JasperCompileManager.compileReport(reportSrcFile);
            HashMap<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("reportsDirPath", reportsDir);
            ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
            list.add(parameters);
            JasperPrint print = JasperFillManager.fillReport(jasperReport, parameters, conn);
            JRViewer viewer = new JRViewer(print);
            viewer.setOpaque(true);
            viewer.setVisible(true);
            this.add(viewer);
            this.setSize(1300, 1000);
            this.setVisible(true);
        }
    }


    public void print(ActionEvent actionEvent) {
        try {
            new PrintReport().showReport(dao.getConn());
        } catch (JRException e1) {
            e1.printStackTrace();
        }
    }
    public void statistic(ActionEvent actionEvent) {
        try {
            new PrintReport1().showReport(dao.getConn());
        } catch (JRException e1) {
            e1.printStackTrace();
        }
    }
    public void statistic1(ActionEvent actionEvent) {
        try {
            new PrintReport2().showReport(dao.getConn());
        } catch (JRException e1) {
            e1.printStackTrace();
        }
    }
    public void resetujBazu() {
        ScientificWorksDAO.removeInstance();
        File dbfile = new File("base.db");
        dbfile.delete();
        dao = ScientificWorksDAO.getInstance();
    }
}
