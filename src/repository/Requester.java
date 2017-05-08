package repository;

import   dataStructures.SimpleSortedList;
import   models.Course;
import   models.Student;

import java.util.Comparator;

public interface Requester {
    void getStudentMarkInCourse(String courseName, String studentName);

    void getStudentsByCourse(String courseName);

    SimpleSortedList<Course> getAllCoursesSorted(Comparator<Course> cmp);

    SimpleSortedList<Student> getAllStudentsSorted(Comparator<Student> cmp);

}
