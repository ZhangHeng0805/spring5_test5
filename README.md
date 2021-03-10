# spring5_test5
# Spring5框架—JdbcTemplate操作数据库
## JdbcTemplate（概念核准备）
   ### 1、什么是JdbcTemplate
        （1）Spring框架对JDBC进行封装，使用JdbcTemplate方便实现对数据库操作
   ### 2、准备工作
        （1）引入相关jar包
            mysql-connector-java-5.1.39.jar
            spring-jdbc-5.2.6.RELEASE.jar
            spring-orm-5.2.6.RELEASE.jar
            spring-tx-5.2.6.RELEASE.jar
        （2）在spring配置文件中配置数据库连接池
        <!--直接配置连接池-->
            <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource" destroy-method="close">
                <property name="driverClassName" value="com.mysql.jdbc.Driver"></property>
                <property name="url" value="jdbc:mysql:///user_db"></property>
                <property name="username" value="root"></property>
                <property name="password" value="root"></property>
            </bean>
        （3）配置JdbcTemplate，注入DataSource
        <!--配置JdbcTemplate对象-->
            <bean id="jdbcTemplate" class="org.springframework.jdbc.core.JdbcTemplate">
                <!--注入DataSource-->
                <property name="dataSource" ref="dataSource"></property>
            </bean>
        （4）创建service类和dao类，在dao注入jdbcTemplate对象
        *配置文件
        <!--组件扫描-->
            <context:component-scan base-package="zh.spring5"></context:component-scan>
        *Service
            @Service
            public class BookService {
                //注入dao
                @Autowired
                private BookDao bookDao;
            }
        *Dao
            @Repository
            public class BookDaoImpl implements BookDao{
                //注入JdbcTemplate
                @Autowired
                private JdbcTemplate jdbcTemplate;
            }
## JdbcTemplate操作数据库（添加）
   ### 1、对应数据库创建实体类
        public class User {
            private String userId;
            private String username;
            private String ustatus;

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getUstatus() {
                return ustatus;
            }

            public void setUstatus(String ustatus) {
                this.ustatus = ustatus;
            }
        }
   ### 2、编写service和dao
        （1）在dao进行数据库添加操作
        （2）调用JdbcTemplate对象里面的update方法是向添加操作
            *有两个参数
                第一个参数：sql语句
                第二个参数：可变参数，设置sql语句值
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
            }
   ### 3、测试类
    @Test
        public void testJdbcTemplate(){
            ApplicationContext context=new ClassPathXmlApplicationContext("bean1.xml");
            BookService bookService=context.getBean("bookService",BookService.class);
            Book book=new Book();
            book.setUserId("1");
            book.setUsername("java");
            book.setUstatus("a");
            bookService.addBook(book);
        }
## JdbcTemplate操作数据库（修改和删除）
   ### 1、修改
        @Override
            public void update(Book book) {
                String sql="update t_book set username=?,ustatus=? where user_id=?";
                Object[] args={book.getUsername(),book.getUstatus(),book.getUserId()};
                int op=jdbcTemplate.update(sql,args);
                System.out.println(op);
            }
   ### 2、删除
        @Override
            public void delete(String id) {
                String sql="delete from t_book where user_id=?";
                int op=jdbcTemplate.update(sql,id);
                System.out.println(op);
            }
## JdbcTemplate操作数据库（查询返回某个值）
   ### 1、查询表里面有多少条数据
   ### 2、使用JdbcTemplate实现查询返回某个值代码
        使用方法queryForObject()
        *有两个参数
        第一个参数：sql语句
        第二个参数：返回类型Class
        @Override
            public int selectCount() {
                String sql="select count(*) from t_book";
                Integer count=jdbcTemplate.queryForObject(sql,Integer.class);
                return count;
            }
## JdbcTemplate操作数据库（查询返回对象）
   ### 1、场景：查询图书的详情
   ### 2、JDBCTemplate实现查询返回对象
    *方法queryForObject()有三个参数：
        第一个参数：SQL语句
        第二个参数：RowMapper，是接口，返回不同类型的数据，使用这个接口里面实现类完成数据封装，
        第三个参数：SQL语句值
        @Override
            public Book selectBookInfo(String id) {
                String sql="select * from t_book where user_id=?";
                Book book = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<Book>(Book.class), id);
                return book;
            }
## JdbcTemplate操作数据库（查询返回集合）
   ### 1、场景：查询图书列表分页...
   ### 2、调用JdbcTemplate方法实现查询返回集合
    *方法query()有三个参数：
            第一个参数：SQL语句
            第二个参数：RowMapper，是接口，返回不同类型的数据，使用这个接口里面实现类完成数据封装，
            第三个参数：SQL语句值
            @Override
                public List<Book> findAll() {
                    String sql="select * from t_book";
                    List<Book> bookList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Book>(Book.class));
                    return bookList;
                }
## JdbcTemplate操作数据库（批量操作）
   ### 1、批量操作：操作表里面多条记录
   ### 2、批量添加
    *方法batchUpdate()有两个参数
        第一个参数：SQL语句
        第二个参数：List集合，添加多条记录数据
        @Override
            public void batchAdd(List<Object[]> batchArgs) {
                String sql="insert into t_book values(?,?,?)";
                int[] ints = jdbcTemplate.batchUpdate(sql, batchArgs);
                System.out.println(Arrays.toString(ints));
            }
        测试方法
        List<Object[]> batchArgs=new ArrayList<>();
                Object[] o1={"5","zh","a"};
                Object[] o2={"6","zz","b"};
                Object[] o3={"7","zyf","c"};
                batchArgs.add(o1);
                batchArgs.add(o2);
                batchArgs.add(o3);
                bookService.batchAdd(batchArgs);
   ### 3、批量修改
    *方法batchUpdate()有两个参数
            第一个参数：SQL语句
            第二个参数：List集合，修改多条记录数据
            @Override
                public void batchUpdate(List<Object[]> batchArgs) {
                    String sql="update t_book set username=?,ustatus=? where user_id=?";
                    int[] ints = jdbcTemplate.batchUpdate(sql, batchArgs);
                    System.out.println(Arrays.toString(ints));
                }
            测试方法
            List<Object[]> batchArgs=new ArrayList<>();
                    Object[] o1={"zh1","a4","7"};
                    Object[] o2={"zz2","b5","5"};
                    Object[] o3={"zyf3","c6","6"};
                    batchArgs.add(o1);
                    batchArgs.add(o2);
                    batchArgs.add(o3);
                    bookService.batchUpdate(batchArgs);
   ### 3、批量删除
        *方法batchUpdate()有两个参数
            第一个参数：SQL语句
            第二个参数：List集合，修改多条记录数据
            @Override
                public void batchDelete(List<Object[]> batchArgs) {
                    String sql="delete from t_book where user_id=?";
                    int[] ints = jdbcTemplate.batchUpdate(sql, batchArgs);
                    System.out.println(Arrays.toString(ints));
                }
            测试方法
            List<Object[]> batchArgs=new ArrayList<>();
                    Object[] o1={"6"};
                    Object[] o2={"5"};
                    batchArgs.add(o1);
                    batchArgs.add(o2);
                    bookService.batchDelete(batchArgs);
