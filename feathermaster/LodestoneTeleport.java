package scripts.feathermaster;

import org.powerbot.script.Condition;
import org.powerbot.script.Tile;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.Component;
import org.powerbot.script.rt6.GameObject;

import java.util.concurrent.Callable;

/**
 * Created by tvari on 2016-02-09.
 */
public class LodestoneTeleport extends Task<ClientContext> {

    private final Tile lumbridgeShop = new Tile(3195, 3250);
    private final Tile portsarimShop = new Tile(3013, 3224);

    public LodestoneTeleport(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public boolean activate() {
        return lumbridgeShop.distanceTo(ctx.players.local()) >= 55
                && !FeatherMaster.boughtLumbridge
                && (portsarimShop.distanceTo(ctx.players.local()) >= 25 || FeatherMaster.boughtPortsarim)
                ||
                portsarimShop.distanceTo(ctx.players.local()) >= 25
                        && !FeatherMaster.boughtPortsarim
                        && (lumbridgeShop.distanceTo(ctx.players.local()) >= 55 || FeatherMaster.boughtLumbridge);
    }
    @Override
    public void execute() {
        if (!FeatherMaster.boughtLumbridge) {
            Teleport(1092, 17);
        } else {
            if (!FeatherMaster.boughtPortsarim) {
                Teleport(1092, 18);
            }
        }
    }
    private void Teleport(int widget, int component) {
        final Component lodestoneWindow = ctx.widgets.component(1092, 0);
        final Component homeTeleport = ctx.widgets.component(1465, 56);
        if (ctx.players.local().animation() != 16385) {
            if (!lodestoneWindow.visible()) {
                if (homeTeleport.interact("Teleport")) {
                    Condition.wait(new Callable<Boolean>() {
                        @Override
                        public Boolean call() throws Exception {
                            return lodestoneWindow.visible();
                        }
                    }, 210, 10);
                }
            } else {
                final Component lumbridgeLodestone = ctx.widgets.component(widget, component);
                if (lumbridgeLodestone.interact("Teleport")) {
                    Condition.wait(new Callable<Boolean>() {
                        @Override
                        public Boolean call() throws Exception {
                            return ctx.players.local().animation() == 16385;
                        }
                    }, 210, 10);
                }
            }
        }
    }
}
