package sample;

import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ScientificWorksDAOTest {

    @Test
    void getField() {
        ScientificWorksDAO.removeInstance();
        File dbfile = new File("baza.db");
        dbfile.delete();
        ScientificWorksDAO dao = ScientificWorksDAO.getInstance();
        dao.addFieldS("Test");
        int id=dao.getIdField("Test");
        FieldOfStudyModel model=new FieldOfStudyModel();
        FieldOfStudy nepoznat = dao.getField(id);
        assertEquals("Test",nepoznat.getTitle());
        FieldOfStudyModel model1=new FieldOfStudyModel();
        model1.reload();
        model.setScWork(model1.getScWork());
        assertNotEquals(0,model.getScWork().size());
        dao.deleteFS(id);
        assertNull(dao.getField(id));
    }

    @Test
    void getField1() {
        ScientificWorksDAO.removeInstance();
        File dbfile = new File("baza.db");
        dbfile.delete();
        ScientificWorksDAO dao = ScientificWorksDAO.getInstance();
        dao.addFieldS("Test");
        int id=dao.getIdField("Test");
        FieldOfStudy nepoznat = dao.getField(id);
        assertEquals("Test",nepoznat.getTitle());
        dao.deleteFS(id);
        assertNull(dao.getField(id));
    }

    @Test
    void getType() {
        ScientificWorksDAO.removeInstance();
        File dbfile = new File("baza.db");
        dbfile.delete();
        ScientificWorksDAO dao = ScientificWorksDAO.getInstance();
        dao.addTypeP("Test");
        int id=dao.getIdType("Test");
        PublicationType nepoznat = dao.getType(id);
        assertEquals("Test",nepoznat.getType());
        dao.deletePT(id);
        assertNull(dao.getType(id));
    }

    @Test
    void getType1() {
        ScientificWorksDAO.removeInstance();
        File dbfile = new File("baza.db");
        dbfile.delete();
        ScientificWorksDAO dao = ScientificWorksDAO.getInstance();
        dao.addTypeP("Test");
        PublicationTypeModel model=new PublicationTypeModel();
        int id=dao.getIdType("Test");
        PublicationType nepoznat = dao.getType(id);
        assertEquals("Test",nepoznat.getType());
        PublicationTypeModel model1=new PublicationTypeModel();
        model1.reload();
        model.setScWork(model1.getScWork());
        assertNotEquals(0,model.getScWork().size());
        dao.deletePT(id);
        assertNull(dao.getType(id));
    }

    @Test
    void ScienTest(){
        ScientificModel model=new ScientificModel();
        ScientificModel model1=new ScientificModel();
        model1.setScWork(model.getScWork());
        assertEquals(model.getScWork().size(),model1.getScWork().size());

    }


}