package org.dx.accountbalancemanager.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.dx.accountbalancemanager.entity.AccountBalance;
import org.dx.accountbalancemanager.entity.RechargeLog;

@Mapper
public interface AccountMapper {

    /**
     * 插入充值记录到数据库
     *
     * @param transferLog 充值记录对象，包含accountId, balance, timestamp等信息
     */
    @Insert("INSERT INTO RechargeLog (timestamp, type, fromAccountId, toAccountId, balance) " +
            "VALUES (#{timestamp}, #{type}, #{fromAccountId},#{toAccountId}, #{balance})")
    void logger(RechargeLog transferLog);


    /**
     * 根据accountId获取账户余额信息
     *
     * @param accountId 账户ID，用于标识哪个账户的余额信息将被检索
     * @return 返回一个AccountBalance对象，包含被请求账户的余额和版本信息

     * 说明：
     * - 该方法使用了MyBatis的注解来关联SQL查询
     * - SQL语句通过accountId来筛选出特定账户的余额信息，并通过FOR UPDATE确保数据在查询期间不会被其他事务修改。加行级写锁。
     * - 这个查询对于账户余额的安全性和事务一致性至关重要
     */
    @Select("SELECT accountId, balance FROM accountbalance WHERE accountId = #{accountId} FOR UPDATE")
    AccountBalance getInformationByAccountId(int accountId);

    /**
     * 更新账户余额

     * 此方法通过一个SQL更新语句，原子性地更新账户余额。
     * 它使用乐观锁机制，通过比较旧的余额和版本号来确保数据的一致性。
     * 如果数据库中账户的accountId、balance和version与提供的旧值匹配，
     * 则更新账户的余额和版本号为新的值。
     * 这种方式可以防止多个线程或进程同时修改同一账户余额时出现的并发问题。
     *
     * @param accountId 账户ID
     * @param newBalance 新的余额
     * @param oldBalance 旧的余额，用于比较

     */
    @Update("UPDATE accountbalance SET balance = #{newBalance} WHERE accountId = #{accountId} AND balance = #{oldBalance}")
    void updateBalance(int accountId, int newBalance, int oldBalance);

}
