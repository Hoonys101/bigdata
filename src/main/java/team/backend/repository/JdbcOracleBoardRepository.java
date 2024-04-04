package team.backend.repository;

import org.springframework.jdbc.datasource.DataSourceUtils;
import team.backend.domain.Board;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
//@Repository
public class JdbcOracleBoardRepository implements BoardRepository {
    //@Autowired
    private final DataSource dataSource;
    //@Autowired
    public JdbcOracleBoardRepository(DataSource dataSource){
        this.dataSource = dataSource;
    }
    Connection getConnection(){
        return DataSourceUtils.getConnection(dataSource);
    }
    private void close(Connection con) {
        DataSourceUtils.releaseConnection(con, dataSource);
    }
    @Override
    public List<Board> list() {
        ArrayList<Board> list = new ArrayList<>();
        String sql = "select * from Board order by seq";
        Connection con = null;
        Statement stat = null;
        ResultSet rs = null;
        try{
            con = getConnection();
            stat = con.createStatement();
            rs = stat.executeQuery(sql);
            while(rs.next()){
                int seq = rs.getInt(1);
                String name = rs.getString(2);
                String email = rs.getString(3);
                String subject = rs.getString(4);
                String content = rs.getString(5);
                Date rdate = rs.getDate(6);
                list.add(new Board(seq, name,email, subject,content, rdate));
            }
        return list;
        }catch(SQLException se){
            return null;
        }finally{
            close(con,stat,rs);
        }

    }

    @Override
    public Board insert(Board board) {
        return null;
    }

    @Override
    public Board content(int seq) {
        return null;
    }

    @Override
    public Boolean delete(int seq) {
        return null;
    }

    @Override
    public Boolean update(Board board) {
        return null;
    }
    private void close(Connection con, Statement stmt, ResultSet rs) {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (con != null) close(con);
        } catch (SQLException se) {

        }
    }


}

