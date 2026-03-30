package me.seyit;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.network.chat.Component;
import org.lwjgl.glfw.GLFW;

public class NoMissClient implements ClientModInitializer {
    private static boolean enabled = true;
    private static KeyMapping toggleKey;

    @Override
    public void onInitializeClient() {
        toggleKey = KeyMappingHelper.registerKeyMapping(new KeyMapping(
                "key.no-miss.toggle",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_N,
                KeyMapping.Category.MISC
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (toggleKey.consumeClick()) {
                enabled = !enabled;
                if (client.player != null) {
                    client.player.sendOverlayMessage(Component.literal(enabled ? "No Miss ON" : "No Miss OFF"));
                }
            }
        });
    }

    public static boolean isEnabled() {
        return enabled;
    }
}
