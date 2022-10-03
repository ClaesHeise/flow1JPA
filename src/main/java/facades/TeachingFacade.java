package facades;

import entities.Semester;
import entities.Student;
import entities.Teacher;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public Student assignSemester(long semesterId, long studentId){
        EntityManager em = emf.createEntityManager();
        Semester semester = em.find(Semester.class, semesterId);
        Student student = em.find(Student.class, studentId);
        em.getTransaction().begin();
        student.assignCurrentSemester(semester);
//        student.setCurrentsemester(semester);
//        em.merge(student);
        em.getTransaction().commit();
        em.close();
        return student;
    }

    public Teacher assignSemesterTeacher(long semesterId, long teacherId){
        EntityManager em = emf.createEntityManager();
        Semester semester = em.find(Semester.class, semesterId);
        Teacher teacher = em.find(Teacher.class, teacherId);
        em.getTransaction().begin();
        teacher.assignCurrentSemester(semester);
        em.getTransaction().commit();
        em.close();
        return teacher;
    }

    public Teacher removeSemesterTeacher(long semesterId, long teacherId){
        EntityManager em = emf.createEntityManager();
        Semester semester = em.find(Semester.class, semesterId);
        Teacher teacher = em.find(Teacher.class, teacherId);
        em.getTransaction().begin();
        teacher.removeCurrentSemester(semester);
        em.getTransaction().commit();
        em.close();
        return teacher;
    }

    public Semester changeSemesterNames(long semesterId, String name, String description){
        EntityManager em = emf.createEntityManager();
        Semester semester = em.find(Semester.class, semesterId);
        em.getTransaction().begin();
        semester.setName(name);
        semester.setDescription(description);
        em.getTransaction().commit();
        em.close();
        return semester;
    }

//    public List<Student> getSemesterStudents(long semesterId){
//        EntityManager em = emf.createEntityManager();
//        long semester = semesterId;
//        TypedQuery<Student> query = em.createQuery("SELECT s FROM Student s WHERE s.currentsemester = :semester", Student.class);
//        List<Student> results = query.getResultList();
//        em.close();
//        return results;
//    }

    public Set<Student> semesterStudents(long semesterId){
        EntityManager em = emf.createEntityManager();
        Semester semester = em.find(Semester.class, semesterId);
        if (semester != null){
            return semester.getStudents();
        }
        return null;

    }

    public List<Student> teachersStudents(long teacherId){
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Student> query = em.createQuery("SELECT s FROM Student s JOIN s.currentsemester " +
                    "sem JOIN sem.teachers t WHERE t.id = :id", Student.class);
            query.setParameter("id", teacherId);
            return query.getResultList();
        }
        finally{
            em.close();
        }
    }


    //Opgaver onsdag 31-08 nedenfor:
    //1
    public List<Student> firstNameStudents(String name){
        EntityManager em = emf.createEntityManager();
        try{
            TypedQuery<Student> query = em.createQuery("SELECT s From Student s WHERE s.firstname = :sName", Student.class);
            query.setParameter("sName", name);
            return query.getResultList();
        }
        finally {
            em.close();
        }
    }
    //2
    public List<Student> lastNameStudents(String name){
        EntityManager em = emf.createEntityManager();
        try{
            TypedQuery<Student> query = em.createQuery("SELECT s From Student s WHERE s.lastname = :sName", Student.class);
            query.setParameter("sName", name);
            return query.getResultList();
        }
        finally {
            em.close();
        }
    }
    //3
    public List<Student> numberOfStudentsForSemester(String semesterName){
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Student> query = em.createQuery("SELECT s FROM Student s JOIN s.currentsemester sem WHERE sem.name = :semester", Student.class);
            query.setParameter("semester", semesterName);

            return query.getResultList();
        }
        finally {
            em.close();
        }
    }
    //4
    public int numberStudentsForTeacher(long teacherId){
        return teachersStudents(teacherId).size();
    }
    //5
    public List<Long> teachesMostSemesters(){
        EntityManager em = emf.createEntityManager();
        try {
            //TypedQuery<Teacher> query = em.createQuery("SELECT t FROM Teacher t JOIN t.semesters sem", Teacher.class);
            List<Long> query = em.createQuery("SELECT cs, COUNT(sem) as cs FROM Teacher t JOIN t.semesters sem GROUP BY t ORDER BY cs desc", Long.class).getResultList();
            //System.out.println(query);
            //List<Teacher> teachers = query.getResultList();
            //query.setParameter("semester", semesterName);
            return query;
        }
        finally {
            em.close();
        }
    }

//    public List<Semester> fewestStudents(){
//        EntityManager em = emf.createEntityManager();
//        try {
//            Query query = em.createQuery("SELECT cs, COUNT(st) as cs From Semester s JOIN s.students st GROUP BY s ORDER BY cs asc");
//            List<Semester> list = query.getResultList();
//            System.out.println("Hello" + query.getClass());
//            System.out.println(query.getFirstResult());
//            System.out.println("Hello2 "+list.get(0).getId());
////            CriteriaBuilder cb = em.getCriteriaBuilder();
////            CriteriaQuery<Student> q = cb.createQuery(Student.class);
////            Root<Student> s = q.from(Student.class);
////            q.select(s);
////            q.orderBy(cb.asc(s.get("currentsemester_id")));
////
////            List<Student> st = q.get;
//            //query.getFirstResult();
//            //System.out.println(query.getResultList());
//            //List<Teacher> teachers = query.getResultList();
//            //query.setParameter("semester", semesterName);
//            //Semester semester = query.getResultList().get(0);
//            return list;
//        }
//        finally {
//            em.close();
//        }
//    }




//    public List<Student> getTeachersStudents(long teacherId){
//        List<Student> students = new ArrayList<>();
//        EntityManager em = emf.createEntityManager();
//        long teacher = teacherId;
//        TypedQuery<Teacher> query = em.createQuery("SELECT t FROM Teacher t", Teacher.class);
//        List<Teacher> results = query.getResultList();
//        Set<Semester> semesters = new LinkedHashSet<>();
//        semesters.addAll(results.stream().filter(teacherStream -> teacherId == teacherStream.getId()).findAny().orElse(null).getSemesters());
//        for(Semester s : semesters){
//            students.addAll(getSemesterStudents(s.getId()));
//        }
//        return students;
//    }



}
