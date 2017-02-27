package scripts.PowerChopper;

import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.TilePath;

public class Bank extends Task<ClientContext> {
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
        else if (!ctx.backpack.select().id(Config.productId).isEmpty()) {
            //click the "deposit inventory" button in the bank
            ctx.bank.depositInventory();
        } else {
            //close the bank when we're done
            ctx.bank.close();
        }
    }
}
