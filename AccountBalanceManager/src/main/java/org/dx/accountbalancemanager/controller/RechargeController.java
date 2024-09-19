package org.dx.accountbalancemanager.controller;

import jakarta.validation.constraints.NotNull;
import org.dx.accountbalancemanager.common.exception.ApiResponse;
import org.dx.accountbalancemanager.entity.RechargeLog;
import org.dx.accountbalancemanager.service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.concurrent.*;

/**
 * @author 10589
 */
@RestController
@Validated
@RequestMapping("/balance")
public class RechargeController {

    @Autowired
    private TransferService transferService;

    /**
     * 充值接口
     *
     * @param numUsers 参与充值的用户数量
     * @param accountId 需要充值的账户ID
     * @param balance 每个用户充值的金额
     * @return 返回充值结果的ApiResponse对象
     */
    @PostMapping("/recharge")
    public ApiResponse<Void> recharge(
            @RequestParam("numUsers") @NotNull int numUsers,
            @RequestParam("accountId") @NotNull int accountId,
            @RequestParam("balance") @NotNull int balance) {

        // 创建一个线程池来执行所有任务
        ExecutorService executor = Executors.newFixedThreadPool(numUsers);
        // 创建一个Future列表来保存所有Future对象
        Future<?>[] futures = new Future[numUsers];

        // 遍历所有用户，为每个用户创建并提交一个充值任务
        for (int i = 0; i < numUsers; i++) {
            int threadId = i;
            // 提交充值任务
            futures[threadId] = executor.submit(() -> {

                // 使用转账服务为指定账户增加余额
                transferService.add(accountId, balance);

                // 创建RechargeLog对象
                RechargeLog log = new RechargeLog(accountId, 0, balance, LocalDateTime.now(), "充值");

                // 记录成功案例并写入到logger
                transferService.logger(log);

            });
        }

        // 等待所有任务完成，并检查是否有异常发生
        for (Future<?> future : futures) {
            try {
                future.get();
            } catch (ExecutionException e) {
                // 处理任务执行过程中发生的异常
                Throwable cause = e.getCause();
                if (cause instanceof RuntimeException) {
                    throw (RuntimeException) cause;
                } else {
                    throw new RuntimeException("Task failed with exception: " + cause.getMessage(), cause);
                }
            } catch (InterruptedException e) {
                // 恢复中断状态
                Thread.currentThread().interrupt();
                throw new RuntimeException("Thread interrupted: " + e.getMessage(), e);
            }
        }
        // 关闭线程池
        executor.shutdown();
        // 返回充值成功的响应
        return ApiResponse.success("0", "Recharge successful", null);
    }

    /**
     * 执行批量转账操作
     * 使用固定用户数量的线程池来并发地从一个账户向另一个账户转账
     *
     * @param numUsers 要进行转账操作的用户数量
     * @param fromAccountId 转账发起者的账户ID
     * @param toAccountId 转账接收者的账户ID
     * @param balance 每个用户要转账的金额
     * @return 返回一个表示批量转账操作结果的响应对象
     */
    @PostMapping("/transfer")
    public ApiResponse<Void> transfer(
            @RequestParam("numUsers") @NotNull int numUsers,
            @RequestParam("fromAccountId") @NotNull int fromAccountId,
            @RequestParam("toAccountId") @NotNull int toAccountId,
            @RequestParam("balance") @NotNull int balance) {

        // 创建一个线程池来执行所有任务
        ExecutorService executor = Executors.newFixedThreadPool(numUsers);
        // 创建一个Future列表来保存所有Future对象
        Future<?>[] futures = new Future[numUsers];

        // 遍历所有用户，为每个用户创建并提交转账任务到线程池
        for (int i = 0; i < numUsers; i++) {
            int threadId = i;
            // 提交转账任务
            futures[threadId] = executor.submit(() -> {
                // 调用转账服务进行转账操作
                transferService.transfer(fromAccountId, toAccountId, balance);

                // 创建RechargeLog对象
                RechargeLog log = new RechargeLog(fromAccountId, toAccountId, balance, LocalDateTime.now(), "转账");

                // 记录成功案例并写入到logger
                transferService.logger(log);
            });
        }

        // 等待所有任务完成，并检查是否有异常发生
        for (Future<?> future : futures) {
            try {
                future.get();
            } catch (ExecutionException e) {
                // 处理任务执行过程中发生的异常
                Throwable cause = e.getCause();
                if (cause instanceof RuntimeException) {
                    throw (RuntimeException) cause;
                } else {
                    throw new RuntimeException("Task failed with exception: " + cause.getMessage(), cause);
                }
            } catch (InterruptedException e) {
                // 恢复中断状态
                Thread.currentThread().interrupt();
                throw new RuntimeException("Thread interrupted: " + e.getMessage(), e);
            }
        }

        // 关闭线程池
        executor.shutdown();

        return ApiResponse.success("0", "转账成功", null);
    }
}
