namespace java com.micmiu.thrift.demo
struct User{
	
	1: i32 id;
	2: string username;
	3: i32 age;
}

service UserService{
	
	void saveUser(1: User user);

	User getUser(1:i32 id);
}
