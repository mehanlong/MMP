package com.cn.mis.timetask;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.cn.mis.domain.bean.pojo.UserList;
import com.cn.mis.domain.bean.pojo.UserListDetail;
import com.cn.mis.domain.entity.User;
import com.cn.mis.service.IUserService;
import com.cn.mis.utils.equals.EqualsUtil;
import com.cn.mis.utils.http.HttpClientUtil;
import com.cn.mis.utils.json.JsonUtil;
import org.springframework.stereotype.Component;

import com.google.gson.reflect.TypeToken;

import lombok.extern.log4j.Log4j;
@Log4j
@Component("userTimeTask") 
public class UserTimeTask {
	@Resource
	private IUserService userService;

	public void run() {
		syncUser();
	}
	
	private List<UserListDetail> getUserList(){
		int start = 0;
		ArrayList<UserListDetail> list = new ArrayList<UserListDetail>();
		String res = HttpClientUtil.sendCrmSSLGetReqset("https://api.xiaoshouyi.com/data/v1/objects/user/list?start="+start+"&count=100", null,TokenThread.tonkenInfo.getToken_type()+" "+TokenThread.tonkenInfo.getAccess_token());
		UserList first = (UserList)JsonUtil.fromJson(new TypeToken<UserList>(){}.getType(), res);
		list.addAll(first.getRecords());
		while(start<first.getTotleSize()){
			start =start+100;
			UserList tmp = getUserList(start);
			list.addAll(tmp.getRecords());
		}
		
		return list;
	}
	
	
	private UserList getUserList(int start){
		
		String res = HttpClientUtil.sendCrmSSLGetReqset("https://api.xiaoshouyi.com/data/v1/objects/user/list?start="+start+"&count=100", null,TokenThread.tonkenInfo.getToken_type()+" "+TokenThread.tonkenInfo.getAccess_token());
		UserList userList = (UserList) JsonUtil.fromJson(new TypeToken<UserList>(){}.getType(), res);
		
		return userList;
	}
	
	public String syncUser(){
		
		try {
			//批量插入一次性不能超过2000个参数
			List<UserListDetail> userList = getUserList();
			ArrayList<User> tmp = new ArrayList<User>();
			ArrayList<Integer> tmpids = new ArrayList<Integer>();
			for(int i=0;i<userList.size();i++){
				String res = HttpClientUtil.sendCrmSSLGetReqset("https://api.xiaoshouyi.com/data/v1/objects/user/info?id="+userList.get(i).getId(), null,TokenThread.tonkenInfo.getToken_type()+" "+TokenThread.tonkenInfo.getAccess_token());
				User user = (User)JsonUtil.fromJson(new TypeToken<User>(){}.getType(), res);
				if(user != null){
					tmpids.add(Integer.valueOf(user.getId()+""));
					tmp.add(user);	
				}
				//每100条或最后不足100条时执行一次批量插入 
				if ((i+1)%100 == 0 || userList.size()-(i+1) == 0){
					List<User> updateids = userService.selectByIds(tmpids);
					ArrayList<User> updateList = new ArrayList<User>();
					ArrayList<User> insertList = new ArrayList<User>();
					for(User tmpUser:tmp){
						for(User updateid:updateids){
							if(updateid.getId().equals(tmpUser.getId())){
								if(!EqualsUtil.domainEquals(tmpUser, updateid)){
									tmpUser.setUpdateFlag(true);
									updateList.add(tmpUser);
								}
								break;
							}
						}
						if(!tmpUser.isUpdateFlag()){
							insertList.add(tmpUser);
						}
					}
					if(updateList.size()>0){
						userService.updateBatch(updateList);
					}
					if(insertList.size()>0){
						userService.insertBatch(insertList);
					}
					if (updateList.size()>0||insertList.size()>0){
						log.info("【用户CRM->MIS】新增数据："+insertList.size()+"，更新数据："+updateList.size() );
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
}