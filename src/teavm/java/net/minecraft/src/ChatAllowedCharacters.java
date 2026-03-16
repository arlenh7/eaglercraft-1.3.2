package net.minecraft.src;

import java.util.List;

import net.lax1dude.eaglercraft.EagRuntime;

public class ChatAllowedCharacters {
	/**
	 * This String have the characters allowed in any text drawing of minecraft.
	 */
	public static final String allowedCharacters = getAllowedCharacters();

	/**
	 * Array of special characters that may not be used in a filename. GuiCreateWorld will replace these with an
	 * underscore when creating world save directory.
	 */
	public static final char[] invalidFilenameCharacters = new char[] { '/', '\n', '\r', '\t', '\u0000', '\f', '`', '?', '*', '\\', '<', '>', '|', '\"', ':' };

	/**
	 * Load the font.txt resource file, that is on UTF-8 format. This file contains the characters that minecraft can
	 * render Strings on screen.
	 */
	private static String getAllowedCharacters() {
		StringBuilder out = new StringBuilder();
		try {
			List<String> lines = EagRuntime.getResourceLines("/font.txt");
			if (lines != null) {
				for (int i = 0, l = lines.size(); i < l; ++i) {
					String line = lines.get(i);
					if (line != null && !line.startsWith("#")) {
						out.append(line);
					}
				}
			}
		} catch (Throwable t) {
			// ignore missing font.txt
		}
		return out.toString();
	}

	public static final boolean isAllowedCharacter(char par0) {
		return par0 != 167 && (allowedCharacters.indexOf(par0) >= 0 || par0 > 32);
	}

	/**
	 * Filter string by only keeping those characters for which isAllowedCharacter() returns true.
	 */
	public static String filerAllowedCharacters(String par0Str) {
		StringBuilder var1 = new StringBuilder();
		char[] var2 = par0Str.toCharArray();
		int var3 = var2.length;

		for (int var4 = 0; var4 < var3; ++var4) {
			char var5 = var2[var4];

			if (isAllowedCharacter(var5)) {
				var1.append(var5);
			}
		}

		return var1.toString();
	}
}
