package com.yupaopao.platform.plugin.drink.ui;

import com.intellij.openapi.application.ApplicationManager;
import com.yupaopao.platform.plugin.drink.config.DrinkRemindSettingConfig;
import com.yupaopao.platform.plugin.drink.service.PluginCoreService;
import com.yupaopao.platform.plugin.drink.util.SystemNotifyUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import javax.swing.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SettingDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;

    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;

    public SettingDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });
        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });
        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });
        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        /*
          上面都是模板自带的逻辑
          下面才是该面板自己的逻辑
         */
        setSize(500, 200);//对话框的长宽
        setLocation(400,200);
        PluginCoreService pluginCoreService = ApplicationManager.getApplication().getService(PluginCoreService.class);
        DrinkRemindSettingConfig settingConfig = pluginCoreService.getSettingConfig();
        Integer maxLastWorkMinutes = settingConfig.getMaxLastWorkMinutes();
        Integer remindIntervalMinutes = settingConfig.getRemindIntervalMinutes();
        Long lastDrinkTime = settingConfig.getLastDrinkTime();
        textField1.setText(maxLastWorkMinutes + "");
        textField2.setText(remindIntervalMinutes + "");
        if (lastDrinkTime != null) {
            SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateTimeStr = dateTimeFormat.format(new Date(settingConfig.getLastDrinkTime()));
            textField3.setText(dateTimeStr);
        } else {
            textField3.setText("");
        }

    }


    private void onOK() {
        // 更新设置
        String text1 = textField1.getText();
        String text2 = textField2.getText();
        if (!isValidateInput(text1) || !isValidateInput(text2)) {
            JOptionPane.showMessageDialog(null, "输入错误，必须要大于0的数字", "tips", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        // 更新设置，重新计时
        PluginCoreService pluginCoreService = ApplicationManager.getApplication().getService(PluginCoreService.class);
        pluginCoreService.updateSettingAndReload(Integer.valueOf(text1), Integer.parseInt(text2));
        // 发系统消息提醒
        SystemNotifyUtil.msgNotify("喝水提醒更新成功");
        // 关闭面板
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    private boolean isValidateInput(String text) {
        if(StringUtils.isEmpty(text)){
            return false;
        }
        if (!NumberUtils.isCreatable(text)) {
            return false;
        }
        Integer intVal = NumberUtils.createInteger(text);
        System.out.printf("isValidateInput text:{%s} intVal:{%s}%n",text,intVal);
        try {
            return intVal > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

}
