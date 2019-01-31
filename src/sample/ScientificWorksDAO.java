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
    private PreparedStatement selectAll, getFieldQuery, getTypeQuery, addField, addType, addScien, maxIdField, maxIdType, maxIdScien, getIdFieldQuery, getIdTypeQuery ;

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
           //selectAll=conn.prepareStatement("SELECT Scientific_work.id,Scientific_work.title, Scientific_work.author, Field_of_Study.title, Scientific_work.journal, Publication_Type.typee, Scientific_work.YearOfIssue, Scientific_work.Citations, Scientific_work.Affiliation FROM Scientific_work, Publication_Type, Field_of_Study.title WHERE Scientific_work.FieldOfStudy=Field_of_Study.id AND Scientific_work.PublicationType=Publication_Type.id ");
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
            getFieldQuery=conn.prepareStatement("SELECT* FROM Field_of_study WHERE ID=?");
            getTypeQuery=conn.prepareStatement("SELECT* FROM Publication_Type WHERE ID=?");
            addField=conn.prepareStatement("INSERT INTO Field_of_study VALUES(?,?)");
            addType=conn.prepareStatement("INSERT INTO Publication_Type VALUES(?,?)");
            addScien=conn.prepareStatement("INSERT INTO Scientific_work VALUES(?,?,?,?,?,?,?,?,?)");
            maxIdField=conn.prepareStatement("SELECT max(id)+1 FROM Field_of_study");
            maxIdType=conn.prepareStatement("SELECT max(id)+1 FROM Publication_Type");
            maxIdScien=conn.prepareStatement("SELECT max(id)+1 FROM Scientific_work");
            getIdFieldQuery=conn.prepareStatement("SELECT id FROM Field_of_study WHERE Title=?");
            getIdTypeQuery=conn.prepareStatement("SELECT id FROM Publication_Type WHERE typee=?");

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




}
