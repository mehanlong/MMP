package com.cn.mis.domain.bean.resp;

import lombok.Data;

import java.util.List;

/**
 * Created by yuejia on 2017/3/8.
 */
@Data
public class ListResult<T> {
    private long total;
    private List<T> rows;
}
