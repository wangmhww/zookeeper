package com.wm.curator.zookeepercurator;

/**
 * @author wangm
 * @title: CuratorClusterBase
 * @projectName zookeeper-praent
 * @description: TODO
 * @date 2021/4/123:40
 */
public class CuratorClusterBase extends CuratorStandaloneBase{
    private final static String CLUSTER_CONNECT_STR = "47.110.71.90:2181,47.110.71.90:2182,47.110.71.90:2183,47.110.71.90:2184";

    private final static Integer CLUSTER_SESSION_TIMEOUT = 100*1000;

    @Override
    public String getConnectStr() {
        return CLUSTER_CONNECT_STR;
    }

    @Override
    public int getSessionTimeoutMs() {
        return CLUSTER_SESSION_TIMEOUT;
    }
}
