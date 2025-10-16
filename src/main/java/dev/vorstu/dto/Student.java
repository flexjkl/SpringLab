package dev.vorstu.dto;

import jakarta.persistence.*;

@Table(name = "students")
@Entity
public class Student {

    public Student() {}

    public Student(Long id, String fio, String group, String phoneNumber) {
        this(fio, group, phoneNumber);
        this.id = id;
    }

    public Student(String fio, String group, String phoneNumber) {
        this.fio = fio;
        this.group = group;
        this.phoneNumber = phoneNumber;
    }

    public String getFio() { return fio; }

    public String getGroup() { return group; }

    public String getPhoneNumber() { return  phoneNumber; }

    public void setFio(String fio) { this.fio = fio; }

    public void setGroup(String group) { this.group = group; }

    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public Long getId() {
        return id;
    }

    private String fio = null;

    @Column(name="group_of_students")
    private String group = null;

    private String phoneNumber = null;
}
