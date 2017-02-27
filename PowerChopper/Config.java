package scripts.PowerChopper;

import org.powerbot.script.Tile;

/**
 * Created by turbo on 2016-02-08.
 */
public class Config {
    // Normal tree = {38786, 38760, 38783, 38787, 38788, 38784}
    public static int[] objectsIds = {38786, 38760, 38783, 38787, 38788, 38784};
    public static int productId = 1511;

    /* Travels from Rimmington mines to Clan Camp Bank
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

    /* Travels from Rimmington forest to Clan Camp Bank */
    public static final Tile[] PATH = {
            new Tile(2994, 3210, 0),
            new Tile(2987, 3201, 0),
            new Tile(2987, 3211, 0),
            new Tile(2982, 3223, 0),
            new Tile(2974, 3231, 0),
            new Tile(2970, 3239, 0),
            new Tile(2972, 3248, 0),
            new Tile(2969, 3255, 0),
            new Tile(2968, 3264, 0),
            new Tile(2966, 3273, 0),
            new Tile(2964, 3282, 0),
            new Tile(2961, 3288, 0),
            new Tile(2957, 3295, 0)
    };


}
