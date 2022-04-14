package com.jcohy.framework.starter.redis.props;

/**
 * 描述: .
 * <p>
 * Copyright © 2022
 * <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.0.1 3/14/22:17:08
 * @since 2022.0.1
 */
public class Locks {

    /**
     * 是否开启：默认为：false，便于生成配置提示.
     */
    private Boolean enabled = Boolean.FALSE;

    /**
     * 单机配置：redis 服务地址.
     */
    private String address = "redis://127.0.0.1:6379";

    /**
     * 密码配置.
     */
    private String password;

    /**
     * db.
     */
    private Integer database = 0;

    /**
     * 连接池大小.
     */
    private Integer poolSize = 20;

    /**
     * 最小空闲连接数.
     */
    private Integer idleSize = 5;

    /**
     * 连接空闲超时，单位：毫秒.
     */
    private Integer idleTimeout = 60000;

    /**
     * 连接超时，单位：毫秒.
     */
    private Integer connectionTimeout = 3000;

    /**
     * 命令等待超时，单位：毫秒.
     */
    private Integer timeout = 10000;

    /**
     * 集群模式，单机：single，主从：master，哨兵模式：sentinel，集群模式：cluster.
     */
    private Mode mode = Mode.SINGLE;

    /**
     * 主从模式，主地址.
     */
    private String masterAddress;

    /**
     * 主从模式，从地址.
     */
    private String[] slaveAddress;

    /**
     * 哨兵模式：主名称.
     */
    private String masterName;

    /**
     * 哨兵模式地址.
     */
    private String[] sentinelAddress;

    /**
     * 集群模式节点地址.
     */
    private String[] nodeAddress;

    public Boolean getEnabled() {
        return this.enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getDatabase() {
        return this.database;
    }

    public void setDatabase(Integer database) {
        this.database = database;
    }

    public Integer getPoolSize() {
        return this.poolSize;
    }

    public void setPoolSize(Integer poolSize) {
        this.poolSize = poolSize;
    }

    public Integer getIdleSize() {
        return this.idleSize;
    }

    public void setIdleSize(Integer idleSize) {
        this.idleSize = idleSize;
    }

    public Integer getIdleTimeout() {
        return this.idleTimeout;
    }

    public void setIdleTimeout(Integer idleTimeout) {
        this.idleTimeout = idleTimeout;
    }

    public Integer getConnectionTimeout() {
        return this.connectionTimeout;
    }

    public void setConnectionTimeout(Integer connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public Integer getTimeout() {
        return this.timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public Mode getMode() {
        return this.mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public String getMasterAddress() {
        return this.masterAddress;
    }

    public void setMasterAddress(String masterAddress) {
        this.masterAddress = masterAddress;
    }

    public String[] getSlaveAddress() {
        return this.slaveAddress;
    }

    public void setSlaveAddress(String[] slaveAddress) {
        this.slaveAddress = slaveAddress;
    }

    public String getMasterName() {
        return this.masterName;
    }

    public void setMasterName(String masterName) {
        this.masterName = masterName;
    }

    public String[] getSentinelAddress() {
        return this.sentinelAddress;
    }

    public void setSentinelAddress(String[] sentinelAddress) {
        this.sentinelAddress = sentinelAddress;
    }

    public String[] getNodeAddress() {
        return this.nodeAddress;
    }

    public void setNodeAddress(String[] nodeAddress) {
        this.nodeAddress = nodeAddress;
    }

    public enum Mode {

        /**
         * 集群模式，单机：single，主从：master，哨兵模式：sentinel，集群模式：cluster.
         */
        SINGLE, MASTER, SENTINEL, CLUSTER

    }

}
