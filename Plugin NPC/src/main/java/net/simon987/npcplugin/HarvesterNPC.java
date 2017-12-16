package net.simon987.npcplugin;

import net.simon987.server.GameServer;
import net.simon987.server.game.Direction;
import org.json.simple.JSONObject;

public class HarvesterNPC extends NonPlayerCharacter {

    public static final int ID = 10; //todo change


    public HarvesterNPC() {
        setTask(new HarvestTask());
        hp = 10;
    }

    @Override
    public void update() {

        if (hp <= 0) {
            setDead(true);
            //TODO: drop biomass

        }


        if (getTask().checkCompleted()) {

            setTask(new HarvestTask());

        } else {
            getTask().tick(this);
        }
    }

    @Override
    public JSONObject serialise() {
        JSONObject json = super.serialise();

        json.put("i", getObjectId());
        json.put("x", getX());
        json.put("y", getY());
        json.put("direction", getDirection().ordinal());
        json.put("hp", hp);
        json.put("energy", energy);
        json.put("action", getAction().ordinal());
        json.put("t", 10);

        return json;
    }

    public static HarvesterNPC deserialize(JSONObject json) {

        HarvesterNPC npc = new HarvesterNPC();
        npc.setObjectId((int) (long) json.get("i"));
        npc.setX((int) (long) json.get("x"));
        npc.setY((int) (long) json.get("y"));
        npc.hp = (int) (long) json.get("hp");
        npc.setDirection(Direction.getDirection((int) (long) json.get("direction")));
        npc.energy = (int) (long) json.get("energy");
        npc.maxEnergy = GameServer.INSTANCE.getConfig().getInt("battery_max_energy");

        return npc;

    }
}