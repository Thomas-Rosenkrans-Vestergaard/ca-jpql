package com.tvestergaard.jpql.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Table(name = "SEMESTER")
@NamedQueries({
        @NamedQuery(name = "Semester.findAll", query = "SELECT s FROM Semester s")
        , @NamedQuery(name = "Semester.findById", query = "SELECT s FROM Semester s WHERE s.id = :id")
        , @NamedQuery(name = "Semester.findByDescription", query = "SELECT s FROM Semester s WHERE s.description = :description")
        , @NamedQuery(name = "Semester.findByName", query = "SELECT s FROM Semester s WHERE s.name = :name")})
public class Semester implements Serializable
{

    private static final long                serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private              Long                id;
    @Column(name = "DESCRIPTION")
    private              String              description;
    @Column(name = "NAME")
    private              String              name;
    @ManyToMany(mappedBy = "semesters")
    private              Collection<Teacher> teachers;
    @OneToMany(mappedBy = "semester")
    private              Collection<Student> students;

    public Semester()
    {
    }

    public Semester(Long id)
    {
        this.id = id;
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

    public Collection<Teacher> getTeachers()
    {
        return teachers;
    }

    public void setTeachers(Collection<Teacher> teacherCollection)
    {
        this.teachers = teacherCollection;
    }

    public Collection<Student> getStudents()
    {
        return students;
    }

    public void setStudents(Collection<Student> studentCollection)
    {
        this.students = studentCollection;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Semester)) {
            return false;
        }
        Semester other = (Semester) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "Semester[ id=" + id + " ]";
    }

}
