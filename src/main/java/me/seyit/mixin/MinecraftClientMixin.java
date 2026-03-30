package me.seyit.mixin;

import me.seyit.NoMissClient;
import net.minecraft.client.Minecraft;
import net.minecraft.world.phys.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Minecraft.class)
public class MinecraftClientMixin {
    @Inject(method = "startAttack", at = @At("HEAD"), cancellable = true)
    private void onAttack(CallbackInfoReturnable<Boolean> cir) {
        Minecraft mc = Minecraft.getInstance();
        
        if (!NoMissClient.isEnabled()) {
            return;
        }

        if (mc.hitResult == null || mc.hitResult.getType() != HitResult.Type.ENTITY) {
            cir.setReturnValue(false);
        }
    }
}
