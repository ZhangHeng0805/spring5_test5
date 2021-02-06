package zh.spring5.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import zh.spring5.entity.Book;

import java.util.Arrays;
import java.util.List;

@Repository
public class BookDaoImpl implements BookDao{
    //注入JdbcTemplate
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void add(Book book) {
        String sql="insert into t_book values(?,?,?)";
        Object[] args={book.getUserId(),book.getUsername(),book.getUstatus()};
        int op=jdbcTemplate.update(sql,args);
        System.out.println(op);
    }

    @Override
    public void update(Book book) {
        String sql="update t_book set username=?,ustatus=? where user_id=?";
        Object[] args={book.getUsername(),book.getUstatus(),book.getUserId()};
        int op=jdbcTemplate.update(sql,args);
        System.out.println(op);
    }

    @Override
    public void delete(String id) {
        String sql="delete from t_book where user_id=?";
        int op=jdbcTemplate.update(sql,id);
        System.out.println(op);
    }

    @Override
    public int selectCount() {
        String sql="select count(*) from t_book";
        Integer count=jdbcTemplate.queryForObject(sql,Integer.class);
        return count;
    }

    @Override
    public Book selectBookInfo(String id) {
        String sql="select * from t_book where user_id=?";
        Book book = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<Book>(Book.class), id);
        return book;
    }

    @Override
    public List<Book> findAll() {
        String sql="select * from t_book";
        List<Book> bookList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Book>(Book.class));
        return bookList;
    }

    @Override
    public void batchAdd(List<Object[]> batchArgs) {
        String sql="insert into t_book values(?,?,?)";
        int[] ints = jdbcTemplate.batchUpdate(sql, batchArgs);
        System.out.println(Arrays.toString(ints));
    }

    @Override
    public void batchUpdate(List<Object[]> batchArgs) {
        String sql="update t_book set username=?,ustatus=? where user_id=?";
        int[] ints = jdbcTemplate.batchUpdate(sql, batchArgs);
        System.out.println();
    }

    @Override
    public void batchDelete(List<Object[]> batchArgs) {
        String sql="delete from t_book where user_id=?";
        int[] ints = jdbcTemplate.batchUpdate(sql, batchArgs);
        System.out.println(Arrays.toString(ints));
    }
}
