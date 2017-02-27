package scripts.feathermaster;

import org.powerbot.script.rt6.ClientContext;

/**
 * Created by tvari on 2016-02-09.
 */
public class JobDone extends Task<ClientContext> {

    public JobDone(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public boolean activate() {
        return FeatherMaster.boughtPortsarim
                && FeatherMaster.boughtLumbridge;
    }

    @Override
    public void execute() {
        // Job done, sleep or whatevar
        // Stopping
        ctx.controller.stop();
    }
}
