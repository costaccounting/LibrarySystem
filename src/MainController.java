/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author prodip
 */
public class MainController implements Initializable {
    static MemberList memberlist = new MemberList(); //static instance of MemberList
    static BookList booklist = new BookList(); // static instance of BookList

    @FXML
    private Button buttonMembers; //button to load member window
    
    @FXML
    private Button buttonBooks; //button to load books window
    
    @FXML
    private Button buttonExit; //exit button to close the program


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try{
            memberlist.load();
        }catch (Exception e){
            System.out.println("file loading error"); //loading member information on memberlist
        }
        buttonMembers.setOnMouseClicked((event) -> {
            onButtonLoadMembers(); //action for clicking on member button
        });
        buttonBooks.setOnMouseClicked((event) -> {
            onButtonLoadBooks(); //action for clicking on book button
        });
        buttonExit.setOnMouseClicked((event) -> {
            onExitButtonClicked(); //action for clicking on exit button
        });

    }

    public void onButtonLoadMembers() { // method that activates the window for members

        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("ViewMembers.fxml")); // the fxml file for members
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Stage secondStage = new Stage();
            secondStage.setTitle("Library Members");
            secondStage.setScene(scene);
            secondStage.initModality(Modality.APPLICATION_MODAL);
            secondStage.show();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
    public void onButtonLoadBooks(){ //method that brings on books window
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("ViewBooks.fxml")); //fxml file for book window
            Scene scene = new Scene(fxmlLoader.load(), 836, 593);
            Stage secondStage = new Stage();
            secondStage.setTitle("Library Books");
            secondStage.setScene(scene);
            secondStage.initModality(Modality.APPLICATION_MODAL);
            secondStage.show();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    private void onExitButtonClicked() {
        Stage stage = (Stage) buttonExit.getScene().getWindow();
        stage.close();         // closing the program
    }

}
