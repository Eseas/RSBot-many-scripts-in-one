package scripts.feathermaster;

//import core.Task;
import org.powerbot.script.Condition;
import org.powerbot.script.Tile;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.Component;
import org.powerbot.script.rt6.GameObject;
import org.powerbot.script.rt6.Npc;

import java.util.concurrent.Callable;

/**
 * Created by tvari on 2016-02-09.
 */
public class LumbridgeShop extends Task<ClientContext> {

    private final int hankID = 8864;

    private final Tile lumbridgeShop = new Tile(3195, 3250);

    public LumbridgeShop(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public boolean activate() {
        return lumbridgeShop.distanceTo(ctx.players.local()) < 55
                && ctx.players.local().animation() != 16386
                && !FeatherMaster.boughtLumbridge;
    }

    @Override
    public void execute() {
        Npc Hank = ctx.npcs.select().id(hankID).poll();

        if (!Hank.inViewport()) {
            ctx.camera.turnTo(lumbridgeShop);
            ctx.movement.step(lumbridgeShop);
            Condition.wait(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    return lumbridgeShop.distanceTo(ctx.players.local()) < 10;
                }
            }, 210, 10);
        } else {
            ctx.camera.turnTo(Hank);
            final Component shopWindow = ctx.widgets.component(1265, 5);
            if (!shopWindow.visible()) {
                if (Hank.interact("Trade")) {
                    Condition.wait(new Callable<Boolean>() {
                        @Override
                        public Boolean call() throws Exception {
                            return shopWindow.visible();
                        }
                    }, 210, 11);
                }
            } else {
                final Component featherWidget = ctx.widgets.component(1265, 20).component(5);
                if (!featherWidget.visible()) {
                    // Scroll down or something
                    // Lazy
                } else {
                    if (featherWidget.interact("Buy All")) {
                        FeatherMaster.boughtLumbridge = true;
                        // Some good condition wait
                        Condition.sleep(210);
                    }
                }
            }
        }
    }
}
