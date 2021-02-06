package zh.spring5.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zh.spring5.dao.BookDao;
import zh.spring5.entity.Book;

import java.util.List;

@Service
public class BookService {
    //注入dao
    @Autowired
    private BookDao bookDao;

    //添加方法
    public void addBook(Book book){
        bookDao.add(book);
    }
    //修改方法
    public void updateBook(Book book){
        bookDao.update(book);
    }
    //删除方法
    public void deleteBook(String id){
        bookDao.delete(id);
    }
    //查询表记录数
    public int selectCount(){
        return bookDao.selectCount();
    }
    //查询表返回对象
    public Book selectOne(String id){
        return bookDao.selectBookInfo(id);
    }
    //查询表返回集合
    public List<Book> findAll(){
        return bookDao.findAll();
    }
    //批量添加
    public void batchAdd(List<Object[]> batchArgs){
        bookDao.batchAdd(batchArgs);
    }
    //批量修改
    public void batchUpdate(List<Object[]> batchArgs){
        bookDao.batchUpdate(batchArgs);
    }
    //批量删除
    public void batchDelete(List<Object[]> batchArgs){
        bookDao.batchDelete(batchArgs);
    }
}
