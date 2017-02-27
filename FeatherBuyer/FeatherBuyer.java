package scripts.FeatherBuyer;

import org.powerbot.script.Condition;
import org.powerbot.script.PollingScript;
import org.powerbot.script.Script;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.GeItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Script.Manifest(name = "MM_featherBuyer", description = "Buys and banks feather")

public class FeatherBuyer extends PollingScript<ClientContext> {
    private List<Task> taskList = new ArrayList<Task>();

    @Override
    public void start() {
        //taskList.addAll(Arrays.asList());
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
