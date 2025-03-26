# reFurnaceBurnTime
通过配置文件自定义熔炉燃料（如煤炭、熔岩桶等）的燃烧时间。

## 介绍
该插件适用于Spigot1.12及以上。  

## 指令
`/refurnaceburntime` 重载配置。  

## 配置文件
插件成功启动后，会在plugins文件夹下生成配置文件，位于 `plugins/reFurnaceBurnTime/config.yml`  

```yaml
# 在这里定义你想要修改燃烧时间的燃料物品及其新的燃烧时间 (单位: tick)
# 1 秒 = 20 tick
# 作为参考 (原版默认值):
#   - COAL (煤炭) / CHARCOAL (木炭): 1600 ticks (可烧炼 8 个物品)
#   - LAVA_BUCKET (熔岩桶): 20000 ticks (可烧炼 100 个物品)
#   - BLAZE_ROD (烈焰棒): 2400 ticks (可烧炼 12 个物品)
#   - WOODEN_PLANKS (木板), LOGS (原木) 等多种木制品: 300 ticks (可烧炼 1.5 个物品)
#   - STICK (木棍): 100 ticks (可烧炼 0.5 个物品)
#   - DRIED_KELP_BLOCK (干海带块): 4000 ticks (可烧炼 20 个物品)

fuel_burn_times:
  # 示例: 将熔岩桶的燃烧时间改为 30000 ticks
  LAVA_BUCKET: 30000
  # 示例: 将烈焰棒的燃烧时间改为 1200 ticks
  BLAZE_ROD: 1200
  # 示例: 将煤炭和木炭的燃烧时间减半为 800 ticks
  COAL: 800
  # 示例: 将木棍的燃烧时间翻倍为 200 ticks
  STICK: 200

  # 您可以在下面按 "物品名称: 燃烧时间" 的格式添加更多燃料...
  # 物品名称必须是有效的 Bukkit Material 枚举名称 (通常为全大写)。
  # 你可以查阅 Spigot API文档 获取完整的 Material 列表:
  # https://hub.spigotmc.org/javadocs/spigot/org/bukkit/Material.html
  # 例如:
  # OAK_LOG: 400 # 将橡木原木的燃烧时间设为 400 ticks
  # BAMBOO: 50   # 设置竹子的燃烧时间 (原版默认值)
  # DRIED_KELP_BLOCK: 4500 # 稍微增加干海带块的燃烧时间
```

## 权限节点
`refurnaceburntime.main` 使用重载指令。

## 使用方法
1. 将插件放入plugins文件夹中，重启服务器。
