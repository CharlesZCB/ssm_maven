package com.cn.model;

import org.apache.solr.client.solrj.beans.Field;

import java.util.Date;

public class User {
    @Field("create_at")
    private Date create_at;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Field("id")
    private String id;
    public Date getCreate_at() {
        return create_at;
    }

    public void setCreate_at(Date create_at) {
        this.create_at = create_at;
    }

    @Field("name")
    private String name;

    @Field("age")
    private int age;

    @Field("sex")
    private int sex;

    public User(String id,String name, int age, int sex,Date create_at) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.create_at = create_at;
    }

    public User() {
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                "name='" + name + '\'' +
                ", age=" + age +
                ", sex=" + sex +
                ", create_at=" + create_at +

                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }
}
