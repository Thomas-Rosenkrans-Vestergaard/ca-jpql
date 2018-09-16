package com.tvestergaard.jpql.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Table(name = "TEACHER")
@NamedQueries({
        @NamedQuery(name = "Teacher.findAll", query = "SELECT t FROM Teacher t")
        , @NamedQuery(name = "Teacher.findById", query = "SELECT t FROM Teacher t WHERE t.id = :id")
        , @NamedQuery(name = "Teacher.findByFirstName", query = "SELECT t FROM Teacher t WHERE t.firstName = :firstname")
        , @NamedQuery(name = "Teacher.findByLastName", query = "SELECT t FROM Teacher t WHERE t.lastName = :lastname")})
public class Teacher implements Serializable
{

    private static final long                 serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private              Long                 id;
    @Column(name = "FIRSTNAME")
    private              String               firstName;
    @Column(name = "LASTNAME")
    private              String               lastName;
    @JoinTable(name = "teacher_semester", joinColumns = {
            @JoinColumn(name = "teachers_ID", referencedColumnName = "ID")}, inverseJoinColumns = {
            @JoinColumn(name = "teaching_ID", referencedColumnName = "ID")})
    @ManyToMany
    private              Collection<Semester> semesters;

    public Teacher()
    {
    }

    public Teacher(Long id)
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

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public Collection<Semester> getSemesters()
    {
        return semesters;
    }

    public void setSemesters(Collection<Semester> semesterCollection)
    {
        this.semesters = semesterCollection;
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
        if (!(object instanceof Teacher)) {
            return false;
        }
        Teacher other = (Teacher) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "Teacher[ id=" + id + " ]";
    }

}
