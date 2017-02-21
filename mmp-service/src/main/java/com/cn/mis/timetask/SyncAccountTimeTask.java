package com.cn.mis.timetask;

import com.cn.mis.utils.date.DateStyle;
import com.cn.mis.utils.date.DateUtil;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by yuejia on 2017/2/16.
 */
@Log4j
@Component("syncAccountTimeTask")
public class SyncAccountTimeTask {

    public void run() {
        log.info("syncAccoutnTimeTask："+ DateUtil.DateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
    }
}
