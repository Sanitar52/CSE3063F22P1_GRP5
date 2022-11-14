import java.io.*;
import java.io.IOException;
import org.json.simple.parser.ParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
public class Main {
    public static void main(String[] args) throws IOException, ParseException {
        ObjectMapper objectMapper = new ObjectMapper();
        File advisorJsonFile = new File("inputs\\Advisors.json");
        Advisor[] advisors = objectMapper.readValue(advisorJsonFile, Advisor[].class);
        File lecturesJsonFile = new File("inputs\\lectures.json");
        Courses[] courses = objectMapper.readValue(lecturesJsonFile, Courses[].class);
        File studentsJsonFile = new File("inputs\\students.json");
        Student[] students = objectMapper.readValue(studentsJsonFile, Student[].class);
        Randomizer randomizer = new Randomizer();
        randomizer.setAvailableCoursesForEachStudent(students, courses, advisors);
        Transcript transcript = new Transcript();
        System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------");
        transcript.printTranscript(students);
    }
}