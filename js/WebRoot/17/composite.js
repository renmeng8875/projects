/**
 *有这样一个需求
 *有一个学校有2个班(一班,二班)
 *每个班级分2个小组(一班一组,一班二组,二班一组,二班二组)
 *学校计算机教室有限,每一个小组分着来上课.
 *考试的时候大家一起考
 *请用程序来模拟这个需求
 */
(function(){
	//不用组合模式
	//学校类
	var school = function(name){
		this.name = name;
		//班级
		var classes = new Array();
		this.addClasses = function(cla){
			classes.push(cla);
			return this;
		}
		this.getClasses = function(){
			return classes;
		}
	}
	//班级类
	var classes = function(name){
		this.name = name;
		var groups = new Array();
		this.getGroup = function(){
			return groups;
		}
		this.addGroup = function(group){
			groups.push(group);
			return this;
		}
	}
	//组
	var group  = function(name){
		this.name = name;
		var students = new Array();
		this.getStudent = function(){
			return students;
		}
		this.addStudent = function(stu){
			students.push(stu);
			return this;
		}
	}	
	//学生类
	var student  = function(name){
		this.name = name;
		this.goToClass = function(){
			document.write(this.name+" 去上课");
		}
		this.finishClass = function(){
			document.write(this.name+" 下课");
		}
	}
	
	//测试
	var a = new student("a");
	var b = new student("b");
	var c = new student("c");
	var d = new student("d");
	var e = new student("e");
	var f = new student("f");
	var g = new student("g");
	var h = new student("h");
	var i = new student("i");
	var one = new classes("一班");
		var oneOne = new group("一班一组");
			oneOne.addStudent(a).addStudent(b);
		var oneTwo = new group("一班二组");
			oneTwo.addStudent(c).addStudent(d);
		
		one.addGroup(oneOne).addGroup(oneTwo);
	var two = new classes("二班"); 
		var twoOne = new group("二班一组"); 
			twoOne.addStudent(e).addStudent(f);
		var twoTwo = new group("二班二组");
			twoTwo.addStudent(g).addStudent(h).addStudent(i)
		two.addGroup(twoOne).addGroup(twoTwo);
	var usPcat = new school("波斯卡特计算机培训学校");
	usPcat.addClasses(one).addClasses(two);
	
	//调用  就写一个 一班一组去上课
	var classes= usPcat.getClasses();
	for (var i = 0; i < classes.length; i++) {
		if(classes[i].name == "一班"){
			for (var j = 0; j < classes[i].getGroup().length; j++) {
//				document.write(classes[i].getGroup()[j])
				if(classes[i].getGroup()[j].name == "一班一组"){
					var s = classes[i].getGroup()[j].getStudent();
					for (var k = 0; k < s.length; k++) {
						s[k].goToClass();
					}
				}
			}
			
		}
	}
	//我快些吐了
	//这种方法一定不是和业务的扩展
})()













