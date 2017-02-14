package com.alipay.tscenter.biz.rpc.vkeydfp;

import com.alipay.mobile.framework.service.annotation.OperationType;
import com.alipay.tscenter.biz.rpc.vkeydfp.request.DeviceDataReportRequest;
import com.alipay.tscenter.biz.rpc.vkeydfp.result.AppListResult;
import com.alipay.tscenter.biz.rpc.vkeydfp.result.DeviceDataReportResult;

public interface DeviceDataReportService {
    @OperationType("alipay.security.vkeyDFP.appList.get")
    AppListResult getAppList(String str);

    @OperationType("alipay.security.vkeyDFP.staticData.report")
    DeviceDataReportResult reportStaticData(DeviceDataReportRequest deviceDataReportRequest);
}
