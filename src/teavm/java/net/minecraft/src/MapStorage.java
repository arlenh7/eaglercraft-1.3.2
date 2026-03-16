package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.lax1dude.eaglercraft.internal.vfs2.VFile2;
import net.peyton.eagler.minecraft.suppliers.WorldSavedDataSupplier;

public class MapStorage {
	private ISaveHandler saveHandler;
	private Map loadedDataMap = new HashMap();
	private List loadedDataList = new ArrayList();
	private Map idCounts = new HashMap();
	private static final Map classToSupplierMapping = new HashMap();

	private static void addMapping(Class par0Class, WorldSavedDataSupplier par1Supplier) {
		classToSupplierMapping.put(par0Class, par1Supplier);
	}

	static {
		addMapping(MapData.class, MapData::new);
	}

	public MapStorage(ISaveHandler par1ISaveHandler) {
		this.saveHandler = par1ISaveHandler;
		this.loadIdCounts();
	}

	public WorldSavedData loadData(Class par1Class, String par2Str) {
		WorldSavedData var3 = (WorldSavedData) this.loadedDataMap.get(par2Str);

		if (var3 != null) {
			return var3;
		} else {
			if (this.saveHandler != null) {
				try {
					VFile2 var4 = this.getMapFile(par2Str);

					if (var4 != null && var4.exists()) {
						try {
							WorldSavedDataSupplier var7Supplier = (WorldSavedDataSupplier) classToSupplierMapping
									.get(par1Class);

							if (var7Supplier != null) {
								var3 = var7Supplier.createWorldSavedData(par2Str);
							} else {
								var3 = (WorldSavedData) par1Class.getConstructor(new Class[] { String.class })
										.newInstance(new Object[] { par2Str });
							}
						} catch (Exception var7) {
							throw new RuntimeException("Failed to instantiate " + par1Class.toString(), var7);
						}

						try (InputStream var5 = var4.getInputStream()) {
							NBTTagCompound var6 = CompressedStreamTools.readCompressed(var5);
							var3.readFromNBT(var6.getCompoundTag("data"));
						}
					}
				} catch (Exception var8) {
					var8.printStackTrace();
				}
			}

			if (var3 != null) {
				this.loadedDataMap.put(par2Str, var3);
				this.loadedDataList.add(var3);
			}

			return var3;
		}
	}

	public void setData(String par1Str, WorldSavedData par2WorldSavedData) {
		if (par2WorldSavedData == null) {
			throw new RuntimeException("Can\'t set null data");
		} else {
			if (this.loadedDataMap.containsKey(par1Str)) {
				this.loadedDataList.remove(this.loadedDataMap.remove(par1Str));
			}

			this.loadedDataMap.put(par1Str, par2WorldSavedData);
			this.loadedDataList.add(par2WorldSavedData);
		}
	}

	public void saveAllData() {
		Iterator var1 = this.loadedDataList.iterator();

		while (var1.hasNext()) {
			WorldSavedData var2 = (WorldSavedData) var1.next();

			if (var2.isDirty()) {
				this.saveData(var2);
				var2.setDirty(false);
			}
		}
	}

	private void saveData(WorldSavedData par1WorldSavedData) {
		if (this.saveHandler != null) {
			try {
				VFile2 var2 = this.getMapFile(par1WorldSavedData.mapName);

				if (var2 != null) {
					NBTTagCompound var3 = new NBTTagCompound();
					par1WorldSavedData.writeToNBT(var3);
					NBTTagCompound var4 = new NBTTagCompound();
					var4.setCompoundTag("data", var3);
					try (OutputStream var5 = var2.getOutputStream()) {
						CompressedStreamTools.writeCompressed(var4, var5);
					}
				}
			} catch (Exception var6) {
				var6.printStackTrace();
			}
		}
	}

	private void loadIdCounts() {
		try {
			this.idCounts.clear();

			if (this.saveHandler == null) {
				return;
			}

			VFile2 var1 = this.getMapFile("idcounts");

			if (var1 != null && var1.exists()) {
				try (DataInputStream var2 = new DataInputStream(var1.getInputStream())) {
					NBTTagCompound var3 = CompressedStreamTools.read(var2);
					Iterator var4 = var3.getTags().iterator();

					while (var4.hasNext()) {
						NBTBase var5 = (NBTBase) var4.next();

						if (var5 instanceof NBTTagShort) {
							NBTTagShort var6 = (NBTTagShort) var5;
							String var7 = var6.getName();
							short var8 = var6.data;
							this.idCounts.put(var7, Short.valueOf(var8));
						}
					}
				}
			}
		} catch (Exception var9) {
			var9.printStackTrace();
		}
	}

	public int getUniqueDataId(String par1Str) {
		Short var2 = (Short) this.idCounts.get(par1Str);

		if (var2 == null) {
			var2 = Short.valueOf((short) 0);
		} else {
			var2 = Short.valueOf((short) (var2.shortValue() + 1));
		}

		this.idCounts.put(par1Str, var2);

		if (this.saveHandler == null) {
			return var2.shortValue();
		} else {
			try {
				VFile2 var3 = this.getMapFile("idcounts");

				if (var3 != null) {
					NBTTagCompound var4 = new NBTTagCompound();
					Iterator var5 = this.idCounts.keySet().iterator();

					while (var5.hasNext()) {
						String var6 = (String) var5.next();
						short var7 = ((Short) this.idCounts.get(var6)).shortValue();
						var4.setShort(var6, var7);
					}

					try (DataOutputStream var9 = new DataOutputStream(var3.getOutputStream())) {
						CompressedStreamTools.write(var4, var9);
					}
				}
			} catch (Exception var8) {
				var8.printStackTrace();
			}

			return var2.shortValue();
		}
	}

	private VFile2 getMapFile(String name) {
		if (this.saveHandler instanceof AnvilSaveConverter.VFileSaveHandler) {
			return ((AnvilSaveConverter.VFileSaveHandler) this.saveHandler).getMapFile(name);
		}
		return null;
	}
}
