package scripts.MM_IronBanker;

import org.powerbot.script.*;
import org.powerbot.script.rt6.*;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.GeItem;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Script.Manifest(name = "Iron & banks", description = "Mines iron and then banks it")

public class IronBanker extends PollingScript<ClientContext> implements MessageListener, PaintListener {
    private List<Task> taskList = new ArrayList<Task>();
    private int ironOreID = 440;
    private int oresMined;
    private int oreGePrice = GeItem.price(ironOreID);

    @Override
    public void start() {
        taskList.addAll(Arrays.asList(new Mine(ctx), new WalkToMines(ctx), new WalkToBank(ctx), new Bank(ctx)));
        //System.out.print(!ctx.objects.id(72081).nearest().poll().inViewport());
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

    @Override
    public void messaged(MessageEvent e) {
        final String msg = e.text().toLowerCase();
        //when we receive a message that says "20 coins have been removed..."
        if (msg.equals("you manage to mine some iron.")) {
            //that means we bought a fur; increment the count.
            oresMined++;
        }
    }

    public static final Font TAHOMA = new Font("Tahoma", Font.PLAIN, 12);

    @Override
    public void repaint(Graphics graphics) {
        final Graphics2D g = (Graphics2D) graphics;
        g.setFont(TAHOMA);

        final int profit = oreGePrice * oresMined;
        final int profitHr = (int) ((profit * 3600000D) / getRuntime());
        final int oresHr = (int) ((oresMined * 3600000D) / getRuntime());
        final int exp = oresMined * 35;
        final int expHr = (int) ((exp * 3600000D) / getRuntime());

        g.setColor(Color.BLACK);
        g.fillRect(5, 5, 160, 70);

        g.setColor(Color.WHITE);

        g.drawString(String.format("Ores: %,d (%,d)", oresMined, oresHr), 10, 20);
        g.drawString(String.format("Profit: %,d (%,d)", profit, profitHr), 10, 40);
        g.drawString(String.format("Exp: %,d (%,d)", exp, expHr), 10, 60);
    }
}
