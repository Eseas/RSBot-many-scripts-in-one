package scripts.MM_featherBuyer;

import org.powerbot.script.Condition;
import org.powerbot.script.Tile;
import org.powerbot.script.rt6.*;
import org.powerbot.script.rt6.ClientContext;

import java.util.concurrent.Callable;

public class BuyInL extends Task<ClientContext> {

    public static final Tile[] PATH = {
            new Tile(3230, 3231, 0),
            new Tile(3224, 3237, 0),
            new Tile(3217, 3245, 0),
            new Tile(3206, 3247, 0),
            new Tile(3198, 3249, 0),
            new Tile(3194, 3252, 0)
    };
    private TilePath pathToHank = new TilePath(ctx, PATH);
    public static final int HANK = 8864;


    public BuyInL(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public boolean activate() {
        GameObject lodestone = ctx.objects.id(36749).poll(); // 69861

        System.out.print("L Activate == " + (lodestone.valid() &&
                lodestone.tile().distanceTo(ctx.players.local()) < 15) + "\n");
        return lodestone.valid() &&
                lodestone.tile().distanceTo(ctx.players.local()) < 15;
    }

    @Override
    public void execute() {
        Condition.sleep(2000);

        pathToHank.traverse();
        Condition.sleep(2000);

        //if (true) return;

        pathToHank.traverse();
        Condition.sleep(2000);
        pathToHank.traverse();
        Condition.sleep(2000);
        pathToHank.traverse();
        Condition.sleep(2000);
        pathToHank.traverse();
        Condition.sleep(2000);
        pathToHank.traverse();
        Condition.sleep(2000);

        Condition.sleep(2000);
        if (!ctx.chat.chatting()) {
            //... talk to the trader
            final Npc trader = ctx.npcs.select().id(HANK).nearest().poll();

            if (trader.interact("Trade")) {
                System.out.print("interacting...");
                //wait until the chat interface comes up (prevents spam clicking) for a maximum of 2500ms (250*10)
                //Condition.wait(condition to wait for, how long to sleep for each iteration, how many iterations to go through)
                //sleeps until call() returns true, then wait() returns true;
                //or times out and returns false
                Condition.wait(new Callable<Boolean>() {
                    @Override
                    public Boolean call() throws Exception {
                        return ctx.chat.chatting();
                    }
                }, 250, 15);
            }
            Condition.sleep(2000);
            if (ctx.widgets.component(1265, 20).component(5).valid()) {
                ctx.widgets.component(1265, 20).component(5).interact("Buy All");

                Condition.sleep(2000);
                ctx.widgets.component(1265, 88).click();

                Condition.sleep(3000);
            }
            if (ctx.widgets.component(1477, 65).component(1).valid()) {
                ctx.widgets.component(1477, 65).component(1).interact("Teleport");
            }
            Condition.sleep(2000);
            if (ctx.widgets.component(1092, 17).valid()) {
                ctx.widgets.component(1092, 17).interact("Teleport");
            }
        }
        // missing else
    }
}
