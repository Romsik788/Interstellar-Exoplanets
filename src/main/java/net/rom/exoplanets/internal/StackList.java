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

package net.rom.exoplanets.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public final class StackList extends ArrayList<ItemStack> {
    /**
	 * 
	 */
	private static final long serialVersionUID = 6598290132403044449L;

	private StackList() {
    }

    public static StackList of(ItemStack... stacks) {
        StackList newList = new StackList();
        Collections.addAll(newList, stacks);
        return newList;
    }

    public static StackList fromInventory(IInventory inventory) {
        StackList newList = new StackList();
        for (int i = 0; i < inventory.getSizeInventory(); ++i) {
            newList.add(inventory.getStackInSlot(i));
        }
        return newList;
    }

    public static StackList fromNBT(NBTTagList tagList) {
        StackList newList = new StackList();
        for (NBTBase nbt : tagList) {
            if (nbt instanceof NBTTagCompound) {
                newList.add(new ItemStack((NBTTagCompound) nbt));
            }
        }
        return newList;
    }

    //region Convenience methods

    public ItemStack firstOfType(Class<?> itemClass) {
        return firstMatch(itemClassMatcher(itemClass));
    }

    public ItemStack firstMatch(Predicate<ItemStack> predicate) {
        return stream().filter(predicate).findFirst().orElse(ItemStack.EMPTY);
    }

    public ItemStack uniqueOfType(Class<?> itemClass) {
        return uniqueMatch(itemClassMatcher(itemClass));
    }

    public ItemStack uniqueMatch(Predicate<ItemStack> predicate) {
        return stream().filter(predicate).collect(Collectors.collectingAndThen(Collectors.toList(),
                list -> list.size() == 1 ? list.get(0) : ItemStack.EMPTY));
    }

    public Collection<ItemStack> allOfType(Class<?> itemClass) {
        return allMatches(itemClassMatcher(itemClass));
    }

    public Collection<ItemStack> allMatches(Predicate<ItemStack> predicate) {
        return stream().filter(predicate).collect(Collectors.toList());
    }

    public int countOfType(Class<?> itemClass) {
        return countOfMatches(itemClassMatcher(itemClass));
    }

    public int countOfMatches(Predicate<ItemStack> predicate) {
        return (int) stream().filter(predicate).count();
    }

    private static Predicate<ItemStack> itemClassMatcher(Class<?> itemClass) {
        return stack -> itemClass.isInstance(stack.getItem());
    }

    @Override
    public boolean add(ItemStack itemStack) {
        return !itemStack.isEmpty() && super.add(itemStack);
    }

    @Override
    public boolean addAll(Collection<? extends ItemStack> c) {
        boolean added = false;
        for (ItemStack stack : c) {
            if (!stack.isEmpty()) {
                added |= super.add(stack);
            }
        }
        return added;
    }

    @Override
    public boolean addAll(int index, Collection<? extends ItemStack> c) {
        boolean added = false;
        for (ItemStack stack : c) {
            if (!stack.isEmpty()) {
                super.add(index, stack);
                added = true;
            }
        }
        return added;
    }

    @Override
    public void add(int index, ItemStack element) {
        if (!element.isEmpty()) {
            super.add(index, element);
        }
    }
}
