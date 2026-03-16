package net.minecraft.src;

public class FontRenderer extends net.peyton.eagler.minecraft.FontRenderer {

	private boolean unicodeFlag = false;
	private boolean bidiFlag = false;

	public FontRenderer(GameSettings gameSettingsIn, String location, RenderEngine textureManagerIn) {
		super(gameSettingsIn, location, textureManagerIn);
	}

	public void drawSplitString(String str, int x, int y, int wrapWidth, int color) {
		this.func_27278_a(str, x, y, wrapWidth, color);
	}

	public void setUnicodeFlag(boolean flag) {
		this.unicodeFlag = flag;
	}

	public void setBidiFlag(boolean flag) {
		this.bidiFlag = flag;
	}

	public boolean getBidiFlag() {
		return this.bidiFlag;
	}

}
