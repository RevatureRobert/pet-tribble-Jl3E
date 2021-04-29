package com.revature.pettribble;

import com.revature.pettribble.db.Crud;
import com.revature.pettribble.model.Lab;
import com.revature.pettribble.model.Tribble;

public class Driver {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        Crud crud = new Crud();
        Lab lab = new Lab(1,"name", 1);
        Tribble tribble = new Tribble(1,"Spot", 50, 15, "red");

        crud.insertSQLQuery(lab);
        crud.insertSQLQuery(tribble);

        crud.selectAllSql(lab);
        crud.deleteById(lab, 1);
        crud.deleteById(tribble, 1);
    }
}
