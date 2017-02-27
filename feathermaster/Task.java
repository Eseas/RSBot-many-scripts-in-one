package scripts.feathermaster;

import org.powerbot.script.ClientAccessor;
import org.powerbot.script.ClientContext;

/**
 * Created by tvari on 2016-02-05.
 */
public abstract class Task<C extends ClientContext> extends ClientAccessor<C>{
    public Task(C ctx) {
        super(ctx);
    }

    public abstract boolean activate();
    public abstract void execute();
}
