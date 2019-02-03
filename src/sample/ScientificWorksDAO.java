package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;

public class ScientificWorksDAO {


    private static ScientificWorksDAO instance;

    private Connection conn;
    private PreparedStatement deleteAll, updateTypeQuery, selectAll,deleteFS,updateFieldQuery, setNullQuery, setNullQuery1, selectAllField, updateScien, selectAllType, getFieldQuery, getTypeQuery, addField, addType, addScien, maxIdField, maxIdType, maxIdScien, getIdFieldQuery, getIdTypeQuery, deleteSW, deletePT ;

    public static ScientificWorksDAO getInstance(){
        if(instance==null)instance= new ScientificWorksDAO();
        return instance;
    }
    private ScientificWorksDAO(){
        try {
            conn= DriverManager.getConnection("jdbc:sqlite:base.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
           selectAll=conn.prepareStatement("SELECT* FROM Scientific_work");
        } catch (SQLException e) {
            try {
                regenerateBase();
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
            try {
                selectAll=conn.prepareStatement("SELECT* FROM Scientific_work");
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
        try {
            deleteAll=conn.prepareStatement("DELETE FROM Scientific_work");
            updateTypeQuery=conn.prepareStatement("UPDATE Publication_type SET typee=? WHERE id=?");
            updateFieldQuery=conn.prepareStatement("UPDATE Field_of_study SET title=? WHERE id=?");
            selectAllType=conn.prepareStatement("SELECT* FROM Publication_Type");
            setNullQuery=conn.prepareStatement("UPDATE Scientific_work SET FieldOfStudy=null WHERE FieldOfStudy=?");
            setNullQuery1=conn.prepareStatement("UPDATE Scientific_work SET PublicationType=null WHERE PublicationType=?");
            selectAllField=conn.prepareStatement("SELECT* FROM Field_of_study");
            getFieldQuery=conn.prepareStatement("SELECT* FROM Field_of_study WHERE ID=?");
            getTypeQuery=conn.prepareStatement("SELECT* FROM Publication_Type WHERE ID=?");
            addField=conn.prepareStatement("INSERT INTO Field_of_study VALUES(?,?)");
            addType=conn.prepareStatement("INSERT INTO Publication_Type VALUES(?,?)");
            addScien=conn.prepareStatement("INSERT INTO Scientific_work VALUES(?,?,?,?,?,?,?,?,?)");
            updateScien=conn.prepareStatement("UPDATE Scientific_work SET title=?, author=?, FieldOfStudy=?, Journal=?, PublicationType=?, YearOfIssue=?, Citations=?, Affiliation=? WHERE id=?");
            maxIdField=conn.prepareStatement("SELECT max(id)+1 FROM Field_of_study");
            maxIdType=conn.prepareStatement("SELECT max(id)+1 FROM Publication_Type");
            maxIdScien=conn.prepareStatement("SELECT max(id)+1 FROM Scientific_work");
            getIdFieldQuery=conn.prepareStatement("SELECT id FROM Field_of_study WHERE Title=?");
            getIdTypeQuery=conn.prepareStatement("SELECT id FROM Publication_Type WHERE typee=?");
            deleteSW=conn.prepareStatement("DELETE FROM Scientific_work WHERE id=?");
            deletePT=conn.prepareStatement("DELETE FROM Publication_Type WHERE id=?");
            deleteFS=conn.prepareStatement("DELETE FROM Field_of_study WHERE id=?");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void regenerateBase() throws FileNotFoundException {
        Scanner ulaz=new Scanner(new FileInputStream("base.db.sql"));
        String sql="";
        while(ulaz.hasNext()){
            sql+=ulaz.nextLine();
            if(sql.charAt(sql.length()-1)==';'){
                try {
                    Statement stmt=conn.createStatement();
                    stmt.execute(sql);
                    sql="";
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        ulaz.close();
    }

    private FieldOfStudy getFieldRS(ResultSet rs) throws SQLException {
       return new FieldOfStudy(rs.getInt(1), rs.getString(2));
    }

    private FieldOfStudy getField(int id) {
        try {
            getFieldQuery.setInt(1, id);
            ResultSet rs=getFieldQuery.executeQuery();
            if(!rs.next())return null;
            return getFieldRS(rs);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    private PublicationType getTypeRS(ResultSet rs) throws SQLException {
        return new PublicationType(rs.getInt(1), rs.getString(2));
    }

    private PublicationType getType(int id) {
        try {
            getTypeQuery.setInt(1, id);
            ResultSet rs=getTypeQuery.executeQuery();
            if(!rs.next())return null;
            return getTypeRS(rs);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    public ObservableList<ScientificWork> getAll(){
        ObservableList<ScientificWork> SW= FXCollections.observableArrayList();
        try {
            ResultSet rs= selectAll.executeQuery();
            while(rs.next()){
                ScientificWork ssw=new ScientificWork(rs.getInt(1), rs.getString(2),rs.getString(3),getField(rs.getInt(4)),rs.getString(5),getType(rs.getInt(6)),rs.getInt(7),rs.getInt(8),rs.getString(9));
                SW.add(ssw);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return SW;
    }

    public void addFieldS(String title){
        try {
            ResultSet rs=maxIdField.executeQuery();
            int id=1;
            if(rs.next()){
                id=rs.getInt(1);
            }
            addField.setInt(1, id);
            addField.setString(2, title);
            addField.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getIdField(String title){
        try {
            getIdFieldQuery.setString(1, title);
            ResultSet rs=getIdFieldQuery.executeQuery();
            int id=-1;
            if(rs.next()){
                id=rs.getInt(1);
            }
            return id;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int getIdType(String title){
        try {
            getIdTypeQuery.setString(1, title);
            ResultSet rs=getIdTypeQuery.executeQuery();
            int id=-1;
            if(rs.next()){
                id=rs.getInt(1);
            }
            return id;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }


    public void addTypeP(String type){
        try {
            ResultSet rs=maxIdType.executeQuery();
            int id=1;
            if(rs.next()){
                id=rs.getInt(1);
            }
            addType.setInt(1, id);
            addType.setString(2, type);
            addType.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id){
        try {
            deleteSW.setInt(1,id);
            deleteSW.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void deletePT(int id){
        try {
            deletePT.setInt(1,id);
            deletePT.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteFS(int id){
        try {
            deleteFS.setInt(1,id);
            deleteFS.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addScien(String title, String autor, int field, String journal, int publ, int year, int citations, String aff){
        try {
            ResultSet rs=maxIdScien.executeQuery();
            int id=1;
            if(rs.next()){
                id=rs.getInt(1);
            }
            addScien.setInt(1, id);
            addScien.setString(2, title);
            addScien.setString(3, autor);
            addScien.setInt(4, field);
            addScien.setString(5, journal);
            addScien.setInt(6,publ);
            addScien.setInt(7, year);
            addScien.setInt(8, citations);
            addScien.setString(9, aff);
            addScien.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateScien(String title, String autor, int field, String journal, int publ, int year, int citations, String aff, int id){
        try {

            updateScien.setString(1, title);
            updateScien.setString(2, autor);
            updateScien.setInt(3, field);
            updateScien.setString(4, journal);
            updateScien.setInt(5,publ);
            updateScien.setInt(6, year);
            updateScien.setInt(7, citations);
            updateScien.setString(8, aff);
            updateScien.setInt(9, id);
            updateScien.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<PublicationType> getAllType(){
        ObservableList<PublicationType> SW= FXCollections.observableArrayList();
        try {
            ResultSet rs= selectAllType.executeQuery();
            while(rs.next()){
                PublicationType ssw=new PublicationType(rs.getInt(1),rs.getString(2));
                SW.add(ssw);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return SW;
    }
    public ObservableList<FieldOfStudy> getAllField(){
        ObservableList<FieldOfStudy> SW= FXCollections.observableArrayList();
        try {
            ResultSet rs= selectAllField.executeQuery();
            while(rs.next()){
                FieldOfStudy ssw=new FieldOfStudy(rs.getInt(1),rs.getString(2));
                SW.add(ssw);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return SW;
    }

    public void setNull(int id){
        try {
            setNullQuery.setInt(1,id);
            setNullQuery.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setNull1(int id){
        try {
            setNullQuery1.setInt(1,id);
            setNullQuery1.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateField(String title, int id){
        try {
            updateFieldQuery.setString(1, title);
            updateFieldQuery.setInt(2,id);
            updateFieldQuery.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void updateType(String title, int id){
        try {
            updateTypeQuery.setString(1, title);
            updateTypeQuery.setInt(2,id);
            updateTypeQuery.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void deleteAll() {
        try {
            deleteAll.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
