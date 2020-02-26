package com.ra.course.com.stackoverflow.entity.implementations;

public class Test {
    public static void main(String[] args) {
        Member m = Member.builder().id(42L)
                .build();
        Admin a = Admin.builder().id(24L)
                .build();

        System.out.println(m.getId());
        System.out.println(a.getId());
    }
}
