package com.letv.core.bean;

public class UserBean implements LetvBaseBean {
    public static final String ISVIP_NO = "0";
    public static final String ISVIP_YES = "1";
    private static final long serialVersionUID = 1;
    public String address = "";
    public String birthday = "";
    public String chkvipday = "";
    public String city = "";
    public String contactEmail = "";
    public String delivery = "";
    public String education = "";
    public String email = "";
    public String gender = "";
    public String income = "";
    public String industry = "";
    public String isIdentify;
    public String isvip = "";
    public String job = "";
    public String lastLoginIp = "";
    public String lastLoginTime = "";
    public String lastModifyTime = "";
    public String level_id = "";
    public String mac = "";
    public String mobile = "";
    public String msn = "";
    public String name = "";
    public String nickname = "";
    public String picture = "";
    public String point = "";
    public String postCode = "";
    public String province = "";
    public String qq = "";
    public String registIp = "";
    public String registService = "";
    public String registTime = "";
    public String score;
    public String ssouid;
    public String status = "";
    public String tv_token;
    public String uid = "";
    public String username = "";
    public VipInfo vipInfo;
    public String vipday = "";

    public static class VipInfo implements LetvBaseBean {
        public long canceltime;
        public String id;
        public long lastdays;
        public int orderFrom;
        public int productid;
        public long seniorcanceltime;
        public String uinfo;
        public String username;
        public int vipType;
    }

    public String toString() {
        return "LetvUser [uid=" + this.uid + ", username=" + this.username + ", status=" + this.status + ", gender=" + this.gender + ", qq=" + this.qq + ", registIp=" + this.registIp + ", registTime=" + this.registTime + ", lastModifyTime=" + this.lastModifyTime + ", birthday=" + this.birthday + ", nickname=" + this.nickname + ", msn=" + this.msn + ", registService=" + this.registService + ", email=" + this.email + ", mobile=" + this.mobile + ", province=" + this.province + ", city=" + this.city + ", postCode=" + this.postCode + ", address=" + this.address + ", mac=" + this.mac + ", picture=" + this.picture + ", name=" + this.name + ", contactEmail=" + this.contactEmail + ", delivery=" + this.delivery + ", point=" + this.point + ", level_id=" + this.level_id + ", isvip=" + this.isvip + ", education=" + this.education + ", industry=" + this.industry + ", job=" + this.job + ", income=" + this.income + ", lastLoginTime=" + this.lastLoginTime + ", lastLoginIp=" + this.lastLoginIp + "]";
    }
}
