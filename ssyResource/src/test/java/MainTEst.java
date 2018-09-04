import com.wande.ssy.entity.User;
import org.junit.Test;

public class MainTEst {

    @Test
    public void sgd(){
        User u = new User();
        u.setPhone("1212151");
        User user = new User().findFirst("SELECT * FROM eqp_user where phone = ?",u.getPhone());
        if (user != null) {
            System.out.println("该手机已经注册");
        }else {
            //否则保存到数据库
            System.out.println("可以注册");
        }
    }


    public static void main(String[] args) {
        System.out.println("sldg ");
    }
}
