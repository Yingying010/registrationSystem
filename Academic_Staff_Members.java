package Service12;

import Service28.Students;
import Service28.student;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.*;
import java.util.ArrayList;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Academic_Staff_Members")
public class Academic_Staff_Members {
    //员工数组
    private ArrayList<academic_staff_member> staffObjects=new ArrayList<>();

    public ArrayList<academic_staff_member> getStaffObjects() {
        return staffObjects;
    }

    public void addStaff(academic_staff_member asm){
        staffObjects.add(asm);
    }

    //读取员工档案
    public static boolean createStaffList(academic_staff_member staff) throws JAXBException, FileNotFoundException {
        //创建Students对象
        Academic_Staff_Members staffList=new Academic_Staff_Members();
        staffList.addStaff(staff);
        //存入文件中
        if(!new File("F:\\java\\WorkSpace\\maven-project\\ManagementSystem\\staffList.xml").exists()){
            //输出流
            OutputStream outputStream = new FileOutputStream( "F:\\java\\WorkSpace\\maven-project\\ManagementSystem\\staffList.xml" );
            //创建JAXB序列化文本
            JAXBContext jAXBContext = JAXBContext.newInstance( Academic_Staff_Members.class );
            //创建编码器
            Marshaller marshaller = jAXBContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            //编码xml文件
            marshaller.marshal(staffList,outputStream);
            return true;
            //文件存在
        }else{
            //输入文件
            InputStream inputStream=new FileInputStream("F:\\java\\WorkSpace\\maven-project\\ManagementSystem\\staffList.xml");
            //创建JAXB序列化文本
            JAXBContext jAXBContext = JAXBContext.newInstance( Academic_Staff_Members.class );
            //创建解码器
            Unmarshaller unmarshaller= jAXBContext.createUnmarshaller();
            //将文件中的信息传入students
            staffList=(Academic_Staff_Members) unmarshaller.unmarshal(inputStream);
            //判断对象是否存在在文件中
            for(int i=0;i< staffList.staffObjects.size();i++){
                //如果id相同，则直接退出不需要创建对象
                if(staffList.staffObjects.get(i).getStaffId()==staff.getStaffId()){
                    System.out.println("员工已经存入过文件中");
                    return false;
                }
            }
            //若对象不存在过
            staffList.addStaff(staff);

            //输出文件
            OutputStream outputStream = new FileOutputStream( "F:\\java\\WorkSpace\\maven-project\\ManagementSystem\\staffList.xml" );

            //创建编码器
            Marshaller marshaller = jAXBContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            marshaller.marshal( staffList, outputStream );
            return true;

            /*
             * 每一次都会覆盖掉上一次的
             * */
        }
    }

}
