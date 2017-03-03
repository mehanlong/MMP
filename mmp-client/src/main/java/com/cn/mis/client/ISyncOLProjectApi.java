package com.cn.mis.client;

import com.cn.mis.domain.bean.resp.RelPermitResp;

public interface ISyncOLProjectApi {
	public String sendData(String json) ;
	public RelPermitResp relPermit(long id);
}
