package sample;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FieldAndPublicationTest {
    @Test
    void getField1() {
        FieldOfStudy field=new FieldOfStudy();
        field.setId(649);
        field.setTitle("Test");
        assertEquals(649, field.getId());
        assertEquals("Test",field.getTitle());

    }

    @Test
    void getPubl() {
        PublicationType field=new PublicationType();
        field.setId(649);
        field.setType("Test");
        assertEquals(649, field.getId());
        assertEquals("Test",field.getType());

    }

    @Test
    void getScien() {
        ScientificWork field=new ScientificWork();
        field.setId(649);
        field.setAuthor("testic");
        field.setJournal("Testic2");
        field.setCitations(55);
        field.setAffiliation("Afi");
        field.setYearOfIssue(1998);
        field.setTitle("Elma");
        field.setFieldOfStudy(new FieldOfStudy(1435, "hehehhe"));
        field.setPublicationType(new PublicationType(1234, "HEHE"));
        assertEquals(649, field.getId());
        assertEquals("testic", field.getAuthor());
        assertEquals("Testic2",field.getJournal());
        assertEquals(55,field.getCitations());
        assertEquals("Afi",field.getAffiliation());
        assertEquals("hehehhe",field.getFieldOfStudy().getTitle());
        assertEquals("HEHE",field.getPublicationType().getType());
        assertEquals(1998, field.getYearOfIssue());
        assertEquals("Elma",field.getTitle());


    }
    @Test
    void getScien1() {
        ScientificWork field=new ScientificWork(649,"Elma","testic",new FieldOfStudy(1435, "hehehhe"),"Testic2",new PublicationType(1234, "HEHE"),1998,55,"Afi");
        assertEquals(649, field.getId());
        assertEquals("testic", field.getAuthor());
        assertEquals("Testic2",field.getJournal());
        assertEquals(55,field.getCitations());
        assertEquals("Afi",field.getAffiliation());
        assertEquals("hehehhe",field.getFieldOfStudy().getTitle());
        assertEquals("HEHE",field.getPublicationType().getType());
        assertEquals(1998, field.getYearOfIssue());
        assertEquals("Elma",field.getTitle());
    }

}