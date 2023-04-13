package wf.utils.bukkit.plugins.vault;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;


public class VaultUtils {


    private Economy economy;

    public VaultUtils(Plugin plugin) {
        RegisteredServiceProvider<Economy> economyProvider = plugin.getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) economy = economyProvider.getProvider();
    }


    public double getMoney(Player p){
        return economy.getBalance(p);
    }

    public void setMoney(Player p, double count){
        economy.depositPlayer(p, count);
    }

    public void addMoney(Player p, double count){
        economy.withdrawPlayer(p, -count);
    }

    public void removeMoney(Player p, double count){
        economy.withdrawPlayer(p, count);
    }

    public boolean containMoney(Player p, double count){
        return getMoney(p) >= count;
    }

    public Economy getEconomy() {
        return economy;
    }

    public void setEconomy(Economy economy) {
        this.economy = economy;
    }

    @Override
    public String toString() {
        return "VaultUtils{" +
                "economy=" + economy +
                '}';
    }
}
