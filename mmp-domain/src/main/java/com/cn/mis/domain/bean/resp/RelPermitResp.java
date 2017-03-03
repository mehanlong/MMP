package com.cn.mis.domain.bean.resp;

import java.io.Serializable;

/**
 * Created by yuejia on 2017/3/1.
 */
public class RelPermitResp extends BaseResp{

    private static final long serialVersionUID = 1507985000575046702L;
    private boolean canRelease;

    public boolean isCanRelease() {
        return canRelease;
    }

    public void setCanRelease(boolean canRelease) {
        this.canRelease = canRelease;
    }


}