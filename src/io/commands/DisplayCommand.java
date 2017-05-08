package io.commands;

import  annotations.Alias;
import  annotations.Inject;
import  dataStructures.SimpleSortedList;
import  exceptions.InvalidInputException;
import  io.OutputWriter;
import models. Course;
import models.  Student;
import  repository.StudentsRepository;

import java.util.Comparator;
@Alias("display")
public class DisplayCommand extends Command {

    @Inject
    private StudentsRepository repository;

    public DisplayCommand(String input, String[] data) {
        super(input, data);
    }

    @Override
    public void execute() throws Exception {
        String[] data = this.getData();
        if(data.length != 3) {
            throw new InvalidInputException(this.getInput());
        }

        String entityDisplay = data[1];
        String sortType = data[2];

        if(entityDisplay.equalsIgnoreCase("students")) {
            Comparator<  Student> studentComparator = this.createStudentComparator(sortType);
            SimpleSortedList<  Student> list = this.repository.getAllStudentsSorted(studentComparator);
            OutputWriter.writeMessageOnNewLine(
                    list.joinWith(System.lineSeparator())
            );
        } else if (entityDisplay.equalsIgnoreCase("courses")) {
            Comparator< Course> courseComparator = this.createCourseComparator(sortType);
            SimpleSortedList< Course> list = this.repository.getAllCoursesSorted(courseComparator);
            OutputWriter.writeMessageOnNewLine(
                    list.joinWith(System.lineSeparator())
            );
        } else {
            throw new InvalidInputException(this.getInput());
        }
    }

    private Comparator< Course> createCourseComparator(String sortType) {
        if(sortType.equalsIgnoreCase("ascending")) {
            return Comparable::compareTo;
        } else if (sortType.equalsIgnoreCase("descending")) {
            return Comparator.reverseOrder();
        } else {
            throw new  InvalidInputException(this.getInput());
        }
    }

    private Comparator<Student> createStudentComparator(String sortType) {
        if(sortType.equalsIgnoreCase("ascending")) {
            return Comparable::compareTo;
        } else if (sortType.equalsIgnoreCase("descending")) {
            return Comparator.reverseOrder();
        } else {
            throw new InvalidInputException(this.getInput());
        }
    }
}
