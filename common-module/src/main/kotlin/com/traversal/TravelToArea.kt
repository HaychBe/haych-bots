package com.traversal

import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory
import com.runemate.game.api.hybrid.location.Coordinate
import com.runemate.game.api.script.framework.task.Task
import com.runemate.game.api.script.framework.task.TaskBot
import org.apache.logging.log4j.Logger

class TravelToArea(private val bot: TaskBot, val logger: Logger) : Task() {

    private val pathfinder = PathFinderInstance(bot, logger);

    override fun validate(): Boolean {
        return Inventory.isEmpty();
    }
    override fun execute() {
        logger.info("[Player] - Inventory emptied");
        logger.info("[PathFinder] - Running to goblins");
        pathfinder.navigateTo(Coordinate(3259,3229,0));

    }


}