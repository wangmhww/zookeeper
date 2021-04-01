package com.wm.zookeeper.zookeeperfirst;

/**
 * @author wangm
 * @title: ClusterBase
 * @projectName zookeeper-praent
 * @description: TODO
 * @date 2021/4/123:09
 */
public class ClusterBase extends StandaloneBase {
    private final static String CLUSTER_CONNECT_STR = "47.110.71.90:2181,47.110.71.90:2182,47.110.71.90:2183,47.110.71.90:2184";

    private final static Integer CLUSTER_SESSION_TIMEOUT = 30*1000;

    @Override
    public String getConnectStr() {
        return CLUSTER_CONNECT_STR;
    }

    @Override
    public int getTimeOut() {
        return CLUSTER_SESSION_TIMEOUT;
    }
}
