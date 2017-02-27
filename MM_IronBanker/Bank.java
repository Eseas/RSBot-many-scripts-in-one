package scripts.MM_IronBanker;

import org.powerbot.script.*;
import org.powerbot.script.Condition;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.GameObject;
import org.powerbot.script.rt6.Item;
import org.powerbot.script.rt6.TilePath;

import java.util.concurrent.Callable;

public class Bank extends Task<ClientContext> {
    private int ironOre = 440;
    private TilePath pathToBank, pathToTrader;

    public Bank(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public boolean activate() {
        return  ctx.backpack.select().count() == 28 && ctx.bank.inViewport();
    }

    @Override
    public void execute() {
        if (!ctx.bank.opened()) {
            ctx.bank.open();
        }
        //if the user has bear fur in their inventory
        else if (!ctx.backpack.select().id(ironOre).isEmpty()) {
            //click the "deposit inventory" button in the bank
            ctx.bank.depositInventory();
        } else {
            //close the bank when we're done
            ctx.bank.close();
        }
    }
}
