package scripts.MM_featherBuyer;

import org.powerbot.script.*;
import org.powerbot.script.rt6.ClientContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Script.Manifest(name = "MM_featherBuyer", description = "Buys and banks feather")

public class MM_featherBuyer extends PollingScript<ClientContext> {
    private List<Task> taskList = new ArrayList<Task>();

    @Override
    public void start() {
        taskList.addAll(Arrays.asList(new BuyInP(ctx), new BuyInL(ctx)));


        // teleport to Port Sarim
        if (ctx.widgets.component(1477, 65).component(1).valid()) {
            ctx.widgets.component(1477, 65).component(1).interact("Teleport");
        }
        Condition.sleep(2000);
        if (ctx.widgets.component(1092, 18).valid()) {
            ctx.widgets.component(1092, 18).interact("Teleport");
        }
        Condition.sleep(15000);
        System.out.print("Start-up ended.");

    }

    @Override
    public void poll() {

        for (Task task : taskList) {
            if (task.activate()) {
                task.execute();
            }
        }
        Condition.sleep(1000);

    }
}
