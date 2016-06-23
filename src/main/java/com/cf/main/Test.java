package com.cf.main;

import com.cf.bean.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

/**
 * Created by cpazstido on 2016/6/22.
 */
public class Test {
    public static void main(String[] args) {
        //加载指定目录下的配置文件，得到configuration对象
        Configuration cfg = new Configuration().configure("hibernate.cfg.xml");
        //根据configuration对象得到session工厂对象
        SessionFactory factory = cfg.buildSessionFactory();
        //使用工厂类打开一个session
        Session session = factory.openSession();
        //开启事务
        Transaction tx = session.beginTransaction();

        Student ss = new Student();
        ss.setSid(29);
        session.delete(ss);
        ss.setSname("ff");

        //提交事务
        tx.commit();
        //关闭资源
        session.close();
        factory.close();
    }
}
