package model;

import java.io.Serializable;

/**
 * @author xiaobaobao
 * @date 2019/8/10 16:08
 */
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	private String name;
	private int age;
	public int getId() {
		return id;
	}

	public User() {
	}

	public User(int id, String name, int age) {
		this.id = id;
		this.name = name;
		this.age = age;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", name='" + name + '\'' +
				", age=" + age +
				'}';
	}
}