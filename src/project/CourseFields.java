package project;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class CourseFields {

	static public TextField courseID = new TextField();
	static private final Label courseName = new Label();
	static private final Label courseDays = new Label();
	static private final Label courseLocation = new Label();
	static private final Label courseTime = new Label();
	static private final ComboBox<String> courseStatus = new ComboBox<>();

	public static ArrayList coursesCode() {
		ArrayList<String> courseCode = new ArrayList<>();
		for (int i = 0; i < CommonClass.courseList.size(); i++) {
			String code = CommonClass.courseList.get(i).getCourseID();
			courseCode.add(code);
		}
		return courseCode;
	}

	public static ScrollPane studentView(int currentCourse) {
		ScrollPane studentScroller = new ScrollPane();
		ArrayList<String> registeredStudents = new ArrayList<>();
		int counter = 0;
		for (int i = 0; i < CommonClass.studentList.size(); i++) {
			for (int j = 0; j < CommonClass.studentList.get(i).getCourses().size(); j++) {
				if (CommonClass.studentList.get(i).getCourses().get(j).getCourseID()
						.equals(CommonClass.courseList.get(currentCourse).getCourseID()) == true) {
					// If the student(s) course matches the current course add it to the ArrayList//
					String students = CommonClass.studentList.get(i).getStudID();
					registeredStudents.add(students);
					counter++;
				}

			}

		}
		ListView<String> studentsView = new ListView<>(FXCollections.observableArrayList(registeredStudents));
		studentsView.setPrefSize(100, 240);
		studentsView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		studentScroller.setContent(studentsView);
		return studentScroller;

	}

	public static HBox studentCounter(int currentCourse) {
		Label numOfStudents = new Label();
		int counter = 0;
		for (int i = 0; i < CommonClass.studentList.size(); i++) {
			for (int j = 0; j < CommonClass.studentList.get(i).getCourses().size(); j++) {
				if (CommonClass.studentList.get(i).getCourses().get(j).getCourseID()
						.equals(CommonClass.courseList.get(currentCourse).getCourseID()) == true) {
					// If the student course(s) matches the current course increase the counter//
					counter++;
				}

			}

		}

		numOfStudents.setText(counter + " Registered Students");
		HBox hbox = new HBox();
		hbox.getChildren().add(numOfStudents);
		hbox.setAlignment(Pos.CENTER);
		hbox.setPadding(new Insets(15, 0, 0, 0));
		return hbox;

	}

	public static GridPane courseField(int currentCourse) {

		GridPane grid = new GridPane();
		// Adding Course Field//
		grid.addRow(0, new Label("ID"), courseID);
		courseID.setText(CommonClass.courseList.get(currentCourse).getCourseID());
		grid.addRow(1, new Label("Name"), courseName);
		courseName.setText(CommonClass.courseList.get(currentCourse).getCourseName());
		grid.addRow(2, new Label("Days"), courseDays);
		courseDays.setText(CommonClass.courseList.get(currentCourse).getCourseDays());
		grid.addRow(3, new Label("Location"), courseLocation);
		courseLocation.setText(CommonClass.courseList.get(currentCourse).getCourseLocation());
		grid.addRow(4, new Label("Time"), courseTime);
		courseTime.setText(CommonClass.courseList.get(currentCourse).getCourseTime());
		grid.addRow(5, new Label("Status"), courseStatus);
		if (CommonClass.courseList.get(currentCourse).getAvailableSeats() > 0) {
			courseStatus.setValue("Open");
		} else {
			courseStatus.setValue("Closed");
		}
		grid.setPadding(new Insets(11, 15, 19, 23));
		grid.setHgap(5);
		grid.setAlignment(Pos.CENTER);

		grid.setMaxWidth(320);
		grid.setMaxHeight(120);
		return grid;

	}

	public static void courseSetter(int currentCourse) {
		// Changing the fields to the current chose course//
		courseID.setText(CommonClass.courseList.get(currentCourse).getCourseID());
		courseName.setText(CommonClass.courseList.get(currentCourse).getCourseName());
		courseDays.setText(CommonClass.courseList.get(currentCourse).getCourseDays());
		courseLocation.setText(CommonClass.courseList.get(currentCourse).getCourseLocation());
		courseTime.setText(CommonClass.courseList.get(currentCourse).getCourseTime());
		if (CommonClass.courseList.get(currentCourse).getAvailableSeats() > 0) {
			courseStatus.setValue("Open");
		} else {
			courseStatus.setValue("Closed");
		}

	}

	public static void emptyCourseSetter() {
		courseID.setText("Not Found");
		courseName.setText("-");
		courseDays.setText("-");
		courseLocation.setText("-");
		courseTime.setText("-");
		courseStatus.setValue("-");
	}

}
