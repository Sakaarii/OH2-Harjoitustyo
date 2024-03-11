package com.example.oh2_harjoitustyo;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;


import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Interface extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Master
        BorderPane MasterPane = new BorderPane();

        // Initialize password list object
        PasswordList pwlist = new PasswordList();
        try {
            FileInputStream fileIn = new FileInputStream("pwdata.dat");
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            try {
                Object o = objectIn.readObject();
                if (o instanceof PasswordList){
                    PasswordList saved = (PasswordList) o;
                    pwlist.setPWList(saved.getPWList());
                }
                objectIn.close();
            }catch (ClassNotFoundException e){
                System.out.println("Tehdaan uusi lista");
            }
        }catch (IOException e){
            System.out.println("Tehdaan uusi tiedosto");
        }

        // Initialize generator object with some default values
        Generator generator = new Generator(12,true,true,true);

        // Bottom:
        HBox Bottom = new HBox();
        Button Generate = new Button("Generate");
        Button Save = new Button("Save");
        Button Clip = new Button("Clip");
        Button Book = new Button("PWBook");

        Bottom.getChildren().addAll(Generate,Save,Clip,Book);

        // Center
        StackPane Center = new StackPane();
        Label Password = new Label("Generate a password!");
        Password.setFont(Font.font(18));

        Center.getChildren().addAll(Password);


        // Right
        VBox Right = new VBox();
        RadioButton Capital = new RadioButton("Capital");
        RadioButton Symbol = new RadioButton("Symbol");
        RadioButton Numbers = new RadioButton("Numbers");
        TextField Length = new TextField("12");
        Length.setPrefWidth(20);
        Right.setSpacing(5);
        Right.setPadding(new Insets(5,5,5,5));
        Length.setAlignment(Pos.BASELINE_CENTER);

        // scene
        Scene scene = new Scene(MasterPane, 500, 150);

        DateFormat dateFormat = new SimpleDateFormat("dd/MM");

        // Save
        Save.setOnAction(event ->{
            StackPane stackPane = new StackPane();
            TextField tf = new TextField();
            Label pw2 = new Label(generator.getPassword());
            pw2.setFont(Font.font(16));
            Button confirm = new Button("Confirm");
            tf.setMaxWidth(200);
            tf.setAlignment(Pos.BASELINE_CENTER);
            pw2.setTranslateY(-30);
            confirm.setTranslateY(30);

            stackPane.getChildren().addAll(pw2, tf, confirm);

            // Making new window for password tagging
            Scene scene1 = new Scene(stackPane,300,100);
            Stage stage1 = new Stage();
            stage1.setTitle("Tag the password");
            stage1.setScene(scene1);
            stage1.show();

            // confirm button adds password to list with tag
            confirm.setOnAction(actionEvent -> {
                pwlist.addToList(new String[]{generator.getPassword(),tf.getText(), dateFormat.format(generator.getDate())});
                System.out.println(pwlist.Print());
                stage.show();
                stage1.close();
            });

            // Makes sure that new windows stays on top
            stage1.setAlwaysOnTop(true);

            // When new window is closed old window comes back
            stage1.setOnCloseRequest(windowEvent ->{
                stage.show();
            });

            // Hides old window to make visual look less cluttered
            stage.hide();
        });



        // Opening password book
        Book.setOnAction(event->{
            BorderPane pane = new BorderPane();
            VBox passwords = new VBox();

            Scene scene2 = new Scene(pane,500, pwlist.AmountOfElements()*45);
            Stage stage2 = new Stage();


            for (String[] o : pwlist.getPWList()){
                StackPane stack = new StackPane();
                stack.setAlignment(Pos.BASELINE_LEFT);

                Label tag = new Label("("+o[1]+")");
                Label pass = new Label(o[0]);
                Label date = new Label(o[2]);
                Button delete = new Button("DEL");


                delete.setTranslateX(450);
                pass.setTranslateX(80);
                tag.setTranslateX(80);
                date.setTranslateY(8);
                date.setTranslateX(25);
                pass.setTranslateY(15);
                delete.setTranslateY(10);

                date.setFont(Font.font(12));
                tag.setFont(Font.font(12));
                pass.setFont(Font.font(15));

                stack.getChildren().addAll(date,tag, pass, delete);
                passwords.getChildren().add(stack);

                pass.setOnMouseClicked(ClickEvent->{
                    ClipboardContent content = new ClipboardContent();
                    content.putString(pass.getText());
                    Clipboard.getSystemClipboard().setContent(content);
                    System.out.println("Copied to clip: " + pass.getText());
                });

                delete.setOnAction(deleteEvent ->{
                    passwords.getChildren().remove(stack);
                    pwlist.removeFromList(o);
                    stage2.setHeight(stage2.getHeight()-45);
                });
            }

            passwords.setSpacing(15);
            passwords.setPadding(new Insets(2,2,2,2));
            pane.setLeft(passwords);

            stage2.setTitle("Password Book");
            stage2.setScene(scene2);
            stage2.show();

            stage2.setOnCloseRequest(windowEvent ->{
                stage.show();
            });

            stage2.setAlwaysOnTop(true);

            stage.hide();
        });


        Right.getChildren().addAll(Length,Capital,Numbers,Symbol);

        MasterPane.setBottom(Bottom);
        MasterPane.setRight(Right);
        MasterPane.setCenter(Center);


        // When a password is generated it checks booleans of radio buttons and length
        Generate.setOnAction(event ->{
            generator.setUseCapital(Capital.isSelected());
            generator.setUseNumbers(Numbers.isSelected());
            generator.setUseSymbols(Symbol.isSelected());
            generator.setLength(Integer.parseInt(Length.getText()));
            generator.generatePassword();
            Password.setText(generator.getPassword());
        });

        // Creates a ClipboardContent object and puts the current password into systems clipboard
        Clip.setOnAction(event ->{
            ClipboardContent content = new ClipboardContent();
            content.putString(generator.getPassword());
            Clipboard.getSystemClipboard().setContent(content);
        });


        stage.setTitle("Password Generator");
        stage.setScene(scene);
        stage.show();

        stage.setOnCloseRequest(CloseEvent->{
            try {
                FileOutputStream fileOut = new FileOutputStream("pwdata.dat");
                ObjectOutputStream objectsOut = new ObjectOutputStream(fileOut);
                objectsOut.writeObject(pwlist);
                objectsOut.close();
            }catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

    }

    public static void main(String[] args) {
        launch();
    }
}