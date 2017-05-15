package com.cn.mis.hessian;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.cn.mis.client.ISyncOLProjectApi;
import com.cn.mis.domain.bean.req.SyncOLReq;
import com.cn.mis.domain.bean.resp.BaseResp;
import com.cn.mis.domain.bean.resp.RelPermitResp;
import com.cn.mis.domain.entity.mis.OLSyncProject;
import com.cn.mis.domain.entity.mis.UFCommunityWithBLOBs;
import com.cn.mis.service.mis.IOLSyncProjectService;
import com.cn.mis.service.mis.IUFCommunityService;
import com.cn.mis.utils.json.JsonUtil;
import org.springframework.stereotype.Service;

import com.google.gson.reflect.TypeToken;

@Service("syncOLProjectHessService")
public class SyncOLProjectHessService implements ISyncOLProjectApi {
	
	@Resource
	private IOLSyncProjectService iOLsyncProjectService;
	
	@Resource
	private IUFCommunityService iUFCommunityService;

	@Override
	public String sendData(String json) {
		BaseResp resp = new BaseResp();
		try {
			SyncOLReq syncOLReq = (SyncOLReq) JsonUtil.fromJson(new TypeToken<SyncOLReq>(){}.getType(), json);
			if(syncOLReq != null){
				List<OLSyncProject> list = syncOLReq.getRecords();
				ArrayList<Long> tmpids = new ArrayList<Long>();
				ArrayList<OLSyncProject> tmplist = new ArrayList<OLSyncProject>();
				for(int i=0;i<list.size();i++){
					OLSyncProject olsync = list.get(i);
					tmpids.add(olsync.getId());
					tmplist.add(olsync);
					//每50条或最后不足50条时执行一次批量插入 
					if ((i+1)%50 == 0 || list.size()-(i+2)<=0){
						List<OLSyncProject> updateids = iOLsyncProjectService.selectByIds(tmpids);
						ArrayList<OLSyncProject> insertList = new ArrayList<OLSyncProject>();
						ArrayList<OLSyncProject> updateList = new ArrayList<OLSyncProject>();
						for(OLSyncProject olsyncTmp:tmplist){
							for(OLSyncProject updateid:updateids){
								if(olsyncTmp.getId().equals(updateid.getId())){
									updateList.add(olsyncTmp);
									olsyncTmp.setUpdateFlag(true);
									olsyncTmp.setImportStatus("U");
									break;
								}
							}
							if(!olsyncTmp.isUpdateFlag()){
								insertList.add(olsyncTmp);
							}
						}
						if(updateList.size()>0){
							iOLsyncProjectService.updateBatch(updateList);
						}
						if(insertList.size()>0){
							iOLsyncProjectService.insertBatch(insertList);
						}
						tmpids.clear();
						tmplist.clear();
					}
				}
				resp.setSuccess(true);
				resp.setStatusCode(200);
				resp.setErro("调用成功");
			} else {
				resp.setSuccess(false);
				resp.setStatusCode(401);
				resp.setErro("数据格式错误");
				return JsonUtil.toJson(resp);
			}
		} catch (Exception e) {
			resp.setSuccess(false);
			resp.setStatusCode(501);
			resp.setErro("接口异常");
			e.printStackTrace();
		}
		return JsonUtil.toJson(resp);
	}

	@Override
	public RelPermitResp relPermit(long id) {
		RelPermitResp resp = new RelPermitResp();
		UFCommunityWithBLOBs ufCommunity = iUFCommunityService.selectByBossId(id);
		if(ufCommunity != null){
			if(ufCommunity.getBossFaceFlag() != null){
				if(ufCommunity.getBossFaceFlag().equals(1)){
					resp.setSuccess(true);
					resp.setStatusCode(200);
					resp.setCanRelease(true);
				} else {
					resp.setSuccess(true);
					resp.setStatusCode(400);
					resp.setCanRelease(false);
					resp.setErro("该社区尚未进行【门禁安装确认】，请到MIS系统-【社区交底及上线】流程中进行处理");
				}
			} else {
				resp.setSuccess(true);
				resp.setStatusCode(400);
				resp.setCanRelease(false);
				resp.setErro("该社区尚未进行【门禁安装确认】，请到MIS系统-【社区交底及上线】流程中进行处理");
			}
		}else{
			resp.setSuccess(true);
			resp.setStatusCode(404);
			resp.setCanRelease(true);
			resp.setErro("MIS系统中无此社区信息，请联系相关人员在MIS中补录此社区");
		}
		return resp;
	}

}
