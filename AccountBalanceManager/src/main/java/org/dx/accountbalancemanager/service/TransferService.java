package org.dx.accountbalancemanager.service;


import org.dx.accountbalancemanager.entity.AccountBalance;
import org.dx.accountbalancemanager.entity.RechargeLog;
import org.dx.accountbalancemanager.mapper.AccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class TransferService {

    @Autowired
    private AccountMapper accountMapper;

    /**
     * 在充值操作后，将充值日志记录到数据库中
     * 该方法被声明为在一个事务中执行，确保日志记录的原子性
     * 如果在事务执行过程中发生任何异常，默认会回滚此操作
     *
     * @param rechargeLog 包含充值日志信息的对象，记录了充值操作的细节
     */
    @Transactional(rollbackFor = Exception.class)
    public void logger(RechargeLog rechargeLog) {
        // 将日志对象插入数据库，记录充值操作
        accountMapper.logger(rechargeLog);
    }

    /**
     * 更新账户余额的方法
     * 此方法采用乐观锁机制，确保线程安全地更新账户余额
     *
     * @param accountId 账户ID
     * @param balance   要增加的余额
     */
    @Transactional(rollbackFor = Exception.class)
    public void add(int accountId, int balance) {
        if(balance > 0){
            // 获取当前账户
            AccountBalance currentAccount = accountMapper.getInformationByAccountId(accountId);
            // 提取当前账户的余额和版本号
            int currentBalance = currentAccount.getBalance();
            // 更新充值账户的余额Balance
            accountMapper.updateBalance(accountId, currentBalance + balance, currentBalance);
        }else{
            throw new RuntimeException("充值失败");
        }
    }

    // 转账方法，从一个账户转指定金额到另一个账户
    // 使用Spring的事务管理，确保操作的原子性
    // 当发生异常时回滚事务，确保数据一致性
    @Transactional(rollbackFor = Exception.class)
    public void transfer(int fromAccountId, int toAccountId, int balance) {

        // 获取当前账户from的余额
        AccountBalance fromAccount = accountMapper.getInformationByAccountId(fromAccountId);
        int fromBalance = fromAccount.getBalance();
        // 判断转账金额是否超过当前余额
        if (fromBalance >= balance) {
            // 更新当前账户的余额Balance（扣钱）
            accountMapper.updateBalance(fromAccountId, fromBalance - balance, fromBalance);
            // 获取目标账户to的余额
            AccountBalance toAccount = accountMapper.getInformationByAccountId(toAccountId);
            int toBalance = toAccount.getBalance();
            // 更新目标账户的余额Balance（加钱）
            accountMapper.updateBalance(toAccountId, toBalance + balance, toBalance);
        } else {
            throw new IllegalArgumentException("余额不足");
        }
    }
}
