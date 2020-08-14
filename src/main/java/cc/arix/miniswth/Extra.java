package cc.arix.miniswth;

import org.bukkit.Location;
import org.bukkit.World;

public class Extra {
    private MiniSWTH ref;

    public Extra(MiniSWTH r) {
        ref = r;
    }

    public Location argsToLocation(String[] args) {
        return argsToLocation(args, 0);
    }

    public Location argsToLocation(String[] args, int startAt) {
        String[] params = new String[args.length-startAt];
        for (int i = startAt; i < args.length; i++) {
            params[i - startAt] = args[startAt];
        }

        // Make sure we have a reasonable amount of params
        // from x y z
        // to world x y z yaw pitch
        if (params.length < 3 || params.length > 6) return null;

        double[] nums = {0.0, 0.0, 0.0, 0.0, 0.0};

        // Check if the first argument is a world or not
        String world = "world";
        boolean customWorld = false;
        for (World w : ref.getServer().getWorlds()) {
            if (w.getName() == params[0]) {
                customWorld = true;
                world = w.getName();
            }
        }

        // Edge case where 3 arguments would be valid but one of them is a world.
        if (customWorld && params.length == 3) return null;

        // Verify numeric value of each argument. If a block coordinate (int) is given, convert it to the middle of
        // the block by adding 0.5.
        for (int i = 0; i < params.length - (customWorld ? 1 : 0); i++) {
            try {
                int num = Integer.parseInt(params[i + (customWorld ? 1 : 0)]);
                nums[i] = (i <= 1 ? num + 0.5 : num);
            } catch (NumberFormatException e) {
                try {
                    nums[i] = Double.parseDouble(params[i + (customWorld ? 1 : 0)]);
                } catch (NumberFormatException e2) {
                    // Not an int nor a double. Fail.
                    return null;
                }
            }
        }



        return new Location(ref.getServer().getWorld(world), nums[0], nums[1], nums[2], (float) nums[3], (float) nums[4]);
    }
}
