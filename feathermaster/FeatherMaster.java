package scripts.feathermaster;

//import core.Task;
import org.powerbot.script.PollingScript;
import org.powerbot.script.Script;
import org.powerbot.script.rt6.ClientContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Created by tvari on 2016-02-09.
 */
@Script.Manifest(
        name = "FeatherMaster",
        description = "Buys feather in lumbridge and port sarim",
        properties = "client=6"
)
public class FeatherMaster extends PollingScript<ClientContext>{

    private List<Task> taskList = new ArrayList<Task>();

    // True if you bought feather there
    public static boolean boughtLumbridge, boughtPortsarim;

    @Override
    public void start() {
        boughtLumbridge = false;
        boughtPortsarim = false;
        // Galetu but vienas task ir reikiami parametrai
        // Nes jie labai panasus
        taskList.addAll(Arrays.asList(new LumbridgeShop(ctx), new PortsarimShop(ctx), new LodestoneTeleport(ctx), new JobDone(ctx)));
    }
    @Override
    public void poll() {
        for (Task task : taskList) {
            if (task.activate()) {
                task.execute();
            }
        }

    }
}
