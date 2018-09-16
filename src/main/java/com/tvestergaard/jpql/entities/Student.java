package com.tvestergaard.jpql.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "STUDENT")
@NamedQueries({
        @NamedQuery(name = "Student.findAll", query = "SELECT s FROM Student s")
        , @NamedQuery(name = "Student.findById", query = "SELECT s FROM Student s WHERE s.id = :id")
        , @NamedQuery(name = "Student.findByFirstName", query = "SELECT s FROM Student s WHERE s.firstName = :firstName")
        , @NamedQuery(name = "Student.findByLastName", query = "SELECT s FROM Student s WHERE s.lastName = :lastName")})
public class Student implements Serializable
{

    private static final long     serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private              Long     id;
    @Column(name = "FIRSTNAME")
    private              String   firstName;
    @Column(name = "LASTNAME")
    private              String   lastName;
    @JoinColumn(name = "CURRENTSEMESTER_ID", referencedColumnName = "ID")
    @ManyToOne
    private              Semester semester;

    public Student()
    {
    }

    public Student(String firstName, String lastName, Semester semester)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.semester = semester;
    }


    public Student(Long id)
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

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstname)
    {
        this.firstName = firstname;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastname)
    {
        this.lastName = lastname;
    }

    public Semester getSemester()
    {
        return semester;
    }

    public void setSemester(Semester semester)
    {
        this.semester = semester;
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
        if (!(object instanceof Student)) {
            return false;
        }
        Student other = (Student) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "Student[ id=" + id + " ]";
    }

}
