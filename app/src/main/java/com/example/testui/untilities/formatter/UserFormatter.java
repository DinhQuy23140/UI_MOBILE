package com.example.testui.untilities.formatter;

import com.example.testui.model.User;

public class UserFormatter {
    public static User format(User user) {
        if (user == null) {
            user = new User();
        }

        if (user.getFullname() == null) user.setFullname("-");
        if (user.getEmail() == null) user.setEmail("-");
        if (user.getPhone() == null) user.setPhone("-");
        if (user.getDob() == null) user.setDob("");
        if (user.getGender() == null) user.setGender("-");
        if (user.getRole() == null) user.setRole("student");
        if (user.getAddress() == null) user.setAddress("-");

        return user;
    }
}
