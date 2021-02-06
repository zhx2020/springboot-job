package com.zwl.job.service;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.zwl.job.dao.UserDao;
import com.zwl.job.entity.Result;
import com.zwl.job.entity.User;
import com.zwl.job.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public List<User> findAll() {
        return userDao.findAll();
    }

    public User findById(String id) {
        return userDao.findById(id);
    }

    public User findFriendById(String id) {
        return userDao.findFriendById(id);
    }

    public Result login(String userId, String userPass) {
        User user = new User();
        user.setUserId(userId);
        user.setUserPass(userPass);
        User res = userDao.selectOne(user);
        if (res != null) {
            String token = JwtUtil.sign(userId, userPass);
            return new Result(true, "登录成功", token);
        }
        return new Result(false, "用户名密码错误");
    }

    public Result check(String token) {
        if (token == null) {
            return new Result(false, "用户未登录");
        } else {
            String userId = JwtUtil.verify(token);
            if (!userId.equals("0")) {
                User res = userDao.findById(userId);
                return new Result(true, "用户已登录", res);
            } else {
                return new Result(false, "登录失效,请重新登录");
            }
        }
    }

    public Result send(String phone) throws Exception {
        //产品名称:云通信短信API产品,开发者无需替换
        String product = "Dysmsapi";
        //产品域名,开发者无需替换
        String domain = "dysmsapi.aliyuncs.com";

        // TODO 此处需要替换成开发者自己的AK(在阿里云访问控制台寻找)
        String accessKeyId = "LTAIChobfywrYMTF";
        String accessKeySecret = "f9np2fAAD9inlgj84hDzbOSqAuUEj6";

        String verifyCode = String.valueOf(new Random().nextInt(900000) + 100000);

        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);

        //组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        //必填:待发送手机号
        request.setPhoneNumbers(phone);
        //必填:短信签名-可在短信控制台中找到
        request.setSignName("淘淘商城");
        //必填:短信模板-可在短信控制台中找到
        request.setTemplateCode("SMS_166655168");
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        //request.setTemplateParam("{\"name\":\"Tom\", \"code\":\"123\"}");
//        request.setTemplateParam("{\"code\":\"123456\"}");
        request.setTemplateParam("{\"code\":" + verifyCode + "}");

        //选填-上行短信扩展码(无特殊需求用户请忽略此字段)
        //request.setSmsUpExtendCode("90997");

        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        request.setOutId("yourOutId");

        //hint 此处可能会抛出异常，注意catch
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);

        System.out.println("当前的注册的手机号：" + phone + "，验证码：" + verifyCode);
        redisTemplate.opsForValue().set(phone, verifyCode, 5 * 60, TimeUnit.SECONDS);
        return new Result(true, "发送成功");
    }

    public Result verify(String phone, String code) {
        String verifyCode = redisTemplate.opsForValue().get(phone);
        if (verifyCode == null) {
            return new Result(false, "验证码失效");
        } else {
            if (verifyCode.equals(code)) {
                return new Result(true, "验证码正确");
            } else {
                return new Result(false, "验证码错误");
            }
        }
    }

    public Result register(User user) {
        User res = userDao.selectByPrimaryKey(user);
        if (res == null) {
            userDao.insertSelective(user);
            return new Result(true, "注册成功");
        } else {
            return new Result(false, "手机号已注册");
        }
    }

    public Result modify(User user) {
        int count = userDao.updateByPrimaryKeySelective(user);
        if (count == 1) {
            return new Result(true, "修改成功");
        } else {
            return new Result(false, "修改失败");
        }
    }

    public Result del(String id) {
        userDao.deleteByPrimaryKey(id);
        return new Result(true, "删除成功");
    }
}
