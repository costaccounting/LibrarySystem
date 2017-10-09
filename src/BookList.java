/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Faiza
 */
public class BookList {

    private ArrayList<Book> booklist = new ArrayList<>();

    public ArrayList<Book> getBookList() {
        return this.booklist;
    }

    //Write a new file for books record.
    public void initialize() {
        Book b[] = new Book[16];
        b[0] = new Book(0, "Electrical Power Systems", "Sara Laurer", "Butterworth-Heinemann", "9780081011249", Subjects.REFERENCE);
        b[1] = new Book(1, "THE GREAT GATSBY", "F. Scott Fitzgerald", "", "2314555678123", Subjects.ENGLISH);
        b[2] = new Book(2, "Time and Methods in Environmental Interfaces Modelling", "Scott ", "Woodhead Publishing Series", "9780444639189", Subjects.REFERENCE);
        b[3] = new Book(3, "Microbiology and Molecular Diagnosis in Pathalogy", " ", "Elsevier", "9780128053515", Subjects.REFERENCE);
        b[4] = new Book(4, "Borderline", "Mishell Baker", "Elsevier", "9780128022515", Subjects.ENGLISH);
        b[5] = new Book(5, "Cloud Atlas", "David Mitchell", "Penguin books", "721345839452", Subjects.ENGLISH);
        b[6] = new Book(6, "Little Women", "Lousia May Alcot", "Penguin books", "45738135982", Subjects.ENGLISH);
        b[7] = new Book(7, "The Kite Runner", "Khaled Hosseini", "Penguin Books", "233845798245", Subjects.ENGLISH);
        b[8] = new Book(8, "Don Quixote", "Miguel De Cervantes", "Penguin Books", "239485761029", Subjects.FRENCH);
        b[9] = new Book(9, "Bad Science", "Ben Goldacre", "Elseiver", "453627189347", Subjects.SCIENCE);
        b[10] = new Book(10, "A Physics book: From the Big Bang to Quantum Resurrection", "Clifford A. Pickover", "Sterling", "9781402778612", Subjects.PHYSICS);
        b[11] = new Book(11, "Chemistry", "Steven Zumdahl and Susan Zumdahl", "Wiley", "9781111784928", Subjects.CHEMISTRY);
        b[12] = new Book(12, "The Geography Book", "Caroline Arnold", "Wiley", "9780471412366", Subjects.GOEGRAPHY);
        b[13] = new Book(13, "Calculus", "James Stewarts", "Indigo books", "987656573829", Subjects.MATHS);
        b[14] = new Book(14, "An introduction to Statistics learning", "Robert Tibshirani and Trevor Hastie", "Springer", "456372819374", Subjects.STATISTICS);
        b[15] = new Book(15, "A short history of Canada", "Desmond Morton", "Indigo books", "716253489123", Subjects.HISTORY);

        for (int i = 0; i < b.length; i++) {
            booklist.add(b[i]);
        }

        PrintWriter writer = null;
        try {
            File fileDescriptor = new File("books.txt");
            writer = new PrintWriter(fileDescriptor);

            for (int i = 0; i < booklist.size(); i++) {
                writer.println(booklist.get(i).toString());
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace(); // Add error recovery here if needed
        } catch (NullPointerException e) {
            System.out.println(e);
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    public void load() {
        Scanner reader = null;
        try {
            booklist.clear();
            File file = new File("books.txt");
            reader = new Scanner(file);
            while (reader.hasNext()) {
                String record = reader.nextLine();
                String[] val = record.split(",");
                try {
                    Book a = new Book(Integer.parseInt(val[0]),
                            val[1], val[2], val[3], val[4], Subjects.valueOf(val[5]));
                    booklist.add(a);
                } catch (ArrayStoreException ex1) {
                    System.out.println("File at line " + booklist.size() + 1
                            + " has insufficient variables");
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace(); // Add error recovery here if needed
        } finally {
            if (reader != null) {
                reader.close();
            }
        }

    }

    public boolean addBook(Book a) {
        for (int i = 0; i < booklist.size(); i++) {
            if (a.equals(booklist.get(i))) {
                return false;
            } else {
                booklist.add(a);
                return true;
            }

        }
        return false;
    }

    public void updateBook(Book a) {
        int index = -1;
        for (int i = 0; i < booklist.size(); i++) {
            if (a.equals(booklist.get(i))) {
                index = booklist.get(i).getId();
            }
        }
        if (index >= 0) {
            booklist.set(index, a);
        }
    }

    public void save() {
        PrintWriter writer = null;
        try {
            File fileDescriptor = new File("books.txt");
            writer = new PrintWriter(fileDescriptor);
            for (int i = 0; i < booklist.size(); i++) {
                writer.println(booklist.get(i).toString());
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace(); // Add error recovery here if needed
        } finally {
            if (writer != null) {
                writer.close();
            }
        }

    }

    public Book getBookByIndex(int index) {
        return booklist.get(index);
    }

    public Book getBookByID(int id) {
        for (int i = 0; i < booklist.size(); i++) {
            if (booklist.get(i).getId() == id) {
                return booklist.get(i);
            }
        }
        return null;
    }

    public ArrayList<Book> getBookByTitle(String name) {
        ArrayList<Book> list = new ArrayList<>();
        for (int i = 0; i < booklist.size(); i++) {
            if (booklist.get(i).getTitle().contains(name)) {
                list.add(booklist.get(i));
            }
        }
        if (list.size() > 0) {
            return list;
        } else {
            return null;
        }
    }

    public ArrayList<Book> getBookByAuthor(String name) {
        ArrayList<Book> list = new ArrayList<>();
        for (int i = 0; i < booklist.size(); i++) {
            if (booklist.get(i).getAuthor().contains(name)) {
                list.add(booklist.get(i));
            }
        }
        if (list.size() > 0) {
            return list;
        } else {
            return null;
        }
    }

    public ArrayList<Book> getBookByPublisher(String name) {
        ArrayList<Book> list = new ArrayList<>();
        for (int i = 0; i < booklist.size(); i++) {
            if (booklist.get(i).getPublisher().contains(name)) {
                list.add(booklist.get(i));
            }
        }
        if (list.size() > 0) {
            return list;
        } else {
            return null;
        }
    }

    public ArrayList<Book> getBookByISBN(String isbn) {
        ArrayList<Book> list = new ArrayList<>();
        for (int i = 0; i < booklist.size(); i++) {
            if (booklist.get(i).getISBN().contains(isbn)) {
                list.add(booklist.get(i));
            }
        }
        if (list.size() > 0) {
            return list;
        } else {
            return null;
        }
    }
    /********************************************Testing**********8
public static void  main(String[] args) {
      BookList booklist = new BookList();
      booklist.initialize();
     
      booklist.load(); 
      booklist.addBook(new Book(16,"A short history of Canada","Desmond Morton","Indigo books","456253489123",Subjects.HISTORY)); 
      booklist.save();
      ArrayList<Book> myBook = booklist.getBookList(); 
      //ArrayList<Book> myBook = booklist.getBookByISBN("457"); 
      for(int i=0; i<myBook.size(); i++)
      System.out.println(myBook.get(i).toString());
     }     
     
     */
     
}
