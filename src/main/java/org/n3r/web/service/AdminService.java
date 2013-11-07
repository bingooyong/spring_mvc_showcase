package org.n3r.web.service;

import org.n3r.eql.Eql;
import org.n3r.web.entity.Admin;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    public Admin findAdmin(String username) {
        return new Eql().selectFirst("findAdmin").params(username).returnType(Admin.class).execute();
    }
}
