package org.n3r.web.service;

import org.n3r.esql.Esql;
import org.n3r.web.entity.Admin;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    public Admin findAdmin(String username) {
        return new Esql().selectFirst("findAdmin").useSqlFile("/org/n3r/web/service/AddressInfoService.esql").params(username).returnType(Admin.class).execute();
    }
}
