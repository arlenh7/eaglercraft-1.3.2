package net.peyton.eagler.minecraft.suppliers;

import net.minecraft.src.WorldSavedData;

public interface WorldSavedDataSupplier<T extends WorldSavedData> {
	T createWorldSavedData(String name);
}
