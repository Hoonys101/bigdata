package team.backend.repository;

import org.springframework.jdbc.datasource.DataSourceUtils;
import team.backend.domain.Address;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
//@Repository// 이 클래스를 스프링의 데이터 액세스 레이어의 구현체로 사용하기 위해
// @Repository 어노테이션을 사용하여 스프링에게 알려줍니다.
public class JdbcOracleAddressRepository implements AddressRepository {
    //@Autowired // 객체를 직접 생성하는게 아니라 외부에서 생성 후 객체주입을 한다?
    private final DataSource dataSource;//데이터베이스 연결을 관리하는데 사용되는 객체

    // 스프링 프레임워크에서 의존성 주입을 자동으로 수행하기 위해 사용하는 어노테이션입니다.
    // @Autowired 어노테이션이 붙은 필드나 생성자에 해당 타입의 빈을 주입합니다.
    //@Autowired
    public JdbcOracleAddressRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    Connection getConnection() {
        return DataSourceUtils.getConnection(dataSource);
    }

    private void close(Connection con) throws SQLException {
        DataSourceUtils.releaseConnection(con, dataSource);
    }

    @Override
    public List<Address> list() {
        ArrayList<Address> list = new ArrayList<>();
        String sql = "select * from ADDRESS order by NAME";
        // 데이터베이스 연결을 나타내는 Connection 객체를 초기화합니다.
        Connection con = null;

        // SQL 문을 실행하기 위한 Statement 객체를 초기화합니다.
        Statement stmt = null;

        // SQL 쿼리의 실행 결과를 저장하는 ResultSet 객체를 초기화합니다.
        ResultSet rs = null;
        try {
            con = getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                long seq = rs.getLong(1);
                String name = rs.getString(2);
                String addr = rs.getString(3);
                Date rdate = rs.getDate(4);
                list.add(new Address(seq, name, addr, rdate));

            }
            return list;
        } catch (SQLException se) {
            return null;
        } finally {
            close(con, stmt, rs);
        }
    }

    private void close(Connection con, Statement stmt, ResultSet rs) {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (con != null) close(con);
        } catch (SQLException se) {

        }
    }

    @Override
    public Address insert(Address address) {
        String sql ="insert into ADDRESS values(ADDRESS_SEQ.nextval, ?, ?, SYSDATE)";
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            con = getConnection();
            pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, address.getName());
            pstmt.setString(2, address.getAddr());
            pstmt.executeUpdate();

            rs = pstmt.getGeneratedKeys();
            if(rs.next()){
                String rowid = rs.getString(1); //rs.getString("ROWID");는 안되네?
                Address addressDb = getAddress (rowid);
                return addressDb;
            }else{
                return null;
            }
        }catch (SQLException se){
            return null;
        }finally{
            close(con, pstmt, rs);
        }
    }


    // 이 메서드는 데이터베이스에서 주어진 ROWID에 해당하는 주소(Address)를 가져오는 메서드입니다.
    private Address getAddress(String rowid){
        String sql = "select * from ADDRESS where ROWID=?";
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, rowid);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                long seq = rs.getLong(1);
                String name = rs.getString(2);
                String addr = rs.getString(3);
                Date rdate = rs.getDate(4);
                return new Address(seq, name, addr, rdate);
            }else{
                return null;
            }
        }catch(SQLException se){
            return null;
        }finally{
            close(con, pstmt, rs);
        }
    }



    @Override
    public boolean delete(long seq) {
        String sql = "delete from ADDRESS where SEQ=?";
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            con = getConnection();
            pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setLong(1, seq);
            int i = pstmt.executeUpdate();
            if(i>0) {
                return true;
            }else{
                return false;
            }

        }catch(SQLException se){
            return false;
        }finally {
            close(con, pstmt, rs);
        }
    }
}
