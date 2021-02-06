package zh.spring5.dao;

import zh.spring5.entity.Book;

import java.util.List;

public interface BookDao {
    //添加方法
    void add(Book book);

    void update(Book book);

    void delete(String id);

    int selectCount();

    Book selectBookInfo(String id);

    List<Book> findAll();

    void batchAdd(List<Object[]> batchArgs);

    void batchUpdate(List<Object[]> batchArgs);

    void batchDelete(List<Object[]> batchArgs);
}
