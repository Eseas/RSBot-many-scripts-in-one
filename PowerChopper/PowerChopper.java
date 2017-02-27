package scripts.PowerChopper;

import org.powerbot.script.*;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.GeItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by turbo on 2016-02-08.
 */
@Script.Manifest(name = "PowerChopper", description = "Chops trees and banks")
public class PowerChopper extends PollingScript<ClientContext> {
    private List<Task> taskList = new ArrayList<Task>();

    @Override
    public void start() {
        taskList.addAll(Arrays.asList(new Chop(ctx), new Bank(ctx), new WalkToBank(ctx)));
    }

    @Override
    public void poll() {

        for (Task task : taskList) {
            if (task.activate()) {
                task.execute();
            }
        }
        Condition.sleep(500);
    }
}
