/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2020, ROMVoid95 <rom.readonlydev@gmail.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package net.rom.exoplanets.astronomy.yzceti.c;

import java.util.ArrayList;
import java.util.List;

import micdoodle8.mods.galacticraft.api.galaxies.CelestialBody;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.BiomeAdaptive;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DimensionType;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.rom.exoplanets.astronomy.yzceti.YzCetiBlocks;
import net.rom.exoplanets.astronomy.yzceti.YzCetiDimensions;
import net.rom.exoplanets.astronomy.yzceti.c.worldgen.BiomeProviderYzCetiC;
import net.rom.exoplanets.init.ExoUniverse;
import net.rom.exoplanets.internal.world.WorldProviderExoPlanet;
import net.rom.exoplanets.internal.world.planet.ExoPlanet;

public class WorldProviderYzCetiC extends WorldProviderExoPlanet {

	@Override
	public float getSolarSize() {
		return 1.0F;
	}

	@Override
	public double getMeteorFrequency() {
		return 2.0;
	}

	@Override
	public double getFuelUsageMultiplier() {
		return 3.8D;
	}

	@Override
	public boolean hasBreathableAtmosphere() {
		return this.getPlanet().isBreathable();
	}

	@Override
	public float getFallDamageModifier() {
		return 0.56F;
	}

	@Override
	public float getSoundVolReductionAmount() {
		return 0.0F;
	}

	@Override
	public float getThermalLevelModifier() {
		return 2.5F;
	}

	@Override
	public float getPlanetTemp() {
		ExoPlanet planet = this.getPlanet();
		float planetTemp = (float) planet.getPlanetTemp();

		if (this.isDaytime()) {
			planetTemp *= 4.5F;
		} else {
			planetTemp = (float) planet.getPlanetTemp();
		}
		return planetTemp;
	}

	@Override
	public double getSolarEnergyMultiplier() {
		return 8.65;
	}

	@Override
	public double getYCoordinateToTeleport() {
		return 2000.0D;
	}

	@Override
	public float getCloudHeight() {
		return 128F;
	}

    @Override
    public Vector3 getSkyColor() {
        return new Vector3(0, 0, 0);
    }

	@Override
	public boolean canRainOrSnow() {
		return this.getPlanet().isDoesRain();
	}

	@Override
	public boolean hasSunset() {
		return true;
	}

	@Override
	public boolean shouldDisablePrecipitation() {
		return !this.canRainOrSnow();
	}

	@Override
	public boolean canDoRainSnowIce(Chunk chunk) {
		return this.canRainOrSnow();
	}

	@Override
	public boolean canRespawnHere() {
		return this.shouldForceRespawn();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public float getStarBrightness(float par1) {
		float f1 = this.world.getCelestialAngle(par1);
		float f2 = 1.0F - (MathHelper.cos(f1 * (float) Math.PI * 2.0F) * 2.0F + 0.30F);

		if (f2 < 0.0F) {
			f2 = 0.0F;
		}
		if (f2 > 1.0F) {
			f2 = 1.0F;
		}
		return f2 * f2 * 1.8F;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public float getSunBrightness(float par1) {
		float f1 = this.world.getCelestialAngle(1.0F);
		float f2 = 0.9F - (MathHelper.cos(f1 * (float) Math.PI * 2.0F) * 2.0F + 0.2F);

		if (f2 < 0.0F) {
			f2 = 0.0F;
		}
		if (f2 > 1.0F) {
			f2 = 1.0F;
		}
		f2 = 1.0F - f2;
		return f2 * 1.5F;
	}

	@Override
	public CelestialBody getCelestialBody() {
		return ExoUniverse.yzcetic;
	}

	@Override
	public double getHorizon() {
		return 44.0D;
	}

	@Override
	public int getAverageGroundLevel() {
		return 65;
	}

	@Override
	public boolean canCoordinateBeSpawn(int var1, int var2) {
		return true;
	}

	@Override
	public ResourceLocation getDungeonChestType() {
		return null;
	}

	@Override
	public List<Block> getSurfaceBlocks() {
		ArrayList<Block> blockList = new ArrayList<>();
		blockList.add(YzCetiBlocks.CetiC.C_SEDIMENTARYROCK);
		blockList.add(YzCetiBlocks.CetiC.C_IGNEOUS);
		blockList.add(YzCetiBlocks.CetiC.C_GRAVEL);
		return blockList;
	}

	@Override
	public DimensionType getDimensionType() {
		return YzCetiDimensions.YZCETIC;
	}

	@Override
	public boolean isSkyColored() {
		return true;
	}

	@Override
	public Class<? extends BiomeProvider> getBiomeProviderClass() {
		BiomeAdaptive.setBodyMultiBiome(ExoUniverse.yzcetic);
		return BiomeProviderYzCetiC.class;
	}

	@Override
	public Class<? extends IChunkGenerator> getChunkProviderClass() {
		return ChunkProviderYzCetiC.class;
	}

	@Override
	public long getDayLength() {
		return 28000L;
	}

	@Override
	public float getGravity() {
		return 0.030F;
	}

	@Override
	public ExoPlanet getExoPlanet() {
		return (ExoPlanet) getCelestialBody();
	}

}