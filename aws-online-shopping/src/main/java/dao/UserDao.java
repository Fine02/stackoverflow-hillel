package dao;

import com.ra.course.aws.online.shopping.entity.user.AccountStatus;
import com.ra.course.aws.online.shopping.entity.user.Admin;
import com.ra.course.aws.online.shopping.entity.user.Member;

public interface UserDao {
    Member findUserByUserNameDao();
    Admin findAdminByUserNameDao();
    String addUserDao();
    AccountStatus updateUserDao();
}
