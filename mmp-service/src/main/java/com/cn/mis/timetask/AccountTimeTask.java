package com.cn.mis.timetask;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import com.cn.mis.domain.bean.pojo.AccountList;
import com.cn.mis.domain.entity.Account;
import com.cn.mis.domain.entity.HrmDepartmentWithBLOBs;
import com.cn.mis.domain.entity.HrmResource;
import com.cn.mis.domain.entity.User;
import com.cn.mis.domain.enums.DbcSelect3;
import com.cn.mis.domain.enums.Level;
import com.cn.mis.domain.enums.LockStatus;
import com.cn.mis.service.IAccountService;
import com.cn.mis.service.IHrmDepartmentService;
import com.cn.mis.service.IHrmResourceService;
import com.cn.mis.service.IUserService;
import com.cn.mis.utils.equals.EqualsUtil;
import com.cn.mis.utils.http.HttpClientUtil;
import com.cn.mis.utils.json.JsonUtil;
import org.springframework.stereotype.Component;

import com.google.gson.reflect.TypeToken;

import lombok.extern.log4j.Log4j;
@Log4j
@Component("accountTimeTask2") 
public class AccountTimeTask {
	@Resource
	private IAccountService accountService;
	
	@Resource
	private IUserService userService;
	
	@Resource
	private IHrmResourceService hrmResourceService;
	
	@Resource
	private IHrmDepartmentService hrmDepartmentService;
	
	public void run() {
		log.info("1");
		sync30(0);
	}
	
