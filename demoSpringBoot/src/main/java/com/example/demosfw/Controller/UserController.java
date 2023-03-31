package com.example.demosfw.Controller;

import com.example.demosfw.ActiveMQ.MessageServiceActiveMQ;
import com.example.demosfw.Config.MyHttpSessionListener;
import com.example.demosfw.MongoDB.Book;
import com.example.demosfw.MongoDB.User;
import com.example.demosfw.Utils.JsonTools;
import com.example.demosfw.Utils.RedisUtil;
import com.mongodb.BasicDBObject;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationOptions;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class UserController {

    @Autowired
    private MessageServiceActiveMQ messageService;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private MongoTemplate mongoTemplate;

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Value("${application.message:Hello World}")
    private String message ;

    @GetMapping("/asd/{name}")
    public String welcome(@PathVariable String name, Map<String, Object> model) {
        model.put("time", new Date());
        model.put("message", this.message);
        return "welcome";
    }

    @RequestMapping("/")
    public Object init(HttpServletRequest request) {
        logger.info("打印日志/----------------------");

        //redis
        Map<String,Object> userInfo = new HashMap<>();
        userInfo.put("type","0000233781");
        userInfo.put("name","sunfanwei");
        redisUtil.hmset("000233781",userInfo);

        //spring-session redis
        HttpSession session = request.getSession();
        session.setAttribute("userInfo", userInfo);

        //ActiveMQ
        messageService.sendMessage(JsonTools.getJsonFromObject(userInfo));

        //MongoDB
        List<Book> userList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Book book = new Book();
            book.setId(i);
            book.setName("testMongoDB" + i);
            book.setType("testMongoDB" + i);
            book.setDescription("testMongoDB---" + i);
            userList.add(book);
        }
        // 单条  批量 插入
        //mongoTemplate.insert(userList, Book.class);
        //mongoTemplate.save(userList,"BookInfo");
        logger.info("MongoDB数据存入");
        return  "login123";
    }

    @RequestMapping("/login")
    public Object foo() {
        logger.info("打印日志----------------------");
        return  "login123";
    }

    @RequestMapping("/index")
    @ResponseBody
    public Object index(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        session.setAttribute("zxc", "zxc");
        return  "index";
    }

    @RequestMapping("/online")
    @ResponseBody
    public Object online() {
        return  "当前在线人数：" + MyHttpSessionListener.online + "人";
    }

    @RequestMapping("/mongoDBQuery")
    @ResponseBody
    public Object mongoDBQuery(HttpServletRequest request) {
        //  查询name=zs
        Query query = Query.query(Criteria.where("name").is("testMongoDB0"));
        List<Book> list5 = mongoTemplate.find(query,Book.class);
        List<Book> list4 = mongoTemplate.find(query,Book.class,"BookInfo");

        //  查询所有
        List<Book> list3 = mongoTemplate.findAll(Book.class);
        List<Book> list2 = mongoTemplate.findAll(Book.class,"BookInfo");

        //  分页查询	page页码，pageSize每页展示几个
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Order.asc("id")));
        Query query1 = new Query().with(pageable);
        List<Book> list1 = mongoTemplate.find(query1, Book.class,"BookInfo");

        //  查询多个
        Query query2 = Query.query(Criteria.where("id")
                .in(0,1,2))
                .with(Sort.by(Sort.Order.desc("id")));
        List<Book> list = this.mongoTemplate.find(query2, Book.class);

        //  查询数量
        Criteria criteria = Criteria.where("name").is("testMongoDB0")
                .and("type").is("testMongoDB0");
        Query query3 = Query.query(criteria);
        long count = this.mongoTemplate.count(query, Book.class);
        return count;

    }

    @RequestMapping("/mongoDBAdd")
    @ResponseBody
    public Object mongoDBAdd(HttpServletRequest request) {

        User user= new User();//
        user.setName("admin");
        user.setAge("88");
        user.setBookId(0);
        user.setId(0);

        List<User> list = new ArrayList<>();
        for (int i = 1; i < 100; i++) {
            User user1= new User();//
            user1.setName("test" + 1);
            user1.setAge("88" + i);
            user1.setBookId(i);
            user1.setId(i);
            list.add(user1);
        }


        //  保存对象到mongodb
        mongoTemplate.insert(user);
        mongoTemplate.save(user);
        //  根据集合名称保存对象到mongodb
        mongoTemplate.save(user,"mongodb_user_save");
        mongoTemplate.insert(user,"mongodb_user_insert");
        //  根据集合名称保存list到mongodb
        /*mongoTemplate.save(list,"mongodb_userInfo_save");*/
        mongoTemplate.insert(list,"mongodb_userInfo_insert");
        mongoTemplate.insert(list,User.class);
        return "success";
    }

    @RequestMapping("/mongoDBUpdate")
    @ResponseBody
    public Object mongoDBUpdate(HttpServletRequest request) {
        Query query = Query.query(Criteria.where("_id").is(0));
        Update update = Update.update("name","admin_admin");
        Update update1 = new Update();
        update1.set("name", "test");
        update1.set("age", "777");
        //  更新一条数据
        mongoTemplate.updateFirst(query,update, User.class);
        mongoTemplate.updateFirst(query,update, "mongodb_user_save");
        mongoTemplate.updateFirst(query,update1, User.class,"mongodb_user_insert");
        //  更新多条数据
        mongoTemplate.updateMulti(query,update, User.class);
        mongoTemplate.updateMulti(query,update,"mongodb_user_save");
        mongoTemplate.updateMulti(query,update, User.class,"mongodb_user_insert");

        Query query1 = Query.query(Criteria.where("_id").is(111));
        Update update2 = Update.update("name","admin_test");
        //  更新数据，如果数据不存在就新增
        mongoTemplate.upsert(query1,update2, User.class);
        mongoTemplate.upsert(query1,update2,"mongodb_user");
        mongoTemplate.upsert(query1,update2, User.class,"mongodb_user");
        return "success";
    }

    @RequestMapping("/mongoDBDelete")
    @ResponseBody
    public Object mongoDBDelete(HttpServletRequest request) {
        User user= new User();
        user.setId(0);


        Query query = Query.query(Criteria.where("_id").in(5,6));
        //  根据条件删除
        //mongoTemplate.remove(query,User.class);
        //  根据条件删除（可删除多条）
        mongoTemplate.remove(query,User.class,"mongodb_userInfo_insert");

        //删除集合，可传实体类，也可以传名称
        //mongoTemplate.dropCollection(Article.class);
        mongoTemplate.dropCollection("jSONObject");

        //删除数据库
        mongoTemplate.getDb().drop();

        //查询出符合条件的第一个结果，并将符合条件的数据删除,只会删除第一条
        query = Query.query(Criteria.where("author").is("yinjihuan"));
        User article = mongoTemplate.findAndRemove(query, User.class);
        List<User> articles = mongoTemplate.findAllAndRemove(query, User.class);


        return "success";
    }

    @RequestMapping("/mongoDBJoinQuery")
    @ResponseBody
    public Object mongoDBJoinQuery() {
        Criteria criteria = new Criteria();
        criteria.andOperator(
                Criteria.where("_id").gte(23),
                Criteria.where("_id").lte(90)
                //Criteria.where("age").regex("888")
        );
        //  设置查询条件：  23  <=  age   <=  30
        //criteria.and("_id").gte(23).lte(30);
        //联合查询条件
        Aggregation newAggregation = Aggregation.newAggregation(
                Aggregation.match(criteria),

                Aggregation.lookup("BookInfo", "bookId", "_id", "bookList"),
                //Aggregation.project("name","bookId").andInclude("result.type","result.description"),
                //Aggregation.unwind("$result"),
                /*排序*/
                Aggregation.sort(Sort.by(Sort.Order.desc("bookId")))

        );
        List<User> result =  mongoTemplate.aggregate(newAggregation, "UserInfo", User.class).getMappedResults() ;
        //List<MedalUserTo> medalUserTos = medalV4MongoTemplate.aggregate(aggregation, MedalTask.class, MedalUserTo.class).getMappedResults();


        return result;


        }
    }



