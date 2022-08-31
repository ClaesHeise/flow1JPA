import entities.Student;
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

        System.out.println("****** Test at vi kan oprette en ny Student:");
        Student newStudent = teachingFacade.createStudent("Jens","Jensen");
        System.out.println(newStudent);

        emf.close();
    }
}
