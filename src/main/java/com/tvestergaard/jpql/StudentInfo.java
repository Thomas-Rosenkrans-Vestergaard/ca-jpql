package com.tvestergaard.jpql;

public class StudentInfo
{
    public String fullName;
    public long   studentId;
    public String classNameThisSemester;
    public String classDescription;

    public StudentInfo(String firstName, String lastName, long studentId, String classNameThisSemester, String classDescription)
    {
        this.fullName = firstName + " " + lastName;
        this.studentId = studentId;
        this.classNameThisSemester = classNameThisSemester;
        this.classDescription = classDescription;
    }
}
