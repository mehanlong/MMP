package com.cn.mis.domain.entity.mis;

import lombok.Data;

@Data
public class UFCategory {
    private Integer id;

    private Integer requestId;

    private String category_name;

    private String category_bossid;

    private Integer category_leve;

    private String fid;

    private String top_category;

    private String second_category;

    private String category_all;

}