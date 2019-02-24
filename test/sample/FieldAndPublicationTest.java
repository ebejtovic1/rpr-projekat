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



}