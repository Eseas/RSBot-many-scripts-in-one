package scripts.MM_IronBanker;

import org.powerbot.script.*;
import org.powerbot.script.Condition;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.GameObject;
import org.powerbot.script.rt6.Item;
import org.powerbot.script.rt6.TilePath;

import java.util.concurrent.Callable;

public class WalkToBank extends Task<ClientContext> {
    public static final Tile[] PATH = {
            new Tile(2957, 3295, 0),
            new Tile(2961, 3288, 0),
            new Tile(2964, 3282, 0),
            new Tile(2966, 3273, 0),
            new Tile(2968, 3264, 0),
            new Tile(2969, 3255, 0),
            new Tile(2972, 3248, 0),
            new Tile(2970, 3239, 0)
    };
    private TilePath pathToMines = new TilePath(ctx, PATH).reverse();

    public WalkToBank(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public boolean activate() {
        return (ctx.backpack.select().count() == 28 && !ctx.bank.inViewport());

    }

    @Override
    public void execute() {
        pathToMines.traverse();
        Condition.sleep(500);
    }
}