package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;

public abstract class CraftingInventoryCB implements ICraftingHandler {
	public List<ItemStack> field_20123_d = new ArrayList<>();
	public List<Slot> field_20122_e = new ArrayList<>();
	public int unusedList = 0;
	private short craftMatrix = 0;
	protected List<ICrafting> field_20121_g = new ArrayList<>();

	protected void func_20117_a(Slot var1) {
		var1.slotNumber = this.field_20122_e.size();
		this.field_20122_e.add(var1);
		this.field_20123_d.add((ItemStack)null);
	}

	public void func_20114_a() {
		for(int var1 = 0; var1 < this.field_20122_e.size(); ++var1) {
			ItemStack var2 = this.field_20122_e.get(var1).getStack();
			ItemStack var3 = this.field_20123_d.get(var1);
			if(!ItemStack.areItemStacksEqual(var3, var2)) {
				var3 = var2 == null ? null : var2.copy();
				this.field_20123_d.set(var1, var3);
			}
		}

	}

	public Slot func_20118_a(int var1) {
		return this.field_20122_e.get(var1);
	}

	public ItemStack func_20116_a(int var1, int var2, EntityPlayer var3) {
		ItemStack var4 = null;
		if(var2 == 0 || var2 == 1) {
			InventoryPlayer var5 = var3.inventory;
			if(var1 == -999) {
				if(var5.getCurrentItem() != null && var1 == -999) {
					if(var2 == 0) {
						var3.dropPlayerItem(var5.getCurrentItem());
					}

					if(var2 == 1) {
						var3.dropPlayerItem(var5.getCurrentItem().splitStack(1));
					}
				}
			} else {
				Slot var6 = (Slot)this.field_20122_e.get(var1);
				if(var6 != null) {
					var6.onSlotChanged();
					ItemStack var7 = var6.getStack();
					if(var7 != null) {
						var4 = var7.copy();
					}

					if(var7 != null || var5.getCurrentItem() != null) {
						int var8;
						if(var7 != null && var5.getCurrentItem() == null) {
							var8 = var2 == 0 ? var7.stackSize : (var7.stackSize + 1) / 2;
							if(var7.stackSize == 0) {
								var6.putStack((ItemStack)null);
							}

							var6.onPickupFromSlot(var7);
						} else if(var7 == null && var5.getCurrentItem() != null && var6.isItemValid(var5.getCurrentItem())) {
							var8 = var2 == 0 ? var5.getCurrentItem().stackSize : 1;
							if(var8 > var6.getSlotStackLimit()) {
								var8 = var6.getSlotStackLimit();
							}

							var6.putStack(var5.getCurrentItem().splitStack(var8));
						} else if(var7 != null && var5.getCurrentItem() != null) {
							if(var6.isItemValid(var5.getCurrentItem())) {
								if(var7.itemID != var5.getCurrentItem().itemID) {
									if(var5.getCurrentItem().stackSize <= var6.getSlotStackLimit()) {
										var6.putStack(var5.getCurrentItem());
									}
								} else if(var7.itemID == var5.getCurrentItem().itemID) {
									if(var2 == 0) {
										var8 = var5.getCurrentItem().stackSize;
										if(var8 > var6.getSlotStackLimit() - var7.stackSize) {
											var8 = var6.getSlotStackLimit() - var7.stackSize;
										}

										if(var8 > var5.getCurrentItem().getMaxStackSize() - var7.stackSize) {
											var8 = var5.getCurrentItem().getMaxStackSize() - var7.stackSize;
										}

										var5.getCurrentItem().splitStack(var8);

										var7.stackSize += var8;
									} else if(var2 == 1) {
										var8 = 1;
										if(var8 > var6.getSlotStackLimit() - var7.stackSize) {
											var8 = var6.getSlotStackLimit() - var7.stackSize;
										}

										if(var8 > var5.getCurrentItem().getMaxStackSize() - var7.stackSize) {
											var8 = var5.getCurrentItem().getMaxStackSize() - var7.stackSize;
										}

										var5.getCurrentItem().splitStack(var8);

										var7.stackSize += var8;
									}
								}
							} else if(var7.itemID == var5.getCurrentItem().itemID && var5.getCurrentItem().getMaxStackSize() > 1) {
								var8 = var7.stackSize;
								if(var8 > 0 && var8 + var5.getCurrentItem().stackSize <= var5.getCurrentItem().getMaxStackSize()) {
									ItemStack var10000 = var5.getCurrentItem();
									var10000.stackSize += var8;
									var7.splitStack(var8);
									if(var7.stackSize == 0) {
										var6.putStack((ItemStack)null);
									}

									var6.onPickupFromSlot(var7);
								}
							}
						}
					}
				}
			}
		}

		return var4;
	}

	public void onCraftGuiClosed(EntityPlayer var1) {
		InventoryPlayer var2 = var1.inventory;
		if(var2.getCurrentItem() != null) {
			var1.dropPlayerItem(var2.getCurrentItem());
		}

	}

	public void onCraftMatrixChanged(IInventory var1) {
		this.func_20114_a();
	}

	public void func_20119_a(int var1, ItemStack var2) {
		this.func_20118_a(var1).putStack(var2);
	}

	public void func_20115_a(ItemStack[] var1) {
		for(int var2 = 0; var2 < var1.length; ++var2) {
			this.func_20118_a(var2).putStack(var1[var2]);
		}

	}

	public void func_20112_a(int var1, int var2) {
	}

	public short func_20111_a(InventoryPlayer var1) {
		++this.craftMatrix;
		return this.craftMatrix;
	}

	public void func_20113_a(short var1) {
	}

	public void func_20110_b(short var1) {
	}

	public abstract boolean func_20120_b(EntityPlayer var1);
}
