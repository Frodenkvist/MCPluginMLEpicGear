package AdditionalEnchants;

import org.bukkit.entity.Player;

import PvpBalance.PVPPlayer;
import PvpBalance.PvpHandler;

public class Healing {
	private static int heal = SaveLoad.LoadSave.HealPot/10;
	public static void heal(Player playerHitting,Player playerHit){
		PVPPlayer pvpHit = PvpHandler.getPvpPlayer(playerHit);
		pvpHit.sethealth(pvpHit.gethealth() + heal);
	}
}
