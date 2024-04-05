package team.backend;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import team.backend.mapper.AddressMapper;
import team.backend.repository.SpringDataJpaOracleAddressRepository;
import team.backend.repository.SpringDataJpaOracleBoardRepository;
import team.backend.service.AddressService;
import team.backend.service.BoardService;
import team.backend.service.SpringDataJpaAddressService;
import team.backend.service.SpringDataJpaBoardService;

import javax.sql.DataSource;

@Configuration
public class SpringConfig {
    @Autowired
    DataSource dataSource; //Jdbc + JdbcTemplate
    @Autowired
    EntityManager em; //JPA이용할때 쓰는것

    @Autowired
    SpringDataJpaOracleAddressRepository repository;
    @Autowired
    SpringDataJpaOracleBoardRepository repositoryBoard;

    /*@Bean
    public AddressRepository addressRepository(){ //Jdbc+JdbcTemplate+JPA
        //return new JdbcOracleAddressRepository(dataSource);
        //return new JdbcTemplateOracleAddressRepository(dataSource);
        return new JpaOracleAddressRepository(em);
    }*/

    @Autowired
    AddressMapper mapper; //MyBatis

    @Bean
    public AddressService addressService(){ //Jdbc+MyBatis+JPA + SpringDataJPA
        //return new JdbcOracleAddressService(addressRepository());
        //return new MybatisAddressService(mapper);
        //return new JpaAddressService(addressRepository());
        return new SpringDataJpaAddressService(repository);
    }
    @Bean
    public BoardService boardService(){
        return new SpringDataJpaBoardService(repositoryBoard);
    }

}
