package com.bank

import com.runemate.game.api.hybrid.local.hud.interfaces.Bank
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory
import com.runemate.game.api.hybrid.region.Banks
import com.runemate.game.api.script.framework.task.Task
import com.runemate.pathfinder.data.BankArea
import org.apache.logging.log4j.Logger

class BankDepositAll(val logger: Logger) : Task() {

    override fun validate(): Boolean {
        return Inventory.newQuery().results().isNotEmpty()
                && Banks.newQuery().results().isNotEmpty()
    }

    override fun execute() {
        logger.info("[Player] - Inventory is not empty and we're at a bank")
        logger.info("[Player] - Opening Bank")
        Bank.open()
        if(Bank.isOpen()) {
            logger.info("[Player] - Bank is open")
            Bank.depositInventory();
            logger.info("[Player] - Depositing Inventory")
            if(Inventory.newQuery().results().isEmpty()) {
                logger.info("[Player] - Inventory Banked, closing inventory");
                Bank.close();
            }
        }
    }

}