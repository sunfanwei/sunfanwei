package com.example.demosfw;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @ClassName SystemLogAspect
 * @Author Administrator
 * @Describe  定义切入面类
 */
@Aspect
@Component
public class SystemLogAspect {


    /**
     * 注解Pointcut切入点
     * 定义出一个或一组方法，当执行这些方法时可产生通知
     * 指向你的切面类方法
     * 由于这里使用了自定义注解所以指向你的自定义注解
     */
    @Pointcut("@annotation(com.example.demosfw.SystemCrmlog)")
    public void crmAspect() {
    }


    /**
     *抛出异常后通知（@AfterThrowing）：方法抛出异常退出时执行的通知
     * 注意在这里不能使用ProceedingJoinPoint
     * 不然会报错ProceedingJoinPoint is only supported for around advice
     * throwing注解为错误信息
     * @param joinPoint
     * @param ex
     */
    @AfterThrowing(value="crmAspect()", throwing="ex")
    public void afterThrowingMethod(JoinPoint joinPoint, Exception ex) throws Exception {
        HttpServletRequest httpServletRequest = getHttpServletRequest();
        //获取管理员用户信息\
        Map<String, Object> user = new HashMap<>();
        CrmLogMessage log=new CrmLogMessage();
        //获取需要的信息
        String context=getServiceMthodDescription(joinPoint);
        String usr_name="";
        String rolename="";
        if(user!=null){
            usr_name = user.get("usr_name").toString();
            rolename=user.get("rolename").toString();
        }
        //管理员姓名
        log.setUserName(usr_name);
        //角色名
        log.setUserRole(rolename);
        //日志信息
        log.setContent(usr_name+context);
        //设置参数集合
        log.setRemarks(getServiceMthodParams(joinPoint));
        //设置表名
        log.setTableName(getServiceMthodTableName(joinPoint));
        //操作时间
        SimpleDateFormat sif=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        log.setDateTime(sif.format(new Date()));
        //设置ip地址
        log.setIp(httpServletRequest.getRemoteAddr());
        //设置请求地址
        log.setRequestUrl(httpServletRequest.getRequestURI());
        //执行结果
        log.setResult("执行失败");
        //错误信息
        log.setExString(ex.getMessage());
        //将数据保存到数据库
        /*Sys_logDao sysLogDao=new Sys_logDao();
        sysLogDao.addSys_log(log);*/
    }


    /**
     * 返回后通知（@AfterReturning）：在某连接点（joinpoint）
     * 正常完成后执行的通知：例如，一个方法没有抛出任何异常，正常返回
     * 方法执行完毕之后
     * 注意在这里不能使用ProceedingJoinPoint
     * 不然会报错ProceedingJoinPoint is only supported for around advice
     * crmAspect()指向需要控制的方法
     *  returning  注解返回值
     * @param joinPoint
     * @param returnValue  返回值
     * @throws Exception
     */
    @AfterReturning(value = "crmAspect()",returning = "returnValue")
    public  void doCrmLog(JoinPoint joinPoint,Object returnValue) throws Exception {
        HttpServletRequest httpServletRequest = getHttpServletRequest();
        //获取管理员用户信息
        //WebUtil webUtil = new WebUtil();
        Map<String, Object> user = new HashMap<>();
        CrmLogMessage log=new CrmLogMessage();
        String context=getServiceMthodDescription(joinPoint);

        String usr_name="";
        String rolename="";
        if(user!=null){
            usr_name = user.get("usr_name").toString();
            rolename=user.get("rolename").toString();
        }
        //管理员姓名
        log.setUserName(usr_name);
        //角色名
        log.setUserRole(rolename);
        //日志信息
        log.setContent(usr_name+context);
        //设置参数集合
        log.setRemarks(getServiceMthodParams(joinPoint));
        //设置表名
        log.setTableName(getServiceMthodTableName(joinPoint));
        //操作时间
        SimpleDateFormat sif=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        log.setDateTime(sif.format(new Date()));
        //设置ip地址
        log.setIp(httpServletRequest.getRemoteAddr());
        //设置请求地址
        log.setRequestUrl(httpServletRequest.getRequestURI());
        if(returnValue!=null){
            if(returnValue instanceof List){
                List ls= (List) returnValue;
                if(ls.size()>0){
                    log.setResult("执行成功");
                }else{
                    log.setResult("执行成功");
                }
            }else if(returnValue instanceof Boolean){
                Boolean falg= (Boolean) returnValue;
                if(falg){
                    log.setResult("执行成功");
                }else{
                    log.setResult("执行失败");
                }
            }else if(returnValue instanceof Integer){
                Integer i= (Integer) returnValue;
                if(i>0){
                    log.setResult("执行成功");
                }else{
                    log.setResult("执行失败");
                }
            }else{
                log.setResult("执行成功");
            }

        }
        //将数据保存到数据库
/*        Sys_logDao sysLogDao=new Sys_logDao();
        sysLogDao.addSys_log(log);*/
    }


