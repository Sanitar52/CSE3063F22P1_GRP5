import java.io.File;
import java.io.FileWriter;
import java.util.*;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import java.nio.file.Files;
import java.nio.file.Paths;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.io.IOException;


public class Student {
    //Attributes for students are here
    private int studentId;
    private String fName;
    private String lName;
    private int totalCredit;

    private int advisorId;
    private double gpa;
    private int currentYear;
    private int currentSemester;
    private List<String> currentSelectedCourses;
    private List<CompletedCourses> completedCourses;

    private List<String> availableCourses;
    private List<FailedCourses> failedCourses;
    //private int counter = 0;


    //Setters and Getters
    public int getCurrentYear() {
        return currentYear;
    }
    public int getAdvisorId() {
        return advisorId;
    }

    public void setAdvisorId(int advisorId) {
        this.advisorId = advisorId;
    }

    public void setCurrentYear(int currentYear) {
        this.currentYear = currentYear;
    }

    public int getCurrentSemester() {
        return currentSemester;
    }

    public void setCurrentSemester(int currentSemester) {
        this.currentSemester = currentSemester;
    }



    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public void setGPA(double gpa) {
        this.gpa = gpa;
    }
    public int getTotalCredit() {
        return totalCredit;
    }

    public void setTotalCredit(int totalCredit) {
        this.totalCredit = totalCredit;
    }

    public void setCurrentSelectedCourses(List<String> currentSelectedCourses) {
        this.currentSelectedCourses = currentSelectedCourses;
    }

    public void setCompletedCourses(List<CompletedCourses> completedCourses) {
        this.completedCourses = completedCourses;
    }

    public void setAvailableCourses(List<String> mandatoryCourses) {
        this.availableCourses = mandatoryCourses;
    }

    public List<FailedCourses> getFailedCourses() {
        return failedCourses;
    }

    public void setFailedCourses(List<FailedCourses> failedCourses) {
        this.failedCourses = failedCourses;
    }

    int getStudentId() { return studentId; }

    String getfName() { return fName; }

    String getlName() { return lName; }

    double getGPA() { return gpa; }

    List<String> getCurrentSelectedCourses() { return currentSelectedCourses; }

    List<CompletedCourses> getCompletedCourses() { return completedCourses; }
    //This method selects courses which are in availableCourses
    public void selectFromAvailableCourses(){
        ArrayList<String> coursesAdd = new ArrayList<>();
        //Here if we have more than 10 available courses we first check failed courses
        //and add them first, after that we randomly select other classes until it's size is 10
        if (availableCourses.size() > 10){
            for (String s : availableCourses){
                if (checkIfCourseFailed(s)) {
                    coursesAdd.add(s);
                }
            }
            //Here we check the list again and if it has less than 10 we add them until it becomes size 10
            //we add those to the list. until it's size is 10
            if (coursesAdd.size() < 10){
                for (String s : availableCourses) {
                    while (coursesAdd.size() != 10){
                        coursesAdd.add(s);
                    }
                }
                //then we update selectedCourses with this method
                this.currentSelectedCourses.addAll(coursesAdd);
            }
            //If however the size becomes more than 10
            //we remove some until it goes back to size 10 again.
            else if (coursesAdd.size() > 10){
                while (coursesAdd.size() != 10){
                    coursesAdd.remove(10);
                }
                this.currentSelectedCourses.addAll(coursesAdd);
            }
        }
        //If the size is not more than 10 we add every available course to selectedCourses attr.
        else {
            this.currentSelectedCourses.addAll(availableCourses);
        }


    }


