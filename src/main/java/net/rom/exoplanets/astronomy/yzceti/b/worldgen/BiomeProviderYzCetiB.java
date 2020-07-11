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

package net.rom.exoplanets.astronomy.yzceti.b.worldgen;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import micdoodle8.mods.galacticraft.api.galaxies.CelestialBody;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.BiomeAdaptive;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeCache;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.rom.exoplanets.astronomy.yzceti.b.worldgen.biome.genlayer.GenLayerYzCetiB;
import net.rom.exoplanets.init.ExoUniverse;

public class BiomeProviderYzCetiB extends BiomeProvider {

	private GenLayer unzoomedBiomes;
	private GenLayer zoomedBiomes;
	private BiomeCache biomeCache;
	private List<Biome> biomesToSpawnIn;
	private CelestialBody body;

	protected BiomeProviderYzCetiB() {
		body = ExoUniverse.yzcetib;
		biomeCache = new BiomeCache(this);
		biomesToSpawnIn = new ArrayList<>();
	}

	public BiomeProviderYzCetiB(long seed, WorldType type) {
		this();
		GenLayer[] genLayers = GenLayerYzCetiB.createWorld(seed);
		this.unzoomedBiomes = genLayers[0];
		this.zoomedBiomes = genLayers[1];
	}

	public BiomeProviderYzCetiB(World world) {
		this(world.getSeed(), world.getWorldInfo().getTerrainType());
	}

	@Override
	public List<Biome> getBiomesToSpawnIn() {
		return this.biomesToSpawnIn;
	}

	@Override
	public Biome getBiome(BlockPos pos, Biome defaultBiome) {
		BiomeAdaptive.setBodyMultiBiome(body);
		return this.biomeCache.getBiome(pos.getX(), pos.getZ(), BiomeAdaptive.biomeDefault);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public float getTemperatureAtHeight(float par1, int par2) {
		return par1;
	}

	@Override
	public Biome[] getBiomesForGeneration(Biome[] biomes, int x, int z, int length, int width) {
		IntCache.resetIntCache();
		BiomeAdaptive.setBodyMultiBiome(body);

		if (biomes == null || biomes.length < length * width) {
			biomes = new Biome[length * width];
		}

		int[] intArray = unzoomedBiomes.getInts(x, z, length, width);

		for (int i = 0; i < length * width; ++i) {
			if (intArray[i] >= 0) {
				biomes[i] = Biome.getBiome(intArray[i]);
			} else {
				biomes[i] = BiomeAdaptive.biomeDefault;
			}
		}

		return biomes;
	}

	@Override
	public Biome[] getBiomes(@Nullable Biome[] oldBiomeList, int x, int z, int width, int depth) {
		return getBiomes(oldBiomeList, x, z, width, depth, true);
	}

	@Override
	public Biome[] getBiomes(@Nullable Biome[] listToReuse, int x, int z, int width, int length, boolean cacheFlag) {
		IntCache.resetIntCache();
		BiomeAdaptive.setBodyMultiBiome(body);

		if (listToReuse == null || listToReuse.length < length * width) {
			listToReuse = new Biome[width * length];
		}

		if (cacheFlag && width == 16 && length == 16 && (x & 15) == 0 && (z & 15) == 0) {
			Biome[] cached = this.biomeCache.getCachedBiomes(x, z);
			System.arraycopy(cached, 0, listToReuse, 0, width * length);
			return listToReuse;
		}

		int[] zoomed = zoomedBiomes.getInts(x, z, width, length);

		for (int i = 0; i < width * length; ++i) {
			if (zoomed[i] >= 0) {
				listToReuse[i] = Biome.getBiome(zoomed[i]);
			} else {
				listToReuse[i] = BiomeAdaptive.biomeDefault;
			}
		}

		return listToReuse;
	}

	@Override
	public boolean areBiomesViable(int x, int z, int range, List<Biome> viables) {
		int i = x - range >> 2;
		int j = z - range >> 2;
		int k = x + range >> 2;
		int l = z + range >> 2;
		int diffX = (k - i) + 1;
		int diffZ = (l - j) + 1;
		int[] unzoomed = this.unzoomedBiomes.getInts(i, j, diffX, diffZ);

		for (int a = 0; a < diffX * diffZ; ++a) {
			Biome biome = Biome.getBiome(unzoomed[a]);

			if (!viables.contains(biome)) {
				return false;
			}
		}

		return true;
	}

	@Override
	public BlockPos findBiomePosition(int x, int z, int range, List<Biome> biomes, Random random) {
		int i = x - range >> 2;
		int j = z - range >> 2;
		int k = x + range >> 2;
		int l = z + range >> 2;
		int diffX = (k - i) + 1;
		int diffZ = (l - j) + 1;
		int[] unzoomed = this.unzoomedBiomes.getInts(i, j, diffX, diffZ);
		BlockPos blockPos = null;
		int count = 0;

		for (int a = 0; a < unzoomed.length; ++a) {
			int x0 = i + a % diffX << 2;
			int z0 = j + a / diffX << 2;
			Biome biome = Biome.getBiome(unzoomed[a]);

			if (biomes.contains(biome) && (blockPos == null || random.nextInt(count + 1) == 0)) {
				blockPos = new BlockPos(x0, 0, z0);
				count++;
			}
		}

		return blockPos;
	}

	@Override
	public void cleanupCache() {
		this.biomeCache.cleanupCache();
	}
}