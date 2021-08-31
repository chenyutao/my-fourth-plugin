package com.yupaopao.platform.plugin.drink.config;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.ide.util.PropertyName;


public class DrinkRemindSettingConfig {

    @PropertyName("DrinkRemind:SettingData:WorkLastTime")
    private Integer maxLastWorkMinutes = 60;
    @PropertyName("DrinkRemind:SettingData.RemindInterval")
    private Integer remindIntervalMinutes = 5;
    @PropertyName("DrinkRemind:SettingData.LastDrinkTime")
    private Long lastDrinkTime;

    private static DrinkRemindSettingConfig INSTANCE;

    public static DrinkRemindSettingConfig getInstance() {
        if (INSTANCE != null) {
            return INSTANCE;
        }
        DrinkRemindSettingConfig setting = new DrinkRemindSettingConfig();
        PropertiesComponent.getInstance().loadFields(setting);
        INSTANCE = setting;
        return INSTANCE;
    }


    /**
     * 更新设置
     */
    public DrinkRemindSettingConfig resetSetting(Integer workTime, Integer remindInterval) {
        this.maxLastWorkMinutes = workTime;
        this.remindIntervalMinutes = remindInterval;
        PropertiesComponent.getInstance().saveFields(this);
        return this;
    }

    public void updateDrinkTime(Long drinkTime) {
        this.lastDrinkTime = drinkTime;
        PropertiesComponent.getInstance().saveFields(this);
    }


    private DrinkRemindSettingConfig() {

    }

    public Integer getMaxLastWorkMinutes() {
        return maxLastWorkMinutes;
    }

//    public void setMaxLastWorkMinutes(Integer maxLastWorkMinutes) {
//        this.maxLastWorkMinutes = maxLastWorkMinutes;
//    }

    public Integer getRemindIntervalMinutes() {
        return remindIntervalMinutes;
    }

//    public void setRemindIntervalMinutes(Integer remindIntervalMinutes) {
//        this.remindIntervalMinutes = remindIntervalMinutes;
//    }

    public Long getLastDrinkTime() {
        return lastDrinkTime;
    }

//    public void setLastDrinkTime(Long lastDrinkTime) {
//        this.lastDrinkTime = lastDrinkTime;
//    }


}
