package com.cf.main;

import com.cf.bean.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.Iterator;
import java.util.List;

/**
 * Created by cpazstido on 2016/6/22.
 */
public class Test {
    public static void main(String[] args) {
        TestList();
        //TestIterate();
    }

    public static void TestList(){
        //加载指定目录下的配置文件，得到configuration对象
        Configuration cfg = new Configuration().configure("hibernate.cfg.xml");
        //根据configuration对象得到session工厂对象
        SessionFactory factory = cfg.buildSessionFactory();
        //使用工厂类打开一个session
        Session session = factory.openSession();
        //开启事务
        Transaction tx = session.beginTransaction();

        /**
         * 此时会发出一条sql，将30个学生全部查询出来
         */
        List<Student> ls = (List<Student>)session.createQuery("from Student").setFirstResult(0).setMaxResults(3).list();
        Iterator<Student> stus = ls.iterator();
        for(;stus.hasNext();)
        {
            Student stu = (Student)stus.next();
            System.out.println(stu.getSname());
        }
        //提交事务
        tx.commit();
        //关闭资源
        session.close();
        factory.close();
    }

    public static void TestIterate(){
        //加载指定目录下的配置文件，得到configuration对象
        Configuration cfg = new Configuration().configure("hibernate.cfg.xml");
        //根据configuration对象得到session工厂对象
        SessionFactory factory = cfg.buildSessionFactory();
        //使用工厂类打开一个session

        Session session = factory.openSession();
        //开启事务
        Transaction tx = session.beginTransaction();

        /**
         * 如果使用iterator方法返回列表，对于hibernate而言，它仅仅只是发出取id列表的sql
         * 在查询相应的具体的某个学生信息时，会发出相应的SQL去取学生信息
         * 这就是典型的N+1问题
         * 存在iterator的原因是，有可能会在一个session中查询两次数据，如果使用list每一次都会把所有的对象查询上来
         * 而是要iterator仅仅只会查询id，此时所有的对象已经存储在一级缓存(session的缓存)中，可以直接获取
         */
        Iterator<Student> stus = (Iterator<Student>)session.createQuery("from Student")
                .setFirstResult(0).setMaxResults(3).iterate();
        for(;stus.hasNext();)
        {
            Student stu = (Student)stus.next();
            System.out.println(stu.getSid()+" "+stu.getSname()+" "+stu.getAge());
        }

        //提交事务
        tx.commit();
        //关闭资源
        session.close();
        factory.close();
    }
}
