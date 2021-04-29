package com.revature.pettribble.db;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.revature.pettribble.annotation.DataTable;
import com.revature.pettribble.config.ApplicationUtil;

import javax.persistence.Column;
import javax.persistence.Id;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.*;

public class Crud {

    public void insertSQLQuery(Object o) throws NoSuchFieldException, IllegalAccessException {
        Class clazz = o.getClass();
        String className = o.getClass().getSimpleName(); // this gives the classname to be used as the table name

        Gson gson = new Gson();
        String json = gson.toJson(o);
        String yourJson = json;
        JsonElement element = new JsonParser().parse(json);
        JsonObject obj = element.getAsJsonObject(); //since you know it's a JsonObject
        Set<Map.Entry<String, JsonElement>> entries = obj.entrySet();//will return members of your object

        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append("insert into ");
        sqlBuilder.append(className.toLowerCase() + " values (");
        int size = entries.size();
        int indexValues = 0;
        for (Map.Entry<String, JsonElement> entry : entries) {
            String check = String.valueOf(entry.getValue());
            if (check.contains("\"")) {
                String newCheck = check.replace("\"", "\'");
                if (indexValues == entries.size() - 1) {
                    sqlBuilder.append(newCheck + ");");
                    break;
                }
                sqlBuilder.append(newCheck + ", ");
                indexValues++;
                continue;
            }
            if (indexValues == entries.size() - 1) {
                sqlBuilder.append(entry.getValue() + ");");
                break;
            }
            sqlBuilder.append(entry.getValue() + ", ");
            indexValues++;
        }

        String sql = String.valueOf(sqlBuilder);
        try {
            Statement st = ApplicationUtil.getInstance().getConnection().createStatement();
            int i = st.executeUpdate(sql);
            System.out.println("The number of updated rows were " + i);

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    public void selectAllSql(Object o) {
        String className = o.getClass().getSimpleName().toLowerCase();
        String sql = "select * from " + className + ";";

        if(doesTableExist(o)){
            try {
                Statement st = ApplicationUtil.getInstance().getConnection().createStatement();
                ResultSet resultSet = st.executeQuery(sql);
                ResultSetMetaData rsmd = resultSet.getMetaData();
                int columnsNumber = rsmd.getColumnCount();
                while (resultSet.next()) {
                    for (int i = 1; i <= columnsNumber; i++) {
                        if (i > 1) System.out.print(",  ");
                        String columnValue = resultSet.getString(i);
//                    System.out.print(columnValue + " " + rsmd.getColumnName(i));// gets column names too
                        System.out.print(columnValue);
                    }
                    System.out.println("");
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }//TODO: make a create a table or crud
//        else{
//            System.out.println("Your table doesn't exist bud.");
//            createTableSQL(o);
//            System.out.println("Now it does!");
//        }

    }

    public void deleteById(Object o, int primaryKey) {
        String tableName = o.getClass().getSimpleName().toLowerCase();
        Queue<String> id = new LinkedList<>();
        Field[] fields = o.getClass().getDeclaredFields();

        //this get my annotations from the class of Object o passed and the annotations .name() gives the column name and .dataType() gives the sql data type acceptable for user
        for (Field f : fields) {
            if (f.getAnnotation(Id.class) != null)
                id.add(f.getAnnotation(Column.class).name());
        }

        String sql = "delete from " + tableName + " where " + id.poll() + "=" + primaryKey + ";";
        try {
            Statement st = ApplicationUtil.getInstance().getConnection().createStatement();
            int i = st.executeUpdate(sql);
            System.out.println("The number of updated rows were " + i);

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public boolean doesTableExist(Object o) {
        String tableNameCheck = o.getClass().getSimpleName().toLowerCase();

        try {
            Connection con = ApplicationUtil.getInstance().getConnection();

            DatabaseMetaData meta = con.getMetaData();
            ResultSet res = meta.getTables(null, null, null,
                    new String[]{"TABLE"});
            while (res.next()) {
                if (tableNameCheck.equals(res.getString("TABLE_NAME"))) {
                    return true;
                }
            }
            res.close();

            con.close();
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return false;
    }
}



////        this get my annotations from the class of Object o passed and the annotations .name() gives the column name and .dataType() gives the sql data type acceptable for user
//        for (Field f : fields) {
//                if (f.getAnnotation(Id.class) != null) {
////                System.out.println(f.getAnnotation(Column.class).name());
//        String idName = f.getAnnotation(Column.class).name();
////                System.out.println(o.getClass().getDeclaredField(idName).get(o));
//        columns.add(f.getAnnotation(Column.class).name());
////                columns.add(f.getAnnotation(com.project1.annotations.Id.class).dataType());
//        }
//        if (f.getAnnotation(Column.class) != null) {
//        System.out.println(f.getAnnotation(Column.class).name());
////                columns.add(f.getAnnotation(Column.class).name());
////                columns.add(f.getAnnotatedType(Column.class));
//        }
//        }






//    Queue<String> columns = new LinkedList<>();//this holds the table names and data types
//
//    Class clazz = o.getClass();
//    Field[] fields = clazz.getDeclaredFields();
//    String className = o.getClass().getSimpleName(); // this gives the classname to be used as the table name
//
////        this get my annotations from the class of Object o passed and the annotations .name() gives the column name and .dataType() gives the sql data type acceptable for user
//        for (Field f : fields) {
//                if (f.getAnnotation(Column.class) != null) {
//        System.out.println(f.getAnnotation(Column.class).name());
//        System.out.println(clazz.getName());
//        columns.add(f.getAnnotation(Column.class).name());
//        }
//        }
//
////        StringBuilder sqlBuilder = new StringBuilder();
////
////        sqlBuilder.append("create table ");
////        sqlBuilder.append(className.toLowerCase() + "(");
////        int size = columns.size() / 2;
////
////        for (int i = 0; i < size; i++) {
////            if (columns.size() == 2) { // this is hard coded as 2 because the last two elements in the queue will be the column name and data type
////                sqlBuilder.append(columns.poll() + " " + columns.poll() + ");");
////                break;
////            }
////            sqlBuilder.append(columns.poll() + " " + columns.poll() + ", ");
////        }
////        //System.out.println(sqlBuilder); // to test the database with first
////
////        String sql = String.valueOf(sqlBuilder);
//////        System.out.println(sql);//TODO: THIS IS THE RIGHT SQL
////        return sql;
//        return null;
//        }