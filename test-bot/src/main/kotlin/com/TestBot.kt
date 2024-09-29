package com

import com.bank.BankDepositAll
import com.runemate.game.api.script.framework.task.TaskBot
import com.traversal.TravelToArea
import com.traversal.TravelToBank
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger


class TestBot : TaskBot() {

    val logger: Logger = LogManager.getLogger("Test Bot");

    override fun onStart(vararg arguments: String?) {
        logger.info("Bot starting...")

        super.onStart(*arguments)
        add(
            TravelToBank(this, logger),
            BankDepositAll(logger),
            TravelToArea(this, logger)
        );
    }
}