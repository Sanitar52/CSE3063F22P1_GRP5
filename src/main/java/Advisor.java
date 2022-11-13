import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Advisor extends Student {
    private int advisorId;
    private String fName;

    private String lName;

    private List<String> studentsLooking;

    public void setfName(String fName){ this.fName = fName;}

    public void setlName(String lName){ this.lName = lName;}

    public void setStudentsLooking(List<String> studentsLooking){
        this.studentsLooking = studentsLooking;
    }

    String getfName() { return fName; }

    String getlName() { return lName; }

    List<String> getStudentsLooking() { return studentsLooking; }

    public void advisorControl(ArrayList<String> chosenClasses, Student student){
        Scanner scanner = new Scanner(System.in);
        String userPromt;
        String reason;
        //TODO
        // student.setRejectedList();
        // Buraya rejectedList yaparken ilk eleman rejected course ismi diğeri sebep şeklinde yapın rejectedListWithReasons = [CSE1142, zaman uyuşmuyor, CSE 3033, kota dolu, CSE3063, kota çok az] gibi
        // ondan sonra student.changeCurrentCourses(approvedList, rejectedListWithReasons) en sonunda yapın
        // En son olarak hoca burada manuel olarak değilde dersleri otomatik approve/reject yapsın demiş. O yüzden mantığınıza göre uydurun.

        for (int i=0; i<chosenClasses.size(); i++){

            System.out.println("Do you accept course: " + chosenClasses.get(i) + "from student: " + student.getfName() + " " + student.getlName());

            userPromt= scanner.nextLine();

            if (userPromt.equals("Yes")){
                ;
            }
            else if(userPromt.equals("No")){

                System.out.println("Why do you reject?");
                reason=scanner.nextLine();
                System.out.println(chosenClasses.get(i) + " Course is rejected. The reason is " + reason);
                chosenClasses.remove(i);
                i--;
            }
        }
    }
}