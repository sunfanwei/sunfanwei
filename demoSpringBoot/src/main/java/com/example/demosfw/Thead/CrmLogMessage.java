package com.example.demosfw.Thead;

/**
 * @ClassName CrmLogMessage
 * @Author Administrator
 * @Describe 数据库日志类
 */
public class CrmLogMessage {
    private Integer logid;//日志id
    private String UserName;//管理员姓名
    private String UserRole;//管理员角色
    private String Content;//日志描述
    private String Remarks;//参数集合
    private String TableName;//表格名称
    private String DateTime;//操作时间
    private String resultValue;//返回值
    private String ip;//ip地址
    private String  requestUrl;//请求地址
    private String result;//操作结果
    private  String ExString;//错误信息

    public CrmLogMessage() {
    }

    @Override
    public String toString() {
        return "CrmLogMessage{" +
                "logid=" + logid +
                ", UserName='" + UserName + '\'' +
                ", UserRole='" + UserRole + '\'' +
                ", Content='" + Content + '\'' +
                ", Remarks='" + Remarks + '\'' +
                ", TableName='" + TableName + '\'' +
                ", DateTime='" + DateTime + '\'' +
                ", resultValue='" + resultValue + '\'' +
                ", ip='" + ip + '\'' +
                ", requestUrl='" + requestUrl + '\'' +
                ", result='" + result + '\'' +
                ", ExString='" + ExString + '\'' +
                '}';
    }

    public CrmLogMessage(Integer logid, String userName, String userRole, String content, String remarks, String tableName, String dateTime, String resultValue, String ip, String requestUrl, String result, String exString) {
        this.logid = logid;
        UserName = userName;
        UserRole = userRole;
        Content = content;
        Remarks = remarks;
        TableName = tableName;
        DateTime = dateTime;
        this.resultValue = resultValue;
        this.ip = ip;
        this.requestUrl = requestUrl;
        this.result = result;
        ExString = exString;
    }

    public String getExString() {
        return ExString;
    }

    public void setExString(String exString) {
        ExString = exString;
    }

    public Integer getLogid() {
        return logid;
    }

    public void setLogid(Integer logid) {
        this.logid = logid;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserRole() {
        return UserRole;
    }

    public void setUserRole(String userRole) {
        UserRole = userRole;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getRemarks() {
        return Remarks;
    }

    public void setRemarks(String remarks) {
        Remarks = remarks;
    }

    public String getTableName() {
        return TableName;
    }

    public void setTableName(String tableName) {
        TableName = tableName;
    }

    public String getDateTime() {
        return DateTime;
    }

    public void setDateTime(String dateTime) {
        DateTime = dateTime;
    }

    public String getResultValue() {
        return resultValue;
    }

    public void setResultValue(String resultValue) {
        this.resultValue = resultValue;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}