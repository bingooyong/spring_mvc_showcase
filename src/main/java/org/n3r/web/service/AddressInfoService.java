package org.n3r.web.service;

import java.util.List;

import org.n3r.esql.Esql;
import org.n3r.web.entity.AddressInfo;
import org.springframework.stereotype.Service;

@Service
public class AddressInfoService {

    public AddressInfo findAddressInfo(String id) {
        return new Esql().selectFirst("findAddressInfo").params(id).returnType(AddressInfo.class).execute();
    }

    public List<AddressInfo> findAllAddressInfo() {
        return new Esql().select("findAllAddressInfo").returnType(AddressInfo.class).execute();
    }
}
