package com.revature.pettribble.db;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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

    public void updateTable(Object o) {//the select by id needs to be used first to get a primary key
        String tableName = o.getClass().getSimpleName().toLowerCase();

        Gson gson = new Gson();
        String json = gson.toJson(o);
        JsonElement element = new JsonParser().parse(json);
        JsonObject obj = element.getAsJsonObject(); //since you know it's a JsonObject
        Set<Map.Entry<String, JsonElement>> entries = obj.entrySet();//will return members of your object

        StringBuilder sqlBuilder = new StringBuilder();
        StringBuilder sqlBuilder2 = new StringBuilder();
        sqlBuilder.append("update ");
        sqlBuilder.append(tableName.toLowerCase()+" set ");
        int size = entries.size();
        int index = 0;
        for (Map.Entry<String, JsonElement> entry : entries) {
            String check = String.valueOf(entry.getValue());
            if (check.contains("\"")) {
                String newCheck = check.replace("\"", "\'");
                if(index == 0){
                    sqlBuilder2.append("where "+entry.getKey()+"="+newCheck+";");
                    index++;
                    continue;
                }else if(index==1){
                    sqlBuilder.append(entry.getKey() + "="+newCheck+" ");
                    index++;
                    continue;
                }
                sqlBuilder.append(", "+entry.getKey() + "="+newCheck+" ");
                continue;  // this is need to skip over the other ford with quotes being added to the StringBuilder
            }
            if(index == 0){
                sqlBuilder2.append("where "+entry.getKey()+"="+entry.getValue()+";");
                index++;
                continue;
            }else if(index==1){
                sqlBuilder.append(entry.getKey() + "="+entry.getValue()+" ");
                index++;
                continue;
            }
            sqlBuilder.append(", "+entry.getKey()+"="+entry.getValue()+" ");

        }
        String sql = sqlBuilder.toString()+sqlBuilder2.toString();
        try {
            Statement st = ApplicationUtil.getInstance().getConnection().createStatement();
            int i = st.executeUpdate(sql);
            System.out.println("The number of updated rows were " + i);

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }
}


