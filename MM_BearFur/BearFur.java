package scripts.MM_BearFur;

import org.powerbot.script.*;
import org.powerbot.script.rt6.ChatOption;
import org.powerbot.script.rt6.GeItem;
import org.powerbot.script.rt6.Npc;
import org.powerbot.script.rt6.TilePath;
import org.powerbot.script.rt6.ClientContext;

import java.awt.*;
import java.util.concurrent.Callable;

@Script.Manifest(
        name = "Auto Bear Fur",
        description = "Buys bear fur for no requirements money making.",
        properties = "client = 6;"
)
//implements MessageListener so we can react based on incoming game messages
//PaintListener to draw a paint
public class BearFur extends PollingScript<ClientContext> implements MessageListener, PaintListener {

    public static final int BEAR_FUR_COST = 20;
    public static final int BEAR_FUR = 948;
    public static final int BARAEK = 547;
    public static final String[] CHAT_OPTIONS = {"Can you sell me some furs?", "Yeah, okay, here you go."};
    public static final Tile[] PATH = {
            new Tile(3186, 3437, 0),
            new Tile(3186, 3432, 0),
            new Tile(3193, 3429, 0),
            new Tile(3203, 3429, 0),
            new Tile(3212, 3433, 0),
            new Tile(3216, 3434, 0)
    };

    private TilePath pathToBank, pathToTrader;
    private int fursBought = 0, furGePrice = 0;

    @Override
    public void start() {
        //creates the TilePath instances to walk from bank to trader, and from trader to bank
        //the arguments are the context instance, and a Tile[] of the tiles that make up the path
        pathToTrader = new TilePath(ctx, PATH);
        pathToBank = new TilePath(ctx, PATH).reverse();
        //furGePrice = GeItem.profile(BEAR_FUR).price(GeItem.PriceType.CURRENT).price();
        furGePrice = 163;
    }

    @Override
    public void poll() {
        //if the user does not have enough gold to buy a fur, stop the script
        if (ctx.backpack.moneyPouchCount() < BEAR_FUR_COST) {
            ctx.controller.stop();
        }

        final State state = getState();
        if (state == null) {
            return;
        }
        switch (state) {
            case WALK_TO_TRADER:
                //traverse() finds the next tile in the path, and returns if it successfully stepped towards that tile
                pathToTrader.traverse();
                break;
            case BUY_FUR:
                //chatting() returns if the player is currently chatting with something/someone
                //if the user is not chatting...
                if (!ctx.chat.chatting()) {
                    //... talk to the trader
                    final Npc trader = ctx.npcs.select().id(BARAEK).nearest().poll();
                    if (trader.interact("Talk-to")) {
                        //wait until the chat interface comes up (prevents spam clicking) for a maximum of 2500ms (250*10)
                        //Condition.wait(condition to wait for, how long to sleep for each iteration, how many iterations to go through)
                        //sleeps until call() returns true, then wait() returns true;
                        //or times out and returns false
                        Condition.wait(new Callable<Boolean>() {
                            @Override
                            public Boolean call() throws Exception {
                                return ctx.chat.chatting();
                            }
                        }, 250, 10);
                    }
                }
                //queryContinue() returns if there is a "continue" button available in the chatting interface
                //if there is a continue button...
                else if (ctx.chat.queryContinue()) {
                    //...continue through it
                    //clickContinue() accepts a boolean argument
                    //pass 'true' to use a key (press Enter), pass 'false' to use the mouse
                    ctx.chat.clickContinue(true);
                    //sleep for 350-500ms
                    Condition.sleep(Random.nextInt(350, 500));
                }
                //Chat query works like any other query, except for the chat options
                //finds all chat options that match the CHAT_OPTIONS argument
                //if there is an option that matches...
                else if (!ctx.chat.select().text(CHAT_OPTIONS).isEmpty()) {
                    final ChatOption option = ctx.chat.poll();
                    //... select that option (true to use key instead of mouse)
                    if (option.select(true)) {
                        //sleep until the interface is completed
                        Condition.wait(new Callable<Boolean>() {
                            @Override
                            public Boolean call() throws Exception {
                                return !option.valid();
                            }
                        }, 250, 10);
                    }
                }
                break;
            case WALK_TO_BANK:
                pathToBank.traverse();
                break;
            case BANK:
                //opened() returns if the bank is currently open
                //if the bank is not open...
                if (!ctx.bank.opened()) {
                    //... open it
                    ctx.bank.open();
                }
                //if the user has bear fur in their inventory
                else if (!ctx.backpack.select().id(BEAR_FUR).isEmpty()) {
                    //click the "deposit inventory" button in the bank
                    ctx.bank.depositInventory();
                } else {
                    //close the bank when we're done
                    ctx.bank.close();
                }
                break;
        }
    }

    private State getState() {
        if (ctx.bank.opened()) {
            return State.BANK;
        } else if (ctx.backpack.select().count() < 28) {
            if (!ctx.npcs.select().id(BARAEK).within(10).isEmpty()) {
                return State.BUY_FUR;
            } else {
                return State.WALK_TO_TRADER;
            }
        } else if (!ctx.bank.inViewport()) {
            return State.WALK_TO_BANK;
        } else if (ctx.bank.nearest().tile().distanceTo(ctx.players.local()) < 10) {
            return State.BANK;
        }
        return null;
    }

    private enum State {
        WALK_TO_TRADER, BUY_FUR, WALK_TO_BANK, BANK
    }

    @Override
    public void messaged(MessageEvent e) {
        final String msg = e.text().toLowerCase();
        //when we receive a message that says "20 coins have been removed..."
        if (msg.equals("20 coins have been removed from your money pouch.")) {
            //that means we bought a fur; increment the count.
            fursBought++;
        }
    }

    public static final Font TAHOMA = new Font("Tahoma", Font.PLAIN, 12);

    @Override
    public void repaint(Graphics graphics) {
        final Graphics2D g = (Graphics2D) graphics;
        g.setFont(TAHOMA);

        final int profit = furGePrice * fursBought;
        final int profitHr = (int) ((profit * 3600000D) / getRuntime());
        final int fursHr = (int) ((fursBought * 3600000D) / getRuntime());

        g.setColor(Color.BLACK);
        g.fillRect(5, 5, 125, 45);

        g.setColor(Color.WHITE);

        g.drawString(String.format("Furs: %,d (%,d)", fursBought, fursHr), 10, 20);
        g.drawString(String.format("Profit: %,d (%,d)", profit, profitHr), 10, 40);
    }
}