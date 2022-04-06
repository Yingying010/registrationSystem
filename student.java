package Service28;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "student")
public class student {
    //id号
    private int stdID;
    //模块代码数组
    private Module_code[] ModuleCode=new Module_code[20];
    //给学生的模块数组
    private Module[] stdModule=new Module[20];

    public student() {
    }

    public student(int stdID) {
        this.stdID = stdID;
    }

    public int getStdID() {
        return stdID;
    }

    public Module_code[] getModuleCode() {
        return ModuleCode;
    }

    public Module[] getStdModule() {
        return stdModule;
    }

    public void setStdID(int stdID) {
        this.stdID = stdID;
    }

    public void setModuleCode(Module_code[] moduleCode) {
        ModuleCode = moduleCode;
    }

    public void setStdModule(Module[] stdModule) {
        this.stdModule = stdModule;
    }
}
