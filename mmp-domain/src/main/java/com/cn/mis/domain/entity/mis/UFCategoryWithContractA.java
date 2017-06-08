package com.cn.mis.domain.entity.mis;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by yuejia on 2017/5/31.
 */
@Data
public class UFCategoryWithContractA {
    private Integer id;
    private Integer mainid;
    private String second_category;
    private String third_category;
    private BigDecimal category_rate;
    private Integer update_flag;
}
