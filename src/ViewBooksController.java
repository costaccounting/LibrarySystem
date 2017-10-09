/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Owner
 */
public class ViewBooksController implements Initializable {

    @FXML
    private TextField txtFieldBook;
    @FXML
    private TextField txtMember;
    @FXML
    private Label labelHeading;
    @FXML
    private Label lblRent;
    @FXML
    private Button btnSearchBooks;
    @FXML
    private Button btnClose;
    @FXML
    private Button btnClear;
    @FXML
    private Button btnRent;
    @FXML
    private TextArea txtSearchResult;
    @FXML
    private RadioButton optionISBN;
    @FXML
    private RadioButton optionID;
    @FXML
    private RadioButton optionTitle;
    @FXML
    private RadioButton optionAuthor;
    BooksRentedList _issueList = new BooksRentedList();
    int bookIndex = -1;
    int memberIndex = -1;
    Book book;
    Member member;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        _issueList.load();
    }

    @FXML
    protected void handleCloseButton(ActionEvent event) {
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }

    @FXML
    protected void handleClearButton(ActionEvent event) {
        txtSearchResult.setText("");
        labelHeading.setText("");
        lblRent.setText("");
        txtMember.setVisible(false);
        btnRent.setVisible(false);
    }

    @FXML
    protected void handleSearchButton(ActionEvent event) {
        MainController.booklist.load();
        String s;
        //int bookIndex;
        ArrayList<Book> books = new ArrayList<>();
        labelHeading.setText("BookID, Title, Author, Publisher, ISBN, Subject");

        if (optionID.isSelected()) {
            try {
                txtSearchResult.setText("");
                String disp = "";
                bookIndex = Integer.parseInt(txtFieldBook.getText());
                if (MainController.booklist.getBookByID(bookIndex) != null) {
                    book = MainController.booklist.getBookByID(bookIndex);
                    books.add(book);
                    for (int i = 0; i < books.size(); i++) {
                        disp += books.get(i).toDisplay() + '\n';
                        txtSearchResult.setText(disp);
                    }
                    if(_issueList.whoRented(book) != null)
                    {
                    lblRent.setText("Enter Member ID");
                    txtMember.setVisible(true);
                    btnRent.setVisible(true);
                    }
                } else {
                    txtSearchResult.setText("Sorry! No book match the specified criteria");
                }
            } catch (Exception ex1) {

            }
        } else if (optionISBN.isSelected()) {
            try {
                txtSearchResult.setText("");
                String disp = "";
                s = txtFieldBook.getText();
                books.addAll(MainController.booklist.getBookByISBN(s));
                for (int i = 0; i < books.size(); i++) {
                    disp += books.get(i).toDisplay() + '\n';
                    txtSearchResult.setText(disp);
                }

            } catch (Exception ex1) {

            }
        } else if (optionTitle.isSelected()) {
            try {
                txtSearchResult.setText("");
                String disp = "";
                s = txtFieldBook.getText();
                books.addAll(MainController.booklist.getBookByTitle(s));
                for (int i = 0; i < books.size(); i++) {
                    disp += books.get(i).toDisplay() + '\n';
                    txtSearchResult.setText(disp);
                }

            } catch (Exception ex1) {

            }
        } else if (optionAuthor.isSelected()) {
            try {
                txtSearchResult.setText("");
                String disp = "";
                s = txtFieldBook.getText();
                books.addAll(MainController.booklist.getBookByAuthor(s));
                for (int i = 0; i < books.size(); i++) {
                    disp += books.get(i).toDisplay() + '\n';
                    txtSearchResult.setText(disp);
                }

            } catch (Exception ex1) {

            }
        }

    }

    @FXML
    protected void handleRentButton(ActionEvent event) {

        if (bookIndex > -1) {
            memberIndex = Integer.parseInt(txtMember.getText());
            member = MainController.memberlist.getMemberByID(memberIndex);
            if (member != null) {
                if (_issueList.addRecord(bookIndex, memberIndex)) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Book " + book.getTitle() + " is issued to " + member.get_name());
                    alert.show();
                    txtSearchResult.setText("");
                    labelHeading.setText("");
                    lblRent.setText("");
                    txtMember.setVisible(false);
                    btnRent.setVisible(false);
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Reference book can't be issued");
                    alert.show();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please give member ID between 1 and 15");
                alert.show();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Book " + book.getTitle() + " can't be issued to " + member.get_name());
            alert.show();
        }
    }

}
