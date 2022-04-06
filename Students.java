package Service28;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Students")
public class Students {
    //student的数组
    student[] studentObjects=new student[1000];
    public student[] getStudentObjects() {
        return studentObjects;
    }

    public void addStudent(student stu){
        for(int i=0;i<studentObjects.length;i++){
            if(studentObjects[i]==null){
                studentObjects[i]=stu;
                break;
            }
        }
    }

    //创建学生档案
    public static boolean createStudentList(int studentID) throws JAXBException, FileNotFoundException {
        //创建Students对象
        Students stus=new Students();
        student stu=new student(studentID);
        stus.addStudent(stu);
        //存入文件中
        if(! new File("F:\\java\\WorkSpace\\maven-project\\ManagementSystem\\studentList.xml").exists()){
            //输出流
            OutputStream outputStream = new FileOutputStream( "F:\\java\\WorkSpace\\maven-project\\ManagementSystem\\studentList.xml" );
            //创建JAXB序列化文本
            JAXBContext jAXBContext = JAXBContext.newInstance( Students.class );
            //创建编码器
            Marshaller marshaller = jAXBContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            //编码xml文件
            marshaller.marshal(stus,outputStream);
            return true;
        //文件存在
        }else{
            //输入文件
            InputStream inputStream=new FileInputStream("F:\\java\\WorkSpace\\maven-project\\ManagementSystem\\studentList.xml");
            //创建JAXB序列化文本
            JAXBContext jAXBContext = JAXBContext.newInstance( Students.class );
            //创建解码器
            Unmarshaller unmarshaller= jAXBContext.createUnmarshaller();
            //将文件中的信息传入students
            stus=(Students) unmarshaller.unmarshal(inputStream);
            //判断对象是否存在在文件中
            for(int i=0;i<stus.studentObjects.length;i++){
                //如果迭代对象为null，那么则直接创建对象
                if(stus.getStudentObjects()[i]==null){
                    stus.addStudent(stu);
                    break;
                }

                //判断迭代的对象不为null的话，是否id相同，则直接退出不需要创建对象
                if(stus.getStudentObjects()[i].getStdID()==stu.getStdID()){
                    System.out.println("学生已经存入过文件中");
                    return false;
                }


            }
            //输出文件
            OutputStream outputStream = new FileOutputStream( "F:\\java\\WorkSpace\\maven-project\\ManagementSystem\\studentList.xml" );

            //创建编码器
            Marshaller marshaller = jAXBContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            marshaller.marshal( stus, outputStream );
            return true;

            /*
            * 每一次都会覆盖掉上一次的
            * */
        }
    }



}
