package Models;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class Course {
    private String name;
    private LinkedHashMap<String, Student> studentsByName;
    public static final int NUMBER_OF_TASKS_ON_EXAM = 5;
    public static final int MAX_SCORE_ON_EXAM_TASK = 100;

    public Course(String name) {
        this.setName(name);
        this.studentsByName = new LinkedHashMap<>();
    }

    private void setName(String name) {
        if(name == null || name.equals("")){
            throw new Exceptions.InvalidStringException();
        }
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Map<String, Student> getStudentsByName() {
        return Collections.unmodifiableMap(studentsByName);
    }

    public void enrollStudent(Student student){
        if(this.studentsByName.containsKey(student.getUserName())){
            throw new Exceptions.DuplicateEntryInStructureException(student.getUserName(), this.getName());
        }
        this.studentsByName.put(student.getUserName(), student);
    }
}
