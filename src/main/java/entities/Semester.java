package entities;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "SEMESTER")
public class Semester
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "NAME")
    private String name;

    @OneToMany(mappedBy = "currentsemester")
    private Set<Student> students = new LinkedHashSet<>();

    @ManyToMany
    @JoinTable(name = "TEACHER_SEMESTER",
            joinColumns = @JoinColumn(name = "SEMESTER_ID"),
            inverseJoinColumns = @JoinColumn(name = "TEACHER_ID"))
    private Set<Teacher> teachers = new LinkedHashSet<>();

    public Semester()
    {
    }

    public Semester(String description, String name)
    {
        this.description = description;
        this.name = name;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Set<Student> getStudents()
    {
        return students;
    }

    public void setStudents(Set<Student> students)
    {
        this.students = students;
    }

    public Set<Teacher> getTeachers()
    {
        return teachers;
    }

    public void setTeachers(Set<Teacher> teachers)
    {
        this.teachers = teachers;
    }



}