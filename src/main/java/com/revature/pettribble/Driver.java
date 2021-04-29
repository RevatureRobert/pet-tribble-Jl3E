package com.revature.pettribble;

import com.revature.pettribble.db.Crud;
import com.revature.pettribble.model.Lab;
import com.revature.pettribble.model.Tribble;

import javax.persistence.Column;
import javax.persistence.Id;

public class Driver {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        Crud crud = new Crud();
        Lab lab = new Lab(1,"name", 1);
        Tribble tribble = new Tribble(1,"Spot", 50, 15, "red");

//        crud.insertSQLQuery(lab);
//        crud.insertSQLQuery(tribble);

//        crud.selectAllSql(lab);
        crud.deleteById(lab, 1);

//        System.out.println();
    }
}
