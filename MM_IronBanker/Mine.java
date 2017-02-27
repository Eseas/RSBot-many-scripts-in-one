package scripts.MM_IronBanker;

import org.powerbot.script.Condition;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.GameObject;

import org.powerbot.script.rt6.GeItem;

public class Mine extends Task<ClientContext> {
    private int[] rockIds = {72081, 72082, 72083};
    private int ironOrePrice = GeItem.price(440);


    public Mine(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public boolean activate() {
        return ctx.backpack.select().count() < 28
                && !ctx.objects.select().id(rockIds).isEmpty()
                && ctx.players.local().animation() == -1;
    }

    @Override
    public void execute() {
        GameObject rock = ctx.objects.id(rockIds).nearest().poll();
        if (rock.inViewport()) {
            rock.interact("Mine");
            Condition.sleep(1000);
        } else {
            ctx.movement.step(rock);
            ctx.camera.turnTo(rock);
        }
    }
}
