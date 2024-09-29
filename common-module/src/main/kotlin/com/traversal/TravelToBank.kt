package com.traversal

import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory
import com.runemate.game.api.hybrid.location.navigation.Landmark
import com.runemate.game.api.hybrid.region.Banks
import com.runemate.game.api.hybrid.region.Players
import com.runemate.game.api.script.framework.task.Task
import com.runemate.game.api.script.framework.task.TaskBot
import com.runemate.pathfinder.data.BankArea
import org.apache.logging.log4j.Logger

class TravelToBank(private val bot: TaskBot, val logger: Logger) : Task() {

    private val pathfinder = PathFinderInstance(bot, logger);

    override fun validate(): Boolean {
        val playerLoc = Players.getLocal()?.position ?: return false
        val inBank = playerLoc in BankArea.LUMBRIDGE_BANK.area
        val result = !Inventory.isEmpty()
                && !inBank

        logger.info("[Player] - Checking if inventory is empty and player is at a bank");
        return result;
    }

    override fun execute() {
        logger.info("[PathFinder] - Executing PathFinder. Running to closest bank")
        pathfinder.navigateTo(Landmark.BANK)
    }

}