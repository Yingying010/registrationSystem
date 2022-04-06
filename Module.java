package Service28;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;


public class Module {
    Module_code moduleCode;
    String academic_year;
    double mark;

    public void setAcademic_year(String academic_year) {
        this.academic_year = academic_year;
    }

    public void setMark(double mark) {
        this.mark = mark;
    }

    public String getAcademic_year() {
        return academic_year; //AY_2021_22
    }

    public double getMark() {
        return mark;//[0-100]
    }


    //注册学生课程
    // 应该是模块包含code和学年才对，只需要将module传进来就可以了吧
    //传进来的是单个对象，那list是不是不会变呢
    public static boolean registStdModule(Students stus,int stdID,Module_code modeCode,String acaYear) throws FileNotFoundException, JAXBException {

        //创建student对象
        student stu_change=new student(stdID);

        //查找要创建module的学生的位置
        int idx=0;
        for(int i=0;i<stus.getStudentObjects().length;i++){
            //要对null做一个限制
            if(stus.getStudentObjects()[i]!=null && stus.getStudentObjects()[i].getStdID()==stu_change.getStdID()){
                idx=i;
            }
        }

        //如果之前有注册过
        for(int i=0;i<stus.getStudentObjects()[idx].getModuleCode().length;i++){
            if(stus.getStudentObjects()[idx].getModuleCode()[i]==modeCode){
                return false;
            }
        }

        //如果没有
        Module module=new Module();
        module.academic_year="AY_2021_22";
        module.moduleCode=modeCode;

        for(int i=0;i<stus.getStudentObjects()[idx].getModuleCode().length;i++){
            if(stus.getStudentObjects()[idx].getModuleCode()[i]!=modeCode){
                stu_change.getModuleCode()[i]=modeCode;
                stu_change.getStdModule()[i]=module;
                break;
            }
        }

        stus.getStudentObjects()[idx]=stu_change;

        //创建JAXB序列化文件
        JAXBContext jAXBContext=JAXBContext.newInstance(Students.class);

        //输出文件
        OutputStream outputStream = new FileOutputStream( "F:\\java\\WorkSpace\\maven-project\\ManagementSystem\\studentList.xml" );

        //创建编码器
        Marshaller marshaller = jAXBContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        marshaller.marshal( stus, outputStream );
        return true;

    }
}
