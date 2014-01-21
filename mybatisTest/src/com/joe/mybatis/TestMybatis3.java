package com.joe.mybatis;

import java.io.Reader;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.joe.mybatis.model.User;

public class TestMybatis3 {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		Reader reader = Resources.getResourceAsReader("com/joe/mybatis/mybatisConfig.xml");
		SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader);
		SqlSession session = sessionFactory.openSession();
		
//		insertOneUser(session);
		//selectOneUser(session,1);
//		updateOneUser(session,1);
//		deleteOneUser(session,1);
		selectAllUsers(session, null);
	}

	private static void insertOneUser(SqlSession session) {
		User user = new User();
		user.setName("joe 4");
		user.setEmail("xiaojun@hp.com");
		user.setCreateTime(new Date());
		user.setTelephone("12345678");
		user.setAge(84);
		
		//test insert
		session.insert("namespaceUser.insert",user);
		session.commit();
	}
	
	private static void selectOneUser(SqlSession session, int id) {
		User userFromDB = (User) session.selectOne("namespaceUser.getById", id);
		System.out.println(userFromDB);
	}
	
	private static void updateOneUser(SqlSession session, int id) {
		User userFromDB = (User) session.selectOne("namespaceUser.getById", id);
		userFromDB.setEmail("jun.xiao3@hp.com");
		userFromDB.setName("big joe");
		userFromDB.setTelephone(null);
		session.update("namespaceUser.update",userFromDB);
		session.commit();
	}

	private static void deleteOneUser(SqlSession session, int id) {
		session.delete("namespaceUser.delete",id);
		session.commit();
		User fromDb = (User) session.selectOne("namespaceUser.getById", id);
		System.out.println(fromDb);
	}

	private static void selectAllUsers(SqlSession session, User user) {
		List<User> list = session.selectList("namespaceUser.pageSelect",user,new RowBounds(0,100));
		Iterator it = list.iterator();
		while(it.hasNext()) {
			User tmp = (User)it.next();
			System.out.println(tmp);
		}
	}
}
