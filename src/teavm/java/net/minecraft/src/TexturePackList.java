package net.minecraft.src;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.minecraft.client.Minecraft;
import net.peyton.eagler.minecraft.ResourcePack;

public class TexturePackList
{
	private static final TexturePackBase DEFAULT_PACK = new TexturePackDefault();

	/** The Minecraft instance. */
	private final Minecraft mc;

	/** The list of the available texture packs. */
	private List<TexturePackBase> availableTexturePacks = new ArrayList<>();
	private Map<String, TexturePackBase> packCache = new HashMap<>();

	/** The TexturePack that will be used. */
	private TexturePackBase selectedTexturePack = DEFAULT_PACK;
	private boolean downloadingTexturePack = false;

	public TexturePackList(File par1File, Minecraft par2Minecraft)
	{
		this.mc = par2Minecraft;
		this.updateAvaliableTexturePacks();
	}

	/**
	 * Sets the new TexturePack to be used, returning true if it has actually changed, false if nothing changed.
	 */
	public boolean setTexturePack(TexturePackBase par1TexturePackBase)
	{
		if (par1TexturePackBase == this.selectedTexturePack)
		{
			return false;
		}
		else
		{
			this.selectedTexturePack = par1TexturePackBase;
			this.mc.gameSettings.skin = par1TexturePackBase.func_77538_c();
			this.mc.gameSettings.saveOptions();
			return true;
		}
	}

	/**
	 * Server texture packs are not supported in the TeaVM build yet.
	 */
	public void requestDownloadOfTexture(String par1Str)
	{
		this.downloadingTexturePack = false;
	}

	public boolean func_77295_a()
	{
		return this.downloadingTexturePack;
	}

	public void func_77304_b()
	{
		this.downloadingTexturePack = false;
		this.updateAvaliableTexturePacks();
		this.mc.func_71395_y();
	}

	/**
	 * check the texture packs the client has installed
	 */
	public void updateAvaliableTexturePacks()
	{
		ArrayList<TexturePackBase> fresh = new ArrayList<>();
		this.selectedTexturePack = DEFAULT_PACK;
		fresh.add(DEFAULT_PACK);

		List<ResourcePack> packs = ResourcePack.getExistingResourcePacks();
		for (int i = 0, j = packs.size(); i < j; ++i)
		{
			ResourcePack rp = packs.get(i);
			String packName = rp.getName();
			TexturePackBase pack = this.packCache.get(packName);

			if (pack == null)
			{
				pack = new TexturePackCustom(rp);
				this.packCache.put(packName, pack);
			}

			if (pack.func_77538_c().equals(this.mc.gameSettings.skin))
			{
				this.selectedTexturePack = pack;
			}

			fresh.add(pack);
		}

		this.availableTexturePacks.removeAll(fresh);
		Iterator<TexturePackBase> oldIt = this.availableTexturePacks.iterator();
		while (oldIt.hasNext())
		{
			TexturePackBase oldPack = oldIt.next();
			oldPack.func_77533_a(this.mc.renderEngine);
			this.packCache.remove(oldPack.func_77536_b());
		}

		this.availableTexturePacks = fresh;
	}

	/**
	 * Returns a list of the available texture packs.
	 */
	public List availableTexturePacks()
	{
		return Collections.unmodifiableList(this.availableTexturePacks);
	}

	public TexturePackBase getSelectedTexturePack()
	{
		return this.selectedTexturePack;
	}

	public boolean func_77300_f()
	{
		if (!this.mc.gameSettings.serverTextures)
		{
			return false;
		}
		else
		{
			ServerData var1 = this.mc.getServerData();
			return var1 == null ? true : var1.func_78840_c();
		}
	}

	public boolean getAcceptsTextures()
	{
		if (!this.mc.gameSettings.serverTextures)
		{
			return false;
		}
		else
		{
			ServerData var1 = this.mc.getServerData();
			return var1 != null && var1.getAcceptsTextures();
		}
	}

	static boolean func_77301_a(TexturePackList par0TexturePackList)
	{
		return par0TexturePackList.downloadingTexturePack;
	}

	static TexturePackBase func_77303_a(TexturePackList par0TexturePackList, TexturePackBase par1TexturePackBase)
	{
		return par0TexturePackList.selectedTexturePack = par1TexturePackBase;
	}

	static String func_77291_a(TexturePackList par0TexturePackList, File par1File)
	{
		return par1File != null ? par1File.getName() : null;
	}

	static Minecraft getMinecraft(TexturePackList par0TexturePackList)
	{
		return par0TexturePackList.mc;
	}
}
