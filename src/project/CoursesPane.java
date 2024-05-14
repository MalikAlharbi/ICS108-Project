package project;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

public class CoursesPane extends StackPane {

	static int currentCourse = 0;

	public CoursesPane() {
		StackPane mainPane = new StackPane();
		BorderPane pane = new BorderPane();
		ScrollPane coursesScroller = new ScrollPane();

		// Adding Courses

		ListView<String> courses = new ListView<>(FXCollections.observableArrayList(CourseFields.coursesCode()));
		courses.setPrefSize(100, 240);
		courses.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		coursesScroller.setContent(courses);

		pane.setMaxHeight(1000);
		pane.setMaxWidth(1000);
		pane.setLeft(coursesScroller);

		// Adding Students
		pane.setRight(CourseFields.studentView(currentCourse));
		pane.setTop(CourseFields.studentCounter(currentCourse));
		// Course Fields//
		CourseFields.courseField(currentCourse);

		// Adding buttons
		HBox buttonsPane = new HBox();
		Button backButton = (new Button("Back"));
		Button previousButton = (new Button("< Previous"));
		Button nextButton = (new Button("Next >"));
		Button searchButton = (new Button("Search >"));
		buttonsPane.getChildren().addAll(backButton, previousButton, nextButton, searchButton);
		buttonsPane.setPadding(new Insets(11, 15, 19, 23));

		buttonsPane.setAlignment(Pos.CENTER);
		pane.setBottom(buttonsPane);
		// Action for mouse click in course//
		courses.setOnMouseClicked(e -> {
			currentCourse = courses.getSelectionModel().getSelectedIndex();
			CourseFields.courseSetter(currentCourse);
			pane.setRight(CourseFields.studentView(currentCourse));
			pane.setTop(CourseFields.studentCounter(currentCourse));

		});
		// Action for click in course//
		courses.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.DOWN) {
				// Using % to reset the counter at the last index//
				currentCourse = (currentCourse + CommonClass.courseList.size() + 1) % CommonClass.courseList.size();
				;
			} else if (e.getCode() == KeyCode.UP) {
				// Using % to reset the counter while going to the last index//
				currentCourse = (currentCourse + CommonClass.courseList.size() - 1) % CommonClass.courseList.size();

			}
			CourseFields.courseSetter(currentCourse);
			pane.setRight(CourseFields.studentView(currentCourse));
			pane.setTop(CourseFields.studentCounter(currentCourse));

		});
		// Action for back//
		backButton.setOnAction(e -> {

			CommonClass.mainScene.setRoot(new MainPane());
			System.out.println("Back");
		});

		// Action for previous//
		previousButton.setOnAction(e -> {
			currentCourse = (currentCourse + CommonClass.courseList.size() - 1) % CommonClass.courseList.size();
			CourseFields.courseSetter(currentCourse);
			pane.setRight(CourseFields.studentView(currentCourse));
			pane.setTop(CourseFields.studentCounter(currentCourse));

		});
		// Action for next//
		nextButton.setOnAction(e -> {
			currentCourse = (currentCourse + 1) % CommonClass.courseList.size();
			CourseFields.courseSetter(currentCourse);
			pane.setRight(CourseFields.studentView(currentCourse));
			pane.setTop(CourseFields.studentCounter(currentCourse));

		});
		// Action for search//

		searchButton.setOnAction(e -> {
			String searchFor = CourseFields.courseID.getText();
			boolean found = false;
			for (int i = 0; i < CourseFields.coursesCode().size(); i++) {
				if (searchFor.equals(CourseFields.coursesCode().get(i))) {
					CoursesPane.currentCourse = i;
					CourseFields.courseSetter(currentCourse);
					pane.setRight(CourseFields.studentView(currentCourse));
					pane.setTop(CourseFields.studentCounter(currentCourse));
					found = true;
					break;
				}
			}

			if (found == false) {
				CourseFields.emptyCourseSetter();
				pane.setRight(new ScrollPane());
				pane.setTop(new Label(""));

			}

			;

		});

		getChildren().addAll(pane, CourseFields.courseField(currentCourse));
		setMaxSize(550, 270);

	}

}
