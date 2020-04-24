package io.github.mainyf.starter

import net.minecraftforge.fml.common.FMLCommonHandler
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {

    override fun onEnable() {
        Bukkit.getPluginManager().registerEvents(object : Listener {
            @EventHandler
            fun doJoin(event: PlayerJoinEvent) {
                FMLCommonHandler.instance().minecraftServerInstance.worlds.forEach {
                    println(it.worldInfo.worldName)
                }
            }
        }, this)
    }

}