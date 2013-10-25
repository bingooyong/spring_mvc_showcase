package org.n3r.web.entity;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

public class AddressInfo extends BaseEntity {

    private String id;

    @Size(min = 2, max = 30, message = "请输入正确长度 [2-30] 的姓名")
    private String name;
    @NotEmpty
    @Email(message = "请输入正确的邮箱")
    private String email;
    @Size(min = 11, max = 11, message = "请输入正确长度 [11] 的手机号码")
    private String phone;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
