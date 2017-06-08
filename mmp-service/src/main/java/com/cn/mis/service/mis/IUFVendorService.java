package com.cn.mis.service.mis;

import com.cn.mis.domain.entity.mis.ImageFiled;
import com.cn.mis.domain.entity.mis.UFVendor;
import com.cn.mis.domain.entity.mis.UFVendorWithResource;

import java.util.List;

/**
 * Created by yuejia on 2017/5/19.
 */
public interface IUFVendorService {
    int deleteByPrimaryKey(Integer id);

    int insert(UFVendor record);

    int insertSelective(UFVendor record);

    UFVendor selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UFVendor record);

    int updateByPrimaryKey(UFVendor record);

    List<UFVendorWithResource> selectAllNeedInit();

    List<UFVendorWithResource> selectAllNeedUpdate();

    int updateBossReturnData(UFVendorWithResource record);

    List<ImageFiled> SelImageFiledPhoto(List<String> ids);

    List<ImageFiled> SelImageFiledComment(List<String> ids);
}
