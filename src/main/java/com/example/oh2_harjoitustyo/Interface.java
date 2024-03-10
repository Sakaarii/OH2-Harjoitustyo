package com.example.oh2_harjoitustyo;

import javafx.application.Application;
import javafx.geometry.Insets;
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


import java.io.IOException;

public class Interface extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Master
        BorderPane MasterPane = new BorderPane();

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
        Label Password = new Label("");
        Password.setFont(Font.font(18));

        Center.getChildren().addAll(Password);


        // Right
        VBox Right = new VBox();
        RadioButton Capital = new RadioButton("Capital");
        RadioButton Symbol = new RadioButton("Symbol");
        RadioButton Numbers = new RadioButton("Numbers");
        TextField Length = new TextField("12");
        Length.setPrefWidth(5);
        Right.setSpacing(5);
        Right.setPadding(new Insets(5,5,5,5));

        Right.getChildren().addAll(Length,Capital,Numbers,Symbol);

        MasterPane.setBottom(Bottom);
        MasterPane.setRight(Right);
        MasterPane.setCenter(Center);


        Generate.setOnAction(event ->{
            generator.setUseCapital(Capital.isSelected());
            generator.setUseNumbers(Numbers.isSelected());
            generator.setUseSymbols(Symbol.isSelected());
            generator.setLength(Integer.parseInt(Length.getText()));
            generator.generatePassword();
            Password.setText(generator.getPassword());
        });

        Clip.setOnAction(event ->{
            ClipboardContent content = new ClipboardContent();
            content.putString(generator.getPassword());
            Clipboard.getSystemClipboard().setContent(content);
        });


        // Scene
        Scene scene = new Scene(MasterPane, 500, 150);



        stage.setTitle("Password Generator");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}