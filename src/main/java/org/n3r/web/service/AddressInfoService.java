package org.n3r.web.service;

import java.util.List;

import org.n3r.eql.Eql;
import org.n3r.eql.EqlPage;
import org.n3r.web.entity.AddressInfo;
import org.n3r.web.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import com.github.dandelion.datatables.core.ajax.DataSet;
import com.github.dandelion.datatables.core.ajax.DatatablesCriterias;

@Service
public class AddressInfoService extends BaseServiceImpl<AddressInfo, String> {

    public AddressInfo findAddressInfo(String id) {
        return new Eql().selectFirst("findAddressInfo").params(id).returnType(AddressInfo.class).execute();
    }

    public List<AddressInfo> findAllAddressInfo() {
        return new Eql().select("findAllAddressInfo").returnType(AddressInfo.class).limit(100).execute();
    }

    public List<AddressInfo> findAddressInfo(AddressInfo addressInfo) {
        return new Eql().select("findAddressInfo").returnType(AddressInfo.class).params(addressInfo).limit(100)
                .execute();
    }

    public DataSet<AddressInfo> findPersonsWithDatatablesCriterias(DatatablesCriterias criterias) {
        EqlPage page = new EqlPage(criterias.getDisplayStart(), criterias.getDisplaySize());
        page.setCurrentPage(criterias.getDisplayStart() / page.getPageRows() + 1);
        List<AddressInfo> orderList = new Eql().select("findPageAddressInfo").returnType(AddressInfo.class)
                .params(criterias).dynamics(criterias)
                .limit(page).execute();
        return new DataSet<AddressInfo>(orderList, new Long(page.getPageRows()), new Long(
                page.getTotalRows()));
    }

}
