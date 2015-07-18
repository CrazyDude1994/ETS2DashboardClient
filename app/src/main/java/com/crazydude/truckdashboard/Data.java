package com.crazydude.truckdashboard;

import com.google.gson.annotations.JsonAdapter;

/**
 * Created by Crazy on 17.07.2015.
 */
public class Data {

    private PluginInfo plugin;
    private GameStateInfo game;
    private InputInfo input;
    private TrailerInfo trailer;
    private TruckInfo truck;

    public TruckInfo getTruck() {
        return truck;
    }

    public PluginInfo getPlugin() {
        return plugin;
    }

    public GameStateInfo getGame() {
        return game;
    }

    public InputInfo getInput() {
        return input;
    }

    public TrailerInfo getTrailer() {
        return trailer;
    }

    public class PluginInfo {
        private int revision;
        private int major;
        private int minor;

        public int getRevision() {
            return revision;
        }

        public int getMajor() {
            return major;
        }

        public int getMinor() {
            return minor;
        }
    }

    public class GameStateInfo {
        private int time_absolute;
        private byte paused;
        private int time;

        public int getTime_absolute() {
            return time_absolute;
        }

        public byte getPaused() {
            return paused;
        }

        public int getTime() {
            return time;
        }
    }

    public class InputInfo {
        private float game_throttle;
        private float game_steer;
        private float game_brake;
        private float game_clutch;
        private float user_steer;
        private float user_throttle;
        private float user_brake;
        private float user_clutch;

        public float getGame_throttle() {
            return game_throttle;
        }

        public float getGame_steer() {
            return game_steer;
        }

        public float getGame_brake() {
            return game_brake;
        }

        public float getGame_clutch() {
            return game_clutch;
        }

        public float getUser_steer() {
            return user_steer;
        }

        public float getUser_throttle() {
            return user_throttle;
        }

        public float getUser_brake() {
            return user_brake;
        }

        public float getUser_clutch() {
            return user_clutch;
        }
    }

    public class TrailerInfo {
        private String trailer_id;
        private String trailer_name;
        private int trailer_length;
        private int trailer_offset;
        private float trailer_weight;

        public String getTrailer_id() {
            return trailer_id;
        }

        public String getTrailer_name() {
            return trailer_name;
        }

        public int getTrailer_length() {
            return trailer_length;
        }

        public int getTrailer_offset() {
            return trailer_offset;
        }

        public float getTrailer_weight() {
            return trailer_weight;
        }
    }

    public class Vector3 {
        private float x, y, z;

        public float getX() {
            return x;
        }

        public float getY() {
            return y;
        }

        public float getZ() {
            return z;
        }
    }

    public class GearInfo {
        private int gear, gears, gear_ranges, gear_range_active;

        public int getGear() {
            return gear;
        }

        public int getGears() {
            return gears;
        }

        public int getGear_ranges() {
            return gear_ranges;
        }

        public int getGear_range_active() {
            return gear_range_active;
        }
    }

    public class EngineInfo {
        private float engine_rpm, engine_rpm_max;

        public float getEngine_rpm() {
            return engine_rpm;
        }

        public float getEngine_rpm_max() {
            return engine_rpm_max;
        }
    }

    public class FuelInfo {
        private float fuel, fuel_capacity, fuel_rate, fuel_avg_consumption;

        public float getFuel() {
            return fuel;
        }

        public float getFuel_capacity() {
            return fuel_capacity;
        }

        public float getFuel_rate() {
            return fuel_rate;
        }

        public float getFuel_avg_consumption() {
            return fuel_avg_consumption;
        }
    }

    public class TruckInfo {
        private boolean engine_enabled;
        private boolean trailer_attached;
        private float speed;
        private Vector3 acceleration;
        private Vector3 position;
        private Vector3 rotation;
        private GearInfo gear_info;
        private EngineInfo engine_info;
        private FuelInfo fuel_info;
        private float truck_weight;
        private int model_offset;
        private int model_length;

        public boolean getEngine_enabled() {
            return engine_enabled;
        }

        public boolean getTrailer_attached() {
            return trailer_attached;
        }

        public float getSpeed() {
            return speed;
        }

        public Vector3 getAcceleration() {
            return acceleration;
        }

        public Vector3 getPosition() {
            return position;
        }

        public Vector3 getRotation() {
            return rotation;
        }

        public GearInfo getGear_info() {
            return gear_info;
        }

        public EngineInfo getEngine_info() {
            return engine_info;
        }

        public FuelInfo getFuel_info() {
            return fuel_info;
        }

        public float getTruck_weight() {
            return truck_weight;
        }

        public int getModel_offset() {
            return model_offset;
        }

        public int getModel_length() {
            return model_length;
        }
    }
}
