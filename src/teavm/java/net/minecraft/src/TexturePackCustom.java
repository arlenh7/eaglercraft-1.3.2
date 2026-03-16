package net.minecraft.src;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.io.File;
import net.lax1dude.eaglercraft.opengl.ImageData;
import net.peyton.eagler.minecraft.ResourcePack;

import org.lwjgl.opengl.GL11;

public class TexturePackCustom implements TexturePackBase {
	private int texturePackName = -1;
	private ImageData texturePackThumbnail;
	private ResourcePack rp;
	private final String texturePackFileName;
	private String descriptionLine1 = "";
	private String descriptionLine2 = "";

	public TexturePackCustom(ResourcePack pack) {
		this.rp = pack;
		this.texturePackFileName = pack.getName();
		this.loadPackInfo();
	}

	// Compatibility constructor for any legacy call sites (server texture downloads are ignored in TeaVM).
	public TexturePackCustom(String packName, File ignored) {
		this.rp = null;
		this.texturePackFileName = packName != null ? packName : "custom";
	}

	private static String truncateString(String value) {
		if (value != null && value.length() > 34) {
			return value.substring(0, 34);
		}
		return value != null ? value : "";
	}

	private void loadPackInfo() {
		InputStream in = null;
		try {
			try {
				in = rp.getResourceAsStream("pack.txt");
				if (in != null) {
					BufferedReader reader = new BufferedReader(new InputStreamReader(in));
					this.descriptionLine1 = truncateString(reader.readLine());
					this.descriptionLine2 = truncateString(reader.readLine());
					reader.close();
				}
			} catch (Throwable t) {
				// ignore missing pack.txt
			} finally {
				try {
					if (in != null) {
						in.close();
					}
				} catch (Throwable t) {
				}
			}

			in = null;
			try {
				in = rp.getResourceAsStream("pack.png");
				if (in != null) {
					this.texturePackThumbnail = ImageData.loadImageFile(in).swapRB();
				}
			} catch (Throwable t) {
				// ignore missing pack.png
			} finally {
				try {
					if (in != null) {
						in.close();
					}
				} catch (Throwable t) {
				}
			}
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	@Override
	public void func_77533_a(RenderEngine renderEngine) {
		if (this.texturePackThumbnail != null && this.texturePackName != -1) {
			renderEngine.deleteTexture(this.texturePackName);
		}
	}

	@Override
	public void func_77535_b(RenderEngine renderEngine) {
		if (this.texturePackThumbnail != null) {
			if (this.texturePackName == -1) {
				this.texturePackName = renderEngine.allocateAndSetupTexture(this.texturePackThumbnail);
			}
			renderEngine.bindTexture(this.texturePackName);
		} else {
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, renderEngine.getTexture("/gui/unknown_pack.png"));
		}
	}

	@Override
	public InputStream getResourceAsStream(String path) {
		if (rp != null) {
			return rp.getResourceAsStream(path);
		}
		return net.lax1dude.eaglercraft.EagRuntime.getResourceStream(path);
	}

	@Override
	public String func_77536_b() {
		return this.texturePackFileName;
	}

	@Override
	public String func_77538_c() {
		return this.texturePackFileName;
	}

	@Override
	public String func_77531_d() {
		return this.descriptionLine1;
	}

	@Override
	public String func_77537_e() {
		return this.descriptionLine2;
	}

	@Override
	public int func_77534_f() {
		return 16;
	}
}
