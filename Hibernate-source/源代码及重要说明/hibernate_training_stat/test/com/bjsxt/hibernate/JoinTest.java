package com.bjsxt.hibernate;

import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;

  
public class JoinTest extends TestCase {
	
	public void testInitData(){
		
		Session session = HibernateUtils.getSession();
		try {
			session.beginTransaction();
			int studentid = 1;
			//创建班级0->4
			for(int i=0; i<5; i++){
				Classes cls = new Classes();
				cls.setClassesNo("SXT"+i);
				session.save(cls);
				
				//创建学生: 
				for(int j=0; j<3; j++){
					Student s = new Student();
					s.setName("学生"+(studentid++));
					s.setClasses(cls);
					s.setStudentNo("STUDENT"+j+"["+cls.getClassesNo()+"]");
					session.save(s);
				}
			}
			
			//成绩单:
			//学生15没有修任何课程，
			//学生1-9，修了第一门课程
			//学生4-14,修了第二门课程
			//学生7-14，修了第三门课程
			int[][] grades = {
					{60,45,80,90,96,88,67,95,95} //课程0,学生(1-9)的成绩,最高分95
					,{65,50,99,81,95,83,72,83,98} //课程1，学生(4-12)的成绩，最高分98
					,{75,60,94,91,85,73,82,63,68} //课程2，学生(6-14)的成绩,最高分94
			};
			
			//分别对应不同成绩的学生的ID
			String[] studentIds = {
					"1,2,3,4,5,6,7,8,9",
					"4,5,6,7,8,9,10,11,12",
					"6,7,8,9,10,11,12,13,14"
			};
			
			//创建课程和对应的成绩
			for(int i=0; i<3; i++){
				Course c = new Course();
				c.setName("课程"+i);
				session.save(c);

				List students = session.createQuery("select s from Student s where s.id in ("+studentIds[i]+")").list();
				for (int j=0; j<students.size(); j++) {
					Student student = (Student)students.get(j);
					Grade g = new Grade();
					g.setCourse(c);
					g.setStudent(student);
					g.setGrade(grades[i][j]);
					session.save(g);
				}
			}

			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		} finally{
			HibernateUtils.closeSession(session);
		}
	}

	//列出所有课程中,成绩最高的学生
	public void testQuery(){
		Session session = HibernateUtils.getSession();
		try {
			session.beginTransaction();
			
			List grades = session.createQuery("select c.name,s.name,g.grade,cls.classesNo from Grade g " +
					"join g.course c join g.student s join s.classes cls " +
					"where g.grade in ( select max(gg.grade) from Grade gg where gg.course.name = c.name group by gg.course.name) " +
					"order by c.name").list();

//			List grades = session.createQuery("select c.name,s.name,g.grade,cls.classesNo from Grade g, " +
//					"Course c, Student s, Classes cls " +
//					"where g.course=c.id and g.student=s.id and s.classes.id=cls.id and " + 
//					"g.grade in ( select max(gg.grade) from Grade gg where gg.course.name = c.name group by gg.course.name) " +
//					"order by c.name").list();
			
			//课程名称、学生名称、分数、所属班级名称
			//List grades = session.createQuery("???请填写相应的HQL语句???").list();
			for (Iterator iter = grades.iterator(); iter.hasNext();) {
				Object[] objs = (Object[]) iter.next();
				System.out.println(objs[0]+","+objs[1]+","+objs[2]+","+objs[3]);
			}
			
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		} finally{
			HibernateUtils.closeSession(session);
		}
	}
}
