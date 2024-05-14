package project;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class MainPane extends BorderPane {
	Label registrationSystemText = new Label("Registration System");
	Button viewCourseButton = (new Button("View Course"));
	Button viewStudentButton = (new Button("View Student"));
	Button saveButton = (new Button("Save"));

	public MainPane() {
		HBox buttonsBox = new HBox();
		buttonsBox.setSpacing(10);
		registrationSystemText.setFont(Font.font("Times New Roman", FontWeight.NORMAL, FontPosture.REGULAR, 50));
		buttonsBox.getChildren().addAll(viewCourseButton, viewStudentButton, saveButton);
		buttonsBox.setAlignment(Pos.BOTTOM_CENTER);
		setCenter(registrationSystemText);
		setBottom(buttonsBox);

		viewCourseButton.setOnAction(e -> {
			// Changing the current scene to the course pane//
			CommonClass.mainScene.setRoot(new CoursesPane());
			System.out.println("Courses");

		});

		viewStudentButton.setOnAction(e -> {
			// Changing the current scene to the student pane//
			CommonClass.mainScene.setRoot(new StudentsPane());
			System.out.println("Students");
		});

		saveButton.setOnAction(e -> {
			try (FileOutputStream fos = new FileOutputStream(new File("res\\Registration.dat"));
					ObjectOutputStream oos = new ObjectOutputStream(fos);) {
				oos.writeObject(CommonClass.courseList);
				oos.writeObject(CommonClass.studentList);
				oos.close();
				System.out.println("Saved");

			}

			catch (FileNotFoundException ex) {
				System.out.println(ex.getMessage());
			}

			catch (IOException ex) {
				System.out.println(ex.getMessage());
			}
		});

	}

}
