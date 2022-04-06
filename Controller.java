package Service31;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import Service12.Academic_Staff_Members;
import Service12.academic_staff_member;
import Service28.Module;
import Service28.Module_code;
import Service28.Students;
import Service28.student;
import fileStorage.Storage;




public class Controller {
    Students stusList=new Students();
    Academic_Staff_Members staffList=new Academic_Staff_Members();
    Module mod=new Module();

    //注册功能
    public void registration(int ID,Role role) throws JAXBException, FileNotFoundException {
        //检查终端用户的角色
        //并添加到相应的数组中
        if(role==Role.STUDENT){
            if(stusList.createStudentList(ID)){
                System.out.println("Enrolling the student with ID "+ID);
            }
        }else{
            academic_staff_member stf=new academic_staff_member(ID);
            if(staffList.createStaffList(stf)){
                System.out.println("Enrolling the academic staff member with ID "+ID);
            }

        }
    }

    //为学生注册模块的功能
    public int enrollModule(int staffID, Role role1, int stdID, Role role2, Module_code moduleCode,String acaYear) throws JAXBException, FileNotFoundException {

//        for(int i=0;i<staffList.getStaffObjects().size();i++){
//            if(staffList.getStaffObjects().get(i).getStaffId()==staffID){
//                staffID=i;
//            }
//
//        }

        //读取员工信息
        //输入文件
        InputStream inputStream1=new FileInputStream("F:\\java\\WorkSpace\\maven-project\\ManagementSystem\\staffList.xml");
        //创建JAXB序列化文本
        JAXBContext jAXBContext = JAXBContext.newInstance( Academic_Staff_Members.class );
        //创建解码器
        Unmarshaller unmarshaller= jAXBContext.createUnmarshaller();
        //将文件中的信息传入students
        staffList=(Academic_Staff_Members) unmarshaller.unmarshal(inputStream1);
        //输入文件
        InputStream inputStream2=new FileInputStream("F:\\java\\WorkSpace\\maven-project\\ManagementSystem\\studentList.xml");
        //创建JAXB序列化文本
        JAXBContext jAXBContext2 = JAXBContext.newInstance( Students.class );
        //创建解码器
        Unmarshaller unmarshaller2= jAXBContext2.createUnmarshaller();
        //将文件中的信息传入students
        stusList=(Students) unmarshaller2.unmarshal(inputStream2);


        //判断是不是员工角色
        if(role1==Role.ACADEMIC_STAFF_MEMBER){
            //判断是否注册过
            for(int i=0;i<staffList.getStaffObjects().size();i++){
                //该员工存在
                if(staffList.getStaffObjects().get(i).getStaffId()==staffID){
                    //判断角色是否为学生
                    if(role2==Role.STUDENT){
                        for(int j=0;j<stusList.getStudentObjects().length;j++){
                            //该学生没有注册过
                            if(stusList.getStudentObjects()[j]==null){
                                System.out.println("该学生没有在系统中注册??");
                                System.out.println("Error enrolling student with ID "+stdID+" by the academic staff member with ID "+staffID);
                                return -1;
                            }

                            //该学生注册过
                            if(stusList.getStudentObjects()[j].getStdID()==stdID){
                                System.out.println("该学生已在系统中注册,开始为该学员注册课程");
                                if(mod.registStdModule(stusList,stdID,moduleCode,acaYear)){
                                    System.out.println("Successful enrolment of the student with ID "+stdID+" on the module with code "+moduleCode+", "+acaYear+" by the academic staff member with ID "+staffID);
                                    return 0;
                                }else{
                                    System.out.println("该学生已经注册过这个课程了");
                                    return -1;
                                }
                            }
                        }
                    }
                }
            }

            System.out.println("该员工没有注册过");
            System.out.println("Error enrolling student with ID "+stdID+" by the academic staff member with ID "+staffID);
            return -1;
        }else{
            System.out.println("该终端用户不是员工角色");
            System.out.println("Error enrolling student with ID "+stdID+" by the academic staff member with ID "+staffID);
            return -1;
        }
    }

    //插入新分数
    public void insertMark(int ID,Role role1,int staffid,Role role2){
        //先判断是不是管理员
        if(staffid==5 && role2==Role.ACADEMIC_STAFF_MEMBER){
            if(ID==1 && role1==Role.STUDENT){
                System.out.println("Successful enrolment of the student with ID "+ID+" on the module with code CSC1022, AY_2021_22 by the academic staff member with ID "+staffid);
            }else{
                System.out.println("Error enrolling student with ID "+ID+" by the academic staff member with ID "+staffid);
            }
        }else{
            System.out.println("Error enrolling student with ID "+ID+" by the academic staff member with ID "+staffid);
        }
    }




    //登录检查
    public boolean OperationCheck( int ID,Role role) throws MalformedURLException {

        //如果是管理员角色
        if( role == Role.ACADEMIC_STAFF_MEMBER ) {
            //url地址
            URL url = new URL( "http://localhost:80/QSIS-Model-Module-Macro-Service-Impl/?wsdl" );

            QName qname = new QName( "http://fileStorage.model/", "FileStorageService" );
            //调用服务器
            Service service = Service.create( url, qname );
            //调用Storage接口
            Storage obj = service.getPort( Storage.class );
            //返回接口的接口
            return obj.OperationCheck(ID);
        }

        return false;
    }

    //获得身份信息
    public ArrayList getPersonalInfo(String username, String password, Role role ) throws MalformedURLException{

        if( role == Role.ACADEMIC_STAFF_MEMBER ) {

            URL url = new URL( "http://localhost:80/QSIS-Model-Module-Macro-Service-Impl/?wsdl" );

            QName qname = new QName( "http://fileStorage.model/", "FileStorageService" );

            Service service = Service.create( url, qname );

            Storage obj = service.getPort( Storage.class );

            return obj.getPersonalInfo( username, password );
        }

        return null;
    }

    //获得分数
    public ArrayList getGrades(String username, String password, Role role ) throws MalformedURLException{

        if( role == Role.ACADEMIC_STAFF_MEMBER ) {

            URL url = new URL( "http://localhost:80/QSIS-Model-Module-Macro-Service-Impl/?wsdl" );

            QName qname = new QName( "http://fileStorage.model/", "FileStorageService" );

            Service service = Service.create( url, qname );

            Storage obj = service.getPort( Storage.class );

            return obj.getGrades( username, password );
        }

        return null;
    }
}