    List<String> getAvailableCourses() { return availableCourses; }
    //This checks if the course is failed for this student
    //and returns true if failed or false if passed
    boolean checkIfCourseFailed(String courseCode){
        boolean check = false;
        for (CompletedCourses completedCourses1 : this.completedCourses){
            if (completedCourses1.getCourseName().equals(courseCode) && completedCourses1.getCourseGrade().equals("FF")) {
                check = true;
                break;
            }
        }
        return check;
    }
    //This method sends currentSelectedCourses to the corresponding advisor for this student
    public void sendToAdvisorSelectedClasses(Advisor[]advisors){
        for (Advisor advisor : advisors){
            if (this.advisorId == advisor.getAdvisorId()){
                advisor.advisorControl(currentSelectedCourses, this);
            }
        }
    }
    //This is a method the advisor calls
    //it changes selected courses depending on if each course is accepted or rejected and then updates it
    public void changeSelectedCourses(ArrayList<String> advisorApprovedCourses, ArrayList<String> advisorRejectedCoursesAndReasons){
        currentSelectedCourses.clear();
        currentSelectedCourses.addAll(advisorApprovedCourses);
        //TODO: decide what to do with rejected courses and their reasons
        //todo:createTranscript();

    }
    //this returns how many courses this student finished.
    public int getCompletedCourseNumber(){
        return completedCourses.size();
    }
    //We call this to calculate GPA of this student
    public void gpaCalculator(Courses[] courses){
        String letterAA = "4.00";
        double AA = Double.parseDouble(letterAA);
        String letterBA = "3.50";
        double BA = Double.parseDouble(letterBA);
        String letterBB = "3.00";
        double BB = Double.parseDouble(letterBB);
        String letterCB = "2.50";
        double CB = Double.parseDouble(letterCB);
        String letterCC = "2.00";
        double CC = Double.parseDouble(letterCC);
        String letterDC = "1.50";
        double DC = Double.parseDouble(letterDC);
        String letterDD = "1.00";
        double DD = Double.parseDouble(letterDD);
        String letterFD = "0.50";
        double FD = Double.parseDouble(letterFD);
        String letterFF = "0.00";
        double FF = Double.parseDouble(letterFF);

        int a = getCompletedCourseNumber();

        int credit;
        double sum = 0;
        int creditSum = 0;
        int transcriptCreditSum = 0;
        credit = 0;
        sum = 0;
        for (CompletedCourses completedCourses1 : completedCourses) {
            for (Courses courses1 : courses){
                if (completedCourses1.getCourseName().equals(courses1.getCourseCode())) {
                    credit = courses1.getCredit();
                    break;
                }
            }
            switch (completedCourses1.getCourseGrade()) {
                case "AA" -> {
                    sum = sum + (AA * credit);
                    creditSum = creditSum + credit;
                    transcriptCreditSum = transcriptCreditSum + credit;
                }
                case "BA" -> {
                    sum = sum + (BA * credit);
                    creditSum = creditSum + credit;
                    transcriptCreditSum = transcriptCreditSum + credit;
                }
                case "BB" -> {
                    sum = sum + (BB * credit);
                    creditSum = creditSum + credit;
                    transcriptCreditSum = transcriptCreditSum + credit;
                }
                case "CB" -> {
                    sum = sum + (CB * credit);
                    creditSum = creditSum + credit;
                    transcriptCreditSum = transcriptCreditSum + credit;
                }
                case "CC" -> {
                    sum = sum + (CC * credit);
                    creditSum = creditSum + credit;
                    transcriptCreditSum = transcriptCreditSum + credit;
                }
                case "DC" -> {
                    sum = sum + (DC * credit);
                    creditSum = creditSum + credit;
                    transcriptCreditSum = transcriptCreditSum + credit;
                }
                case "DD" -> {
                    sum = sum + (DD * credit);
                    creditSum = creditSum + credit;
                    transcriptCreditSum = transcriptCreditSum + credit;
                }
                case "FD" -> {
                    sum = sum + (FD * credit);
                    creditSum = creditSum + credit;
                }
                case "FF" -> {
                    sum = sum + (FF * credit);
                    creditSum = creditSum + credit;
                }
                default -> System.out.println("Hatali giris yaptiniz.");
            }
        }

        //  System.out.println("Total Credit : " + creditSum);
        double GPA = (int)((sum / creditSum) * 100.0) / 100.0 ;
        this.gpa = GPA;
       // System.out.println("gpa : " + gpa);
        // System.out.println(transcriptCreditSum);
        creditSum = 0;
        totalCredit = transcriptCreditSum;

    }



}

    /*public void setCompletedCoursesFromGivenArray(String courseName, String courseGrade, ArrayList<String> arrayOfCourses){
        CompletedCourses completedcoursesTest = new CompletedCourses();
        completedcoursesTest.setCourseName(courseName);
        completedcoursesTest.setCourseGrade(courseGrade);
        for (int i = 0 ; i < arrayOfCourses.size(); i ++){
            this.completedCourses.set(i, completedcoursesTest);
        }

        for (int i = 0; i < courseName.size(); i++){
            completedCourses.add(courseName.get(i))
        }
    }*/

