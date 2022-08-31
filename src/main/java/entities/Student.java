package entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "STUDENT")
public class Student
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "FIRSTNAME")
    private String firstname;

    @Column(name = "LASTNAME")
    private String lastname;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CURRENTSEMESTER_ID")
    private Semester currentsemester;

    public Student()
    {
    }

    public Student(String firstName, String lastName)
    {
        this.firstname = firstName;
        this.lastname = lastName;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getFirstname()
    {
        return firstname;
    }

    public void setFirstname(String firstname)
    {
        this.firstname = firstname;
    }

    public String getLastname()
    {
        return lastname;
    }

    public void setLastname(String lastname)
    {
        this.lastname = lastname;
    }

    public Semester getCurrentsemester()
    {
        return currentsemester;
    }

    public void setCurrentsemester(Semester currentsemester)
    {
        this.currentsemester = currentsemester;
    }

    @Override
    public String toString()
    {
        return "Student{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", currentsemester="  + currentsemester +
                '}';
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        Student student = (Student) o;
        return getId().equals(student.getId());
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(getId());
    }
}