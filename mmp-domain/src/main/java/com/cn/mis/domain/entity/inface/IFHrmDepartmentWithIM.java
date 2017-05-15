package com.cn.mis.domain.entity.inface;

import java.util.Date;

/**
 * Created by yuejia on 2017/4/14.
 */
public class IFHrmDepartmentWithIM extends IFHrmDepartment{
    private Integer imid;

    private Integer imsyncflag;

    private Date imcreatetime;

    private Date immodifytime;

    public Integer getImid() {
        return imid;
    }

    public void setImid(Integer imid) {
        this.imid = imid;
    }

    public Integer getImsyncflag() {
        return imsyncflag;
    }

    public void setImsyncflag(Integer imsyncflag) {
        this.imsyncflag = imsyncflag;
    }

    public Date getImcreatetime() {
        return imcreatetime;
    }

    public void setImcreatetime(Date imcreatetime) {
        this.imcreatetime = imcreatetime;
    }

    public Date getImmodifytime() {
        return immodifytime;
    }

    public void setImmodifytime(Date immodifytime) {
        this.immodifytime = immodifytime;
    }
}
