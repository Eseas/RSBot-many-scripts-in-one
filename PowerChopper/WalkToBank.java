package scripts.PowerChopper;

import org.powerbot.script.Condition;
import org.powerbot.script.Tile;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.TilePath;


public class WalkToBank extends Task<ClientContext> {
    /*
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
    */
    public static final Tile[] PATH = Config.PATH;
    private TilePath pathToMines = new TilePath(ctx, PATH);

    public WalkToBank(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public boolean activate() {
        return (ctx.backpack.select().count() == 28 && !ctx.bank.inViewport());
    }

    @Override
    public void execute() {
        System.out.print("Waling to bank :))");
        pathToMines.traverse();
        Condition.sleep(2000);
    }
}