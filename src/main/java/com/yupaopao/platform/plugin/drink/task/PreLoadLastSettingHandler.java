package com.yupaopao.platform.plugin.drink.task;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.application.PreloadingActivity;
import com.intellij.openapi.progress.ProgressIndicator;
import com.yupaopao.platform.plugin.drink.service.PluginCoreService;
import org.jetbrains.annotations.NotNull;

public class PreLoadLastSettingHandler extends PreloadingActivity {
    @Override
    public void preload(@NotNull ProgressIndicator indicator) {
        PluginCoreService pluginCoreService = ApplicationManager.getApplication().getService(PluginCoreService.class);
        pluginCoreService.init();
    }

}
