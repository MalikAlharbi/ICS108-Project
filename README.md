# Registration System

This application serves as a registration system for courses and students. It provides a user-friendly interface with various functionalities to manage course registration and student details. The system allows users to view courses, view student details, save data, and perform search operations.


## Features

The registration system offers the following features:

### Courses Pane

- **View courses:** Opens the courses pane, where all the available courses are displayed in a ListView.
- **Next/Previous:** Allows navigating through the course list to view the details of the next or previous course.
- **Search:** Enables searching for a course by entering its ID. If the course is found, its information is displayed; otherwise, an appropriate message is shown.
- **Save:** Writes the arrays of courses and students into the output file named "Registration.dat".

### Students Pane

- **View student details:** Opens the students pane, where student IDs are displayed in a TextField and their registered courses are shown in a ListView.
- **Next/Previous:** Allows navigating through the student list to view the details of the next or previous student.
- **Drop:** Enables dropping a course for the current student. When clicked, the selected course is removed from the student's courses and added back to the available courses in the ComboBox. The availableSeats field is incremented by one.
- **Register:** Allows registering a course selected from the ComboBox for the current student. When clicked, the selected course is added to the student's courses and displayed in the ListView. The availableSeats field is decremented by one.
- **Search:** Enables searching for a student by entering their ID. If the student is found, their information is displayed; otherwise, an appropriate message is shown.
- **Save:** Writes the arrays of courses and students into the output file named "Registration.dat".




## Project Completion Date 

This project was completed in December 2021.
