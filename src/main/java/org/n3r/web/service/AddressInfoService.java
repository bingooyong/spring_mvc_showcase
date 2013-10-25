package org.n3r.web.service;

import java.util.List;

import org.n3r.esql.Esql;
import org.n3r.web.entity.AddressInfo;
import org.n3r.web.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class AddressInfoService extends BaseServiceImpl<AddressInfo, String> {

    public AddressInfo findAddressInfo(String id) {
        return new Esql().selectFirst("findAddressInfo").params(id).returnType(AddressInfo.class).execute();
    }

    public List<AddressInfo> findAllAddressInfo() {
        return new Esql().select("findAllAddressInfo").returnType(AddressInfo.class).execute();
    }

    public List<AddressInfo> findAddressInfo(AddressInfo addressInfo) {
        return new Esql().select("findAddressInfo").returnType(AddressInfo.class).params(addressInfo).execute();
    }
}
