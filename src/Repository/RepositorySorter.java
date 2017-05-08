package repository;

import io.OutputWriter;

import java.util.*;
import java.util.stream.Collectors;

public class RepositorySorter {
    public void printSortedStudents(HashMap<String, Double> studentsWithMarks,
                                           String comparisonType, Integer numberOfStudents) {
        Comparator<Map.Entry<String, Double>> comparator = (x, y) ->
                Double.compare(x.getValue(), y.getValue());

        List<String> sortedStudents = studentsWithMarks.entrySet().stream()
                .sorted(comparator)
                .limit(numberOfStudents)
                .map( x -> x.getKey())
                .collect(Collectors.toList());

        if(comparisonType.equals("descending")){
            Collections.reverse(sortedStudents);
        }

        for(String student : sortedStudents){
            OutputWriter.printStudent(student, studentsWithMarks.get(student));
        }
    }
}
