package com.macro.cloud.service;

/**
 * 全局唯一 ：ID 的全局唯一性肯定是首先要满足的！
 * 高性能 ： 分布式 ID 的生成速度要快，对本地资源消耗要小。
 * 高可用 ：生成分布式 ID 的服务要保证可用性无限接近于 100%。
 * 方便易用 ：拿来即用，使用方便，快速接入！
 *
 *
 */
public interface DistributeIdService {

    /**
     *
     * 生成全局唯一的分布式Id
     *
     * @return id Object
     */

    long generateDistributeId(String tableName);
}
