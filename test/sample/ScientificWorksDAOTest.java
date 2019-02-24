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


}