package project;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class StudentsPane extends BorderPane {
	ObservableList<String> items;
	int studentIndex = 0; // used to show students and reg courses

	// Declaring needed buttons
	Button backBtn = new Button("Back");
	Button regBtn = new Button("Register");
	Button dropBtn = new Button("Drop");
	Button searchBtn = new Button("Search");
	Button previousBtn = new Button("<Previous");
	Button nextBtn = new Button("Next>");

	// Declaring needed text fields & labels
	TextField tfStudentsID = new TextField();
	ListView<String> listCourses = new ListView<>();
	ComboBox<String> cbNotRegCourses = new ComboBox<>();

	Label studentIDLbl = new Label("Student ID");
	Label regCoursesLbl = new Label("Registered Courses");
	Label notRegCoursesLbl = new Label("Not Registered Courses");

	// Declaring alert to show the user appropriate message for wrong input
	Alert alert = new Alert(Alert.AlertType.WARNING);

	// Creating a Student and Course objects
	Student selectedStudent = null;
	Course checkCourse;

	// This Method returns a Student id when we enter an id in the id textfield
	private Student getStudentById(String student_id) {
		Student student = null;
		for (int i = 0; i < CommonClass.studentList.size(); i++) {
			Student tempStudent = CommonClass.studentList.get(i);

			if (tempStudent.getStudID().equals(student_id)) {
				student = tempStudent;
				// System.out.println(student.toString());
				break;
			}
		}

		return student;
	}

	// This method is used when transitioning between students in the search,next
	// and prev buttons
	private void showStudentData() {
		listCourses.getItems().clear();
		cbNotRegCourses.getItems().clear();

		if (selectedStudent == null) {
			return;
		}

		for (int i = 0; i < selectedStudent.getCourses().size(); i++) {
			String course_id = selectedStudent.getCourses().get(i).getCourseID();
			listCourses.getItems().add(course_id);
		}
		for (int i = 0; i < CommonClass.courseList.size(); i++) {
			Course tempCourse = CommonClass.courseList.get(i);

			boolean flag = false;

			for (int j = 0; j < selectedStudent.getCourses().size(); j++) {
				String course_id = selectedStudent.getCourses().get(j).getCourseID();
				if (course_id.equals(tempCourse.getCourseID())) {
					flag = true;
					break;
				}
			}
			if (!flag && tempCourse.getAvailableSeats() > 0) {
				cbNotRegCourses.getItems().add(tempCourse.getCourseID());
			}
		}
	}

	// to show appropriate message for the user
	private void showAlert(String strContent) {
		alert.setContentText(strContent);
		alert.show();
	}

	// delete a regstired course when using the drop button
	private void deleteCourse(String course_id) {
		if (selectedStudent == null) {
			showAlert("Please select student first.");
			return;
		}

		for (int i = 0; i < CommonClass.courseList.size(); i++) {
			Course course = CommonClass.courseList.get(i);

			if (course.getCourseID().equals(course_id)) {
				selectedStudent.getCourses().remove(course);
				int availableSeat = course.getAvailableSeats();
				course.setAvailableSeats(++availableSeat);

				listCourses.getItems().remove(listCourses.getSelectionModel().getSelectedItem());
				break;
			}
		}

		showStudentData();
	}

	// this method is declared to add the course selected from the not reg courses
	// in the combobox
	private void insertCouse(String course_id) {
		if (selectedStudent == null) {
			showAlert("Please select student first.");
			return;
		}

		for (int i = 0; i < CommonClass.courseList.size(); i++) {
			Course course = CommonClass.courseList.get(i);

			if (course.getCourseID().equals(course_id)) {
				selectedStudent.getCourses().add(course);
				int availableSeat = course.getAvailableSeats();
				course.setAvailableSeats(--availableSeat);

				cbNotRegCourses.getItems().remove(cbNotRegCourses.getSelectionModel().getSelectedItem());
				break;
			}
		}

		showStudentData();
	}

	// Search method to Associate with search button
	private void search() {
		if (tfStudentsID.getText().equals("")) {
			showAlert("Please enter student id");
			tfStudentsID.setFocusTraversable(true); // used for nodes to respond to key events

			selectedStudent = null;
			return;
		}

		String student_id = tfStudentsID.getText();
		Student student = getStudentById(student_id);

		if (student == null) {

			showAlert("There is no student with this id");
			selectedStudent = null;
			listCourses.getItems().clear();
			cbNotRegCourses.getItems().clear();
			return;
		}

		selectedStudent = student;

		showStudentData();
	}

	// StudentPane constructor
	public StudentsPane() {
		// setting the design of the window

		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
		grid.setHgap(10);
		grid.setVgap(10);

		grid.add(studentIDLbl, 0, 0);
		grid.add(tfStudentsID, 1, 0);
		grid.add(regCoursesLbl, 0, 1);
		grid.add(listCourses, 1, 1);
		grid.add(notRegCoursesLbl, 0, 2);
		grid.add(cbNotRegCourses, 1, 2);

		cbNotRegCourses.setMaxWidth(300);

		HBox buttonsBox = new HBox();
		buttonsBox.setSpacing(10);
		buttonsBox.getChildren().addAll(backBtn, previousBtn, nextBtn, regBtn, dropBtn, searchBtn);
		buttonsBox.setAlignment(Pos.BOTTOM_CENTER);
		buttonsBox.setPadding(new Insets(10, 10, 10, 10));
		setBottom(buttonsBox);

		setCenter(grid);

		// Declaring EventHandlers for each button
		backBtn.setOnAction(e -> {
			CommonClass.mainScene.setRoot(new MainPane());
			System.out.println("Back");
		});

		dropBtn.setOnAction(e -> {
			String selectedItem = listCourses.getSelectionModel().getSelectedItem();

			if (selectedItem == null) {
				showAlert("Please select registered course.");
				return;
			}

			String selectedCourseId = selectedItem;

			deleteCourse(selectedCourseId);

		});

		regBtn.setOnAction(e -> {

			String selectedItem = cbNotRegCourses.getSelectionModel().getSelectedItem();

			if (selectedItem == null) {
				showAlert("Please select course.");
				return;
			}

			String selectedCourseId = selectedItem;
			insertCouse(selectedCourseId);
		});

		searchBtn.setOnAction(e -> {
			search();
		});

		tfStudentsID.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.getKeyCode("Enter")) {
				search();
			}
		});

		previousBtn.setOnAction(e -> {
			int selectedId = CommonClass.studentList.size();

			if (selectedStudent == null) {
				selectedStudent = CommonClass.studentList.get(CommonClass.studentList.size() - 1);
			} else {
				for (int i = 0; i < CommonClass.studentList.size(); i++) {
					Student student = CommonClass.studentList.get(i);

					if (selectedStudent.getStudID().equals(student.getStudID())) {
						selectedId = i;
						break;
					}
				}

				int nextIndex = selectedId - 1;
				if (nextIndex < 0) {
					nextIndex = CommonClass.studentList.size() - 1;
				}

				selectedStudent = CommonClass.studentList.get(nextIndex);
			}

			tfStudentsID.setText(selectedStudent.getStudID());
			showStudentData();
		});

		nextBtn.setOnAction(e -> {
			int selectedId = -1;

			if (selectedStudent == null) {
				selectedStudent = CommonClass.studentList.get(0);
			} else {
				for (int i = 0; i < CommonClass.studentList.size(); i++) {
					Student student = CommonClass.studentList.get(i);

					if (selectedStudent.getStudID().equals(student.getStudID())) {
						selectedId = i;
						break;
					}
				}

				int nextIndex = selectedId + 1;
				if (nextIndex >= CommonClass.studentList.size()) { // if the last student is reached , it will be reset
																	// to the first when next is pressed
					nextIndex = 0;
				}

				selectedStudent = CommonClass.studentList.get(nextIndex);
			}

			tfStudentsID.setText(selectedStudent.getStudID());
			showStudentData();
		});
	}
}