	public String syncList(){
		AccountList first = queryAccountList(0);
		int start = 0;
		ArrayList<Account> list = new ArrayList<Account>();
		int index = first.getTotalSize();
		//start每次加30，因为CRM的sql查询限制是一次30条数据
		try {
			while( start<index ){
				AccountList tmp = queryAccountList(start);
				list.addAll(tmp.getRecords());
				start = start+30;
				if(list.size()%1500 == 0 || index<start){
					syncList(list);
					list.clear();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		return "success";
		
	}
	
	
	public String syncList(ArrayList<Account> list){
		try {
			//批量插入一次性不能超过2000个参数
			ArrayList<Integer> tmpids = new ArrayList<Integer>();
			ArrayList<Account> tmp = new ArrayList<Account>();
			List<User> userList = userService.selectAll();
			List<HrmResource> resourceList = hrmResourceService.selectAll();
			List<HrmDepartmentWithBLOBs> departmentList = hrmDepartmentService.selectAll();
			for(int i=0;i<list.size();i++){
				Account account = list.get(i);
				account.setLevelExplain(Level.getName(account.getLevel().toString()));
				account.setDbcSelect3Explain(DbcSelect3.getName(account.getDbcSelect3().toString()));
				account.setLockStatusExplain(LockStatus.getName(account.getLockStatus().toString()));
				for(User user:userList){
					if(account.getOwnerId().equals(user.getId())){
						account.setOwnerPhone(user.getPhone());
						account.setOwnerName(user.getName());
						break;
					}
				}
				for(HrmResource resource: resourceList){
					if(resource.getTelephone() == null){
						resource.setTelephone("15652155049");
					}
					if(resource.getTelephone().equals(account.getOwnerPhone())){
						account.setDepartmentid(resource.getDepartmentid());
						break;
					}
				}
				for(HrmDepartmentWithBLOBs department:departmentList){
					if(department.getId().equals(account.getDepartmentid())){
						account.setDepartment(department.getDepartmentname());
						break;
					}
				}
				tmpids.add(account.getId());
				tmp.add(account);
				//每40条或最后不足40条时执行一次批量插入 
				if ((i+1)%40 == 0 || list.size()-(i+2)<=0){
					List<Account> updateids = accountService.selectByIds(tmpids);
					ArrayList<Account> insertList = new ArrayList<Account>();
					ArrayList<Account> updateList = new ArrayList<Account>();
					
					for(Account accountTmp:tmp){
						for(Account updateid:updateids){
							if(accountTmp.getId().equals(updateid.getId())){
								if(!accountTmp.equals(updateid)){
									accountTmp.setUpdateFlag(true);
									accountTmp.setImportStatus("U");
									updateList.add(accountTmp);
								}
								break;
							}
						}
						if(!accountTmp.isUpdateFlag()){
							insertList.add(accountTmp);
						}
					}
					if(updateList.size()>0){
						accountService.updateBatch(updateList);
					}
					if(insertList.size()>0){
						accountService.insertBatch(insertList);
					}
					tmpids.clear();
					tmp.clear();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		
		return "success";
	}
	
	public String sync30(int start){
		try {
			AccountList first = queryAccountList(start);
			ArrayList<Integer> ids = new ArrayList<Integer>();
			List<User> userList = userService.selectAll();
			List<HrmResource> resourceList = hrmResourceService.selectAll();
			List<HrmDepartmentWithBLOBs> departmentList = hrmDepartmentService.selectAll();
			
			//批量插入
			for(Account account:first.getRecords()){
				ids.add(account.getId());
			}
			List<Account> updateids = accountService.selectByIds(ids);
			ArrayList<Account> insertList = new ArrayList<Account>();
			ArrayList<Account> updateList = new ArrayList<Account>();
			
			for(Account account:first.getRecords()){
				for(Account updateid:updateids){
					if(account.getId().equals(updateid.getId())){
						if(!EqualsUtil.domainEquals(account, updateid)){
							account.setUpdateFlag(true);
							setTmpAccount(account, userList, resourceList, departmentList);
							updateList.add(account);
						}
						break;
					}
				}
				if(!account.isUpdateFlag()){
					setTmpAccount(account, userList, resourceList, departmentList);
					insertList.add(account);
				}
			}
			if(updateList.size()>0){
				accountService.updateBatch(updateList);
			}
			if(insertList.size()>0){
				accountService.insertBatch(insertList);
			}
			if (updateList.size()>0||insertList.size()>0){
				log.info("【物业CRM->MIS】新增数据:"+insertList.size()+",更新数据:"+updateList.size());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		return "success";
	}
	
	private void setTmpAccount(Account accountTmp,List<User> userList,List<HrmResource> resourceList,List<HrmDepartmentWithBLOBs> departmentList){
		if(accountTmp.isUpdateFlag()){
			accountTmp.setImportStatus("U");
		} else {
			accountTmp.setAccount_type("未签约");	
		}
		accountTmp.setLevelExplain(Level.getName(accountTmp.getLevel().toString()));
		accountTmp.setDbcSelect3Explain(DbcSelect3.getName(accountTmp.getDbcSelect3().toString()));
		accountTmp.setLockStatusExplain(LockStatus.getName(accountTmp.getLockStatus().toString()));
		for(User user:userList){
			if(accountTmp.getOwnerId().equals(user.getId())){
				accountTmp.setOwnerPhone(user.getPhone());
				accountTmp.setOwnerName(user.getName());
				break;
			}
		}
		for(HrmResource resource: resourceList){
			if(resource.getTelephone() == null){
				resource.setTelephone("15652155049");
			}
			if(resource.getTelephone().equals(accountTmp.getOwnerPhone())){
				accountTmp.setDepartmentid(resource.getDepartmentid());
				break;
			}
		}
		for(HrmDepartmentWithBLOBs department:departmentList){
			if(department.getId().equals(accountTmp.getDepartmentid())){
				accountTmp.setDepartment(department.getDepartmentname());
				break;
			}
		}
	}
	
	private AccountList queryAccountList(int start){
		HashMap<String,String> params = new HashMap<String,String>();
		params.put("q", "select id,"					//ID
				+ "ownerId,"					//客户所有人
				+ "accountName,"				//公司名称
				+ "level,"						//客户分级（跟进状态）
				+ "state,"						//省
				+ "city,"						//市
				+ "region,"						//区
				+ "dbcInteger1,"				//预估社区数量
				+ "dbcInteger6,"				//预估总户数
				+ "dbcVarchar2,"				//联系人姓名
				+ "dbcReal1,"					//预估管理总面积
				+ "dbcSelect3,"					//物业类型
				+ "dbcSVarchar1,"				//联系人电话
				+ "lockStatus,"					//状态
				+ "createdat,"					//创建日期
				+ "highSeaId"					//所属公海
				+ " from Account order by updatedAt desc limit "
				+ start+","
				+ "30");
		String res = HttpClientUtil.sendCrmSSLPostRequest("https://api.xiaoshouyi.com/data/v1/query",
				params,
				TokenThread.tonkenInfo.getToken_type()+" "+TokenThread.tonkenInfo.getAccess_token(), 
				"UTF-8", 
				"UTF-8");
		
		
		AccountList accountList = (AccountList) JsonUtil.fromJson(new TypeToken<AccountList>(){}.getType(), res);
		return accountList;
	}
}