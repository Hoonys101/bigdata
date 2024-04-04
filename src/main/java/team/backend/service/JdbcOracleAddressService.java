package team.backend.service;

import team.backend.domain.Address;
import team.backend.repository.AddressRepository;

import java.util.List;

//@Service
public class JdbcOracleAddressService implements AddressService {
    private final AddressRepository repository;

    public JdbcOracleAddressService(AddressRepository repository)
    {
        this.repository = repository;

    }
    @Override
    public List<Address> listS() {
        return repository.list();
    }
    @Override
    public Address insertS(Address address) {

        return repository.insert(address);
    }

    @Override
    public boolean deleteS(Long seq) {

        return repository.delete(seq);
    }
}
