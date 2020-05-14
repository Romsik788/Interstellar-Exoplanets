/*
 * Fun Ores -- ItemIngotAlloy
 * Copyright (C) 2018 SilentChaos512
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation version 3
 * of the License.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.rom.exoplanets.content.item;

import java.util.Arrays;
import java.util.List;

import net.minecraft.item.Item;
import net.rom.exoplanets.content.EnumAlloy;
import net.rom.exoplanets.content.IMetal;
import net.rom.exoplanets.internal.RecipeBuilder;
import net.rom.exoplanets.util.CreativeExoTabs;

public class ItemIngotAlloy extends ItemBaseMetal {

    public ItemIngotAlloy() {
        super("ingot", "ingot");
        setCreativeTab(CreativeExoTabs.ITEMS_CREATIVE_TABS);
    }

    @Override
    public List<IMetal> getMetals(Item item) {
    	return Arrays.asList(EnumAlloy.values());
    }

    @Override
    public void addRecipes(RecipeBuilder recipes) {
        for (EnumAlloy metal : EnumAlloy.values()) {

            // Ingots <--> Blocks
                recipes.addCompression("block_" + metal.getMetalName(), metal.getIngot(), metal.getBlock(), 9);
            // Nuggets <--> Ingots
                recipes.addCompression("ingot_" + metal.getMetalName(), metal.getNugget(), metal.getIngot(), 9);
        }
    }
}