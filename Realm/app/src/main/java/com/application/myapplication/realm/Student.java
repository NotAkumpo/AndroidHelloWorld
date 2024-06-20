package com.application.myapplication.realm;

import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Student extends RealmObject {

    @PrimaryKey
    private String uuid = UUID.randomUUID().toString();


    private String name;
    private String course;
    private String idNumber;
    private Integer age;
    private Boolean scholar;

    public Student(){}

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Boolean isScholar() {
        return scholar;
    }

    public void setScholar(boolean scholar) {
        this.scholar = scholar;
    }


    @Override
    public String toString() {
        return "Student{" +
                "uuid='" + uuid + '\'' +
                ", name='" + name + '\'' +
                ", course='" + course + '\'' +
                ", idNumber='" + idNumber + '\'' +
                ", age=" + age +
                ", scholar=" + scholar +
                '}';
    }
}
