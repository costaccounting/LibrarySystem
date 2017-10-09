/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author prodip
 */
public class MemberList extends ArrayList {
    private List<Member> memberlist = new ArrayList<>(); //local variable for memberlist
    
    public List getMemberList(){return this.memberlist; } //getter for memberlist
    
    public void initialize(){ //initializing a sample list of members
        Member p[] = new Member[15];
        p[1]= new Member(1,"Prodip Mitra","","2014-11-24");
        p[2]= new Member(2,"Lord Kelvin","","2014-11-25");
        p[3]= new Member(3,"Marie Curie","","2015-01-09");
        p[4]= new Member(4,"Max Planck","", "2015-02-19");
        p[5]= new Member(5,"Patty Jo Watson","","2015-03-12");
        p[6]= new Member(6,"Edmond Halley","","2015-04-09");
        p[7]= new Member(7,"Enrico Fermi","","2015-07-23");
        p[8]= new Member(8,"Blaise Pascal","","2015-10-30");
        p[9]= new Member(9,"Ruzena Bajcsy","","2016-01-05");
        p[10]= new Member(10,"Sarah Boysen","","2016-02-23");
        p[11]= new Member(11,"Polly Matzinger","","2016-03-05");
        p[12]= new Member(12,"Niels Bohr","","2016-05-04");
        p[13]= new Member(13,"Wolfgang Pauli","","2016-05-28");
        p[0]= new Member(14,"Max Born","","2016-10-05");
        
        for(int i=0; i<14;i++){
            memberlist.add(p[i]);
        }
        PrintWriter writer = null; //writer for translating member information in a file
        try {
            File fileDescriptor = new File("members.txt"); //assigning a filename
            writer = new PrintWriter(fileDescriptor); //assigning a writer to write on file
            for(int i=0;i< p.length ; i++){ 
                writer.println(p[i].toString()); //adding what to write on the writer
            }
            
        } catch (FileNotFoundException e) {
            e.printStackTrace(); // Add error recovery here if needed
        } finally {
            if (writer != null) {
                writer.close(); // saving and closing the file
            }
        }
    }
    public void load(){ //loader for file
        Scanner reader = null; //assigning a reader for file
        try {
            memberlist.clear();
            File file = new File("members.txt"); //mentioning the file name
            reader = new Scanner(file);
            while (reader.hasNext()) { //check if the next line exists
                String record = reader.nextLine(); //scan the next line
                String[] val = record.split(","); //split the values with a comma decilimator
                try{
                    Member a = new Member(Integer.parseInt(val[0]),
                            val[1], val[2], val[3], val[4]); //crating an instance of a member with the obtained values
                    memberlist.add(a); // add the member to the array 
                }
                catch(ArrayStoreException ex1){
                    System.out.println("File at line " + memberlist.size() +1 +
                            " has insufficient variables"); //message if the reader failed to retrive a member info for having insufficient variables
                }
            }            
        } catch (FileNotFoundException e) {
            e.printStackTrace(); // message to show when the member file is missing
        } finally {
            if (reader != null) {
                reader.close(); // closing the file
            }
        }
        
    }
    public Member getMemberByIndex(int index){ //getter for member by index
        return memberlist.get(index);
    }
    public Member getMemberByID(int id){ //getter for member by id
        for(int i=0; i<memberlist.size(); i++){
            if(memberlist.get(i).get_id()==id)
                return memberlist.get(i);
        }
        return null;
    }
    public ArrayList<Member> getMemberByName(String name){ //getter for  member by name with case insensitive and partial value 
        ArrayList<Member> list = new ArrayList<>();
        for(int i=0; i<memberlist.size(); i++){
            if(memberlist.get(i).get_name().toLowerCase().contains(name.toLowerCase()))
                list.add(memberlist.get(i));            
        }
        if(list.size()>0)return list;
        else return null;
    }
    public ArrayList<Member> getMemberByJoinDate(String date){
        ArrayList<Member> list = new ArrayList<>();
        for(Member m: memberlist){
            if(m.get_joinDate().equals(date))
                list.add(m);
        }
        if(list.size()>0)return list;
        else return null;
    }
    public ArrayList<Member> getMemberByExpireDate(String date){ //getter for member by expire date
        ArrayList<Member> list = new ArrayList<>();
        for(Member m: memberlist){
            if(m.get_expireDate().equals(date))
                list.add(m);
        }
        if(list.size()>0)return list;
        else return null;
    }
    public ArrayList<Member> getExpiredMembers(){ //getter for member by expired status
        ArrayList<Member> list = new ArrayList<>();
        for(Member m: memberlist){
            if(m.isExpired())
                list.add(m);
        }
        if(list.size()>0)return list;
        else return null;
    }
    public void save(){ //method for saving the member file
        PrintWriter writer = null; //crating a writer
        try {
            File fileDescriptor = new File("members.txt"); //pointing the file name
            writer = new PrintWriter(fileDescriptor); 
            for(int i=0; i< memberlist.size(); i++){
                writer.println(memberlist.get(i).toString()); //giving writer the member information as separated by commas
            }
            
            

        } catch (FileNotFoundException e) {
            e.printStackTrace(); // Add error recovery here if needed
        } finally {
            if (writer != null) {
                writer.close(); //closing the file
            }
        }
        
    }
    
    
}
