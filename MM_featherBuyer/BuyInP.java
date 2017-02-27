package scripts.MM_featherBuyer;

import org.powerbot.script.Condition;
import org.powerbot.script.Tile;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.GameObject;
import org.powerbot.script.rt6.Npc;
import org.powerbot.script.rt6.TilePath;

public class BuyInP extends Task<ClientContext> {

    public static final Tile[] PATH = {
            new Tile(3014, 3223, 0)
    };
    private TilePath pathToGerrant = new TilePath(ctx, PATH);
    public static final int GERRANT = 558;


    public BuyInP(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public boolean activate() {
        GameObject lodestone = ctx.objects.id(69863).poll();

        System.out.print("PS Activate == " + (lodestone.valid() &&
                lodestone.tile().distanceTo(ctx.players.local()) < 15) + "\n");

        return lodestone.valid() &&
                lodestone.tile().distanceTo(ctx.players.local()) < 15;
    }

    @Override
    public void execute() {

        Condition.sleep(1000);
        pathToGerrant.traverse();

        Condition.sleep(2000);

        final Npc trader = ctx.npcs.select().id(GERRANT).poll();
        trader.interact("Trade");

        Condition.sleep(1500);
        if (ctx.widgets.component(1265, 20).component(7).valid()) {
            ctx.widgets.component(1265, 20).component(7).interact("Buy All");
            Condition.sleep(1500);
            ctx.widgets.component(1265, 88).click();
            Condition.sleep(1000);
        }
        if (ctx.widgets.component(1477, 65).component(1).valid()) {
            ctx.widgets.component(1477, 65).component(1).interact("Teleport");
        }
        Condition.sleep(2000);
        if (ctx.widgets.component(1092, 17).valid()) {
            ctx.widgets.component(1092, 17).interact("Teleport");
        }
        Condition.sleep(10000);
    }
}
