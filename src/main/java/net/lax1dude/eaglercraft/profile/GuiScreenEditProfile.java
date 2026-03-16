/*
 * Copyright (c) 2022-2024 lax1dude, ayunami2000. All Rights Reserved.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 */

package net.lax1dude.eaglercraft.profile;

import net.lax1dude.eaglercraft.EagRuntime;
import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.GuiTextField;
import net.minecraft.src.StringTranslate;
import org.lwjgl.input.Keyboard;

public class GuiScreenEditProfile extends GuiScreen {

	private final GuiScreen parent;
	private GuiTextField usernameField;
	private GuiButton doneButton;

	public GuiScreenEditProfile(GuiScreen parent) {
		this.parent = parent;
	}

	public void initGui() {
		Keyboard.enableRepeatEvents(true);
		StringTranslate tr = StringTranslate.getInstance();

		this.controlList.clear();
		this.doneButton = new GuiButton(0, this.width / 2 - 100, this.height / 4 + 96 + 12,
				tr.translateKey("gui.done"));
		this.controlList.add(this.doneButton);
		this.controlList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 120 + 12,
				tr.translateKey("gui.cancel")));

		this.usernameField = new GuiTextField(this.fontRenderer, this.width / 2 - 100, 60, 200, 20);
		this.usernameField.setFocused(true);
		String currentName = EaglerProfile.getName();
		this.usernameField.setText(currentName == null ? "" : currentName);
		this.usernameField.setMaxStringLength(16);
		updateDoneEnabled();
	}

	public void updateScreen() {
		this.usernameField.updateCursorCounter();
	}

	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
	}

	protected void keyTyped(char par1, int par2) {
		this.usernameField.textboxKeyTyped(par1, par2);
		updateDoneEnabled();

		if (par1 == 13) {
			this.actionPerformed(this.doneButton);
			return;
		}
		if (par2 == 1) {
			this.actionPerformed((GuiButton)this.controlList.get(1));
		}
	}

	protected void mouseClicked(int par1, int par2, int par3) {
		super.mouseClicked(par1, par2, par3);
		this.usernameField.mouseClicked(par1, par2, par3);
	}

	protected void actionPerformed(GuiButton par1GuiButton) {
		if (!par1GuiButton.enabled) {
			return;
		}
		if (par1GuiButton.id == 0) {
			saveProfile();
			this.mc.displayGuiScreen(this.parent);
		} else if (par1GuiButton.id == 1) {
			markProfileSeen();
			this.mc.displayGuiScreen(this.parent);
		}
	}

	public void drawScreen(int par1, int par2, float par3) {
		this.drawDefaultBackground();
		this.drawCenteredString(this.fontRenderer, "Edit Profile", this.width / 2, this.height / 4 - 60 + 20, 16777215);
		this.drawString(this.fontRenderer, "Username", this.width / 2 - 100, 47, 10526880);
		this.usernameField.drawTextBox();
		super.drawScreen(par1, par2, par3);
	}

	private void updateDoneEnabled() {
		if (this.doneButton != null) {
			this.doneButton.enabled = this.usernameField.getText().trim().length() > 0;
		}
	}

	private void saveProfile() {
		String name = sanitizeName(this.usernameField.getText());
		EaglerProfile.setName(name);
		EaglerProfile.save();
		markProfileSeen();
	}

	private void markProfileSeen() {
		EagRuntime.setStorage("profileSeen", new byte[] { 1 });
	}

	private static String sanitizeName(String name) {
		if (name == null) {
			name = "";
		}
		name = name.trim().replaceAll("[^A-Za-z0-9]", "_");
		while (name.length() < 3) {
			name = name + "_";
		}
		if (name.length() > 16) {
			name = name.substring(0, 16);
		}
		return name;
	}
}
