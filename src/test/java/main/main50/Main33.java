package main.main50;

import com.sk89q.worldedit.event.platform.Interaction;
import wf.utils.bukkit.data.PersistDataUtils;
import wf.utils.bukkit.entity.EntityUtils;
public class Main33 {


    public static void main(String[] args) {

        EntityUtils.removeEntityByClassIf((e) -> {

            if(!PersistDataUtils.getString(null, "", e).equals("123")) return false;

            return true;
        }, Interaction.class, null);

    }


}
