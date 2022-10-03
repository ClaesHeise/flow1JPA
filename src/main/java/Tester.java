import entities.Semester;
import entities.Student;
import entities.Teacher;
import facades.TeachingFacade;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class Tester
{
    public static void main(String[] args)
    {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
        TeachingFacade teachingFacade = TeachingFacade.getInstance(emf);

        List<Student> studentList = teachingFacade.getStudents();

        for (Student student : studentList)
        {
            System.out.println(student.toString());
        }

        // Create new student

//        System.out.println("****** Test at vi kan oprette en ny Student:");
//        Student newStudent = teachingFacade.createStudent("Jens","Jensen");
//        System.out.println(newStudent);

        //teachingFacade.assignSemester(3, 6);

        //teachingFacade.assignSemesterTeacher(3, 2);

        //teachingFacade.removeSemesterTeacher(3,3);

        //teachingFacade.changeSemesterNames(1, "Magic class", "Hogwarts");

//        List<Student> studentList2 = teachingFacade.getSemesterStudents(3);
//        System.out.println("Students by semester 3:");
//        for (Student student : studentList2)
//        {
//            System.out.println(student.toString());
//        }

        System.out.println();
//        List<Student> studentList3 = teachingFacade.teachersStudents(1);
//        System.out.println("Teachers students:");
//        for (Student student : studentList3)
//        {
//            System.out.println(student.toString());
//        }

        //List<Teacher> teacher = teachingFacade.teachesMostSemesters();
        //long teacher = teachingFacade.teachesMostSemesters();
//        List<Long> teacher = teachingFacade.teachesMostSemesters();
//        System.out.println("teach"+teacher.get(0));
//        for(Teacher t : teacher){
//            System.out.println(t.getId());
//        }

        System.out.println();
//        List<Semester> semesters = teachingFacade.fewestStudents();
        //List<Integer> semesters = teachingFacade.fewestStudents();


//            System.out.println(semesters.get(0).getId());
        teachingFacade.assignSemester(1, 9);


        List<Student> studentList2 = teachingFacade.getStudents();

        for (Student student : studentList2)
        {
            System.out.println(student.toString());
        }
        emf.close();
    }
}
