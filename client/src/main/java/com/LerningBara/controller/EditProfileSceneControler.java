package com.LerningBara.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;

import javax.imageio.ImageIO;

import com.LerningBara.app.App;

import UserData.User;
import UserData.UserData;
import ImageData.ImageData;
import connection.Message;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
//import javafx.scene.control.ImageView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import java.awt.image.BufferedImage;

public class EditProfileSceneControler {
    @FXML
    private Label email;
    @FXML
    private Label username;
    @FXML
    private Label emailLabel;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label passLabel;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField oldPassField;
    @FXML
    private PasswordField newPassField;

    @FXML
    public void initialize() {
        /*
         * Initializes username and email labels with user's data
         */
        App app = App.getInstance();
        email.setText(app.user.getEmail());
        username.setText(app.user.getUsername());
    }

    public void changeEmail() {
        /*
         * Gets new email from user
         * Checks if the requirements to change email are met
         * Changes email in the database
         */

        //Set empty text in unused labels
        passLabel.setText("");
        usernameLabel.setText("");

        User curr_user = App.getInstance().user; //Save User object of current App instance


        String emailStr = emailField.getText(); //Get text from user

        //Check if the field is empty and print warning
        if (emailStr.equals("")) {
            emailLabel.setStyle("-fx-text-fill: red");
            emailLabel.setText("Email cannot be empty."); //Show hint
            return;
        }

        //Initialize UserData object with new email
        UserData usr = new UserData(curr_user.getUsername(), "", emailStr, curr_user.getId());
        App.getInstance().client.sendMessage("Update user", usr); //Send message to server
        System.out.println("Waiting for server response");
        Message messageReceived = App.getInstance().client.getOneRecivedObject(); //Recieve message
        Object obj = messageReceived.getObject(); //Get Object from message

        //Check if the update in database was successfull and inform user
        if ((Boolean) obj) {
            App.getInstance().user.setAttributes(curr_user.getId(), curr_user.getUsername(), emailStr); //Set new user in current App instance
            email.setText(emailStr);
            emailLabel.setStyle("-fx-text-fill: blue");
            emailLabel.setText("Email changed."); //Inform user about successful change
        } else {
            emailLabel.setStyle("-fx-text-fill: red");
            emailLabel.setText("Database failure."); //Inform user that the update has failed
        }

    }

    public void changeUsername() {
        /*
         * Gets new username from user
         * Checks if the requirements to change username are met
         * Changes email in the database
         */

        //Set empty text in unused labels
        passLabel.setText("");
        emailLabel.setText("");

        String usernameStr = usernameField.getText(); //Get text from user

        Boolean db_resp = false; //Initialize Boolean variable for database response

        UserData usr = new UserData(usernameStr); //Initialize UserData object with new username

        //Check if new username exists in database
        App.getInstance().client.sendMessage("User exists", usr); //Send message to server
        System.out.println("Waiting for server response");
        Message messageReceived = App.getInstance().client.getOneRecivedObject(); //Recieve message
        Object obj = messageReceived.getObject(); //Get Object from message
        if (obj instanceof Boolean) {
            db_resp = (Boolean) obj; //Save database response
        }

        //Check if username is empty or already taken
        if (usernameStr.equals("")) {
            usernameLabel.setStyle("-fx-text-fill: red");
            usernameLabel.setText("Username cannot be empty."); //Show hint
            return;
        } else if (db_resp) {
            usernameLabel.setStyle("-fx-text-fill: red");
            usernameLabel.setText("Username already taken."); //Show hint
            return;
        }

        User curr_user = App.getInstance().user; //Save User object of current App instance
        //Initialize Userdata with new username
        usr = new UserData(usernameStr, "", curr_user.getEmail(), curr_user.getId());

        //Send update request
        App.getInstance().client.sendMessage("Update user", usr); //Send message to server
        System.out.println("Waiting for server response");
        messageReceived = App.getInstance().client.getOneRecivedObject(); //Recieve message from server
        obj = messageReceived.getObject(); //Get Object from message

        //Check if the update in database was successfull and inform user
        if ((Boolean) obj) {
            App.getInstance().user.setAttributes(curr_user.getId(), usernameStr, curr_user.getEmail());
            username.setText(usernameStr);
            usernameLabel.setStyle("-fx-text-fill: blue");
            usernameLabel.setText("Username changed."); //Inform user about successful change
        } else {
            usernameLabel.setStyle("-fx-text-fill: red");
            usernameLabel.setText("Database failure."); //Inform user that the update has failed
        }
    }

