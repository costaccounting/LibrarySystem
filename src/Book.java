
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Faiza Jahanzeb
 */
public class Book {
    protected int _id; //Book ID as the primary key for book table
    protected String _title = ""; //Book title
    protected String _author = ""; // Author Name of the book
    protected String _publisher = ""; //Publisher of the book
    protected long _isbn= 0; // isbn for library record
    protected Subjects _subject = Subjects.UNSPECIFIED; // Subject-wise category of the book
    protected boolean _rented = false; //status for book is rented or inside the library
    protected boolean _isReferenceOnly = false; //status to indicate if the book is a reference
    private LocalDate _dateIssued = null; //status to indicate when the book is issued
    private LocalDate _dateDue = _dateIssued != null ? _dateIssued.plusDays((long)21):null; // status to indicate when the book is due
    private boolean _isOverDue = false; //booleanstatus for overdue
    
    public Book( int id, String title, String author, String publisher, String isbn, Subjects subject){ //complete constructor for book 
        _id = id;
        _title = title;
        _author = author;
        _publisher = publisher;
        _isbn = Long.parseLong(isbn);
        _subject = subject;
        _isReferenceOnly = (this._subject == Subjects.REFERENCE);
        
    }
    public Book(int id, String title, String isbn) //basic constructor for book  with id , title, and isbn
    {
        _id = id;
        _title = title;
        _isbn = Long.parseLong(isbn);
        
    }
    public Book(int id, String title){ //basic constructor for book with only title
        _id = id;
        _title = title;
        
    }
    public Book( int id, String title, String author, String publisher, 
            String isbn, Subjects subject,String dateIssued, 
            String dateDue, Boolean isRented){ // full constructor for book
        _id = id;
        _title = title;
        _author = author;
        _publisher = publisher;
        _isbn = Long.parseLong(isbn);
        _subject = subject;
        _rented = isRented;
        _isReferenceOnly = (this._subject == Subjects.REFERENCE);
        _dateIssued = str2Date(dateIssued);
        _dateDue = str2Date(dateDue);
        _isOverDue = LocalDate.now().isAfter(_dateDue);
    }
    
    //gettes for book
    public int getId(){return _id;}
    public String getTitle(){return _title;}
    public String getAuthor(){return _author;}
    public String getPublisher(){return _publisher;}
    public String getISBN(){return _isbn+"";}
    public String getSubject(){return _subject.toString();}
    public Boolean isRented(){return _rented;}
    public Boolean isReferneceOnly(){return _isReferenceOnly;}
    public LocalDate getIssueDate(){return _dateIssued;}
    public LocalDate getDueDate(){return _dateDue;}
    
   //setters for the book
    public void setTitle(String title){_title = title;}
    public void setAuthor(String author){_author = author;}
    public void setPublisher(String publisher){_publisher = publisher;}
    public void setISBN(String isbn){_isbn = Long.parseLong(isbn);}
    public void setSubject(Subjects subject){_subject = subject;}
    public void setIssueDate(String dateIssued){ _dateIssued = str2Date(dateIssued);}       
    public void bookRented(){_rented = true;}
    //public void bookReturned(){_rented = false;}
    
    public String toString(){ //tostring with separating values as comma
        return getId()+ "," + getTitle() + ","+
                getAuthor()+ "," +getPublisher()+","+ getISBN() + "," +
                getSubject();
    }
    public String toDisplay(){ //advanced tostring for more userfriendly format
        return "Book ID: "+getId()+ "\nTitle: " + getTitle() + "\nAuthor: "+
                getAuthor()+ "\nPublisher: " +getPublisher()+"\nISBN: "+ getISBN() + "\nSubject: " +
                getSubject()+"\n";
    }
    public boolean equals(Book book){ //comparing two books by id
        return (_id == book.getId() && (_isbn+"").contains(book.getISBN()));
    }
    public LocalDate str2Date(String date) {
        try {
            return LocalDate.parse(date);

        } catch (NumberFormatException e) {
            System.err.println("Date can not be converted in a usable format. Please enter"
                    + "a date in style of YYYY-MM-DD");

        }
        return null;
    }
    public int reIssue(){
        if(!_isOverDue){
        _dateDue = LocalDate.now().plusDays(7);
        _isOverDue = LocalDate.now().isAfter(_dateDue);
        _rented = true;
        return 0;
        }
        else
            return overDueDays();
    }
    
    //Calculates the number of OverDue days when book is returned.
    public int bookReturn(){
        _rented = false;
        return overDueDays();
    }
    public int overDueDays(){
            Period due;
        int dueDays = 0;
        if (_isOverDue){
            LocalTime startTime = LocalTime.now().truncatedTo(ChronoUnit.MINUTES);
            due = _dateDue.until(LocalDate.now());   
            dueDays = due.getDays();
        }
        return dueDays;
    }
    
}
