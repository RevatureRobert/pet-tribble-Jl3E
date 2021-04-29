package com.revature.pettribble.servlet;

import com.revature.pettribble.db.Crud;
import com.revature.pettribble.model.Tribble;

import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/tribble")
public class TribbleServlet extends HttpServlet {

    @Override
    public void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        Crud crud = new Crud();
        Tribble tribble;
        PrintWriter out = resp.getWriter();
        StringBuilder jsonBuffer = new StringBuilder();
        String line;

        //Try reading body
        try{
            BufferedReader reader = req.getReader();
            while((line = reader.readLine()) != null){
                jsonBuffer.append(line);
            }
        }catch (Exception e){
            out = resp.getWriter();
            out.write("Couldn't read body");
        }

        //Try to create reimbursement with json body contents
        try{
            JSONObject jsonObject = new JSONObject(jsonBuffer.toString());
            int id = jsonObject.getJSONObject("reimbursement").getInt("id");
            crud.getById(id);// need this still
            out.print("Success");
        }catch(Exception e){
            out.print("Failure");
        }


    }
}
