/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author prodip
 */
public class ViewMembersController implements Initializable {

    @FXML
    Button buttonSearch; // button for searching
    @FXML
    Button buttonClose; // button to close the window
    @FXML
    TextField txtField1; // text field for entering input
    @FXML
    VBox vBox1; // vbox is used to vertically arrange search results
    Button renew = new Button("Renew"); // button used to renew selected member
    Button book = new Button("Show rented books"); // button used to show rented book by one checked member

    private List<CheckBox> checkboxes = new ArrayList<>(); // varibale to store results

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        buttonSearch.setOnMouseClicked(
                (MouseEvent event) -> search() //action event for clicking search button
        );
        buttonClose.setOnMouseClicked(
                (MouseEvent event) -> buttonClosePressed()); //action event for clicking close button
        renew.setOnMouseClicked(
                (MouseEvent event) -> renew() //action event for clicking renew button
        );
        book.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                showBook(); //action event for loading book button

            }
        });

    }

    private void search() { // search method
        MainController.memberlist.load();
        checkboxes.clear();
        vBox1.getChildren().clear();
        int x; //variable to hold the integer data from textfield
        String s; //variable to hold the string data from textfield
        List<Member> m = new ArrayList<>(); //arraylist to store members coming out of result
        try {
            x = Integer.parseInt(txtField1.getText());

            m.add(MainController.memberlist.getMemberByID(x));
            checklist_generator(m); //generate checklist 
            vBox1.getChildren().add(renew);
            vBox1.getChildren().add(book);

        } catch (NumberFormatException ex) {
            if (txtField1.getText() != null) {
                try {
                    String disp = "";
                    s = txtField1.getText();
                    if (!s.toLowerCase().contains("expire")) //condition to see if the string in textfield does not have the string expire
                    {
                        m.addAll(MainController.memberlist.getMemberByName(s)); //find members by name 
                    } else {
                        m.addAll(MainController.memberlist.getExpiredMembers()); //find members by expired status
                    }
                    checklist_generator(m);

                    vBox1.getChildren().add(renew);
                    vBox1.getChildren().add(book);
                } catch (NullPointerException ex1) {

                }
            } else {

            }
        }
    }

    private void buttonClosePressed() { //method for button close
        Stage stage = (Stage) buttonClose.getScene().getWindow();
        stage.close();
    }

    private void renew() { // method for renewing membership from checked results
        for (int i = 0; i < checkboxes.size(); i++) {
            CheckBox x = checkboxes.get(i);
            if (x.isSelected()) {
                String s = x.getText();
                String records[] = s.split(",");
                MainController.memberlist.getMemberByID(Integer.parseInt(records[0])).renew();
            }

        }
        MainController.memberlist.save();//saving the changed member data in the textfile
        MainController.memberlist.load(); //loading the textfile with saved changes
        search(); //perform search again to show the changed work
    }

    private void showBook() { //method to bring up the list of books rented by the member
        try {
            Pane pop = new VBox(); //vbox for vertically allign results
            Scene scene = new Scene(pop, 400, 200); // new scene
            Stage secondStage = new Stage(); //new stage as popup
            secondStage.setTitle("Currently rented books");
            secondStage.setScene(scene);
            BooksRentedList booklist = new BooksRentedList();
            booklist.load();
            List<Book> books = new ArrayList<Book>();// arraylist to store book as a list

            for (int i = 0; i < checkboxes.size(); i++) {
                CheckBox x = checkboxes.get(i);

                if (x.isSelected()) {

                    String s = x.getText();
                    String records[] = s.split(",");
                    books = booklist.booksRented(MainController.memberlist.getMemberByID(
                            Integer.parseInt(records[0]))); // get list of books by searching member id
                    for (int j = 0; j < books.size(); j++) {
                        pop.getChildren().add(new Text(books.get(j).getTitle() + "\n"));
                    }
                }

            }
            secondStage.show(); //load the popup window
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void checklist_generator(List<Member> m) {
        for (int i = 0; i < m.size(); i++) {
            HBox box1 = new HBox();
            CheckBox ch1 = new CheckBox(m.get(i).toDisplay()); //new checkbox
            ch1.setId("cbox" + m.get(i).get_id()); //set an id for checkbox
            checkboxes.add(ch1); //checkbox added to the  arraylist
            box1.getChildren().addAll(ch1); 
            vBox1.getChildren().add(box1); //checkbox element added to the vertical display 
        }
    }

}
