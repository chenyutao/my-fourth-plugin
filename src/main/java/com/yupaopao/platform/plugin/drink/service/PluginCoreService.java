package com.yupaopao.platform.plugin.drink.service;

import com.yupaopao.platform.plugin.drink.config.DrinkRemindSettingConfig;
import com.yupaopao.platform.plugin.drink.ui.RemindDialog;
import com.yupaopao.platform.plugin.drink.util.SystemNotifyUtil;

import javax.swing.*;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public final class PluginCoreService {
    private DrinkRemindSettingConfig settingConfig;

    private Timer workTimeTimer = new Timer();
    private Timer remindTimer = new Timer();

    private Boolean inRestRemindStep = false;
    private final Long pluginStartTime = System.currentTimeMillis();
    private Long remindStartTime = System.currentTimeMillis();

    private static PluginCoreService INSTANCE;

    public static PluginCoreService getInstance(){
        if(INSTANCE==null){
            INSTANCE = new PluginCoreService();
        }
        return INSTANCE;
    }

    public void init() {
        DrinkRemindSettingConfig settingConfig = DrinkRemindSettingConfig.getInstance();
        this.settingConfig = settingConfig;
        this.reloadSetting(settingConfig);

        // 2. 发通知
        String msgContent = "休息提醒插件: 初始化成功";
        SystemNotifyUtil.msgNotify(msgContent);
    }

    public void updateSettingAndReload(Integer workTime, Integer remindInterval) {
        // 更新配置
        settingConfig.resetSetting(workTime, remindInterval);
        // 重新加载
        this.reloadSetting(settingConfig);
    }

    public DrinkRemindSettingConfig getSettingConfig() {
        System.out.printf("getSettingConfig workTime: {%s} remindInterval: {%s}%n", settingConfig.getMaxLastWorkMinutes(), settingConfig.getRemindIntervalMinutes());
        return settingConfig;
    }

    public void logDrinkAndPrepareNext() {
        // 0.发通知
        String msgContent = "眼睛休息完成，继续工作";
        SystemNotifyUtil.msgNotify(msgContent);
        // 1. 更新喝水时间
        settingConfig.updateDrinkTime(System.currentTimeMillis());
        inRestRemindStep = false;
        // 2. 取消后续提醒
        cancelLaterRemind();
        // 3. 重启工作时长监督任务
        restartWorkTimeMonitor(settingConfig);
    }

    private void reloadSetting(DrinkRemindSettingConfig settingConfig) {
        if (!inRestRemindStep) {
            //如果在工作阶段
            restartWorkTimeMonitor(settingConfig);
        } else {
            // 在提醒阶段
            restartDrinkRemindScheduleTask(settingConfig);
        }
    }

    private void restartWorkTimeMonitor(DrinkRemindSettingConfig settingConfig) {
        // 取消之前的任务
        cancelLastWorkTimeMonitor();
        // 创建新的任务
        resetAndStartNewWorkTimeMonitor(settingConfig);
    }

    private void cancelLastWorkTimeMonitor() {
        workTimeTimer.cancel();
        workTimeTimer.purge();
        workTimeTimer = new Timer();
    }

    private void resetAndStartNewWorkTimeMonitor(DrinkRemindSettingConfig settingConfig) {
        TimerTask drinkNotifyTask = new TimerTask() {
            @Override
            public void run() {
                notifyAndStartRemindTask(settingConfig);
            }
        };
        // 计算时间
        int workTimeInMs = settingConfig.getMaxLastWorkMinutes() * 60 * 1000;
        Long lastTime = settingConfig.getLastDrinkTime();
        if (lastTime == null) {
            lastTime = pluginStartTime;
        }
        Date nextNotifyTime = new Date(lastTime + workTimeInMs);
        // 启动提醒
        workTimeTimer.schedule(drinkNotifyTask, nextNotifyTime);
    }

    /**
     * 通知喝水时间已经到了，启动提醒任务
     */
    private void notifyAndStartRemindTask(DrinkRemindSettingConfig settingConfig) {
        inRestRemindStep = true;
        // 1. 发通知
        String msgContent = String.format("你已经工作 %s 分钟了，需要休息一下眼睛", settingConfig.getMaxLastWorkMinutes());
        SystemNotifyUtil.msgNotify(msgContent);
        // 2. 重置定时提醒的定时任务
        remindStartTime = System.currentTimeMillis();
        restartDrinkRemindScheduleTask(settingConfig);
    }

    /**
     * 重置喝水提醒
     */
    private void restartDrinkRemindScheduleTask(DrinkRemindSettingConfig settingConfig) {
        // 1. 取消后续的提醒
        cancelLaterRemind();
        // 2. 创建新的提醒
        resetRemindScheduleAndStart(settingConfig);
    }

    /**
     * 重新设置提醒 & 开启
     */
    private void resetRemindScheduleAndStart(DrinkRemindSettingConfig settingConfig) {
        TimerTask drinkRemindTask = new TimerTask() {
            @Override
            public void run() {
                // 更新提醒时间
                remindStartTime = System.currentTimeMillis();
                // 提醒
                String msgContent = "快、快，起来走一走、喝一杯~";
                SystemNotifyUtil.msgNotify(msgContent);
                 // 弹框
                callNewWindow();
            }
        };
        int intervalInMs = settingConfig.getRemindIntervalMinutes() * 60 * 1000;
        Date firstTime = new Date(remindStartTime + intervalInMs);
        remindTimer.schedule(drinkRemindTask, firstTime, intervalInMs);
    }

    private void cancelLaterRemind() {
        remindTimer.cancel();
        remindTimer.purge();
        remindTimer = new Timer();
    }

    private void callNewWindow(){
        String windowTitle = "老婆吩咐";
        RemindDialog remindDialog = new RemindDialog();
        remindDialog.setTitle(windowTitle);
        remindDialog.setVisible(true);
    }

}