    public void changePassword() {
        /*
         * Gets new password from user
         * Checks if the requirements to change password are met
         * Changes email in the database
         */

        //Set empty text in unused labels
        usernameLabel.setText("");
        emailLabel.setText("");

        User curr_user = App.getInstance().user; //Save User object of current App instance

        String passStr = newPassField.getText(); //Get text from user

        Boolean db_resp = false; //Initialize Boolean variable for database response

        //Initialize UserData object with old password
        UserData usr = new UserData(curr_user.getUsername(), oldPassField.getText());

        //Check old password in database
        App.getInstance().client.sendMessage("Check password", usr); //Send message to server
        System.out.println("Waiting for server response");
        Message messageReceived = App.getInstance().client.getOneRecivedObject(); //Recieve message from server
        Object obj = messageReceived.getObject(); //Get Object from message

        if (obj instanceof Boolean) {
            db_resp = (Boolean) obj; //save db response
        }

        //check if new password is empty or old password is incorrect
        if (passStr.equals("")) {
            passLabel.setStyle("-fx-text-fill: red");
            passLabel.setText("New password cannot be empty."); //Show hint
            return;
        } else if (!db_resp) {
            passLabel.setStyle("-fx-text-fill: red");
            passLabel.setText("Incorrect password."); //Show hint
            return;
        }

        //Initialize Userdata with new password
        usr = new UserData(curr_user.getUsername(), passStr, curr_user.getEmail(), curr_user.getId());

        //Send update user request
        App.getInstance().client.sendMessage("Update user", usr); //Send message to server
        System.out.println("Waiting for server response");
        messageReceived = App.getInstance().client.getOneRecivedObject(); //Recieve message from server
        obj = messageReceived.getObject(); //Get object from message

        if ((Boolean) obj) {
            passLabel.setStyle("-fx-text-fill: blue");
            passLabel.setText("Password changed."); //Inform user about successful change
        } else {
            passLabel.setStyle("-fx-text-fill: red");
            passLabel.setText("Database failure."); //Inform user that update has failed
        }
    }

    public void uploadPicture() throws Exception {
        /*
         * Opens FileChooser with filters
         * Saves image to database
         */

        // Initialize new FileChooser
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose a picture"); //Set title
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg", "*.gif")); //Add filters
        File file = fileChooser.showOpenDialog(App.getInstance().getPrimaryStage()); //Open dialog window and save chosenfile

        //Check if any file has been chosen
        if (file != null) {
            Image image = new Image(file.toURI().toString()); //Initialize image with file in desired string format
            BufferedImage bufferedImage = javafx.embed.swing.SwingFXUtils.fromFXImage(image, null);  //Change image to BufferedImage

            //Convert buffered image to byte array
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream(); //Initialize byte stream
            ImageIO.write(bufferedImage, "png", outputStream); //Write image to output stream
            byte[] imageBytes = outputStream.toByteArray(); //Save byte array from output stream

            ImageData img = new ImageData(); //Initialize ImageData object
            img.map = imageBytes; //Set map attribute to image byte array
            img.username = App.getInstance().user.getUsername(); //Set username of current user

            //Set icon in database
            App.getInstance().client.sendMessage("Add profile icon", img); //Send message to server
            System.out.println("Waiting for server response");
            Message messageReceived = App.getInstance().client.getOneRecivedObject(); //Recieve message
            Object obj = messageReceived.getObject(); //Get object from message

            //Check if the update was successfull
            if ((Boolean) obj) {
                System.out.println("Image has been sent succesfully.");
            }

        } else {
            System.out.println("No file has been chosen.");
        }
    }

    public void goBack() throws Exception {
        /*
         * Sets root to the previous scene
         */
        App.setRoot("UserMenuScene");
    }
}
