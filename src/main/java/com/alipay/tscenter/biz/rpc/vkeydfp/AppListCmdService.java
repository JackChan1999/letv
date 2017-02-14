package com.alipay.tscenter.biz.rpc.vkeydfp;

import com.alipay.mobile.framework.service.annotation.OperationType;
import com.alipay.tscenter.biz.rpc.vkeydfp.request.AppListCmdRequest;
import com.alipay.tscenter.biz.rpc.vkeydfp.result.AppListCmdResult;

public interface AppListCmdService {
    @OperationType("alipay.security.vkeyDFP.appListCmd.get")
    AppListCmdResult getAppListCmd(AppListCmdRequest appListCmdRequest);

    @OperationType("alipay.security.vkeyDFP.appListCmd.reGet")
    AppListCmdResult reGetAppListCmd(AppListCmdRequest appListCmdRequest);
}
