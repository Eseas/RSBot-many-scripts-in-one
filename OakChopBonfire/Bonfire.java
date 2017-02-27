package scripts.OakChopBonfire;

import org.powerbot.script.Condition;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.GameObject;
import org.powerbot.script.rt6.Item;

import java.util.concurrent.Callable;

public class Bonfire extends Task<ClientContext> {
    private int logId = 1521;
    private  int fireId = 70758;

    public Bonfire(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public boolean activate() {
        return  ctx.backpack.select().count() == 28;
    }

    @Override
    public void execute() {
        GameObject fire = ctx.objects.id(fireId).poll();

        if (fire.inViewport()) {
            ctx.movement.step(fire);
            ctx.camera.turnTo(fire);
            fire.interact("Use");

            Condition.sleep(2000);
            if (ctx.widgets.component(1179, 2).valid()) {
                ctx.widgets.component(1179, 2).click();
            }

        } else {
            Item item = ctx.backpack.select().id(logId).poll();

            item.interact("Light");
            Condition.sleep(7000);

            item = ctx.backpack.select().id(logId).poll();
            item.interact("Craft");
            Condition.sleep(2000);
            if (ctx.widgets.component(1179, 40).valid()) {
                ctx.widgets.component(1179, 40).click();
            }
        }

        Condition.sleep(2000);
        Condition.wait(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return ctx.players.local().stance() != 16701;
            }
        }, 1000, 60);
    }
}