    /**
     *获取自定义注解里的日志描述
     * @param joinPoint
     * @return 返回注解里面的日志描述
     * @throws Exception
     */
    private String getServiceMthodDescription(JoinPoint joinPoint)
            throws Exception {
        //类名
        String targetName = joinPoint.getTarget().getClass().getName();
        //方法名
        String methodName = joinPoint.getSignature().getName();
        //参数
        Object[] arguments = joinPoint.getArgs();
        //通过反射获取示例对象
        Class targetClass = Class.forName(targetName);
        //通过实例对象方法数组
        Method[] methods = targetClass.getMethods();
        String description = "";
        for(Method method : methods) {
            //判断方法名是不是一样
            if(method.getName().equals(methodName)) {
                //对比参数数组的长度
                Class[] clazzs = method.getParameterTypes();
                if(clazzs.length == arguments.length) {
                    //获取注解里的日志信息
                    description = method.getAnnotation(SystemCrmlog.class).description();
                    break;
                }
            }
        }
        return description;
    }

    /**
     *获取自定义注解里的表名
     * @param joinPoint
     * @return 返回注解里的表名字
     * @throws Exception
     */
    private String getServiceMthodTableName(JoinPoint joinPoint)
            throws Exception {
        //类名
        String targetName = joinPoint.getTarget().getClass().getName();
        //方法名
        String methodName = joinPoint.getSignature().getName();
        //参数
        Object[] arguments = joinPoint.getArgs();
        //通过反射获取示例对象
        Class targetClass = Class.forName(targetName);
        //通过实例对象方法数组
        Method[] methods = targetClass.getMethods();
        //表名
        String tableName = "";
        for (Method method : methods) {
            //判断方法名是不是一样
            if (method.getName().equals(methodName)) {
                //对比参数数组的长度
                Class[] clazzs = method.getParameterTypes();
                if (clazzs.length == arguments.length) {
                    //获取注解里的表名
                    tableName = method.getAnnotation(SystemCrmlog.class).tableName();
                    break;
                }
            }
        }
        return tableName;
    }

    /**
     * 获取json格式的参数用于存储到数据库中
     * @param joinPoint
     * @return
     * @throws Exception
     */
    private String getServiceMthodParams(JoinPoint joinPoint)
            throws Exception {
        Object[] arguments = joinPoint.getArgs();
        ObjectMapper om=new ObjectMapper();
        return om.writeValueAsString(arguments);
    }

    /**
     * 获取当前的request
     * 这里如果报空指针异常是因为单独使用spring获取request
     * 需要在配置文件里添加监听
     * <listener>
     * <listener-class>
     * org.springframework.web.context.request.RequestContextListener
     * </listener-class>
     * </listener>
     * @return
     */
    public HttpServletRequest getHttpServletRequest(){
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes)ra;
        HttpServletRequest request = sra.getRequest();
        return request;
    }

}