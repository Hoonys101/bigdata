package team.backend.repository;

import team.backend.domain.Address;

import java.util.List;

public interface AddressRepository {
    List<Address> list();

    Address insert(Address address);

    boolean delete(long seq);
}