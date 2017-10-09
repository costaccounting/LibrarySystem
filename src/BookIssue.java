
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author Owner
 */
public class BookIssue {
    private int _issueID; //an id for each issue
    private Book _book; // variable for a book
    private Member _member; //variable for a member
    private BookList _booklist = new BookList(); //local list of books
    
    //getters for variables
    public int getIssueID(){return _issueID;}
    public Book getBook(){return _book;}
    public Member getMember(){return _member;}
    
    public BookIssue(){
        MainController.memberlist.load();
        MainController.booklist.load();
    }
    public boolean rentBook(int id, int bookID, int MemberID){
         _book = MainController.booklist.getBookByID(bookID);
        if(_book != null){ 
            if (!_book.isRented() && !_book.isReferneceOnly()){
                _issueID = id;
                _member = MainController.memberlist.getMemberByID(MemberID);
                _book.bookRented(); // Change the rented status in book object
                
                _book.setIssueDate(LocalDate.now().toString());
                
                MainController.booklist.updateBook(_book); //Update the booklist so that it reflects the book is rented
                MainController.booklist.save();
                return true;
            }
        }
        return false;
    }
    //@return int 0 means success, negative value means not success, positive value is number of over due days
    public int reRentBook(int bookID){
      _book = MainController.booklist.getBookByID(bookID);
        if(_book != null){ 
            if(_book.reIssue()==0){
            MainController.booklist.updateBook(_book);
            MainController.booklist.save();
            return 0; 
            } else
                return _book.overDueDays();
        }else
            return -1;
    }
    //@return int 0 means success, negative value means not success, positive value is number of over due days
    public int returnBook(int bookID){
         _book = MainController.booklist.getBookByID(bookID);
         int OverDueDays = -1;
        if(_book != null){ 
            OverDueDays = _book.bookReturn();
            MainController.booklist.updateBook(_book); //Update the booklist so that it reflects the book is rented
            MainController.booklist.save();
        }
        return OverDueDays;
    }
    public String toString(){ //basic tostring as commaseparated values 
        return _issueID+","+_book.toString()+","+_member.toString();
    }
}
