/**
 * Copyright (C) 2020 Interstellar:  Exoplanets
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.rom.exoplanets.conf;

import java.io.File;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class InitConfigFiles {
	
	public static void init(FMLPreInitializationEvent event) {
		
		registerEventHandler(new SConfigSystems(new File(event.getModConfigurationDirectory(), "Exoplanets/systems.cfg")));
		registerEventHandler(new SConfigDimensionID(new File(event.getModConfigurationDirectory(), "Exoplanets/dimensions.cfg")));
		registerEventHandler(new SConfigCore(new File(event.getModConfigurationDirectory(), "Exoplanets/core.cfg")));
	}
	
    public static void registerEventHandler(Object handler) {
        MinecraftForge.EVENT_BUS.register(handler);
    }
}
