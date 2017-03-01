package Models;

import StaticData.ExceptionMessages;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class Student {
    private String userName;
    private LinkedHashMap<String, Course> enrolledCourses;
    private LinkedHashMap<String, Double> marksByCourseName;

    public Student(String username) {
        this.setUserName(username);
        this.enrolledCourses = new LinkedHashMap<>();
        this.marksByCourseName = new LinkedHashMap<>();
    }

    public String getUserName() {
        return userName;
    }

    public Map<String, Course> getEnrolledCourses() {
        return Collections.unmodifiableMap(this.enrolledCourses);
    }

    public Map<String, Double> getMarksByCourseName() {
        return Collections.unmodifiableMap(this.marksByCourseName);
    }

    private void setUserName(String userName) {
        if(userName == null || userName.equals("")){
            throw new Exceptions.InvalidStringException();
        }
        this.userName = userName;
    }

    public void enrollInCourse(Course course){
        if(this.enrolledCourses.containsKey(course.getName())){
           throw new Exceptions.DuplicateEntryInStructureException(this.getUserName(), course.getName());
        }
        this.enrolledCourses.put(course.getName(), course);
    }

    public void setMarksInCourse(String courseName, int... scores){
        if(!this.enrolledCourses.containsKey(courseName)){
            throw new Exceptions.KeyNotFoundException();

        }

        if(scores.length > Course.NUMBER_OF_TASKS_ON_EXAM){
            throw new IllegalArgumentException(ExceptionMessages.INVALID_NUMBER_OF_SCORES);
        }
        double mark = calcuateMark(scores);
        this.marksByCourseName.put(courseName, mark);
    }

    private double calcuateMark(int[] scores) {
        double percentageOfSolvedExam = Arrays.stream(scores).sum() /
                (double) (Course.NUMBER_OF_TASKS_ON_EXAM * Course.MAX_SCORE_ON_EXAM_TASK) ;
        return percentageOfSolvedExam * 4 + 2;
    }

}
