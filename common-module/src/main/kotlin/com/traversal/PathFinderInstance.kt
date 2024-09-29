package com.traversal

import com.runemate.game.api.hybrid.location.Area
import com.runemate.game.api.hybrid.location.Coordinate
import com.runemate.game.api.hybrid.location.navigation.Landmark
import com.runemate.game.api.hybrid.location.navigation.Path
import com.runemate.game.api.script.framework.task.TaskBot
import com.runemate.pathfinder.Pathfinder
import org.apache.logging.log4j.Logger


class PathFinderInstance(private val bot: TaskBot, val logger: Logger) {

    private val pathfinder: Pathfinder by lazy { Pathfinder.create(bot) }

    fun navigateTo(destination: Coordinate) {
        val path: Path = getPath(destination)
        logger.info("[PathFinder] - Navigating to ${path}")
        path.step()
    }

    fun navigateTo(destination: Landmark) {
        val path: Path = getPath(destination)
        logger.info("[PathFinder] - Navigating to ${path}")
        path.step()
    }

    fun navigateTo(destination: Area) {
        val path: Path = getPath(destination)
        logger.info("[PathFinder] - Navigating to ${path}")
        path.step()
    }


    private fun <T> getPath(destination: T): Path {
        return if (pathfinder.lastPath != null && pathfinder.lastPath.isValid()) {
            logger.info("[PathFinder] - Using same path as valid ${pathfinder.lastPath}")
            pathfinder.lastPath
        } else {
            logger.info("[PathFinder] - No valid last path. Creating new path to ${destination}")
            pathfinder.pathBuilder()
                .preferAccuracy()
                .enableMinigameTeleports(false)
                .apply {
                    when (destination) {
                        is Coordinate -> destination(destination)
                        is Landmark -> destination(destination)
                        is Area -> destination(destination)
                        else -> {
                            logger.error("[PathFinder] - Unsupported Destination Type ${destination!!.let { it::class.simpleName }}. Please contact Haych")
                            throw IllegalArgumentException("Unsupported destination type")
                        }
                    }
                }
                .findPath()
        }
    }

}