package facades;

import entities.Semester;
import entities.Student;
import entities.Teacher;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

public class TeachingFacade
{
    private static TeachingFacade instance;
    private static EntityManagerFactory emf;

    private TeachingFacade()
    {
    }

    public static TeachingFacade getInstance(EntityManagerFactory _emf)
    {
        if (instance == null)
        {
            emf = _emf;
            instance = new TeachingFacade();
        }
        return instance;
    }

    // ***** Facademetoder

    public List<Student> getStudents()
    {
        EntityManager em = emf.createEntityManager();

        try
        {
            TypedQuery<Student> query = em.createQuery("SELECT s FROM Student s", Student.class);
            return query.getResultList();
        }
        finally
        {
            em.close();
        }
    }

    public Student createStudent(String firstName, String lastName)
    {
        EntityManager em = emf.createEntityManager();
        Student newStudent = new Student(firstName, lastName);
        em.getTransaction().begin();
            em.persist(newStudent);
        em.getTransaction().commit();
        em.close();
        return newStudent;
    }

    public Semester createSemester(String name, String description)
    {
        EntityManager em = emf.createEntityManager();
        Semester newSemester = new Semester(name, description);
        em.getTransaction().begin();
        em.persist(newSemester);
        em.getTransaction().commit();
        em.close();
        return newSemester;
    }

    public Teacher createTeacher(String firstName, String lastName)
    {
        EntityManager em = emf.createEntityManager();
        Teacher newTeacher = new Teacher(firstName, lastName);
        em.getTransaction().begin();
        em.persist(newTeacher);
        em.getTransaction().commit();
        em.close();
        return newTeacher;
    }





}
