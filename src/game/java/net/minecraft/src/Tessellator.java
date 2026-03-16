package net.minecraft.src;

public class Tessellator {

	private final net.peyton.eagler.minecraft.Tessellator delegate;

	public static final Tessellator instance = new Tessellator(net.peyton.eagler.minecraft.Tessellator.instance);

	private Tessellator(net.peyton.eagler.minecraft.Tessellator delegate) {
		this.delegate = delegate;
	}

	public void startDrawingQuads() {
		delegate.startDrawingQuads();
	}

	public void startDrawing(int mode) {
		delegate.startDrawing(mode);
	}

	public void draw() {
		delegate.draw();
	}

	public void addVertexWithUV(double x, double y, double z, double u, double v) {
		delegate.addVertexWithUV(x, y, z, u, v);
	}

	public void addVertex(double x, double y, double z) {
		delegate.addVertex(x, y, z);
	}

	public void setColorOpaque_I(int color) {
		delegate.setColorOpaque_I(color);
	}

	public void setColorRGBA_I(int color, int alpha) {
		delegate.setColorRGBA_I(color, alpha);
	}

	public void setColorOpaque_F(float r, float g, float b) {
		delegate.setColorOpaque_F(r, g, b);
	}

	public void setColorRGBA_F(float r, float g, float b, float a) {
		delegate.setColorRGBA_F(r, g, b, a);
	}

	public void setColorOpaque(int r, int g, int b) {
		delegate.setColorOpaque(r, g, b);
	}

	public void setColorRGBA(int r, int g, int b, int a) {
		delegate.setColorRGBA(r, g, b, a);
	}

	public void disableColor() {
		delegate.disableColor();
	}

	public void setNormal(float x, float y, float z) {
		delegate.setNormal(x, y, z);
	}

	public void setTranslation(double x, double y, double z) {
		delegate.setTranslationD(x, y, z);
	}

	public void setTranslationD(double x, double y, double z) {
		delegate.setTranslationD(x, y, z);
	}

	public void setTranslationF(float x, float y, float z) {
		delegate.setTranslationF(x, y, z);
	}

	public void addTranslation(float x, float y, float z) {
		delegate.setTranslationF(x, y, z);
	}

	public void setBrightness(int brightness) {
		delegate.setBrightness(brightness);
	}

	public void setRenderingChunk(boolean renderingChunk) {
		delegate.setRenderingChunk(renderingChunk);
	}

}
