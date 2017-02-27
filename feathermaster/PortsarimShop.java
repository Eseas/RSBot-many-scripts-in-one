package scripts.feathermaster;

import org.powerbot.script.Condition;
import org.powerbot.script.Tile;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.Component;
import org.powerbot.script.rt6.Npc;

import java.util.concurrent.Callable;

/**
 * Created by tvari on 2016-02-09.
 */
public class PortsarimShop extends Task<ClientContext> {

    public PortsarimShop(ClientContext ctx) {
        super(ctx);
    }

    private final int gerrantID = 558;

    private final Tile portsarimShop = new Tile(3013, 3224);

    @Override
    public boolean activate() {
        return portsarimShop.distanceTo(ctx.players.local()) < 25
                && ctx.players.local().animation() != 16386
                && !FeatherMaster.boughtPortsarim;
    }

    @Override
    public void execute() {
        Npc Gerrant = ctx.npcs.select().id(gerrantID).poll();

        if (!Gerrant.inViewport()) {
            ctx.camera.turnTo(portsarimShop);
            ctx.movement.step(portsarimShop);

            Condition.wait(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    return portsarimShop.distanceTo(ctx.players.local()) < 10;
                }
            }, 210, 10);
        } else {
            ctx.camera.turnTo(Gerrant);
            final Component shopWindow = ctx.widgets.component(1265, 5);
            if (!shopWindow.visible()) {
                if (Gerrant.interact("Trade")) {
                    Condition.wait(new Callable<Boolean>() {
                        @Override
                        public Boolean call() throws Exception {
                            return shopWindow.visible();
                        }
                    }, 210, 11);
                }
            } else {
                final Component featherWidget = ctx.widgets.component(1265, 20).component(7);
                if (!featherWidget.visible()) {
                    // Scroll down or something
                    // Lazy
                } else {
                    if (featherWidget.interact("Buy All")) {
                        FeatherMaster.boughtPortsarim = true;
                        // Some good condition wait
                        Condition.sleep(210);
                    }
                }
            }
        }
    }
}
