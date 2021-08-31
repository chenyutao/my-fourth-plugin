package com.yupaopao.platform.plugin.drink.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.application.ApplicationManager;
import com.yupaopao.platform.plugin.drink.service.PluginCoreService;

import javax.swing.*;

public class DrinkReportAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        // 弹窗
        JOptionPane.showMessageDialog(null, "小伙子真棒！😁", "Message", JOptionPane.INFORMATION_MESSAGE);
        // 记录喝水时间
        PluginCoreService.getInstance().logDrinkAndPrepareNext();
    }

}
