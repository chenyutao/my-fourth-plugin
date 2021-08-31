package com.yupaopao.platform.plugin.drink.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.yupaopao.platform.plugin.drink.ui.SettingDialog;

public class DrinkRemindSettingAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        SettingDialog settingDialog = new SettingDialog();
        settingDialog.setVisible(true);
    }

}
