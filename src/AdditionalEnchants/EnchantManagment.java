package AdditionalEnchants;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import PvpBalance.Effects;

public class EnchantManagment {
	public static final String CONFIRM_CODE = "FGHF";
	public static int enchantmentType(ItemStack weapon){
		//1 = healing
		//2 = drainlife
		//0 = nothing
		ItemMeta meta = weapon.getItemMeta();
		if(weapon.getType() != null && weapon.hasItemMeta()){
			List<String> lore = meta.getLore();
			if(lore != null && lore.toString().contains(CONFIRM_CODE)){
				if(lore.get(2).toString().equalsIgnoreCase("Healing")){
					return 1;
				}
				if(lore.contains("Drainlife")){
					return 2;
				}
			}
		}
		return 0;
	}
	public static boolean executeEnchant(Player playerHitting, Player playerHit,ItemStack weapon){
		int enchantmentType = enchantmentType(weapon);
		if(enchantmentType != 0){
			switch(enchantmentType){
			case 1:
				if(hasCharges(weapon) == true){
					Healing.heal(playerHitting, playerHit);
					Effects.healEffect(playerHit);
					return true;
				}
				break;
			case 2:
				return true;
			default:
				return false;
				
			}
		}
		return false;
	}
	public static boolean hasCharges(ItemStack weapon){
		ItemMeta meta = weapon.getItemMeta();
		List<String> lore = meta.getLore();
		if(lore.toString().contains(CONFIRM_CODE)){
			String charge = lore.get(3);
			String[] check = charge.split(":");
			int charges = Integer.parseInt(check[1]);
			if(charges > 0){
				lore.set(3, "CHARGES:" + (charges -1));
				meta.setLore(lore);
				weapon.setItemMeta(meta);
				return true;
			}
		}
		return false;
	}
	@SuppressWarnings("null")
	public static void addCharges(Player player, ItemStack weapon){
		if(player.getInventory().contains(Material.GOLD_INGOT)){
			int goldCount = 0;
			ItemMeta meta = weapon.getItemMeta();
			List<String> lore = meta.getLore();
			if(lore != null && lore.toString().contains(CONFIRM_CODE)){
				for(ItemStack stack : player.getInventory()){
					if(stack != null){
						if(stack.getType() == Material.GOLD_INGOT){
							goldCount += stack.getAmount();
							player.getInventory().remove(stack);
							break;
						}
					}
				}
				if(goldCount > 0){
					String charge = lore.get(3);
					String[] check = charge.split(":");
					int charges = Integer.parseInt(check[1]);
					charges += goldCount;
					lore.set(3, "CHARGES:" + (charges));
					meta.setLore(lore);
					weapon.setItemMeta(meta);
					player.sendMessage(ChatColor.GREEN + "CHARGES INCREASED TO " + charges);
				}
			}
			else{
				player.sendMessage(ChatColor.RED + "You cannot charge this item it is not a healing rod");
			}
		}
		else{
			player.sendMessage(ChatColor.RED + "You need more then 5 gold ingots to charge that");
		}
	}
}
