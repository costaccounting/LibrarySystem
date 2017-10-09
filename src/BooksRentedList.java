
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Owner
 */
public class BooksRentedList {

    private ArrayList<BookIssue> _booksIssuedlist = new ArrayList<>(); // local arraylist variable for rented book
    
    public ArrayList<BookIssue> getBooksRentedList(){return _booksIssuedlist;} //getter 
    
    public boolean addRecord( int bookID, int memberID){ //add a record of book issue with member and book id
        BookIssue bookissue = new BookIssue();
        if(bookissue.rentBook(_booksIssuedlist.size()+1, bookID, memberID)){ 
            _booksIssuedlist.add(bookissue);
            this.save(); //if rent was made
            return true; 
        }
        return false; // if rent is unsuccessful
    }

    public void initialize() { //initializing rent for a few dummy transactions
        BookIssue bookIssue[] = new BookIssue[3];
        bookIssue[0] = new BookIssue();
        if(bookIssue[0].rentBook(0, 2, 1))
            _booksIssuedlist.add(bookIssue[0]);

        bookIssue[1] = new BookIssue();
        if (bookIssue[1].rentBook(1, 9, 2))
            _booksIssuedlist.add(bookIssue[1]);

        bookIssue[2] = new BookIssue();
        if (bookIssue[2].rentBook(2, 14, 3))
            _booksIssuedlist.add(bookIssue[2]);

        PrintWriter writer = null; //creating a writer
        try {
            File fileDescriptor = new File("booksissued.txt"); //writing the records in bookisued.txt 
            writer = new PrintWriter(fileDescriptor);
            for (int i = 0; i < _booksIssuedlist.size(); i++) {
                writer.println(_booksIssuedlist.get(i).toString());
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace(); // Add error recovery here if needed
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    public void load() { //loading records from bookissued.txt
        Scanner reader = null;
        try {
            _booksIssuedlist.clear();
            File file = new File("booksissued.txt");
            reader = new Scanner(file);
            while (reader.hasNext()) {
                String record = reader.nextLine();
                String[] val = record.split(",");
                try {

                    BookIssue a = new BookIssue();
                    a.rentBook(Integer.parseInt(val[0]),
                            Integer.parseInt(val[1]), Integer.parseInt(val[7]));
                    _booksIssuedlist.add(a);
                } catch (ArrayStoreException ex1) {
                    System.out.println("File at line " + _booksIssuedlist.size() + 1
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

    public void save() { //saving changes in the local variable to bookissued file
        PrintWriter writer = null;
        try {
            File fileDescriptor = new File("booksissued.txt");
            writer = new PrintWriter(fileDescriptor);
            for (int i = 0; i < _booksIssuedlist.size(); i++) {
                writer.println(_booksIssuedlist.get(i).toString());
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace(); // Add error recovery here if needed
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }
    public Member whoRented(Book book){
        for(int i=0; i< _booksIssuedlist.size(); i++){
            if(_booksIssuedlist.get(i).getBook().equals(book)){
                return _booksIssuedlist.get(i).getMember();
            }
        }
        return null;
        
    }
    public ArrayList<Book> booksRented(Member member){ //getter for list of books rented by a perticular member
        ArrayList a1 = new ArrayList();
     
        for(int i=0; i< _booksIssuedlist.size(); i++){
            if(_booksIssuedlist.get(i).getMember().get_id() == (member.get_id())){
                a1.add(_booksIssuedlist.get(i).getBook());
            }
        }
        return a1;
    }
}
    /******************************TESTING**********************************************
     public static void main(String[] args)
    {
        BooksRentedList issueList = new BooksRentedList();
       Member m = new Member(2,"Lord Kelvin","","2014-11-25");
       // Member m1 = new Member(1,"Prodip Mitra","","2014-11-24");
       // Book b = new Book(2, "Time and Methods in Environmental Interfaces Modelling", "Scott ", "Woodhead Publishing Series", "9780444639189", Subjects.REFERENCE);
        Book b1 = new Book(15, "A short history of Canada", "Desmond Morton", "Indigo books", "716253489123", Subjects.HISTORY);
       // issueList.initialize();
        issueList.load();
        
        if(issueList.addRecord( 0, 2)){
            System.out.println("Record added");
        }else{
            System.out.println("Record not added");
        }
        ArrayList<BookIssue> myBookList = issueList._booksIssuedlist; 
      
      for(int i=0; i<myBookList.size(); i++)
        System.out.println(myBookList.get(i).toString());
      
        ArrayList<Book> books = issueList.booksRented(m);
        for(int i=0; i< books.size(); i++)
        System.out.println(books.get(i).toString());
        System.out.println("Book "+b1.toString()+" is issued to: "+ issueList.whoRented(b1));
    }
******************************************************************************************/


