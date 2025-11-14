package dev.vorstu.entity;

import jakarta.persistence.*;

@Table(name = "students")
@Entity
public class Student {

    public Student() {}

    public Student(Student student) {
        this(student.id, student.fio, student.group, student.phoneNumber);
    }

    public Student(Long id, String fio, String group, String phoneNumber) {
        this(fio, group, phoneNumber);
        this.id = id;
    }

    public Student(String fio, String group, String phoneNumber) {
        this.fio = fio;
        this.group = group;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return fio + " " + group + " " + phoneNumber + " " + id;
    }

    public String getFio() { return fio; }

    public String getGroup() { return group; }

    public String getPhoneNumber() { return  phoneNumber; }

    public Long getId() {
        return id;
    }

    public void setFio(String fio) { this.fio = fio; }

    public void setGroup(String group) { this.group = group; }

    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public void setId(Long id) { this.id = id; }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String fio = null;

    @Column(name="group_of_students")
    private String group = null;

    private String phoneNumber = null;
}
