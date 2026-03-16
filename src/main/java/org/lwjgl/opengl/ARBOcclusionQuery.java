package org.lwjgl.opengl;

import net.lax1dude.eaglercraft.internal.buffer.IntBuffer;

public class ARBOcclusionQuery {

	public static final int GL_SAMPLES_PASSED_ARB = 0x8914;
	public static final int GL_QUERY_RESULT_AVAILABLE_ARB = 0x8867;
	public static final int GL_QUERY_RESULT_ARB = 0x8866;

	public static void glGenQueriesARB(IntBuffer buffer) {
		if (buffer == null) {
			return;
		}
		for (int i = buffer.position(); i < buffer.limit(); ++i) {
			buffer.put(i, 0);
		}
	}

	public static void glBeginQueryARB(int target, int id) {
	}

	public static void glEndQueryARB(int target) {
	}

	public static void glGetQueryObjectuARB(int id, int pname, IntBuffer params) {
		if (params == null || params.remaining() == 0) {
			return;
		}
		params.put(params.position(), 1);
	}
}
