package com.yupaopao.platform.plugin.drink.util;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationGroup;
import com.intellij.notification.NotificationGroupManager;
import com.intellij.notification.Notifications;
import com.intellij.openapi.ui.MessageType;

public class SystemNotifyUtil {

    public static void msgNotify(String body){
        // 系统模板的通知功能
        NotificationGroup notificationGroup = NotificationGroupManager.getInstance().getNotificationGroup("RestAndDrinkNotification");
        Notification notification = notificationGroup.createNotification(body, MessageType.INFO);
        Notifications.Bus.notify(notification);
    }

}
