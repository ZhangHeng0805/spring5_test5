package zh.spring5.test;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import zh.spring5.entity.Book;
import zh.spring5.service.BookService;

import java.util.ArrayList;
import java.util.List;

public class TestBook {
    @Test
    public void testJdbcTemplate(){
        ApplicationContext context=new ClassPathXmlApplicationContext("bean1.xml");
        BookService bookService=context.getBean("bookService",BookService.class);
        Book book=new Book();
        book.setUserId("4");
        book.setUsername("C语言");
        book.setUstatus("（在线）");
        //添加
//        bookService.addBook(book);
        //修改
//        bookService.updateBook(book);
        //删除
//        bookService.deleteBook("1");
        //查询表记录数
//        int count=bookService.selectCount();
//        System.out.println(count);
        //查询表返回对象
//        Book book1 = bookService.selectOne("1");
//        System.out.println(book1);
        //查询表返回集合
//        List<Book> all = bookService.findAll();
//        System.out.println(all);
        //批量添加
//        List<Object[]> batchArgs=new ArrayList<>();
//        Object[] o1={"5","zh","a"};
//        Object[] o2={"6","zz","b"};
//        Object[] o3={"7","zyf","c"};
//        batchArgs.add(o1);
//        batchArgs.add(o2);
//        batchArgs.add(o3);
//        bookService.batchAdd(batchArgs);
        //批量修改
//        List<Object[]> batchArgs=new ArrayList<>();
//        Object[] o1={"zh1","a4","7"};
//        Object[] o2={"zz2","b5","5"};
//        Object[] o3={"zyf3","c6","6"};
//        batchArgs.add(o1);
//        batchArgs.add(o2);
//        batchArgs.add(o3);
//        bookService.batchUpdate(batchArgs);
        //批量删除
        List<Object[]> batchArgs=new ArrayList<>();
        Object[] o1={"6"};
        Object[] o2={"5"};
        batchArgs.add(o1);
        batchArgs.add(o2);
        bookService.batchDelete(batchArgs);


    }
}
