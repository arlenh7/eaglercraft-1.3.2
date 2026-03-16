package net.minecraft.src;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

import net.lax1dude.eaglercraft.EagRuntime;
import net.lax1dude.eaglercraft.opengl.ImageData;
import org.lwjgl.opengl.GL11;

public abstract class TexturePackImplementation implements TexturePackBase {
	private final String field_77545_e;
	private final String field_77542_f;
	protected final File field_77548_a;
	protected String field_77546_b;
	protected String field_77547_c;
	protected ImageData field_77544_d;
	private int field_77543_g;

	protected TexturePackImplementation(String name, String displayName) {
		this(name, null, displayName);
	}

	protected TexturePackImplementation(String name, File file, String displayName) {
		this.field_77543_g = -1;
		this.field_77545_e = name;
		this.field_77542_f = displayName;
		this.field_77548_a = file;
		this.func_77539_g();
		this.func_77540_a();
	}

	private static String truncateLine(String value) {
		if (value != null && value.length() > 34) {
			return value.substring(0, 34);
		}
		return value != null ? value : "";
	}

	private void func_77539_g() {
		InputStream in = null;
		try {
			in = this.getResourceAsStream("/pack.png");
			if (in != null) {
				ImageData img = ImageData.loadImageFile(in);
				this.field_77544_d = img != null ? img.swapRB() : null;
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
	}

	protected void func_77540_a() {
		this.field_77546_b = "";
		this.field_77547_c = "";

		try {
			List<String> lines = EagRuntime.getResourceLines("/pack.txt");
			if (lines != null && !lines.isEmpty()) {
				this.field_77546_b = truncateLine(lines.get(0));
				if (lines.size() > 1) {
					this.field_77547_c = truncateLine(lines.get(1));
				}
			} else {
				InputStream in = this.getResourceAsStream("/pack.txt");
				if (in != null) {
					try (BufferedReader rd = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))) {
						this.field_77546_b = truncateLine(rd.readLine());
						this.field_77547_c = truncateLine(rd.readLine());
					}
				}
			}
		} catch (Throwable t) {
			// ignore missing pack.txt
		}
	}

	@Override
	public void func_77533_a(RenderEngine renderEngine) {
		if (this.field_77544_d != null && this.field_77543_g != -1) {
			renderEngine.deleteTexture(this.field_77543_g);
		}
	}

	@Override
	public void func_77535_b(RenderEngine renderEngine) {
		if (this.field_77544_d != null) {
			if (this.field_77543_g == -1) {
				this.field_77543_g = renderEngine.allocateAndSetupTexture(this.field_77544_d);
			}
			renderEngine.bindTexture(this.field_77543_g);
		} else {
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, renderEngine.getTexture("/gui/unknown_pack.png"));
		}
	}

	@Override
	public InputStream getResourceAsStream(String path) {
		return EagRuntime.getResourceStream(path);
	}

	@Override
	public String func_77536_b() {
		return this.field_77545_e;
	}

	@Override
	public String func_77538_c() {
		return this.field_77542_f;
	}

	@Override
	public String func_77531_d() {
		return this.field_77546_b;
	}

	@Override
	public String func_77537_e() {
		return this.field_77547_c;
	}

	@Override
	public int func_77534_f() {
		return 16;
	}
}
