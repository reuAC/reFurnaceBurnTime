package org.reuac.refurnaceburntime;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.FurnaceBurnEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public final class ReFurnaceBurnTime extends JavaPlugin implements Listener, CommandExecutor {

    private Map<Material, Integer> customBurnTimes;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        customBurnTimes = new HashMap<>();

        loadConfigValues();

        Bukkit.getPluginManager().registerEvents(this, this);
        Bukkit.getPluginCommand("refurnaceburntime").setExecutor(this);
    }

    @Override
    public void onDisable() {
        if (customBurnTimes != null) {
            customBurnTimes.clear();
            customBurnTimes = null;
        }
    }

    private void loadConfigValues() {
        customBurnTimes.clear();

        ConfigurationSection fuelSection = getConfig().getConfigurationSection("fuel_burn_times");

        if (fuelSection == null) {
            getLogger().warning("在 config.yml 中未找到 'fuel_burn_times' 配置部分。将不会应用任何自定义燃烧时间。");
            return;
        }

        int loadedCount = 0;
        for (String materialName : fuelSection.getKeys(false)) {
            if (!fuelSection.isInt(materialName)) {
                getLogger().warning("配置 'fuel_burn_times' 中的 '" + materialName + "' 的值不是有效的整数。已跳过。");
                continue;
            }

            int burnTime = fuelSection.getInt(materialName);

            if (burnTime < 0) {
                getLogger().warning("配置 'fuel_burn_times' 中的 '" + materialName + "' 的燃烧时间 (" + burnTime + ") 不能为负数。已跳过。");
                continue;
            }

            Material material;
            try {
                material = Material.matchMaterial(materialName.toUpperCase());
                if (material == null) {
                    material = Material.valueOf(materialName.toUpperCase());
                }
            } catch (IllegalArgumentException e) {
                getLogger().warning("在 'fuel_burn_times' 配置中找到无效的物品名称 '" + materialName + "'。请使用有效的 Material 名称 (例如 COAL, LAVA_BUCKET)。已跳过。");
                continue;
            }

            if (material == null) {
                getLogger().warning("无法解析物品名称 '" + materialName + "'。已跳过。");
                continue;
            }

            customBurnTimes.put(material, burnTime);
            loadedCount++;
        }
        getLogger().info("从 config.yml 加载了 " + loadedCount + " 个自定义燃料燃烧时间。");
    }

    @EventHandler
    public void onFurnaceBurn(FurnaceBurnEvent event) {
        Material fuelType = event.getFuel().getType();

        if (customBurnTimes.containsKey(fuelType)) {
            int customTime = customBurnTimes.get(fuelType);
            event.setBurnTime(customTime);
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        reloadConfig();
        loadConfigValues();
        sender.sendMessage("重载成功");
        return true;
    }
}