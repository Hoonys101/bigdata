package team.backend.repository;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import team.backend.domain.Address;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

//@Repository
public class JdbcTemplateOracleAddressRepository implements AddressRepository {
    private final JdbcTemplate jdbcTemplate;
    //@Autowired
    public JdbcTemplateOracleAddressRepository(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
    @Override
    public List<Address> list() {
        String sql = "select * from ADDRESS order by SEQ desc";
        List<Address> list = jdbcTemplate.query(sql, addressRowMapper());
        return list;
    }
    private RowMapper<Address> addressRowMapper(){
        return new RowMapper<Address>() {
            @Override
            public Address mapRow(ResultSet rs, int rowNum) throws SQLException {
                Address address = new Address();
                address.setSeq(rs.getLong("seq"));
                address.setName(rs.getString("name"));
                address.setAddr(rs.getString("addr"));
                address.setRdate(rs.getDate("rdate"));
                return address;
            }
        };
    }
    @Override
    public Address insert(Address address) {
        long seq = jdbcTemplate.queryForObject("select ADDRESS_SEQ.nextval from DUAL", Long.class);
        String sql = "insert into ADDRESS values(?,?,?,SYSDATE)";
        jdbcTemplate.update(sql, seq, address.getName(), address.getAddr());
        address.setSeq(seq);

        return address;
    }
    /*
    @Override
    public boolean insert(Address address) { //boolean을 리턴하는 경우
        String sql = "insert into ADDRESS values(ADDRESS_SEQ.nextval,?,?,SYSDATE)";
        int count = jdbcTemplate.update(sql, address.getName(), address.getAddr());
        if(count > 0) return true;
        else return false;
    }*/

    @Override
    public boolean delete(long seq) {
        String sql = "delete from ADDRESS where SEQ=?";
        int count = jdbcTemplate.update(sql, seq);
        if(count > 0) return true;
        else return false;
    }
}
