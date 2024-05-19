package org.drastan.ButtonTP;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class ButtonTpConfig {
    private static Properties p;

    public static void load() {
        //Load Config settings
        FileInputStream fis = null;
        try {
            //Copy the file from the jar if it is missing
            File file = new File(ButtonTp.dataFolder + "/config.properties");
            if (!file.exists()) {
            	ButtonTp.plugin.saveResource("config.properties", true);
            }

            //Load config file
            p = new Properties();
            fis = new FileInputStream(file);
            p.load(fis);

            ButtonTpListener.delay = loadInt("WarpDelay", 0);

            ButtonTp.defaultTakeItems = loadBool("DefaultCanTakeItems", true);
            ButtonTp.defaultRestricted = loadBool("DefaultRestricted", false);
            ButtonTp.defaultMax = loadInt("DefaultMaxWarpsPerReset", 1);

            String[] defaultResetTime = loadString("DefaultResetTime", "0'0'0'0").split("'");
            ButtonTp.defaultDays = Integer.parseInt(defaultResetTime[0]);
            ButtonTp.defaultHours = Integer.parseInt(defaultResetTime[1]);
            ButtonTp.defaultMinutes = Integer.parseInt(defaultResetTime[2]);
            ButtonTp.defaultSeconds = Integer.parseInt(defaultResetTime[3]);

            ButtonTpCommand.multiplier = loadInt("CommandWarpMultiplier", 5);

            Warp.log = loadBool("LogWarps", false);
            Warp.broadcast = loadBool("BroadcastWarps", false);

            Warp.sound = loadBool("EnderManSoundWhenWarping", true);

            String string = "PLUGIN CONFIG MUST BE REGENERATED!";
            ButtonTpMessages.broadcast = loadString("WarpUsedBroadcast", string);
            ButtonTpMessages.permission = loadString("PermissionMessage", string);
            ButtonTpMessages.insufficentFunds = loadString("InsufficientFundsMessage", string);
            ButtonTpMessages.sourceInsufficentFunds = loadString("SourceInsufficientFundsMessage", string);
            ButtonTpMessages.delay = loadString("WarpDelayMessage", string);
            ButtonTpMessages.alreadyWarping = loadString("AlreadyWarpingMessage", string);
            ButtonTpMessages.cancel = loadString("WarpCancelMessage", string);
            ButtonTpMessages.cannotUseWarps = loadString("CannotUseWarpsMessage", string);
            ButtonTpMessages.noAccess = loadString("NoAccessMessage", string);
            ButtonTpMessages.cannotTakeItems = loadString("CannotTakeItemsMessage", string);
            ButtonTpMessages.cannotTakeArmor = loadString("CannotTakeArmorMessage", string);
            ButtonTpMessages.worldMissing = loadString("WorldMissingMessage", string);
            ButtonTpMessages.cannotHaveAnotherReward = loadString("CannotHaveAnotherRewardMessage", string);
            ButtonTpMessages.cannotUseAgain = loadString("CannotUseAgainMessage", string);
            ButtonTpMessages.timeRemainingReward = loadString("TimeRemainingRewardMessage", string);
            ButtonTpMessages.timeRemainingUse = loadString("TimeRemainingUseMessage", string);

            ButtonTpMessages.formatAll();
        } catch (Exception missingProp) {
        	ButtonTp.logger.severe("Failed to load ButtonTp Config");
            missingProp.printStackTrace();
        } finally {
            try {
                fis.close();
            } catch (Exception e) {
            }
        }
    }

    /**
     * Loads the given key and prints an error if the key is missing
     *
     * @param key The key to be loaded
     * @return The String value of the loaded key
     */
    private static String loadString(String key, String defaultString) {
        if (p.containsKey(key)) {
            return p.getProperty(key);
        } else {
        	ButtonTp.logger.severe("Missing value for " + key);
        	ButtonTp.logger.severe("Please regenerate the config.properties file (delete the old file to allow a new one to be created)");
        	ButtonTp.logger.severe("DO NOT POST A TICKET FOR THIS MESSAGE, IT WILL JUST BE IGNORED");
            return defaultString;
        }
    }

    /**
     * Loads the given key and prints an error if the key is not an Integer
     *
     * @param key The key to be loaded
     * @return The Integer value of the loaded key
     */
    private static int loadInt(String key, int defaultValue) {
        String string = loadString(key, null);
        try {
            return Integer.parseInt(string);
        } catch (Exception e) {
        	ButtonTp.logger.severe("The setting for " + key + " must be a valid integer");
        	ButtonTp.logger.severe("DO NOT POST A TICKET FOR THIS MESSAGE, IT WILL JUST BE IGNORED");
            return defaultValue;
        }
    }

    /**
     * Loads the given key and prints an error if the key is not a boolean
     *
     * @param key The key to be loaded
     * @return The boolean value of the loaded key
     */
    private static boolean loadBool(String key, boolean defaultValue) {
        String string = loadString(key, null);
        try {
            return Boolean.parseBoolean(string);
        } catch (Exception e) {
        	ButtonTp.logger.severe("The setting for " + key + " must be 'true' or 'false' ");
        	ButtonTp.logger.severe("DO NOT POST A TICKET FOR THIS MESSAGE, IT WILL JUST BE IGNORED");
            return defaultValue;
        }
    }
}
