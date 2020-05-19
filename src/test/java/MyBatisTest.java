/**
 * @author xiaobaobao
 * @date 2019/8/10 16:19
 */

import model.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;

public class MyBatisTest {

	SqlSession session;
	SqlSession session1;

	@Before
	public void init() {
		//定义读取文件名
		String resources = "mybatis-config.xml";
		//创建流
		Reader reader = null;
		try {
			//读取mybatis-config.xml文件到reader对象中
			reader = Resources.getResourceAsReader(resources);
		} catch (IOException e) {
			e.printStackTrace();
		}
		//初始化mybatis,创建SqlSessionFactory类的实例
		SqlSessionFactory sqlMapper = new SqlSessionFactoryBuilder().build(reader);
		//创建session实例
		session = sqlMapper.openSession();
		session1 = sqlMapper.openSession();
		System.out.println("=================开始执行语句=================");
	}

	@Test
	public void run1() {
		//传入参数查询，返回结果
		User user = session.selectOne("User.findById", 1);
		System.out.println();
		session.selectOne("User.findById", 2);
		System.out.println("---------------------------------------------");
		//会直接从缓存中读取
		user = session.selectOne("User.findById", 1);
		System.out.println();
		session.selectOne("User.findById", 2);
		System.out.println("---------------------------------------------");
		user.setAge(user.getAge() + 1);
		session.update("update", user);
		System.out.println("---------------------------------------------");
		user = session.selectOne("User.findById", 1);
		System.out.println();
		session.selectOne("User.findById", 2);
	}

	@Test
	public void run2() {
		//传入参数查询，返回结果
		session.selectOne("User.findById", 1);
		System.out.println("---------------------------------------------");
		session.selectOne("User.findById", 1);
//		System.out.println("---------------------------------------------");
//		session.clearCache();
//		session.selectOne("User.findById", 1);
	}

	@Test
	public void run3() {
		session.selectOne("User.findById", 1);
		session.commit();
		System.out.println("---------------------------------------------");
		session.selectOne("User.findById", 1);
//		session.commit();
//		System.out.println("---------------------------------------------");
//		session1.selectOne("User.findById", 1);
//		System.out.println("---------------------------------------------");
//		User user = new User(1, "123", new Random().nextInt(10000));
//		session1.update("User.update", user);
//		System.out.println("---------------------------------------------");
//		System.out.println(session1.selectOne("User.findById", 1));
	}

	@Test
	public void run5_readOnly() {
		session.selectOne("User.findById", 1);
		session.commit();
		System.out.println("---------------------------------------------");
		session.selectOne("User.findById", 1);
		System.out.println("---------------------------------------------");
		User user = new User(1, "123", new Random().nextInt(10000));
		session1.update("User.update", user);
		session1.commit();
		System.out.println("---------------------------------------------");
		System.out.println(((User) session1.selectOne("User.findById", 1)).getAge());
	}

	@Test
	public void run4() {
		System.out.println(session.selectList("User.findByInByArray", new int[]{1, 3}));
		System.out.println("---------------------------------------------");
		System.out.println(session.selectList("User.findByInByList", Arrays.stream(new int[]{1, 3}).boxed().collect(Collectors.toList())));
	}

	@After
	public void close() {
		System.out.println("=================结束执行语句=================");
		//关闭session
		session.commit();
		session.close();

		session1.commit();
		session1.close();
	}
}