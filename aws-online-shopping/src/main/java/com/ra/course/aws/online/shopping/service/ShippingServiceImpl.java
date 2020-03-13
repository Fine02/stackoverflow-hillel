package com.ra.course.aws.online.shopping.service;

import com.ra.course.aws.online.shopping.dao.ShippingDao;
import com.ra.course.aws.online.shopping.entity.Address;
import com.ra.course.aws.online.shopping.entity.user.Member;
import com.ra.course.aws.online.shopping.exceptions.MemberNotFoundException;
import com.ra.course.aws.online.shopping.exceptions.ShippingAddressNotFoundException;

public class ShippingServiceImpl implements ShippingService {
    private transient final ShippingDao shippingDao;

    public ShippingServiceImpl(final ShippingDao addressDao) {
        this.shippingDao = addressDao;
    }

    @Override
    public Address specifyShippingAddress(final Member member, final Address address) throws MemberNotFoundException, ShippingAddressNotFoundException {
        if (shippingDao.isFoundMemberID(member.getMemberID())) {
            final var shipmentAddress = member.getAccount().getShippingAddress();
            if (shippingDao.findShippingAddress(shipmentAddress)) {
                return shipmentAddress;
            } else {
                member.getAccount().setShippingAddress(address);
                shippingDao.updateShippingAddress(member);
            }
            throw new ShippingAddressNotFoundException("There is not found shipping address");
        }
        throw new MemberNotFoundException("There is not found the Member by this ID");
    }
}
