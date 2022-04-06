package Service12;

import Service28.Module;
import Service28.Module_code;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "academic_staff_member")
public class academic_staff_member {
    //员工id
    int staffId;
    //员工模块code
    Module_code[] module_codes=new Module_code[2];
    //员工模块
    Module[] staffModule=new Module[2];

    public academic_staff_member() {
    }

    public academic_staff_member(int staffId) {
        this.staffId = staffId;
    }

    public int getStaffId() {
        return staffId;
    }


}
