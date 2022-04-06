package CLI;

import Service28.Module_code;
import Service31.Controller;
import Service31.Role;


public class CLI {
    public static void main( String[] args ) {

        try{
            Controller controller = new Controller();

            //硬编码人员信息
            for(int x=0;x<5;x++){
                controller.registration(x,Role.STUDENT);
            }

            controller.registration(5,Role.ACADEMIC_STAFF_MEMBER);
            controller.registration(6,Role.ACADEMIC_STAFF_MEMBER);

            //注册学生的模块
            controller.enrollModule(0,Role.ACADEMIC_STAFF_MEMBER,1,Role.STUDENT, Module_code.CSC1022,"AY_2021_22");
            controller.enrollModule(5,Role.ACADEMIC_STAFF_MEMBER,7,Role.STUDENT,Module_code.CSC1022,"AY_2021_22");
            controller.enrollModule(5,Role.ACADEMIC_STAFF_MEMBER,1,Role.STUDENT,Module_code.CSC1022,"AY_2021_22");


        }

        catch( Exception ex ) { ex.printStackTrace(); }
    }
}
