package Repository;

import IO.OutputWriter;
import StaticData.ExceptionMessages;
import StaticData.SessionData;
import Models.Course;
import Models.Student;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StudentsRepository {
    private boolean isDataInitialised = false;
    private LinkedHashMap<String, Course> courses;
    private LinkedHashMap<String, Student> students;

    private RepositoryFilter filter;
    private RepositorySorter sorter;

    public StudentsRepository(RepositoryFilter filter, RepositorySorter sorter){
        this.filter = filter;
        this.sorter = sorter;
    }

    public void loadData(String fileName){
        if(this.isDataInitialised){
            throw new IllegalArgumentException(ExceptionMessages.DATA_ALREADY_INITIALIZED);
        }

        this.students = new LinkedHashMap<String, Student>();
        this.courses = new LinkedHashMap<String, Course>();
        try {
            this.readData(fileName);
        } catch (IOException e) {
        OutputWriter.displayException(ExceptionMessages.ERROR_READING_FILE);
        }
    }

    public void unloadData(String fileName){
        if(!this.isDataInitialised){
            throw new IllegalArgumentException(ExceptionMessages.DATA_NOT_INITIALIZED);
        }

        this.students = null;
        this.courses = null;
        this.isDataInitialised = false;
    }

    private void readData(String fileName) throws IOException {
        String regex = "([A-Z][a-zA-Z#\\+]*_[A-Z][a-z]{2}_\\d{4})\\s+([A-Za-z]+\\d{2}_\\d{2,4})\\s([\\s0-9]+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher;

        String path = SessionData.currentPath + "\\" + fileName;
        List<String> lines = Files.readAllLines(Paths.get(path));


        for(String line : lines) {
            matcher = pattern.matcher(line);

            if (!line.isEmpty() && matcher.find()) {
                String courseName = matcher.group(1);
                String studentName = matcher.group(2);
                String scoresStr = matcher.group(3);

                try{
                    String[] splitScores = scoresStr.split("\\s+");
                    int[] scores = new int[splitScores.length];
                    for (int i = 0; i < splitScores.length; i++) {
                        scores[i] = Integer.parseInt(splitScores[i]);
                    }

                    if (Arrays.stream(scores).anyMatch(s -> s > 100 || s < 0)){
                        OutputWriter.displayException(ExceptionMessages.INVALID_SCORE);
                        continue;
                    }

                    if (scores.length > Course.NUMBER_OF_TASKS_ON_EXAM){
                        OutputWriter.displayException(ExceptionMessages.INVALID_NUMBER_OF_SCORES);
                        continue;
                    }

                    if(!this.students.containsKey(studentName)){
                        this.students.put(studentName, new Student(studentName));
                    }

                    if(!this.courses.containsKey(courseName)){
                        this.courses.put(courseName, new Course(courseName));
                    }

                    Course course = this.courses.get(courseName);
                    Student student = this.students.get(studentName);
                    student.enrollInCourse(course);
                    student.setMarksInCourse(courseName, scores);
                    course.enrollStudent(student);
                }catch (NumberFormatException nfe){
                    OutputWriter.displayException(nfe.getMessage());
                }
            }
        }

        isDataInitialised = true;
        OutputWriter.writeMessageOnNewLine("Data read.");
    }

    private boolean isQueryForCoursePossible(String courseName) {
        if(!isDataInitialised){
            OutputWriter.displayException(ExceptionMessages.DATA_NOT_INITIALIZED);
            return false;
        }

        if(!this.courses.containsKey(courseName)){
            OutputWriter.displayException(ExceptionMessages.NON_EXISTING_COURSE);
            return false;
        }
        return true;
    }

    private boolean isQueryForStudentPossible(String courseName, String studentName) {
        if(!isQueryForCoursePossible(courseName)){
            return false;
        }

        if(!this.courses.get(courseName).getStudentsByName().containsKey(studentName)){
            OutputWriter.displayException(ExceptionMessages.NON_EXISTING_STUDENT);
            return false;
        }
        return true;
    }

    public void getStudentMarkInCourse(String course, String student){
        if(!isQueryForStudentPossible(course,student)){
            return;
        }

       double mark = this.courses.get(course).getStudentsByName().get(student).getMarksByCourseName().get(course);
        OutputWriter.printStudent(student,mark);
    }

    public void getStudentsByCourse(String course){
        if(!isQueryForCoursePossible(course)){
            return;
        }

        OutputWriter.writeMessageOnNewLine(course + ":");
        for(Map.Entry<String, Student> student : this.courses.get(course).getStudentsByName().entrySet()) {
            this.getStudentMarkInCourse(course, student.getKey());
        }
    }

    public void filterAndTake(String courseName, String filter) {
        int studentsToTake = this.courses.get(courseName).getStudentsByName().size();
        filterAndTake(courseName, filter, studentsToTake);
    }

    public void filterAndTake(String courseName, String filter, int studentsToTake) {
        if (!isQueryForCoursePossible(courseName)) {
            return;
        }

        LinkedHashMap<String, Double> marks = new LinkedHashMap<>();
        for(Map.Entry<String, Student> entry : this.courses.get(courseName).getStudentsByName().entrySet()){
            marks.put(entry.getKey(), entry.getValue().getMarksByCourseName().get(courseName));
        }
        this.filter.printFilteredStudents(marks,filter,studentsToTake);
    }

    public void orderAndTake(String courseName, String orderType) {
        int studentsToTake = this.courses.get(courseName).getStudentsByName().size();
        orderAndTake(courseName, orderType, studentsToTake);
    }

    public void orderAndTake(String courseName, String orderType, int studentsToTake){
        if(!isQueryForCoursePossible(courseName)){
            return;
        }

        LinkedHashMap<String, Double> marks = new LinkedHashMap<>();
        for(Map.Entry<String, Student> entry : this.courses.get(courseName).getStudentsByName().entrySet()){
            marks.put(entry.getKey(), entry.getValue().getMarksByCourseName().get(courseName));
        }
        this.sorter.printSortedStudents(marks, orderType, studentsToTake);
    }


}
