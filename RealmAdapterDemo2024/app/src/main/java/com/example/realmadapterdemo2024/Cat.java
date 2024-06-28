package com.example.realmadapterdemo2024;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Cat extends RealmObject
{
    @PrimaryKey
    private String uuid;

    private String name;
    private String breed;
    private boolean deceased;

    public Cat() {}

    public Cat(String uuid, String name, String breed, boolean deceased) {
        this.uuid = uuid;
        this.name = name;
        this.breed = breed;
        this.deceased = deceased;
    }

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

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public boolean isDeceased() {
        return deceased;
    }

    public void setDeceased(boolean deceased) {
        this.deceased = deceased;
    }

    @Override
    public String toString() {
        return "Cat{" +
                "uuid='" + uuid + '\'' +
                ", name='" + name + '\'' +
                ", breed='" + breed + '\'' +
                ", deceased=" + deceased +
                '}';
    }
}
